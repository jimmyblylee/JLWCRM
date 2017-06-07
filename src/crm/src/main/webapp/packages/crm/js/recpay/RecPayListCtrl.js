/**
 * Created by Jimmybly Lee on 2017/5/29.
 */
function RecPayListCtrl($scope, $http, $q, $listService) {
    $scope.initDatePicker = function() {
        $('input[ng-model="condition.date"]').datepicker();
    };

    $scope.condition = {};

    $listService.init($scope, {
        "controller": "RecPayController",
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
        var modalScope = $("div[ng-controller='RecPayModifyCtrl']").scope();
        modalScope.recpay = {};
        modalScope.title = "增加回款";
        modalScope.method = "create";
    };

    $scope.prepareToUpdate = function(item) {
        var modalScope = $("div[ng-controller='RecPayModifyCtrl']").scope();
        modalScope.recpay = item;
        modalScope.title = "更新回款";
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
                            "controller": "RecPayController",
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
