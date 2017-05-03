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
angular.module("MetronicApp").controller("DictListCtrl", function ($scope, $modal, $http, $q, $rootScope, $listService) {
    // 处理查询条件和分页服务初始化
    $scope.condition = {"isEnabled": "true"};
    $listService.init($scope, {
        "controller": "DictController",
        "method": "queryFuzzyAllDict",
        callback: function(success) {
            $scope.dictList = success.data.result;
        }
    });
    $scope.load = function() {
        $listService.load();
    };
    $scope.load();

    // 字典项 是否可用
    getSelectValueByDictList($http, $q, 'STATUS_TYPE', 'STATUS_CODE').then(function (success) {
        $scope.IsEnabledJson = StrParesJSON(success).result;
    }, function (error) {
        console.info(error);
    });

    $scope.prepareToAdd = function() {
        var modalScope = $('div[ng-controller="DictModifiedCtrl"]').scope();
        modalScope.modalTitle = "添加字典";
        modalScope.dict = {};
        modalScope.method = "createDict";
    };

    $scope.prepareToUpdate = function(dict) {
        var modalScope = $('div[ng-controller="DictModifiedCtrl"]').scope();
        modalScope.modalTitle = "修改字典";
        modalScope.dict = dict;
        modalScope.method = "updateDict";
    };

    $scope.singleDelete = function(dictId) {
        bootbox.dialog({
            message: "是否删除字典项？",
            title: "请您确认",
            buttons: {
                success: {
                    label: " 删 除 ! ",
                    className: "red fa fa-trash",
                    callback: function() {
                        sendPost($http, {
                            "controller": "DictController",
                            "method": "deleteDict",
                            "dictId": dictId
                        },$q).then(function() {
                            $scope.load();
                        }, function() {});
                    }
                },
                main: {
                    label: " 取 消 ",
                    className: "btn-outline dark"
                }
            }
        });
    };

    $scope.recoverDictState = function(dictId) {
        bootbox.dialog({
            message: "是否恢复使用字典项？",
            title: "请您确认",
            buttons: {
                success: {
                    label: " 恢 复 ! ",
                    className: "green-meadow fa fa-trash",
                    callback: function() {
                        sendPost($http, {
                            "controller": "DictController",
                            "method": "reciveDict",
                            "dictId": dictId
                        },$q).then(function() {
                            $scope.load();
                        }, function() {});
                    }
                },
                main: {
                    label: " 取 消 ",
                    className: "btn-outline dark"
                }
            }
        });
    }
});
