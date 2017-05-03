/* ***************************************************************************
 * EZ.JWAF/EZ.JCWAP: Easy series Production.
 * Including JWAF(Java-based Web Application Framework)
 * and JCWAP(Java-based Customized Web Application Platform).
 * Copyright (C) 2016-2017 the original author or authors.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of MIT License as published by
 * the Free Software Foundation;
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the MIT License for more details.
 *
 * You should have received a copy of the MIT License along
 * with this library; if not, write to the Free Software Foundation.
 * ***************************************************************************/
angular.module("MetronicApp").controller("DictParentCtrl", function ($scope, $modal, $http, $q) {
    getTreeAndView($scope, $http, $q, 'DictController', 'getDictTreeParent', "");
    $scope.selectNode = function(node) {
        $scope.id = node.id;
        $scope.label = node.label;
    };
    $scope.commit = function() {
        var modifyScope = $('div[ng-controller="DictModifiedCtrl"]').scope();
        modifyScope.dict.parent = {"id":$scope.id, "value": $scope.label};
    }
});
