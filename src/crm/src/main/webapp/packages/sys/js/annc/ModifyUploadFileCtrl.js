angular.module('MetronicApp').controller("ModifyUploadFileCtrl", ["$scope", "$http", "$q", "$modal", function ($scope, $http, $q, $modal) {
	
	$scope.changeFile = function(){//上传更改事件
//		debugger
//		var file = document.querySelector('input[id="annouAttInputModifyId"]').files[0];
//		var oriFileName = file.name;
//		var modalScope = $('div[ng-controller="AnncModifyCtrl"]').scope();//.  $('#AnncModifyId')
//		modalScope.attName = oriFileName;
//		modalScope.selected.attName = oriFileName;
//		modalScope.uploadFile = file;
//		modalScope.changeFileFlag = true;//设置更改附件的标识
		
	}
	$scope.ok = function(){
		var file = document.querySelector('input[id="annouAttInputModifyId"]').files[0];
		var oriFileName = file.name;
		var modalScope = $('div[ng-controller="AnncModifyCtrl"]').scope();//.  $('#AnncModifyId')
		modalScope.attName = oriFileName;
		modalScope.selected.attName = oriFileName;
		modalScope.uploadFile = file;
		modalScope.changeFileFlag = true;//设置更改附件的标识
	}
}]);