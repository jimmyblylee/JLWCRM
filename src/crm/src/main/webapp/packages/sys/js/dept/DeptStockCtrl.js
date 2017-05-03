/**
 * Created by JimmyblyLee on 2016/12/21.
 */
// 公司的批准机关主页面控制器
function DeptStockCtrl($scope, $modal, $http, $q) {

    var deptId = $scope.assignedDept.id;

    // 获取当前公司的批准机关的列表
    $scope.refreshList = function() {
        sendPost($http, {
            "controller": "DeptController",
            "method": "queryDsDeptStock",
            "deptId": deptId
        }, $q).then(function(success){
            $scope.stockList = StrParesJSON(success).result;
        }, function(err) {});
    }
    $scope.refreshList();

    // 获取经济类型
    getDictList($http, $q, "DS_ECONOMYSTYLE").then(function(success) {
        $scope.types = StrParesJSON(success).result;
    }, function(error) {});

    $scope.deleteStock = function(stockId) {
        sendPost($http, {
            "controller": "DeptController",
            "method": "deleteDsDeptStock",
            "stockId": stockId
        }, $q).then(function(success){
            $scope.refreshList();
        }, function(err) {});
    };

    $scope.addStock = function() {
        $scope.actionName = "添加";
        $scope.stock = undefined;
        var modalInstance = $modal({
            scope: $scope,
            templateUrl : 'packages/sys/views/dept/stock/edit.html',
            show : false,
            controller : 'EditStockCtrl',
            backdrop : "static"
        });
        modalInstance.$promise.then(modalInstance.show);
    };

    $scope.editStock = function(stock) {
        $scope.actionName = "修改";
        $scope.stock = stock;
        var modalInstance = $modal({
            scope: $scope,
            templateUrl : 'packages/sys/views/dept/stock/edit.html',
            show : false,
            controller : 'EditStockCtrl',
            backdrop : "static"
        });
        modalInstance.$promise.then(modalInstance.show);
    };
}

function EditStockCtrl($scope, $modal, $http, $q , $rootScope) {

    $scope.submit = function() {
        $scope.stock.deptId = $scope.assignedDept.id;
        sendPost($http, {
            "controller": "DeptController",
            "method": $scope.actionName == "添加" ? "createDsDeptStock" : "updateDsDeptStock",
            "stock": ObjParesJSON($scope.stock)
        }, $q).then(function(success){
            $scope.refreshList();
            $scope.$hide();
        }, function(err) {});
    }
}