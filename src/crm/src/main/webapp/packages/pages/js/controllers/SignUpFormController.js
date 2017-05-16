var MetronicApp = angular.module("MetronicApp", [ "ui.router", "ui.bootstrap",
		"pascalprecht.translate",// 国际化
		'mgcrea.ngStrap' // 弹框插件
]);
var storage = window.sessionStorage;
var storageLocal = window.localStorage;
MetronicApp.config([ '$translateProvider', function($translateProvider) {
	$translateProvider.preferredLanguage('cn/loginCN');
	$translateProvider.useStaticFilesLoader({
		prefix : '/crm/packages/pages/i18n/',
		suffix : '.json'
	});
} ]);

// 登录
MetronicApp.controller('signInFormCtrl',function($scope, $http, $window, $q, $interval, $rootScope) {
					/**
					 * 给验证码赋值
					 */
					$scope.getVerifyCode = function() {
						var loginGetVerifyCode = mergeReauestData(
								'LoginController', 'getVerifyImg',"");
						var verifyCode = sendPost($http, loginGetVerifyCode, $q);
						verifyCode.then(function(success) {
							var responseResult = StrParesJSON(success);
							$scope.verifyNumber = responseResult.result.number;
							var values =responseResult.result.image;
					        var str = "<span><img  height = \"43px\" title=\"点击图片刷新\" alt=\"点击图片刷新\" src=\"data:image/gif;base64," + values + "\"/></span><br />" ;
							document.getElementById("iconVerify").innerHTML = str;

						}, function(error) {
							console.info(error);
						});
					}
					$scope.getVerifyCode();
					$scope.submitted = false;
					$scope.codeValidate = false;
					$scope.loginGlobalItem = '';
					$scope.loginGlobalList = function() {
						var loginGlobalParams = mergeJson('loginItem', 10123);
						var loginname = mergeReauestData(
								'GlobalListController', 'queryLoginList',
								loginGlobalParams);
						var loginGlobalName = sendPost($http, loginname, $q);
						loginGlobalName.then(function(success) {

								var loginGlobalNameResponse = StrParesJSON(success);
								$scope.loginGlobalItem = loginGlobalNameResponse.result[0].variableDescribe;
								/*
								 * 放在这里为了让加载验证码的时候更快点
								 */
								$scope.noFollowVerifyCode();
						}, function(error) {
							console.info(error);
						});

					}
					$scope.loginGlobalList();

					/**
					 * 是否需要验证码
					 */
					$scope.noFollowVerifyCode = function() {
						var loginGbalId = mergeJson('variableName', 10126);
						var loginIsVerifyCode = mergeReauestData('GlobalListController', 'getGlobalById',loginGbalId);

						var loginGlobalName = sendPost($http, loginIsVerifyCode, $q);
						loginGlobalName.then(function(success) {
							var loginGlobalNameResponse = StrParesJSON(success);
							$scope.globalIsEnabled = loginGlobalNameResponse.result.isEnabled;
						}, function(error) {
							console.info(error);
						});

					}

					// 点击更换验证码
					$scope.changeCode = function() {
						var imgCode = document.getElementById("imgCode");
						var dateTime = new Date();
						imgCode.src = 'mvc/dispatch?controller=LoginController&method=getVerifyImg&'+ dateTime.getTime();
					}
					// "记住我"-->标识
					var rememberCheckbox = document.getElementById("remember-me");

					// 获取账号和密码
					var rememberMe = getCookie('rememberMe');
					if ((rememberMe != null) && (rememberMe != "")) {
						var account = rememberMe.split(",")[0];
						var pwd = rememberMe.split(",")[1];
						$scope.user = {
							"account" : account,
							"password" : pwd
						};
						rememberCheckbox.checked = true;
					}
					// 验证码输入框获取焦点隐藏错误信息
					$scope.hideErrorInfo = function() {
						$scope.checkcodeIsCorrect = false;
						$scope.checkcodeIsNull = false;
					}
					// 账号和密码获取焦点隐藏错误信息
					$scope.hideZMError = function() {
						$scope.singOnForm = false;
						var accountValue = document.getElementById("accountId").value;
						var pswValue = document.getElementById("pswId").value;
						if (accountValue.length != 0 && pswValue.length != 0) {
							$scope.loginerror = false;
						}
					}
					// 验证账号是必填项
					$scope.verifyAccountRequired = function() {
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
					}
					// 验证密码是必填项
					$scope.verifyPswRequired = function() {
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
					}
					// 验证验证码是否为空
					$scope.verifyRequired = function() {
						var checkcode = document.getElementById("checkcodeId").value;
						if (checkcode.length == 0) {
							$scope.checkcodeIsNull = true;
							$scope.checkcodeIsCorrect = false;
						} else {
							$scope.checkcodeIsNull = false;
						}
					}
					$scope.isVerifyImg = false;
					$scope.singOnForm = false;
					// 验证验证码是否正确
					$scope.checkVerVerify = function(){
						var checkcode = document.getElementById("checkcodeId").value;

						// 请求后台验证验证码
						if (checkcode.length >= 4) {
							if (checkcode.toLowerCase() == $scope.verifyNumber){
								$scope.isVerifyImg = true;
								$scope.checkcodeIsCorrect = false;
							} else {
								// 验证码验证失败
								$scope.checkcodeIsCorrect = true;
								$scope.isVerifyImg = false;
							}
						}
					}
					// 登录提交
					$scope.submitLoginForm = function() {
						// 是否填写用户名和密码
						$scope.verifyAccountRequired();
						$scope.verifyPswRequired();
						if($scope.globalIsEnabled == true || $scope.globalIsEnabled == undefined){
							// 验证码是必填的
							$scope.verifyRequired();
							if ($scope.checkcodeIsNull == false&& $scope.singOnForm == false&& $scope.singOnPsw == false&& $scope.singOnAccount == false
									&& $scope.checkcodeIsCorrect == false) {
								// 验证验证码的正确性
								var checkFormCode = $scope.checkCode.toLowerCase();
								if (checkFormCode == $scope.verifyNumber) {
									$scope.isVerifyImg = true;
									$scope.checkcodeIsCorrect = false;
									//提交表单数据
									$scope.loginCommit();
								} else {
									// 验证码验证失败
									$scope.checkcodeIsCorrect = true;
									$scope.isVerifyImg = false;
								}
							}
						}else{
							//提交表单数据
							$scope.loginCommit();
						}
					}
					//登录提交
					$scope.loginCommit = function(){
						// 验证用户名和密码
						var accountParams = mergeJson('account',$scope.user.account);
						var passwordParams = mergeJson('password',$scope.user.password);
						var loginParams = {};
						$.extend(loginParams,accountParams,passwordParams);
						var loginData = mergeReauestData('LoginController','checkAccountSys',loginParams);
						var loginResult = sendPost($http,loginData, $q);
						loginResult.then(function(success) {
											var loginResult = JSON.parse(success);
											storage.setItem('restoken',JSON.stringify(loginResult));
											var loginTime = loginResult.logintime;
											$rootScope.loginTime = loginTime;
											var userinfo = loginResult.token.user;
											// 登录的时候存放下
											setCookie("userId",userinfo.id);
											var arrayFuncCode = new Array();
											for(var i=0;i<loginResult.token.func.allCloneSubFuncs.length;i++){
												arrayFuncCode[i] =loginResult.token.func.allCloneSubFuncs[i].code;
											}
											var strFuncCode = arrayFuncCode.join(",");
											var addStrFuncCode = strFuncCode+",home";
											setCookie("funcCode",addStrFuncCode);
											if (loginTime == null){
												storageLocal.setItem('loginTime',"您好,第一次登录,请愉快使用!");
											} else {
												storageLocal.setItem('loginTime',loginTime);
											}
											if (rememberCheckbox.checked){
												setCookie('rememberMe',$scope.user.account+ ","+ $scope.user.password);
											} else {
												setCookie('rememberMe',"");
											}
											$window.location.href = "./#/home.html";
										},function(error) {
											$scope.getVerifyCode();
											// 更换验证码信息
											var errorMsg = JSON.parse(error);
											if(errorMsg && errorMsg.success == false){
												$scope.checkcodeIsCorrect = false;
												$scope.loginerror = true;
												$scope.singOnForm = true;
											};
										});
					}

					// 键盘登录
					$scope.loginKeyUp = function($event) {
						if ($event.keyCode == 13) {
							$scope.submitLoginForm();
						}
					};
					// 返回登录隐藏错误信息
					$scope.$on('to-child-login', function(event, data) {
						$scope.singOnAccount = false;
						$scope.loginerror = false;
						$scope.singOnForm = false;
						$scope.singOnPsw = false;
						$scope.checkcodeIsNull = false;
						$scope.checkcodeIsCorrect = false;
					});
				});

