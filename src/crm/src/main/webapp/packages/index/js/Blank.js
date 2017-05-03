/**
 * Created by JimmyblyLee on 2017/1/17.
 * Copyright © 2006, 2016, Jimmybly Lee. All rights reserved.
 */
angular.module("MetronicApp").controller("Blank", ["$scope", "$rootScope", function($scope, $rootScope) {
    $scope.pageNum = $rootScope.settings.pageNum;
}]);
