/**
 * Created by Jimmybly Lee on 2017/5/29.
 */
function RecPayModifyCtrl($scope, $http, $q) {
    $scope.initDatePicker = function() {
        $('input[ng-model="recpay.date"]').datepicker();
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
        var recpay = $scope.recpay;
        sendPost($http, {
            controller: "RecPayController",
            method: $scope.method,
            "entity": JSON.stringify(recpay)
        }, $q).then(function() {
            $("div[ng-controller='RecPayListCtrl']").scope().load();
        })
    }
}
