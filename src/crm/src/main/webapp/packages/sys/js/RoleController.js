var MetronicApp = angular.module("MetronicApp", []);
/**
 * 展示角色列表信息
 * 
 * @author liujie
 * @Email jie_liu1@asdc.com.cn
 */
MetronicApp.controller('RoleInfoCtrl',['$scope','$rootScope','$http','$q',
						'$filter',
						'$modal',
						'$timeout',
						function($scope,$rootScope,$http, $q, $filter, $modal, $timeout) {

							// 定义变量
							$scope.roleList = []; // 当前返回总数据
							$scope.Pagenum = Pagenum;
							$scope.itemsPerPage = itemsPerPage;// 当前页显示数据
							$scope.isBaseRoleFont = "true";
							var ispc=IsPC();

							// 定义弹窗页面
							var addRoleInfo = $modal({
								scope : $scope,
								templateUrl : 'packages/sys/views/role/add_role.html',
								show : false,
								container : '#roleInfoPage',
								controller : 'AddRoleInfo',
								backdrop : "static"
							});
							var editRoleInfo = $modal({
								scope : $scope,
								templateUrl : 'packages/sys/views/role/edit_role.html',
								show : false,
								container : '#roleInfoPage',
								controller : 'ViewRoleController',
								backdrop : "static"
							});
							var viewRoleInfo = $modal({
								scope : $scope,
								templateUrl : 'packages/sys/views/role/view_role.html',
								show : false,
								container : '#roleInfoPage',
								controller : 'ViewRoleController',
								backdrop : "static"
							});
							var viewUserRole = $modal({
								scope : $scope,
								templateUrl : 'packages/sys/views/role/add_user.html',
								show : false,
								container : '#roleInfoPage',
								controller : 'ViewUserRole',
								backdrop : "static"
							});
							var deleteRoleInfo = $modal({
								scope : $scope,
								templateUrl : 'packages/sys/views/role/delete_role.html',
								show : false,
								container : '#roleInfoPage',
								controller : 'DeleteRoleInfo',
								backdrop : "static"
							});
							var recoverRoleInfo = $modal({
								scope : $scope,
								templateUrl : 'packages/sys/views/role/recover_role.html',
								show : false,
								container : '#roleInfoPage',
								controller : 'RecoverRoleInfo',
								backdrop : "static"
							});
							var addPerMiss = $modal({
								scope : $scope,
								templateUrl : 'packages/sys/views/role/add_permissions.html',
								show : false,
								container : '#roleInfoPage',
								controller : 'AddPerMissCtrl',
								backdrop : "static"
							});

							// 初始化获取字典
							$scope.roleInfo = {
								roleStatus : "true"
							};
                            //获取字典信息-->状态类型
							$scope.getRoleIsEnabled = function() {
								if ($scope.roleIsEnabledJson == undefined){
									var responseDictResult = getSelectValueByDictList($http, $q,
											'STATUS_TYPE', 'STATUS_CODE');
									responseDictResult.then(function(success) {
									
									$scope.roleIsEnabledJson = StrParesJSON(success).result;
									}, function(error) {
										console.info(error);
									});
								}
							}
							$scope.getRoleIsEnabled();
							
							//刷新
							$scope.refreshUserList = function (){
								$scope.bigCurrentPage =1;
								$scope.roleInfo.queryParams =undefined;
								$scope.itemsPerPage = Pagenum[0];
							    $scope.roleInfo.roleStatus = "true";
								$scope.requestPageService("true");
							}

							$scope.changRolePage = function(){
								$scope.bigCurrentPage =1;
								$scope.requestPageService("true");
							}
							
							 var sysUserInfo = $rootScope.token.user.type;
							 $rootScope.sysUserType = $rootScope.token.user.type.code;	
							 
							 
							// 获取角色页面列表
							$scope.requestPageService = function(isEnabled) {
								document.getElementById("roleCheckParent").checked = false;
								var pageQuery, roleStatus, pageQueryParams = {};
								if ($scope.roleInfo.roleStatus == undefined) {
									$scope.roleInfo.roleStatus = "";
								}
								//按照id查询、添加、修改、删除过后，都应该是true。  ---id不变
								if($scope.roleInfo.roleStatus =="true" && isEnabled=="false"){
									$scope.roleInfo.roleStatus = "true";
								}else if($scope.roleInfo.roleStatus != "true" && isEnabled=="false"){
									$scope.roleInfo.roleStatus = "false";
								}
								if($scope.deleteRoleButton && isEnabled == "true"){
									$scope.roleInfo.roleStatus = "true";
								}else if($scope.recoverRoleButton && isEnabled == "true"){
									$scope.roleInfo.roleStatus = "false";
								}
								
								//分页的时候应该是，根据true和false来判断 了
								if($scope.deleteRoleButton && isEnabled == 'true'){
									$scope.roleInfo.roleStatus = "true";
								}
								if ($scope.roleInfo.roleStatus == "true") {
									$scope.deleteRoleButton = true;
									$scope.recoverRoleButton = false;
								} else {
									$scope.deleteRoleButton = false;
									$scope.recoverRoleButton = true;
								}
								if ($scope.roleInfo.queryParams == undefined) {
									$scope.roleInfo.queryParams = "";
								}
								if(!ispc){
										$scope.itemsPerPage=30;
										$scope.bigCurrentPage = 1;			
									}
								var roleInfoResult = pageService($http, $q,
										'RoleController', 'queryRoleInfo',
										$scope.bigCurrentPage,
										$scope.itemsPerPage,
										ObjParesJSON($scope.roleInfo));
								roleInfoResult
										.then(
												function(success) {
													var roleInfoResponse = StrParesJSON(success);
													$scope.roleList = roleInfoResponse.result;
													$scope.bigTotalItems = roleInfoResponse.total;

												}, function(error) {
													console.info(error);
												});
							}
							$scope.requestPageServiceApp = function(isEnabled) {
								document.getElementById("roleCheckParent").checked = false;
								var pageQuery, roleStatus, pageQueryParams = {};
								if ($scope.roleInfo.roleStatus == undefined) {
									$scope.roleInfo.roleStatus = "";
								}
								//按照id查询、添加、修改、删除过后，都应该是true。  ---id不变
								if($scope.roleInfo.roleStatus =="true" && isEnabled=="false"){
									$scope.roleInfo.roleStatus = "true";
								}else if($scope.roleInfo.roleStatus != "true" && isEnabled=="false"){
									$scope.roleInfo.roleStatus = "false";
								}
								if($scope.deleteRoleButton && isEnabled == "true"){
									$scope.roleInfo.roleStatus = "true";
								}else if($scope.recoverRoleButton && isEnabled == "true"){
									$scope.roleInfo.roleStatus = "false";
								}
								
								//分页的时候应该是，根据true和false来判断 了
								if($scope.deleteRoleButton && isEnabled == 'true'){
									$scope.roleInfo.roleStatus = "true";
								}
								if ($scope.roleInfo.roleStatus == "true") {
									$scope.deleteRoleButton = true;
									$scope.recoverRoleButton = false;
								} else {
									$scope.deleteRoleButton = false;
									$scope.recoverRoleButton = true;
								}
								if ($scope.roleInfo.queryParams == undefined) {
									$scope.roleInfo.queryParams = "";
								}
								$scope.itemsPerPage=5;
								var totalItemsAPP=$scope.bigTotalItems;
								var array=$scope.roleList;
								$scope.totalItemsAPP=Math.ceil(totalItemsAPP/5);
								$scope.bigCurrentPage=(array.length/5)+1;
								if ($scope.bigCurrentPage <= $scope.totalItemsAPP) {
									var roleInfoResult = pageService($http, $q,
											'RoleController', 'queryRoleInfo',
											$scope.bigCurrentPage,
											$scope.itemsPerPage,
											ObjParesJSON($scope.roleInfo));
									roleInfoResult
											.then(
													function(success) {
														var roleInfoResponse = StrParesJSON(success);																											
														$scope.roleList =array.concat(roleInfoResponse.result);
													}, function(error) {
														console.info(error);
													});
								}
								
							}
							$scope.bigCurrentPage = 1;
							$scope.requestPageService("true");
							
							scrollwatch($scope, $timeout);
							window.onscroll = function(){
								if (!ispc) {
							    	isscrollbottom=scrollbottom();
							      　if(isscrollbottom){
							      		$scope.requestPageServiceApp()
							        }
							    }

							   };	
							// 回车查询
							$scope.keyup = function($event) {
								if ($event.keyCode == 13) {
									$scope.requestPageService('1');
								}

							}
							$scope.addRoleInfo = function() {
								addRoleInfo.$promise.then(addRoleInfo.show);
							}						
							// 根据角色ID查询功能
							$scope.queryFuncByRoleId = function() {
								$scope.funcTree ="";
								var roleParams = mergeStr('roleId',
										$scope.addPerMissRoleId);
								var funcByRoleData = mergeReauestData(
										'RoleController', 'queryFuncByRoleId',
										roleParams);
								var funcResult = sendPost($http,
										funcByRoleData, $q);
								funcResult.then(function(success) {
									var funcResponse = StrParesJSON(success);
									var funcTrees = funcResponse.result;
									$scope.funcTree = funcTrees;
									$scope.expandedNodes = [$scope.funcTree[0]];
									$scope.$broadcast('to-child', $scope.expandedNodes);
									$scope.showSelected = function(sel) {
										$scope.selectedNode = sel;
									};
								}), function(error) {
									console.info(error);
								};
							}
							//权限设置弹框
							$scope.addPerMissButton = function(roleId) {
								$scope.addPerMissRoleId = roleId;
								addPerMiss.$promise.then(addPerMiss.show);
								$scope.queryFuncByRoleId();
							}

							$scope.closeaddPerMissButton = function() {								
								addPerMiss.$promise.then(addPerMiss.hide);
								fullscreenRemove();
							}	
							$scope.closeviewUserRole = function() {								
								viewUserRole.$promise.then(viewUserRole.hide);
								fullscreenRemove();
							}							
							// 隐藏弹窗
							$scope.hideWin = function() {
								addRoleInfo.$promise.then(addRoleInfo.hide);
								viewRoleInfo.$promise.then(viewRoleInfo.hide);
								editRoleInfo.$promise.then(editRoleInfo.hide);
								viewUserRole.$promise.then(viewUserRole.hide);
								deleteRoleInfo.$promise.then(deleteRoleInfo.hide);
								addPerMiss.$promise.then(addPerMiss.hide);
								recoverRoleInfo.$promise.then(recoverRoleInfo.hide);
							}

							$scope.closeviewRoleInfo = function() {		
									viewRoleInfo.$promise.then(viewRoleInfo.hide);
									fullscreenRemove();
								};

							// 批量删除角色
							$scope.deleteRoleList = function(operationParam) {
								var roleIdListJson = [];
								if (operationParam == 'deleteRoleList') {
									var roleIdList = document
											.getElementsByName('roleCheckChild');
									var userIdJsonIndex = 0;
									for (var i = 0; i < roleIdList.length; i++) {
										if (roleIdList[i].checked) {
											roleIdListJson[userIdJsonIndex] = parseInt(roleIdList[i].value);
											userIdJsonIndex++;
										}
									}
									$scope.deleteRoleIdList = roleIdListJson;
									if (roleIdListJson.length > 0) {
										$scope.warnMessage = "确认删除选中角色？";
										$scope.confirmShow = true;
										$scope.confirmErrorShow = true;
									} else {
										$scope.warnMessage = "请至少选择一个角色！";
										$scope.confirmShow = true;
										$scope.confirmErrorShow = false
									}
								} else if (operationParam == 'delete') {
									if ($scope.deleteRoleIdList != undefined
											&& $scope.deleteRoleIdList.length > 0) {
										roleIdListJson = $scope.deleteRoleIdList;
									} else if ($scope.deleteRoleId != '') {
										roleIdListJson[0] = $scope.deleteRoleId;
									}
								} else if (operationParam == 'clear') {
									$scope.deleteRoleIdList = [];
									$scope.deleteRoleId = '';
								}
								if (roleIdListJson.length > 0
										&& operationParam == 'delete') {
									var deleteRoleParams = {};
									deleteRoleParams["roleId"] = roleIdListJson;
									deleteRoleParams = ObjParesJSON(deleteRoleParams);
									deleteRoleParams = mergeStr("roleIdList",
											deleteRoleParams);
									var deleteRoleData = mergeReauestData(
											'RoleController', 'deleteRoleInfo',
											deleteRoleParams);
									var deleteRoleResult = sendPost($http,
											deleteRoleData, $q);

									deleteRoleResult
											.then(
													function(success) {
														$scope.deleteRoleIdList = [];
														$scope.deleteRoleId = '';

														if ($scope.roleList.length == 1
																&& $scope.bigCurrentPage != 1) {
															$scope.bigCurrentPage = $scope.bigCurrentPage - 1;
															$scope
																	.requestPageService("true");
														} else {
															$scope
																	.requestPageService("true");
														}
														$scope.checkAll(1);
														$modal({
															scope : $scope,
															title : "提示",
															templateUrl : 'packages/sys/views/role/tip.html',
															content : '批量删除角色信息成功',
															show : true,
															backdrop : "static"
														});
													}, function(error) {
														console.info(error);
													});
								}
							}
							// 删除角色
							$scope.deleteRole = function(id, bigCurrentPage) {
								$scope.deleteId = id;
								deleteRoleInfo.$promise
										.then(deleteRoleInfo.show);
							}
							// 恢复角色信息
							$scope.recoverRole = function(id, bigCurrentPage) {
								$scope.recoverRoleId = id;
								recoverRoleInfo.$promise
										.then(recoverRoleInfo.show);
							}
							// 批量恢复角色
							var flagRecoverAll = 0;
							$scope.recoverRoleList = function(operationParam) {
								flagRecoverAll = 0;
								var roleIdListJson = [];
								if (operationParam == 'recoverRoleList') {
									var roleIdList = document
											.getElementsByName('roleCheckChild');
									var userIdJsonIndex = 0;
									for (var i = 0; i < roleIdList.length; i++) {
										if (roleIdList[i].checked) {
											roleIdListJson[userIdJsonIndex] = parseInt(roleIdList[i].value);
											userIdJsonIndex++;
											flagRecoverAll++;
										}
									}
									$scope.recoverRoleIdList = roleIdListJson;
									if (roleIdListJson.length > 0) {
										$scope.warnMessage = "确认恢复选中角色？";
										$scope.confirmShow = true;
										$scope.confirmErrorShow = true
									} else {
										$scope.warnMessage = "请至少选择一个角色！";
										$scope.confirmShow = true;
										$scope.confirmErrorShow = false
									}
								} else if (operationParam == 'recover') {
									if ($scope.recoverRoleIdList != undefined
											&& $scope.recoverRoleIdList.length > 0) {
										roleIdListJson = $scope.recoverRoleIdList;
									} else if ($scope.recoverRoleId != '') {
										roleIdListJson[0] = $scope.recoverRoleId;
									}
								} else if (operationParam == 'clear') {
									$scope.recoverRoleIdList = [];
									$scope.recoverRoleId = '';
								}
								if (roleIdListJson.length > 0
										&& operationParam == 'recover') {
									var recoverRoleParams = {};
									recoverRoleParams["roleId"] = roleIdListJson;
									recoverRoleParams = ObjParesJSON(recoverRoleParams);
									recoverRoleParams = mergeStr("roleIdList",
											recoverRoleParams);
									var recoverRoleData = mergeReauestData(
											'RoleController',
											'recoverRoleInfo',
											recoverRoleParams);
									var recoverRoleResult = sendPost($http,
											recoverRoleData, $q);
									recoverRoleResult
											.then(
													function(success) {
														$scope.recoverRoleIdList = [];
														$scope.recoverRoleId = '';

														if ($scope.roleList.length == flagRecoverAll
																&& $scope.bigCurrentPage != 1) {
															$scope.bigCurrentPage = $scope.bigCurrentPage - 1;
															$scope
																	.requestPageService();
														} else {
															$scope
																	.requestPageService();
														}
														$scope.checkAll(1);
														$modal({
															scope : $scope,
															title : "提示",
															templateUrl : 'packages/sys/views/role/tip.html',
															content : '批量恢复角色信息成功',
															show : true,
															backdrop : "static"
														});
													}, function(error) {
														console.info(error);
													});
								}
							}
							// 查看角色信息
							$scope.viewRole = function(roleId){
								$scope.roleIsBaseRole = true;
								$scope.roleIsEnabled = true;
								$scope.isViewRole = true;
								$scope.queryRoleInfoByRoleId(roleId);
							}
							//是否可用
							$scope.getRoleIsVisible = function() {
								if ($scope.roleIsVisibleJosns == undefined) {
									var responseDictResult = getSelectValueByDictList($http, $q,
											'WHETHER_TYPE', 'WHETHER_CODE');
									responseDictResult.then(function(success) {
										$scope.roleIsVisibleJosns = StrParesJSON(success).result;
									}, function(error) {
										console.info(error);
									});
								}
							}
							// 根据角色ID查询角色信息
							$scope.queryRoleInfoByRoleId = function(roleId) {
								$scope.getRoleIsVisible();
								var changeRoleParams = mergeStr('roleId',roleId);
								var changeRoleData = mergeReauestData('RoleController', 'queryInfoById',changeRoleParams);
								var changeRoleResult = sendPost($http,changeRoleData, $q);
								changeRoleResult.then(function(success) {
										var changeRoleResponse = StrParesJSON(success);
										$scope.roleId = roleId;
										$scope.viewRoleName = changeRoleResponse.result.name;
										$scope.updateRoleName = changeRoleResponse.result.name;
										
										if (changeRoleResponse.result.isBaseRole){
											$scope.roleTypeCode = 'true';
										} else {
											$scope.roleTypeCode = 'false';
										}
											$scope.viewRoleDesc = changeRoleResponse.result.desc;
											if($scope.isViewRole){
												viewRoleInfo.$promise.then(viewRoleInfo.show);
											}else{
												editRoleInfo.$promise.then(editRoleInfo.show);
											}
									}, function(error) {
										console.info(error);
									});
							}
							// 修改角色信息
							$scope.changeRole = function(roleId) {
								$scope.isViewRole = false;
								$scope.roleIsBaseRole = true;
								$scope.roleIsEnabled = true;
								$scope.queryRoleInfoByRoleId(roleId);
							}
							// 展示当前角色用户信息
							$scope.userRoleList = function(roleId, name) {
								$scope.addUserRoleName = name;
								var updateRoleParams = mergeStr('roleId',
										roleId);
								var updateRoleData = mergeReauestData(
										'RoleController', 'queryUserByRoleId',
										updateRoleParams);
								var updateRoleResult = sendPost($http,
										updateRoleData, $q);
								updateRoleResult
										.then(
												function(success) {
													var updateRoleResponse = StrParesJSON(success);
													$scope.addUserRoleId = roleId;
													$scope.addUserItems = updateRoleResponse.result;
													viewUserRole.$promise
															.then(viewUserRole.show);
												}, function(error) {
													console.info(error);
												});/**/

							}
							// 重置查询条件
							$scope.resetQuery = function() {
								$scope.roleInfo.queryParams = "";
								$scope.roleInfo.roleStatus = "true";
							}
							// 复选框全选全不选
							$scope.checkAll = function(cancel) {
								var parent_check = document
										.getElementById("roleCheckParent");
								if (cancel == 1) {
									parent_check.checked = false;
								}
								var child_check = document
										.getElementsByName("roleCheckChild");
								for (i = 0; i < child_check.length; i++) {
									if (child_check[i].type == "checkbox") {
										child_check[i].checked = parent_check.checked;
									}
								}
							}
							
						} ])

