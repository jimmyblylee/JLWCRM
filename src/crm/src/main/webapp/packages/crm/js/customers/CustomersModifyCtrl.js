/**
 * Created by Jimmybly Lee on 2017/5/29.
 */
function CustomersModifyCtrl($scope, $http, $q) {
    getSelectValueByDictList($http, $q, "CUSTOMER_TRADE", "NATURE").then(function(success) {
        $scope.TradeList = StrParesJSON(success).result;
        console.log($scope.TradeList);
    }, function(error) {
        console.log(error);
    });

    getSelectValueByDictList($http, $q, "CUSTOMER_QUALITY", "NATURE").then(function(success) {
        $scope.QualityList = StrParesJSON(success).result;
        console.log($scope.QualityList);
    }, function(error) {
        console.log(error);
    });

    getSelectValueByDictList($http, $q, "CUSTOMER_SCOPE", "NATURE").then(function(success) {
        $scope.ScopeList = StrParesJSON(success).result;
        console.log($scope.ScopeList);
    }, function(error) {
        console.log(error);
    });

    $scope.submit = function() {
        var customers = $scope.customers;
        sendPost($http, {
            controller: "CustomerController",
            method: $scope.method,
            "entity": JSON.stringify(customers)
        }, $q).then(function() {
            $("div[ng-controller='CustomersListCtrl']").scope().load();
        })
    }
}
