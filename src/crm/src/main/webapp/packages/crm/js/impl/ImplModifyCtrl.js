/**
 * Created by Jimmybly Lee on 2017/5/29.
 */
function ImplModifyCtrl($scope, $http, $q) {
    getSelectValueByDictList($http, $q, "IMPL_ROLE", "NATURE").then(function(success) {
        $scope.roleList = StrParesJSON(success).result;
    });
    $scope.submit = function() {
        var impl = $scope.impl;

        sendPost($http, {
            controller: "ImplController",
            method: $scope.method,
            "entity": JSON.stringify(impl)
        }, $q).then(function() {
            $("div[ng-controller='ImplListCtrl']").scope().load();
        })
    };

    $scope.uploadImage = function() {
        var file = $("input[type='file']", $("div[ng-controller='ImplModifyCtrl']"))[0].files[0];
        if (file) {
            var fd = new FormData();
            fd.append("salesImage", file);
            $http({
                method: "POST",
                url: "mvc/dispatch?controller=ImageController&method=convertFileToBase64",
                data: fd,
                headers: {
                    'Content-Type' : undefined
                },
                transformRequest : angular.identity
            }).success(function(success) {
                $scope.impl.img = success.result;
            }).error(function(error){
                console.log(error);
                bootbox.dialog({
                    message: "请求失败，请联系管理员。",
                    title: "提示",
                    buttons: {
                        main: {
                            label: " 确 定 ",
                            className: "btn-outline dark"
                        }
                    }
                });
            });
        }
    };
}
