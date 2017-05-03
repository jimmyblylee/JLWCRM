var MetronicApp = angular.module("MetronicApp", [ "ui.router", "ui.bootstrap",
		'mgcrea.ngStrap', // 弹框插件
		"pascalprecht.translate"// 国际化
]);

MetronicApp.controller('accountCtrl',['$scope','$rootScope','$translate','$http','$q','$window','$modal',
						function($scope, $rootScope, $translate, $http, $q,$window, $modal) {
	
	        var storage = window.localStorage;
	        
	        var userId  = getCookie("userId");
	        if(userId!=undefined && userId!=null && userId != ""){
	             var sysUserJson = mergeJson('userId',userId);
	             var sysUserData = mergeReauestData('UserController', 'getPersonInfo',sysUserJson);
	             var sysUserResult = sendPost($http,sysUserData, $q);
	             sysUserResult.then(function(success){
	            	 
		               success = JSON.parse(success);
		               var sysUserPersonInfo = success.result;
		               $rootScope.sysUser = sysUserPersonInfo[0];
		               $scope.userName = $rootScope.sysUser.name;
	             }), function(error) {
	               console.info(error);
	             };       
	         }
			$scope.account = false;// 编辑窗口是否可见
			
			$scope.loginTime = storage.getItem("loginTime");
			
			// 显示和编辑的切换
			$scope.accountshow = function() {
				$scope.loginTime = storage.getItem("loginTime");
				$scope.account = !$scope.account;
				if ($scope.account) {
					$scope.updateUser = JSON.parse(JSON.stringify($scope.sysUser));
				}
			};
			//取消编辑
			$scope.cancelEdit = function(){
				document.getElementById("remarkTextAreaId").style.cssText = '';
				$scope.account = !$scope.account;
			}
			
			//显示修改密码弹框
			$scope.editPasswordModal = function(){
				editPasswordModal.$promise.then(editPasswordModal.show);
			}
			//隐藏修改密码弹框
			$scope.$on('to-parent', function() {
				editPasswordModal.$promise.then(editPasswordModal.hide);
			})
			
			// 修改密码的弹框
			editPasswordModal = $modal({scope : $scope,templateUrl : 'packages/sys/views/user/edit_password.html',
				show : false,container : '#modalView',controller : 'updatePsModalCtl',backdrop:"static"});
			
			// 修改信息后提交
			$scope.submitUpdateForm = function() {
				if ($scope.accountModelForm.$valid) {
					var updateUserJson = mergeJson('sysUser',
							$scope.updateUser);
					var updateUserData = mergeReauestData(
							'UserController', 'updatePersonal',
							updateUserJson);
					var updateUserResult = sendPost($http,updateUserData, $q);
					
					updateUserResult.then(function(success) {
						
					$rootScope.sysUser = JSON.parse(JSON.parse(success).sysUser);
										$modal({
											scope : $scope,
											title : "提示",
											templateUrl : 'packages/sys/views/user/tip.html',
											content : '注册信息修改成功',
											show : true,
											backdrop : "static"
										});
										$scope.accountshow();
									}), function(error) {
								console.info(error);
							}
				} else {
					$scope.accountModelForm.submitted = true;
				}
			};
		} ]);

