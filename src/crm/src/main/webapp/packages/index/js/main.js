/***
 Metronic AngularJS App Main Script
 ***/
var Pagenum = [5, 10, 30, 50];
var itemsPerPage = 5;
var storage = window.sessionStorage;
var storageLocal = window.localStorage;
/* Metronic App */
var MetronicApp = angular.module("MetronicApp", [
    "ui.router",
    "ui.bootstrap",
    "oc.lazyLoad",
    "ngSanitize",
    'mgcrea.ngStrap', //弹框插件
    "pascalprecht.translate", //国际化
    'chieffancypants.loadingBar',
    'cfp.loadingBar',
    'angular-drag'
]);

/* Configure ocLazyLoader(refer: https://github.com/ocombe/ocLazyLoad) */
MetronicApp.config(['$ocLazyLoadProvider', function ($ocLazyLoadProvider) {
    $ocLazyLoadProvider.config({
        // global configs go here
    });
}]);

//AngularJS v1.3.x workaround for old style controller declarition in HTML
MetronicApp.config(['$controllerProvider', function ($controllerProvider) {
    // this option might be handy for migrating old apps, but please don't use it
    // in new ones!
    $controllerProvider.allowGlobals();
}]);

MetronicApp.config(['$translateProvider', function ($translateProvider) {
    // $translateProvider.preferredLanguage('cn/dashboardCN');
    //本地资源文件路径
    $translateProvider.useStaticFilesLoader({
        prefix: '/jbp/packages/pages/i18n/',
        suffix: '.json'
    });
}]);
MetronicApp.factory('httpInterceptor', ['$q', '$injector', '$rootScope', '$window', '$timeout', function ($q, $injector, $rootScope, $window) {
    return {
        'responseError': function (response) {
            if (response.status == 404) {
                var rootScope = $injector.get('$rootScope');
                rootScope.stateBeforLogin = $injector.get('$rootScope').$state.current.name;
                rootScope.$state.go("home");
                return $q.reject(response);
            } else if (response.status == 417) {
                return $q.reject(response);
            } else if (response.status == 401) {
                $window.location = "./#/403.html";
                return $q.reject(response);
            } else if (response.status == 500) {
                $window.location = "./login.html";
                return $q.reject(response);
            }
            return $q.reject(response);
        },
        'response': function (response) {
            return response;
        },
        'request': function (config) {
            //处理AJAX请求（否则后台IsAjaxRequest()始终false）
            config.headers['X-Requested-With'] = 'XMLHttpRequest';
            return config || $q.when(config);
        },
        'requestError': function (config) {
            return $q.reject(config);
        }
    };
}]);

/********************************************
 END: BREAKING CHANGE in AngularJS v1.3.x:
 *********************************************/

/* Setup global settings */
MetronicApp.factory('settings', ['$rootScope', '$http', '$q', function ($rootScope, $http, $q) {
    /**
     * 获取全局参数
     */
    $rootScope.getGlobalset = function (globalCode, globalVal) {
        var loginGlobalParams = mergeJson('loginItem', globalCode);
        var loginname = mergeReauestData('GlobalListController', 'queryLoginList', loginGlobalParams);
        var loginGlobalName = sendPost($http, loginname, $q);
        loginGlobalName.then(function (success) {
            var loginGlobalNameResponse = StrParesJSON(success);
            settings[globalVal] = loginGlobalNameResponse.result[0]['variableDescribe'];
            settings[loginGlobalNameResponse.result[0]['variableDescribe'] + 'Selected'] = true;
        }, function (error) {
            console.info(error);
        });
    };
    // supported languages
    var settings = {
        layout: {
            pageSidebarClosed: false, // sidebar menu state
            pageContentWhite: true, // set page content layout
            pageBodySolid: false, // solid body color state
            pageAutoScrollOnLoad: 1000, // auto scroll to top on page load,
            defaultSelected: false
        },
        assetsPath: 'resources',
        globalPath: 'resources/global',
        layoutPath: 'resources/layouts/layout'
    };

    $rootScope.settings = settings;

    return settings;
}]);

