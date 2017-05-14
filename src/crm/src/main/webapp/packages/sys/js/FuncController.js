/*
 * 查找所有菜单
 */
function FunctableCtrl($scope, $filter, $modal, $http, $q, $compile, $timeout) {
	$scope.isTableNodeShowZ = true;
	// 定义变量
	$scope.funcList = []; // 当前返回总数据
	$scope.Pagenum = Pagenum;
	$scope.itemsPerPage = itemsPerPage;// 当前页显示数据
	// 获取菜单
	$scope.funcInfo = {
		funcStatus : "true"
	};
	$scope.getFuncIsEnabled = function() {
		if ($scope.funcIsEnabledJson == undefined) {
			var responseDictResult = getSelectValueByDictList($http, $q,
					'STATUS_TYPE', 'STATUS_CODE');
			responseDictResult.then(function(success) {
				$scope.funcIsEnabledJson = StrParesJSON(success).result;
			}, function(error) {
				console.info(error);
			});
		}
	}
	$scope.getFuncIsEnabled();
	
	//展开下级菜单
	$scope.cunId ="";
	$scope.getChildrenFunc = function(funcParentId) {
		//这里我们可以请求一下后台，让后台为我们返回一个关于tr td 的json字符串。然后追加在这个节点的后面
		var show = document.getElementById("funcId" + funcParentId);
		var padding = angular.element(show).css('padding-left');
		var cunIds = $scope.cunId;
		if(cunIds.length >50 ){
			var cunIds = "";
		}
		// 如果返回-1.表示没有点击过。因此可以进行请求的
		var spliceId = "%"+funcParentId+"%";
		if (cunIds.indexOf(spliceId) == -1) {
			$scope.getChildrenFuncByRequest(funcParentId, padding, show);
		} else {
			$scope.showAlreadyExistsNode(funcParentId, show);
			//如果全选框选中
			var checkedAll = document.getElementById("funcCheckParent").checked;
			if(checkedAll){
				$scope.checkAll();
			}
		}
	}
	
	/*
	 * 通过后台请求得到。下级的tr节点
	 */
	$scope.getChildrenFuncByRequest = function(funcParentId, padding, show) {
		// 传递给后台的数据：funcParentId(父级菜单Id) padding(内边距值)
		var finalMarge = {
			"funcParentId" : funcParentId,
			"padding" : padding
		};
		var requestFuncData = mergeReauestData('FuncController', 'spliceTrTd',finalMarge);
		var reqSpliceTrTdResult = sendPost($http, requestFuncData, $q);
		reqSpliceTrTdResult.then(function(success) {
			var trtd = JSON.parse(success);
			$scope.trList = trtd.result;
			angular.element(show).find("span:eq(0)").hide();
			angular.element(show).find("span:eq(1)").show();
			$scope.cunId = $scope.cunId + "%" + funcParentId + "%";
			var parent_check = document.getElementById("funcCheckParent");
			for (var i = 0; i < $scope.trList.length; i++) {
				var reforeNode = document.getElementById("funcId"
						+ funcParentId).parentNode;
				angular.element(reforeNode).after($compile($scope.trList[i])($scope));
			}
			//如果全选框选中
			var checkedAll = document.getElementById("funcCheckParent").checked;
			if(checkedAll){
				$scope.checkAll();
			}
		})
	}
	/*
	 * 已经请求过的tr标签节点。直接显示就可以了
	 */
	$scope.showAlreadyExistsNode = function(funcParentId) {
		var showBtn = document.getElementById("funcId" + funcParentId);
		angular.element(showBtn).find("span:eq(0)").hide();
		angular.element(showBtn).find("span:eq(1)").show();
		
		var trHideNames = document.getElementsByName("#spliceflag"+ funcParentId + "#");

		for(var i=0;i<trHideNames.length;i++){
			trHideNames[i].style.display = '';
		}
	}
	/*
	 * 隐藏拼接tr标签 目前支持的隐藏是自己的下级。应该要隐藏下级的下级的下级啦！
	 * 所以要得到它包含的所有的funcParentId啦！可以通过后台得到所有的id啦！
	 * 但是前台页需要通过递归来解决这样的问题啦！
	 */
	$scope.hideChildrenFunc = function(funcParentId) {
		var jsonParam = {
			"funcParentId" : funcParentId
		};
		var requestFuncData = mergeReauestData('FuncController',
				'getNeedHideFuncIds', jsonParam);
		var respFundsResult = sendPost($http, requestFuncData, $q);
		respFundsResult.then(function(success) {
			var respResult = JSON.parse(success);
			var fundstr = respResult.result;
			// 字符串转换数组
			var fundsArray = fundstr.split(",");
			for (keys in fundsArray) {
				var showBtn = document.getElementById("funcId"
						+ fundsArray[keys]);
				//显示、隐藏 展开和收缩图标
				angular.element(showBtn).find("span:eq(0)").show();
				angular.element(showBtn).find("span:eq(1)").hide();
				//得到他们的所有下级 
				var trHideNames = document.getElementsByName("#spliceflag"+ fundsArray[keys] + "#");
				for (var i = 0; i < trHideNames.length; i++) {
					trHideNames[i].style.display = 'none';
					var tdCkecked = trHideNames[i].firstChild;
					if(tdCkecked.firstChild != null){
						var flag = tdCkecked.firstChild.checked;
						if(flag){
							tdCkecked.firstChild.checked = false;
						}
					}
				}
			}
		})
	}
	/*
	 * 修改弹框
	 */
	var updateFuncModal = $modal({
		scope : $scope,
		templateUrl : 'packages/sys/views/func/update_func.html',
		show : false,
		backdrop : "static",
		controller : "updateFuncCtl"
	});

	$scope.updateFuncInfoWin = function(updateFuncId, detailsOrEidt) {
		updateFuncModal.$promise.then(updateFuncModal.show);
		$scope.detailsOrEidt = detailsOrEidt;
		$scope.updateFuncId = updateFuncId;
	}

	// 查看弹窗
	var findFuncModal = $modal({
		scope : $scope,
		templateUrl : 'packages/sys/views/func/find_func.html',
		show : false,
		backdrop : "static",
		controller : "updateFuncCtl"
	});
	$scope.findFuncInfo = function(updateFuncId, detailsOrEidt) {
		findFuncModal.$promise.then(findFuncModal.show);
		$scope.detailsOrEidt = detailsOrEidt;
		$scope.updateFuncId = updateFuncId;
	};
	$scope.closefindFuncModal = function() {		
		findFuncModal.$promise.then(findFuncModal.hide);
		fullscreenRemove();
	};
	/*
	 * 通过部门id得到部门详情
	 * 
	 */
	$scope.findFuncInfoById = function(funcId) {
		// 找到查看页面
		findFuncModal.$promise.then(findFuncModal.show);
		var devFuncId = mergeJson("updateFuncId", funcId);
		var requestDeptData = mergeReauestData('FuncController',
				'findFuncInfoById', devFuncId);
		var respRecoverResult = sendPost($http, requestDeptData, $q);
		respRecoverResult.then(function(success) {
			var JsonFunInfo = JSON.parse(success);
			$scope.funcInfo = JsonFunInfo.result;
			if ($scope.funcInfo.isVisible) {
				$scope.funcInfo.isVisible = "true";
			} else {
				$scope.funcInfo.isVisible = "false";
			}
			$scope.findFuncInfoParentName = JsonFunInfo.result.parent.name;
		})
	};

	$scope.funcPageService = function(addOrUpdate) {
		// 初始化以及清空funcId条件
		$scope.cunId = "";
		document.getElementById("funcCheckParent").checked = false;

		if (addOrUpdate == "true") {
			$scope.funcInfo.funcStatus = "true";
		}

		if ($scope.funcInfo.funcStatus == undefined) {
			$scope.funcInfo.funcStatus = "";
		}

		if ($scope.funcInfo.queryParams == undefined) {
			$scope.funcInfo.queryParams = "";
		}
		var funcInfoResult = pageService($http, $q, 'FuncController',
				'queryFuncInfo', $scope.currentPage, $scope.itemsPerPage,
				ObjParesJSON($scope.funcInfo));
		funcInfoResult.then(function(success) {
			// 恢复和删除之间转换
			if ($scope.funcInfo.funcStatus == "true") {
				$scope.deleteFuncButton = true;
				$scope.recoverFuncButton = false;
			} else {
				$scope.deleteFuncButton = false;
				$scope.recoverFuncButton = true;
			}
			var funcInfoResponse = StrParesJSON(success);
			$scope.funcList = funcInfoResponse.result;
			$scope.bigTotalItems = funcInfoResponse.total;
		}, function(error) {
			console.info(error);
		});
	}
	// 所有的查询方法都走这一个，呵呵！
	$scope.funcPageService("true");

	scrollwatch($scope, $timeout);

	// 回车查询
	$scope.keyup = function($event) {
		if ($event.keyCode == 13) {
			$scope.funcPageService("true");
		}

	}
	// 批量删除
	$scope.deleteFuncList = function(operationParam) {
		var funcIdListJson = [];
		if (operationParam == 'deleteFuncList') {
			var funcIdList = document.getElementsByName('funcCheckChild');
			var userIdJsonIndex = 0;
			for (var i = 0; i < funcIdList.length; i++) {
				var isShow = funcIdList[i].parentNode.parentNode.style.display;
				if (funcIdList[i].checked && isShow != "none") {
					funcIdListJson[userIdJsonIndex] = parseInt(funcIdList[i].value);
					userIdJsonIndex++;
				}
			}
			$scope.deleteFuncIdList = funcIdListJson;
			if (funcIdListJson.length > 0) {
				$scope.warnMessage = "确认删除选中功能？";
				$scope.confirmShow = true;
				$scope.confirmErrorShow = true;
			} else {
				$scope.warnMessage = "请至少选择一个功能！";
				$scope.confirmShow = true;
				$scope.confirmErrorShow = false
			}
		} else if (operationParam == 'delete') {
			if ($scope.deleteFuncIdList != undefined
					&& $scope.deleteFuncIdList.length > 0) {
				funcIdListJson = $scope.deleteFuncIdList;
			} else if ($scope.deleteFuncId != '') {
				funcIdListJson[0] = $scope.deleteFuncId;
			}
		} else if (operationParam == 'clear') {
			$scope.deleteFuncIdList = [];
			$scope.deleteFuncId = '';
		}
		if (funcIdListJson.length > 0 && operationParam == 'delete') {
			var deleteFuncParams = {};
			deleteFuncParams["funcId"] = funcIdListJson;
			deleteFuncParams = ObjParesJSON(deleteFuncParams);
			deleteFuncParams = mergeStr("funcIdList", deleteFuncParams);
			var deleteFuncData = mergeReauestData('FuncController',
					'deleteFuncInfo', deleteFuncParams);
			var deleteFuncResult = sendPost($http, deleteFuncData, $q);

			deleteFuncResult.then(function(success) {
				$scope.deleteFuncIdList = [];
				$scope.deleteFuncId = '';
				$scope.funcPageService("true");
				$scope.checkAll(1);
				$modal({
					scope : $scope,
					title : "提示",
					templateUrl : 'packages/sys/views/func/tip.html',
					content : '菜单项删除成功！',
					show : true,
					backdrop : "static"
				});
			}, function(error) {
				console.info(error);
			});
		}
	}
	// 批量恢复
	$scope.recoverFuncList = function(operationParam) {
		var funcIdListJson = [];
		if (operationParam == 'recoverFuncList') {
			var funcIdList = document.getElementsByName('funcCheckChild');
			var userIdJsonIndex = 0;
			for (var i = 0; i < funcIdList.length; i++) {
				if (funcIdList[i].checked) {
					funcIdListJson[userIdJsonIndex] = parseInt(funcIdList[i].value);
					userIdJsonIndex++;
				}
			}
			$scope.recoverFuncIdList = funcIdListJson;
			if (funcIdListJson.length > 0) {
				$scope.warnMessage = "确认要恢复选中菜单，及其上级菜单吗？";
				$scope.confirmShow = true;
				$scope.confirmErrorShow = true
			} else {
				$scope.warnMessage = "请至少选择一个功能！";
				$scope.confirmShow = true;
				$scope.confirmErrorShow = false
			}
		} else if (operationParam == 'recover') {
			if ($scope.recoverFuncIdList != undefined
					&& $scope.recoverFuncIdList.length > 0) {
				funcIdListJson = $scope.recoverFuncIdList;
			} else if ($scope.recoverFuncId != '') {
				funcIdListJson[0] = $scope.recoverFuncId;
			}
		} else if (operationParam == 'clear') {
			$scope.recoverFuncIdList = [];
			$scope.recoverFuncId = '';
		}
		if (funcIdListJson.length > 0 && operationParam == 'recover') {
			var recoverFuncParams = {};
			recoverFuncParams["funcId"] = funcIdListJson;
			recoverFuncParams = ObjParesJSON(recoverFuncParams);
			recoverFuncParams = mergeStr("funcIdList", recoverFuncParams);
			var recoverFuncData = mergeReauestData('FuncController',
					'recoverFuncInfo', recoverFuncParams);
			var recoverFuncResult = sendPost($http, recoverFuncData, $q);

			recoverFuncResult.then(function(success) {
				$scope.recoverFuncIdList = [];
				$scope.recoverFuncId = '';
				$scope.funcPageService("false");
				$scope.checkAll(1);
				$modal({
					scope : $scope,
					title : "提示",
					templateUrl : 'packages/sys/views/func/tip.html',
					content : '菜单项恢复成功！',
					show : true,
					backdrop : "static"
				});
			}, function(error) {
				console.info(error);
			});
		}
	}

	// 重置查询条件
	$scope.resetFuncQuery = function() {
		$scope.funcInfo.queryParams = "";
		$scope.funcInfo.funcStatus = "true";
	}
	
	// 复选框全选全不选
	$scope.checkAll = function(cancel) {
		var parent_check = document.getElementById("funcCheckParent");
		if (cancel == 1) {
			parent_check.checked = false;
		}
		var child_check = document.getElementsByName("funcCheckChild");
		
		for (i = 0; i < child_check.length; i++) {
			if (child_check[i].type == "checkbox") {
				child_check[i].checked = parent_check.checked;
			}
		}
	}

	// 删除弹窗
	var deleteOtherModal = $modal({
		scope : $scope,
		templateUrl : 'packages/sys/views/func/prompt.html',
		show : false,
		backdrop : "static"
	});
	
	$scope.deleteFuncModelWin = function(funcId) {
		deleteOtherModal.$promise.then(deleteOtherModal.show);
		$scope.funcId = funcId;
	};
	$scope.deleteFunc = function() {
		var funcIdList = [];
		funcIdList[0] = $scope.funcId;
		var deleteFuncParams = {};
		deleteFuncParams["funcId"] = funcIdList;
		deleteFuncParams = ObjParesJSON(deleteFuncParams);
		deleteFuncParams = mergeStr("funcIdList", deleteFuncParams);
		if ($scope.deleteFuncButton) {
			var funcData = mergeReauestData('FuncController', 'deleteFuncInfo',
					deleteFuncParams);
		} else {
			var funcData = mergeReauestData('FuncController',
					'recoverFuncInfo', deleteFuncParams);
		}
		var deleteResult = sendPost($http, funcData, $q);
		deleteResult.then(function(success) {
			deleteOtherModal.$promise.then(deleteOtherModal.hide);
			if ($scope.deleteFuncButton) {
				$scope.funcPageService("true");
				$modal({
					scope : $scope,
					title : "提示",
					templateUrl : 'packages/sys/views/func/tip.html',
					content : '菜单项删除成功',
					show : true,
					backdrop : "static"
				});
			} else {
				$scope.funcPageService("false");
				$modal({
					scope : $scope,
					title : "提示",
					templateUrl : 'packages/sys/views/func/tip.html',
					content : '菜单项恢复成功',
					show : true,
					backdrop : "static"
				});
			}

		});
	};
	
	//恢复菜单信息
	$scope.recoverCommonFun = function(deleteFuncParams){
		var funcData = mergeReauestData('FuncController','recoverFuncInfo', deleteFuncParams);
		var deleteResult = sendPost($http, funcData, $q);
		deleteResult.then(function(success) {
			deleteOtherModal.$promise.then(deleteOtherModal.hide);
				$scope.funcPageService("false");
				$modal({
					scope : $scope,
					title : "提示",
					templateUrl : 'packages/sys/views/func/tip.html',
					content : '菜单项恢复成功',
					show : true,
					backdrop : "static"
				});
		});
	}
	
	
	//添加菜单
	var addFuncInfoModal = $modal({
		scope : $scope,
		templateUrl : 'packages/sys/views/func/add_func.html',
		show : false,
		backdrop : "static",
		controller : 'addFuncInfo',
		container : '#funcInfoPage'
	});
	$scope.addFuncModelWin = function(type) {
		$scope.icon = null;
		$scope.addFuncInfoParentName = null;
		$scope.addFuncInfoParentId = null;
		$scope.updateFuncId = null;
		if (type == "addFirstMenu") {
			addFuncInfoModal.$promise.then(addFuncInfoModal.show);
			$scope.funcAddTitle = "添加菜单";
		} else {
			$scope.funcAddTitle = "添加下级菜单";
			addFuncInfoModal.$promise.then(addFuncInfoModal.show);
			$scope.addFuncInfoParentId = type;
			var changeFuncParams = mergeStr('updateFuncId',type);
			var changeFuncData = mergeReauestData('FuncController','findFuncInfoById', changeFuncParams);
			var changeFuncResult = sendPost($http, changeFuncData, $q);
			changeFuncResult.then(function(success) {
								var changeFuncResponse = StrParesJSON(success);
								document.getElementById("funcName").value = changeFuncResponse.result.name;
								$scope.addFuncInfoParentName = changeFuncResponse.result.name;
							}, function(error) {
								console.info(error);
			});
		}
		
	};

	var addParentInfoModal = $modal({
		scope : $scope,
		templateUrl : 'packages/sys/views/func/tree.html',
		show : false,
		backdrop : "static",
		controller : 'selectParentFunc'		
	});
	$scope.chooseParentFunc = function() {
		addParentInfoModal.$promise.then(addParentInfoModal.show);
	}
	var iconModal = $modal({
		scope : $scope,
		templateUrl : 'packages/sys/views/func/icon.html',
		show : false,
		backdrop : "static",
		controller : 'selectIcon',
		placement : "center"
	});
	$scope.chooseIcon = function() {
		iconModal.$promise.then(iconModal.show);
	}

    $scope.$on('iconSelectedToparent', function(event,icon) {
		$scope.icon='fa '+icon;
		iconModal.$promise.then(iconModal.hide);
	});
	$scope.$on('funcSelectTo-parent', function(event, funcName, funcId) {
		$scope.addFuncInfoParentName = funcName;
		$scope.addFuncInfoParentId = funcId;
		$scope.updateFuncInfoParentId = funcId;
	});


	// 隐藏弹窗
	$scope.hideWin = function() {
		updateFuncModal.$promise.then(updateFuncModal.hide);
		addFuncInfoModal.$promise.then(addFuncInfoModal.hide);
		deleteOtherModal.$promise.then(deleteOtherModal.hide);
	}
	//是否可用
	$scope.getFuncIsVisible = function() {
		if ($scope.funcIsVisibleJosns == undefined) {
			var responseDictResult = getSelectValueByDictList($http, $q,
					'WHETHER_TYPE', 'WHETHER_CODE');
			responseDictResult.then(function(success) {
				$scope.funcIsVisibleJosns = StrParesJSON(success).result;
			}, function(error) {
				console.info(error);
			});
		}
	}
	//菜单类型
	$scope.getFuncTypeCode = function() {
		if ($scope.funcTypeCodeJosns == undefined) {
			var responseDictResult = getSelectValueByDictList($http, $q,
					'FUNC_TYPE', 'NATURE');
			responseDictResult.then(function(success) {
				$scope.funcTypeCodeJosns = StrParesJSON(success).result;
			}, function(error) {
				console.info(error);
			});
		}
	}
	
	
}
/**
 * 修改弹框
 * 
 * @param $scope
 * @param $filter
 * @param $modal
 * @param $http
 * @param $q
 */
