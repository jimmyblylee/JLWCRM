/**
 * Created by Jimmybly Lee on 2017/5/29.
 */
function ClewsModifyCtrl($scope, $http, $q) {

    $scope.initDatePicker = function() {
        $('input[ng-model=\'clews.proposal\']').datepicker();
    };
    getSelectValueByDictList($http, $q, "CLEW_PERIOD", "NATURE").then(function(success) {
        $scope.PeriodList = StrParesJSON(success).result;
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
        "controller" : "ContactController",
        "method" : "query",
        "pageQuery" : "{}",
        "start" : 0,
        "limit" : 1000
    },$q).then(function(success) {
        $scope.contactList = StrParesJSON(success).result;
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
        var clews = $scope.clews;
        sendPost($http, {
            controller: "ClewController",
            method: $scope.method,
            "entity": JSON.stringify(clews)
        }, $q).then(function() {
            $("div[ng-controller='ClewsListCtrl']").scope().load();
        })
    }
}
