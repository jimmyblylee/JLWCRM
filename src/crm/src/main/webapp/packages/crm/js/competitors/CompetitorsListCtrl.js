/**
 * Created by Jimmybly Lee on 2017/5/29.
 */
function CompetitorsListCtrl($scope, $http, $q, $listService) {

    $scope.condition = {};

    $listService.init($scope, {
        "controller": "CompetitorController",
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
        var modalScope = $("div[ng-controller='CompetitorsModifyCtrl']").scope();
        modalScope.competitors = {};
        modalScope.title = "增加竞争对手";
        modalScope.method = "create";
    };

    $scope.prepareToUpdate = function(item) {
        var modalScope = $("div[ng-controller='CompetitorsModifyCtrl']").scope();
        modalScope.competitors = item;
        modalScope.title = "更新竞争对手";
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
                            "controller": "CompetitorController",
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
