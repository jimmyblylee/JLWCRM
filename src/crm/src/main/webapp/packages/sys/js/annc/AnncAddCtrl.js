angular.module('MetronicApp').controller("AnncAddCtrl", ["$scope", "$http", "$q", "$modal", function ($scope, $http, $q, $modal) {

	//保存公告数据
	$scope.saveAnnouncement = function(){

		var fd = new FormData();
		var file = document.querySelector('input[id="annouAttInputId"]').files[0];
		var params = {};
		if (file != undefined) {
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
					$scope.newAdd.fileName = file.name;
					$scope.newAdd.hasAtt = 1;//是否有附件
					$scope.newAdd.isTop = 1;
					$scope.newAdd.attName = success.attName;
					$scope.newAdd.attPath = success.attPath;
//					$scope.newAdd.path = success.cns_file_result.url;
					params = {
							'controller': 'AnnouncementController',
							'method':'createAnnc',
							'formDatas':JSON.stringify($scope.newAdd)
					};
					$scope.saveAddDatas(params);
				})
		}else{
			$scope.newAdd.hasAtt = 0;//是否有附件
			$scope.newAdd.isTop = 1;
			params = {
					'controller': 'AnnouncementController',
					'method':'createAnnc',
					'formDatas':JSON.stringify($scope.newAdd)
			};
			$scope.saveAddDatas(params);
		}
	}
	$scope.saveAddDatas = function(params){
		var insertPost = sendPost($http, params, $q);
		insertPost.then(function(resultObj){
			resultObj = JSON.parse(resultObj);
			if(resultObj.result){
				 bootbox.dialog({
	                    title: "提示",
	                    message: "添加成功!",
	                    buttons: {main: {label: " 确 定 "}}
	                });
				 var modalScope = $('div[ng-controller="AnnouMgntCtrl"]').scope();
				 modalScope.queryAnnouResult(1);
				 $('div[id="AnncAddId"]').modal('hide');
			}else{
				 bootbox.dialog({
	                    title: "提示",
	                    message: "添加失败!",
	                    buttons: {main: {label: " 确 定 "}}
	                });
				 return;
			}
		});
	}

}]);