/* Setup App Main Controller */
MetronicApp.controller('AppController', ['$scope', '$rootScope', '$translate', '$window', '$state', '$http', '$q', '$interval', '$modal',
    function ($scope, $rootScope, $translate, $window, $state, $http, $q, $interval, $modal) {
        //多语言支持
        $rootScope.switching = function (lang) {
            setLanguage($translate, lang, "dashboard");
            setCookie('languageChoice', lang);
        };
        var lang = getCookie('languageChoice');
        if (lang != null) {
            setLanguage($translate, lang, "dashboard");
        }

        $scope.$on('$viewContentLoaded', function () {
            $interval(function () {
                Layout.initContent();
            }, 100, [10]);
        });
        $rootScope.isHide = false;
        $window.onhashchange = function () {
            if (!$rootScope.isHide) {
                angular.element('.modal').css({'display': 'none'});
                angular.element('.modal-backdrop').remove();
            }
        };

        $scope.pageBar = function (pageBarUrl, pageBarChildItmeSub, pageBarChildItme, pageBarItme, pageBarParneItme) {
            $scope.pageBarData = {
                'pageBarParneItme': pageBarParneItme,
                'pageBarItme': pageBarItme,
                'pageBarChildItme': pageBarChildItme,
                'pageBarChildItmeSub': pageBarChildItmeSub,
                'pageBarUrl': pageBarUrl
            };
            storage.setItem("pageBarData", JSON.stringify($scope.pageBarData));

        };
        $scope.pageBarData = JSON.parse(storage.getItem("pageBarData"));

        $scope.tokenExtlogin = function () {
            $rootScope.timeoutTip.$promise.then($rootScope.timeoutTip.hide);
            $window.location.href = "./login.html";
        };
        //当路由改变时发生
        $rootScope.$on('$stateChangeStart', function (event, toState) {
            /*
             * 判断浏览器中是否存在cookie。
             *     1如果cookie不存在，证明你还没登陆过，直接去登录,
             *     2如果cookie存在，判读session是否过期，如果session过期，提示登录超时
             *
             *     3.当路由发生改变了,从cookie中哪出这个东西，然后进行比较，看有没有这个权限
             */
            var userId = getCookie("userId");
            var restoken = JSON.parse(storage.getItem("restoken"));
            if (userId != undefined && userId != null) {
                var code = toState.data.name;
                var funcCode = getCookie("funcCode");
                if (funcCode.indexOf(code) == -1 || ($rootScope.isHide && code == "home")) {
                    $rootScope.isHide = true;
                    angular.element('.page-spinner-bar').addClass('hide');
                    $rootScope.timeoutTip = $modal({
                        scope: $scope,
                        title: "提示",
                        templateUrl: 'packages/index/tpl/tip.html',
                        content: '权限不够,将为你跳转到登录页面',
                        show: true,
                        backdrop: "static"
                    });
                    event.preventDefault();
                    $state.go("home");
                } else {
                    /**
                     * 1.如果cookie存在，但是restoken不存在。那么如果想通过标签页直接访问，需要使用把登录时间也转变下！
                     */
                    if ((restoken == undefined || restoken == null || restoken == "") && (userId != undefined && userId != null)) {
                        //需要更新用户信息
                        registerTokenCookie(userId, new Date().getTime(), $window);
                    } else if (restoken != undefined && restoken != null && restoken.token.user.id != userId) {
                        //不需要更新用户信息
                        registerTokenCookie(userId, "", $window);
                    }

                    if (restoken != undefined && restoken != null && restoken != "") {
                        var compareCode = restoken.token.func['allCloneSubFuncs'];
                        for (var i = 0; i < compareCode.length; i++) {
                            if (compareCode[i].code == "icon") {
                                $rootScope.iconName = compareCode[i].name;
                                $rootScope.iconIcon = compareCode[i].icon;
                            }
                            if (compareCode[i].code == "account") {
                                $rootScope.accountName = compareCode[i].name;
                                $rootScope.accountNameIcon = compareCode[i].icon;
                            }
                        }
                    }
                }
            } else {
                angular.element('.page-spinner-bar').addClass('hide');
                $rootScope.timeoutTip = $modal({
                    scope: $scope,
                    title: "提示",
                    templateUrl: 'packages/index/tpl/tip.html',
                    content: '会话超时，请重新登录',
                    show: true,
                    backdrop: "static"
                });
                event.preventDefault();
            }
            //判断是否是点击logo
            var sidebarmenu;
            if (location.hash == "#/home.html") {
                sidebarmenu = angular.element('.page-sidebar-menu');
                sidebarmenu.find('.nav-item.open').removeClass('open');
                sidebarmenu.find(' a > .arrow.open').removeClass('open');
                sidebarmenu.find('.sub-menu').slideUp();
                sidebarmenu.find('li.active').removeClass('active');
                sidebarmenu.find('li > a > .selected').remove();
            }
            //判断是否是个人资料和头像
            if (location.hash == "#/account.html" || location.hash == "#/edit_icon.html") {
                sidebarmenu = angular.element('.page-sidebar-menu');
                var j = 0;
                sidebarmenu.find('a').each(function (i) {
                    var userUrl = sidebarmenu.find('a').eq(i).attr("href");
                    if (userUrl == location.hash) {
                        if (sidebarmenu.find('a').eq(i).parents('.sub-menu').css("display") == "none") {
                            sidebarmenu.find('a').eq(i).parents('.sub-menu').css({"display": ""})
                        }
                        j = i;
                    }
                });
                if (j == 0) {
                    sidebarmenu = angular.element('.page-sidebar-menu');
                    sidebarmenu.find('.nav-item.open').removeClass('open');
                    sidebarmenu.find(' a > .arrow.open').removeClass('open');
                    sidebarmenu.find('.sub-menu').slideUp();
                    sidebarmenu.find('li.active').removeClass('active');
                    sidebarmenu.find('li > a > .selected').remove();

                }
            }
        });
        $rootScope.closeModel = $modal({
            scope: $scope,
            templateUrl: 'packages/index/tpl/closePrompt.html',
            show: false,
            backdrop: "static"
        });

        $scope.determine_close = function (flag) {//点击确定放弃执行隐藏弹框
            $rootScope.closeModel.$promise.then($rootScope.closeModel.hide);
            $rootScope.$broadcast('hideModel', flag);
        };
    }]
);

