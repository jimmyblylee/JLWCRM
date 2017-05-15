var MetronicApp = angular.module("MetronicApp", [
    "ui.router",
    "ui.bootstrap" ,
    "pascalprecht.translate",// 国际化
    'mgcrea.ngStrap' // 弹框插件
]);
var storage = window.sessionStorage;
MetronicApp.config(['$translateProvider',function($translateProvider){
    $translateProvider.preferredLanguage('cn/loginCN');
    $translateProvider.useStaticFilesLoader({
        prefix:'/crm/packages/pages/i18n/',
        suffix:'.json'
    });
}]);

// 登录

MetronicApp.controller('signInFormCtrl', function($scope,$http,$window,$q,$interval){
    $scope.submitted = false;
    $scope.codeValidate=false;
  $scope.loginGlobalItem='';
  $scope.loginGlobalList = function() {
      var loginGlobalParams = mergeJson('loginItem', 10123);
      var loginname = mergeReauestData('GlobalListController',
            'queryLoginList', loginGlobalParams);
      var loginGlobalName = sendPost($http, loginname, $q);
      loginGlobalName.then(function(success) {
         var loginGlobalNameResponse = StrParesJSON(success);
         $scope.loginGlobalItem= loginGlobalNameResponse.result[0].variableDescribe;
      }, function(error) {
        console.info(error);
      });

  }
    $scope.loginGlobalList();
    // "记住我"-->标识
    var rememberCheckbox=document.getElementById("remember-me");
    // 获取账号和密码
    var rememberMe=getCookie('rememberMe');
    if((rememberMe!=null)&&(rememberMe!="")){
        var account=rememberMe.split(",")[0];
        var pwd=rememberMe.split(",")[1];
        $scope.user={"account":account,"password":pwd};
        rememberCheckbox.checked=true;
    }
    // 点击更换验证码
    $scope.changeCode=function(){
        var imgCode=document.getElementById("imgCode");
        var rand=Math.floor(Math.random()*10);
        imgCode.src='mvc/dispatch?controller=LoginController&method=getVerifyImg&'+rand;
    }

    //验证码输入框获取焦点隐藏错误信息
    $scope.hideErrorInfo = function (){
    	  $scope.checkcodeIsCorrect=false;
    	  $scope.checkcodeIsNull = false;
    }
    //账号和密码获取焦点隐藏错误信息
    $scope.hideZMError = function(){
    	$scope.singOnForm = false;
    	var accountValue = document.getElementById("accountId").value;
    	var pswValue = document.getElementById("pswId").value;
    	if(accountValue.length !=0 && pswValue.length !=0){
    		$scope.loginerror = false;
    	}
    }
    //验证账号是必填项
    $scope.verifyAccountRequired = function(){
    	var accountValue = document.getElementById("accountId").value;
    	var pswValue = document.getElementById("pswId").value;

    	if(accountValue.length == 0){
    		$scope.loginerror = true;
    		$scope.singOnAccount =true;
    	}else{
    		$scope.singOnAccount =false;
            $scope.loginerror = false;
    	}
    	if(accountValue.length !=0 && pswValue.length !=0){
    		$scope.loginerror = false;
    	}
    }
    //验证密码是必填项
    $scope.verifyPswRequired = function(){
    	var pswValue = document.getElementById("pswId").value;
    	if(pswValue.length == 0){
    		$scope.loginerror = true;
    		$scope.singOnPsw =true;
    	}else{
    		$scope.singOnPsw =false;
    	}
    	var accountValue = document.getElementById("accountId").value;
    	if(accountValue.length !=0 && pswValue.length !=0){
    		$scope.loginerror = false;
    	}
    }
    //验证验证码是否为空
    $scope.verifyRequired = function(){
    	var checkcode = document.getElementById("checkcodeId").value;
    	if(checkcode.length == 0){
    		  $scope.checkcodeIsNull = true;
              $scope.checkcodeIsCorrect = false;
    	}else{
    		  $scope.checkcodeIsNull = false;
    	}
        if(checkcode.length >= 4){
             var checkCode=$scope.checkCode.toLowerCase();
             var codeParam = mergeJson('rand',checkCode);
             var codeData = mergeReauestData('LoginController','checkVerifyImg',codeParam);
             var codeResult = sendPost($http,codeData,$q);
             codeResult.then(function(success){
             var result=JSON.parse(success).valid;
                 //验证成功
                 if(result){
                      $scope.isVerifyImg = true;
                      $scope.checkcodeIsCorrect=false;
                 }else{
                    //验证码验证失败
                    $scope.checkcodeIsCorrect=true;
                    $scope.isVerifyImg = false;
                 }
             },function(error){
                 $scope.checkcodeIsCorrect=true;
             });
        }

    }
    $scope.isVerifyImg = false;
    //验证验证码是否正确
     $scope.checkVerVerify = function() {
         //前端验证验证码、密码、账户的是否是必填项
        if($scope.checkcodeIsNull == false && $scope.singOnAccount == false && $scope.singOnPsw == false){
             //后台验证验证码是否输入正确
             var checkCode=$scope.checkCode.toLowerCase();
             var codeParam = mergeJson('rand',checkCode);
             var codeData = mergeReauestData('LoginController','checkVerifyImg',codeParam);
             var codeResult = sendPost($http,codeData,$q);
             codeResult.then(function(success){
             var result=JSON.parse(success).valid;
                 //验证成功
                 if(result){
                      $scope.isVerifyImg = true;
                      $scope.checkcodeIsCorrect=false;
                 }else{
                    //验证码验证失败
                    $scope.checkcodeIsCorrect=true;
                    $scope.isVerifyImg = false;
                 }
             },function(error){
                 $scope.checkcodeIsCorrect=true;
             });
          }else{
             // 请填写验证码
             $scope.verifyRequired();
             //是否填写用户名和密码
             $scope.verifyAccountRequired();
             $scope.verifyPswRequired();
          }

    }

    //登录提交
    $scope.submitForm = function() {

    	//是否填写用户名和密码
        $scope.verifyAccountRequired();
        $scope.verifyPswRequired();

        //验证用户名
        var accountParams = mergeJson('account',$scope.user.account);
        var passwordParams = mergeJson('password',$scope.user.password);
        var loginParams = {};
        $.extend(loginParams,accountParams,passwordParams);
        var loginData = mergeReauestData('LoginController','checkAccountSys',loginParams);
        var loginResult = sendPost($http,loginData,$q);
        loginResult.then(function(success){
            var userid = JSON.parse(success).token.user.id;
            var loginTime =  JSON.parse(success).logintime;
            if(loginTime == null){
            	 storage.setItem('loginTime',"您好,第一次登录,请愉快使用!");
            }else{
            	 storage.setItem('loginTime',loginTime);
            }
            setCookie('loginTime',loginTime);
            storage.getItem('loginTime');
            var userid = JSON.parse(success).token.user.id;
            if(rememberCheckbox.checked){
                setCookie('rememberMe',$scope.user.account+","+$scope.user.password);
            }
            else{
                setCookie('rememberMe',"");
            }
            var userName = $scope.user.account;
            // getTime();
            $window.location.href = "./#/home.html";

        },function(error){
            //更换验证码信息
           errorMsg = JSON.parse(error);
           if (errorMsg.success==false) {
            $scope.checkcodeIsCorrect=false;
            $scope.changeCode();
            $scope.loginerror = true;
            $scope.singOnForm = true;
            };
        });
	}
    //键盘登录
	$scope.keyup = function ($event) {
	     if($event.keyCode==13){
	        $scope.submitForm();
	      }
	};
	//返回登录隐藏错误信息
	$scope.$on('to-child-login', function(event,data) {
		$scope.singOnAccount =false;
        $scope.loginerror = false;
        $scope.singOnForm = false;
        $scope.singOnPsw =false;
        $scope.checkcodeIsNull =false;
        $scope.checkcodeIsCorrect=false;
    });
   $interval(function() {
        $scope.changeCode();
   },1200000);
});