function updateFuncCtl($scope, $filter, $modal, $http, $q,$rootScope) {

	$scope.getFuncTypeCode();
	$scope.getFuncIsVisible();
	
	var detailsOrEidt = $scope.$parent.detailsOrEidt;
	if (detailsOrEidt == "edit") {
		$scope.editFuncSumbit = true;
	} else {
		$scope.viewFuncShow = true;
	}
	var changeFuncParams = mergeStr('updateFuncId', $scope.$parent.updateFuncId);
	var changeFuncData = mergeReauestData('FuncController', 'findFuncInfoById',
			changeFuncParams);
	var changeFuncResult = sendPost($http, changeFuncData, $q);
	changeFuncResult.then(function(success) {
						var changeFuncResponse = StrParesJSON(success);
						$scope.updateFuncInfo = changeFuncResponse.result;
                        $scope.icon = $scope.updateFuncInfo.icon;
						$scope.funcIsVisible = $scope.updateFuncInfo.isVisible+"";
						$scope.funcTypeCode = $scope.updateFuncInfo.typeCode;
						if ($scope.updateFuncInfo.parent != undefined
								&& $scope.updateFuncInfo.parent != null) {
							$scope.updateFuncInfoParentName = $scope.updateFuncInfo.parent.name;
							$scope.updateFuncInfoParentId = $scope.updateFuncInfo.parent.id;
						}
					}, function(error) {
						console.info(error);
					});
	
	//当功能类型值获取焦点是隐藏错误信息
	$scope.hideErrorTypeInfo = function(){
		  $scope.funcTypeAndCode = false;
		  $scope.funcTypeAndLink = false;
	}

	/*
	 * 验证url
	 */
	$scope.checkUrl = function() {
		 var  re =new RegExp("/");
		 var url = document.getElementById("funcUpUrlId").value;
		 if(url != undefined && url != "" && url.length >0){
			 if(re.test(url)){
		            //正常---》包含"/"
			     $scope.urlError = false;
	         }else{
	        	 $scope.urlError = true;
	         }
		 }else{
			 $scope.urlError = false;
		 }
	}
	$scope.urlFocus = function() {
		 $scope.urlError = false;
		 $scope.funcTypeAndLink = false;
	}
	
	$scope.urlChange = function() {
		$scope.urlError = changeUrl($scope.updateFuncInfo.url);
	}

	  //失去焦点检验排序
	  $scope.checkSort = function() { 
		  $scope.orderError=checkNumber($scope.updateFuncInfo.order);
	  }
	  //值发生改变验证排序
	  $scope.changeSort = function(){
		  $scope.orderError= false;
	  }
	
	  //获取焦点隐藏弹框
	  $scope.hideErrorCode = function(){
		  $scope.funcTypeAndCode = false;
		  $scope.funcIsExit = false;  
		  $scope.funcIsExitYes =false;
	  }
	$scope.orderError = false;
	$scope.urlError = false;
	$scope.funcTypeAndLink =false;
	$scope.funcTypeAndCode = false;
	$scope.updateFuncs = function(){
		$scope.submitted = true;
			var urlValue = document.getElementById("funcUpUrlId").value;
			var codeValue = document.getElementById("codeUpId").value;
		
			if($scope.funcTypeCode =="LINK" && (urlValue == undefined || urlValue == null || urlValue == "")){
				$scope.funcTypeAndLink = true;
				$scope.funcTypeAndCode = false;
			}else if($scope.funcTypeCode  =="FEATURE" && (codeValue == undefined || codeValue == null || codeValue == "")){
				$scope.funcTypeAndCode = true;
				$scope.funcTypeAndLink = false;
			}
			if ($scope.updaFuncsForm.$valid &&  $scope.orderError == false && $scope.urlError == false && 
					$scope.funcTypeAndLink == false && $scope.funcTypeAndCode == false) {
				        var updateFormData = $scope.updateFuncInfo;
						if($scope.$parent.updateFuncInfoParentId != undefined){
							updateFormData.parent = {
									"id" : $scope.$parent.updateFuncInfoParentId,
							}
						}
			            updateFormData.icon = $scope.icon;
						updateFormData.typeCode = $scope.funcTypeCode;
						if($scope.funcIsVisible == "false"){
							updateFormData.isVisible = false;
						}else{
							updateFormData.isVisible = true;
						}
						updateFuncInfoParams = mergeJson("updateFunc", updateFormData);
						var updateFuncData = mergeReauestData('FuncController', 'update',
								updateFuncInfoParams);
						var updateFuncResult = sendPost($http, updateFuncData, $q);
		
						updateFuncResult.then(function(success) {
							
							var func = JSON.parse(success).result;
							if(func.isEnabled == true){
								//此用户已经存在
								$scope.funcIsExit = true;
							}else if(func.isEnabled == false){
								//此用户已经存在，你可以进行恢复
								$scope.funcIsExitYes = true;
							}else{
								$scope.$parent.$parent.hideWin();
								$scope.$parent.$parent.funcPageService("true");
								$modal({
									scope : $scope,
									title : "提示",
									templateUrl : 'packages/sys/views/func/tip.html',
									content : '菜单修改成功',
									show : true,
									backdrop : "static"
								});
								$scope.updaFuncsForm = undefined
							}
						}, function(error) {
							var errorInfo = JSON.parse(error).errMsg;
							if(errorInfo.indexOf("类型为链接时") != -1){
								$scope.upFuncErrMsg = true;
								$scope.errorUpFuncMsg = "当选择的类型为链接时,链接值不能为空";
								$scope.updaFuncsForm.funcCheckLinkAndLinkValue = true;
							}
						});
					}else{
						$scope.updaFuncsForm.submitted = true;
					}
	}
    var iconModal = $modal({
		scope : $scope,
		templateUrl : 'packages/sys/views/func/icon.html',
		show : false,
		backdrop : "static",
		controller : 'selectIcon',
		placement : "center"
	});
	$scope.chooseIcon = function() {
		iconModal.$promise.then(iconModal.show);
	}
	 $scope.$on('iconSelectedToparent', function(event,icon) {
		$scope.icon='fa '+icon;
		iconModal.$promise.then(iconModal.hide);
	 });
	 	//关闭修改弹框
	$scope.closefuncEidt = function(){
		if($scope.updaFuncsForm.$pristine){//判断是否有修改 如果为true代表没有修改执行关闭
			$scope.$parent.$parent.hideWin();
			$scope.updaFuncsForm = undefined;
			fullscreenRemove();
		}else{//如果是false 代表有修改 显示提示框
			$rootScope.closeModel.$promise.then($rootScope.closeModel.show);	
		}	
	}
	$rootScope.$on('hideModel', function(event, hideModel) {//确定丢弃输入的修改内容后需要执行的方法
		if ($scope.updaFuncsForm != undefined) {
			if (hideModel) {					
				$scope.updateFuncs(); // 执行提交方法
			}else{
				$scope.$parent.$parent.hideWin();
				$scope.updaFuncsForm = undefined;
				fullscreenRemove();
			};

		};		
			
	})
}
/**
 * 添加弹框
 * 
 * @param $scope
 * @param $filter
 * @param $modal
 * @param $http
 * @param $q
 */
