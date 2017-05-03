/**
 * Created by JimmyblyLee on 2016/12/21.
 */
// 公司的批准机关主页面控制器
function DeptApplyCtrl($scope, $modal, $http, $q) {

    var deptId = $scope.assignedDept.id;

    // 获取当前公司的批准机关的列表
    $scope.refreshList = function() {
        sendPost($http, {
            "controller": "DeptController",
            "method": "queryDsDeptApply",
            "deptId": deptId
        }, $q).then(function(success){
            $scope.applyList = StrParesJSON(success).result;
        }, function(err) {});
    }
    $scope.refreshList();

    // 获取机关级别
    getDictList($http, $q, "DS_REGISTERLEVEL").then(function(success) {
        $scope.registerLevels = StrParesJSON(success).result;
    }, function(error) {});

    $scope.deleteApply = function(applyId) {
        sendPost($http, {
            "controller": "DeptController",
            "method": "deleteDsDeptApply",
            "applyId": applyId
        }, $q).then(function(success){
            $scope.refreshList();
        }, function(err) {});
    };

    $scope.addApply = function() {
        $scope.actionName = "添加";
        $scope.officeApply = undefined;
        $scope.closeEditModal = function() {
            modalInstance.close();
        }
        var modalInstance = $modal({
            scope: $scope,
            templateUrl : 'packages/sys/views/dept/office-apply/edit.html',
            show : false,
            controller : 'EditOfficeApplyCtrl',
            backdrop : "static"
        });
        modalInstance.$promise.then(modalInstance.show);
    };

    $scope.editApply = function(officeApply) {
        $scope.actionName = "修改";
        $scope.officeApply = officeApply;
        $scope.closeEditModal = function() {
            modalInstance.close();
        }
        var modalInstance = $modal({
            scope: $scope,
            templateUrl : 'packages/sys/views/dept/office-apply/edit.html',
            show : false,
            controller : 'EditOfficeApplyCtrl',
            backdrop : "static"
        });
        modalInstance.$promise.then(modalInstance.show);
    };
}

function EditOfficeApplyCtrl($scope, $modal, $http, $q , $rootScope) {

    $scope.submit = function() {
        $scope.officeApply.deptId = $scope.assignedDept.id;
        sendPost($http, {
            "controller": "DeptController",
            "method": $scope.actionName == "添加" ? "createDsDeptApply" : "updateDsDeptApply",
            "apply": ObjParesJSON($scope.officeApply)
        }, $q).then(function(success){
            $scope.refreshList();
            $scope.$hide();
        }, function(err) {});
    }
}