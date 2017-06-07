/**
 * Created by Jimmybly Lee on 2017/6/7.
 */
function ProjRiskCtrl($scope, $http, $q) {
    sendPost($http, {
        "controller" : "RiskController",
        "method" : "queryProjectRisk"
    }, $q).then(function(success){
        $scope.list = StrParesJSON(success).result;
    });
}