/**
 * 添加角色信息
 * 
 * @author liujie
 * @Email jie_liu1@asdc.com.cn
 */
MetronicApp.controller('AddRoleInfo',['$scope','$http','$q','$filter','$modal','$rootScope',
		function($scope, $http, $q, $filter, $modal,$rootScope ) {
            //默认为否
			$scope.roleTypeCode ="false";
			//是否可用
			$scope.getRoleIsVisible = function() {
				if ($scope.roleIsVisibleJosns == undefined) {
					var responseDictResult = getSelectValueByDictList($http, $q,
							'WHETHER_TYPE', 'WHETHER_CODE');
					responseDictResult.then(function(success) {
						$scope.roleIsVisibleJosns = StrParesJSON(success).result;
					}, function(error) {
						console.info(error);
					});
				}
			}
			$scope.getRoleIsVisible();
			$scope.addRoleNameErrorShow = false;
			$scope.addRole = function() {
				if ($scope.roleForm.$valid) {
					var roleName = $scope.role.name;
					if (roleName == undefined) {
						$scope.addRoleNameErrorShow = true;
						$scope.roleForm.submitted = true;
					} else {
						if ($scope.roleTypeCode == "true") {
							$scope.role.isBaseRole = true;
						} else {
							$scope.role.isBaseRole = false;
						}
						var addRoleParams = mergeJson('role',$scope.role);
						var addRoleData = mergeReauestData('RoleController', 'addRole',addRoleParams);
						var addRoleResult = sendPost($http,
								addRoleData, $q);
						addRoleResult.then(function(success) {
								var addRoleResponse = StrParesJSON(success);
								
								if (addRoleResponse.result == true) {
									//隐藏弹框
									$scope.$parent.$parent.hideWin();
									
									$scope.$parent.$parent.requestPageService("true");
								}
								$modal({
									scope : $scope,
									title : "提示",
									templateUrl : 'packages/sys/views/role/tip.html',
									content : '添加角色信息成功',
									show : true,
									backdrop : "static"
								});
								$scope.roleForm=undefined
							},
						function(error) {
							console.info(error);
						});
					}
				} else {
					$scope.roleForm.submitted = true;

				}
			}


			$scope.addhideModal=function(){
				if($scope.roleForm.$pristine){//判断是否有修改 如果为true代表没有修改执行关闭
					$scope.$parent.$parent.hideWin();
					$scope.roleForm=undefined;
					fullscreenRemove();
				}else{//如果是false 代表有修改 显示提示框
					$rootScope.closeModel.$promise.then($rootScope.closeModel.show);	
				}
			}			
			$rootScope.$on('hideModel', function(event, roledeModel) {//确定丢弃输入的修改内容后需要执行的方法			
				if (roledeModel &&　$scope.roleForm!=undefined) {					
					$scope.addRole(); // 执行提交方法
				}else{
					$scope.$parent.$parent.hideWin();
					$scope.roleForm=undefined;
					fullscreenRemove();
				};
			})

		} ])
