var MetronicApp = angular.module("MetronicApp", ["ui.router", "ui.bootstrap",
    'mgcrea.ngStrap' // 弹框插件
]);
var storage = window.sessionStorage;
var storageLocal = window.localStorage;

// 登录
MetronicApp.controller('signInFormCtrl', function ($scope, $http, $window, $q, $interval, $rootScope) {
    $scope.submitted = false;
    $scope.codeValidate = false;
    $scope.loginGlobalItem = '';
    $scope.loginGlobalList = function () {
        var loginGlobalParams = mergeJson('loginItem', 10123);
        var loginname = mergeReauestData(
            'GlobalListController', 'queryLoginList',
            loginGlobalParams);
        var loginGlobalName = sendPost($http, loginname, $q);
        loginGlobalName.then(function (success) {

            var loginGlobalNameResponse = StrParesJSON(success);
            $scope.loginGlobalItem = loginGlobalNameResponse.result[0].variableDescribe;
        }, function (error) {
            console.info(error);
        });

    };
    $scope.loginGlobalList();

    // "记住我"-->标识
    var rememberCheckbox = document.getElementById("remember-me");

    // 获取账号和密码
    var rememberMe = getCookie('rememberMe');
    if ((rememberMe != null) && (rememberMe != "")) {
        var account = rememberMe.split(",")[0];
        var pwd = rememberMe.split(",")[1];
        $scope.user = {
            "account": account,
            "password": pwd
        };
        rememberCheckbox.checked = true;
    }
    // 验证码输入框获取焦点隐藏错误信息
    $scope.hideErrorInfo = function () {
        $scope.checkcodeIsCorrect = false;
        $scope.checkcodeIsNull = false;
    };
    // 账号和密码获取焦点隐藏错误信息
    $scope.hideZMError = function () {
        $scope.singOnForm = false;
        var accountValue = document.getElementById("accountId").value;
        var pswValue = document.getElementById("pswId").value;
        if (accountValue.length != 0 && pswValue.length != 0) {
            $scope.loginerror = false;
        }
    };
    // 验证账号是必填项
    $scope.verifyAccountRequired = function () {
        var accountValue = document.getElementById("accountId").value;
        var pswValue = document.getElementById("pswId").value;

        if (accountValue.length == 0) {
            $scope.loginerror = true;
            $scope.singOnAccount = true;
        } else {
            $scope.singOnAccount = false;
            $scope.loginerror = false;
        }
        if (accountValue.length != 0 && pswValue.length != 0) {
            $scope.loginerror = false;
        }
        if ($scope.singOnForm == true) {
            $scope.loginerror = true;
        }
    };
    // 验证密码是必填项
    $scope.verifyPswRequired = function () {
        var pswValue = document.getElementById("pswId").value;
        if (pswValue.length == 0) {
            $scope.loginerror = true;
            $scope.singOnPsw = true;
        } else {
            $scope.singOnPsw = false;
        }
        var accountValue = document.getElementById("accountId").value;
        if (accountValue.length != 0 && pswValue.length != 0) {
            $scope.loginerror = false;
        }
        if ($scope.singOnForm == true) {
            $scope.loginerror = true;
        }
    };
    $scope.isVerifyImg = false;
    $scope.singOnForm = false;
    // 登录提交
    $scope.submitLoginForm = function () {
        // 是否填写用户名和密码
        $scope.verifyAccountRequired();
        $scope.verifyPswRequired();
        //提交表单数据
        $scope.loginCommit();
    };
    //登录提交
    $scope.loginCommit = function () {
        // 验证用户名和密码
        var accountParams = mergeJson('account', $scope.user.account);
        var passwordParams = mergeJson('password', $scope.user.password);
        var loginParams = {};
        $.extend(loginParams, accountParams, passwordParams);
        var loginData = mergeReauestData('LoginController', 'checkAccountSys', loginParams);
        var loginResult = sendPost($http, loginData, $q);
        loginResult.then(function (success) {
            var loginResult = JSON.parse(success);
            storage.setItem('restoken', JSON.stringify(loginResult));
            var loginTime = loginResult.logintime;
            $rootScope.loginTime = loginTime;
            var userinfo = loginResult.token.user;
            // 登录的时候存放下
            setCookie("userId", userinfo.id);
            var arrayFuncCode = [];
            for (var i = 0; i < loginResult.token.func.allCloneSubFuncs.length; i++) {
                arrayFuncCode[i] = loginResult.token.func.allCloneSubFuncs[i].code;
            }
            var strFuncCode = arrayFuncCode.join(",");
            var addStrFuncCode = strFuncCode + ",home";
            setCookie("funcCode", addStrFuncCode);
            if (loginTime == null) {
                storageLocal.setItem('loginTime', "您好,第一次登录,请愉快使用!");
            } else {
                storageLocal.setItem('loginTime', loginTime);
            }
            if (rememberCheckbox.checked) {
                setCookie('rememberMe', $scope.user.account + "," + $scope.user.password);
            } else {
                setCookie('rememberMe', "");
            }
            $window.location.href = "index.html#/home.html";
        }, function (error) {
        });
    };

    // 键盘登录
    $scope.loginKeyUp = function ($event) {
        if ($event.keyCode == 13) {
            $scope.submitLoginForm();
        }
    };
    // 返回登录隐藏错误信息
    $scope.$on('to-child-login', function () {
        $scope.singOnAccount = false;
        $scope.loginerror = false;
        $scope.singOnForm = false;
        $scope.singOnPsw = false;
        $scope.checkcodeIsNull = false;
        $scope.checkcodeIsCorrect = false;
    });
});
