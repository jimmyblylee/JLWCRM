var deptCtrlFlag = "";
function UsertableCtrl($scope, $http, $q, $filter, $modal, $timeout) {
	$scope.items = []; // 当前返回总数据
	$scope.itemsPerPage = itemsPerPage;// 当前页显示数据
	$scope.Pagenum = Pagenum;
	$scope.user = {};
	$scope.user.isEnabled = "true";
	
	/**
	 * Description：获取有效性
	 * 
	 * @author name：yuruixin
	 */
	$scope.getUserIsEnabled = function() {
		if ($scope.userIsEnabledJson == null) {
			var responseDictResult = getSelectValueByDictList($http, $q,
					'STATUS_TYPE', 'STATUS_CODE');
			responseDictResult.then(function(success) {
				$scope.userIsEnabledJson = StrParesJSON(success).result;
			}, function(error) {
				console.info(error);
			});
		}
	}
	$scope.getUserIsEnabled();
	// 弹窗getDeptTreeParent
	var deptSelect = $modal({
		scope : $scope,
		templateUrl : 'packages/sys/views/user/deptTreePop.html',
		show : false,
		container : '#userlist',
		controller : 'deptSelectCtrl',
		backdrop : "static"
	});
	var userInfoPage = $modal({
		scope : $scope,
		templateUrl : 'packages/sys/views/enterpriseUser/userInfo.html',
		show : false,
		container : '#userlist',
		controller : 'userInfoPageCtrl',
		backdrop : "static"
	});
	var updateUserInfoPage = $modal({
		scope : $scope,
		templateUrl : 'packages/sys/views/enterpriseUser/updateUserInfo.html',
		show : false,
		container : '#userlist',
		controller : 'userInfoPageCtrl',
		backdrop : "static"
	});
	var addUserPage = $modal({
		scope : $scope,
		templateUrl : 'packages/sys/views/enterpriseUser/addUser.html',
		show : false,
		container : '#userlist',
		controller : 'addUserCtrl',
		backdrop : "static"
	});

	$scope.$on('to-parent', function() {
		addUserPage.$promise.then(addUserPage.hide);
		updateUserInfoPage.$promise.then(updateUserInfoPage.hide);
	})

	$scope.setEnabled = function(selectCode, selectValue) {
		
		$scope.user.isEnabled = selectCode;
		$scope.isEnabledValue = selectValue;
	}
	/**
	 * Description：给根据条件查询相应用户信息
	 * 
	 * @author name：yuruixin
	 */
	$scope.queryUser = function(isEnabled) {
		document.getElementById("parent").checked = false;
	    if($scope.user.isEnabled =="true" && isEnabled=="false"){
	    	$scope.user.isEnabled = "true";
		}else if($scope.user.isEnabled != "true" && isEnabled=="false"){
			$scope.user.isEnabled = "false";
		}
		if($scope.isEnabledTrueShow && isEnabled == "true"){
			$scope.user.isEnabled = "true";
		}else if($scope.isEnabledFalseShow && isEnabled == "true"){
			$scope.user.isEnabled = "false";
		}
		if ($scope.user.isEnabled == "true") {
			$scope.isEnabledTrueShow = true;
			$scope.isEnabledFalseShow = false;
		} else {
			$scope.isEnabledTrueShow = false;
			$scope.isEnabledFalseShow = true;
		}
		if($scope.deptId != undefined){
			$scope.user.deptId = $scope.deptId;
		}
		if($scope.likeQuery != undefined){
			$scope.user.name = $scope.likeQuery;
		}
		
		var userQuery = ObjParesJSON($scope.user);
		var sysUserResult = pageService($http, $q, 'UserController',
				'querySysUserByDeptAndAccountAndName',$scope.currentPage,
				$scope.itemsPerPage, userQuery);
		sysUserResult.then(function(success) {
			var userQueryResponse = StrParesJSON(success);
			$scope.items = userQueryResponse.result;
			$scope.bigTotalItems = userQueryResponse.total;

		}, function(error) {
			console.info(error);
		});
	}
	$scope.currentPage =1;
	 var userId  = getCookie("userId");
     if(userId!=undefined && userId!=null && userId != ""){
          var sysUserJson = mergeJson('userId',userId);
          var sysUserData = mergeReauestData('UserController', 'getPersonInfo',sysUserJson);
          var sysUserResult = sendPost($http,sysUserData, $q);
          sysUserResult.then(function(success){
        	  
	               success = JSON.parse(success);
	               var sysUserPersonInfo = success.result[0];
	               $scope.deptId = sysUserPersonInfo.dept.id;
	               $scope.queryUser("true");
          }), function(error) {
            console.info(error);
          };       
      }
	scrollwatch($scope, $timeout);	
	$scope.queryUserList = function(isEnabled) {
		$scope.isEnabledTrueShow = undefined;
		$scope.isEnabledFalseShow = undefined;
		$scope.queryUser(isEnabled);
	}
	
	$scope.changUserPageValue = function(){
		$scope.currentPage =1;
		$scope.queryUser("true");
	}

	// 刷新
	$scope.refreshUserList = function() {
		$scope.deptName =null;
		$scope.likeQuery=null;
		$scope.currentPage =1;
		$scope.itemsPerPage = Pagenum[0];
		$scope.queryUser("true");
		$scope.order = "account";
		$scope.reverse = false;
		sort_global($scope, $scope.order, $scope.reverse, null);
		
	}

	// 回车查询
	$scope.keyup = function($event) {
		if ($event.keyCode == 13) {
			$scope.queryUserList("false");
		}

	}
	$scope.queryUserByDept = function(deptId_tree,eventTarget) {
/*		if ($scope.deptId == deptId_tree) {
               angular.element(eventTarget.currentTarget).addClass('tree-selected')
		}else{*/
			angular.element('.treeLink').removeClass('tree-selected')
		//};
		$scope.deptId = deptId_tree;
		$scope.queryUser("true");
	}
	/**
	 * Description：排序
	 * 
	 * @author name：yuruixin
	 */
	$scope.order = "account";
	$scope.reverse = false;
	$scope.sort_by = function(newSortingOrder) {
		sort_global($scope, $scope.order, $scope.reverse, newSortingOrder);
	}
	/**
	 * Description：获取证件类型
	 * 
	 * @author name：yuruixin
	 */
	$scope.getIdType = function() {
		if ($scope.idTypeDicts == null) {
			var responseUserIdTypeResult = getSelectValueByDictList($http, $q,
					'USER_ID_TYPE', 'NATURE');
			responseUserIdTypeResult.then(function(success) {
				$scope.idTypeDicts = StrParesJSON(success).result;
				$scope.$broadcast('dict.idTypeDicts', $scope.idTypeDicts);
			}, function(error) {
				console.info(error);
			});
		}
	}
	$scope.getIdType();
	/**
	 * Description：获取性别
	 * 
	 * @author name：yuruixin
	 */
	$scope.getSex = function() {
		if ($scope.sexDicts == null) {
			var responseUserSexResult = getSelectValueByDictList($http, $q,
					'SEX', 'NATURE');
			responseUserSexResult.then(function(success) {
				$scope.sexDicts = StrParesJSON(success).result;
			}, function(error) {
				console.info(error);
			});
		}
	}
	$scope.getSex();
	/**
	 * Description：获取用户类型
	 * 
	 * @author name：yuruixin
	 */
	//默认普通用户
	$scope.addUserInfoType = "NORMAL";
	$scope.getUserType = function() {
		if ($scope.userTypeDicts == null) {
			var userTypeDictsResult = getSelectValueByDictList($http, $q,
					'USER_TYPE', 'NATURE');
			userTypeDictsResult.then(function(success) {
				$scope.userTypeDicts = StrParesJSON(success).result;
			}, function(error) {
				console.info(error);
			});
		}
	}
	$scope.getUserType();
	/**
	 * Description：复选框 全选/全不选
	 * 
	 * @author name：yuruixin
	 */
	$scope.checkAll = function(cancel) {
		var parent_check = document.getElementById("parent");
		if (cancel == 1) {
			parent_check.checked = false;
		}
		var child_check = document.getElementsByName("userCheckChild");
		for (i = 0; i < child_check.length; i++) {
			if (child_check[i].type == "checkbox") {
				child_check[i].checked = parent_check.checked;
			}
		}
	}
	$scope.groupInfo = function() {
		if ($scope.groupList == null) {
			var creatGroupResult = pageService($http, $q, 'UserController',
					'queryAllGroup', null, null, null);
			creatGroupResult.then(function(success) {
				$scope.groupList = StrParesJSON(success).result;
			}, function(error) {
				console.info(error);
			});
		}
	}
	$scope.groupInfo();
	/**
	 * Description：在弹出页面生成用户组复选信息
	 * 
	 * @author name：yuruixin
	 */
	$scope.creatGroupText = function() {
		var groupChecked = document.getElementsByName('childs_group');
		for (var i = 0; i < groupChecked.length; i++) {
			groupChecked[i].checked = false;
		}
		var userChecked = document.getElementsByName('userCheckChild');
		var userIdJsonIndex = 0;
		var userIdJson = [];
		for (var k = 0; k < userChecked.length; k++) {
			if (userChecked[k].checked) {
				userIdJson[userIdJsonIndex] = parseInt(userChecked[k].value);
				userIdJsonIndex++;
			}
		}
		if (userIdJson.length > 0) {
			$scope.replaceGroupWindowTipShow = false;
			$scope.replaceGroupWindowShow = true;
		} else {
			$scope.replaceGroupWindowTipShow = true;
			$scope.replaceGroupWindowShow = false;
		}
	}

	/**
	 * Description：更新用户的用户组信息
	 * 
	 * @author name：yuruixin
	 */
	$scope.replaceUsersGroup = function() {
		var userIdJson = [];
		var groupIdJson = [];
		var paramsJson = {}
		var groupChecked = document.getElementsByName('childs_group');
		var userChecked = document.getElementsByName('userCheckChild');
		var userIdJsonIndex = 0;
		for (var k = 0; k < userChecked.length; k++) {
			if (userChecked[k].checked) {
				userIdJson[userIdJsonIndex] = parseInt(userChecked[k].value);
				userIdJsonIndex++;
			}
		}
		var groupIdJsonIndex = 0;
		if (userIdJson.length > 0) {
			for (var i = 0; i < groupChecked.length; i++) {
				if (groupChecked[i].checked) {
					groupIdJson[groupIdJsonIndex] = parseInt(groupChecked[i].value);
					groupIdJsonIndex++;
				}
			}
			paramsJson.userIdJson = userIdJson;
			paramsJson.groupIdJson = groupIdJson;
			paramsJson = JSON.stringify(paramsJson);
			var replaceUsersGroupResult = pageService($http, $q,
					'UserController', 'replaceUserGroup', null, null,
					paramsJson);
			replaceUsersGroupResult
					.then(
							function(success) {
								$scope.replaceUserGroupSuccessFlag = StrParesJSON(success).result;
								$modal({
									scope : $scope,
									title : "提示",
									templateUrl : 'packages/sys/views/user/tip.html',
									content : '更新用户与用户组信息成功',
									show : true,
									backdrop : "static"
								});
							}, function(error) {
								console.info(error);
							});
		}
	}

	/**
	 * Description：重置deptCtrlFlag
	 * 
	 * @author name：yuruixin
	 */
	$scope.clearFlag = function() {
		deptCtrlFlag = "";
	}

	/**
	 * Description：批量删除用户
	 * 
	 * @author name：yuruixin
	 */
	$scope.deleteUsers = function(deleteParam) {
		var userIdJson = [];
		var deleteJson = {};
		if (deleteParam != 'delete' && deleteParam != 'deleteUsers'
				&& deleteParam != 'clear') {
			$scope.deleteUserId = deleteParam;
			$scope.confirmShow = true;
			$scope.confirmErroeShow = true;
			$scope.warnMessage = "确认删除该用户？";
		} else if (deleteParam == 'deleteUsers') {
			var userIds = document.getElementsByName('userCheckChild');
			var userIdJsonIndex = 0;
			for (var i = 0; i < userIds.length; i++) {
				if (userIds[i].checked) {
					userIdJson[userIdJsonIndex] = parseInt(userIds[i].value);
					userIdJsonIndex++;
				}
			}
			$scope.deleteUserIds = userIdJson;
			if (userIdJson.length > 0) {
				$scope.warnMessage = "确认删除选中用户？";
				$scope.confirmShow = true;
				$scope.confirmErroeShow = true;
			} else {
				$scope.warnMessage = "请至少选择一名用户！";
				$scope.confirmShow = true;
				$scope.confirmErroeShow = false;
			}
		} else if (deleteParam == 'delete') {
			if ($scope.deleteUserIds != undefined
					&& $scope.deleteUserIds.length > 0) {
				userIdJson = $scope.deleteUserIds;
			} else if ($scope.deleteUserId != '') {
				userIdJson[0] = $scope.deleteUserId;
			}
		} else if (deleteParam == 'clear') {
			$scope.deleteUserIds = [];
			$scope.deleteUserId = '';
		}
		if (userIdJson.length > 0 && deleteParam == 'delete') {
			deleteJson.userIdJson = userIdJson;
			deleteJson = JSON.stringify(deleteJson);
			var deleteUserResult = pageService($http, $q, 'UserController',
					'deleteUser', null, null, deleteJson);
			deleteUserResult.then(function(success) {
				$scope.deleteUserIds = [];
				$scope.deleteUserId = '';
				$scope.deleteUser = StrParesJSON(success).result;
				$scope.queryUser("true");
				document.getElementById("parent").checked = false;
				$modal({
					scope : $scope,
					title : "提示",
					templateUrl : 'packages/sys/views/user/tip.html',
					content : '用户删除成功',
					show : true,
					backdrop : "static"
				});
			}, function(error) {
				console.info(error);
			});
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
			input.addEventListener('change', $scope.readPhoto, false);
		}
		$scope.userPhoto = {};
	}

	/**
	 * Description：获取头像base64编码后字符串
	 * 
	 * @author name：yuruixin
	 */
	$scope.readPhoto = function() {
		var photoFile = this.files[0];
		if (!/image\/\w+/.test(photoFile.type)) {
			document.getElementById('inputFile').value = '';
			return false;
		} else if (photoFile.size > 51200) {
			document.getElementById('inputFile').value = '';
			// 把这个消息传递过去
			$scope.$broadcast('userPhoto.error', "选择的图片最大为50KB");

			return false;
		} else {
			var reader = new FileReader();
			reader.readAsDataURL(photoFile);
			reader.onload = function(e) {
				console.log(this.result);
				$scope.$broadcast('userPhoto.photoStr', this.result);
			}
		}
	}

	$scope.clearPhoto = function() {
		document.getElementById('inputFile').value = '';
		$scope.$broadcast('userPhoto.photoStr', null);
	}
	/**
	 * Description：恢复用户
	 * 
	 * @author name：yuruixin
	 */
	$scope.repeatUsers = function(repeatParam) {
		var userIdJson = [];
		var repeatJson = {};
		if (repeatParam != 'repeat' && repeatParam != 'repeatUsers'
				&& repeatParam != 'clear') {
			$scope.repeatUserId = repeatParam;
			$scope.confirmShow = true;
			$scope.confirmErroeShow = true;
			$scope.warnMessage = "确认恢复该用户？";
		} else if (repeatParam == 'repeatUsers') {
			var userIds = document.getElementsByName('userCheckChild');
			var userIdJsonIndex = 0;
			for (var i = 0; i < userIds.length; i++) {
				if (userIds[i].checked) {
					userIdJson[userIdJsonIndex] = parseInt(userIds[i].value);
					userIdJsonIndex++;
				}
			}
			$scope.repeatUserIds = userIdJson;
			if (userIdJson.length > 0) {
				$scope.warnMessage = "确认恢复选中用户？";
				$scope.confirmShow = true;
				$scope.confirmErroeShow = true;
			} else {
				$scope.warnMessage = "请至少选择一名用户！";
				$scope.confirmShow = true;
				$scope.confirmErroeShow = false;
			}
		} else if (repeatParam == 'repeat') {
			if ($scope.repeatUserIds != undefined
					&& $scope.repeatUserIds.length > 0) {
				userIdJson = $scope.repeatUserIds;
			} else if ($scope.repeatUserId != '') {
				userIdJson[0] = $scope.repeatUserId;
			}
		} else if (repeatParam == 'clear') {
			$scope.repeatUserIds = [];
			$scope.repeatUserId = '';
		}
		if (userIdJson.length > 0 && repeatParam == 'repeat') {
			repeatJson.userIdJson = userIdJson;
			repeatJson = JSON.stringify(repeatJson);
			var repeatUserResult = pageService($http, $q, 'UserController',
					'repeatUser', null, null, repeatJson);
			repeatUserResult.then(function(success) {
				$scope.repeatUserIds = [];
				$scope.repeatUserId = '';
				$scope.repeatUser = StrParesJSON(success).result;
				$scope.queryUser("false");
				document.getElementById("parent").checked = false;
				$modal({
					scope : $scope,
					title : "提示",
					templateUrl : 'packages/sys/views/user/tip.html',
					content : '用户恢复成功',
					show : true,
					backdrop : "static"
				});
			}, function(error) {
				console.info(error);
			});
		}
	}

	/**
	 * Description：重置查询条件
	 * 
	 * @author name：yuruixin
	 */
	$scope.resetQuery = function() {
		$scope.deptName = "";
		$scope.user.account = "";
		$scope.user.name = "";
		$scope.user.isEnabled = "true";
		$scope.deptId = null;
	}

	/**
	 * Description：接收子controller传来的值
	 * 
	 * @author name：yuruixin
	 */
	$scope.$on('deptSelectTo-parent', function(event, deptName, deptId, orgLevelType) {
		if (deptCtrlFlag == "updateUser") {
			$scope.deptNameUpdate = deptName;
			$scope.deptIdUpdate = deptId;
		} else if (deptCtrlFlag == "addUser") {
			$scope.deptNameAdd = deptName;
			$scope.deptIdAdd = deptId;
            if(deptId !=null){
                var params = {};
                params.deptID = deptId;
                var requestUserControllerData = mergeReauestData('UserController',
                    'queryReportGroupByDeptID', params);
                var responseQueryUserResult = sendPost($http, requestUserControllerData,$q);
                responseQueryUserResult.then(function(success) {
                    $scope.reportGroupLists = StrParesJSON(success).result;
                    }), function(error) {
                    console.info(error);
                };
            }
		} else {
			$scope.deptName = deptName;
			$scope.deptId = deptId;
		}
        $scope.orgLevelType = orgLevelType;
	});

	/**
	 * Description：部门树弹窗
	 * 
	 * @author name：yuruixin
	 */

	$scope.deptSelect = function() {
        deptSelect.$promise.then(deptSelect.show);
	};

	/**
	 * Description：用户详情弹窗
	 * 
	 * @author name：yuruixin
	 */
	$scope.userInfoPage = function(userId) {
		$scope.userInfoId = userId;
		userInfoPage.$promise.then(userInfoPage.show);
	};

	/**
	 * Description：用户详情更改弹窗
	 * 
	 * @author name：yuruixin
	 */
	$scope.updateUserInfoPage = function(userId) {
		$scope.userInfoId = userId;
		updateUserInfoPage.$promise.then(updateUserInfoPage.show);
	};
       $scope.$on("re-totree",function(){
			$scope.$broadcast('re-totreeemit');
		})
			
			
	/**
	 * Description：用户增加弹窗
	 * 
	 * @author name：yuruixin
	 */
	$scope.addUserPage = function() {
		addUserPage.$promise.then(addUserPage.show);
	};

	// 测试使用------------------开始----------------------->>>
	$scope.setDeptSelect = function(deptSelect_) {
		deptSelect = deptSelect_;
	};
	$scope.setUserInfoPage = function(userInfoPage_) {
		userInfoPage = userInfoPage_;
	};
	$scope.setUpdateUserInfoPage = function(updateUserInfoPage_) {
		updateUserInfoPage = updateUserInfoPage_;
	};
	// 测试使用------------------结束----------------------->>>
}

