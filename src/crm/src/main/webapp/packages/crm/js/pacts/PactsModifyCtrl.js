/**
 * Created by Jimmybly Lee on 2017/5/29.
 */
function PactsModifyCtrl($scope, $http, $q) {
    getSelectValueByDictList($http, $q, "PACT_STATUS", "NATURE").then(function(success) {
        $scope.StatusList = StrParesJSON(success).result;
        console.log($scope.StatusList);
    }, function(error) {
        console.log(error);
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