// 忘记密码
MetronicApp.controller('forgetCtrl',function($scope,$http,$q, $modal){
	$scope.forgeter={'email':''}
    $scope.submitted=false;
    $scope.submitForm = function() {
        if ($scope.forgetForm.$valid) {
            var emailJson = mergeJson('emailForgetPwd',$scope.forgeter.email);
            var emailData = mergeReauestData('ForgetPasswordController','sendEmail',emailJson);
            var emailResult = sendPost($http,emailData,$q);
            emailResult.then(function(success){
                var result=JSON.parse(success).result;
                if(result){
                    var pwdPrompt = $modal({
                        scope : $scope,
                        title:"提示",
                        templateUrl : '/crm/loginPrompt.html',
                        content : '密码发送成功',
                        show : true
                    });
                }else{
                    var pwdPrompt = $modal({
                        scope : $scope,
                        title:"提示",
                        templateUrl : '/crm/loginPrompt.html',
                        content : '密码发送失败',
                        show : true
                    });
                }
            },function(error){
            	 $scope.forget = true;
            });
        } else {
            $scope.forgetForm.submitted = true;
        }
    }
    $scope.hideError = function(){
    	 $scope.forget = false;
    }
    $scope.emaiBure = function(){
         if ($scope.forgetForm.$valid) {
        if ($scope.forget==false) {
            var emailJson = mergeJson('emailForgetPwd',$scope.forgeter.email);
            var emailData = mergeReauestData('ForgetPasswordController','sendEmail',emailJson);
            var emailResult = sendPost($http,emailData,$q);
            emailResult.then(function(success){
            },function(error){
                 $scope.forget = true;
            });

        };
        } else {
            $scope.forgetForm.submitted = true;
        }

    }
     $scope.emaiChang = function(){

        $scope.forget = false;

     }
	//进入忘记登录页面隐藏错误信息
	$scope.$on('to-child-forget', function(event,data) {
        $scope.forget = false;
        $scope.forgetForm.submitted = false;
    });
})

