/**
 * Created by Jimmybly Lee on 2017/5/29.
 */
function SalesListCtrl($scope, $http, $q, $listService) {

    $scope.condition = {};

    $listService.init($scope, {
        "controller": "SalesController",
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
        var modalScope = $("div[ng-controller='SalesModifyCtrl']").scope();
        modalScope.sales = {
            img: "iVBORw0KGgoAAAANSUhEUgAAAC0AAAAtCAIAAAC1eHXNAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAxBpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMy1jMDExIDY2LjE0NTY2MSwgMjAxMi8wMi8wNi0xNDo1NjoyNyAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wTU09Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9tbS8iIHhtbG5zOnN0UmVmPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvc1R5cGUvUmVzb3VyY2VSZWYjIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6MTc3OTZCQTEzQkMzMTFFMjgwNzY4QjU3RDAzQUNEQzYiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6MTc3OTZCQTAzQkMzMTFFMjgwNzY4QjU3RDAzQUNEQzYiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNiBXaW5kb3dzIj4gPHhtcE1NOkRlcml2ZWRGcm9tIHN0UmVmOmluc3RhbmNlSUQ9IjMzQjFCRDAyNDI0MDkyMzY0NUEwQkVDOEY0NjZCN0MwIiBzdFJlZjpkb2N1bWVudElEPSIzM0IxQkQwMjQyNDA5MjM2NDVBMEJFQzhGNDY2QjdDMCIvPiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8L3g6eG1wbWV0YT4gPD94cGFja2V0IGVuZD0iciI/PnItSkUAAARmSURBVHjavFi5TiRBDJ0tmhsEQiCQQOJIkciISQlA4gP4Jv6DgAAyIsSVAAEJBCDEHXGIGxbYYd+OJa+xXdXTs9I6GE13V5dfPd+dzc3NlUqlHxXhP3xJd76+vuQdKfQUgv/8K/+w0J1yuawW029Ge8lfqU9isjgIhHxEl2qNfCQ3kYszdXRXq2JIbkQ3rQ6lzKqXL0ICk+ZyHkKImUNdumwpQKQIe/IlnzlzN1KWKkXEmsNagZUpO6rDZ8oxrZPK/4oAeSal3j61FMoDZLRU2SwWHe4Waa+UcZFwmqDItwjkERVhvyqCBaEiCei58tcu0sbpIKqrq3t5eXl/f29ubsblz4rgUVtbG9AAWY04pC10OH0/JV0+PDx0dHRMTEz09vZi5ePj483NzdnZ2cnJCda0trYiWSl2pfV9HNVD/hNdWXZ/f48TT09Pd3V10f2enp6RkZHx8fHT09Pl5eXb21s8+vj4KMRHKLT69fUVlMzMzEAThQMfHTI4ODg7O9vX13d3dwfEaQI0DhUpnP8VpSRwCxx9eHiYF1BSYmlpaZmcnCSnSVOrgqgqPrhWASWbIybgA8SAOflivp9WyRuBQKR0d3fnLu7s7Pz8/CzmH0yRNYcq6MABK1CsFhKVV6TpWUvmZjqXD7yGU8JFchUjktlvVAuS46e8SL0gL7E1cFxeXqZBIKqfn59hQT53LXHrvsl2we7IFukdsaa/vx/ZVtVYe8hvfirTeW7WA5SGhoY0Dqy5uroCGqlVmcYCCqqvlEuVdQAROo6Pj9PkXV9fHxwcwJ3ttm4FjeYPzmYsHEGoZLu7u/v7+xYEJVZ4xubmJsiAM1mUlgbGGr48sa5O4Orr65Ggtra2VIfAWA8PD/EUVZDj040XeyfE+hS3/Yf3tbe3Pz09gXz1FuFAZSEHsq6WThBBpZe0YBkogbKLiwu3XhwdHQFHuSKJ+mBbvszt4t0hRU1N1u/e3t6Q5QA0NlCpRCXBhdi6mItAN2opTOMiBg7VebjOYR8Fu84aiO/QoVHqhoaGLOimpqbR0VEbbjZeLPEhESmux4AMuOrAwIANdfyZmpoaGxtDm6iKpTvrSgmJcHJLLtpPOOnCwoI7DsEu6A5BjGrVci0V0q7gmhPZbGdnZ2lpSXouFdj19fXz83N0ZTRPyFLnzgMcJVliuI3VPKiEi6ysrEANelWOGjjv9vY2JTGbMdOFPcTSreKGREY/iurGxsbi4iIH8NraGkYKVBa5Mp0kdR7LTV92I7CCVnR1dRW2wE3ME8jocGGySKxgxQb34DbDMUBlIdCHHdE2gxKk+b29PUwuioxYtUr1H7ljsT0KoCA04Lbz8/P4j/HODpWxhOHgUPNtIYFi4KDGp7GxEZey4scyaYqP2BeSWA7g/2haqeshMtJ9V2p+UVDUp5U0Dlsg7efAanHYL272Q0/1DLmdZS3zXKH3/1F9Ld8datZRDIebr9ITYpXIYmH/v/moxs+yQuoTfPzLRzrIbwEGAPFYTpqUqkiNAAAAAElFTkSuQmCC"
        };
        modalScope.title = "增加销售人员";
        modalScope.method = "create";
    };

    $scope.prepareToUpdate = function(item) {
        var modalScope = $("div[ng-controller='SalesModifyCtrl']").scope();
        modalScope.sales = item;
        modalScope.title = "更新销售人员";
        modalScope.method = "update";
    };

    $scope.delete = function(item) {
        bootbox.dialog({
            message: "是否确认要删除" + item.name + "？",
            title: "请您确认",
            buttons: {
                success: {
                    label: " 删 除 ! ",
                    className: "red fa fa-ban",
                    callback: function() {
                        sendPost($http, {
                            "controller": "SalesController",
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
