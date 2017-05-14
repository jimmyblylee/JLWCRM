//2016.5.16 LIUYUAN
function globallistCtrl($scope, $filter, $modal, $log, $http, $q, $timeout,$rootScope) {
	$scope.globalItems = [];
	$scope.globalItemsPerPage = itemsPerPage; // 每页显示5条
	$scope.Pagenum = Pagenum; // 显示条数下拉的值 //
	$scope.order = "variableName"; // 初始查询条件
	$scope.reverse = false;
	$scope.globalDel;
	$scope.globalDelCancel;
	$scope.globalEditId = '';
	$scope.indexNum;
	$scope.globalQueryData = null;
	var ispc=IsPC();
	$scope.globals = {
		"isEnabled" : "true"
	};	
	$scope.IsExit = false;
	$scope.changGlobalPageValue = function(){
		$scope.currentPage = 1; 
		$scope.globalItemsdata("true")
	}	

	$scope.globalItemsdata = function(isEnabled) {
		if($scope.globals.isEnabled =="true" && isEnabled=="false"){
			$scope.globals.isEnabled = "true";
		}else if($scope.globals.isEnabled != "true" && isEnabled=="false"){
			$scope.globals.isEnabled = "false";
		}
		
		if($scope.delEnabledGlobal && isEnabled == "true"){	
			$scope.globals.isEnabled = "true";
		}else if($scope.isEnabledGlobal && isEnabled == "true"){
			$scope.globals.isEnabled = "false";
		}		
		if ($scope.globals.isEnabled == "true") {
			$scope.delEnabledGlobal = true;
			$scope.isEnabledGlobal = false;
		} else {
			$scope.delEnabledGlobal = false;
			$scope.isEnabledGlobal = true;
		}		  
		if(!ispc){
			$scope.globalItemsPerPage=30;
			$scope.currentPage = 1;			
		}
		$scope.globalDateParams = JSON.stringify($scope.globals);
		var globalInfoResult = pageService($http, $q, 'GlobalListController',
				'queryAllGlobalList', $scope.currentPage, $scope.globalItemsPerPage,
				$scope.globalDateParams);
		globalInfoResult.then(function(success) {
			var globalResponse = StrParesJSON(success);
			$scope.globalItems = globalResponse.result;
			$scope.totalItems = globalResponse.total;
			scrollwatch($scope, $timeout);
		}, function(error) {
			console.info(error);
		});
	};
	$scope.globalItemsdataApp = function(isEnabled) {
		if($scope.globals.isEnabled =="true" && isEnabled=="false"){
			$scope.globals.isEnabled = "true";
		}else if($scope.globals.isEnabled != "true" && isEnabled=="false"){
			$scope.globals.isEnabled = "false";
		}
		
		if($scope.delEnabledGlobal && isEnabled == "true"){	
			$scope.globals.isEnabled = "true";
		}else if($scope.isEnabledGlobal && isEnabled == "true"){
			$scope.globals.isEnabled = "false";
		}		
		if ($scope.globals.isEnabled == "true") {
			$scope.delEnabledGlobal = true;
			$scope.isEnabledGlobal = false;
		} else {
			$scope.delEnabledGlobal = false;
			$scope.isEnabledGlobal = true;
		}		  
		$scope.globalItemsPerPage=5;
		var globaltotleAPP=$scope.totalItems;
		var array=$scope.globalItems;
		$scope.globaltotleAPP=Math.ceil(globaltotleAPP/5);
		$scope.currentPage=(array.length/5)+1;
		if ($scope.currentPage <= $scope.globaltotleAPP) {
			$scope.globalDateParams = JSON.stringify($scope.globals);
			var globalInfoResult = pageService($http, $q, 'GlobalListController',
					'queryAllGlobalList', $scope.currentPage, $scope.globalItemsPerPage,
					$scope.globalDateParams);
			globalInfoResult.then(function(success) {			
				var globalResponse = StrParesJSON(success);			
				$scope.globalItems = array.concat(globalResponse.result);
			}, function(error) {
				console.info(error);
			});
		};
		
	};
	$scope.globalItemsdata(1);
    window.onscroll = function(){
    	if (!ispc) {
	    	isscrollbottom=scrollbottom();
	      　if(isscrollbottom){
	      		$scope.globalItemsdataApp()
	        }
	    }

   };	
	$scope.getIsEnabled = function() {
		if ($scope.IsEnabledJson == null) {
			var responseDictResult = getSelectValueByDictList($http, $q,
					'STATUS_TYPE', 'STATUS_CODE');
			responseDictResult.then(function(success) {
				$scope.IsEnabledJson = StrParesJSON(success).result;
				$scope.globals.isEnabled = "true";
			}, function(error) {
				console.info(error);
			});
		}
	}
	$scope.getIsEnabled();
	$scope.setEnabled = function(selectCode, selectValue) {
		$scope.globals = {
			"isEnabled" : selectCode
		}
		$scope.isEnabledValue = selectValue;
	}
	// 排序
	$scope.sort_by = function(newSortingOrder) {
		sort_global($scope, $scope.order, $scope.reverse, newSortingOrder);

	};

	$scope.keyup = function($event) {
		if ($event.keyCode == 13) {
			$scope.globalItemsdata('1')
		}

	}

	// 删除
	$scope.delGlobal = function() {
		var delGlobalParams = mergeJson('globalId', $scope.globalDel);
		var delGlobalData = mergeReauestData('GlobalListController',
				'delGlobalList', delGlobalParams);
		var delGlobalResult = sendPost($http, delGlobalData, $q);
		delGlobalResult.then(function(success) {
			if ($scope.globalDelCancel) {
				cancelDelModal.$promise.then(cancelDelModal.hide);
				delModal.$promise.then(delModal.hide);
				var globalPrompt = $modal({
					scope : $scope,
					title : "提示",
					templateUrl : 'packages/sys/views/global/prompt.html',
					content : '全局参数恢复成功',
					show : true,
					backdrop : "static"
				});
			} else {
				delModal.$promise.then(delModal.hide);
				var globalPrompt = $modal({
					scope : $scope,
					title : "提示",
					templateUrl : 'packages/sys/views/global/prompt.html',
					content : '全局参数删除成功',
					show : true,
					backdrop : "static",
				});
			}
			$scope.globalItemsdata("true");

		})
	};
	$scope.hideError = function(){		
		$scope.IsExit = false;				
		$scope.IsExitYes = false;
	}
	// 添加
	$scope.submitGlobalForm = function() {
		if ($scope.globalModelForm.$valid && !$scope.IsExit && !$scope.IsExitYes) {
			if($scope.globalEditId != '' && $scope.globalEditId != null
					&& $scope.globalEditId != undefined){
				
				var addGlobalParams = mergeJson('globalItme', $scope.globaLData);
				var globalAddData = mergeReauestData('GlobalListController',
						'updateGlobalList', addGlobalParams);
				var getGlobalResult = sendPost($http, globalAddData, $q);
				getGlobalResult.then(function(success) {
					var global = JSON.parse(success).result;
					if(global.isEnabled == true){
						//此用户已经存在
						$scope.IsExit = true;
						//$scope.globaLData = {}
					}else if(global.isEnabled == false){
						//此用户已经存在，你可以进行恢复
						$scope.IsExitYes = true;
						//$scope.globaLData = {}
					}else{
						if ($scope.globalEditId != '' && $scope.globalEditId != null
								&& $scope.globalEditId != undefined) {
							eidtModal.$promise.then(eidtModal.hide);
							// 字母+数字
							var globalPrompt = $modal({
								scope : $scope,
								title : "提示",
								templateUrl : 'packages/sys/views/global/prompt.html',
								content : '全局参数修改成功',
								show : true,
								backdrop : "static",
								
							});							
						}
						$scope.globalItemsdata("true");
						$scope.globaLData = {}
					}	
				}, function(error) {
					console.info(error);					
				});			
			}else{
				//1.添加的时候需要先查询一下
				var getGlobalIsExitByValue = mergeJson('variableName', $scope.globaLData.variableName);
				var getGlobalData = mergeReauestData('GlobalListController',
						'getGlobalIsExitByVariableValue', getGlobalIsExitByValue);
				var getGlobalResult = sendPost($http, getGlobalData, $q);
				getGlobalResult.then(function(success){
					console.log(success);
					var global = JSON.parse(success).result;
					if(global.isEnabled == true){
						//此用户已经存在
						$scope.IsExit = true;
					}else if(global.isEnabled == false){
						//此用户已经存在，你可以进行恢复
						$scope.IsExitYes = true;
					}else{
						var addGlobalParams = mergeJson('globalItme', $scope.globaLData);
						var globalAddData = mergeReauestData('GlobalListController',
									'addGlobalList', addGlobalParams);
						// result
						var globalAddresult = sendPost($http, globalAddData, $q);
						globalAddresult.then(function(success) {
							// 弹窗
							addModal.$promise.then(addModal.hide);
							var globalPrompt = $modal({
								scope : $scope,
								title : "提示",
								templateUrl : 'packages/sys/views/global/prompt.html',
								content : '全局参数添加成功',
								show : true,
								backdrop : "static"
							});
							$scope.globalItemsdata("true");
							$scope.globaLData = {}
						}, function(error) {
							console.info(error);
						});			
					}
				})
			}
		} else {
			$scope.globalModelForm.submitted = true;
		}

	}
	// 与子页面的交互 开始
	// 定义第一个弹框的地址
	var addModal = $modal({
		scope : $scope,
		templateUrl : 'packages/sys/views/global/global_modal.html',
		show : false,
		container : '#globalModelContainer',
		controller : 'submodal',
		backdrop : "static"
		});


	// 接受子页面传过来的值
	$scope.$on('to-parent', function(event, globalModelForm, globaLData) {
		$scope.globalModelForm = globalModelForm;
		$scope.globaLData = globaLData;		
		$scope.submitGlobalForm(); // 执行提交方法
	})	
   
	$scope.$on('tocolse', function(event, globalModelForm , globaLData) {//接受子控制器传来的值
		if(globalModelForm.$pristine){//判断是否有修改 如果为true代表没有修改执行关闭
			if($scope.globalEditId != '' && $scope.globalEditId != null	&& $scope.globalEditId != undefined){//判断是修改还是添加
					eidtModal.$promise.then(eidtModal.hide);
			}else{
					addModal.$promise.then(addModal.hide);
			}
			fullscreenRemove();	
			$scope.globalModelForm=undefined
		}else{//如果是false 代表有修改 显示提示框
			$rootScope.closeModel.$promise.then($rootScope.closeModel.show);	
		}
        $scope.globalModelForm = globalModelForm;
		$scope.globaLData = globaLData;		
	})
	$rootScope.$on('hideModel', function(event, globalhideModel) {//确定丢弃输入的修改内容后需要执行的方法	
		var globalDataJSON={}
		if ($scope.globalModelForm!=undefined && $scope.globaLData != undefined ) {		
			if (globalhideModel) {					
				$scope.submitGlobalForm(); // 执行提交方法
			}else{
				if($scope.globalEditId != '' && $scope.globalEditId != null	&& $scope.globalEditId != undefined){//判断是修改还是添加
					eidtModal.$promise.then(eidtModal.hide);     
				}else{
					addModal.$promise.then(addModal.hide);
				}
				$scope.globalModelForm=undefined;
				fullscreenRemove();
			};
		};
	})
	// 显示弹框
	$scope.showModal = function() {
		addModal.$promise.then(addModal.show);
		$scope.globaLData = {}
		$scope.globalEditId = null;
	};

	// 删除
	var delModal = $modal({
		scope : $scope,
		templateUrl : 'packages/sys/views/global/prompt.html',
		content : '您确定要删除吗？',
		show : false,
		backdrop : "static"
	});
	var cancelDelModal = $modal({
		scope : $scope,
		templateUrl : 'packages/sys/views/global/prompt.html',
		content : '您确定要恢复吗？',
		show : false,
		backdrop : "static"
	});

	$scope.singleDelete = function(globalid, delPrompt) {
		if (delPrompt) {
			cancelDelModal.$promise.then(cancelDelModal.show);
		} else {
			delModal.$promise.then(delModal.show);
		}
		$scope.globalDel = globalid;
		$scope.globalDelCancel = delPrompt;

	};
	// 修改
	var eidtModal = $modal({
		scope : $scope,
		templateUrl : 'packages/sys/views/global/global_eidt.html',
		show : false,
		container : '#globalModelContainer',
		controller : 'eidtSubmodal',		
		backdrop : "static"
	});
	$scope.eidtModal = function(globalid, indexNum) {
		eidtModal.$promise.then(eidtModal.show);
		$scope.globalEditId = globalid;
		$scope.indexNum = indexNum;
	};
	// 为测试写的
	$scope.setAddModal = function(_addModal) {
		addModal = _addModal;
	};
	$scope.setEidtModal = function(_eidtModal) {
		eidtModal = _eidtModal;
	};
	$scope.setDelModal = function(_delModal) {
		delModal = _delModal;
	};

};

