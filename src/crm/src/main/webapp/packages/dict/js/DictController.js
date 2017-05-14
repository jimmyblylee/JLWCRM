/**
 * 字典管理页面控制器
 * 
 */
function dictListCtrl($scope, $filter, $modal, $log, $http, $q, $timeout,$aside) {
	$scope.dictList = [];
	$scope.itemsPerPage = itemsPerPage; //每页显示5条
	$scope.Pagenum = Pagenum;
	$scope.dictStatus = "true";
	var ispc=IsPC();
	
	//获取禁用、可用
	$scope.getIsEnabled = function() {
		if ($scope.IsEnabledJson == undefined) {
			var responseDictResult = getSelectValueByDictList($http, $q,
					'STATUS_TYPE', 'STATUS_CODE');
			responseDictResult.then(function(success) {
				$scope.IsEnabledJson = StrParesJSON(success).result;
			}, function(error) {
				console.info(error);
			});
		}
	};
	
	$scope.getIsEnabled();
	
	// 添加弹框中点击取消后清空数据
	$scope.hide = function() {
		$scope.dictParentId = null;
		$scope.dictParentValue = null;
	};

	// 回车查询
	$scope.keyup = function($event) {
		if ($event.keyCode == 13) {
			$scope.SearchDictList("true");
		}
	}
	$scope.treeAPPListName="";
	$scope.treeAPPListNameFUN = function($event) {	
		var getTreeData = mergeReauestData("DictController","getDictTreeParent","");
		var promiseResult = sendPost($http, getTreeData, $q);
		promiseResult.then(function(value) {		
			var respData = StrParesJSON(value);
			$scope.treedata = respData.result;	
			$scope.treeAPPListName = $scope.treedata[0].label
			
		}), function(error) {
			console.info(error);
		};
    }
    $scope.treeAPPListNameFUN();
	
		//树Aside的显示与隐藏
		var treeAside = $aside({
			scope: $scope, 
			template: 'packages/dict/views/tree.html',
			container:"#pageDictId",
			show:false  
		}); 
		$scope.treeAsideShow = function(){
			 treeAside.$promise.then(function() {
			    treeAside.show();
			})
		}
	//点击树节点查询
	$scope.clickTreeNode =  function(dictId,eventTarget){
		if ($scope.dictTreeId == dictId) {
               angular.element(eventTarget.currentTarget).addClass('tree-selected')
		}else{
			angular.element('.treeLink').removeClass('tree-selected')
		};
		$scope.dictTreeId = dictId;
		$scope.requestDictList("true");
		$scope.treeAPPListName=eventTarget.currentTarget.innerText
		treeAside.$promise.then(function() {
		     treeAside.hide();
		  })
	}
	
	//分页请求  --》改变分页参数
	$scope.changPageValue = function(){
		$scope.currentPage = 1;
		$scope.requestDictList("true");
	}
	
	//还原数据
	$scope.refreshDictList = function(){
		$scope.keyWords = "";
		$scope.dictStatus ="true"
		$scope.currentPage = 1;		
		$scope.itemsPerPage = Pagenum[0];
		$scope.requestDictList("true");
		$scope.order = "order";
		$scope.reverse = false;
		sort_global($scope, $scope.order, $scope.reverse,null);
	}
	
	//添加、修改、删除  --》进行查询
	$scope.pageJump = function(dictParentId){
		if(dictParentId !=null && dictParentId != undefined){
			  $scope.dictTreeId = dictParentId;
		}
	    $scope.$broadcast("to-child","child");
		$scope.requestDictList("true");
	}
	//恢复 成功后页面跳转
	$scope.recoverPageJump = function(dictParentId){
		if(dictParentId !=null && dictParentId != undefined){
			  $scope.dictTreeId = dictParentId;
		}
	    $scope.$broadcast("to-child","child");
		$scope.requestDictList("false");
	}
	/*
	 * 分页请求,treeId：树状图的id
	 */
	$scope.requestDictList = function(isEnabled) {
		//清除选择的多选框
		document.getElementById("parent").checked = false;
		if($scope.dictStatus =="true" && isEnabled=="false"){
			$scope.dictStatus = "true";
		}else if($scope.dictStatus !="true" && isEnabled=="false"){
			$scope.dictStatus = "false";
		}
		if($scope.isEnabledTrueShow && isEnabled == "true"){
			$scope.dictStatus = "true";
		}else if($scope.isEnabledFalseShow && isEnabled == "true"){
			$scope.dictStatus = "false";
		}
		if($scope.dictStatus == "true"){
			$scope.dictIsEnabled = true;
		}else{
			$scope.dictIsEnabled = false;
		}
		var dictAddData = {
			"code" : $scope.keyWords,
			"isEnabled" : $scope.dictIsEnabled,
			"id" : $scope.dictTreeId
		};
		if(!ispc){
			$scope.itemsPerPage=30;
			$scope.currentPage = 1;			
		}
		var dict = ObjParesJSON(dictAddData);
		var queryResqData = pageService($http, $q, 'DictController',
				'queryFuzzyAllDict', $scope.currentPage, $scope.itemsPerPage,
				dict);
		queryResqData.then(function(success) {
			if ($scope.dictStatus == "true") {
				$scope.isEnabledTrueShow = true;
				$scope.isEnabledFalseShow = false;
			} else {
				$scope.isEnabledTrueShow = false;
				$scope.isEnabledFalseShow = true;
			}
			var queryRespResult = StrParesJSON(success);
			$scope.dictList = queryRespResult.result;
			$scope.totalItems = queryRespResult.total;
		}, function(error) {
			console.info(error);
		});

	};
	$scope.requestDictListApp = function(isEnabled) {
		//清除选择的多选框
		document.getElementById("parent").checked = false;
		if($scope.dictStatus =="true" && isEnabled=="false"){
			$scope.dictStatus = "true";
		}else if($scope.dictStatus !="true" && isEnabled=="false"){
			$scope.dictStatus = "false";
		}
		if($scope.isEnabledTrueShow && isEnabled == "true"){
			$scope.dictStatus = "true";
		}else if($scope.isEnabledFalseShow && isEnabled == "true"){
			$scope.dictStatus = "false";
		}
		if($scope.dictStatus == "true"){
			$scope.dictIsEnabled = true;
		}else{
			$scope.dictIsEnabled = false;
		}
		var dictAddData = {
			"code" : $scope.keyWords,
			"isEnabled" : $scope.dictIsEnabled,
			"id" : $scope.dictTreeId
		};
		$scope.itemsPerPage=5;
		var totalItemsAPP=$scope.totalItems;
		var array=$scope.dictList;
		$scope.totalItemsAPP=Math.ceil(totalItemsAPP/5);
		$scope.currentPage=(array.length/5)+1;
		if ($scope.currentPage <= $scope.totalItemsAPP) {
			var dict = ObjParesJSON(dictAddData);
			var queryResqData = pageService($http, $q, 'DictController',
					'queryFuzzyAllDict', $scope.currentPage, $scope.itemsPerPage,
					dict);
			queryResqData.then(function(success) {
				if ($scope.dictStatus == "true") {
					$scope.isEnabledTrueShow = true;
					$scope.isEnabledFalseShow = false;
				} else {
					$scope.isEnabledTrueShow = false;
					$scope.isEnabledFalseShow = true;
				}
				var queryRespResult = StrParesJSON(success);
				$scope.dictList =array.concat(queryRespResult.result); 			
			}, function(error) {
				console.info(error);
			});
		}
		

	};
	$scope.dictTreeId =1;
	$scope.requestDictList("true");
	
	scrollwatch($scope, $timeout);
    window.onscroll = function(){
    	if (!ispc) {
	    	isscrollbottom=scrollbottom();
	      　if(isscrollbottom){
	      		$scope.requestDictListApp()
	        }
	    }

   };	
	// 检索查询
	$scope.SearchDictList = function() {
		$scope.currentPage = 1;
		$scope.requestDictList('false');
	};
	// 刷新页面
	$scope.RefreshPage = function() {
		//请求列表
		$scope.requestDictList("true");
		$scope.removeCheckBox();
	};
	
	// 清空复选框
	$scope.removeCheckBox = function() {
		var dictDeleteList_check = document.getElementsByName("dictCheckChild");
		for (var i = 0; i < dictDeleteList_check.length; i++) {
			dictDeleteList_check[i].checked = false;
		}
	};

	// 排序
	$scope.order = "order"; // 排序初始查询条件
	$scope.reverse = false;
	$scope.sort_by = function(newSortingOrder) {
		sort_global($scope, $scope.order, $scope.reverse, newSortingOrder);
	};

	
	var addDictModal = $modal({
		scope : $scope,
		templateUrl : 'packages/dict/views/add_dict.html',
		show : false,
		container : '#pageDictId',
		controller : 'addPageController',
		backdrop : 'static'
	});
	// 点击添加按钮触发 显示添加页面弹出框
	$scope.addDictModal = function() {
		$scope.updateDictId = null;
		$scope.dictAddParentId = null,
		$scope.dictAddParentValue = null;
		addDictModal.$promise.then(addDictModal.show);
	};

	// 关闭添加弹框
	$scope.$on('to-parent-add', function() {
		addDictModal.$promise.then(addDictModal.hide);
	});

	// 添加字典键值
	var addDictKeyModal = $modal({
		scope : $scope,
		templateUrl : 'packages/dict/views/add_dictkey.html',
		show : false,
		container : '#pageDictId',
		controller : 'addKeyPageController',
		backdrop : 'static'
	});
	// 添加键值的时候触发
	$scope.addDictKeyModal = function(dictId) {
		$scope.updateDictId = null;
		$scope.dictAddParentId = null,
		$scope.dictAddParentValue = null;;
		addDictKeyModal.$promise.then(addDictKeyModal.show);
		$scope.dictkKeyid = dictId;
	};

	// 关闭添加键值弹框
	$scope.$on('to-parent-addkey', function() {
		
		addDictKeyModal.$promise.then(addDictKeyModal.hide);
	});
	
	var updateDictModal = $modal({
		scope : $scope,
		templateUrl : 'packages/dict/views/update_dict.html',
		show : false,
		container : '#pageDictId',
		controller : 'updatePageController',
		backdrop : 'static'
	});
	//显示修改页面弹出框
	$scope.updateDictModal = function(dictId) {
		updateDictModal.$promise.then(updateDictModal.show);
		$scope.updateDictId = dictId;
	};
	
	var viewDictModal = $modal({
		scope : $scope,
		templateUrl : 'packages/dict/views/details_dict.html',
		show : false,
		container : '#pageDictId',
		controller : 'updatePageController',
		backdrop : 'static'
	});
	//显示修改页面弹出框
	$scope.viewDictModal = function(dictId) {
		viewDictModal.$promise.then(viewDictModal.show);
		$scope.updateDictId = dictId;
	};
	$scope.closeviewDictModal = function() {
		viewDictModal.$promise.then(viewDictModal.hide);
		fullscreenRemove();
	};

	// 关闭修改弹框
	$scope.$on('to-parent-update', function() {
		updateDictModal.$promise.then(updateDictModal.hide);
	});

	var dictDeleteModal = $modal({
		scope : $scope,
		templateUrl : 'packages/dict/views/del_dict.html',
		container : '#pageDictId',
		show : false,
		backdrop : "static"

	});
	// 点击删除按钮
	$scope.singleDelete = function(dictId) {
		dictDeleteModal.$promise.then(dictDeleteModal.show);
		$scope.delIsDictId = dictId;
	};
	
	// 删除
	$scope.delDidtByid = function() {
		var deleteDictJsonParams = mergeJson('dictId', $scope.delIsDictId);
		reqDictData = mergeReauestData('DictController', 'deleteDict',
				deleteDictJsonParams);
		var deleteRespResult = sendPost($http, reqDictData, $q);
		deleteRespResult.then(function(success) {
			dictDeleteModal.$promise.then(dictDeleteModal.hide);
			$modal({
				scope : $scope,
				templateUrl : 'packages/dict/views/play_dict.html',
				title : '提示',
				content : '字典删除成功',
				how : true,
				backdrop : "static"
			});
			if ($scope.dictList.length == 0) {
				$scope.currentPage - 1;
				$scope.pageJump()
			} else {
				$scope.pageJump();
			}
		});
	};

	//恢复弹框
	$scope.recoverDictState = function(dictId) {
		promptRecover.$promise.then(promptRecover.show);
		$scope.recvDictId = dictId;
	};
	var promptRecover = $modal({
		scope : $scope,
		templateUrl : 'packages/dict/views/recover_dict.html',
		show : false,
		backdrop : 'static'
	});

	// 恢复
	$scope.recoverDictByid = function() {
		var deleteDictJsonParams = mergeJson('dictId', $scope.recvDictId);
		reqDictData = mergeReauestData('DictController', 'reciveDict',
				deleteDictJsonParams);
		var deleteRespResult = sendPost($http, reqDictData, $q);
		deleteRespResult.then(function(success) {
			$scope.dictStatus = "false";
			if ($scope.dictList.length == 0) {
				$scope.currentPage - 1;
				$scope.recoverPageJump();
			} else {
				$scope.recoverPageJump();
			}
			
			promptRecover.$promise.then(promptRecover.hide);
			$modal({
				scope : $scope,
				title : '提示',
				content : "字典恢复成功",
				templateUrl : 'packages/dict/views/play_dict.html',
				how : true,
				backdrop : 'static'
			});

		});
	};

	/**
	 * Description：复选框 全选/全不选
	 * 
	 * @author name：wangyishuai
	 */
	$scope.checkAll = function(cancel) {
		var parent_check = document.getElementById("parent");
		if (cancel == 1) {
			parent_check.checked = false;
		}
		var child_check = document.getElementsByName("dictCheckChild");
		for (var i = 0; i < child_check.length; i++) {
			if (child_check[i].type == "checkbox") {
				child_check[i].checked = parent_check.checked;
			}
		}
	};

	/**
	 * Description：批量删除用户
	 * 
	 * @author name：wangyishuai
	 */
	$scope.deleteDicts = function(deleteParam) {
		var dictIdJson = [];
		var deleteJson = {};
		if (deleteParam == 'deleteDicts') {
			var dictIds = document.getElementsByName('dictCheckChild');
			var dictIdJsonIndex = 0;
			for (var i = 0; i < dictIds.length; i++) {
				if (dictIds[i].checked) {
					dictIdJson[dictIdJsonIndex] = parseInt(dictIds[i].value);
					dictIdJsonIndex++;
				}
			}
			if (isNaN(dictIdJson[0])) {
				dictIdJson = dictIdJson.slice(1);
			}
			$scope.deleteDictIds = dictIdJson;
			if (dictIdJson.length > 0) {
				$scope.warnMessage = "确认删除选中字典？";
				$scope.confirmShow = true;
				$scope.confirmErroeShow = true;
			} else {
				$scope.warnMessage = "请至少选择一条字典数据！";
				$scope.confirmShow = true;
				$scope.confirmErroeShow = false;
			}
		} else if (deleteParam == 'delete') {
			if ($scope.deleteDictIds != undefined
					&& $scope.deleteDictIds.length > 0) {
				dictIdJson = $scope.deleteDictIds;
			}
		} else if (deleteParam == 'clear') {
			$scope.deleteDictIds = [];
		}

		if (dictIdJson.length > 0 && deleteParam == 'delete') {
			deleteJson.dictIdJson = dictIdJson;
			deleteJson = JSON.stringify(deleteJson);
			var deleteDictResult = pageService($http, $q, 'DictController',
					'deleteDicts', null, null, deleteJson);
			deleteDictResult.then(function(success) {
				$scope.deleteDictIds = [];
				$scope.deleteDictId = '';
				$scope.deleteDict = StrParesJSON(success).result;
				$scope.pageJump();
				$modal({
					scope : $scope,
					templateUrl : 'packages/dict/views/play_dict.html',
					title : '系统提示',
					content : '批量删除字典删除成功',
					how : true,
					backdrop : 'static'
				});
			}, function(error) {
				console.info(error);
			});
		}

	};

	/**
	 * Description：批量恢复用户
	 * 
	 * @author name：wangyishuai
	 */
	$scope.recoverDicts = function(recoverParam) {
		var dictIdJson = [];
		var recoverJson = {};
		if (recoverParam == 'recoverDicts') {
			var dictIds = document.getElementsByName('dictCheckChild');
			var dictIdJsonIndex = 0;
			for (var i = 0; i < dictIds.length; i++) {
				if (dictIds[i].checked) {
					dictIdJson[dictIdJsonIndex] = parseInt(dictIds[i].value);
					dictIdJsonIndex++;
				}
			}
			if (isNaN(dictIdJson[0])) {
				dictIdJson = dictIdJson.slice(1);
			}
			$scope.recoverDictIds = dictIdJson;
			if (dictIdJson.length > 0) {
				$scope.warnMessage = "确认恢复选中字典？";
				$scope.confirmShow = true;
				$scope.confirmErroeShow = true;
			} else {
				$scope.warnMessage = "请至少选择一条字典数据！";
				$scope.confirmShow = true;
				$scope.confirmErroeShow = false;
			}

		} else if (recoverParam == 'recover') {
			if ($scope.recoverDictIds != undefined
					&& $scope.recoverDictIds.length > 0) {
				dictIdJson = $scope.recoverDictIds;
			}
		} else if (recoverParam == 'clear') {
			$scope.recoverDictIds = [];
		}
		if (dictIdJson.length > 0 && recoverParam == 'recover') {
			recoverJson.dictIdJson = dictIdJson;
			recoverJson = JSON.stringify(recoverJson);
			var recoverDictResult = pageService($http, $q, 'DictController',
					'recoverDicts', null, null, recoverJson);
			recoverDictResult.then(function(success) {
				$scope.recoverDictIds = [];
				$scope.recoverDictId = '';
				$scope.recoverDict = StrParesJSON(success).result;
				//$scope.requestDictList("false");
				$scope.recoverPageJump();
				$modal({
					scope : $scope,
					templateUrl : 'packages/dict/views/play_dict.html',
					title : '系统提示',
					content : '批量恢复字典成功',
					show : true,
					backdrop : "static"
				});
			}, function(error) {
				console.info(error);
			});
		}

	};
	// 创建选择上级字典弹出框
	var parentDictTreeModal = $modal({
		scope : $scope,
		templateUrl : 'packages/dict/views/parent_dict.html',
		show : false,
		container : '#pageDictId',
		backdrop : 'static',
		controller:'selectParentDictCtl'
	});
	// 选择上级字典触发
	$scope.chooseParentDict = function(editOrAddDict) {
		//得到这个值，不让点击
		var dictId = $scope.updateDictId;
		if(dictId != 1){
			parentDictTreeModal.$promise.then(parentDictTreeModal.show);
		}
		$scope.editOrAddDict = editOrAddDict;
	};
	
	//接受传过来的值啦
	$scope.$on('dictSelect-parent', function(event,dictId,dictName) {
		if(dictId != undefined && dictName != undefined){
			if($scope.editOrAddDict == "edit"){
				$scope.dictEditParentId = dictId,
				$scope.dictEditParentValue = dictName;
			}else if($scope.editOrAddDict == "add"){
				$scope.dictAddParentId = dictId,
				$scope.dictAddParentValue = dictName;
			}
		}
		parentDictTreeModal.$promise.then(parentDictTreeModal.hide);
	});

	/***************   前端测试   ***********************/
	
	// 上级弹框测试
	$scope.setparentDictTreeModal = function(_parentDictTreeModal) {
		parentDictTreeModal = _parentDictTreeModal;
	};

	// 添加测试
	$scope.setaddDictModal = function(_addDictModal) {
		addDictModal = _addDictModal;
	};

	$scope.setupdateDictModal = function(_updateDictModal) {
		updateDictModal = _updateDictModal;
	};

	$scope.setdictDeleteModal = function(_dictDeleteModal) {
		dictDeleteModal = _dictDeleteModal;
	};
	
	//获取禁用、可用
	$scope.getDictType = function(){
		if ($scope.dictTypeJson == undefined) {
			var responseDictResult = getSelectValueByDictList($http, $q,
					'WHETHER_TYPE', 'WHETHER_CODE');
			responseDictResult.then(function(success) {
				$scope.dictTypeJson = StrParesJSON(success).result;
			}, function(error) {
				console.info(error);
			});
		}
	};
}

