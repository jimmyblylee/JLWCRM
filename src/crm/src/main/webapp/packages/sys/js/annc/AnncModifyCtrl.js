angular.module('MetronicApp').controller("AnncModifyCtrl", ["$scope", "$http", "$q", "$modal", function ($scope, $http, $q, $modal) {

	//保存公告数据
	$scope.saveAnnouncement = function(){

		var fd = new FormData();
		var file =null;
		if($scope.changeFileFlag){
			file = $scope.uploadFile;
		}
		var params = {};
		if (file != null) {
			fd.append('customFilter', file);

			$http({
				method : 'POST',
				url : "/jbp/mvc/dispatch?controller=AnnouncementController&method=saveUploadedReportFile",
				data : fd,
				headers : {
					'Content-Type' : undefined
				},
				transformRequest : angular.identity
			}).success(
				function(success) {
					$scope.selected.fileName = file.name;
					$scope.selected.hasAtt = 1;//是否有附件
					$scope.selected.attName = success.attName;
					$scope.selected.attPath = success.attPath;
					params = {
							'controller': 'AnnouncementController',
							'method':'updateAnnc',
							'formDatas':JSON.stringify($scope.selected)
					};
					$scope.saveModifyDatas(params);
				})
		}else{
			params = {
					'controller': 'AnnouncementController',
					'method':'updateAnnc',
					'formDatas':JSON.stringify($scope.selected)
			};
			$scope.saveModifyDatas(params);
		}
	}
	$scope.saveModifyDatas = function(params){
		var insertPost = sendPost($http, params, $q);
		insertPost.then(function(resultObj){
			resultObj = JSON.parse(resultObj);
			if(resultObj.result){
				 bootbox.dialog({
	                    title: "提示",
	                    message: "编辑成功!",
	                    buttons: {main: {label: " 确 定 "}}
	                });
				 var modalScope = $('div[ng-controller="AnnouMgntCtrl"]').scope();
				 modalScope.queryAnnouResult(1);
				 $('div[id="AnncModifyId"]').modal('hide');
			}else{
				 bootbox.dialog({
	                    title: "提示",
	                    message: "编辑失败!",
	                    buttons: {main: {label: " 确 定 "}}
	                });
				 return;
			}
		});
	}
		
	//放弃修改附件
	$scope.discardChangeFile = function(){
		debugger
		$scope.selected.attName = $scope.oriModifyObj.attName;
		$scope.changeFileFlag = false;
		
	}

}]);