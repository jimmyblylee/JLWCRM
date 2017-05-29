/**
 * Created by Jimmybly Lee on 2017/5/29.
 */
function SalesModifyCtrl($scope, $http, $q) {
    $scope.submit = function() {
        var sales = $scope.sales;

        sendPost($http, {
            controller: "SalesController",
            method: $scope.method,
            "entity": JSON.stringify(sales)
        }, $q).then(function() {
            $("div[ng-controller='SalesListCtrl']").scope().load();
        })
    };

    $scope.uploadImage = function() {
        var file = $("input[type='file']", $("div[ng-controller='SalesModifyCtrl']"))[0].files[0];
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
                console.log(success);
                $scope.sales.img = success.result;
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