//修改密码弹框
MetronicApp.controller('updatePsModalCtl', ['$scope','$rootScope','$translate','$http','$q','$window','$modal',
                                		function($scope, $rootScope, $translate, $http, $q, $window, $modal) {
	/**
	 * Description：校验两次输入密码是否一致
	 * 
	 * @author name：yuruixin
	 */
	$scope.checkPassword = function() {
		
		if ($scope.rpassword != undefined) {
			if ($scope.newPassword != null && $scope.rpassword == $scope.newPassword) {
				$scope.checkPsw = false;
			} else {
				$scope.checkPsw = true;
			}
		}else{
			$scope.checkPsw = false;
		}
	}
	//验证旧密码是否正确
	$scope.checkOldPassword = function(){
		    $scope.userId = $rootScope.token.user.id;
			//获取用户名和密码
		    if($rootScope.token.user.account != undefined &&  $scope.oldPassword != undefined){
		    	var accountParams = mergeJson('account',$rootScope.token.user.account);
	            var passwordParams = mergeJson('password',$scope.oldPassword);
	            
	            var loginParams = {};
	            $.extend(loginParams,accountParams,passwordParams);
	            var loginData = mergeReauestData('LoginController','queryUserIsExitByAccountAndPwd',loginParams);
	            var loginResult = sendPost($http,loginData,$q);
	            loginResult.then(function(success){
	            	$scope.oldPsw = false;
	            },function(error){
	            	$scope.oldPsw = true;
	            });
            }
	}
	//获取焦点错误信息
	$scope.hideOldPsw = function(){
		$scope.oldPsw = false;
	}
	//提交修改后的密码啦！
	 $scope.submitPwdForm = function() {
		 if($scope.editPwdForm.$valid){
			 if($scope.oldPsw == false && $scope.checkPsw == false){
					var sysUserJson = mergeJson('userId',$scope.userId);
					var sysUserData = mergeReauestData('UserController', 'getPersonInfo',sysUserJson);
					var sysUserResult = sendPost($http,sysUserData, $q);
					sysUserResult.then(function(success) {
						
						success = JSON.parse(success);
						var sysUserPersonInfo = success.result;
						$scope.sysUser = sysUserPersonInfo[0];
						$scope.sysUser.sysUserPwd = {
								password:$scope.rpassword
						}
						var updateUserJson = JSON.stringify($scope.sysUser);
						var updateUserData = pageService($http, $q, 'UserController',
									'updateSysUserPassword', null, null, updateUserJson);
						updateUserData.then(function(success) {
							/*
							 * 隐藏修改弹出框，提示修改机构信息成功
							 */
							$scope.$emit('to-parent');
							$modal({
								scope : $scope,
								title : "提示",
								templateUrl : 'packages/sys/views/user/tip.html',
								content : '修改密码成功',
								show : true,
								backdrop : "static"
							});
						 });
					}), function(error) {
						console.info(error);
					};
			 }
		 }else{
			 $scope.editPwdForm.submitted = true;
		 }
	 }
}]);
                                
MetronicApp.controller('editIconCtrl', ['$scope','$rootScope','$translate','$http','$q','$window','$modal',
		function($scope, $rootScope, $translate, $http, $q, $window, $modal) {
			/**
			 * Description：获取头像base64编码后字符串
			 * 
			 * @author name：yuruixin
			 */
			$scope.viewIcon = function() {
				var photoFile = this.files[0];
				/*
				 * 目前仅仅支持jpg格式的图片文件！
				 */
				if (!/image\/\w+/.test(photoFile.type)) {
					document.getElementById('inputFile').value = '';
					return false;
				} else if (photoFile.size > 51200) {
					document.getElementById('inputFile').value = '';
					document.getElementById("viewErrorInFo").style.display="";
					return false;
				} else {
					var reader = new FileReader();
					reader.readAsDataURL(photoFile);
					reader.onload = function(e) {
						$("#addUserPhoto").attr("src", this.result);
						$scope.$broadcast('userPhoto.photoStr', this.result);
						$scope.iconData = this.result;
						document.getElementById("viewErrorInFo").style.display="none";
					}
				}
			}
			/**
			 * Description：初始化头像上传相关
			 * 
			 * @author name：yuruixin
			 */
			$scope.photoInit = function() {
				var input = document.getElementById("inputFile");
				if (typeof (FileReader) === 'undefined') {
					input.setAttribute('disabled', 'disabled');
				} else {
					input.addEventListener('change', $scope.viewIcon, false);
				}
				$scope.userPhoto = {};
			}
			$scope.photoInit();
			// 提交上传的
			$scope.uploadForm = function() {
				// 验证表单
				if ($scope.iconData != null && $scope.iconData != undefined
						&& $scope.iconData != "") {					
					var userId  = getCookie("userId");
					var sysUserJson = mergeJson('userId',userId);
					var sysUserData = mergeReauestData('UserController',
							'getPersonInfo', sysUserJson);
					var sysUserResult = sendPost($http, sysUserData, $q);
					sysUserResult.then(function(success) {
						success = JSON.parse(success);
						var sysUserPersonInfo = success.result;
						$scope.sysUser = sysUserPersonInfo[0];
						  $scope.sysUser.sysUserPhoto = {photoStr:$scope.iconData};
						  var updateUserJson = JSON.stringify($scope.sysUser);
						  var updateUserData = pageService($http, $q, 'UserController',
									'updateUser', null, null, updateUserJson);
						  updateUserData.then(function(success) {
							  document.getElementById("viewSuccessInFo").style.display="";
							  console.log("成功了");
						  });
					}), function(error) {
						console.info(error);
					};				
				}
			}
			$scope.imgReload = function() {		
					$window.location.reload();
			}
		} ]);