/**
 * Description：更新用户信息controller
 * 
 * @author name：yuruixin
 */
function userInfoPageCtrl($scope, $http, $q, $filter, $modal,$rootScope) {
    $scope.$on('deptSelectToReportGroup-parent', function(event, deptName, deptId, orgLevelType) {
        if($scope.deptId !=null){
            var params = {};
            params.deptID = deptId;
            var requestUserControllerData = mergeReauestData('UserController',
                'queryReportGroupByDeptID', params);
            var responseQueryUserResult = sendPost($http, requestUserControllerData,$q);
            responseQueryUserResult.then(function(success) {
                $scope.deptIDUpdate = null;
                $scope.reportGroupUpdateLists = StrParesJSON(success).result;
            }), function(error) {
                console.info(error);
            };
        }
    });
    var deptUpdateSelect = $modal({
        scope : $scope,
        templateUrl : 'packages/sys/views/user/deptTreePop.html',
        show : false,
        container : '#userlist',
        controller : 'deptSelectCtrl',
        backdrop : "static"
    });
    $scope.deptSelect = function() {
        deptUpdateSelect.$promise.then(deptUpdateSelect.show);
    };
	/**
	 * Description：生成用户详情信息
	 * 
	 * @author name：yuruixin
	 */
	$scope.deptNameUpdate;
	$scope.queryUserInfo = function(userId) {
		$scope.userPhoto = {};
		$scope.sysUserPwd = {};
		deptCtrlFlag = "updateUser";
		// 初始化用户组信息
		var getPhotoResult = pageService($http, $q, 'UserController',
				'getUserPhoto', null, userId, null);
		getPhotoResult.then(function(success) {

			$scope.userPhotoShow = StrParesJSON(success).result == null ? null
					: StrParesJSON(success).result;

			$("#updateUserPhoto").attr("src", $scope.userPhotoShow);
		});

		var groupsResult = pageService($http, $q, 'UserController',
				'queryGroupsByUserId', null, userId, null);

		groupsResult.then(function(success) {
			groupsResponse = StrParesJSON(success).result;
			var group = document.getElementsByName('child_group');
			for (var i = 0; i < group.length; i++) {
				group[i].checked = false;
			}
            $scope.deptNameUpdate = $scope.userInfo.dept.name;
			for ( var key in groupsResponse) {
				for (var i = 0; i < group.length; i++) {
					if (group[i].value == groupsResponse[key].id) {
						group[i].checked = true;
					}
				}
			}
			$scope.photoInit();
		}, function(error) {
			console.info(error);
		});
		// 获取用户详细信息
		for ( var key in $scope.items) {
			if ($scope.items[key].id == userId) {
				$scope.userInfo = StrParesJSON(JSON.stringify($scope.items[key]));
                if($scope.userInfo.reportGroupEntity != null){
                    $scope.reportGroupInfoName = $scope.userInfo.reportGroupEntity.name;
                    var params = {};
                    var deptIDUpdate = $scope.userInfo.dept.id;
                    params.deptID = deptIDUpdate;
                    var requestUserControllerData = mergeReauestData('UserController',
                        'queryReportGroupByDeptID', params);
                    var responseQueryUserResult = sendPost($http, requestUserControllerData,$q);
                    responseQueryUserResult.then(function(success) {
                        $scope.reportGroupUpdateLists = StrParesJSON(success).result;
                        for(var i= 0;i<$scope.reportGroupUpdateLists.length;i++){
                            if($scope.reportGroupUpdateLists[i].id == $scope.userInfo.reportGroupEntity.id){
                                $scope.deptIDUpdate = $scope.reportGroupUpdateLists[i].id.toString();
                            }
                        }
                    }), function(error) {
                        console.info(error);
                    };
                }
				//记录原有的email
				$scope.originalEmail = $scope.userInfo.email;
			}
		}
		// 根据用户信息初始化类型默认值
		if ($scope.userInfo.type != null) {
			for ( var i in $scope.userTypeDicts) {
				if ($scope.userTypeDicts[i].code == $scope.userInfo.type.code) {
					$scope.userInfo.type = $scope.userTypeDicts[i];
				}
			}
		}
		
		// 根据用户信息初始化性别默认值
		if ($scope.userInfo.sex != null) {
			for ( var i in $scope.sexDicts) {
				if ($scope.sexDicts[i].code == $scope.userInfo.sex.code) {
					$scope.userInfo.sex = $scope.sexDicts[i];
				}
			}
		}
		// 根据用户信息初始化用户id类型默认值
		if ($scope.userInfo.idType != null) {
			for ( var i in $scope.idTypeDicts) {
				if ($scope.idTypeDicts[i].code == $scope.userInfo.idType.code) {
					$scope.userInfo.idType = $scope.idTypeDicts[i];
				}
			}
		}
		if ($scope.userInfo.birthday != null) {
			$scope.userInfo.birthday = $scope.userInfo.birthday
					.substring(0, 10);
		}
	}
	//默认密码
	$scope.sysUserPwd ={};
	
	$scope.queryUserInfo($scope.userInfoId);			
	$scope.editHidemodal = function(){
		if($scope.updateForm.$pristine){//判断是否有修改 如果为true代表没有修改执行关闭
			$scope.$emit('to-parent');
			$scope.updateForm = undefined
			$scope.clearFlag();
			}else{//如果是false 代表有修改 显示提示框
				$rootScope.closeModel.$promise.then($rootScope.closeModel.show);	
			}
		}			
		$rootScope.$on('hideModel', function(event, usereditHidemodal) {//确定丢弃输入的修改内容后需要执行的方法	
			if ($scope.updateForm != undefined) {		
				if (usereditHidemodal) {	
					if ($scope.updateForm.account.$error.required || $scope.updateForm.name.$error.required || $scope.updateForm.dept.$error.required ||
						$scope.updateForm.youjian.$error.required ) {

						    $scope.updateForm.submitted = true;

					}else if(  $scope.updateForm.email || $scope.updateForm.isSame || $scope.updateForm.tel || $scope.updateForm.fixedTel || $scope.updateForm.postalCode || $scope.emailUpAlreadyExit){
					}else{

						$scope.updateUser(); // 执行提交方法						
					}					
					
				}else{
					$scope.$emit('to-parent');
					$scope.updateForm = undefined
					$scope.clearFlag();
				};
			};
		})
	$scope.today = function() {
		$scope.userInfo.birthday;
	};
	$scope.today();
	$scope.clear = function() {
		$scope.userInfo.birthday = null;
	};
	$scope.disabled = function(date, mode) {
		return;
	};
	$scope.toggleMin = function() {
		$scope.minDate = new Date();
	};
	$scope.toggleMin();
	$scope.open = function($event) {
		$event.preventDefault();
		$event.stopPropagation();
		$scope.opened = true;
	};
	$scope.dateOptions = {
		formatYear : 'yy',
		startingDay : 1
	};
	$scope.format = 'yyyy-MM-dd';
	/**
	 * Description：向后台发送更新用户详情信息
	 * 
	 * @author name：yuruixin
	 */
	$scope.updateUser = function() {
		//1.验证基础表单数据 
		if ($scope.updateForm.$valid) {
			//2.验证要修改的验证码是否已经被注册,如果这两个值相等，就不用查了
			if($scope.originalEmail == $scope.userInfo.email){
				$scope.commitUpdateInfo();
			}else{
				//3.判断邮件是否已经注册
				var emailJson = mergeJson('email',$scope.userInfo.email);
				var emailData = mergeReauestData('RegisterController','verifyEmail', emailJson);
				var emailResult = sendPost($http, emailData, $q);
				emailResult.then(function(success){
					//4.表示此邮件没有被注册过，可以继续使用
					if(JSON.parse(success).result == true){
						$scope.commitUpdateInfo();
					}else{
						$scope.emailUpAlreadyExit = true;
					}
				})
			}
		} else {
			$scope.updateForm.submitted = true;
		}
	}
	/**
	 * 提交用户信息
	 */
	$scope.commitUpdateInfo = function(){
		deptCtrlFlag = "";
		// 获取复选框信息
		var groupIdJson = [];
		var group = document.getElementsByName('child_group');
		var userIdJsonIndex = 0;
		for (var i = 0; i < group.length; i++) {
			if (group[i].checked) {
				groupIdJson[userIdJsonIndex] = group[i].value;
				userIdJsonIndex++;
			}
		}
		$scope.userInfo.groupId = groupIdJson;
		if ($scope.deptIdUpdate != null) {
			$scope.userInfo.dept = {};
			$scope.userInfo.dept.id = $scope.deptIdUpdate;
		}
		if ($scope.userInfo.type != null) {
			$scope.userInfo.typeCode = $scope.userInfo.type.code;
		}
		if ($scope.userInfo.sex != null) {
			$scope.userInfo.sexCode = $scope.userInfo.sex.code;
		}
		if ($scope.userInfo.idType != null) {
			$scope.userInfo.idTypeCode = $scope.userInfo.idType.code;
		}
		if (/\D/gi.test($scope.userInfo.postalCode)) {
			$scope.userInfo.postalCode = null;
		}
		if($scope.userPhoto.photoStr != undefined && $scope.userPhoto.photoStr != null){
			$scope.userInfo.sysUserPhoto = $scope.userPhoto;
		}else{
			$scope.userPhoto.photoStr =$scope.userPhotoShow
			$scope.userInfo.sysUserPhoto = $scope.userPhoto;
		}
		$scope.userInfo.sysUserPwd = $scope.sysUserPwd;
        if($scope.deptIDUpdate != null && $scope.deptIDUpdate != ""){
            $scope.userInfo.reportGroupId = $scope.deptIDUpdate;
        }
		var paramsJson = JSON.stringify($scope.userInfo);
		var updateUserResult = pageService($http, $q, 'UserController',
				'updateUserCompany', null, null, paramsJson);
		console.log("paramsJson:" + paramsJson);
		updateUserResult.then(function(success) {
			$scope.updateUserSuccessFlag = StrParesJSON(success).result;
			$scope.deptId = StrParesJSON(StrParesJSON(success).pageQuery).dept.id;
			$scope.$emit('to-parent');
			$modal({
				scope : $scope,
				title : "提示",
				templateUrl : 'packages/sys/views/user/tip.html',
				content : '用户信息更新成功',
				show : true,
				backdrop : "static"
			});
			$scope.updateForm = undefined;			
			$scope.$emit("re-totree");
			$scope.queryUserByDept($rootScope.orgDataToken.deptID)
		}, function(error) {
			$scope.errorUpdateMsg = JSON.parse(error).errMsg;
			$scope.userUpdateErrMsg = true;
			$scope.userUpdateAccout = true;
		});
		
	}

	/**
	 * Description：校验两次输入密码是否一致
	 * 
	 * @author name：yuruixin
	 */
	$scope.checkPassword = function() {
		if ($scope.rpassword != undefined) {
			if ($scope.sysUserPwd != null && $scope.rpassword == $scope.sysUserPwd.password) {
				$scope.updateForm.isSame = false;
			} else {
				$scope.updateForm.isSame = true;
			}
		}else{
			$scope.addUserForm.isSame = false;
		}
	}
	/*
	 * 离开焦点判断邮件格式 ---》并且判断邮件是否注册
	 */
	$scope.checkEmail = function() {
		$scope.updateForm.email = checkEmail($scope.userInfo.email);
	}
	
	$scope.emailFocus = function(){
		$scope.emailUpAlreadyExit = false;
		 $scope.updateForm.submitted = false;
	}
	
	//确认密码获取焦点，隐藏错误信息
	$scope.hideErrorPsw = function(){
		$scope.updateForm.isSame = false;
		$scope.updateForm.submitted = false;
	}
	//邮政编码获得焦点隐藏错误信息
	$scope.checkFocus = function(){
		$scope.updateForm.postalCode= false;
		$scope.updateForm.submitted = false;
	}
	
	$scope.hideErrorFexTel = function(){
		$scope.updateForm.fixedTel = false;
		$scope.updateForm.submitted = false;
	}
	/*
	 * 离开焦点判断邮件格式
	 */
	$scope.checkEmail = function() {
		$scope.updateForm.email = checkEmail($scope.userInfo.email);
	}
	/*
	 * 邮件改变判断邮件格式
	 */
	$scope.emailChange = function() {
		$scope.updateForm.email = changeEmail($scope.userInfo.email);
	}

	$scope.hideErrorPhone = function() {
		$scope.updateForm.tel = false;
		$scope.addUserForm.submitted = false;
	}

	// 值发生改变验证手机号
	$scope.checkPhone = function() {
		$scope.updateForm.tel = checkPhone($scope.userInfo.tel);
	}

	$scope.phoneChange = function() {
		$scope.updateForm.tel = changePhone($scope.userInfo.tel);
	}

	// 失去焦点检验固定电话
	$scope.checkFexTel = function() {
		$scope.updateForm.fixedTel = checkFaxTel($scope.userInfo.fixedTel);
	}
	// 值发生改变验证固定电话
	$scope.changeFaxTel = function() {
		$scope.updateForm.fixedTel = changeFaxTel($scope.userInfo.fixedTel);
	}

	// 失去焦点检验邮编
	$scope.checkPostalCode = function() {
		$scope.updateForm.postalCode = checkNumber($scope.userInfo.postalCode);
	}
	// 值发生改变验证邮编
	$scope.changePostalCode = function() {
		$scope.updateForm.postalCode = false;
	}

	/**
	 * Description：接收父controller传递的照片文件字符串
	 * 
	 * @author name：yuruixin
	 */
	$scope.$on('userPhoto.photoStr', function(event, photoStr) {
		$scope.userPhotoShow = photoStr == null ? null : photoStr;
		$("#updateUserPhoto").attr("src", $scope.userPhotoShow);
		$scope.userPhoto.photoStr = photoStr;

		$scope.iconUpError = false;
		var iconUp = document.getElementById("updateIconId");
		addClass(iconUp, "ng-hide");
	});
	$scope.iconUpError = false;
	$scope.$on('userPhoto.error', function(event, photoStr) {
		$scope.iconUpError = true;
		var iconUp = document.getElementById("updateIconId");
			removeClass(iconUp, "ng-hide");
	});
}