// 添加弹窗控制器
function addPageController($scope, $modal, $http, $q,$rootScope) {
	$scope.dictAddParentValue = "字典管理";
	$scope.dictAddParentId = 1;
	
	$scope.getDictType();
	$scope.dictType = "true";
	$scope.submitDictForm = function(){		
		// 添加表单验证
		if ($scope.dictModelForm.$valid && !$scope.orderIsNumber) {
			var dictFromData = $scope.dictAddData;
			// 判断是否有添加上级
			if ($scope.$parent.dictAddParentId != null && dictFromData != null) {
				dictFromData.parent = {
					"id" : $scope.$parent.dictAddParentId,
					"value" : $scope.$parent.dictAddParentValue
				};
			}else{
				dictFromData.parent = {
				    "id" : $scope.dictAddParentId,
				    "value" :$scope.dictAddParentValue
				};
			}
			
			if($scope.dictType == "true"){
				dictFromData.isNature = true;
			}else{
				dictFromData.isNature = false;
			}
			
			var addDictJsonParams = mergeJson('dict', dictFromData);
			var reqDictData = mergeReauestData('DictController', 'createDict',
					addDictJsonParams);
			var addDictResqResult = sendPost($http, reqDictData, $q);
			addDictResqResult.then(function(success) {
				
				$scope.$emit('to-parent-add');
				$modal({
					scope : $scope,
					templateUrl : 'packages/dict/views/play_dict.html',
					title : '提示',
					content : '字典添加成功',
					show : true,
					backdrop : "static"
				});
				$scope.dictModelForm =undefined
				if(dictFromData.parent != null){
					$scope.pageJump(dictFromData.parent.id);
				}else{
					$scope.pageJump(dictFromData.parent.id);
				}
			
				
			}, function(error) {
				var errorInfo = JSON.parse(error).errMsg;
				if(errorInfo.indexOf("在系统中已经拥有了一个字典") != -1){
					$scope.DictErrMsg = true;
					$scope.errorDictMsg = "系统中已存在一条实际值为"+dictFromData.code+"、类型为"+dictFromData.nature+"的记录";
					$scope.dictModelForm.dictCheckCodeAndNature = true;
				}
			});
		} else {
			$scope.dictModelForm.submitted = true;
		}		;
	};

	// 失去焦点检验排序
	$scope.checkOrder = function() {
		$scope.orderIsNumber = checkNumber($scope.dictAddData.order);
	}
	// 值发生改变验证排序
	$scope.changeOrder = function() {
		$scope.orderIsNumber = false;
	}
	$scope.addHidemodal = function(){
		if($scope.dictModelForm.$pristine){//判断是否有修改 如果为true代表没有修改执行关闭
			$scope.$emit('to-parent-add');
			$scope.dictModelForm = undefined;
			fullscreenRemove();
			}else{//如果是false 代表有修改 显示提示框
				$rootScope.closeModel.$promise.then($rootScope.closeModel.show);	
			}
		}			
		$rootScope.$on('hideModel', function(event, dictaddHidemodal) {//确定丢弃输入的修改内容后需要执行的方法	
			if ( $scope.dictModelForm!= undefined) {		
					if (dictaddHidemodal) {					
						$scope.submitDictForm(); // 执行提交方法
					}else{
						$scope.$emit('to-parent-add');
						$scope.dictModelForm = undefined;
						fullscreenRemove();					
					};
				};	
		})

}

