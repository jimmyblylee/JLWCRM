/**
 * Created by Jimmybly Lee on 2017/5/29.
 */
function ClewsListCtrl($scope, $http, $q, $listService) {

    $scope.condition = {};

    getSelectValueByDictList($http, $q, "CLEW_PERIOD", "NATURE").then(function(success) {
        $scope.PeriodList = StrParesJSON(success).result;
        console.log($scope.PeriodList);
    }, function(error) {
        console.log(error);
    });

    sendPost($http, {
        "controller" : "CustomerController",
        "method" : "query",
        "pageQuery" : "{}",
        "start" : 0,
        "limit" : 1000
    },$q).then(function(success) {
        $scope.customerList = StrParesJSON(success).result;
    });

    sendPost($http, {
        "controller" : "ContactController",
        "method" : "query",
        "pageQuery" : "{}",
        "start" : 0,
        "limit" : 1000
    },$q).then(function(success) {
        $scope.contactList = StrParesJSON(success).result;
    });

    sendPost($http, {
        "controller" : "SalesController",
        "method" : "query",
        "pageQuery" : "{}",
        "start" : 0,
        "limit" : 1000
    },$q).then(function(success) {
        $scope.saleList = StrParesJSON(success).result;
    });


    $listService.init($scope, {
        "controller": "ClewController",
        "method": "query",
        // "pageQuery" : $scope.condition,
        callback: function(success) {
            $scope.list = success.data.result;
        }
    });
    $scope.load = function() {
        $listService.load();
    };
    $scope.load();

    $scope.prepareToAdd = function() {
        var modalScope = $("div[ng-controller='ClewsModifyCtrl']").scope();
        modalScope.clews = {};
        modalScope.title = "增加线索";
        modalScope.method = "create";
    };

    $scope.prepareToUpdate = function(item) {
        var modalScope = $("div[ng-controller='ClewsModifyCtrl']").scope();
        modalScope.clews = item;
        modalScope.title = "更新线索";
        modalScope.method = "update";
    };

    $scope.remove = function(item) {
        bootbox.dialog({
            message: "是否确认要删除？",
            title: "请您确认",
            buttons: {
                success: {
                    label: " 删 除 ! ",
                    className: "red fa fa-ban",
                    callback: function() {
                        sendPost($http, {
                            "controller": "ClewController",
                            "method": "remove",
                            "id": item.id
                        }, $q).then(function() {
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
}
