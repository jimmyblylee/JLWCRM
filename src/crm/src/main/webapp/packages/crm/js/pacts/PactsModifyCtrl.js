/**
 * Created by Jimmybly Lee on 2017/5/29.
 */
function PactsModifyCtrl($scope, $http, $q) {
    $scope.initDatePicker = function() {
        $('input[ng-model=\'pacts.date\']').datepicker();
    };
    getSelectValueByDictList($http, $q, "PACT_STATUS", "NATURE").then(function(success) {
        $scope.StatusList = StrParesJSON(success).result;
        console.log($scope.StatusList);
    }, function(error) {
        console.log(error);
    });

    sendPost($http, {
        "controller" : "CustomerController",
        "method" : "query",
        "pageQuery" : "{}",
        "start" : 0,
        "limit" : 1000
    },$q).then(function(success) {
        $scope.customerList = StrParesJSON(success).result;
    });

    sendPost($http, {
        "controller" : "SalesController",
        "method" : "query",
        "pageQuery" : "{}",
        "start" : 0,
        "limit" : 1000
    },$q).then(function(success) {
        $scope.saleList = StrParesJSON(success).result;
    });

    $scope.submit = function() {
        var pacts = $scope.pacts;
        sendPost($http, {
            controller: "PactController",
            method: $scope.method,
            "entity": JSON.stringify(pacts)
        }, $q).then(function() {
            $("div[ng-controller='PactsListCtrl']").scope().load();
        })
    }
}