/***
 Layout Partials.
 By default the partials are loaded through AngularJS ng-include directive. In case they loaded in server side(e.g: PHP include function) then below partial
 initialization can be disabled and Layout.init() should be called on page load complete as explained above.
 ***/

/* Setup Layout Part - Header */
MetronicApp.controller('HeaderController', ['$scope','$http','$q', function ($scope,$http, $q) {
    $scope.user = JSON.parse(storage.getItem("restoken")).token.user;
    $scope.$on('$includeContentLoaded', function () {
        var config = {
            height: '250px',
            railVisible: false,
            railColor: '#222',
            railOpacity: 0.3,
            wheelStep: 10,
            allowPageScroll: false,
            disableFadeOut: false
        };
        $('#header_notification_bar').find('.scroller').slimScroll(config);
        $('#header_task_bar').find('.scroller').slimScroll(config);

        Layout.initHeader(); // init header
    });

}]);

/* Setup Layout Part - Sidebar */
MetronicApp.controller('SidebarController', ['$scope', '$http', '$rootScope', '$location', '$q', '$window', function ($scope, $http, $rootScope, $location, $q, $window) {
    $scope.tokenExtlogin = function () {
        $rootScope.timeoutTip.$promise.then($rootScope.timeoutTip.hide);
        $window.location.href = "./login.html";
    };
    var restoken = JSON.parse(storage.getItem("restoken"));
    if (restoken == undefined || restoken == null || restoken == "") {
        $scope.$on('$includeContentLoaded', function () {
            setTimeout(function () {
                Layout.initSidebar();
            }, 500);
        });
        var token = mergeReauestData('LoginController', 'registerOrGetCurrentToken', {});
        var tokenResult = sendPost($http, token, $q);
        tokenResult.then(function (res) {
            $rootScope.token = JSON.parse(res).token;
        });
    } else {
        $rootScope.token = restoken.token;
        $scope.$on('$includeContentLoaded', function () {
            setTimeout(function () {
                Layout.initSidebar();
            }, 100);
        });
    }

    $.ajaxSettings.async = false;
    $.getJSON('packages/sidebarfile.json', function (data) {
        var notEmpty = function (target) {
            return target != undefined && target != null && target != "";
        };
        var treeSource = {};
        $.each(data, function (i, val) {
            if (notEmpty(val) && notEmpty(val.name) && notEmpty(val.url)) {
                $.getJSON(val.url, function (childrenData) {
                    $.each(childrenData, function (i, val) {
                        val["url"] = "#/" + i.replace(new RegExp(/(\.)/g), '-') + ".html"
                    });
                    $.extend(treeSource, childrenData);
                    $scope.sidebarConfig = treeSource;
                });
            }
        });
    });
    /*
     * 链接跳转
     */
    $scope.skip = function (url) {
        var urlLength = url.length;
        if (url.substr(0, 2) == "//") {
            $window.open("http://" + url, "_blank");
        } else if (url.substr(0, 1) == "/" || url.substr(0, 7) == "http://" || url.substr(0, 8) == "https://") {
            $window.open(url, "_blank");
        } else if (url.substring(urlLength - 3, urlLength) == "com" || url.substring(urlLength - 2, urlLength) == "cn") {
            $window.open("http://" + url, "_blank");
        } else {
            $window.open(url, "_self");
        }
    }
}]);

/* Setup Layout Part - Footer */
MetronicApp.controller('FooterController', ['$scope', function ($scope) {
    $scope.$on('$includeContentLoaded', function () {
        Layout.initFooter(); // init footer
    });
}]);

