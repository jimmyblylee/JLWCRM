/**
 * 机构列表页controller，针对弹框，此控制器属于一级父类
 * 
 * @param $scope
 *            作用域
 * @param $filter
 *            过滤器
 * @param $modal
 *            数据模型（弹框使用）
 * @param $http
 *            服务向服务器发送请求，应用响应服务器传送过来的数据
 * @param $q
 */
function deptListCtrl($scope, $filter, $modal, $http, $q, $timeout,$aside) {
	$scope.deptList = [];
	$scope.itemsPerPage = itemsPerPage;
	$scope.Pagenum = Pagenum;
	$scope.deptIsEnabled = "true";
	var ispc=IsPC();
	//请求字典数据
	$scope.getDeptIsEnabled = function(){
		if ($scope.deptIsEnabledJson == undefined) {
			var responseDictResult = getSelectValueByDictList($http, $q,
					'STATUS_TYPE', 'STATUS_CODE');
			responseDictResult.then(function(success) {
				$scope.deptIsEnabledJson = StrParesJSON(success).result;
			}, function(error) {
				console.info(error);
			});
		}
	}
	$scope.getDeptIsEnabled();
	//刷新大树
	$scope.refresh = function(){
		$scope.deptQueryId =1;
		$scope.currentPage = 1;
		$scope.deptIsEnabled = "true";
		$scope.likeQueryCondition = "";
		$scope.queryDeptListByLike("true");
		$scope.order = "sort";
		$scope.reverse = false;
		sort_global($scope, $scope.order, $scope.reverse, null);
	}
	// 点击刷新：
	$scope.refreshEmptyCondition = function() {
		$scope.likeQueryCondition = "";
		$scope.deptIsEnabled = "true";
		$scope.itemsPerPage = Pagenum[0];
		$scope.currentPage =1;
		$scope.queryDeptListByLike("true");
		$scope.order = "sort";
		$scope.reverse = false;
		sort_global($scope, $scope.order, $scope.reverse, null);
	}
	//模糊查询
	$scope.queryDeptLike = function(){
		$scope.queryDeptListByLike("false");
	}
	//点击下一页
	$scope.queryNextPage = function(){
		$scope.queryDeptListByLike("true");
	}
	//改变分页参数
	$scope.queryByPage = function(){
		$scope.currentPage = 1;
		$scope.queryDeptListByLike("true");
	}
	$scope.treeAPPListName=JSON.parse(storage.getItem("restoken"));
	$scope.treeAPPListName=$scope.treeAPPListName.token.user.dept.name
	//树Aside的显示与隐藏
	  var treeAside = $aside({
	  	scope: $scope, 
	  	template: 'packages/sys/views/dept/tree.html',
	  	container:"#modalView",
	  	show:false  
	  }); 
	 $scope.treeAsideShow = function(){
		 treeAside.$promise.then(function() {
		    treeAside.show();
	 })
 }
	//点击树节点进行查询
	$scope.queryByTreeId = function(deptQueryId,eventTarget){
		if ($scope.deptQueryId==deptQueryId) {
             angular.element(eventTarget.currentTarget).addClass('tree-selected')
		}else{
			angular.element('.treeLink').removeClass('tree-selected')

		};
		$scope.deptQueryId = deptQueryId;
		$scope.queryDeptListByLike("true");
		$scope.treeAPPListName=eventTarget.currentTarget.innerText
		treeAside.$promise.then(function() {
		     treeAside.hide();
		  })
	}
	
	//添加、修改、删除  --》进行查询
	$scope.pagDeptJump = function(deptParentId){
		if(deptParentId == undefined){
			$scope.deptQueryId = 1;
		}else{
			$scope.deptQueryId = deptParentId;
		}
		$scope.$broadcast("to-child","child");
		$scope.queryDeptListByLike("true");
	}
	
	
	$scope.deptQueryId = 1;
	$scope.currentPage = 1;
	// 模糊查询
	$scope.queryDeptListByLike = function(deptIsEnabled) {
		
		if($scope.deptIsEnabled =="true" && deptIsEnabled=="false"){
			
			$scope.deptIsEnabled = "true";
			
		}else if($scope.deptIsEnabled !="true" && deptIsEnabled=="false"){
			
			$scope.deptIsEnabled = "false";
		}
		
		if($scope.isEnabledTrueShow && deptIsEnabled == "true"){
			
			$scope.deptIsEnabled = "true";
			
		}else if($scope.isEnabledFalseShow && deptIsEnabled == "true"){
			
			$scope.deptIsEnabled = "false";
		}
		
		if ($scope.deptIsEnabled == "true") {
			var tmpIsEnabled = true;
		} else {
			var tmpIsEnabled = false;
		}
		
		// 把id查询条件设置为null
		$scope.deptDataParam = {
			"id" : $scope.deptQueryId,
			"name" : $scope.likeQueryCondition,
			"isEnabled" : tmpIsEnabled,
		}
		if(!ispc){
			$scope.itemsPerPage=5;
			$scope.currentPage = 1;			
		}
		var deptStringData = ObjParesJSON($scope.deptDataParam);
		var promiseResult = pageService($http, $q, 'DeptController',
				'queryDeptBypaging', $scope.currentPage, $scope.itemsPerPage,
				deptStringData);
		promiseResult.then(function(success) {
			
			var queryDeptResult = StrParesJSON(success);

			if ($scope.deptIsEnabled == "true" || $scope.deptIsEnabled == "") {
				$scope.isEnabledTrueShow = true;
				$scope.isEnabledFalseShow = false;
			} else {
				$scope.isEnabledTrueShow = false;
				$scope.isEnabledFalseShow = true;
			}
			// 总条数
			$scope.totalItems = queryDeptResult.total;
			// 获取的列表集合
			$scope.deptList = queryDeptResult.result;
		});
	}
		$scope.queryDeptListByLikeApp= function(deptIsEnabled) {
		
		if($scope.deptIsEnabled =="true" && deptIsEnabled=="false"){
			
			$scope.deptIsEnabled = "true";
			
		}else if($scope.deptIsEnabled !="true" && deptIsEnabled=="false"){
			
			$scope.deptIsEnabled = "false";
		}
		
		if($scope.isEnabledTrueShow && deptIsEnabled == "true"){
			
			$scope.deptIsEnabled = "true";
			
		}else if($scope.isEnabledFalseShow && deptIsEnabled == "true"){
			
			$scope.deptIsEnabled = "false";
		}
		
		if ($scope.deptIsEnabled == "true") {
			var tmpIsEnabled = true;
		} else {
			var tmpIsEnabled = false;
		}
		
		// 把id查询条件设置为null
		$scope.deptDataParam = {
			"id" : $scope.deptQueryId,
			"name" : $scope.likeQueryCondition,
			"isEnabled" : tmpIsEnabled,
		}
		$scope.itemsPerPage=5;
		var totalItemsAPP=$scope.totalItems;
		var array=$scope.deptList;
		$scope.totalItemsAPP=Math.ceil(totalItemsAPP/5);
		$scope.currentPage=(array.length/5)+1;
		if ($scope.currentPage <= $scope.totalItemsAPP) {
			var deptStringData = ObjParesJSON($scope.deptDataParam);
				var promiseResult = pageService($http, $q, 'DeptController',
						'queryDeptBypaging', $scope.currentPage, $scope.itemsPerPage,
						deptStringData);
				promiseResult.then(function(success) {
					
					var queryDeptResult = StrParesJSON(success);

					if ($scope.deptIsEnabled == "true" || $scope.deptIsEnabled == "") {
						$scope.isEnabledTrueShow = true;
						$scope.isEnabledFalseShow = false;
					} else {
						$scope.isEnabledTrueShow = false;
						$scope.isEnabledFalseShow = true;
					}				
					$scope.deptList =array.concat(queryDeptResult.result); 		
				});

		}
	
	}
	
	 var userId  = getCookie("userId");
     if(userId!=undefined && userId!=null && userId != ""){
          var sysUserJson = mergeJson('userId',userId);
          var sysUserData = mergeReauestData('UserController', 'getPersonInfo',sysUserJson);
          var sysUserResult = sendPost($http,sysUserData, $q);
          sysUserResult.then(function(success){
        	  
	               success = JSON.parse(success);
	               var sysUserPersonInfo = success.result[0];
	               $scope.deptQueryId = sysUserPersonInfo.dept.id;
	               $scope.queryDeptListByLike("true");
          }), function(error) {
            console.info(error);
          };       
      }
	
	scrollwatch($scope, $timeout);
	window.onscroll = function(){
		if (!ispc) {
	    	isscrollbottom=scrollbottom();
	      　if(isscrollbottom){
	      		$scope.queryDeptListByLikeApp()
	        }
	    }

   };	
	// 回车查询
	$scope.keyup = function($event) {
		if ($event.keyCode == 13) {
			$scope.queryDeptListByLike($scope.deptIsEnabled);
		}
	}
	// 排序
	$scope.order = "sort";
	$scope.reverse = false;
	$scope.sort_by = function(newSortingOrder) {
	    //这里还需要请求一下后台，然后进行一下总排序
		sort_global($scope, $scope.order, $scope.reverse, newSortingOrder);
	};

	/*
	 * 添加一级机构、二级机构弹框 indexNum参数用来区分: 查看或修改的机构的列索引(区分二级机构添加还是一级机构添加)
	 */
	$scope.addDeptModal = function(parentDeptId) {
		$scope.editDeptId = null;
		if (parentDeptId != null && parentDeptId != undefined) {
			addDeptModal.$promise.then(addDeptModal.show,
					$scope.addSecondShow = true, $scope.addShow = false);
			$scope.addTitle = "添加下级机构";
			$scope.parentDeptId = parentDeptId;
		} else {
			addDeptModal.$promise.then(addDeptModal.show,
					$scope.addShow = true, $scope.addSecondShow = false);
			$scope.addTitle = "添加机构";
			$scope.parentDeptId = null;
		}
	};

	var addDeptModal = $modal({
		scope : $scope,
		templateUrl : 'packages/sys/views/dept/dept_add.html',
		show : false,
		container : '#modalView',
		controller : 'addDeptModalCtl',
		backdrop : "static"
	});

	//查看机构
	$scope.viewDeptModal = function(deptId) {
		$scope.editDeptId = deptId;
		viewDeptModal.$promise.then(viewDeptModal.show);
	};
	$scope.closeDeptView = function() {		
		viewDeptModal.$promise.then(viewDeptModal.hide);
		fullscreenRemove();
	};
	var viewDeptModal = $modal({
		scope : $scope,
		templateUrl : 'packages/sys/views/dept/dept_view.html',
		show : false,
		container : '#modalView',
		controller : 'editDeptModalCtl',
		backdrop : "static"
	});
	/*
	 * 修改机构、查看机构弹框 params: deptId: 查看或修改的机构id indexNum: 查看或修改的机构的列索引
	 * detailsOrEdit: 区别是details还是edit，以便页面是否显示确认按钮
	 */
	$scope.editDeptModal = function(deptId) {
	    editDeptModal.$promise.then(editDeptModal.show);
		$scope.editDeptId = deptId;
	};
	var editDeptModal = $modal({
		scope : $scope,
		templateUrl : 'packages/sys/views/dept/dept_edit.html',
		show : false,
		container : '#modalView',
		controller : 'editDeptModalCtl',
		backdrop : "static"
	});

	// 选择上级机构，显示机构弹框
	$scope.chooseParentDept = function() {
		parentDeptTreeModal.$promise.then(parentDeptTreeModal.show);
	};
	// 创建选择上级机构弹框实例
	var parentDeptTreeModal = $modal({
		scope : $scope,
		templateUrl : 'packages/sys/views/dept/parent_dept.html',
		show : false,
		container : '#parentDeptModalView',
		backdrop : "static",
		controller : "selectParentDeptCtl"
	});
	/*
	 * 接收、监听子控制器过来的值 关闭弹出的添加、修改弹框
	 */
	$scope.$on('to-parent', function() {
		addDeptModal.$promise.then(addDeptModal.hide);
		editDeptModal.$promise.then(editDeptModal.hide);
	})

	// 点击恢复按钮触发事件
	$scope.recoverDeptState = function(deptId) {
		promptRecover.$promise.then(promptRecover.show);
		$scope.recvDeptId = deptId;
	};
	var promptRecover = $modal({
		scope : $scope,
		templateUrl : 'packages/sys/views/dept/recover_dept.html',
		content : '您确定要恢复吗？',
		show : false,
		backdrop : "static"
	});

	// 确认恢复
	$scope.recoverDeptByid = function() {
		var recoverDeptParams = mergeJson("deptid", $scope.recvDeptId);
		var requestDeptData = mergeReauestData('DeptController',
				'recoverDeptState', recoverDeptParams);
		var respRecoverResult = sendPost($http, requestDeptData, $q);
		respRecoverResult.then(function(success) {

			if ($scope.deptList.length == 1 && $scope.currentPage != 1) {
				$scope.currentPage = $scope.currentPage - 1;
				$scope.pagDeptJump($scope.deptQueryId);
			} else {
				$scope.pagDeptJump($scope.deptQueryId);
			}
			/*
			 * 隐藏恢复提示框，提示恢复部门成功
			 */
			promptRecover.$promise.then(promptRecover.hide);
			$modal({
				scope : $scope,
				title : '提示',
				templateUrl : 'packages/sys/views/dept/recover_dept.html',
				content : "机构恢复成功！",
				show : true,
				backdrop : "static"
			});
		})
	};

	// 点击删除按钮触发事件
	$scope.deleteDeptById = function(deptId) {
		
		var queryDeptParams = mergeJson("deptid", deptId);
		
		var requestDeptData = mergeReauestData('UserController',
				'queryUserByDeptId', queryDeptParams);
		var responseDeleteResult = sendPost($http, requestDeptData,
				$q);
		responseDeleteResult.then(function(success) {
			var sign = JSON.parse(success).result;
			if(sign == true){
				//已存在
				$modal({
					scope : $scope,
					title : "提示",
					templateUrl : 'packages/sys/views/user/tip.html',
					content : '机构下面存在用户,不可以删除',
					show : true,
					backdrop : "static"
				});
			}else{
				promptWhetherDelete.$promise.then(promptWhetherDelete.show);
				$scope.delDeptId = deptId;
			}
			
		})
	};
	var promptWhetherDelete = $modal({
		scope : $scope,
		templateUrl : 'packages/sys/views/dept/del_dept.html',
		content : '确定要删除该机构吗？',
		show : false,
		backdrop : "static"
	});
	// 确认删除
	$scope.delDeptByid = function() {
		var deleteDeptParams = mergeJson("deptid", $scope.delDeptId);
		var requestDeptControllerData = mergeReauestData('DeptController',
				'removeSysDept', deleteDeptParams);
		var responseDeleteResult = sendPost($http, requestDeptControllerData,
				$q);
		responseDeleteResult.then(function(success) {
			
			if ($scope.deptList.length == 1 && $scope.currentPage != 1) {
				$scope.currentPage - 1
				$scope.pagDeptJump($scope.deptQueryId);
			} else {
				$scope.pagDeptJump($scope.deptQueryId);
			}
			/*
			 * 隐藏删除提示框，提示删除部门成功
			 */
			promptWhetherDelete.$promise.then(promptWhetherDelete.hide);
			$modal({
				scope : $scope,
				title : "提示",
				templateUrl : 'packages/sys/views/dept/del_dept.html',
				content : "删除机构成功！",
				show : true,
				backdrop : "static"
			});
		})
	};
 
}

