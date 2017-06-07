/**
 * Created by Jimmybly Lee on 2017/5/29.
 */
function ContactModifyCtrl($scope, $http, $q) {

    $scope.submit = function() {
        var contact = $scope.contact;

        sendPost($http, {
            controller: "ContactController",
            method: $scope.method,
            "entity": JSON.stringify(contact)
        }, $q).then(function() {
            $("div[ng-controller='ContactListCtrl']").scope().load();
        })
    };

    $scope.uploadImage = function() {
        var file = $("input[type='file']", $("div[ng-controller='ContactModifyCtrl']"))[0].files[0];
        if (file) {
            var fd = new FormData();
            fd.append("contactImage", file);
            $http({
                method: "POST",
                url: "mvc/dispatch?controller=ImageController&method=convertContactFileToBase64",
                data: fd,
                headers: {
                    'Content-Type' : undefined
                },
                transformRequest : angular.identity
            }).success(function(success) {
                $scope.contact.img = success.result;
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
