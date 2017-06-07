/**
 * Created by Jimmybly Lee on 2017/5/29.
 */
function ProjectModifyCtrl($scope, $http, $q) {
    getSelectValueByDictList($http, $q, "PROJ_STATUS", "NATURE").then(function(success) {
        $scope.statusList = StrParesJSON(success).result;
    });

    sendPost($http, {
        "controller" : "ImplController",
        "method" : "query",
        "pageQuery" : "{}",
        "start" : 0,
        "limit" : 1000
    },$q).then(function(success) {
        $scope.chargerList = StrParesJSON(success).result;
    });

    sendPost($http, {
        "controller" : "PactController",
        "method" : "query",
        "pageQuery" : "{}",
        "start" : 0,
        "limit" : 1000
    },$q).then(function(success) {
        $scope.pactList = StrParesJSON(success).result;
    });

    $scope.submit = function() {
        var project = $scope.project;
        sendPost($http, {
            controller: "ProjectController",
            method: $scope.method,
            "entity": JSON.stringify(project)
        }, $q).then(function() {
            $("div[ng-controller='ProjectListCtrl']").scope().load();
        })
    }
}
