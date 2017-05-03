/* ***************************************************************************
 * EZ.JWAF/EZ.JCWAP: Easy series Production.
 * Including JWAF(Java-based Web Application Framework)
 * and JCWAP(Java-based Customized Web Application Platform).
 * Copyright (C) 2016-2017 the original author or authors.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of MIT License as published by
 * the Free Software Foundation;
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the MIT License for more details.
 *
 * You should have received a copy of the MIT License along
 * with this library; if not, write to the Free Software Foundation.
 * ***************************************************************************/
angular.module("MetronicApp").controller("SelfDeptCtrl", function ($filter, $rootScope, $scope, $http, $q, $listService) {

    // 初始化当前企业信息
    $scope.refresh = function (deptId, isTemp) {
        sendPost($http, {
            "controller": isTemp ? "DeptApplyController" : "DeptController",
            "method": "getDeptById",
            "deptId": deptId
        }, $q).then(function (success) {
            var dept = StrParesJSON(success).result;
            if (dept.orgFoundDate) {
                dept.orgFoundDate = $filter('date')(new Date(dept.orgFoundDate), 'yyyy/MM/dd');
            }
            if (dept.childrenPlant == undefined) {
                dept.childrenPlant = [];
            }
            $scope.dept = dept;
            $scope.prepareAreaList(dept);
        }, function (err) {});

        // 股权信息
        sendPost($http, {
            "controller": "DeptController",
            "method": "queryDsDeptStock",
            "deptId": $rootScope.token.user.dept.id
        }, $q).then(function (success) {
            $scope.stockList = StrParesJSON(success).result;
            $scope.stockMaxId = 0;
            $.each($scope.stockList, function (idx, data) {
                $scope.stockMaxId = data.id > $scope.stockMaxId ? data.id : $scope.stockMaxId;
            });
        });
    };
    $scope.refresh($rootScope.token.user.dept.id);
    $scope.onLevelTypeChange = function(levelType) {
        $scope.dept.orgLevelType = levelType;
        if (levelType != 9) {
            $scope.dept.orgNonElectricType = {};
        }
    };

    $scope.addNullStock = function () {
        $scope.stockMaxId = $scope.stockMaxId + 1;
        $scope.stockList.push({
            "id": $scope.stockMaxId,
            "holder": "",
            "typeCode": "",
            "ratio": ""
        });
    };
    $scope.deleteOneStock = function (index) {
        $scope.stockList.splice(index, 1);
    };

    // 表单提交
    $scope.submitDeptForm = function () {
        var editDeptData = $scope.dept;
        editDeptData.orgCreateDate = $filter('date')(new Date(), 'yyyy/MM/dd');
        if ($scope.isNotValid(editDeptData)) {
            return;
        }
        var deptFromParams = {};
        mergeJson('dept', editDeptData);
        mergeJson('stocks', $scope.stockList);
        $.extend(deptFromParams, mergeJson('dept', editDeptData), mergeJson('stocks', $scope.stockList), mergeJson('adminUser', {}));
        var reqDevData = mergeReauestData('DeptController', 'updateSysDept', deptFromParams);
        sendPost($http, reqDevData, $q).then(function (success) {
            App.alert({
                type: "success",
                message: "更新成功",
                placement: "append",
                icon: 'fa fa-info'
            });
        });
    };
    $scope.isNotValid = function (obj) {
        var isNullStr = function(val) {
            return val == undefined || val == "";
        };
        var valid = true, message = "";
        // 单位规范名称
        if (isNullStr(obj.name)) {
            valid = false;
            message += "单位规范名称，不能为空！";
            $('input[ng-model="dept.name"]').parent().parent().addClass('has-error');
        } else {
            $('input[ng-model="dept.name"]').parent().parent().removeClass('has-error');
        }
        // 简称
        if (isNullStr(obj.orgNameShort)) {
            valid = false;
            message += "单位简称，不能为空！";
            $('input[ng-model="dept.orgNameShort"]').parent().parent().addClass('has-error');
        } else {
            $('input[ng-model="dept.orgNameShort"]').parent().parent().removeClass('has-error');
        }
        // 单位所在地
        if (isNullStr(obj.orgAddScopeCode)) {
            valid = false;
            message += "请填写完整单位所在地及区划！";
            $('select[ng-model="dept.orgAddProvince.code"]').parent().parent().addClass('has-error');
            $('select[ng-model="dept.orgAddCity.code"]').parent().parent().addClass('has-error');
            $('select[ng-model="dept.orgAddCounty.code"]').parent().parent().addClass('has-error');
            $('select[ng-model="dept.orgAddTown.code"]').parent().parent().addClass('has-error');
            $('select[ng-model="dept.orgAddVillage.code"]').parent().parent().addClass('has-error');
        } else {
            $('select[ng-model="dept.orgAddProvince.code"]').parent().parent().removeClass('has-error');
            $('select[ng-model="dept.orgAddCity.code"]').parent().parent().removeClass('has-error');
            $('select[ng-model="dept.orgAddCounty.code"]').parent().parent().removeClass('has-error');
            $('select[ng-model="dept.orgAddTown.code"]').parent().parent().removeClass('has-error');
            $('select[ng-model="dept.orgAddVillage.code"]').parent().parent().removeClass('has-error');
        }
        // 单位注册地
        if (isNullStr(obj.orgRegScopeCode)) {
            valid = false;
            message += "请填写完整单位注册地及区划！";
            $('select[ng-model="dept.orgRegProvince.code"]').parent().parent().addClass('has-error');
            $('select[ng-model="dept.orgRegCity.code"]').parent().parent().addClass('has-error');
            $('select[ng-model="dept.orgRegCounty.code"]').parent().parent().addClass('has-error');
            $('select[ng-model="dept.orgRegTown.code"]').parent().parent().addClass('has-error');
            $('select[ng-model="dept.orgRegVillage.code"]').parent().parent().addClass('has-error');
        } else {
            $('select[ng-model="dept.orgRegProvince.code"]').parent().parent().removeClass('has-error');
            $('select[ng-model="dept.orgRegCity.code"]').parent().parent().removeClass('has-error');
            $('select[ng-model="dept.orgRegCounty.code"]').parent().parent().removeClass('has-error');
            $('select[ng-model="dept.orgRegTown.code"]').parent().parent().removeClass('has-error');
            $('select[ng-model="dept.orgRegVillage.code"]').parent().parent().removeClass('has-error');
        }
        // 上级单位：资产口径
        if (isNullStr(obj.parentAsset)) {
            valid = false;
            message += "上级单位：（1）资产口径上级单位，不能为空！";
            $('input[ng-model="dept.parentAsset"]').parent().parent().addClass('has-error');
        } else {
            $('input[ng-model="dept.parentAsset"]').parent().parent().removeClass('has-error');
        }
        // 上级单位：管理口径
        if (isNullStr(obj.parentMgmt)) {
            valid = false;
            message += "上级单位：（2）区域管理上级单位，不能为空！";
            $('input[ng-model="dept.parentMgmt"]').parent().parent().addClass('has-error');
        } else {
            $('input[ng-model="dept.parentMgmt"]').parent().parent().removeClass('has-error');
        }
        // 是否并表企业
        if (isNullStr(obj.isConsolidated)) {
            valid = false;
            message += "是否集团公司并表企业，不能为空！";
            $('input[ng-model="dept.isConsolidated"]').parent().parent().parent().addClass('has-error');
        } else {
            $('input[ng-model="dept.isConsolidated"]').parent().parent().parent().removeClass('has-error');
        }
        // 能源类型
        if (obj.orgLevelType != 9) {
            if (!obj.orgEnergyUtilize1) {
                valid = false;
                message += "能源类型1，不能为空！";
                $('select[ng-model="dept.orgEnergyUtilize1.code"]').parent().parent().parent().addClass('has-error');
            } else {
                $('select[ng-model="dept.orgEnergyUtilize1.code"]').parent().parent().parent().removeClass('has-error');
            }
            if ((!obj.orgEnergyUtilize2 || !obj.orgEnergyUtilize2.code) && $scope.orgEnergyUtilize2List.length > 0) {
                valid = false;
                message += "能源类型2，不能为空！";
                $('select[ng-model="dept.orgEnergyUtilize2.code"]').parent().parent().parent().addClass('has-error');
            } else {
                $('select[ng-model="dept.orgEnergyUtilize2.code"]').parent().parent().parent().removeClass('has-error');
            }
        }
        // 法人
        if (isNullStr(obj.orgLegalPerson)) {
            valid = false;
            message += "法定代表人(单位负责人)，不能为空！";
            $('input[ng-model="dept.orgLegalPerson"]').parent().parent().addClass('has-error');
        } else {
            $('input[ng-model="dept.orgLegalPerson"]').parent().parent().removeClass('has-error');
        }
        // 单位负责人
        if (isNullStr(obj.orgPrincipal)) {
            valid = false;
            message += "单位负责人，不能为空！";
            $('input[ng-model="dept.orgPrincipal"]').parent().addClass('has-error');
        } else {
            $('input[ng-model="dept.orgPrincipal"]').parent().removeClass('has-error');
        }
        // 统计负责人
        if (isNullStr(obj.orgStatisticsPrincipal)) {
            valid = false;
            message += "统计负责人，不能为空！";
            $('input[ng-model="dept.orgStatisticsPrincipal"]').parent().addClass('has-error');
        } else {
            $('input[ng-model="dept.orgStatisticsPrincipal"]').parent().removeClass('has-error');
        }
        // 填表人
        if (isNullStr(obj.orgCreator)) {
            valid = false;
            message += "填表人，不能为空！";
            $('input[ng-model="dept.orgCreator"]').parent().addClass('has-error');
        } else {
            $('input[ng-model="dept.orgCreator"]').parent().removeClass('has-error');
        }
        // 填表人联系电话
        if (isNullStr(obj.orgCreatorTel)) {
            valid = false;
            message += "联系电话，不能为空！";
            $('input[ng-model="dept.orgCreatorTel"]').parent().addClass('has-error');
        } else {
            $('input[ng-model="dept.orgCreatorTel"]').parent().removeClass('has-error');
        }
        // 联系手机
        if (isNullStr(obj.orgCreatorMobile)) {
            valid = false;
            message += "联系手机，不能为空！";
            $('input[ng-model="dept.orgCreatorMobile"]').parent().addClass('has-error');
        } else {
            $('input[ng-model="dept.orgCreatorMobile"]').parent().removeClass('has-error');
        }

        if (!valid) {
            App.alert({
                type: "danger",
                message: message,
                placement: "append",
                icon: 'fa fa-warning'
            });
        }
        return !valid;
    };

    // 为弹出的选择单位的窗口做准备
    $scope.condition = {};
    $listService.init($scope, {
        "controller": "DeptController",
        "method": "queryDeptByName",
        callback: function(success) {
            $scope.deptList = success.data.result;
        }
    });
    $scope.searchDept = function() {
        $scope.pageRequest.getResponse();
    };
    $scope.prepareToSelectDept = function(type) {
        $scope.deptSelectTyp = type;
    };
    $scope.selectDept = function(dept) {
        if ($scope.deptSelectTyp != "parentPlant") {
            $scope.dept[$scope.deptSelectTyp] = dept;
        } else {
            $scope.dept.childrenPlant.push(dept);
        }
    };
    $scope.deleteSelectedDept = function(dept) {
        var index;
        $.each($scope.dept.childrenPlant, function(idx, data){
            if (data.id == dept.id) {
                index = idx;
            }
        });
        $scope.dept.childrenPlant.splice(index, 1);
    };

    $scope.showApplyToEditDept = function(flag) {
        if (flag) {
            $('#applyToEditDept').removeClass("hide");
            $('#viewSelfDept').addClass('hide');
            $('#linkToApplyDeptBtn').addClass('hide');
            $('#submitApplyDeptBtn').removeClass('hide');
            $('#cancelApplyDeptBtn').removeClass('hide');
        } else {
            $('#applyToEditDept').addClass("hide");
            $('#viewSelfDept').removeClass('hide');
            $('#linkToApplyDeptBtn').removeClass('hide');
            $('#submitApplyDeptBtn').addClass('hide');
            $('#cancelApplyDeptBtn').addClass('hide');
        }
    };

    $scope.submitApplyDept = function() {
        var editDeptData = $scope.dept;
        editDeptData.orgCreateDate = $filter('date')(new Date(), 'yyyy/MM/dd');
        if ($scope.isNotValid(editDeptData)) {
            return;
        }
        var deptFromParams = {type: 'DEPT_UPDATE'};
        mergeJson('dept', editDeptData);
        mergeJson('stocks', $scope.stockList);
        $.extend(deptFromParams, mergeJson('dept', editDeptData), mergeJson('stocks', $scope.stockList), mergeJson('adminUser', {}));
        var reqDevData = mergeReauestData('DeptApplyController', 'applyDept', deptFromParams);
        sendPost($http, reqDevData, $q).then(function (success) {
            App.alert({
                type: "success",
                message: "申请成功",
                placement: "append",
                icon: 'fa fa-info'
            });
        });
    };

    /*
     * 初始化必要字典以及关联表信息到画面中
     */
    // 字典 注册类型
    getDictList($http, $q, "DS_NON_ELECTRIC_TYPE").then(function (success) {
        $scope.orgNonElectricTypeList = StrParesJSON(success).result;
    }, function (error) {});
    // 字典 注册类型
    getDictList($http, $q, "DS_BIZ_CODE").then(function (success) {
        $scope.orgMainBizCodeList = StrParesJSON(success).result;
    }, function (error) {});

    var regions = {"1": "Province", "2": "City", "3": "County", "4": "Town", "5": "Village",
        "Province" : "1", "City": "2", "County": "3", "Town": "4","Village": "5"};
    // 字典 地址相关
    $.each(regions, function(key, data) {
        if (parseInt(key)) {
            $scope['org' + 'Add' + data + 'List'] = [];
            $scope['org' + 'Reg' + data + 'List'] = [];
        }
    });
    $scope.prepareAreaList = function(dept) {
        $.each(regions, function(key, data) {
            if (parseInt(key)) {
                $.each(['Add', 'Reg'], function(idx, type) {
                    if (dept['org' + type + data]) {
                        getSelectValueByDictList($http, $q, 'AREA_CODE', dept['org' + type + data]['code']).then(function (success) {
                            $scope['org' + type + regions[(parseInt(regions[data]) + 1) + ""] + 'List'] = StrParesJSON(success).result;
                        }, function (error) {});
                    }
                });
            }
        });
    };
    getSelectValueByDictList($http, $q, 'AREA_CODE', 'NATURE').then(function (success) {
        $scope.orgAddProvinceList = StrParesJSON(success).result;
        $scope.orgRegProvinceList = StrParesJSON(success).result;
    }, function (error) {});
    $scope.onAreaChange = function(type, region) {
        var temp = region;
        // 删除接下来的数据，准备重新初始化
        while (temp != "Village") {
            // 删除下一个列表以及值
            temp = regions[(parseInt(regions[temp]) + 1) + ""];
            $scope['org' + type + temp + "List"] = [];
            $scope.dept['org' + type + temp] = {};
            $scope.dept['org' + type + 'ScopeCode'] = "";
            $scope.dept['org' + type + 'VillageCode'] = "";
        }

        var nextRegion = regions[(parseInt(regions[region]) + 1) + ""];
        var currentCode = $scope.dept['org' + type + region]['code'];
        var currentList = $scope['org' + type + region + 'List'];
        var currentDict = {};
        $.each(currentList, function(idx, data) {
            if (data['code'] == currentCode) {
                currentDict = data;
            }
        });

        // 如果下一个列表有值则初始化下一个列表
        if (currentDict['hasChildren']) {
            getSelectValueByDictList($http, $q, 'AREA_CODE', currentCode).then(function (success) {
                $scope['org' + type + nextRegion + 'List'] = StrParesJSON(success).result;
            }, function (error) {});
        } else {
            $scope.dept['org' + type + 'ScopeCode'] = currentCode;
            if (region == "Village") {
                $scope.dept['org' + type + 'VillageCode'] = currentDict['remarks'];
            }
        }
    };
    // 字典 服务对象
    getSelectValueByDictList($http, $q, 'DS_SERVICEOBJ', '-1').then(function (success) {
        $scope.orgServiceObjectList = StrParesJSON(success).result;
    }, function (error) {});
    // 字典 调度方式
    getSelectValueByDictList($http, $q, 'DS_DISPACHSTYLE', '-1').then(function (success) {
        $scope.orgDispachStyleList = StrParesJSON(success).result;
    }, function (error) {});
    // 字典 接入电网
    getSelectValueByDictList($http, $q, 'DS_INJECT', '-1').then(function (success) {
        $scope.orgInjectList = StrParesJSON(success).result;
    }, function (error) {});
    // 字典 能源利用类型1
    getSelectValueByDictList($http, $q, 'DS_ENERGYUTILIZE1', '-1').then(function (success) {
        $scope.orgEnergyUtilize1List = StrParesJSON(success).result;
        $scope.orgEnergyUtilize2List = [];
    }, function (error) {});
    $scope.onEnergy1Change = function() {
        $scope.dept.orgEnergyUtilize2 = {};
        getSelectValueByDictList($http, $q, 'DS_ENERGYUTILIZE1', $scope.dept.orgEnergyUtilize1.code).then(function (success) {
            $scope.orgEnergyUtilize2List = StrParesJSON(success).result;
        }, function (error) {});
        if ($scope.dept.orgEnergyUtilize1.code != 1) {
            $scope.dept.orgReservoirType = {};
            $scope.dept.basin = "";
        }
    };
    // 水库类型
    getSelectValueByDictList($http, $q, 'DS_RESERVOIR_TYPE', '-1').then(function (success) {
        $scope.orgReservoirTypeList = StrParesJSON(success).result;
    }, function (error) {});

    // 字典 注册类型
    getDictList($http, $q, "DS_SUBREGISTERSTYLE").then(function (success) {
        $scope.orgSubregisterStyleList = StrParesJSON(success).result;
    }, function (error) {
        console.info(error);
    });
    // 获取经济类型
    getDictList($http, $q, "DS_ECONOMYSTYLE").then(function (success) {
        $scope.economyStyleList = StrParesJSON(success).result;
    }, function (error) {});

});