// 添加键值控制器
function addKeyPageController($scope, $modal, $http, $q,$rootScope) {

	$scope.getDictType();
	$scope.dictType = "true";
	//$scope.dictkKeyid
	if ($scope.$parent.dictkKeyid != '' && $scope.$parent.dictkKeyid != null
			&& $scope.$parent.dictkKeyid != undefined) {
		
		for (key in $scope.$parent.dictList) {
			if ($scope.$parent.dictList[key].id == $scope.$parent.dictkKeyid) {
				
				$scope.dictKeyData = {
					"label" : $scope.$parent.dictList[key].label,
					"isNature" : $scope.$parent.dictList[key].isNature,
					"order" : $scope.$parent.dictList[key].order,
					"remarks" : $scope.$parent.dictList[key].remarks,
					"nature":$scope.$parent.dictList[key].nature
				};
				var dictParent = $scope.$parent.dictList[key];
				// 判断该字典是否存在上级
				if (dictParent != null) {
					$scope.dictKeyParentId = dictParent.id;
					$scope.dictKeyParentValue = dictParent.value;
				}
				break;
			}
		}
	}

	// 失去焦点检验排序
	$scope.checkOrder = function() {
		$scope.orderIsNumber = checkNumber($scope.dictKeyData.order);
	}
	// 值发生改变验证排序
	$scope.changeOrder = function() {
		$scope.orderIsNumber = false;
	}
	
	//提交表单数据
	$scope.submitDictForm = function() {
		$scope.dictKeyData.code = $scope.dictKeyDataCode;
		var dictFromData = $scope.dictKeyData;
		if ($scope.$parent.dictAddParentId != null) {
			dictFromData.parent = {
				"id" : $scope.$parent.dictAddParentId,
				"value" : $scope.$parent.dictAddParentValue
			};
		}else{
			//来自页面初始化的时候
			dictFromData.parent = {
					"id" : $scope.dictKeyParentId,
					"value" : $scope.dictKeyParentValue
			};
		}
		if($scope.dictType == "true"){
			dictFromData.isNature = true;
		}else{
			dictFromData.isNature = false;
		}
		// 添加键值表单验证
		if ($scope.dictModelForm.$valid && !$scope.orderIsNumber) {
			
			var addDictJsonParams = mergeJson('dict', dictFromData);
			var reqDictData = mergeReauestData('DictController', 'createDict',
					addDictJsonParams);
			var addDictResqResult = sendPost($http, reqDictData, $q);
			addDictResqResult.then(function(success) {
				$scope.$emit('to-parent-addkey');
				$modal({
					scope : $scope,
					templateUrl : 'packages/dict/views/play_dict.html',
					title : '提示',
					content : '添加键值成功！',
					show : true,
					backdrop : "static"
				});
				$scope.dictModelForm = undefined
				$scope.pageJump(dictFromData.parent.id);
			}, function(error) {
				var errorInfo = JSON.parse(error).errMsg;
				if(errorInfo.indexOf("在系统中已经拥有了一个字典") != -1){
					$scope.DictKeyErrMsg = true;
					 $scope.errorDictKeyMsg = "系统中已存在一条实际值为"+$scope.dictKeyData.code+"、类型为"+$scope.dictKeyData.nature+"的记录";
					$scope.dictModelForm.dictKeyCheckCodeAndNature = true;
				}
			});
		} else {
			$scope.dictModelForm.submitted = true;
		}
	};
	$scope.addKeyHidemodal = function(){
		if($scope.dictModelForm.$pristine){//判断是否有修改 如果为true代表没有修改执行关闭
			$scope.$emit('to-parent-addkey');
			$scope.dictModelForm = undefined;
			fullscreenRemove();
			}else{//如果是false 代表有修改 显示提示框
				$rootScope.closeModel.$promise.then($rootScope.closeModel.show);	
			}
		}			
		$rootScope.$on('hideModel', function(event, dictkeyHidemodal) {//确定丢弃输入的修改内容后需要执行的方法		
			if ($scope.dictModelForm != undefined) {
				if (dictkeyHidemodal) {					
					$scope.submitDictForm(); // 执行提交方法
				}else{
					$scope.$emit('to-parent-addkey');
					$scope.dictModelForm = undefined;
					fullscreenRemove();
				};
			};	
			
	})
}

