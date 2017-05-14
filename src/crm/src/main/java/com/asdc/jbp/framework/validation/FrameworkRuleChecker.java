/**
 * Project Name : jbp-framework <br>
 * File Name : FrameworkRuleChecker.java <br>
 * Package Name : com.asdc.jbp.framework.validation <br>
 * Create Time : Apr 13, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.framework.validation;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionValidationException;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * ClassName : FrameworkRuleChecker <br>
 * Description : check whether the Componet are defined under the rule of scope <br>
 * <h1>Rules:</h1>
 * <h2>1. Beans' Annotation rules</h2>
 * <p>
 * <ul>
 * <li><b>Controller Layer</b> beans must only annotated by {@link Controller}.</li>
 * <li><b>Service Layer</b> beans must only annotated by {@link Service}.</li>
 * <li><b>Dao Layer</b> beans must only annotated by {@link Repository}.</li>
 * </ul>
 * </p>
 * <h2>2. Beans' Scope rules</h2>
 * <p>
 * <ul>
 * <li><b>Controllers'</b> Scope must be <b>prototype</b> with annotation {@link Scope} with param {@link ConfigurableBeanFactory#SCOPE_PROTOTYPE}.</li>
 * <li><b>Services'</b> Scope must be <b>prototype</b> with annotation {@link Scope} with param {@link ConfigurableBeanFactory#SCOPE_PROTOTYPE}</li>
 * <li><b>Daos'</b> Scope must be <b>singleton</b> with annotation {@link Scope} with param {@link ConfigurableBeanFactory#SCOPE_SINGLETON}</li>
 * </ul>
 * </p>
 * <h2>3. Beans' Name rules</h2>
 * <p>
 * <ul>
 * <li><b>Controllers'</b> name must only end with 'Controller' like 'FooController'.</li>
 * <li><b>Services'</b> name must only end with <b>'ServiceImpl'</b> like 'FooServiceImpl'.</li>
 * <li><b>Daos'</b> name must only end with 'Dao' like 'FooDao'.</li>
 * </ul>
 * </p>
 * <h2>4. Security proxy rules</h2>
 * <p>
 * Method security validation with http session, <b>must only be cut at service layer</b> with annotation
 * like @PreAuthorize(\"hasAnyAuthority(['ADD_USER','LOGIN','DEL_GROUP'])\").
 * </p>
 * <h2>5. Controller proxy rules</h2>
 * <p>
 * <b>Controller must not be proxied by any way</b>. If you set some AOP login at controller layer, please remove it, or cut it into service layer.
 * </p>
 * <h2>6. Service Interface rules</h2>
 * <p>
 * <b>Services must implement an interface named end with 'Service'</b> like 'FooServiceImpl' implemnting 'FooService'.
 * </p>
 * Create Time : Apr 13, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
@Component
public class FrameworkRuleChecker implements BeanFactoryPostProcessor {

    private ConfigurableListableBeanFactory factory;
    private Logger log = LoggerFactory.getLogger(getClass());

    private final static String CNS_ADVISE_SINGLETON = "Please add @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) to the type definition";
    private final static String CNS_ADVISE_PROTOTYPE = "Please add @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) to the type definition";

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.beans.factory.config.BeanFactoryPostProcessor#postProcessBeanFactory(org.springframework.beans.factory.config.
     * ConfigurableListableBeanFactory)
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        log.info("###########################################################");
        log.info("Validating the BeanDefinition.");
        log.info("");
        this.factory = beanFactory;
        boolean valid = true;
        valid &= validateComponentsAnnotation();
        valid &= validateScopeDefinitions();
        valid &= validateNameDefinitions();
        valid &= validateSecurityProxy();
        valid &= validateControllerProxy();
        valid &= validateServicesInterface();
        if (valid) {
            log.info("Result: validation success, and there is no bad codes.");
        } else {
            printRule();
            log.error("Result: validation failed!, Please change code fllowing the advise above.");
        }
        log.info("");
        log.info("Validation of BeanDefinition done");
        log.info("###########################################################");
        if (!valid) {
            throw new BeanDefinitionValidationException("There are beans with bad Scope Definition");
        }
    }

    private void printRule() {
        log.info("Rules: ");
        log.info("1. Beans' Annotation rules:");
        log.info("    Controller Layer beans must only annotated by @Controller.");
        log.info("    Service Layer beans must only annotated by @Service.");
        log.info("    Dao Layer beans must only annotated by @Repository.");
        log.info("");
        log.info("2. Beans' Scope rules:");
        log.info("    Controllers' Scope must be prototype with annotation @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE).");
        log.info("    Services' Scope must be prototype with annotation @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE).");
        log.info("    Daos' Scope must be singleton with annotation @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON).");
        log.info("");
        log.info("3. Beans' Name rules:");
        log.info("    Controllers' name must only end with 'Controller' like 'FooController'.");
        log.info("    Services' name must only end with 'ServiceImpl' like 'FooServiceImpl'.");
        log.info("    Daos' name must only end with 'Dao' like 'FooDao'.");
        log.info("");
        log.info("4. Security proxy rules:");
        log.info("    Method security validation with http session, "
                + "must only be cut at service layer with annotation like @PreAuthorize(\"hasAnyAuthority(['ADD_USER','LOGIN','DEL_GROUP'])\").");
        log.info("");
        log.info("5. Controller proxy rules:");
        log.info("    Controller must not be proxied by any way. "
                + "If you set some AOP login at controller layer, please remove it, or cut it into service layer.");
        log.info("");
        log.info("6. Service Interface rules:");
        log.info("    Services must implement an interface named end with 'Service' like 'FooServiceImpl' implemnting 'FooService'.");
        log.info("");
    }

    private void printIssue(String type, String name, String issue, String advise) {
        log.error("Bad code found:");
        log.error("bean: {}", type);
        log.error("name: \"{}\"", name);
        log.error("issue: {}", issue);
        log.error("advise: {}", advise);
        log.error("");
    }

    /**
     * Description : check whether target type should ignore rule validation <br>
     * Create Time: Apr 15, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param type
     * @return true for the target type wants ignore the rule check
     */
    private boolean isIgnore(Class<?> type) {
        return type.getAnnotation(IgnoreFrameworkRuleCheck.class) != null;
    }

    /**
     * Description : target type's scope is defined by "singleton" <br>
     * Create Time: Apr 15, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param type
     * @return true for the bean is defined by Singleton Scope or no Scope defined with singleton by default
     */
    private boolean isScopeSingleton(Class<?> type) {
        Scope scope = type.getAnnotation(Scope.class);
        return scope == null || ConfigurableBeanFactory.SCOPE_SINGLETON.equals(scope);
    }

    /**
     * Description : validate, controller service and dao's scope definition <br>
     * Create Time: Apr 15, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @see #validateControllersScope()
     * @see #validateServicesScope()
     * @see #validateDaosScope()
     * @return true for all valid
     */
    private boolean validateScopeDefinitions() {
        boolean valid = true;
        valid &= validateControllersScope();
        valid &= validateServicesScope();
        valid &= validateDaosScope();
        return valid;
    }

    /**
     * Description : validate whether the controller, service and dao are named by rule <br>
     * Create Time: Apr 15, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @see #validateControllersName()
     * @see #validateServicesName()
     * @see #validateDaosName()
     * @return true for all valid
     */
    protected boolean validateNameDefinitions() {
        boolean valid = true;
        valid &= validateControllersName();
        valid &= validateServicesName();
        valid &= validateDaosName();
        return valid;
    }

    /**
     * Description : validate controller scope definition <br>
     * The controller's scope should be "prototype" <br>
     * Create Time: Apr 15, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @return true for all valid
     */
    protected boolean validateControllersScope() {
        boolean valid = true;
        String[] names = factory.getBeanNamesForAnnotation(Controller.class);
        for (String name : names) {
            Class<?> type = factory.getType(name);
            if (!isIgnore(type) && isScopeSingleton(type)) {
                printIssue(type.getName(), name, "It's a controller. But the Scope is Singleton.", CNS_ADVISE_PROTOTYPE);
                valid = false;
            }
        }
        return valid;
    }

    /**
     * Description : validate whether the controller is named by "*Controller" <br>
     * Create Time: Apr 15, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @return true for all valid
     */
    protected boolean validateControllersName() {
        boolean valid = true;
        String[] names = factory.getBeanNamesForAnnotation(Controller.class);
        for (String name : names) {
            Class<?> type = factory.getType(name);
            if (!isIgnore(type) && !type.getName().endsWith("Controller")) {
                printIssue(type.getName(), name, "It's a controller. But it's not named end with \"Controller\".",
                        "Please rename it like " + type.getName() + "Controller");
                valid = false;
            }
        }
        return valid;
    }

    protected boolean validateControllerProxy() {
        boolean valid = true;
        String[] names = factory.getBeanNamesForAnnotation(Controller.class);
        for (String name : names) {
            Object bean = factory.getBean(name);
            if (AopUtils.isAopProxy(bean)) {
                printIssue(((Advised) bean).getTargetClass().getName(), name, "It's a proxied Controller, And a controller should not be proxied. ",
                        "Please verify it. Maybe it's been configed by Security or Other AOP.");
                valid = false;
            }
        }
        return valid;
    }

    /**
     * Description : validate whteher the service scope is "prototype" <br>
     * Create Time: Apr 15, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @return true for all valid
     */
    protected boolean validateServicesScope() {
        boolean valid = true;
        String[] names = factory.getBeanNamesForAnnotation(Service.class);
        for (String name : names) {
            Class<?> type = factory.getType(name);
            if (!isIgnore(type) && isScopeSingleton(type)) {
                printIssue(type.getName(), name, "It's a service. But the Scope is Singleton.", CNS_ADVISE_PROTOTYPE);
                valid = false;
            }
        }
        return valid;
    }

    /**
     * Description : validate whether the servie is named by "*ServiceImpl" <br>
     * Create Time: Apr 15, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @return true for all valid
     */
    protected boolean validateServicesName() {
        boolean valid = true;
        String[] names = factory.getBeanNamesForAnnotation(Service.class);
        for (String name : names) {
            Class<?> type = factory.getType(name);
            if (!isIgnore(type) && !type.getName().endsWith("ServiceImpl")) {
                printIssue(type.getName(), name, "It's a service. But it's not named end with \"ServiceImpl\".",
                        "Please rename it like " + type.getName() + "ServiceImpl");
                valid = false;
            }
        }
        return valid;
    }

    /**
     * Description : validate whether the service implemented a "*Service" named interface <br>
     * Create Time: Apr 15, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @return true for all valid
     */
    protected boolean validateServicesInterface() {
        boolean valid = true;
        String[] names = factory.getBeanNamesForAnnotation(Service.class);
        for (String name : names) {
            Class<?> type = factory.getType(name);
            if (!isIgnore(type)) {
                // check interface
                boolean serviceInterfaceFound = false;
                for (Class<?> clazz : type.getInterfaces()) {
                    if (clazz.getName().endsWith("Service")) {
                        serviceInterfaceFound = true;
                        break;
                    }
                }
                if (!serviceInterfaceFound) {
                    printIssue(type.getName(), name, "It's a service. But it has no \"*Service\" named Interface.",
                            "Please extract an interface named like \"*Service\" from it.");
                    valid = false;
                }
            }
        }
        return valid;
    }

    /**
     * Description : validate whether the daos scope is "sigleton" <br>
     * Create Time: Apr 15, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @return true for all valid
     */
    protected boolean validateDaosScope() {
        boolean valid = true;
        String[] names = factory.getBeanNamesForAnnotation(Repository.class);
        for (String name : names) {
            Class<?> type = factory.getType(name);
            if (!isIgnore(type) && !isScopeSingleton(type)) {
                printIssue(type.getName(), name, "It's a Dao. But the Scope is Prototype.", CNS_ADVISE_SINGLETON);
                valid = false;
            }
        }
        return valid;
    }

    /**
     * Description : validate whether the daos are named like "*Dao" <br>
     * Create Time: Apr 15, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @return true for all valid
     */
    protected boolean validateDaosName() {
        boolean valid = true;
        String[] names = factory.getBeanNamesForAnnotation(Repository.class);
        for (String name : names) {
            Class<?> type = factory.getType(name);
            if (!isIgnore(type) && !type.getName().endsWith("Dao")) {
                printIssue(type.getName(), name, "It's a DAO. But it's not named end with \"Dao\".", "Please rename it like " + type.getName() + "Dao");
                valid = false;
            }
        }
        return valid;
    }

    /**
     * Description : validate whether the beans are right annotated <br>
     * Create Time: Apr 15, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @return true for all valid
     */
    protected boolean validateComponentsAnnotation() {
        boolean valid = true;
        String[] names = factory.getBeanNamesForAnnotation(Component.class);
        for (String name : names) {
            Class<?> type = factory.getType(name);
            if (!isIgnore(type)) {
                if (type.getName().endsWith("Controller") && type.getAnnotation(Component.class) != null) {
                    printIssue(type.getName(), name, "It's named end with \"Controller\" like a controoler.", "please change the @Component with @Controller");
                    valid = false;
                }
                if (type.getName().endsWith("ServiceImpl") && type.getAnnotation(Component.class) != null) {
                    printIssue(type.getName(), name, "It's named end with \"ServiceImpl\" like a service.", "please change the @Component with @Service");
                    valid = false;
                }
                if (type.getName().endsWith("Dao") && type.getAnnotation(Component.class) != null) {
                    printIssue(type.getName(), name, "It's named end with \"Dao\" like a dao.", "please change the @Component with @Repository");
                    valid = false;
                }
            }
        }
        return valid;
    }

    /**
     * Description : validate whether the security AOP is on Service Layer <br>
     * Create Time: Apr 15, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @return true for all valid
     */
    protected boolean validateSecurityProxy() {
        boolean valid = true;
        String[] names = factory.getBeanDefinitionNames();
        for (String name : names) {
            Class<?> type = factory.getType(name);
            if (!isIgnore(type)) {
                if (type.getAnnotation(Controller.class) != null || type.getAnnotation(Repository.class) != null) {
                    if (type.getAnnotation(PreAuthorize.class) != null) {
                        printIssue(type.getName(), name, "It's not a Service, and Type should not do security check by @PreAuthorize",
                                "Please remove the @PreAuthorize, and add it to the service if it's nessesary!");
                        valid = false;
                    }
                    for (Method method : type.getMethods()) {
                        if (method.getAnnotation(PreAuthorize.class) != null) {
                            printIssue(type.getName(), name, "It's not a service, And Method {" + method.getName() + "} should not annotated by @PreAuthorize",
                                    "Please remove the @PreAuthorize, and add it to the service if it's nessesary!");
                            valid = false;
                        }
                    }
                }
            }
        }
        return valid;
    }
}