/**
 * 查看或修改角色信息
 * 
 * @author liujie
 * @Email jie_liu1@asdc.com.cn
 */
MetronicApp.controller('ViewRoleController', [
		'$scope',
		'$http',
		'$q',
		'$filter',
		'$modal',
		'$rootScope',
		function($scope, $http, $q, $filter, $modal,$rootScope) {
			$scope.viewRoleSubmitted = false;
			$scope.updateRoleInfoNameError = false;
			
			$scope.updateRole = function(){
				if ($scope.roleEditForm.$valid) {
					var json = {};
					json["name"] = $scope.updateRoleName;
					json["desc"] = $scope.viewRoleDesc;
					
					if ($scope.roleTypeCode == 'true') {
						json["isBaseRole"] = true;
					} else {
						json["isBaseRole"] = false;
					}					
					json["id"] = $scope.roleId;
					var updateRoleParams = mergeJson('role', json);
					var updateRoleData = mergeReauestData('RoleController',
							'updateRole', updateRoleParams);
					var updateRoleResult = sendPost($http, updateRoleData, $q);
					updateRoleResult.then(function(success) {
						var updateRoleResponse = StrParesJSON(success);
						if (updateRoleResponse.result == true) {
							$scope.$parent.$parent.hideWin();
							$scope.$parent.$parent.requestPageService("true");
						} else {
							$scope.viewRoleSubmitted = true;
							$scope.addRoleNameErrorShow = true;
						}
						$modal({
							scope : $scope,
							title : "提示",
							templateUrl : 'packages/sys/views/role/tip.html',
							content : '修改角色信息成功',
							show : true,
							backdrop : "static"
						});
						$scope.roleEditForm=undefined
					}, function(error) {
						console.info(error);
					});
				} else {
					$scope.roleEditForm.submitted = true;
				}
			}
			$scope.edithideModal=function(){
				if($scope.roleEditForm.$pristine){//判断是否有修改 如果为true代表没有修改执行关闭
					$scope.$parent.$parent.hideWin();
					$scope.roleEditForm=undefined;
					fullscreenRemove();
				}else{//如果是false 代表有修改 显示提示框
					$rootScope.closeModel.$promise.then($rootScope.closeModel.show);	
				}
			}			
			$rootScope.$on('hideModel', function(event, roleEdithideModal) {//确定丢弃输入的修改内容后需要执行的方法			
				if (roleEdithideModal && $scope.roleEditForm!=undefined) {					
					$scope.updateRole(); // 执行提交方法
				}else{
					$scope.$parent.$parent.hideWin();
					$scope.roleEditForm=undefined;
					fullscreenRemove();
				};
			})


		} ])

