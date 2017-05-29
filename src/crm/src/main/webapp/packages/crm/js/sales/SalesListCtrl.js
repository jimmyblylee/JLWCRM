/**
 * Created by Jimmybly Lee on 2017/5/29.
 */
function SalesListCtrl($scope, $http, $q, $listService) {

    $scope.condition = {};

    $listService.init($scope, {
        "controller": "SalesController",
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
        var modalScope = $("div[ng-controller='SalesModifyCtrl']").scope();
        modalScope.sales = {};
        modalScope.title = "增加销售人员";
        modalScope.method = "create";
    };

    $scope.prepareToUpdate = function(item) {
        var modalScope = $("div[ng-controller='SalesModifyCtrl']").scope();
        modalScope.sales = item;
        modalScope.title = "更新销售人员";
        modalScope.method = "update";
    };

    $scope.delete = function(item) {
        bootbox.dialog({
            message: "是否确认要删除" + item.name + "？",
            title: "请您确认",
            buttons: {
                success: {
                    label: " 删 除 ! ",
                    className: "red fa fa-ban",
                    callback: function() {
                        sendPost($http, {
                            "controller": "SalesController",
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