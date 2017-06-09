/**
 * Created by Jimmybly Lee on 2017/5/29.
 */
function CostModifyCtrl($scope, $http, $q) {
    $scope.initDatePicker = function() {
        $('input[ng-model="cost.time"]').datepicker();
    };

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
        var cost = $scope.cost;
        if ($scope.pageBarData.pageBarChildItmeSub == "销售") {
            cost.type = { "id" : -10901}
        } else if ($scope.pageBarData.pageBarChildItmeSub == "实施") {
            cost.type = { "id" : -10902}
        } else {
            cost.type = { "id" : -10903}
        }
        sendPost($http, {
            controller: "CostController",
            method: $scope.method,
            "entity": JSON.stringify(cost)
        }, $q).then(function() {
            $("div[ng-controller='CostListCtrl']").scope().load();
        });
    }
}
