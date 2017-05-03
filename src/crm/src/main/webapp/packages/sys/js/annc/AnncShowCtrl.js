angular.module('MetronicApp').controller("AnncShowCtrl", ["$scope", "$http", "$q", "$modal", function ($scope, $http, $q, $modal) {

	$scope.downAnncAtt = function(annc){
		window.open("mvc/dispatch?controller=AnnouncementController&method=downAnncAtt&path="+annc.attPath+"&attName="+annc.attName,"_blank");
	}

}]);