/**
 * Description：添加用户controller
 * 
 * @author name：yuruixin
 */
function addUserCtrl($http, $scope, $modal, $q, $rootScope) {
	$scope.$emit('deptSelectTo-parent', null, null, null);
	deptCtrlFlag = "addUser";
	var queryAllGroupResult = pageService($http, $q, 'UserController',
			'queryAllGroup', null, null, null);
	queryAllGroupResult.then(function(success) {
		$scope.photoInit();
		var groupData = StrParesJSON(success).result
		for(var i = 0; i < groupData.length; i++){
			if(groupData[i].id == 0){
				groupData[i].checked = true;
			}else{
				groupData[i].checked = false;
			}
		}
		$scope.groupList = groupData ;
		
	}, function(error) {
		console.info(error);
	});
    $rootScope.orgDataToken;

	//默认普通用户
	$scope.addUserInfoType = "NORMAL";
	/**
	 * Description：添加用户(确定按钮触发)
	 *
	 * @author name：yuruixin
	 */
	$scope.addUser = function() {
		//1.表单基本验证
		if ($scope.addUserForm.$valid && !$scope.userIsExit && !$scope.userIsExitYes) {
            if($scope.addUserInfo.reportGroupId == null || $scope.addUserInfo.reportGroupId == ""){
                $scope.reportGroupError = true;
                return;
            }
			
			//2.判断要添加的用户有没有重复
			var account = mergeJson("userAccount", $scope.addUserInfo.account);
			var requestUserControllerData = mergeReauestData('UserController',
					'queryUserIdByAccont', account);
			var responseQueryUserResult = sendPost($http, requestUserControllerData,$q);
			responseQueryUserResult.then(function(success) {
				
				var user = JSON.parse(success).result;
				//已存在此用户
				if(user.isEnabled == true){
					//此用户已经存在
					$scope.userIsExit = true;
				}else if(user.isEnabled == false){
					//此用户已经存在，你可以进行恢复
					$scope.userIsExitYes = true;
				}else{
					//3.判断邮件是否已经注册
					var emailJson = mergeJson('email',$scope.addUserInfo.email);
					var emailData = mergeReauestData('RegisterController','verifyEmail', emailJson);
					var emailResult = sendPost($http, emailData, $q);
					emailResult.then(function(success){
						//表示此邮件没有被注册过，可以继续使用
						if(JSON.parse(success).result == true){
							deptCtrlFlag = "";
							// 获取复选框信息
							var groupIdJson = [];
							var group = document.getElementsByName('child_group_add');
							var groupIdJsonIndex = 0;
							for (var i = 0; i < group.length; i++) {
								if (group[i].checked) {
									groupIdJson[groupIdJsonIndex] = group[i].value;
									groupIdJsonIndex++;
								}
							}
							$scope.addUserInfo.groupId = groupIdJson;
							$scope.addUserInfo.dept = {};
							$scope.addUserInfo.dept.id = $scope.deptIdAdd;
                            console.log("deptId: " + $scope.deptIdAdd);
                            $scope.addUserInfo.dept.orgLevelType = $scope.orgLevelType;
							$scope.addUserInfo.sysUserPwd = $scope.sysUserPwd;
							$scope.addUserInfo.sysUserPhoto = $scope.userPhoto;
							console.log($scope.addUserInfo.sysUserPhoto);
							if ($scope.addUserInfoType != null
									&& $scope.addUserInfoType != undefined) {
								
								$scope.addUserInfo.typeCode = $scope.addUserInfoType;
							}
							if ($scope.addUserInfo.sex != null
									&& $scope.addUserInfo.sex.code != undefined) {
								
								$scope.addUserInfo.sexCode = $scope.addUserInfo.sex.code;
							}
							if ($scope.addUserInfo.idType != null
									&& $scope.addUserInfo.idType.code != undefined) {
								
								$scope.addUserInfo.idTypeCode = $scope.addUserInfo.idType.code;
							}
							if ($scope.addUserInfo.postalCode != null
									&& /\D/gi.test($scope.addUserInfo.postalCode)) {
								$scope.addUserInfo.postalCode = null;
							}
							var paramsJson = JSON.stringify($scope.addUserInfo);

							var updateUserResult = pageService($http, $q, 'UserController',
									'addUserCompany', null, null, paramsJson);

							console.log("updateUserResult:" + JSON.stringify(updateUserResult));

							updateUserResult.then(function(success) {
								$scope.addSuccessFlag = StrParesJSON(success).result;
								
								$scope.queryUser("true");

								$scope.$emit('to-parent');
								$modal({
									scope : $scope,
									title : "提示",
									templateUrl : 'packages/sys/views/user/tip.html',
									content : '用户添加成功',
									show : true,
									backdrop : "static"
								});
								$scope.addUserForm=undefined
							}, function(error) {
								$scope.errorMsg = JSON.parse(error).errMsg;
								$scope.userErrMsg = true;
								$scope.userAccout = true;
							});
						}else{
							$scope.emailAlreadyExit = true;
						}
					})
				}
			})
		} else {
			//如果
			if($scope.userIsExit != undefined && !$scope.userIsExit){
				$scope.addUserForm.submitted = true;
			}
		}
	}
	$scope.addHidemodal = function(){
		if($scope.addUserForm.$pristine){//判断是否有修改 如果为true代表没有修改执行关闭
			$scope.$emit('to-parent');
			$scope.addUserForm = undefined			
			$scope.clearFlag();
			}else{//如果是false 代表有修改 显示提示框
				$rootScope.closeModel.$promise.then($rootScope.closeModel.show);	
			}
		}			
	$rootScope.$on('hideModel', function(event, userhideModal) {//确定丢弃输入的修改内容后需要执行的方法
		if ($scope.addUserForm != undefined) {			
				if (userhideModal) {
					if ($scope.addUserForm.account.$error.required ||  $scope.addUserForm.dept.$error.required ||  $scope.addUserForm.name.$error.required ||
						 $scope.addUserForm.youjian.$error.required ||	 $scope.addUserForm.rePassword.$error.required || $scope.addUserForm.password.$error.required) {

						    $scope.addUserForm.submitted = true;

					}else if( $scope.addUserForm.email ||  $scope.addUserForm.isSame || $scope.addUserForm.tel ||  $scope.addUserForm.fixedTel || $scope.addUserForm.postalCode || $scope.emailAlreadyExit){
					}else{

						$scope.addUser(); // 执行提交方法						
					}				
					
				}else{
					$scope.$emit('to-parent');
					$scope.addUserForm = undefined					
					$scope.clearFlag();
				};
		};
	})
	
	$scope.checkPassword = function() {
		if ($scope.rpassword != undefined){
			if ($scope.sysUserPwd != null && $scope.rpassword == $scope.sysUserPwd.password) {
				$scope.addUserForm.isSame = false;
			} else {
				$scope.addUserForm.isSame = true;
			}
		}else{
			$scope.addUserForm.isSame = false;
		}
	}
	
	$scope.hideErrorinfo=function(){
		$scope.userIsExit = false;
		$scope.userIsExitYes = false;
		$scope.addUserForm.submitted = false;
	}
	
	//确认密码获取焦点，隐藏错误信息
	$scope.hideErrorPsw = function(){
		$scope.addUserForm.isSame = false;
		$scope.addUserForm.submitted = false;
	}
	//邮政编码获得焦点隐藏错误信息
	$scope.checkFocus = function(){
		$scope.addUserForm.postalCode = false;
		$scope.addUserForm.submitted = false;
	}
	
	$scope.hideErrorFexTel = function(){
		$scope.addUserForm.fixedTel = false;
		$scope.addUserForm.submitted = false;
	}
	/*
	 * 离开焦点判断邮件格式
	 */
	$scope.checkEmail = function() {
		$scope.addUserForm.email = checkEmail($scope.addUserInfo.email);
	}
	/*
	 * 邮件改变判断邮件格式
	 */
	$scope.emailChange = function() {
		$scope.addUserForm.email = changeEmail($scope.addUserInfo.email);
	}
	
	$scope.emailFocus = function(){
		$scope.emailAlreadyExit = false;
		$scope.addUserForm.submitted = false;
	}

	// 值发生改变验证手机号
	$scope.checkPhone = function() {
		$scope.addUserForm.tel = checkPhone($scope.addUserInfo.tel);
	}

	$scope.phoneChange = function() {
		$scope.addUserForm.tel = changePhone($scope.addUserInfo.tel);
	}

	$scope.hideErrorPhone = function() {
		$scope.addUserForm.tel = false;
		$scope.addUserForm.submitted = false;
	}

	// 失去焦点检验固定电话
	$scope.checkFexTel = function() {
		$scope.addUserForm.fixedTel = checkFaxTel($scope.addUserInfo.fixedTel);
	}
	// 值发生改变验证固定电话
	$scope.changeFexTel = function() {
		$scope.addUserForm.fixedTel = changeFexTel($scope.addUserInfo.fixedTel);
	}

	// 失去焦点检验邮编
	$scope.checkPostalCode = function() {
		$scope.addUserForm.postalCode = checkNumber($scope.addUserInfo.postalCode);
	}
	// 值发生改变验证邮编
	$scope.changePostalCode = function() {
		$scope.addUserForm.postalCode = false;
		$scope.addUserForm.submitted = false;
	}

	$scope.today = function() {
		$scope.addUserInfo = {};
		$scope.addUserInfo.birthday = null;
	};
	$scope.today();
	$scope.clear = function() {
		$scope.addUserInfo.birthday = null;
	};
	$scope.disabled = function(date, mode) {
		return;
	};
	$scope.toggleMin = function() {
		$scope.minDate = new Date();
	};
	$scope.toggleMin();
	$scope.open = function($event) {
		$event.preventDefault();
		$event.stopPropagation();
		$scope.opened = true;
	};
	$scope.dateOptions = {
		formatYear : 'yy',
		startingDay : 1
	};
	$scope.format = 'yyyy-MM-dd';

	/**
	 * Description：接收父controller传递的照片文件字符串
	 * 
	 * @author name：yuruixin
	 */

	$scope.$on('userPhoto.photoStr', function(event, photoStr) {
		$scope.userPhotoShow = photoStr == null ? null : photoStr;
		$("#addUserPhoto").attr("src", $scope.userPhotoShow);
		$scope.photoAddShow = true;
		$scope.userPhoto.photoStr = photoStr;

		$scope.iconError = false;
		var icon = document.getElementById("iconId");
		addClass(icon, "ng-hide");
	});
	$scope.iconError = false;
	$scope.$on('userPhoto.error', function(event, photoStr) {
		$scope.iconError = true;
		var icon = document.getElementById("iconId");
		removeClass(icon, "ng-hide");
	});
}

