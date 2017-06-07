/**
 * Created by Jimmybly Lee on 2017/5/29.
 */
function ProjectListCtrl($scope, $http, $q, $listService) {
    getSelectValueByDictList($http, $q, "PROJ_STATUS", "NATURE").then(function(success) {
        $scope.statusList = StrParesJSON(success).result;
    });

    $scope.condition = {};

    $listService.init($scope, {
        "controller": "ProjectController",
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
        var modalScope = $("div[ng-controller='ProjectModifyCtrl']").scope();
        modalScope.project = {};
        modalScope.title = "新项目立项";
        modalScope.method = "create";
    };

    $scope.prepareToUpdate = function(item) {
        var modalScope = $("div[ng-controller='ProjectModifyCtrl']").scope();
        modalScope.project = item;
        modalScope.title = "项目变更";
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
                            "controller": "ProjectController",
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
