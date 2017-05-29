
// Route State Load Spinner(used on page or content load)
MetronicApp.directive('ngSpinnerBar', ['$rootScope','$http','$q','$modal',
    function($rootScope,$http,$q,$modal) {
        return {
            link: function(scope, element, attrs) {

                // by defult hide the spinner bar
                element.addClass('hide'); // hide spinner bar by default

                // display the spinner bar whenever the route changes(the content part started loading)
                $rootScope.$on('$stateChangeStart', function() {
                	var userId  = getCookie("userId");
                	if(userId != null && userId != undefined){
                		 element.removeClass('hide'); // show spinner bar
                	}
                });

                // hide the spinner bar on rounte change success(after the content loaded)
                $rootScope.$on('$stateChangeSuccess', function() {
                	 element.addClass('hide'); // hide spinner bar
                     $('body').removeClass('page-on-load'); // remove page loading indicator
                     Layout.setSidebarMenuActiveLink('match'); // activate selected link in the sidebar menu
                     // auto scorll to page top
                     setTimeout(function () {
                         App.scrollTop(); // scroll to the top on content load
                     }, $rootScope.settings.layout.pageAutoScrollOnLoad);
                });

                // handle errors
                $rootScope.$on('$stateNotFound', function() {
                    element.addClass('hide'); // hide spinner bar

                });

                // handle errors
                $rootScope.$on('$stateChangeError', function() {
                    element.addClass('hide'); // hide spinner bar
                });
            }
        };
    }
])

// Handle global LINK click
MetronicApp.directive('a', function() {
    return {
        restrict: 'E',
        link: function(scope, elem, attrs) {
            if (attrs.ngClick || attrs.href === '' || attrs.href === '#') {
                elem.on('click', function(e) {
                    e.preventDefault(); // prevent link click for above criteria
                });
            }

        }
    };
});

// Handle Dropdown Hover Plugin Integration
MetronicApp.directive('dropdownMenuHover', function () {
  return {
    link: function (scope, elem) {
      elem.dropdownHover();
    }
  };
});

MetronicApp.directive('bsPopup', function ($parse) {
        return {
            require: 'ngModel',
            restrict: 'A',
            link: function (scope, elem, attrs, ctrl) {
                scope.$watch(function () {
                    return $parse(ctrl.$modelValue)(scope);
                }, function (newValue) {
                    if (newValue ==0) {
                        $(elem).modal('hide');
                        return;
                    }
                    if (newValue == 1) {
                        $(elem).modal('show');
                        return;
                    }
                });
            }
        }
 });

//监听浏览器高度的指令
MetronicApp.directive('resize', function ($window) {
    return function (scope, element) {
        var w = angular.element($window);
        scope.getWindowDimensions = function () {
            return { 'h': w.height(), 'w': w.width() };
        };
        scope.$watch(scope.getWindowDimensions, function (newValue, oldValue) {
            scope.windowHeight = newValue.h;
            scope.windowWidth = newValue.w;
            scope.style = function () {
                return {
                    'height': (newValue.h - 100) + 'px',
                    'width': (newValue.w - 100) + 'px'
                };
            };
            headeh=angular.element(".page-header").height();
            element.css('max-height',
                        (newValue.h - headeh) + 'px');
        }, true);

        w.bind('resize', function () {
            scope.$apply();
        });
    }
});


//当宽度小于992时弹框自动显示移动设备布局
MetronicApp.directive('modalsize', function ($window) {
    return function (scope, element) {
        var widowsObj = angular.element($window);
        scope.getwidowsObjDimensions = function () {
            return { 'h': widowsObj.height(), 'w': widowsObj.width() };
        };
        scope.$watch(scope.getwidowsObjDimensions, function (newValue, oldValue) {
            scope.modalSizeHeight = newValue.h;
            scope.modalSizeWidth = newValue.w;
            if (scope.modalSizeWidth < 992  &&  element.find('.tools').css("display")=="block" ) {
                element.find('.fullscreen').click();
                element.find('.tools').css({display: 'none'});
            }else if (scope.modalSizeWidth > 992 &&  element.find('.tools').css("display")=="none") {
                element.find('.fullscreen').click();
                element.find('.tools').css({display: 'block'});

            };
        }, true);
        widowsObj.bind('modalsize', function () {
            scope.$apply();
        });
    }
});

MetronicApp.directive("tablePagingFooter", [function() {
    return {
        restrict: "A",
        link: function() {
            return null;
        },
        templateUrl: "packages/index/tpl/table-paging-footer.html"
    }
}]);
