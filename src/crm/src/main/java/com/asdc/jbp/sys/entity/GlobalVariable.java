
package com.asdc.jbp.sys.entity;

import com.asdc.jbp.framework.dao.JpaOrmOperator;
import com.asdc.jbp.framework.exception.ServiceException;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * ClassName: GlobalVariable.java <br>
 * Description: 当服务器启动时，将全局变量存入内存中<br>
 * Create by: yuruixin/ruixin_yu@asdc.com.cn <br>
 * Create Time: 2016年5月30日<br>
 */
public class GlobalVariable {

    @Resource(name = "SysCommonDao")
    private JpaOrmOperator sysCommonDao;

    /**
     * Description：将数据库中和配置文件中的全局参数存入内存中
     *
     * @author name：yuruixin
     **/
    @PostConstruct
    public void init() throws ServiceException, IOException {
        //noinspection unchecked
        List<SysGlobalVariable> SysGlobalVariableList = (List<SysGlobalVariable>) sysCommonDao.queryByNamedQuery("sys.hql.getGlobalVariableInit");
        for (SysGlobalVariable sysGlobalVariable : SysGlobalVariableList) {
            System.setProperty(sysGlobalVariable.getVariableName(), sysGlobalVariable.getVariableValue());
        }
    }
}