/**
 * 删除角色信息
 * 
 * @author liujie
 * @Email jie_liu1@asdc.com.cn
 */
MetronicApp.controller('DeleteRoleInfo', [
		'$scope',
		'$http',
		'$q',
		'$filter',
		'$modal',
		function($scope, $http, $q, $filter, $modal) {
			// 根据当前角色ID删除角色
			$scope.deleteRole = function(roleId) {
				var roleIdList = [];
				roleIdList[0] = roleId;
				var deleteRoleParams = {};
				deleteRoleParams["roleId"] = roleIdList;
				deleteRoleParams = ObjParesJSON(deleteRoleParams);
				deleteRoleParams = mergeStr("roleIdList", deleteRoleParams);
				var deleteRoleData = mergeReauestData('RoleController',
						'deleteRoleInfo', deleteRoleParams);
				var deleteRoleResult = sendPost($http, deleteRoleData, $q);
				deleteRoleResult.then(function(success) {
					var deleteRoleResponse = StrParesJSON(success);
					$scope.delRoleInfoFlag = deleteRoleResponse.result;
					if (deleteRoleResponse.result == true) {

						$scope.$parent.$parent.hideWin();

						if ($scope.$parent.$parent.roleList.length == 1
								&& $scope.$parent.$parent.bigCurrentPage != 1) {

							$scope.bigCurrentPage = $scope.bigCurrentPage - 1;
							$scope.$parent.$parent.requestPageService("true");
						} else {
							$scope.$parent.$parent.requestPageService("true");
						}

						$modal({
							scope : $scope,
							title : "提示",
							templateUrl : 'packages/sys/views/role/tip.html',
							content : '删除角色信息成功',
							show : true,
							backdrop : "static"
						});
					}
				}, function(error) {
					console.info(error);
				});
			}
		} ])
