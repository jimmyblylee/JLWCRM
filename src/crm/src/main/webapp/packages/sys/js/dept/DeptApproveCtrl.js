angular.module('MetronicApp').controller("DeptApproveCtrl", function ($rootScope, $scope, $http, $q, $listService) {
    $.getJSON('packages/sys/js/apply.json', function (data) {
        $scope.statusCfg = data;
    });

    $scope.condition = {"deptId": $rootScope.token.user.dept.id};
    $listService.init($scope, {
        "controller": "DeptApplyController",
        "method": "queryTask",
        callback: function(success) {
            $scope.taskList = success.data.result;
        }
    });
    $scope.loadTask = function() {
        $scope.pageRequest.getResponse();
    };
    $scope.loadTask();

    $scope.approve = function(taskId, action) {
        bootbox.dialog({
            message: action.indexOf("pass") > -1 ? "是否通过？" : "是否驳回？",
            title: "请您确认",
            buttons: {
                success: {
                    label: action.indexOf("pass") > -1 ? " 通 过 " : " 驳 回 ",
                    className: action.indexOf("pass") > -1 ? "blue-dark fa fa-check-square" : "red fa fa-ban",
                    callback: function() {
                        sendPost($http, {
                            "controller": "DeptApplyController",
                            "method": action,
                            "taskId": taskId
                        }, $q).then(function () {
                            $scope.loadTask();
                        });
                    }
                },
                main: {
                    label: " 取 消 ",
                    className: "btn-outline dark"
                }
            }
        });
    };
    $scope.showDetail = function(deptId) {
        $('#viewDeptToBeApprovedDetailModal').scope().refresh(deptId, true);
    }
});
