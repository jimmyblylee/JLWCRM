/**
 * Created by JimmyblyLee on 2016/12/22.
 */
function ReportGroupCtrl($scope, $modal, $http, $q, $rootScope) {

    // 获取所有报表
    pageService($http, $q, "DeptController", "queryAllReport", 1, -1).then(function (success) {
        $scope.dsReportList = StrParesJSON(success).result;
    }, function () {
    });

    // 获取当前报表组
    $scope.refreshGroup = function () {
        sendPost($http, {
            "controller": "DeptController",
            "method": "queryReportGroup",
            "deptId": $rootScope.token.user.dept.id
        }, $q).then(function (success) {
            $scope.groupList = StrParesJSON(success).result;
        }, function () {
        });
    }
    $scope.refreshGroup();

    $scope.selectedGroupName = "请创建或选择报表组，然后分配权限。";
    // 函数，根据给定报表组获取对应的报表
    $scope.refreshReport = function (groupId, groupName) {
        $('.selected-group-header').removeClass('font-red-thunderbird');
        if (groupId != $scope.selectedGroupId) {
            $scope.selectedGroupName = "当前编辑工作组：" + groupName;
        }
        $scope.selectedGroupId = groupId;
        pageService($http, $q, "DeptController", "queryReportByOrg", 1, -1, groupId).then(function (success) {
            $scope.assignedReportList = StrParesJSON(success).result;
        }, function (error) {
            console.info(error);
        });
    }

    $scope.reportChange = function () {
        $scope.dsReportList.forEach(function (item) {
            if (item.name == $scope.assignReportName) {
                $scope.assignReportId = item.id;
            }
        });
    }
    $scope.addReport = function () {
        if ($scope.selectedGroupName == "请创建或选择报表组，然后分配权限。") {
            $('.selected-group-header').addClass('font-red-thunderbird');
            return;
        }
        sendPost($http, {
            "controller": "DeptController",
            "method": "insertReprotOrgRel",
            "reportId": $scope.assignReportId,
            "groupId": $scope.selectedGroupId,
            "orgId": $rootScope.token.user.dept.id
        }, $q).then(function (success) {
            $scope.refreshReport($scope.selectedGroupId, $scope.selectedGroupName);
            // 被分配的报表列表
            sendPost($http, {
                "controller": "ReportController",
                "method": "initDataByDeptIdAndReportId",
                "reportId": $scope.assignReportId,
                "deptId": $scope.selectedGroupId
            }, $q);
        },function(){});
    }

    $scope.deleteReport = function (reportId) {
        sendPost($http, {
            "controller": "DeptController",
            "method": "deleteReportOrgRel",
            "reportId": reportId,
            "orgId": $scope.selectedGroupId
        }, $q).then(function (success) {
            $scope.refreshReport($scope.selectedGroupId, $scope.selectedGroupName);
        }, function () {
        });
    }

    $scope.createGroup = function () {
        if ($scope.deptData == undefined || $scope.deptData.name == undefined || $scope.deptData.name == "") {
            $('.report-group-name-invalid').removeClass('hide');
            return;
        } else {
            $('.report-group-name-invalid').addClass('hide');
        }
        $scope.deptData.orgLevelType = 4;
        // 验证表单
        if (true) {
            var addDeptData = $scope.deptData;
            addDeptData.parent = {
                "id": $rootScope.token.user.dept.id
            };
            var deptFromParams = mergeJson('dept', addDeptData);
            var reqDevData = mergeReauestData('DeptController',
                'createSysDept', deptFromParams);
            var addResqResult = sendPost($http, reqDevData, $q);
            addResqResult.then(function (success) {
                $scope.refreshGroup();
            });
        }
    }
    $scope.deleteGroup = function(groupId) {
        var deleteDeptParams = mergeJson("groupId", groupId);
        var requestDeptControllerData = mergeReauestData('DeptController','deleteRelByGroupId', deleteDeptParams);
        var responseDeleteResult = sendPost($http, requestDeptControllerData,
            $q);
        responseDeleteResult.then(function() {
            $scope.refreshGroup();
        });
    }
    $scope.modifyGroup = function(group){
    	$scope.selected = $scope.deepCopy(group);
    	modifyGroupWin.$promise.then(modifyGroupWin.show);
    }
    $scope.hideModifyGroup = function(group){
    	modifyGroupWin.$promise.then(modifyGroupWin.hide);
    }
    
    var modifyGroupWin = $modal({
		scope : $scope,
		templateUrl : 'packages/sys/views/dept/my-dept/report_group_edit.html',
		show : false,
//		container : '#orgBasicSituationResultId',
		controller : 'ReportGroupCtrl',
		backdrop : "static"
	});
    $scope.saveGroupName = function(){
    	
    	var params = {
				'controller': 'DeptController',
				'method': 'updateGroupWithId',
				'groupName': $scope.selected.name,
				'groupId': $scope.selected.id
		};
		var modifyPost = sendPost($http, params, $q);
		modifyPost.then(
				function(success){
					success = JSON.parse(success).result;
					if(success){
						$scope.$parent.hideModifyGroup();
						$scope.$parent.refreshGroup();
					}else{
						$modal({
							scope : $scope,
							title : "提示",
							templateUrl : 'packages/sys/views/user/tip.html',
							content : '修改失败',
							show : true,
							backdrop : "static"
						});
					}
		
				});
    	
    }
    $scope.deepCopy = function(source){
		var result = {};
		for(var key in source){
			result[key] = source[key];
		}
		return result;
	}
}