// 忘记密码
MetronicApp.controller('forgetCtrl', function($scope, $http, $q, $modal) {
	$scope.forgeter = {
		'email' : ''
	}
	$scope.submitted = false;
	$scope.submitForm = function() {

		if ($scope.forgetForm.$valid && $scope.forget == false){

			var emailJson = mergeJson('email',$scope.forgeter.email);
			var emailData = mergeReauestData('RegisterController','verifyEmail', emailJson);
			var emailResult = sendPost($http, emailData, $q);
			emailResult.then(function(success){
				if(JSON.parse(success).result == true) {
					//如果等于true的话，表示没有此邮件
					$scope.forget = true;
				}else{
					$scope.forget = false;
					var emailJson = mergeJson('emailForgetPwd',$scope.forgeter.email);
					var emailData = mergeReauestData('ForgetPasswordController','sendEmail', emailJson);
					var emailResult = sendPost($http, emailData, $q);
					emailResult.then(function(success) {
						var result = JSON.parse(success).result;
						if (result) {
							var pwdPrompt = $modal({
								scope : $scope,
								title : "提示",
								templateUrl : '/crm/loginPrompt.html',
								content : '密码发送成功',
								show : true
							});
						} else {
							var pwdPrompt = $modal({
								scope : $scope,
								title : "提示",
								templateUrl : '/crm/loginPrompt.html',
								content : '密码发送失败',
								show : true
							});
						}
					}, function(error) {
						$scope.forget = true;
					});
				}
			},function(error) {
				$scope.forget = true;
			});
		} else {
			$scope.forgetForm.submitted = true;
		}
	}
	$scope.hideError = function() {
		$scope.forget = false;
	}
	$scope.emaiBure = function() {
		if ($scope.forgetForm.$valid) {
			if ($scope.forget == false) {
				var emailJson = mergeJson('email',$scope.forgeter.email);
				var emailData = mergeReauestData('RegisterController','verifyEmail', emailJson);
				var emailResult = sendPost($http, emailData, $q);
				emailResult.then(function(success){
					if(JSON.parse(success).result == true) {
						//如果等于true的话，表示没有此邮件
						$scope.forget = true;
					}else{
						$scope.forget = false;
					}
				},function(error) {
					$scope.forget = true;
				});
			};
		} else {
			$scope.forgetForm.submitted = true;
		}
	}

	$scope.emaiChang = function() {
		$scope.forget = false;
	}
	// 进入忘记登录页面隐藏错误信息
	$scope.$on('to-child-forget', function(event, data) {
		$scope.forget = false;
		$scope.forgetForm.submitted = false;
	});
})

