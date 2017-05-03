function AnnouMgntCtrl($scope, $filter, $http, $q, $modal, $log) {
	$scope.selectedPeriod = [];//选中的统计周期选项值
	$scope.selectedPeriodName = [];//选中的统计周期的选项值的名称

	$scope.itemsPerPage = 10;
	$scope.Pagenum = Pagenum;
	/**
	 * 获得指标信息列表**
	 */
	$scope.queryAnnouResult = function(currentPage){
		if (currentPage == undefined || currentPage == "") {
			currentPage = 1;
		}
		$scope.currentPage = currentPage;
		var searchCondition = $scope.searchCondition;//查询条件：指标名称
		if(searchCondition==undefined){
			searchCondition = "";
		}
		$scope.announArrayLists = {};
		queryParams = {
				'controller': 'AnnouncementController',
				'method':'queryAnncByCondition',
				'title': searchCondition,
				'isMenu': 1,
				"start":(currentPage - 1) * $scope.itemsPerPage,
				'limit': $scope.itemsPerPage
		};
		var queryResult = sendPost($http, queryParams, $q);
		queryResult.then(function(obj){
			obj = JSON.parse(obj);
			$scope.renderData( obj.result);
			$scope.announArrayLists = obj.result;
			$scope.totalItems = obj.total;
		});
	}
	$scope.queryAnnouResult(1);

	//去掉时间里的T
	$scope.renderData = function(objList){
		for(var i=0; i<objList.length; i++){
			if( objList[i].isTop) {
				objList[i].toTopRender = "是";
			}else{
				objList[i].toTopRender = "否";
			}
			if( objList[i].hasAtt){
				objList[i].hasAttachRender = "是";
			}else{
				objList[i].hasAttachRender = "否";
			}
			var da = objList[i].publishDate;
			if(da!=""){
				var end = da.indexOf("T");
				objList[i].publishDate = da.slice(0, end);
			}
			 var validDate =  objList[i].validEndDate;
			 if(validDate != ""){
				 var end1 = validDate.indexOf("T");
				 objList[i].validEndDate = validDate.slice(0, end1);
			 }
		}
	}

	$scope.resetSearch = function(){
		$scope.searchCondition = "";
	}

	$scope.deepCopy = function(source){
		var result = {};
		for(var key in source){
			result[key] = source[key];
		}
		return result;
	}

	$scope.showModifyAnnouWin = function(obj){
		 var modalScope = $('div[ng-controller="AnncModifyCtrl"]').scope();
		 var result = $scope.deepCopy(obj);
		 modalScope.selected = result;
		 modalScope.changeFileFlag = false;//默认没有更改附加
		 var oriModifyObj =  $scope.deepCopy(obj);
		 modalScope.oriModifyObj = oriModifyObj;
	}
	
	$scope.showAnnouWin = function(obj){
		var modalScope = $('div[ng-controller="AnncShowCtrl"]').scope();
		 var result = $scope.deepCopy(obj);
		 modalScope.showAnnc = result;
	}

	$scope.deleteAnnou = function(obj){
		var annouId = obj.id;
		var deleteParams = {
				'controller': 'AnnouncementController',
				'method':'deleteAnnc',
				'anncId': annouId
		};

		var deleteObj = sendPost($http, deleteParams, $q);
		deleteObj.then(function(resultObj){
			resultObj = JSON.parse(resultObj);
			if(resultObj.result){
				bootbox.dialog({
                    title: "提示",
                    message: "删除成功!",
                    buttons: {main: {label: " 确 定 "}}
                });
				var modalScope = $('div[ng-controller="AnnouMgntCtrl"]').scope();
				modalScope.queryAnnouResult(1);
			}else{
				bootbox.dialog({
                    title: "提示",
                    message: "删除失败!",
                    buttons: {main: {label: " 确 定 "}}
                });
			}
		});
	}
}