/* Setup Rounting For All Pages */
MetronicApp.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
    var userId = getCookie("userId");

    if (userId != undefined && userId != null) {
        $.ajaxSettings.async = false;

        var restoken = JSON.parse(storage.getItem("restoken"));

        /**
         * 1.如果cookie中存在值，但是session回话中没有存在缓存的值。
         *    这种场景是当用通过tab页面直接访问页面信息的时候，需要进行login时间的更换
         * 2.如果session中存在token的值，但是用户的cookie值发生了改变，需要用第二个登录用户的token值代替第一个用户token值
         *    这种场景用来针对一个浏览器多个用于登录
         */
        function configState(func) {
            var notEmpty = function (target) {
                return target != undefined && target != null && target != "";
            };
            $.getJSON('packages/sidebarfile.json', function (data) {
                $.each(data, function (i, val) {
                    // 模块分支（第一层）
                    if (notEmpty(val) && notEmpty(val.url)) {
                        $.getJSON(val.url + "?" + Math.random(), function (data) {
                            $.each(data, function (routeKey, routeData) {
                                // 基础路由信息
                                var state = {};
                                state.url = "/" + routeKey.replace(new RegExp(/(\.)/g), '-') + ".html";
                                state.data = {};
                                state.data.name = routeKey;
                                if (notEmpty(routeData['pageTitle'])) {
                                    state.data.pageTitle = routeData['pageTitle'];
                                }
                                if (notEmpty(routeData['templateUrl'])) {
                                    state.templateUrl = routeData.templateUrl + "?" + Math.random();
                                }
                                // 延迟加载文件
                                if (notEmpty(routeData.files)) {
                                    state.resolve = {
                                        deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                                            return $ocLazyLoad.load({
                                                name: 'MetronicApp',
                                                insertBefore: '#ng_load_plugins_before',
                                                cache: false,
                                                files: routeData.files
                                            });
                                        }]
                                    };
                                }
                                // 控制器
                                if (notEmpty(routeData.controller)) {
                                    state.controller = routeData.controller;
                                }
                                $stateProvider.state(routeKey.replace(new RegExp(/(\.)/g), '-'), state);
                            });
                        });
                    }
                })
            });
        }
    } else {
        window.location.href = "./login.html";
    }

    if ((restoken == undefined || restoken == null || restoken == "") && (userId != undefined && userId != null)) {
        $.post("mvc/dispatch", {
                controller: "LoginController",
                method: "registerTokenCookie",
                userId: userId,
                dateTime: new Date().getTime()
            },
            function (data) {
                storage.setItem('restoken', JSON.stringify(data));
                storageLocal.setItem('loginTime', data['logintime']);
                configState(data.token.func['allCloneSubFuncs']);
            })
    } else if (restoken != undefined && restoken != null && restoken.token.user.id != userId) {
        //不需要更新用户信息
        $.post("mvc/dispatch", {
                controller: "LoginController",
                method: "registerTokenCookie",
                userId: userId,
                dateTime: ""
            },
            function (data) {
                storage.setItem('restoken', JSON.stringify(data));
                configState(data.token.func['allCloneSubFuncs']);
            })
    } else {
        configState(restoken.token.func['allCloneSubFuncs']);
    }


    // Redirect any unmatched url
    $urlRouterProvider.otherwise("/home.html");
}]);

/* Init global settings and run the app */
MetronicApp.run(["$rootScope", "settings", "$state", function ($rootScope, settings, $state) {
    $rootScope.$state = $state; // state to be accessed from view
    $rootScope.$settings = settings; // state to be accessed from view
}]);

MetronicApp.controller('exitCtrl', ['$scope', '$http', '$q', '$window', '$modal', function ($scope, $http, $q, $window, $modal) {
    // 点击删除按钮触发事件
    $scope.exitSysModal = function () {
        promptRxit.$promise.then(promptRxit.show);
    };
    var promptRxit = $modal({
        scope: $scope,
        templateUrl: 'packages/index/tpl/exit_modal.html',
        show: false,
        backdrop: "static"
    });

    $scope.confirmExitSys = function () {
        //清楚相关用户信息
        var requestLogoutController = mergeReauestData('LoginController', 'logout', "");
        var responseLogoutResult = sendPost($http, requestLogoutController, $q);
        responseLogoutResult.then(function () {
            promptRxit.$promise.then(promptRxit.hide);
            $window.location.href = "./login.html";
        })
    }
}]);

//监听窗口变化的方法，用于解决当拖动过弹框后，再缩小窗口，恢复坐标
function tellAngular() {
    var domElt = document.getElementsByClassName('t-modal-portlet');
    var scope = angular.element(domElt).scope();
    var attrStyle = angular.element(".t-modal-portlet .modal-content").attr("style");
    if (scope != undefined && attrStyle != undefined) {
        scope.$apply(function () {
            angular.element(".t-modal-portlet .t-modal-drags-fixed").removeAttr("style");
        });
    }
}

//dom加载的时候调用 tellAngular
document.addEventListener("DOMContentLoaded", tellAngular, false);

//resize被触发是调用 tellAngular
window.onresize = tellAngular;