// 注册
MetronicApp.controller('registerCtrl',['$scope','$http','$q','$window','$modal',
						function($scope, $http, $q, $window, $modal) {
							$scope.submitted = false;
							$scope.isSame = false;
							$scope.tncShow = true;
							$scope.isSub = false;
							$scope.isChecked = false;
							// 发送验证码
							$scope.sendVerifyCode = function() {
								$scope.emailChangeEvent();
								var emailValue = document.getElementById("createrEmail").value;
								var emailNoLength = checkEmail(emailValue);
								// 1.前台验证邮件格式是否正确
								if ($scope.emailFormat == false
										&& $scope.emailRequired == false
										&& emailNoLength == false) {
									// 2.后台验证邮件是否已经被注册了
									var verifyEmailJson = mergeJson('email',emailValue);
									var verifyEmailData = mergeReauestData('RegisterController','verifyEmail', verifyEmailJson);
									var verifyEmailResult = sendPost($http,
											verifyEmailData, $q);
									verifyEmailResult
											.then(function(success) {
												// 2.1.表名验证通过
												if (JSON.parse(success).result == true) {

													var emailJson = mergeJson(
															'emailRegister',
															emailValue);
													var emailData = mergeReauestData(
															'RegisterController',
															'sendCode',
															emailJson);
													var emailResult = sendPost(
															$http, emailData,
															$q);
													emailResult
															.then(function(
																	success) {
																// 2.1 验证邮件没有被注册
																$scope.emailErrMsg = false;
																if (JSON
																		.parse(success).result) {
																	var codePrompt = $modal({
																		scope : $scope,
																		title : "提示",
																		templateUrl : 'loginPrompt.html',
																		content : '验证码发送成功',
																		show : true
																	});
																} else {
																	var codePrompt = $modal({
																		scope : $scope,
																		title : "提示",
																		templateUrl : 'loginPrompt.html',
																		content : '验证码发送失败',
																		show : true
																	});
																}
															})
												} else {
													// 2.2 验证邮件被注册
													$scope.emailErrMsg = true;
												}

											})

								} else {
									if ($scope.emailRequired == false) {
										$scope.emailFormat = checkEmail(emailValue);
									}
								}
							}
							$scope.userIsExitYes =false;
							//账户失去焦点事件
							$scope.checkDevAccount = function(){
								var account = mergeJson("userAccount", $scope.creater.account);
								var requestUserControllerData = mergeReauestData('UserController',
										'queryUserIdByAccont', account);
								var responseQueryUserResult = sendPost($http, requestUserControllerData,
										$q);
								responseQueryUserResult.then(function(success) {
									var user = JSON.parse(success).result;
									//已存在此用户
									if(user.isEnabled == true){
										//此用户已经存在
										$scope.userIsExitYes = true;
									}else if(user.isEnabled == false){
										//此用户已经存在，你可以进行恢复
										$scope.userIsExitYes = true;
									}else{
										$scope.userIsExitYes = false;
									}
								})
							}

							// 邮件改变事件
							$scope.emailChangeEvent = function() {
								var emailValue = document
										.getElementById("createrEmail").value;
								if (emailValue.length == 0) {
									$scope.emailRequired = true;
									$scope.emailFormat = false;
								} else {
									$scope.emailRequired = false;
									$scope.emailFormat = changeEmail(emailValue);
								}
							}
							// 邮件失去焦点事件
							$scope.emailBlurEvent = function() {
								var emailValue = document
										.getElementById("createrEmail").value;
								if (emailValue.length == 0) {
									$scope.emailRequired = true;
									$scope.emailFormat = false;
								} else {
									$scope.emailRequired = false;
									$scope.emailFormat = checkEmail(emailValue);
								}
								$scope.emailErrMsg = false;
							}
							// 邮件获得焦点事件
							$scope.hideRegisterEmailtip = function() {
								$scope.emailRequired = false;
								$scope.emailFormat = false;
								$scope.emailErrMsg = false;
							}
							// 获取焦点隐藏错误信息
							$scope.hidRregisterVerifycodeErrorInfo = function() {
								$scope.registerVerifycodeRequired = false;
								$scope.registerFormVerifycodeError = false;
							}
							// 失去焦点验证验证码是否正确
							$scope.validateCode = function() {
								if ($scope.verifyCode != null) {
									var codeParam = mergeJson('verifyCode',
											$scope.verifyCode);
									var codeData = mergeReauestData(
											'RegisterController',
											'checkVerifyCode', codeParam);
									var codeResult = sendPost($http, codeData,
											$q);
									codeResult
											.then(function(success) {
												var result = JSON
														.parse(success).valid;
												if (result)// 验证成功
												{
													$scope.registerFormVerifycodeError = false;
												} else// 验证失败
												{
													$scope.registerFormVerifycodeError = true;
												}
											});
								} else {
									// 验证码是必填的
									$scope.registerVerifycodeRequired = true;
								}
							}
							// 检验密码是否相同
							$scope.checkPassword = function() {
								if ($scope.creater.password != undefined
										&& $scope.creater.password != null
										&& $scope.creater.rpassword != undefined
										&& $scope.creater.rpassword != null) {
									if ($scope.creater.rpassword == $scope.creater.password) {

										$scope.pswUnify = false;
									} else {
										$scope.pswUnify = true;
									}
								}
							}
							// 如果用户名重复了。当在此输入用户名的时候
							$scope.hideErrorRegMsg = function() {
								$scope.regMsg = false;
								$scope.userIsExitYes = false;
							}
							$scope.goToLogin = function() {
								$window.location.href = "./login.html";
							}
							// 提交注册内容
							$scope.submitForm = function() {

								// 1.验证码不可以是空的、必须同意协议

								var registerCode = document
										.getElementById("reisterCodeId").value;

								if (registerCode.length == 0) {
									$scope.registerVerifycodeRequired = true;
								} else {
									$scope.registerVerifycodeRequired = false;
								}

								$scope.registerForm.isSub = true;
								if (!$scope.isChecked) {
									$scope.registerForm.tncShow = false;

								}

								if ($scope.registerForm.$valid
										&& $scope.registerForm.tncShow
										&& $scope.registerFormVerifycodeError == false
										&& $scope.registerVerifycodeRequired == false
										&& $scope.regMsg == false
										&& $scope.userIsExitYes ==false) {

									var createrJson = mergeJson('creatUser',
											$scope.creater);
									var createrData = mergeReauestData(
											'RegisterController', 'register',
											createrJson);
									var createrResult = sendPost($http,
											createrData, $q);
									createrResult.then(function(value) {

										$scope.registerReset();
										var loginPrompt = $modal({
											scope : $scope,
											title : "系统提示信息",
											templateUrl : 'loginPrompt.html',
											content : "用户注册成功,前往登录页面!",
											show : true
										});
									},
											function(error) {
												$scope.regErrorMsg = JSON
														.parse(error).errMsg;
												$scope.regMsg = true;
											});
								} else {
									$scope.registerForm.submitted = true;
								}
							};

							// 重置
							$scope.registerReset = function() {
								document.getElementById("register-form")
										.reset();
								$scope.registerForm.submitted = false;
								$scope.registerForm.codeValidate = false;
								$scope.registerForm.tncShow = true;
								$scope.registerVerifycodeRequired = false;
								$scope.registerFormVerifycodeError = false;
								$scope.regMsg = false;
								$scope.regMsg = false;
								$scope.emailFormat = false;
								$scope.pswUnify = false;
								$scope.emailRequired = false;
							};

							$scope.tncChange = function() {
								$scope.isChecked = !$scope.isChecked;
								if ($scope.registerForm.isSub) {
									$scope.registerForm.tncShow = !$scope.registerForm.tncShow;
								} else {
									$scope.registerForm.tncShow = true;
								}
							};
							// 监控清空注册信息
							$scope.$on('to-child-reset', function(event, data) {
								$scope.registerReset();
							});

							// 隐私条框和服务协议弹框
							var serviceOrRivacy = $modal({
								scope : $scope,
								templateUrl : './goToLogin.html',
								show : false,
								controller : 'serviceAgreementCtl',
								backdrop : "static",
								placement : "center"
							});

							$scope.serviceOrRivacy = function(serviceOrPrivacy) {

								serviceOrRivacy.$promise
										.then(serviceOrRivacy.show);

								$scope.serviceOrPrivacy = serviceOrPrivacy;
							};

						} ]);