/**
 * 增加页面弹框控制器
 * 
 * @param $scope
 *            作用域
 * @param $modal
 *            数据模型（弹框使用）
 * @param $http
 *            服务向服务器发送请求，应用响应服务器传送过来的数据
 * @param $q
 */
function addDeptModalCtl($scope, $modal, $http, $q , $rootScope) {
	$scope.submitted = true;
	// 添加提交
	$scope.submitDeptForm = function() {
		// 验证表单
		if ($scope.addDeptForm.$valid && !$scope.telError && !$scope.postalCodeError
				&& !$scope.eamilError && !$scope.faxError && !$scope.sortError) {
			
			var addDeptData = $scope.deptData;
			if ($scope.parentid != null) {
				addDeptData.parent = {
					"id" : $scope.parentid
				};
			}
			var deptFromParams = mergeJson('dept', addDeptData);
			var reqDevData = mergeReauestData('DeptController',
					'createSysDept', deptFromParams);
			var addResqResult = sendPost($http, reqDevData, $q);
			addResqResult.then(function(success) {
				
				// 请求列表数据
				$scope.pagDeptJump($scope.parentid);
				
				$scope.$broadcast("to-child","child");
				/*
				 * 隐藏修改弹出框，提示修改机构信息成功
				 */
				$scope.$emit('to-parent');
				$modal({
					scope : $scope,
					title : '提示',
					templateUrl : 'packages/sys/views/dept/del_dept.html',
					content : "添加机构成功！",
					show : true,
					backdrop : "static"
				});
				$scope.addDeptForm = undefined
			})
		} else {
			$scope.addDeptForm.submitted = true;
		}
	}
	/*
	 * 添加二级机构的时候 --需要为其准备上级机构的数据
	 */
	if ($scope.$parent.parentDeptId != undefined) {
		for (key in $scope.$parent.deptList) {
			if ($scope.$parent.deptList[key].id == $scope.$parent.parentDeptId) {
				var paretDept = $scope.$parent.deptList[key];
				$scope.parentid = paretDept.id,
				$scope.parentname = paretDept.name;
				break;
			}
		}
	}else{
		 var userId  = getCookie("userId");
     if(userId!=undefined && userId!=null && userId != ""){
          var sysUserJson = mergeJson('userId',userId);
          var sysUserData = mergeReauestData('UserController', 'getPersonInfo',sysUserJson);
          var sysUserResult = sendPost($http,sysUserData, $q);
          sysUserResult.then(function(success){        	  
	               success = JSON.parse(success);
	               var sysUserPersonInfo = success.result[0];	              
	              $scope.parentname =sysUserPersonInfo.dept.name;
			       $scope.parentid = sysUserPersonInfo.dept.id;
          }), function(error) {
            console.info(error);
          }; 
      }
		
	}
	
	//关闭添加弹框
	$scope.closeDeptAdd = function(){
		if($scope.addDeptForm.$pristine){//判断是否有修改 如果为true代表没有修改执行关闭
			$scope.$emit('to-parent');
			fullscreenRemove();					
		}else{//如果是false 代表有修改 显示提示框
			$rootScope.closeModel.$promise.then($rootScope.closeModel.show);	
		}	
	}
	$rootScope.$on('hideModel', function(event, depthideModel) {//确定丢弃输入的修改内容后需要执行的方法
		if ($scope.addDeptForm!= undefined) {
			if (depthideModel) {					
				$scope.submitDeptForm(); // 执行提交方法
			}else{
				$scope.$emit('to-parent');
				fullscreenRemove();
				$scope.addDeptForm = undefined	
			};	
		};
		
	})
	// 选择上级机构，显示机构弹框
	$scope.chooseParentDept = function() {
		parentDeptTreeModal.$promise.then(parentDeptTreeModal.show);
	};

	// 创建选择上级机构弹框实例
	var parentDeptTreeModal = $modal({
		scope : $scope,
		templateUrl : 'packages/sys/views/dept/parent_dept.html',
		show : false,
		container : '#parentDeptModalView',
		backdrop : "static"
	});

	/*
	 * 点击上级机构树节点，零时保存选择的二级机构
	 */
	$scope.saveChooseParentDept = function(deptParentId, deptParentName,eventTarget){
		if ($scope.deptParentid==deptParentId) {
               angular.element(eventTarget.currentTarget).addClass('tree-selected')
		}else{
			   angular.element('.treeLink').removeClass('tree-selected')
		};
		$scope.deptParentid = deptParentId;
		$scope.deptParentName = deptParentName;
	}
	/*
	 * 显示选择的二级机构
	 */
	$scope.viewChooseParentDept = function() {
		if($scope.deptParentid != undefined && $scope.deptParentName != undefined){
			$scope.parentid = $scope.deptParentid,
			$scope.parentname = $scope.deptParentName;
		}
		parentDeptTreeModal.$promise.then(parentDeptTreeModal.hide);
	}
	$scope.closeParentDept = function() {
		parentDeptTreeModal.$promise.then(parentDeptTreeModal.hide);
	};
	 /*
	   * 离开焦点判断邮件格式
	   */
	  $scope.checkEmail = function(){
		  //$scope.addDeptForm.email = checkEmail($scope.deptData.email);
		  $scope.eamilError = checkEmail($scope.deptData.email);
	  }
	  /*
	   * 邮件改变判断邮件格式
	   */
	  $scope.emailChange = function(){
		 $scope.eamilError = changeEmail($scope.deptData.email);  
	  }
	  /*
	   * 验证url
	   */
	  $scope.checkUrl = function(){
		  $scope.urlError = checkUrl($scope.deptData.url);  
	  }	
	  $scope.urlChange = function(){
		  $scope.urlError = changeUrl($scope.deptData.url);  
	  }
	  
	  //值发生改变验证手机号
	  $scope.checkPhone = function(){
		  $scope.telError =checkNumber($scope.deptData.tel);
	  }
	  
	  $scope.phoneChange = function(){
		  $scope.telError =false;
	  }
	  
	  //失去焦点检验邮编
	  $scope.checkPostalCode = function() { 
		  $scope.postalCodeError=checkNumber($scope.deptData.postalCode);
	  }
	  //值发生改变验证邮编
	  $scope.changePostalCode = function(){
		  $scope.postalCodeError= false;
	  }
	  
	  //失去焦点检验排序
	  $scope.checkSort = function() { 
		  $scope.sortError=checkNumber($scope.deptData.sort);
	  }
	  //值发生改变验证排序
	  $scope.changeSort = function(){
		  $scope.sortError= false;
	  }
	  
	  //失去焦点检验传真
	  $scope.checkFax = function() { 
		  $scope.faxError=checkFax($scope.deptData.fax);
	  }
	  //值发生改变验证传真
	  $scope.changeFax = function(){
		  $scope.faxError= changeFax($scope.deptData.fax);
	  }

	  //失去焦点验证电话号码
	  $scope.checkFaxTel = function() { 
		  $scope.telError=checkFaxTel($scope.deptData.tel);
	  }
	  //值发生改变验证电话号码
	  $scope.changeFaxTel = function(){
		  $scope.telError= changeFaxTel($scope.deptData.tel);
	  }
	  
	  
}
/**
 * 修改页面弹框控制器
 * 
 * @param $scope
 *            作用域
 * @param $modal
 *            数据模型（弹框使用）
 * @param $http
 *            服务向服务器发送请求，应用响应服务器传送过来的数据
 * @param $q
 */
