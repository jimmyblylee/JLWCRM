/**
 * Created by Jimmybly Lee on 2017/5/29.
 */
function SalesModifyCtrl($scope, $http, $q) {
    $scope.submit = function() {
        var sales = $scope.sales;

        sendPost($http, {
            controller: "SalesController",
            method: $scope.method,
            "entity": JSON.stringify(sales)
        }, $q).then(function() {
            $("div[ng-controller='SalesListCtrl']").scope().load();
        })
    }
}