// 修改弹窗控制器
function updatePageController($scope, $modal, $http, $q,$rootScope) {
	//调用键值
	$scope.getDictType();
	
	if ($scope.$parent.updateDictId != '' && $scope.$parent.updateDictId != null
			&& $scope.$parent.updateDictId != undefined) {
		
		for (key in $scope.$parent.dictList) {
			if ($scope.$parent.dictList[key].id == $scope.$parent.updateDictId) {
				
				$scope.dictUpdateData = JSON.parse(JSON
						.stringify($scope.$parent.dictList[key]));
				
				var dictParent = $scope.$parent.dictList[key].parent;
				// 判断该字典是否存在上级
				if (dictParent != null) {
					$scope.dictUpdateParentId = dictParent.id;
					$scope.dictUpdateParentValue = dictParent.value;
					
					if(dictParent.id == null){
						document.getElementById("dictUpIdFont")
					}
				}
				if($scope.$parent.dictList[key].isNature != null && $scope.$parent.dictList[key].isNature == true){
					$scope.dictType ="true";
				}else{
					$scope.dictType ="false";
				}
				break;
			}
		}
	}
	
	// 失去焦点检验排序
	$scope.checkOrder = function() {
		$scope.orderIsNumber = checkNumber($scope.dictUpdateData.order);
	}
	// 值发生改变验证排序
	$scope.changeOrder = function() {
		$scope.orderIsNumber = false;
	}
	$scope.submitDictForm = function() {
		var dictFromData = $scope.dictUpdateData;
		// 修改表单验证
		if ($scope.updateDictForm.$valid && !$scope.orderIsNumber) {
			
			if($scope.$parent.dictEditParentId!=undefined&&$scope.$parent.dictEditParentValue!=undefined){
				dictFromData.parent = {
						"id" : $scope.$parent.dictEditParentId,
						"value" : $scope.$parent.dictEditParentValue
				};
				
			}else if($scope.dictUpdateParentId != null && $scope.dictUpdateParentId != undefined && dictFromData != null) {
				dictFromData.parent = {
						"id" : $scope.dictUpdateParentId,
						"value" : $scope.dictUpdateParentValue
				};
			}else {
				dictFromData.parent = null;
			}
			if($scope.dictType == "true"){
				dictFromData.isNature =true;
			}else{
				dictFromData.isNature =false;
			}

			var updateDictJsonParams = mergeJson('dict', dictFromData);
			var reqDictData = mergeReauestData('DictController', 'updateDict',
					updateDictJsonParams);
			var updateResqResult = sendPost($http, reqDictData, $q);
			updateResqResult.then(function(success) {
				$scope.$emit('to-parent-update');
				
				$modal({
					scope : $scope,
					title : '提示',
					templateUrl : 'packages/dict/views/play_dict.html',
					content : '字典修改成功',
					show : true,
					backdrop : "static"
				});
				$scope.updateDictForm = undefined
				if(dictFromData.parent != null){
					$scope.pageJump(dictFromData.parent.id);
				}else{
					$scope.pageJump(1);
				}
			}, function(error) {
				var errorInfo =JSON.parse(error).errMsg;
				if(errorInfo.indexOf("在系统中已经拥有了一个字典") != -1){
					$scope.upDictErrMsg = true;
					$scope.errorUpDictMsg = "系统中已存在一条实际值为"+dictFromData.code+"、类型为"+dictFromData.nature+"的记录";
					$scope.updateDictForm.dictCheckCodeAndNature = true;
				}
			});
		} else {
			$scope.updateDictForm.submitted = true;
		}
	};
	$scope.editHidemodal = function(){
		if($scope.updateDictForm.$pristine){//判断是否有修改 如果为true代表没有修改执行关闭
			$scope.$emit('to-parent-update');
			$scope.updateDictForm = undefined;
			fullscreenRemove();	
			}else{//如果是false 代表有修改 显示提示框
				$rootScope.closeModel.$promise.then($rootScope.closeModel.show);	
			}
		}			
		$rootScope.$on('hideModel', function(event, dicteditHidemodal) {//确定丢弃输入的修改内容后需要执行的方法
		if ($scope.updateDictForm!= undefined) {
			if (dicteditHidemodal ) {					
				$scope.submitDictForm(); // 执行提交方法
			}else{
				$scope.$emit('to-parent-update');
				$scope.updateDictForm = undefined;
				fullscreenRemove();		
			};
		};			
				
	})
}
/**
 * 
 * @param $scope
 * @param $modal
 * @param $http
 * @param $q
 */