function editDeptModalCtl($scope, $modal, $http, $q,$rootScope) {
	// 表单提交
	$scope.submitDeptForm = function() {
		if ($scope.editDeptForm.$valid && !$scope.telError && !$scope.postalCodeError && !$scope.eamilError 
				&& !$scope.faxError && !$scope.sortError) {
			
			var editDeptData = $scope.deptData;
			if ($scope.parentid != null) {
				editDeptData.parent = {
					"id" : $scope.parentid
				};
			}
			var deptFromParams = mergeJson('dept', editDeptData);
			var reqDevData = mergeReauestData('DeptController',
					'updateSysDept', deptFromParams);
			var editResqResult = sendPost($http, reqDevData, $q);
			editResqResult.then(function(success) {
				
				// 请求列表数据
				
				$scope.pagDeptJump($scope.parentid);
				/*
				 * 隐藏修改弹出框，提示修改机构信息成功
				 */
				$scope.$emit('to-parent');
				$modal({
					scope : $scope,
					title : '提示',
					templateUrl : 'packages/sys/views/dept/del_dept.html',
					content : "修改机构成功！",
					show : true,
					backdrop : "static"
				});
				$scope.editDeptForm = undefined
			})
		} else {
			$scope.editDeptForm.submitted = true;
		}
	}
	// 为修改页面回显数据
	// 初始化一些数据，判断进入的是添加页面还是修改页面
	if ($scope.$parent.editDeptId != '' && $scope.$parent.editDeptId != null) {
		for (key in $scope.$parent.deptList) {
			if ($scope.$parent.deptList[key].id == $scope.$parent.editDeptId) {
				$scope.deptData = JSON.parse(JSON
						.stringify($scope.$parent.deptList[key]));
				var deptParent = $scope.$parent.deptList[key].parent;
				if (deptParent != null) {
					$scope.parentid = deptParent.id,
					$scope.parentname = deptParent.name;
				}
				break;
			}
		}
	}

	// 选择上级机构，显示机构弹框
	$scope.chooseParentDept = function() {
		if(deptParent != null){
			parentDeptTreeModal.$promise.then(parentDeptTreeModal.show);
		}
	};

	// 创建选择上级机构弹框实例
	var parentDeptTreeModal = $modal({
		scope : $scope,
		templateUrl : 'packages/sys/views/dept/parent_dept.html',
		show : false,
		container : '#parentDeptModalView',
		backdrop : "static"
	});

	/*
	 * 点击上级机构树节点，零时保存选择的二级机构
	 */
	$scope.saveChooseParentDept = function(deptParentId, deptParentName,eventTarget){
		if ($scope.deptParentid==deptParentId) {
            angular.element(eventTarget.currentTarget).addClass('tree-selected')
		}else{
			angular.element('.treeLink').removeClass('tree-selected')
		};
		$scope.deptParentid = deptParentId;
		$scope.deptParentName = deptParentName;
	}
	/*
	 * 显示选择的二级机构
	 */
	$scope.viewChooseParentDept = function() {
		if($scope.deptParentid != null && $scope.deptParentName != null){
			$scope.parentid = $scope.deptParentid,
			$scope.parentname = $scope.deptParentName;
		}
		parentDeptTreeModal.$promise.then(parentDeptTreeModal.hide);
	}
	$scope.closeParentDept = function() {
		parentDeptTreeModal.$promise.then(parentDeptTreeModal.hide);
	};
// 关闭修改机构的弹框
	$scope.closeDepteidt = function(){
		if($scope.editDeptForm.$pristine){//判断是否有修改 如果为true代表没有修改执行关闭
			$scope.$emit('to-parent');
			fullscreenRemove();			
		}else{//如果是false 代表有修改 显示提示框
			$rootScope.closeModel.$promise.then($rootScope.closeModel.show);	
		}	
	}
	$rootScope.$on('hideModel', function(event, depthideModel) {//确定丢弃输入的修改内容后需要执行的方法
		if ($scope.editDeptForm != undefined) {
			if (depthideModel ) {					
				$scope.submitDeptForm(); // 执行提交方法
			}else{
				$scope.$emit('to-parent');	
				$scope.editDeptForm = undefined;
				fullscreenRemove();
			};
		};		
			
	})
	 /*
	   * 离开焦点判断邮件格式
	   */
	  $scope.checkEmail = function(){
		  $scope.eamilError = checkEmail($scope.deptData.email);
	  }
	  /*
	   * 邮件改变判断邮件格式
	   */
	  $scope.emailChange = function(){
		 $scope.eamilError = changeEmail($scope.deptData.email);  
	  }
	  /*
	   * 验证url
	   */
	  $scope.checkUrl = function(){
		  $scope.urlError = checkUrl($scope.deptData.url);  
	  }	
	  $scope.urlChange = function(){
		  $scope.urlError = changeUrl($scope.deptData.url);  
	  }
	  
	  //值发生改变验证手机号
	  $scope.checkPhone = function(){
		  $scope.telError =checkNumber($scope.deptData.tel);
	  }
	  
	  $scope.phoneChange = function(){
		  $scope.telError =false;
	  }
	  
	  //失去焦点检验邮编
	  $scope.checkPostalCode = function() { 
		  $scope.postalCodeError=checkNumber($scope.deptData.postalCode);
	  }
	  //值发生改变验证邮编
	  $scope.changePostalCode = function(){
		  $scope.postalCodeError= false;
	  }
	  
	  //失去焦点检验排序
	  $scope.checkSort = function() { 
		  $scope.sortError=checkNumber($scope.deptData.sort);
	  }
	  //值发生改变验证排序
	  $scope.changeSort = function(){
		  $scope.sortError= false;
	  }
	  
	  //失去焦点传真
	  $scope.checkFax = function() { 
		  $scope.faxError=checkFax($scope.deptData.fax);
	  }
	  //值发生改变验证传真
	  $scope.changeFax = function(){
		  $scope.faxError= changeFax($scope.deptData.fax);
	  }
	  

	  //失去焦点验证电话号码
	  $scope.checkFaxTel = function() { 
		  $scope.telError=checkFaxTel($scope.deptData.tel);
	  }
	  //值发生改变验证电话号码
	  $scope.changeFaxTel = function(){
		  $scope.telError= changeFaxTel($scope.deptData.tel);
	  }

	  
	
}

