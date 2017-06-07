/**
 * Created by Jimmybly Lee on 2017/5/29.
 */
function PactsListCtrl($scope, $http, $q, $listService) {

    $scope.condition = {};

    getSelectValueByDictList($http, $q, "PACT_STATUS", "NATURE").then(function(success) {
        $scope.StatusList = StrParesJSON(success).result;
        console.log($scope.StatusList);
    }, function(error) {
        console.log(error);
    });

    $listService.init($scope, {
        "controller": "PactController",
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
        var modalScope = $("div[ng-controller='PactsModifyCtrl']").scope();
        modalScope.pacts = {};
        modalScope.title = "增加合同";
        modalScope.method = "create";
    };

    $scope.prepareToUpdate = function(item) {
        var modalScope = $("div[ng-controller='PactsModifyCtrl']").scope();
        modalScope.pacts = item;
        modalScope.title = "更新合同";
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
                            "controller": "PactController",
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