/**
 * Description：机构选择窗口controller
 * 
 * @author name：yuruixin
 */
function deptSelectCtrl($scope, $modal,$http,$q) {
	$scope.deptName = null;
	$scope.deptId = null;
	$scope.clickDept = function(deptName, deptId, orgLevelType,eventTarget){
		if ($scope.deptName==deptName) {
               angular.element(eventTarget.currentTarget).addClass('tree-selected')
		}else{
			angular.element('.treeLink').removeClass('tree-selected')

		};
		$scope.deptName = deptName;
		$scope.deptId = deptId;
        $scope.orgLevelType = orgLevelType;
        console.log("org _____ deptId : " + deptId);
        console.log("org level type : " + orgLevelType);
	}
	$scope.clickDeptConfirm = function() {
		if ((deptCtrlFlag == "updateUser" || deptCtrlFlag == "addUser")
				&& $scope.deptName != null) {
			document.getElementById("dept").value = $scope.deptName;
		}
        $scope.$emit('deptSelectToReportGroup-parent', $scope.deptName, $scope.deptId, $scope.orgLevelType);
		$scope.$emit('deptSelectTo-parent', $scope.deptName, $scope.deptId, $scope.orgLevelType);
	}
	$scope.clearDept = function() {
		if (deptCtrlFlag != "updateUser" && deptCtrlFlag != "addUser") {
			$scope.$emit('deptSelectTo-parent', null, null, null);
		}
	}
}

