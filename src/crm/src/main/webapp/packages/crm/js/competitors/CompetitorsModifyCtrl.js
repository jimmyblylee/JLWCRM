/**
 * Created by Jimmybly Lee on 2017/5/29.
 */
function CompetitorsModifyCtrl($scope, $http, $q) {
    $scope.submit = function() {
        var competitors = $scope.competitors;

        sendPost($http, {
            controller: "CompetitorController",
            method: $scope.method,
            "entity": JSON.stringify(competitors)
        }, $q).then(function() {
            $("div[ng-controller='CompetitorsListCtrl']").scope().load();
        })
    }
}
