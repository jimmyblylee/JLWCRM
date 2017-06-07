/**
 * Created by Jimmybly Lee on 2017/5/29.
 */
function CostListCtrl($scope, $http, $q, $listService) {
    var type;
    if ($scope.pageBarData.pageBarChildItmeSub == "销售") {
        type = { "code" : "1"}
    } else if ($scope.pageBarData.pageBarChildItmeSub == "实施") {
        type = { "code" : "2"}
    } else {
        type = { "code" : "3"}
    }
    $scope.condition = {
        "type" : type
    };

    $listService.init($scope, {
        "controller": "CostController",
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
        var modalScope = $("#modifyCostModalDiv").scope();
        modalScope.cost = {};
        modalScope.title = "新增支出";
        modalScope.method = "create";
    };

    $scope.prepareToUpdate = function(item) {
        var modalScope = $("#modifyCostModalDiv").scope();
        modalScope.cost = item;
        modalScope.title = "修改支出";
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
                            "controller": "CostController",
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
    };
}
