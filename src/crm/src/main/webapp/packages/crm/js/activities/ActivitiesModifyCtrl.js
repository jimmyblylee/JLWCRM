/**
 * Created by Jimmybly Lee on 2017/5/29.
 */
function ActivitiesModifyCtrl($scope, $http, $q) {
    $scope.submit = function() {
        var activities = $scope.activities;

        sendPost($http, {
            controller: "ActivityController",
            method: $scope.method,
            "entity": JSON.stringify(activities)
        }, $q).then(function() {
            $("div[ng-controller='ActivitiesListCtrl']").scope().load();
        })
    }
}