function selectParentDictCtl($scope, $modal, $http, $q){
	
	// 点击上级字典树：节点零时保存选择的二级字典
	$scope.saveChooseParentDict = function(dictParentId, dictParentValue,eventTarget) {
		if ($scope.dictParentId == dictParentId) {
               angular.element(eventTarget.currentTarget).addClass('tree-selected')
		}else{
			angular.element('.treeLink').removeClass('tree-selected')

		};
		$scope.dictParentId = dictParentId;
		$scope.dictParentValue = dictParentValue;
	};
	// 显示选择的二级字典
	$scope.viewChooseParentDict = function() {
		
		if($scope.dictParentId != undefined && $scope.dictParentValue!=undefined){
			
			document.getElementById("dictUpIdFont").value = $scope.dictParentValue;
			document.getElementById("dictUpIdDev").value = $scope.dictParentId;
			document.getElementById("dictUpIdFontApp").value = $scope.dictParentValue;
			document.getElementById("dictUpIdDevApp").value = $scope.dictParentId;
		}
		
        $scope.$emit('dictSelect-parent', $scope.dictParentId, $scope.dictParentValue);
		
	};

}

/**
 * 字典树的弹框
 * @param $scope
 * @param $http
 * @param $q
 */
function DictFileStyle($window,$scope, $http, $q,$state) {
	//大树
	$scope.getDictTree = function() {
		getTreeAndView($scope, $http, $q, 'DictController', 'getDictTreeParent',"");
	}
	$scope.getTreeRefresh = function() {		
		$state.reload();
	}
	$scope.getDictTree();
	
	//刷新大树   --》重新调用刷新tree方法
	//      -->列表的数据也需要回到初始状态 
	$scope.refreshDictWeb = function(){
		$scope.$parent.refreshDictList();
		$scope.getDictTree();
	}
	
	//添加、修改、删除调用
	$scope.$on("to-child",function(event,data){
		$scope.getDictTree();
	});
}

//	
function DictFileStyleParent($window,$scope, $http, $q) {
	if($scope.updateDictId != undefined && $scope.updateDictId != null){
		var hideDictIdParams = mergeJson("dictId", $scope.updateDictId)
		$scope.getHideDictTree = function() {
			getTreeAndView($scope, $http, $q, 'DictController', 'getDictTreeParent',hideDictIdParams);
		}
		$scope.getHideDictTree();
	}else{
		$scope.getDictTree = function() {
			getTreeAndView($scope, $http, $q, 'DictController', 'getDictTreeParent',"");
		}
		$scope.getDictTree();
	}
}