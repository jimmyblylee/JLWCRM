/**
 * Created by JimmyblyLee on 2016/12/22.
 */
function MyDeptEditCtrl($scope, $modal, $http, $q, $rootScope) {

    // 初始化当前企业信息
    $scope.refresh = function() {
        sendPost($http, {
            "controller": "DeptController",
            "method": "getDeptById",
            "deptId": $rootScope.token.user.dept.id
        }, $q).then(function (success) {
            $scope.deptData = StrParesJSON(success).result;

            // 获得上级组织机构信息
            sendPost($http, {
                "controller": "DeptController",
                "method": "getParentDeptInfo",
                "deptId": $rootScope.token.user.dept.id
            }, $q).then(function (success) {
                $scope.deptData.parent = StrParesJSON(success).result;
                $scope.parentname = $scope.deptData.parent.name;
                $scope.parentid = $scope.deptData.parent.id;
            }, function (err) {
            });
        }, function (err) {
        });
    }
    $scope.refresh();

    sendPost($http, {
        "controller": "DeptController",
        "method": "getDeptById",
        "deptId": $rootScope.token.user.dept.id
    }, $q).then(function (success) {
        $scope.deptData = StrParesJSON(success).result;
    }, function (err) {
    });

    // 登记注册机关信息
    sendPost($http, {
        "controller": "DeptController",
        "method": "queryDsDeptApply",
        "deptId": $rootScope.token.user.dept.id
    }, $q).then(function (success) {
        $scope.applyList = StrParesJSON(success).result;
    });

    // 股权信息
    sendPost($http, {
        "controller": "DeptController",
        "method": "queryDsDeptStock",
        "deptId": $rootScope.token.user.dept.id
    }, $q).then(function (success) {
        $scope.stockList = StrParesJSON(success).result;
    });


    // 表单提交
    $scope.submitDeptForm = function() {
        if (true) {

            var editDeptData = $scope.deptData;
            if ($scope.parentid != null) {
                editDeptData.parent = {
                    "id" : $scope.parentid
                };
            }
            var deptFromParams = mergeJson('dept', editDeptData);
            var reqDevData = mergeReauestData('DeptController',
                'updateSysDept', deptFromParams);
            var editResqResult = sendPost($http, reqDevData, $q);
            editResqResult.then(function() {},function() {})
        }
    }

    $scope.assignedDept = $rootScope.token.user.dept;
    /*
     * 维护注册登记机关
     */
    $scope.assignOfficeApply = function() {
        var modalInstance = $modal({
            scope : $scope,
            templateUrl : 'packages/sys/views/dept/office-apply/main.html',
            show : false,
            controller : 'DeptApplyCtrl',
            backdrop : "static"
        });
        modalInstance.$promise.then(modalInstance.show);
    };

    /*
     * 维护注册登记机关
     */
    $scope.assignStock = function() {
        var modalInstance = $modal({
            scope : $scope,
            templateUrl : 'packages/sys/views/dept/stock/main.html',
            show : false,
            controller : 'DeptStockCtrl',
            backdrop : "static"
        });
        modalInstance.$promise.then(modalInstance.show);
    };

    /*
     * 初始化必要字典以及关联表信息到画面中
     */
    // 字典 注册类型
    getDictList($http, $q, "DS_SUBREGISTERSTYLE").then(function (success) {
        $scope.orgSubregisterStyles = StrParesJSON(success).result;
    }, function (error) {
        console.info(error);
    });

    // 字典 单位规模
    getDictList($http, $q, "DS_ORGSIZE").then(function (success) {
        $scope.orgScales = StrParesJSON(success).result;
    }, function (error) {
        console.info(error);
    });

    // 字典 服务对象
    getDictList($http, $q, "DS_SERVICEOBJ").then(function (success) {
        $scope.orgServiceObjects = StrParesJSON(success).result;
    }, function (error) {
        console.info(error);
    });

    // 字典 调度方式
    getDictList($http, $q, "DS_DISPACHSTYLE").then(function (success) {
        $scope.orgDispachStyles = StrParesJSON(success).result;
    }, function (error) {
        console.info(error);
    });

    // 字典 能源利用类型1
    getDictList($http, $q, "DS_ENERGYUTILIZE1").then(function (success) {
        $scope.orgEnergyUtilize1s = StrParesJSON(success).result;
    }, function (error) {
        console.info(error);
    });

    // 字典 能源利用类型2
    getDictList($http, $q, "DS_ENERGYUTILIZE2").then(function (success) {
        $scope.orgEnergyUtilize2s = StrParesJSON(success).result;
    }, function (error) {
        console.info(error);
    });

    // 字典 城市
    $http.post("packages/sys/views/dept/city.json").success(
        function (response) {
            $scope.division = response;
            $scope.oriDivision = response;
        });


    /*
    日期控件
     */
    $scope.today = function() {
    };
    $scope.today();
    $scope.clear = function() {
        $scope.deptData.orgFoundDate = null;
    };
    $scope.disabled = function(date, mode) {
        return;
    };
    $scope.toggleMin = function() {
        $scope.minDate = new Date();
    };
    $scope.toggleMin();
    $scope.open = function($event) {
        $event.preventDefault();
        $event.stopPropagation();
        $scope.opened = true;
    };
    $scope.dateOptions = {
        formatYear : 'yy',
        startingDay : 1
    };
    $scope.format = 'yyyy-MM-dd';
}
