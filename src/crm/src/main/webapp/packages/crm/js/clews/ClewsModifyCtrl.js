/**
 * Created by Jimmybly Lee on 2017/5/29.
 */
function ClewsModifyCtrl($scope, $http, $q) {
    getSelectValueByDictList($http, $q, "CLEW_PERIOD", "NATURE").then(function(success) {
        $scope.PeriodList = StrParesJSON(success).result;
        console.log($scope.PeriodList);
    }, function(error) {
        console.log(error);
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
