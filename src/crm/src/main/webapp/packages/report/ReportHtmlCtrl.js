var MetronicApp = angular.module("MetronicApp", []);
    
    MetronicApp.controller('reportCtrl', ['$scope','$http','$q',function($scope,$http,$q) {
        
        $scope.test = function() {
            var reportParams = mergeJson('report',$scope.report);
            var reportData = mergeReauestData('DemoReportController','initData',reportParams);
            var result = senReportInitData($http,reportData,$q);
            result.then(function(success){
                if(StrParesJSON(success).result){
                	window.open("http://localhost:8080/jbp/mvc/testDemo?modelName=reportDemo01.jrxml");
                }
            },function(error){
                console.info(error);
            });
        }
 }])
 
 function senReportInitData(request, data, $q){
	var deferred = $q.defer();

    request({
        method : 'post',
        url : 'http://localhost:8080/jbp/mvc/dispatch',
        data : data,
        headers : {
            'Content-Type' : 'application/x-www-form-urlencoded'
        },
        transformRequest : function(obj) {
            var str = [];
            for ( var p in obj) {
                str.push(encodeURIComponent(p) + "="
                        + encodeURIComponent(obj[p]));
            }
            return str.join("&");
        }
    }).success(function(req,status, headers, cfg) {
        //console.info('postsuccess=' + req);
        deferred.resolve(ObjParesJSON(req));
    }).error(function(rep, status, headers, cfg) {
        //console.info('posterror=' + ObjParesJSON(rep));
        deferred.reject(ObjParesJSON(rep));
    })

    return deferred.promise;
}