function addFuncInfo($scope, $filter, $modal, $http, $q,$rootScope) {
	
	if ($scope.$parent.addFuncInfoParentId != undefined) {
		$scope.parentFuncFirst = false;
		$scope.parentFuncSecond = true;
	}else{
		$scope.parentFuncFirst = false;
		$scope.parentFuncSecond = true;
	}
	
	
	$scope.addFuncInfoParentName = "功能管理";
	$scope.addFuncInfoParentId = 0;

	$scope.getFuncTypeCode();
	$scope.getFuncIsVisible();
	$scope.funcIsVisible = "true";
	$scope.funcTypeCode = "FEATURE";
	
	var addParentInfoModal = $modal({
		scope : $scope,
		templateUrl : 'packages/sys/views/func/tree.html',
		show : false,
		backdrop : "static",
		container : '#addFuncInfoWin',
		controller : 'selectParentFunc'
	});
	$scope.chooseParentFunc = function() {
		addParentInfoModal.$promise.then(addParentInfoModal.show);
	}
	/*
	 * 验证url
	 */
	$scope.checkUrl = function() {
		 var  re =new RegExp("/");
		 var url = document.getElementById("funcUrlId").value;
		 if(url != undefined && url.length >0){
			 if(re.test(url)){
		            //正常---》包含"/"
			     $scope.urlError = false;
	         }else{
	        	 $scope.urlError = true;
	         }
		 }else{
			 $scope.urlError = false;
		 }
	}
	$scope.urlFocus = function() {
		 $scope.funcTypeAndLink = false;
	}
	$scope.urlChange = function() {
		$scope.urlError = changeUrl($scope.formData.url);
	}
	
	//当功能类型值获取焦点是隐藏错误信息
	$scope.hideErrorTypeInfo = function(){
		  $scope.funcTypeAndCode = false;
		  $scope.funcTypeAndLink = false;
	}
	
	
  //获取焦点隐藏弹框
  $scope.hideErrorCode = function(){
	  $scope.funcTypeAndCode = false;
	  $scope.funcIsExit = false;  
	  $scope.funcIsExitYes =false;
  }
  
  //失去焦点验证code是否存在
  $scope.verifyCodeIsExit = function(){
		//1.验证code是否已经存在
		var getFuncExitByCode = mergeJson('code', $scope.formData.code);
		var getFuncData = mergeReauestData('FuncController',
				'queryFuncByCode', getFuncExitByCode);
		var getFuncResult = sendPost($http, getFuncData, $q);
		getFuncResult.then(function(success){
			var func = JSON.parse(success).result;
			//已存在此用户
			if(func.isEnabled == true){
				//此用户已经存在
				$scope.funcIsExit = true;
			}else if(func.isEnabled == false){
				//此用户已经存在，你可以进行恢复
				$scope.funcIsExitYes = true;
			}
		})
  }
  
	
	  //失去焦点检验排序
	  $scope.checkSort = function() { 
		  $scope.orderError=checkNumber($scope.formData.order);
	  }
	  //值发生改变验证排序
	  $scope.changeSort = function(){
		  $scope.orderError= false;
	  }
	$scope.orderError = false;
	$scope.urlError = false;
	$scope.funcTypeAndLink =false;
	$scope.funcTypeAndCode = false;
	$scope.funcIsExit = false;
	$scope.funcIsExitYes = false;
	$scope.addFuncs = function(funcInfo) {
		$scope.submitted = true;
		var urlValue = document.getElementById("funcUrlId").value;
		var codeValue = document.getElementById("codeId").value;
		if($scope.funcTypeCode =="LINK" && (urlValue == undefined || urlValue == null || urlValue == "")){
			$scope.funcTypeAndLink = true;
			$scope.funcTypeAndCode = false;
		}else if($scope.funcTypeCode  =="FEATURE" && (codeValue == undefined || codeValue == null || codeValue == "")){
			$scope.funcTypeAndCode = true;
			$scope.funcTypeAndLink = false;
		}
		if(codeValue!=undefined && codeValue!=null && codeValue!=""){
			var getFuncExitByCode = mergeJson('code',codeValue);
			var getFuncData = mergeReauestData('FuncController',
					'queryFuncByCode', getFuncExitByCode);
			var getFuncResult = sendPost($http, getFuncData, $q);
			getFuncResult.then(function(success){
				var func = JSON.parse(success).result;
				//已存在此用户
				if(func.isEnabled == true){
					//此用户已经存在
					$scope.funcIsExit = true;
				}else if(func.isEnabled == false){
					//此用户已经存在，你可以进行恢复
				    $scope.funcIsExitYes = true;
				}else{
					$scope.addFuncFrom();
				}
			})
		}else{
			$scope.addFuncFrom();
		}
	}
	//添加
	$scope.addFuncFrom = function(){
		if ($scope.addFuncsForm.$valid && $scope.orderError == false && $scope.urlError == false
				&& $scope.funcTypeAndLink == false  && $scope.funcTypeAndCode == false 
				&& $scope.funcIsExit == false && $scope.funcIsExitYes == false) {
			var funcFormData = $scope.formData;
            if($scope.icon != null){
				funcFormData.icon = $scope.icon;
            }
			if( $scope.$parent.addFuncInfoParentId != null &&  $scope.$parent.addFuncInfoParentId != undefined){
				funcFormData.parent = {
						"id" : $scope.$parent.addFuncInfoParentId,
				}
			}else{
				funcFormData.parent = {
						"id" : $scope.addFuncInfoParentId,
				}
			}
			funcFormData.typeCode = $scope.funcTypeCode;
			if($scope.funcIsVisible == "false"){
				funcFormData.isVisible = false;
			}else{
				funcFormData.isVisible = true;
			}
			addFuncInfoParams = mergeJson("addFunc", funcFormData);
			var addFuncData = mergeReauestData('FuncController', 'addFunc',
					addFuncInfoParams);
			var deleteFuncResult = sendPost($http, addFuncData, $q);
			deleteFuncResult.then(function(success) {
				var deleteFuncResponse = StrParesJSON(success);
				$scope.delFuncInfoFlag = deleteFuncResponse.result;
				if (deleteFuncResponse.result == true) {
					$scope.$parent.$parent.hideWin();
					$scope.$parent.$parent.funcPageService("true");
				}
				$modal({
					scope : $scope,
					title : "提示",
					templateUrl : 'packages/sys/views/func/tip.html',
					content : '菜单添加成功',
					show : true,
					backdrop : "static"
				});
				$scope.addFuncsForm = undefined
			}, function(error) {
				var errorInfo = JSON.parse(error).errMsg;
				if(errorInfo.indexOf("类型为链接时") != -1){
					$scope.funcErrMsg = true;
					$scope.errorFuncMsg = "当选择的类型为链接时,链接值不能为空";
					$scope.addFuncsForm.funcCheckLinkAndLinkValue = true;
				}
			});
		}else{
			$scope.addFuncsForm.submitted = true;
		}
	}
	//关闭添加弹框
	$scope.closefuncAdd = function(){
		if($scope.addFuncsForm.$pristine){//判断是否有修改 如果为true代表没有修改执行关闭
			$scope.$parent.$parent.hideWin();
			$scope.addFuncsForm=undefined;
			fullscreenRemove();		
		}else{//如果是false 代表有修改 显示提示框
			$rootScope.closeModel.$promise.then($rootScope.closeModel.show);	
		}	
	}
	$rootScope.$on('hideModel', function(event, hideModel) {//确定丢弃输入的修改内容后需要执行的方法
		if ($scope.addFuncsForm!=undefined) {
			if (hideModel) {					
				$scope.addFuncs(); // 执行提交方法
			}else{
				$scope.$parent.$parent.hideWin();
				$scope.addFuncsForm=undefined;
				fullscreenRemove();
			};	
		};		
			
	})
}
/**
 * 
 * @param $scope   请求tree
 * @param $filter
 * @param $modal
 * @param $http
 * @param $q
 */