/**
 * 树模型接受控制层 -------没有完全展开的树模型
 * 
 * @param $scope
 * @param $http
 * @param $q
 */
function FileUserStyle($scope, $http, $q,$state) {
	$scope.getDeptTreeInUser = function() {
		getTreeAndView($scope, $http, $q, 'DeptController', 'getDeptTreeParent');
	}
	$scope.getTreeUserRefresh = function() {		
		$state.reload();
	}	
	$scope.$on('re-totreeemit',function(){
		getTreeAndView($scope, $http, $q, 'DeptController', 'getDeptTreeParent');

	})
	$scope.getDeptTreeInUser();
}

function hasClass(obj, cls) {
	return obj.className.match(new RegExp('(\\s|^)' + cls + '(\\s|$)'));
}
function addClass(obj, cls) {
	if (!hasClass(obj, cls))
		obj.className += " " + cls;
}
function removeClass(obj, cls) {
	if (hasClass(obj, cls)) {
		var reg = new RegExp('(\\s|^)' + cls + '(\\s|$)');
		obj.className = obj.className.replace(reg, ' ');
	}
}
angular.module("ngLocale", [],[
		"$provide",
		function($provide) {
			var PLURAL_CATEGORY = {
				ZERO : "zero",
				ONE : "one",
				TWO : "two",
				FEW : "few",
				MANY : "many",
				OTHER : "other"
			};
			$provide.value("$locale", {
				"Today" : "\u4eca\u5929",
				"CLEAR" : "\u6e05\u7a7a",
				"CLOSE" : "\u5173\u95ed",
				"DATETIME_FORMATS" : {
					"AMPMS" : [ "\u4e0a\u5348", "\u4e0b\u5348" ],
					"DAY" : [ "\u661f\u671f\u65e5", "\u661f\u671f\u4e00",
							"\u661f\u671f\u4e8c", "\u661f\u671f\u4e09",
							"\u661f\u671f\u56db", "\u661f\u671f\u4e94",
							"\u661f\u671f\u516d" ],
					"MONTH" : [ "1\u6708", "2\u6708", "3\u6708", "4\u6708",
							"5\u6708", "6\u6708", "7\u6708", "8\u6708",
							"9\u6708", "10\u6708", "11\u6708", "12\u6708" ],
					"SHORTDAY" : [ "\u5468\u65e5", "\u5468\u4e00",
							"\u5468\u4e8c", "\u5468\u4e09", "\u5468\u56db",
							"\u5468\u4e94", "\u5468\u516d" ],
					"SHORTMONTH" : [ "1\u6708", "2\u6708", "3\u6708",
							"4\u6708", "5\u6708", "6\u6708", "7\u6708",
							"8\u6708", "9\u6708", "10\u6708", "11\u6708",
							"12\u6708" ],
					"fullDate" : "y\u5e74M\u6708d\u65e5EEEE",
					"longDate" : "y\u5e74M\u6708d\u65e5",
					"medium" : "yyyy-M-d ah:mm:ss",
					"mediumDate" : "yyyy-M-d",
					"mediumTime" : "ah:mm:ss",
					"short" : "yy-M-d ah:mm",
					"shortDate" : "yy-M-d",
					"shortTime" : "ah:mm"
				},
				"NUMBER_FORMATS" : {
					"CURRENCY_SYM" : "\u00a5",
					"DECIMAL_SEP" : ".",
					"GROUP_SEP" : ",",
					"PATTERNS" : [ {
						"gSize" : 3,
						"lgSize" : 3,
						"macFrac" : 0,
						"maxFrac" : 3,
						"minFrac" : 0,
						"minInt" : 1,
						"negPre" : "-",
						"negSuf" : "",
						"posPre" : "",
						"posSuf" : ""
					}, {
						"gSize" : 3,
						"lgSize" : 3,
						"macFrac" : 0,
						"maxFrac" : 2,
						"minFrac" : 2,
						"minInt" : 1,
						"negPre" : "(\u00a4",
						"negSuf" : ")",
						"posPre" : "\u00a4",
						"posSuf" : ""
					} ]
				},
				"id" : "zh-cn",
				"pluralCat" : function(n) {
					return PLURAL_CATEGORY.OTHER;
				}
			});
		} ]);
