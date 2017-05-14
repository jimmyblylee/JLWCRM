/**
 * 用户组管理 ---控制器
 */
var updateGroup;
var deleteGroup;
function GrouptableCtrl($scope, $filter, $http, $q, $modal, $log, $timeout,$rootScope) {
	$scope.itemsPerPage = itemsPerPage;
	$scope.Pagenum = Pagenum;
	$scope.selected = '';
	$scope.selectGroupIsEnabled = "true";
	var ispc=IsPC();
	/**
	 * Description：获取有效性
	 * 
	 * @author name：yuruixin
	 */
	$scope.IsEnabledJson = null;
	$scope.isEnabled = "true";
	if ($scope.IsEnabledJson == null) {
		var responseDictResult = getSelectValueByDictList($http, $q,
				'STATUS_TYPE', 'STATUS_CODE');
		responseDictResult.then(function(success) {
			$scope.IsEnabledJson = StrParesJSON(success).result;
		}, function(error) {
			console.info(error);
		});
	}
	$scope.globals = {
		"isEnabled" : "true"
	};
	$scope.groupIsEnabled = "true";

	$scope.setEnabled = function(selectCode) {
		$scope.groupIsEnabled = selectCode;
	}

	$scope.closeAddGroup = function() {
		addGroupWindow.$promise.then(addGroupWindow.hide);
		$scope.formGroupData.name = "";
		$scope.formGroupData.desc = "";
		$scope.formGroupData.submitted = false;
	}
	
	//分页请求  --》改变分页参数
	$scope.changPageValue = function(){
		$scope.currentPage = 1;
		$scope.queryGroupResult("true");
	}
	
	// 查询所有用户组列表
	$scope.queryGroupResult = function(isEnabled, type) {

		if (isEnabled == "true") {

			$scope.groupIsEnabled = "true";
		}

		if ($scope.groupIsEnabled == "true" && isEnabled == "false") {
			$scope.setEnabled("true");
		} else if ($scope.groupIsEnabled != "true" && isEnabled == "false") {
			$scope.setEnabled("false");
		}

		if ($scope.updateGloupButton && isEnabled == "true") {

			$scope.setEnabled("true");

		} else if ($scope.recoverGloupButton && isEnabled == "true") {
			$scope.setEnabled("false");
		}

		if ($scope.groupIsEnabled == "true") {
			$scope.updateGloupButton = true;
			$scope.recoverGloupButton = false;
		} else {
			$scope.updateGloupButton = false;
			$scope.recoverGloupButton = true;
		}

		var pageQueryParams = {};

		pageQueryParams["groupIsEnabled"] = $scope.groupIsEnabled;

		if ($scope.selectGroupConditions != undefined) {

			pageQueryParams["groupName"] = $scope.selectGroupConditions;

		} else {
			pageQueryParams["groupName"] = "";
		}
		if ($scope.currentPage == undefined) {
			$scope.currentPage = 1;
		}
		pageQueryParams = ObjParesJSON(pageQueryParams);
		if (type == "1") {
			$scope.currentPage = 1;
		}
		if(!ispc){
			$scope.itemsPerPage=30;
			$scope.currentPage = 1;			
		}
		var queryGroupResult = pageService($http, $q, 'GroupController',
				'queryAllSysGroup', $scope.currentPage, $scope.itemsPerPage,
				pageQueryParams);
		queryGroupResult.then(function(queryGroupValue) {
			queryGroupValue = JSON.parse(queryGroupValue);
			var GroupArray = queryGroupValue.result;
			$scope.GroupArrayLists = GroupArray;
			$scope.totalItems = queryGroupValue.total;
			$scope.removeCheckBox();
			scrollwatch($scope, $timeout);
		}), function(error) {
			console.info(error);
		};
	}
	$scope.queryGroupResultApp = function(isEnabled, type) {

		if (isEnabled == "true") {

			$scope.groupIsEnabled = "true";
		}

		if ($scope.groupIsEnabled == "true" && isEnabled == "false") {
			$scope.setEnabled("true");
		} else if ($scope.groupIsEnabled != "true" && isEnabled == "false") {
			$scope.setEnabled("false");
		}

		if ($scope.updateGloupButton && isEnabled == "true") {

			$scope.setEnabled("true");

		} else if ($scope.recoverGloupButton && isEnabled == "true") {
			$scope.setEnabled("false");
		}

		if ($scope.groupIsEnabled == "true") {
			$scope.updateGloupButton = true;
			$scope.recoverGloupButton = false;
		} else {
			$scope.updateGloupButton = false;
			$scope.recoverGloupButton = true;
		}

		var pageQueryParams = {};

		pageQueryParams["groupIsEnabled"] = $scope.groupIsEnabled;

		if ($scope.selectGroupConditions != undefined) {

			pageQueryParams["groupName"] = $scope.selectGroupConditions;

		} else {
			pageQueryParams["groupName"] = "";
		}
		if ($scope.currentPage == undefined) {
			$scope.currentPage = 1;
		}
		pageQueryParams = ObjParesJSON(pageQueryParams);
		if (type == "1") {
			$scope.currentPage = 1;
		}
		$scope.itemsPerPage=5;
		var totalItemsAPP=$scope.totalItems;
		var array=$scope.GroupArrayLists;
		$scope.totalItemsAPP=Math.ceil(totalItemsAPP/5);
		$scope.currentPage=(array.length/5)+1;
		if ($scope.currentPage <= $scope.totalItemsAPP) {
			var queryGroupResult = pageService($http, $q, 'GroupController',
						'queryAllSysGroup', $scope.currentPage, $scope.itemsPerPage,
						pageQueryParams);
				queryGroupResult.then(function(queryGroupValue) {
					queryGroupValue = JSON.parse(queryGroupValue);	
					$scope.GroupArrayLists =array.concat(queryGroupValue.result); 		
					$scope.removeCheckBox();
					scrollwatch($scope, $timeout);
				}), function(error) {
					console.info(error);
				};

		}
	
	}
	window.onscroll = function(){
		if (!ispc) {
    	isscrollbottom=scrollbottom();
      　if(isscrollbottom){
      		$scope.queryGroupResultApp()
        }
    }

   };
	// 初始化
	$scope.queryGroupResult("true");
	$scope.closeupdateRolePopup = function() {		
		updateRolePopup.$promise.then(updateRolePopup.hide);
		fullscreenRemove();
	};
	// 修改权限弹窗
	var updateRolePopup = $modal({
		scope : $scope,
		templateUrl : 'packages/sys/views/group/group_power.html',
		show : false,
		backdrop : "static"
	});
	var roleListByGroupId;

	$scope.groupRole = function(userGroup) {
		$scope.removeCheckBox();
		updateRolePopup.$promise.then(updateRolePopup.show);
		roleListByGroupId = userGroup;
		$scope.roleGroup = userGroup;
		var queryRolesByGroupIdJsonParams = mergeJson('queryRolesByGroupId',
				userGroup);
		var queryRolesData = mergeReauestData('GroupController', 'queryRoles',
				queryRolesByGroupIdJsonParams);
		var queryRolesByGroupIdResult = sendPost($http, queryRolesData, $q);
		queryRolesByGroupIdResult.then(function(roleList) {
			roleList = JSON.parse(roleList);
			$scope.groupRoleList = roleList.result;
		}, function(error) {
		});
	}
	// 删除原来的角色信息，然后根据roleIds进行更新
	$scope.replaceRolesToGroup = function() {
		var groupRoleValue = "";
		var groupRoleId = document.getElementsByName("groupRole");

		for (var i = 0; i < groupRoleId.length; i++) {
			if (groupRoleId[i].checked) {
				groupRoleValue = groupRoleValue + groupRoleId[i].value + "_";
				groupRoleValue = groupRoleValue.replace("/", "");
			}
		}
		var rolesToGroupJsonParams = mergeJson('queryRolesByGroupId',
				roleListByGroupId);
		var rolesByGroupIdJsonParams = mergeJson('replaceRolesToGroup',
				groupRoleValue);
		var replaceRolesToGroupJsonParams = {};
		$.extend(replaceRolesToGroupJsonParams, rolesByGroupIdJsonParams,
				rolesToGroupJsonParams);
		var rolesToGroupData = mergeReauestData('GroupController',
				'replaceRolesToGroup', replaceRolesToGroupJsonParams);
		var rolesToGroupResult = sendPost($http, rolesToGroupData, $q);
		rolesToGroupResult.then(function(success) {
			updateRolePopup.$promise.then(updateRolePopup.hide);

			$scope.queryGroupResult("true");

			$modal({
				scope : $scope,
				title : "提示",
				templateUrl : 'packages/sys/views/group/tip.html',
				content : '分配角色成功',
				show : true,
				backdrop : "static"
			});

		}, function(error) {

		});
	}
	// 查看用户组详情弹窗
	var selectGroupPopup = $modal({
		scope : $scope,
		templateUrl : 'packages/sys/views/group/group_info.html',
		show : false,
		backdrop : "static"
	});
	$scope.closeGroupPopup = function() {		
		selectGroupPopup.$promise.then(selectGroupPopup.hide);
		fullscreenRemove();
	};
	$scope.selectGroup = [];
	// 用户组赋权
	$scope.selectInfo = function(userGroup) {
		$scope.removeCheckBox();
		$scope.selectGroup = userGroup;
		selectGroupPopup.$promise.then(selectGroupPopup.show);
		$scope.groupInfo = userGroup;
		var queryRolesByGroupIdJsonParams = mergeJson('queryRolesByGroupId',
				userGroup);
		var queryRolesData = mergeReauestData('GroupController',
				'queryRolesByGroupId', queryRolesByGroupIdJsonParams);
		var queryRolesResult = sendPost($http, queryRolesData, $q);
		queryRolesResult.then(function(queryRolesList) {
			queryRolesList = JSON.parse(queryRolesList);
			$scope.roleList = queryRolesList.result;
		}, function(error) {
		});
	};
	// 修改用户组弹窗
	var updateGroupWindow = $modal({
		scope : $scope,
		templateUrl : 'packages/sys/views/group/group_update.html',
		show : false,
		container : '#GrouptableCtrId',
		controller : 'GroupSubmodal',
		backdrop : "static"
	});
	$scope.updateGroup = [];
	$scope.update = function(userGroup, indexNum) {
		$scope.updateGroup = userGroup;
		updateGroupWindow.$promise.then(updateGroupWindow.show);
		$scope.indexNum = indexNum;
	};

	$scope.$on('to-parentUpdate', function(event, editGroupForm, updateGroup) {
		$scope.editGroupForm = editGroupForm;
		$scope.updateGroupToFather = updateGroup;
		$scope.groupUpdate();
	})
$scope.$on('tocolseUpdate', function(event,editGroupForm, updateGroup) {
	 $scope.editGroupForm = editGroupForm;
	 $scope.updateGroupToFather = updateGroup;
	 if ($scope.editGroupForm!=undefined) {
		if($scope.editGroupForm.$pristine){//判断是否有修改 如果为true代表没有修改执行关闭		
			updateGroupWindow.$promise.then(updateGroupWindow.hide);
			$scope.editGroupForm = undefined;
			fullscreenRemove();	      
		}else{//如果是false 代表有修改 显示提示框
			$rootScope.closeModel.$promise.then($rootScope.closeModel.show);	
		}


	 };		
		
	})


	$rootScope.$on('hideModel', function(event, hideModel) {//确定丢弃输入的修改内容后需要执行的方法	
		if ($scope.editGroupForm != undefined && $scope.updateGroupToFather!=undefined) {
			if (hideModel ) {					
					$scope.groupUpdate(); // 执行提交方法
			}else{
				updateGroupWindow.$promise.then(updateGroupWindow.hide);
				$scope.editGroupForm = undefined;
				fullscreenRemove();	           
			};
		};		
		
	})

	// 修改用户组
	$scope.groupUpdate = function() {
		$scope.removeCheckBox();
		if ($scope.editGroupForm.$valid) {
			var updateGroipJsonParams = mergeJson('update',
					$scope.updateGroupToFather);
			var updateGroupData = mergeReauestData('GroupController',
					'updateSysGroup', updateGroipJsonParams);
			var updateGroupResult = sendPost($http, updateGroupData, $q);
			updateGroupResult.then(function(success) {
				updateGroupWindow.$promise.then(updateGroupWindow.hide);
				$scope.queryGroupResult("true");
				$modal({
					scope : $scope,
					title : "提示",
					templateUrl : 'packages/sys/views/group/tip.html',
					content : '修改用户组成功',
					show : true,
					backdrop : "static"
				});
				$scope.indexNum = undefined;
				$scope.editGroupForm = undefined;		     
			}, function(error) {
			});
		} else {
			$scope.editGroupForm.submitted = true;
		}

	}
	// 添加用户组弹窗
	var addGroupWindow = $modal({
		scope : $scope,
		templateUrl : 'packages/sys/views/group/group_add.html',
		show : false,
		container : '#GrouptableCtrId',
		controller : 'GroupSubmodal',
		backdrop : "static"
	});

	$scope.showGroupModal = function() {
		$scope.removeCheckBox();
		addGroupWindow.$promise.then(addGroupWindow.show);
	};

	$scope.$on('to-parent', function(event, groupForm, formGroupData) {
		$scope.groupForm = groupForm;
		$scope.globaLData = formGroupData;
		$scope.addGroup(); // 执行提交方法
	})
	$scope.$on('tocolse', function(event, groupForm, formGroupData) {
	$scope.groupForm = groupForm;
	$scope.formGroupData = formGroupData;	
	if ($scope.groupForm!=undefined) {
	  if(groupForm.$pristine){//判断是否有修改 如果为true代表没有修改执行关闭		
			addGroupWindow.$promise.then(addGroupWindow.hide);
			$scope.groupForm = undefined;
			fullscreenRemove();	       
		}else{//如果是false 代表有修改 显示提示框
			$rootScope.closeModel.$promise.then($rootScope.closeModel.show);	
		}
	};
	})
	$rootScope.$on('hideModel', function(event, hideModel) {//确定丢弃输入的修改内容后需要执行的方法
	if ($scope.groupForm != undefined) {			
			if (hideModel ) {					
					$scope.addGroup(); // 执行提交方法
			}else{
				addGroupWindow.$promise.then(addGroupWindow.hide);
				$scope.groupForm = undefined;
				 $scope.formGroupData.name = undefined;
	            $scope.formGroupData.desc = undefined;
	            fullscreenRemove();
			};
		};
	})

	// 添加用户组
	$scope.formGroupData = {}; // 定义添加用户组变量
	$scope.addGroup = function() {
		if ($scope.groupForm.$valid) {
			var addGroupJsonParams = mergeJson('addGroup', $scope.formGroupData);
			var createGroupData = mergeReauestData('GroupController',
					'createSysGroup', addGroupJsonParams);
			var createGroupResult = sendPost($http, createGroupData, $q);
			createGroupResult.then(function(success) {
				addGroupWindow.$promise.then(addGroupWindow.hide);
				$scope.queryGroupResult("true");
				$scope.formGroupData.name = "";
				$scope.formGroupData.desc = "";
				$modal({
					scope : $scope,
					title : "提示",
					templateUrl : 'packages/sys/views/group/tip.html',
					content : '添加用户组成功',
					show : true,
					backdrop : "static"
				});
				$scope.groupForm = undefined;	           
	            $scope.formGroupData.name = undefined;
	            $scope.formGroupData.desc = undefined;
			}, function(error) {
				console.info(error);
			});
		} else {
			$scope.formGroupData.submitted = true;
		}

	}
	// 删除用户组弹窗
	var delWindow = $modal({
		scope : $scope,
		templateUrl : 'packages/sys/views/group/group_delete.html',
		show : false,
		backdrop : "static"
	});
	$scope.deletegroup = function(userGroup) {
		delWindow.$promise.then(delWindow.show);
		deleteGroup = userGroup;
	};
	// 删除用户组
	$scope.groupdelete = function() {
		$scope.removeCheckBox();
		var delGroupJsonParams = mergeJson('deleteGroup', deleteGroup);
		var removeGroupData = mergeReauestData('GroupController',
				'removeSysGroup', delGroupJsonParams);
		var removeGroupResult = sendPost($http, removeGroupData, $q);
		removeGroupResult.then(function(success) {

			delWindow.$promise.then(delWindow.hide);

			if ($scope.GroupArrayLists.length == 1 && $scope.currentPage != 1) {
				$scope.currentPage = $scope.currentPage - 1;
				$scope.queryGroupResult("true");
			} else {
				$scope.queryGroupResult("true");
			}
			$scope.$parent.indexNum = "";
			$modal({
				scope : $scope,
				title : "提示",
				templateUrl : 'packages/sys/views/group/tip.html',
				content : '删除用户组成功',
				show : true,
				backdrop : "static"
			});
		})
	};
	// 恢复用户组弹窗
	var recoveWindow = $modal({
		scope : $scope,
		templateUrl : 'packages/sys/views/group/group_recove.html',
		show : false,
		backdrop : "static"
	});
	var recoveGroup;
	$scope.recovegroup = function(userGroup) {
		recoveWindow.$promise.then(recoveWindow.show);
		recoveGroup = userGroup;
	};
	// 恢复用户组
	$scope.groupRecove = function() {
		var recGroupJsonParams = mergeJson('recoveGroup', recoveGroup);
		var recoveGroupData = mergeReauestData('GroupController',
				'recoveSysGroup', recGroupJsonParams);
		var recoveGroupResult = sendPost($http, recoveGroupData, $q);
		recoveGroupResult.then(function(success) {
			recoveWindow.$promise.then(recoveWindow.hide);
			if ($scope.GroupArrayLists.length == 1 && $scope.currentPage != 1) {
				$scope.currentPage = $scope.currentPage - 1;
				$scope.queryGroupResult();
			} else {
				$scope.queryGroupResult();
			}
			$scope.queryGroupResult();
			$modal({
				scope : $scope,
				title : "提示",
				templateUrl : 'packages/sys/views/group/tip.html',
				content : '恢复用户组成功',
				show : true,
				backdrop : "static"
			});
		})
	};

	// 回车查询
	$scope.keyup = function($event) {
		if ($event.keyCode == 13) {
			$scope.queryGroupResult('1');
		}

	}
	// 复选框
	$scope.checkAll = function(cancel) {
		var groupDeleteList_check = document
				.getElementsByName("groupDeleteList");
		if (!groupDeleteList_check[0].checked) {
			for (var i = 1; i < groupDeleteList_check.length; i++) {
				groupDeleteList_check[i].checked = false;
			}
		} else {
			for (var i = 1; i < groupDeleteList_check.length; i++) {
				groupDeleteList_check[i].checked = true;
			}
		}

	}
	// 清空复选框
	$scope.removeCheckBox = function() {
		var groupDeleteList_check = document
				.getElementsByName("groupDeleteList");
		for (var i = 0; i < groupDeleteList_check.length; i++) {
			groupDeleteList_check[i].checked = false;
		}
	}
	// error弹窗
	var errorWindow = $modal({
		scope : $scope,
		templateUrl : 'packages/sys/views/group/group_error.html',
		show : false
	});
	$scope.errorGroup = function() {
		errorWindow.$promise.then(errorWindow.show);
	};
	// 批量删除弹窗
	var deleteAllWindow = $modal({
		scope : $scope,
		templateUrl : 'packages/sys/views/group/group_deleteAll.html',
		show : false,
		backdrop : "static"
	});
	$scope.deleteAllGroup = function() {
		deleteAllWindow.$promise.then(deleteAllWindow.show);
	};

	// 用于标记是否把整个页面都删除了 --》用与删除或恢复后是否留在当前页面
	var flagDeleteAll = 0;

	// 批量删除
	$scope.deleteGroupAll = function(cancel) {
		$scope.deleteGroupCheckList = "";
		flagDeleteAll = 0;
		var deleteGroupList = document.getElementsByName("groupDeleteList");
		for (var i = 1; i < deleteGroupList.length; i++) {
			if (deleteGroupList[i].checked) {

				$scope.deleteGroupCheckList = $scope.deleteGroupCheckList
						+ deleteGroupList[i].value + "_";
				flagDeleteAll++;
			}
		}
		if ($scope.deleteGroupCheckList == "") {
			$scope.errorGroup();
		} else {
			$scope.deleteAllGroup();
		}

	}
	$scope.deleteGroupAllForList = function(cancel) {
		var deleteGroupCheckListJsonParams = mergeJson('deleteGroupCheckList',
				$scope.deleteGroupCheckList);
		var deleteGroupCheckListData = mergeReauestData('GroupController',
				'deleteGroupList', deleteGroupCheckListJsonParams);
		var deleteGroupCheckListResult = sendPost($http,
				deleteGroupCheckListData, $q);
		deleteGroupCheckListResult.then(function(deleteGroupList) {

			if ($scope.GroupArrayLists.length == flagDeleteAll
					&& $scope.currentPage != 1) {
				$scope.currentPage = $scope.currentPage - 1;
				$scope.queryGroupResult("true");
			} else {
				$scope.queryGroupResult("true");
			}
			$scope.removeCheckBox();
			deleteAllWindow.$promise.then(deleteAllWindow.hide);
			$modal({
				scope : $scope,
				title : "提示",
				templateUrl : 'packages/sys/views/group/tip.html',
				content : '批量删除用户组成功',
				show : true,
				backdrop : "static"
			});
		}, function(error) {
		});
	}

	// 批量恢复弹窗
	var recoveAllWindow = $modal({
		scope : $scope,
		templateUrl : 'packages/sys/views/group/group_recoveAll.html',
		show : false,
		backdrop : "static"
	});

	$scope.recoveAllGroup = function() {
		recoveAllWindow.$promise.then(recoveAllWindow.show);
	};
	// 批量恢复

	var flagRecoveAll = 0;

	$scope.recoveGroupAll = function(cancel) {
		var flagRecoveAll = 0;
		$scope.deleteGroupCheckList = "";
		var deleteGroupList = document.getElementsByName("groupDeleteList");
		for (var i = 1; i < deleteGroupList.length; i++) {
			if (deleteGroupList[i].checked) {
				$scope.deleteGroupCheckList = $scope.deleteGroupCheckList
						+ deleteGroupList[i].value + "_";

				flagRecoveAll++;
			}
		}
		if ($scope.deleteGroupCheckList == "") {
			$scope.errorGroup();
		} else {
			$scope.recoveAllGroup();
		}
	}
	$scope.recoveGroupAllForList = function(cancel) {
		var recoveGroupCheckListJsonParams = mergeJson('recoveGroupCheckList',
				$scope.deleteGroupCheckList);
		var recoveGroupCheckListData = mergeReauestData('GroupController',
				'recoveGroupList', recoveGroupCheckListJsonParams);
		var recoveGroupCheckListResult = sendPost($http,
				recoveGroupCheckListData, $q);
		recoveGroupCheckListResult.then(function() {
			if ($scope.GroupArrayLists.length == flagRecoveAll
					&& $scope.currentPage != 1) {

				$scope.currentPage = $scope.currentPage - 1;

				$scope.queryGroupResult("false");
			} else {
				$scope.queryGroupResult("false");
			}
			$scope.removeCheckBox();
			recoveAllWindow.$promise.then(recoveAllWindow.hide);
			$modal({
				scope : $scope,
				title : "提示",
				templateUrl : 'packages/sys/views/group/tip.html',
				content : '批量恢复用户组成功',
				show : true,
				backdrop : "static"
			});
		}, function(error) {
		});
	}

}
function GroupSubmodal($scope, $modal, $http, $q) {
	$scope.addGroup = function() {
		$scope.$emit('to-parent', $scope.groupForm, $scope.formGroupData);		
	}
	$scope.hideModel = function() {
		$scope.$emit('tocolse', $scope.groupForm, $scope.formGroupData);			
	}
	$scope.groupUpdate = function() {
		$scope.$emit('to-parentUpdate', $scope.editGroupForm,$scope.updateGroup);}
	$scope.hideModelUpdate = function() {
		$scope.$emit('tocolseUpdate', $scope.editGroupForm, $scope.updateGroup);			
	}
	$scope.formGroupData.submitted = false;
	if ($scope.$parent.GroupArrayLists != ''
			&& $scope.$parent.GroupArrayLists != null
			&& $scope.$parent.GroupArrayLists != undefined
			&& $scope.$parent.indexNum != undefined) {
		$scope.updateGroup = {
			"id" : $scope.$parent.GroupArrayLists[$scope.$parent.indexNum].id,
			"name" : $scope.$parent.GroupArrayLists[$scope.$parent.indexNum].name,
			"desc" : $scope.$parent.GroupArrayLists[$scope.$parent.indexNum].desc,
			"isEnabled" : $scope.$parent.GroupArrayLists[$scope.$parent.indexNum].isEnabled
		}
	}

}