function selectParentFunc($scope, $filter, $modal, $http, $q) {
	/*
	 * 请求jsonTree
	 */
	if($scope.updateFuncId != undefined && $scope.updateFuncId != null){
		var hideFunxIdParams = mergeJson("funcId", $scope.updateFuncId)
		
		$scope.getHideFuncTree = function() {
			getTreeAndView($scope, $http, $q, 'FuncController', 'getFuncTreeParent',hideFunxIdParams);
		}
		$scope.getHideFuncTree();
	}else{
		$scope.getFuncTree = function() {
			getTreeAndView($scope, $http, $q, 'FuncController', 'getFuncTreeParent',"");
		}
		$scope.getFuncTree();
	}
	$scope.funcName = null;
	$scope.funcId = null;
	$scope.chooseFunc = function(funcName, funcId ,eventTarget) {
		if ($scope.funcId==funcId) {
               angular.element(eventTarget.currentTarget).addClass('tree-selected')
		}else{
			   angular.element('.treeLink').removeClass('tree-selected')
		};
		$scope.funcName = funcName;
		$scope.funcId = funcId;
	}
	$scope.viewChooseParentFunc = function() {
		if ($scope.funcName != null) {
			document.getElementById("funcName").value = $scope.funcName;
			if (document.getElementById("funcNameApp") != null && document.getElementById("funcNameApp") !=undefined) {
				document.getElementById("funcNameApp").value = $scope.funcName;
			   }
		}

		$scope.$emit('funcSelectTo-parent', $scope.funcName, $scope.funcId);
	}
	$scope.clearParentFunc = function() {
		$scope.$emit('funcSelectTo-parent', null, null);
		fullscreenRemove();
	}
}
function selectIcon($scope){
    $scope.iconClass='';	
	$scope.iconSelected= function(iconName){
		angular.element('i.btn.btn-primary').removeClass('btn-primary');
		angular.element(iconName.target).addClass('btn-primary');
		$scope.iconClass=angular.element(iconName.target).children('input').attr('value');
	}

	$scope.iconSelectedSave = function() {
		$scope.$emit('iconSelectedToparent', $scope.iconClass);
	}
	

}