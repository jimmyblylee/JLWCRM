/**
 * Created by JimmyblyLee on 2016/12/22.
 */
var MetronicApp = angular.module("MetronicApp", [
    "ui.bootstrap",
    "ngSanitize",
    'mgcrea.ngStrap',   //弹框插件
    'chieffancypants.loadingBar',
    'cfp.loadingBar',
    'angular-drag',
    'ui.select'
]);
MetronicApp.config(['$controllerProvider', function($controllerProvider) {
  $controllerProvider.allowGlobals();
}]);
MetronicApp.config(['$httpProvider',function($httpProvider){
   $httpProvider.interceptors.push('httpInterceptor');
   $httpProvider.defaults.headers.post = {
        'Content-Type': 'application/x-www-form-urlencoded'
   }
}]);
MetronicApp.factory('httpInterceptor', [ '$q', '$injector','$rootScope','$window','$timeout',function($q, $injector,$rootScope,$window,$timeout) {
	 var httpInterceptor = {
        'responseError' : function(response) {
        	var sign = 1;
            if(response.status == 404){
            	var rootScope = $injector.get('$rootScope');
            	console.info(rootScope);
                var state = $injector.get('$rootScope').$state.current.name;
                console.info(state);
                rootScope.stateBeforLogin = state;
                rootScope.$state.go("home");
                return $q.reject(response);
            }else if(response.status == 417){
            	return $q.reject(response);
            }else if (response.status == 401){
            	 $window.location = "./#/403.html";
            	 return $q.reject(response);
            }else if(response.status == 500){
                 $window.location.href = "./login.html";
                 return $q.reject(response);
            };
            return $q.reject(response);
        },
        'response' : function(response) {
            return response;
        },
        'request' : function(config) {
        	 //处理AJAX请求（否则后台IsAjaxRequest()始终false）
        	 config.headers['X-Requested-With'] = 'XMLHttpRequest';
        	 return config || $q.when(config);
        },
        'requestError' : function(config){
            return $q.reject(config);
        }
    }
    return httpInterceptor;
}]);
MetronicApp.controller('RegisterCtrl', ['$scope', '$http', '$q', '$rootScope', '$modal', function($scope, $http, $q, $rootScope, $modal) {


   // 初始化当前企业信息
   $scope.deptData = {};
   $scope.adminUser = {};
   // 企业登记注册相关情况
   $scope.applyList = [];
   $scope.applyMaxId = 0;
   $scope.addNullApply = function() {
   	$scope.applyMaxId = $scope.applyMaxId + 1;
   	$scope.applyList.push({
   		"id" : $scope.applyMaxId,
   		"name": "",
   		"levelTypeCode": "",
   		"number": ""
   	});
   }
   $scope.deleteOneApply = function(index) {
   	$scope.applyList.splice(index, 1);
   }

   // 股权情况
   $scope.stockList = [];
   $scope.stockMaxId = 0;
   $scope.addNullStock = function() {
   	$scope.stockMaxId = $scope.stockMaxId + 1;
   	$scope.stockList.push({
   		"id" : $scope.stockMaxId,
   		"holder": "",
   		"typeCode": "",
   		"ratio": ""
   	});
   }
   $scope.deleteOneStock = function(index) {
   	$scope.stockList.splice(index, 1);
   }

   // 表单提交
   $scope.submitDeptForm = function() {
       var editDeptData = $scope.deptData;
       if($scope.isNotValid(editDeptData)){
    	   $modal({
				scope : $scope,
				title : "提示",
				templateUrl : 'packages/sys/views/user/tip.html',
				content : '请填写<span style="color:#F00"> *</span> 标注的部分！',
				show : true,
				backdrop : "static"
			});
    	   return;
       }
       var deptFromParams = {};
		$.extend(deptFromParams, mergeJson('applys', $scope.applyList), mergeJson('dept', editDeptData), mergeJson('stocks', $scope.stockList), mergeJson('adminUser', $scope.adminUser));
       var reqDevData = mergeReauestData('DeptController', 'updateDept', deptFromParams);
       var editResqResult = sendPost($http, reqDevData, $q);
       editResqResult.then(function() {
	        var modalInstance = $modal(
	        {
	            templateUrl: 'packages/sys/views/dept/register/pop.html',
				show : false,
				container : '#modalView',
				backdrop : "static"
	        });
        	modalInstance.$promise.then(modalInstance.show);
       },function() {});
   }
   $scope.isNotValid = function(obj){
	   return  ( obj.name==undefined? 1:0) ||
	   		   ( obj.parent==undefined? 1:0)||
			   ( obj.orgCreatorMobile==undefined? 1:0) ||
			   ( obj.orgEnergyUtilize1Code==undefined? 1:0 ) ||
//			   ( obj.orgEnergyUtilize2Code==undefined? 1:0) ||
			   ( obj.orgLevelType==undefined? 1:0);
   }

   /*
    * 初始化必要字典以及关联表信息到画面中
    */
    // 上级单位列表
    sendPost($http, {
    	"controller": "DeptController",
    	"method": "queryAllNo3Dept"
    },$q).then(function(success) {
    	$scope.deptList = StrParesJSON(success).result;
    });

   // 字典 注册类型
   getDictList($http, $q, "DS_SUBREGISTERSTYLE").then(function (success) {
       $scope.orgSubregisterStyles = StrParesJSON(success).result;
   }, function (error) {
       console.info(error);
   });

   // 字典 单位规模
   getDictList($http, $q, "DS_ORGSIZE").then(function (success) {
       $scope.orgScales = StrParesJSON(success).result;
   }, function (error) {
       console.info(error);
   });

   // 字典 服务对象
   getDictList($http, $q, "DS_SERVICEOBJ").then(function (success) {
       $scope.orgServiceObjects = StrParesJSON(success).result;
   }, function (error) {
       console.info(error);
   });

   // 字典 调度方式
   getDictList($http, $q, "DS_DISPACHSTYLE").then(function (success) {
       $scope.orgDispachStyles = StrParesJSON(success).result;
   }, function (error) {
       console.info(error);
   });

   // 字典 能源利用类型1
   getDictList($http, $q, "DS_ENERGYUTILIZE1").then(function (success) {
       $scope.orgEnergyUtilize1s = StrParesJSON(success).result;
   }, function (error) {
       console.info(error);
   });
   $scope.queryEnergy2ByEnergy1 =  function(){

   	var energy1Id = Number(document.getElementsByName("registerEnergy1Name")[0].value.split(':')[1]);
//		var energy1Id = $scope.deptData.energyUtilizeStyle1.id;
		var energy1Params = {
				'controller': 'DictController',
				'method': 'getChildrenListByDictId',
				'dictId': energy1Id
		};
		var energy1AndChildren = sendPost($http, energy1Params, $q);
		energy1AndChildren.then(function(obj) {
			obj = JSON.parse(obj);
			$scope.orgEnergyUtilize2s = obj.result;
		}, function(error) {
		});
	}

   // 字典 能源利用类型2
//   getDictList($http, $q, "DS_ENERGYUTILIZE2").then(function (success) {
//       $scope.orgEnergyUtilize2s = StrParesJSON(success).result;
//   }, function (error) {
//       console.info(error);
//   });

    // 获取机关级别
    getDictList($http, $q, "DS_REGISTERLEVEL").then(function(success) {
        $scope.registerLevels = StrParesJSON(success).result;
    }, function(error) {});

    // 获取经济类型
    getDictList($http, $q, "DS_ECONOMYSTYLE").then(function(success) {
        $scope.types = StrParesJSON(success).result;
    }, function(error) {});

    // 获取机关级别
    getDictList($http, $q, "DS_REGISTERLEVEL").then(function(success) {
        $scope.registerLevels = StrParesJSON(success).result;
    }, function(error) {});

   // 字典 城市
   $http.post("packages/sys/views/dept/city.json").success(
       function (response) {
           $scope.division = response;
           $scope.oriDivision = response;
       });


   /*
    日期控件
    */
   $scope.today = function() {
   };
   $scope.today();
   $scope.clear = function() {
       $scope.deptData.orgFoundDate = null;
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
}]);