// 注册
MetronicApp.controller('registerCtrl', ['$scope','$http','$q','$window','$modal',function($scope,$http,$q,$window,$modal) {
    $scope.submitted = false;
    $scope.isSame=false;
    $scope.tncShow=true;
    $scope.isSub=false;
    $scope.isChecked=false;

    // 发送验证码
    $scope.sendVerifyCode=function(){
    	$scope.emailChangeEvent();
    	var emailValue = document.getElementById("createrEmail").value;
    	var emailNoLength = checkEmail(emailValue);
    	//1.前台验证邮件格式是否正确
        if($scope.emailFormat == false && $scope.emailRequired == false  &&  emailNoLength == false){
        	//2.后台验证邮件是否已经被注册了
    		var emailJson = mergeJson('emailRegister',emailValue);
            var emailData = mergeReauestData('RegisterController','sendCode',emailJson);
            var emailResult = sendPost($http,emailData,$q);
            emailResult.then(function(success){
            	if(JSON.parse(success).result == true){
            		//2.1  验证邮件没有被注册
            		$scope.emailErrMsg = false;
                         if(result){
                             var codePrompt = $modal({
                                 scope : $scope,
                                 title:"提示",
                                 templateUrl : 'loginPrompt.html',
                                 content : '验证码发送成功',
                                 show : true
                             });
                         }else{
                             var codePrompt = $modal({
                                 scope : $scope,
                                 title:"提示",
                                 templateUrl : 'loginPrompt.html',
                                 content : '验证码发送失败',
                                 show : true
                             });
                         }


            	}else{
            		//2.2  验证邮件被注册
            		$scope.emailErrMsg = true;
            	}
            })
        }else{
        	if($scope.emailRequired == false){
        		$scope.emailFormat = checkEmail(emailValue);
        	}
        }
    }
    //邮件改变事件
	$scope.emailChangeEvent = function(){
		var emailValue = document.getElementById("createrEmail").value;
		if(emailValue.length == 0){
			$scope.emailRequired = true;
			$scope.emailFormat = false;
		}else{
			$scope.emailRequired = false;
			$scope.emailFormat = changeEmail(emailValue);
		}
	}
     //邮件失去焦点事件
    $scope.emailBlurEvent = function(){
        var emailValue = document.getElementById("createrEmail").value;
        if(emailValue.length == 0){
            $scope.emailRequired = true;
            $scope.emailFormat = false;
        }else{
            $scope.emailRequired = false;
            $scope.emailFormat = checkEmail(emailValue);
        }
    }
      //邮件获得焦点事件
    $scope.hideRegisterEmailtip = function(){
            $scope.emailRequired = false;
            $scope.emailFormat = false;
            $scope.emailRequired = false;

    }
	//获取焦点隐藏错误信息
	$scope.hidRregisterVerifycodeErrorInfo =function (){
		 $scope.registerVerifycodeRequired=false;
		 $scope.registerFormVerifycodeError=false;
	}
    //失去焦点验证验证码是否正确
    $scope.validateCode=function(){
        if($scope.verifyCode!=null){
            var codeParam = mergeJson('verifyCode',$scope.verifyCode);
            var codeData = mergeReauestData('RegisterController','checkVerifyCode',codeParam);
            var codeResult = sendPost($http,codeData,$q);
            codeResult.then(function(success){
                var result=JSON.parse(success).valid;
                if(result)//验证成功
                {
                    $scope.registerFormVerifycodeError=false;
                }else// 验证失败
                {
                    $scope.registerFormVerifycodeError=true;
                }
            });
        }else{
        	//验证码是必填的
            $scope.registerVerifycodeRequired=true;
        }
    }
    //检验密码是否相同
    $scope.checkPassword=function(){
    	if ($scope.creater.password != undefined && $scope.creater.password != null &&
    			$scope.creater.rpassword != undefined && $scope.creater.rpassword != null) {
			if ($scope.creater.rpassword == $scope.creater.password) {

				$scope.pswUnify = false;
			} else {
				$scope.pswUnify = true;
			}
		}
    }
    //如果用户名重复了。当在此输入用户名的时候
    $scope.hideErrorRegMsg = function(){
    	$scope.regMsg =  false;
    }
    $scope.goToLogin = function(){
    	 $window.location.href = "./login.html";
    }
    // 提交注册内容
    $scope.submitForm = function() {

    	//1.验证码不可以是空的、必须同意协议

    	var registerCode = document.getElementById("reisterCodeId").value;

    	if(registerCode.length == 0){
    		$scope.registerVerifycodeRequired = true;
    	}else{
    		$scope.registerVerifycodeRequired = false;
    	}

        $scope.registerForm.isSub=true;
        if(!$scope.isChecked){
            $scope.registerForm.tncShow=false;

        }

        if ($scope.registerForm.$valid && $scope.registerForm.tncShow &&
        		$scope.registerFormVerifycodeError ==false && $scope.registerVerifycodeRequired == false && $scope.regMsg == false) {

            var createrJson = mergeJson('creatUser',$scope.creater);
            var createrData = mergeReauestData('RegisterController','register',createrJson);
            var createrResult = sendPost($http,createrData,$q);
            createrResult.then(function(value){
                // $window.location.href = "./index.html";

             $scope.registerReset();
           	 var loginPrompt = $modal({
                 scope : $scope,
                 title:"系统提示信息",
                 templateUrl : 'loginPrompt.html',
                 content : "用户注册成功,前往登录页面!",
                 show : true
             });
            },function(error){
            	$scope.regErrorMsg = JSON.parse(error).errMsg;
				$scope.regMsg =  true;
				//$scope.regAccout = true;
            });
        } else {
            $scope.registerForm.submitted = true;
        }
    };

    //重置
    $scope.registerReset=function(){
        document.getElementById("register-form").reset();
        $scope.registerForm.submitted = false;
        $scope.registerForm.codeValidate =false;
        $scope.registerForm.tncShow = true;
        $scope.registerVerifycodeRequired=false;
        $scope.registerFormVerifycodeError=false;
        $scope.regMsg=false;
        $scope.regMsg=false;
        $scope.emailFormat=false;
        $scope.pswUnify=false;
        $scope.emailRequired=false;
    };

    $scope.tncChange=function(){
        $scope.isChecked=!$scope.isChecked;
        if( $scope.registerForm.isSub){
            $scope.registerForm.tncShow=!$scope.registerForm.tncShow;
        }else{
            $scope.registerForm.tncShow=true;
        }
    };
    //监控清空注册信息
    $scope.$on('to-child-reset', function(event,data) {
        $scope.registerReset();
    });

    //隐私条框和服务协议弹框
	var serviceOrRivacy = $modal({scope : $scope,templateUrl : './goToLogin.html',
		show : false,controller : 'serviceAgreementCtl',backdrop:"static",placement : "center"});

	$scope.serviceOrRivacy = function(serviceOrPrivacy){

		serviceOrRivacy.$promise.then(serviceOrRivacy.show);

		$scope.serviceOrPrivacy = serviceOrPrivacy;
	};

}]);
//服务条框和隐私协议弹框
MetronicApp.controller('serviceAgreementCtl', function($scope,$http,$window,$q) {

    $scope.privacyAndService = function() {

    	if($scope.$parent.serviceOrPrivacy == "serviceAgreement"){

    		var privacyAndServiceParams = mergeJson('loginItem', 10124);

    	}else if($scope.$parent.serviceOrPrivacy == "privacy"){

    		var privacyAndServiceParams = mergeJson('loginItem', 10125);
    	}

        var privacyAndServiceData = mergeReauestData('GlobalListController',
              'queryLoginList', privacyAndServiceParams);

        var privacyAndServiceRequest = sendPost($http, privacyAndServiceData, $q);

        privacyAndServiceRequest.then(function(success) {
           var privacyAndServiceResponse = StrParesJSON(success);

           console.log(privacyAndServiceResponse);

           $scope.privacyAndServiceGlobalItem= privacyAndServiceResponse.result[0].variableDescribe;

        }, function(error) {
          console.info(error);
        });

    }
      $scope.privacyAndService();

})
// 登陆和注册的切换
MetronicApp.controller('signInAndUpCtrl', function($scope,$translate) {
    // 多语言切换
    var lang=getCookie('languageChoice');
    if(lang!=null){
        setLanguage($translate,lang,"login");
    }

    $scope.signInIsShow=true;
    $scope.registerIsShow=false;
    $scope.forgetIsShow=false;
    $scope.forgetPassword=function(){
        $scope.signInIsShow=false;
        $scope.forgetIsShow=true;
        $scope.$broadcast('to-child-forget', 'child');
    };
    //忘记密码返回登录   --》隐藏上一次的错误信息
    $scope.forgetBack=function(){
        $scope.forgetIsShow=false;
        $scope.signInIsShow=true;
        $scope.$broadcast('to-child-login', 'child');
    };
    $scope.createAnAccount=function(){
        $scope.signInIsShow=false;
        $scope.registerIsShow=true;
        //清空注册信息
        $scope.$broadcast('to-child-reset', 'child');
    };
    //注册过之后返回登陆
    $scope.registerBack=function(){
    	$scope.$broadcast('to-child-login', 'child');
        $scope.signInIsShow=true;
        $scope.registerIsShow=false;
    }

});