/**
 * 恢复角色信息
 * 
 * @author liujie
 * @Email jie_liu1@asdc.com.cn
 */
MetronicApp.controller('RecoverRoleInfo', [
		'$scope',
		'$http',
		'$q',
		'$filter',
		'$modal',
		function($scope, $http, $q, $filter, $modal) {
			// 根据当前角色ID恢复角色
			$scope.recoverRole = function(roleId) {
				var roleIdList = [];
				roleIdList[0] = roleId;
				var recoverRoleParams = {};
				recoverRoleParams["roleId"] = roleIdList;
				recoverRoleParams = ObjParesJSON(recoverRoleParams);
				recoverRoleParams = mergeStr("roleIdList", recoverRoleParams);
				var deleteRoleData = mergeReauestData('RoleController',
						'recoverRoleInfo', recoverRoleParams);
				var deleteRoleResult = sendPost($http, deleteRoleData, $q);
				deleteRoleResult.then(function(success) {
					var deleteRoleResponse = StrParesJSON(success);
					$scope.delRoleInfoFlag = deleteRoleResponse.result;
					if (deleteRoleResponse.result == true) {
						$scope.$parent.$parent.hideWin();
						if ($scope.$parent.$parent.roleList.length == 1
								&& $scope.$parent.$parent.bigCurrentPage != 1) {

							$scope.bigCurrentPage = $scope.bigCurrentPage - 1;
							$scope.$parent.$parent.requestPageService("false");
						} else {
							$scope.$parent.$parent.requestPageService("false");
						}
						$modal({
							scope : $scope,
							title : "提示",
							templateUrl : 'packages/sys/views/role/tip.html',
							content : '恢复角色信息成功',
							show : true,
							backdrop : "static"
						});
					}
				}, function(error) {
					console.info(error);
				});
			}
		} ])
/**
 * 展示当前角色用户信息
 * 
 * @author liujie
 * @Email jie_liu1@asdc.com.cn
 */
