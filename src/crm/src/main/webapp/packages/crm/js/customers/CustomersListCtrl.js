/**
 * Created by Jimmybly Lee on 2017/5/29.
 */
function CustomersListCtrl($scope, $http, $q, $listService) {

    $scope.condition = {};

    sendPost($http, {
        "controller" : "CustomerController",
        "method" : "query",
        "pageQuery" : "{}",
        "start" : 0,
        "limit" : 1000
    },$q).then(function(success) {
        $scope.customerList = StrParesJSON(success).result;
    });

    $listService.init($scope, {
        "controller": "CustomerController",
        "method": "query",
        callback: function(success) {
            $scope.list = success.data.result;
        }
    });
    $scope.load = function() {
        $listService.load();
    };
    $scope.load();

    $scope.prepareToAdd = function() {
        var modalScope = $("div[ng-controller='CustomersModifyCtrl']").scope();
        modalScope.customers = {};
        modalScope.title = "增加客户";
        modalScope.method = "create";
    };

    $scope.prepareToUpdate = function(item) {
        var modalScope = $("div[ng-controller='CustomersModifyCtrl']").scope();
        modalScope.customers = item;
        modalScope.title = "更新客户";
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
                            "controller": "CustomerController",
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