// 服务条框和隐私协议弹框
MetronicApp.controller('serviceAgreementCtl',function($scope, $http, $window, $q, $sce) {

					$scope.privacyAndService = function() {

						if ($scope.$parent.serviceOrPrivacy == "serviceAgreement") {
							var privacyAndServiceParams = mergeJson(
									'loginItem', 10124);
						} else if ($scope.$parent.serviceOrPrivacy == "privacy") {
							var privacyAndServiceParams = mergeJson(
									'loginItem', 10125);
						}
						var privacyAndServiceData = mergeReauestData(
								'GlobalListController', 'queryLoginList',
								privacyAndServiceParams);
						var privacyAndServiceRequest = sendPost($http,
								privacyAndServiceData, $q);

						privacyAndServiceRequest.then(function(success) {
							var privacyAndServiceResponse = StrParesJSON(success);
							console.log(privacyAndServiceResponse);
							$scope.privacyAndServiceGlobalItem = $sce
									.trustAsHtml(privacyAndServiceResponse.result[0].variableDescribe
											.replace(/\n/g,"<br/>"));

						}, function(error) {
							console.info(error);
						});

					}
					$scope.privacyAndService();
				})
// 登陆和注册的切换
MetronicApp.controller('signInAndUpCtrl', function($scope, $translate) {
	// 多语言切换
	var lang = getCookie('languageChoice');
	if (lang != null) {
		setLanguage($translate, lang, "login");
	}

	$scope.signInIsShow = true;
	$scope.registerIsShow = false;
	$scope.forgetIsShow = false;
	$scope.forgetPassword = function() {
		$scope.signInIsShow = false;
		$scope.forgetIsShow = true;
		$scope.$broadcast('to-child-forget', 'child');
	};
	// 忘记密码返回登录 --》隐藏上一次的错误信息
	$scope.forgetBack = function() {
		$scope.forgetIsShow = false;
		$scope.signInIsShow = true;
		$scope.$broadcast('to-child-login', 'child');
	};
	$scope.createAnAccount = function() {
		$scope.signInIsShow = false;
		$scope.registerIsShow = true;
		// 清空注册信息
		$scope.$broadcast('to-child-reset', 'child');
	};
	// 注册过之后返回登陆
	$scope.registerBack = function() {
		$scope.$broadcast('to-child-login', 'child');
		$scope.signInIsShow = true;
		$scope.registerIsShow = false;
	}

});