MetronicApp.controller('ViewUserRole', [ '$scope', '$http', '$q', '$filter',
		'$modal', function($scope, $http, $q, $filter, $modal) {
			var addUserByRole = $modal({
				scope : $scope,
				templateUrl : 'packages/sys/views/role/select_user.html',
				show : false,
				container : '#addUserbyRole',
				controller : 'AddUserRole',
				backdrop : "static"
			});
			var deleteUserByRole = $modal({
				scope : $scope,
				templateUrl : 'packages/sys/views/role/delete_user.html',
				show : false,
				container : '#addUserbyRole',
				controller : 'DeleteUserRole',
				backdrop : "static"
			});
			$scope.addUser = function(roleId) {
				addUserByRole.$promise.then(addUserByRole.show);
				$scope.selectUserRoleId = roleId;
			}
			$scope.hideUpdateUserByRole = function() {
				addUserByRole.$promise.then(addUserByRole.hide);
				deleteUserByRole.$promise.then(deleteUserByRole.hide);
			}
			// 移除当前用户
			$scope.deleteUsersByRoleId = function(userId, roleId, userName) {
				$scope.deleteUserName = userName;
				$scope.deleteUserByRole = $scope.addUserRoleName;
				$scope.deleteUserId = userId;
				$scope.deleteUserByRoleId = roleId;
				deleteUserByRole.$promise.then(deleteUserByRole.show);
			}

		} ])
/**
 * 从角色中移除用户
 * 
 * @author liujie
 * @Emcil jie_liu1@asdc.com.cn
 */
MetronicApp.controller('DeleteUserRole', [
		'$scope',
		'$http',
		'$q',
		'$filter',
		function($scope, $http, $q, $filter) {
			// 根据角色ID移除单个用户
			$scope.deleteUser = function(userId, roleId) {

				var userIdParam = mergeStr('userId', userId);
				var roleIdParam = mergeStr('roleId', roleId);
				var deleteUsersByRoleIdParams = {};
				$.extend(deleteUsersByRoleIdParams, userIdParam, roleIdParam);

				var deleteUsersByRoleIdParamsData = mergeReauestData(
						'RoleController', 'removeUserByRole',
						deleteUsersByRoleIdParams);
				var remUserByRoleId = sendPost($http,
						deleteUsersByRoleIdParamsData, $q);
				remUserByRoleId.then(function(success) {
					var remUserResponse = StrParesJSON(success);
					$scope.deleteUserInfoByRoleIdFlag = remUserResponse.result;
					if (remUserResponse.result == true) {
						$scope.$parent.$parent.hideUpdateUserByRole();
						$scope.$parent.$parent.$parent.$parent.userRoleList(
								roleId, $scope.addUserRoleName);
					}
				}, function(error) {
					console.info(error);
				});
			}
		} ])

/**
 * 为角色添加用户
 * 
 * @author liujie
 * @Emcil jie_liu1@asdc.com.cn
 */
MetronicApp.controller('AddUserRole',['$scope','$http','$q','$filter','$modal',
		function($scope, $http, $q, $filter, $modal) {
	
			// 展示部门树
			$scope.getDeptTree = function(){
				getTreeAndView($scope, $http, $q, 'DeptController', 'getDeptTreeParent',"");
			}

			$scope.showSelected = function(sel) {
				$scope.selectedNode = sel;
			};
			// 根据角色ID查询用户信息
			$scope.queryUserByRole = function() {
				var queryUserParams = mergeStr('roleId',
						$scope.selectUserRoleId);
				var queryUserData = mergeReauestData(
						'RoleController', 'queryUserByRoleId',
						queryUserParams);
				var queryUserResult = sendPost($http,
						queryUserData, $q);
				queryUserResult
						.then(
								function(success) {
									var queryUserResponse = StrParesJSON(success);
									$scope.selectUsers = queryUserResponse.result;
								}, function(error) {
									console.info(error);
								});
			}
			// 根据部门ID查询用户信息
			
			$scope.getUserByDept = function(deptId,eventTarget) {
				if ($scope.deptId == deptId) {
		               angular.element(eventTarget.currentTarget).addClass('tree-selected')
				}else{
					angular.element('.treeLink').removeClass('tree-selected')
				};
				$scope.deptId = deptId;
				var getUserByDeptParams = mergeStr('deptId',
						deptId);
				var getUserByDeptData = mergeReauestData(
						'RoleController', 'queryUserByDeptId',
						getUserByDeptParams);
				var getUserByDeptResult = sendPost($http,
						getUserByDeptData, $q);
				getUserByDeptResult
						.then(
								function(success) {
									var getUserByDeptResponse = StrParesJSON(success);
									$scope.addUserList = getUserByDeptResponse.result;
								}, function(error) {
									console.info(error);
								});
			}

			$scope.getDeptTree();
			$scope.queryUserByRole();

			// 移除已选人员中的信息
			$scope.cancelUser = function(index) {
				$scope.selectUsers.splice(index, 1);
			}
			// 待选人员添加到已选人员
			$scope.chosseUser = function(userId) {
				var queryUserByIdParams = mergeStr('userId',
						userId);
				var requestData = mergeReauestData(
						'RoleController', 'queryUserByUserId',
						queryUserByIdParams);
				var resultUserInfo = sendPost($http,
						requestData, $q);
				resultUserInfo
						.then(
								function(success) {
									var userInfoResponse = StrParesJSON(success);
									if ($scope.selectUsers.length > 0) {
										if (!$scope.selectUsers
												.contains(userInfoResponse.result)) {
											$scope.selectUsers
													.push(userInfoResponse.result);
										}
									} else {
										$scope.selectUsers
												.push(userInfoResponse.result);
									}
								}, function(error) {
									console.info(error);
								});
			}
			// 用于判断已选中是否存在该条数据
			Array.prototype.contains = function(obj) {
				var i = this.length;
				while (i--) {
					if (this[i].id == obj.id) {
						return true;
					}
				}
				return false;
			};

			// 保存更改
			$scope.addUserByRole = function() {
				var userInfo = mergeJson('userInfo',
						$scope.selectUsers);
				var roleId = mergeStr('roleId',
						$scope.selectUserRoleId);
				var addUserByRoleParams = {};
				$.extend(addUserByRoleParams, userInfo, roleId);

				var updateUserByRoleData = mergeReauestData(
						'RoleController', 'addUserByRole',
						addUserByRoleParams);
				var updateUserByRole = sendPost($http,
						updateUserByRoleData, $q);
				updateUserByRole
						.then(
								function(success) {
									var addUserInfoResponse = StrParesJSON(success);
									if (addUserInfoResponse.result == true) {
										$scope.$parent.$parent
												.hideUpdateUserByRole();
										$scope.$parent.$parent.$parent.$parent
												.userRoleList(
														$scope.selectUserRoleId,
														$scope.$parent.$parent.addUserRoleName);
										$modal({
											scope : $scope,
											title : "提示",
											templateUrl : 'packages/sys/views/role/tip.html',
											content : '分配用户成功！',
											show : true,
											backdrop : "static"
										});
									}
								}, function(error) {
									console.info(error);
								});
			}

		} 
])