// 给父级页面传值
function submodal($scope, $modal) {
	$scope.hideModel = function(){
		$scope.$emit('tocolse', $scope.globalModelForm, $scope.globaLData);
		
	}
	$scope.submitGlobalForm = function() {
		$scope.$emit('to-parent', $scope.globalModelForm, $scope.globaLData);
	}	
	if ($scope.$parent.globalEditId != ''
			&& $scope.$parent.globalEditId != null
			&& $scope.$parent.globalEditId != undefined) {
		for (key in $scope.$parent.globalItems) {
			if ($scope.$parent.globalItems[key].variableID == $scope.$parent.globalEditId) {
				$scope.globaLData = JSON.parse(JSON
						.stringify($scope.$parent.globalItems[key]));
				break;
			}
		}

	}
}
function eidtSubmodal($scope, $modal) {
	$scope.hideModel = function(){
		$scope.$emit('tocolse', $scope.globalModelForm, $scope.globaLData);
	}
	$scope.submitGlobalForm = function() {
		$scope.$emit('to-parent', $scope.globalModelForm, $scope.globaLData);
	}
	if ($scope.$parent.globalEditId != ''
			&& $scope.$parent.globalEditId != null
			&& $scope.$parent.globalEditId != undefined) {
		for (key in $scope.$parent.globalItems) {
			if ($scope.$parent.globalItems[key].variableID == $scope.$parent.globalEditId) {
				$scope.globaLData = JSON.parse(JSON
						.stringify($scope.$parent.globalItems[key]));
				break;
			}
		}

	}
}