/**
 * 树模型接受控制层
 * 
 * @param $scope
 *            作用域
 * @param $http
 *            服务向服务器发送请求，应用响应服务器传送过来的数据
 * @param $q
 */
function FileStyle($scope, $http, $q,$state) {
	$scope.getDeptTree = function() {
		getTreeAndView($scope, $http, $q, 'DeptController', 'getDeptTreeParent',"");
	}
	$scope.getTreeRefresh = function() {
		//重新加载了一下路由
		$state.reload();
	}
	$scope.getDeptTree();
	
	$scope.refreshDeptWeb= function(){
		$scope.$parent.refresh();
		$scope.getDeptTree();
	}
	//添加、修改、删除调用
	$scope.$on("to-child",function(event,data){
		$scope.getDeptTree();
	});
	
}
//上级机构控制器
function FileStyleParent($scope, $http, $q){
	if($scope.editDeptId != undefined && $scope.editDeptId != null){
		var hideDeptIdParams = mergeJson("deptId", $scope.editDeptId)
		$scope.getHideDeptTree = function(){
			getTreeAndView($scope, $http, $q, 'DeptController', 'getDeptTreeParent',hideDeptIdParams);
		}
		$scope.getHideDeptTree();
	}else{
		$scope.getDeptTree = function(){
			getTreeAndView($scope, $http, $q, 'DeptController', 'getDeptTreeParent',"");
		}
		$scope.getDeptTree();
	}
}