/**
 * 角色分配权限
 * 
 * @author liujie
 * @Email jie_liu1@asdc.com.cn
 */
MetronicApp.controller('AddPerMissCtrl',
				['$scope','$http','$q','$filter','$modal','$rootScope',
						function($scope, $http, $q, $filter, $modal,$rootScope) {	
							 $scope.$on('to-child', function(event,data) {
								  $scope.expandedNodes = data;   
							 });
												
						//进行判断
						$scope.buttonClick =  function(node,parentNode){
							/*
							 * 四种情况
							 *   1.选中的只有子节点，没有父节点
							 *       这种情况，自身发生相反的变化,子节点也的状态和父节点状态一致
							 *                          -》full、port  -->notCheck    
							 *                          -》notCheck  --> full port
							 *   2.选中的只有父节点，没有子节点
							 *       这种情况，自身发生相反的变化 ，如果子节点全部被选中  ---》 full
							 *                           如果子节点部分被选中   ---》 port
							 *   3.选中的即有父节点，也有子节点
							 *       这中情况，自身发生相反的变化，子节点状态和父节点状态一致
							 *                           父节点
							 *   4.选中的即有没有父节点也没有子节点
							 *       
							 */
							 //第一种情况
						     if(node != null && parentNode == null  && node.children!=null && node.children!=undefined){
						    	 var full = node.checkbox_true;
						    	 var notChecked = node.checkbox_false;
						    	 var part = node.checkbox_true_part;
						    	 //选自身
						    	 if(full || part){
						    		 node.checkbox_true=false;
						    		 node.checkbox_true_part=false;
						    		 node.checkbox_false=true;
						    		 //选子节点 
						    		 $scope.checkChild(node.children,false);
						    	 }else{
						    		 node.checkbox_true=true;
						    		 node.checkbox_true_part=false;
						    		 node.checkbox_false=false;
						    		 //选子节点 
						    		 $scope.checkChild(node.children,true);
						    	 }
						     }
						     //第二种情况
						     if(parentNode != null && node.children == undefined  && node.children == null){
						    	 var full = node.checkbox_true;
						    	 var notChecked = node.checkbox_false;
						    	 var part = node.checkbox_true_part;
						    	 
						    	 //选自身
						    	 if(full || part){
						    		 node.checkbox_true=false;
						    		 node.checkbox_true_part=false;
						    		 node.checkbox_false=true;
						    		 //选父节点
						    	      $scope.checkParentEd(parentNode,node,false);
						    	 }else{
						    		 node.checkbox_true=true;
						    		 node.checkbox_true_part=false;
						    		 node.checkbox_false=false;
						    		 //选父节点 
						    		 $scope.checkParentEd(parentNode,node,true);
						    	 }
						     }
						     //第三种情况
						     if(parentNode != null && node.children != undefined  && node.children != null){
						    	 var full = node.checkbox_true;
						    	 var notChecked = node.checkbox_false;
						    	 var part = node.checkbox_true_part;
						    	 
						    	 //选自身
						    	 if(full || part){
						    		 node.checkbox_true=false;
						    		 node.checkbox_true_part=false;
						    		 node.checkbox_false=true;
						    		  //选子节点 
						    		 $scope.checkChild(node.children,false);
						    		 //选父节点
						    	      $scope.checkParentEd(parentNode,node,false);
						    	      
						    	 }else{
						    		 node.checkbox_true=true;
						    		 node.checkbox_true_part=false;
						    		 node.checkbox_false=false;
						    		   //选子节点 
						    		 $scope.checkChild(node.children,true);
						    		 //选父节点 
						    		 $scope.checkParentEd(parentNode,node,true);

						    	 }
						     }
						     //第四种情况
						     if(node != null && parentNode == null  && node.children==null && node.children==undefined){
						    	 var full = node.checkbox_true;
						    	 var notChecked = node.checkbox_false;
						    	 var part = node.checkbox_true_part;
						    	 //选自身
						    	 if(full || part){
						    		 node.checkbox_true=false;
						    		 node.checkbox_true_part=false;
						    		 node.checkbox_false=true;						    		
						    	 }else{
						    		 node.checkbox_true=true;
						    		 node.checkbox_true_part=false;
						    		 node.checkbox_false=false;						    		
						    	 }
						     }
						}
						var isCheckDate=null;
						$scope.checkParentEd = function(parentNode,node,flag){							
						 	//拿到所有的数据
					    	var funcTree  = $scope.$parent.funcTree;
							var x=0;
							var y=0;
							if(parentNode.children.length > 0){
								for(var i=0;i<parentNode.children.length;i++){
									
									if(parentNode.children[i].checkbox_true ){
										     x++
									}
									if(y==0 && parentNode.children[i].checkbox_true_part){
										     y++
									}
								}
							    if(x == parentNode.children.length && flag != "part"){ 
			    					parentNode.checkbox_true = true;
			    					parentNode.checkbox_false = false;
			    					parentNode.checkbox_true_part = false;
			    				}else if(x == 0 && flag != "part" && y == 0){                    
			    					parentNode.checkbox_true = false;
			    					parentNode.checkbox_false = true;
			    					parentNode.checkbox_true_part = false;
			    				
			    				}else{
			    					parentNode.checkbox_true = false;
			    					parentNode.checkbox_false = false;
			    					parentNode.checkbox_true_part = true;
			    					flag = "part";
			    				}							   
							    $scope.checkParen(funcTree,parentNode);							   
								if(isCheckDate != null){
									$scope.checkParentEd(isCheckDate,parentNode,flag);									
								}
							    
							}else{
								$scope.checkParen(funcTree,parentNode);
								if(isCheckDate != null){
									$scope.checkParentEd(isCheckDate,parentNode,falg);
								}else{
									parentNode.checkbox_true = true;
			    					parentNode.checkbox_false = false;
			    					parentNode.checkbox_true_part = false;
								}
							}
						}
					    /*
					     * 选中父亲  -->这里应该要进行遍历的并且要进行递归来判断了
					     *       -->需要传递三个参数，一个是当前节点，一个是父节点，还有一个是标记
					     */
						var isCheckParent = null;
					    $scope.checkParen = function(funcTree1,parentNode1){
					       isCheckDate = null					    	
					    	if(funcTree1.length > 0){
				    			 for(var i=0;i<funcTree1.length;i++){				    			 
				    			 	if(isCheckDate == null ){
					    				 $scope.checkParent(funcTree1[i],parentNode1);
					    				 if (isCheckParent!=null) {
						    				  isCheckDate = isCheckParent;	
						    				  return isCheckDate
					    				  };		    				
				    				 }
					    		 }
					    		 return isCheckDate
				    		}					    	
					    	return isCheckDate
					    }
					    $scope.checkParent = function(funcTreeValue,parentNode){
					     isCheckParent = null					    	
					    if(funcTreeValue.children!=undefined && funcTreeValue.children!=null && funcTreeValue.children.length > 0){					    						    		
					    	  for(var i=0;i<funcTreeValue.children.length;i++){
					    		if (isCheckParent==null ) {
						    		if(funcTreeValue.children[i].id == parentNode.id){
						    			isCheckParent = funcTreeValue;						    			
						    			return isCheckParent;						    			
						    		}else if(funcTreeValue.children[i].id != parentNode.id ){
						    			if (funcTreeValue.children[i].children != undefined && funcTreeValue.children[i].children.length>0) {
						    				$scope.checkParent(funcTreeValue.children[i],parentNode);
						    			}
						    		}else {						    			
						    			return isCheckParent;
						    		}
						    		
						    	   }	
						    	}						    		
							   return isCheckParent;
							}else{
								return isCheckParent;
							};			    	
					    	return isCheckParent;

					    }
					    	
			
					    //选中子类
						$scope.checkChild = function(chiList,flag) {
							if (chiList.length > 0) {
								for (var i=0;i< chiList.length;i++) {
									chiList[i].checkbox_true = flag;
									if(flag){
										chiList[i].checkbox_true_part = !flag;	
									}else{
										chiList[i].checkbox_true_part = flag;	
									}
									chiList[i].checkbox_false = !flag;
									if(chiList[i].children != undefined && chiList[i].children.length > 0){
										$scope.checkChild(chiList[i].children,flag);
									}
								}
							}
						}

							var funcIds = "";
							function  diGuiGetTreeChecked(treeCheckedJson){
		                        for(var i=0;i<treeCheckedJson.length;i++){
		                        	
		                        	if(treeCheckedJson[i].checkbox_true ==true || treeCheckedJson[i].checkbox_true_part ==true ){
		                        		funcIds += treeCheckedJson[i].id +",";
		                        	}
		                        	if(treeCheckedJson[i].children != undefined && treeCheckedJson[i].children.length > 0){
		                        		diGuiGetTreeChecked(treeCheckedJson[i].children);
		                        	}
								}
		                        return funcIds;
							}
							
							$scope.addPerMisByRole = function() {
								//判断是否为选中状态
								var treeCheckedJson  = $scope.$parent.funcTree;
								//对这个树进行遍历
								var listIds = diGuiGetTreeChecked(treeCheckedJson);

								var addUserByRoleParams = {};
								if(listIds != "" && listIds != null){
									var listIds  =  listIds.substring(0,listIds.length-1); 
									var idsArray =  listIds.split(",");
									//传递过去的是一个菜单数组
									addUserByRoleParams["funcId"] = idsArray;
								}else{
									addUserByRoleParams["funcId"] = [];
								}

								addUserByRoleParams["roleId"] = $scope.addPerMissRoleId;
								addUserByRoleParams = ObjParesJSON(addUserByRoleParams);
								addUserByRoleParams = mergeStr("addPer",addUserByRoleParams);
								var addPerMissData = mergeReauestData(
										'RoleController', 'addPerMissByRoleId',
										addUserByRoleParams);
								var addPerMissResult = sendPost($http,
										addPerMissData, $q);
								addPerMissResult.then(function(success) {
										var addPerMissResponse = StrParesJSON(success);
										if (addPerMissResponse.result) {
											$scope.$parent.$parent.hideWin();
											$modal({
												scope : $scope,
												title : "提示",
												templateUrl : 'packages/sys/views/role/tip.html',
												content : '分配权限成功！',
												show : true,
												backdrop : "static"
											});
									    /* var userId  = getCookie("userId");	
										 $.post("mvc/dispatch",{controller:"LoginController",method:"registerTokenCookie",userId:userId},
								                   function(data) {
								             storage.setItem('restoken',JSON.stringify(data));
								             $rootScope.token=data.token; 
								         })*/
										}
										
								}, function(error) {
									console.info(error);
								});
							}

						} ])