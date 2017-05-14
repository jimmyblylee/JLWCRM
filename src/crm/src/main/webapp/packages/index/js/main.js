var Pagenum = [5,10,15];
var itemsPerPage = 5;
var MetronicApp = angular.module("MetronicApp", [
    "ui.router",
    "ui.bootstrap",
    "oc.lazyLoad",
    "ngSanitize",
    "ng.ueditor",
    'mgcrea.ngStrap',   //弹框插件
    "pascalprecht.translate",  //国际化
    'chieffancypants.loadingBar',
    'cfp.loadingBar',
    'angular-drag'
]);
var storage = window.sessionStorage;
var storageLocal = window.localStorage;
function getCookielang(name)
{
var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
if(arr=document.cookie.match(reg))
return unescape(arr[2]);
else
return null;
}
langCookie=getCookielang("languageChoice")
if (langCookie == "" || langCookie == null || langCookie == undefined) {
  langCookie="zh-cn"
};
datepicker_i18n(langCookie);
MetronicApp.config(['$ocLazyLoadProvider', function($ocLazyLoadProvider) {
    $ocLazyLoadProvider.config({
    });
}]);

MetronicApp.config(['$controllerProvider', function($controllerProvider) {
  $controllerProvider.allowGlobals();
}]);
/*
 *添加http拦截
 */
MetronicApp.config(['$httpProvider',function($httpProvider){
   $httpProvider.interceptors.push('httpInterceptor');
   $httpProvider.defaults.headers.post = {
        'Content-Type': 'application/x-www-form-urlencoded'
   }
}]);

MetronicApp.config(['$translateProvider',function($translateProvider){
    $translateProvider.preferredLanguage('cn/dashboardCN');
    //本地资源文件路径
    $translateProvider.useStaticFilesLoader({
        prefix:'/jbp/packages/pages/i18n/',
        suffix:'.json'
    });
}]);
MetronicApp.factory('httpInterceptor', [ '$q', '$injector','$rootScope','$window','$timeout',function($q, $injector,$rootScope,$window,$timeout) {
	 var httpInterceptor = {
        'responseError' : function(response) {
        	var sign = 1;
            if(response.status == 404){
            	var rootScope = $injector.get('$rootScope');
            	console.info(rootScope);
                var state = $injector.get('$rootScope').$state.current.name;
                console.info(state);
                rootScope.stateBeforLogin = state;
                rootScope.$state.go("home");
                return $q.reject(response);
            }else if(response.status == 417){            	
            	return $q.reject(response);
            }else if (response.status == 401){
            	 $window.location = "./#/403.html";
            	 return $q.reject(response);
            }else if(response.status == 500){
                 $window.location.href = "./login.html";
                 return $q.reject(response);
            };
            return $q.reject(response);
        },
        'response' : function(response) {
            return response;
        },
        'request' : function(config) {
        	 //处理AJAX请求（否则后台IsAjaxRequest()始终false）
        	 config.headers['X-Requested-With'] = 'XMLHttpRequest';
        	 return config || $q.when(config);
        },
        'requestError' : function(config){
            return $q.reject(config);
        }
    }
    return httpInterceptor;
}]);

MetronicApp.factory('settings', ['$rootScope','$http','$q', function($rootScope,$http,$q) {  
    /**
     * 获取全局参数
     */
    $rootScope.getGlobalset = function(globalCode,globalVal) { 
      var loginGlobalParams = mergeJson('loginItem', globalCode);     
      var loginname = mergeReauestData('GlobalListController',
            'queryLoginList', loginGlobalParams); 
      var loginGlobalName = sendPost($http, loginname, $q);
      loginGlobalName.then(function(success){       
         var loginGlobalNameResponse = StrParesJSON(success);
         settings[globalVal] =loginGlobalNameResponse.result[0].variableDescribe;        
         if(globalVal=="globalColor"){
              settings[loginGlobalNameResponse.result[0].variableDescribe+'Selected'] = true;  
              angular.element('#laoutmin').after('<link href="resources/layouts/layout/css/themes/'+ settings[globalVal]+'.min.css" rel="stylesheet" type="text/css" id="style_color" />')
         };
          if(globalVal=="themeStyle"){             
              angular.element('#ng_load_plugins_before').after('<link href="resources/global/css/'+ settings.themeStyle+'.min.css" id="style_components" rel="stylesheet" type="text/css" />')
         };       
         if (globalVal=="setSidebarPostion") {    
              if (settings[globalVal]=='top') {
                $rootScope.getGlobalset(10003,'tplPath');                 
                $rootScope.settings.layout.pageFullWidth = true;
               }else if ( settings[globalVal]=='left') {
                    $rootScope.getGlobalset(10002,'tplPath');                    
                    $rootScope.settings.layout.pageFullWidth = false;
               }else if ( settings[globalVal]=='right') {
                    $rootScope.getGlobalset(10004,'tplPath');                    
                    $rootScope.settings.layout.pageFullWidth = false;
               }
         };
         if (globalVal=="tplPath") {             
              $rootScope.$broadcast('headerImg_task')
         };

      }, function(error) {
        console.info(error);
      });
    }    
   var settings = {
        layout: {           
            pageContentWhite: true,
            pageBodySolid: false,
            pageAutoScrollOnLoad: 1000,
            defaultSelected:false,
            pageFullWidth:false,           
        },
        assetsPath: 'resources',
        globalPath: 'resources/global',
        layoutPath: 'resources/layouts/layout',
        tplPath:'default',
        themeStyle:'components-rounded',
        pageboxed: "boxed",
        pageHeaderFixed:"fixed",
        pageSidebarFixed:"fixed",
        dropdownDark:"dark",
        pageSidebarClosed: "true"

    };
    $rootScope.getGlobalset(10123,'titlePath');
    $rootScope.getGlobalset(10000,'globalColor');
    $rootScope.getGlobalset(10001,'setSidebarPostion');
    $rootScope.getGlobalset(10011,'pageboxed');
    $rootScope.getGlobalset(10012,'pageHeaderFixed');
    $rootScope.getGlobalset(10013,'dropdownDark'); 
    $rootScope.getGlobalset(10014,'pageSidebarFixed');
    $rootScope.getGlobalset(10015,'pageSidebarClosed');
     
    $rootScope.settings = settings;
    return settings;
}]);


MetronicApp.controller('AppController', ['$scope', '$rootScope','$translate','$window','$state','$http','$q','$interval','$modal','cfpLoadingBar','$aside', function($scope, $rootScope,$translate,$window,$state,$http,$q,$interval,$modal,cfpLoadingBar,$aside) {
       $rootScope.sysTime = new Date();
     $rootScope.getSysTimedataFun=function(){
       getSysTimedata = mergeReauestData('GlobalListController',
            'getGlobalSysTime', ""); 
       getSysTime = sendPost($http, getSysTimedata, $q);
       getSysTime.then(function(success){             
         sysTime = StrParesJSON(success);
         $rootScope.sysTime =new Date(sysTime.result);                 
      }, function(error) {
        console.info(error);
      });  
    }
    $rootScope.getSysTimedataFun();
	//多语言支持
    $rootScope.switching=function(lang){
        setLanguage($translate,lang,"dashboard");
         setCookie('languageChoice',lang);
     };
    var lang=getCookie('languageChoice');
    if(lang!=null){
        setLanguage($translate,lang,"dashboard");
    }
 
    $scope.$on('$viewContentLoaded', function() { 
       $interval(function(){            
            Layout.initContent();
        },100,[10])
    });
    $rootScope.isHide = false;
    $window.onhashchange = function() {   
        var timeOut = angular.element('.modal').attr("id");
        if(!$rootScope.isHide){
        	angular.element('.modal').css({'display': 'none' })
        	angular.element('.modal-backdrop').remove();
        }
    };
    $scope.pageBar = function(pageBarUrl,pageBarChildItmeSub,pageBarChildItme,pageBarItme,pageBarParneItme){
         $scope.pageBarData={'pageBarParneItme':pageBarParneItme,'pageBarItme':pageBarItme,'pageBarChildItme':pageBarChildItme,'pageBarChildItmeSub':pageBarChildItmeSub,'pageBarUrl':pageBarUrl};
         storage.setItem("pageBarData",JSON.stringify($scope.pageBarData));
        
    }
   $scope.pageBarData = JSON.parse(storage.getItem("pageBarData"));
    $scope.tokenExtlogin = function(){
        timeoutTip.$promise.then(timeoutTip.hide);        
        $window.location.href = "./login.html";
    }

    //当路由改变时发生
    $rootScope.$on('$stateChangeStart',function(event, toState, toParams, fromState, fromParams){
    	/*     
         * 判断浏览器中是否存在cookie。
         *     1如果cookie不存在，证明你还没登陆过，直接去登录,
         *     2如果cookie存在，判读session是否过期，如果session过期，提示登录超时
         *     
         *     3.当路由发生改变了,从cookie中哪出这个东西，然后进行比较，看有没有这个权限
         */
        var userId  = getCookie("userId");
        var restoken = JSON.parse(storage.getItem("restoken"));
        if(userId != undefined && userId!=null){
            var token = mergeReauestData('getControllerSession','getSession');
            var tokenResult = sendPost($http,token,$q);
                       tokenResult.then(function(restokenset){
                       if (restokenset.indexOf("loginTimeOut")!=-1) {
                    	    $rootScope.isHide = true;
                            timeoutTip=$modal({
                                scope : $scope,
                                title : "提示",
                                templateUrl : 'packages/index/tpl/tip.html',
                                content : '会话超时，请重新登录',
                                show : true,
                                backdrop : "static"                                         
                            }); 
                            angular.element('.page-spinner-bar').addClass('hide');
                            angular.element('body').addClass('page-on-load'); // remove page loading indicator
                            event.preventDefault();
                            $state.go("home");
                        }else {
                           var code = toState.data.name;
                           var funcCode= getCookie("funcCode");
                           var funcCodeArray =funcCode.split(",");
                            if(funcCode.indexOf("account") == -1){
                                  $rootScope.accountIshideOrShow = false;
                            }else{
                                  $rootScope.accountIshideOrShow = true;
                            }

                            if(funcCode.indexOf("icon") == -1){
                                $rootScope.iconIshideOrShow = false;
                            }else{
                                $rootScope.iconIshideOrShow = true;
                            }
                           if(funcCode.indexOf(code) == -1 || ($rootScope.isHide &&code=="home")){
                        	    $rootScope.isHide = true;
                        	    angular.element('.page-spinner-bar').addClass('hide');
                                timeoutTip=$modal({
                                    scope : $scope,
                                    title : "提示",
                                    templateUrl : 'packages/index/tpl/tip.html',
                                    content : '权限不够,将为你跳转到登录页面',
                                    show : true,
                                    backdrop : "static"                                           
                                });
                                event.preventDefault();
                                $state.go("home");
                           }else{
                               /**
                                * 1.如果cookie存在，但是restoken不存在。那么如果想通过标签页直接访问，需要使用把登录时间也转变下！
                                */
                        	   var restoken = JSON.parse(storage.getItem("restoken"));
                               if((restoken == undefined || restoken == null || restoken == "") && (userId != undefined && userId!=null)){
                            	   //需要更新用户信息
                            	   registerTokenCookie(userId,new Date().getTime(),$window);
                               }else if(restoken != undefined && restoken != null &&  restoken.token.user.id != userId){
                            	   //不需要更新用户信息
                            	   registerTokenCookie(userId,"",$window);
                               }
                               
                                if(restoken!=undefined && restoken!=null && restoken!=""){
                                  var compareCode = restoken.token.func.allCloneSubFuncs;
                                  for(var i=0;i<compareCode.length;i++){
	                                   if(compareCode[i].code =="icon"){
	                                       $rootScope.iconName = compareCode[i].name;
	                                       $rootScope.iconIcon = compareCode[i].icon;
	                                    }
	                                    if(compareCode[i].code =="account"){
	                                      $rootScope.accountName = compareCode[i].name;
	                                      $rootScope.accountNameIcon = compareCode[i].icon;
	                                   }
                                  }  
                                }
                        	   var sysUserJson = mergeJson('userId',userId);
                               var sysUserData = mergeReauestData('UserController', 'getPersonInfo',sysUserJson);
                               var sysUserResult = sendPost($http,sysUserData, $q);
                               sysUserResult.then(function(success){
                                 success = JSON.parse(success);
                                 var sysUserPersonInfo = success.result;  
                                 //用户信息
                                 $rootScope.sysUser = sysUserPersonInfo[0];
                                 $scope.userName = $rootScope.sysUser.name;
                               }), function(error) {
                                 console.info(error);
                               };
                               /*
                                * 更新用户，相关信息（头像/名称等）
                                */
                               var getPhotoResult = pageService($http, $q, 'UserController',
                                       'getUserPhoto', null, userId, null);
                               getPhotoResult.then(function(success) {
                                   $scope.userPhotoData = StrParesJSON(success).result == null ? null
                                           : StrParesJSON(success).result;
                                   if($scope.userPhotoData != "" && $scope.userPhotoData!=null){
                                       $("#userIcon").attr("src", $scope.userPhotoData);
                                   }else{                        
                                       angular.element('#userIcon').remove();             
                                   }
                                });    
                           }
                        }                       
                  }); 
        }else{
        	angular.element('.page-spinner-bar').addClass('hide');
	      	  timeoutTip=$modal({
		          scope : $scope,
		          title : "提示",
		          templateUrl : 'packages/index/tpl/tip.html',
		          content : '会话超时，请重新登录',
		          show : true,
		          backdrop : "static"                                         
		      });
        	  event.preventDefault();
        }
       
        $rootScope.setThemeUserType = $rootScope.token.user.type.code;
        //判断是否是点击logo
         if(location.hash=="#/home.html"){
           sidebarmenu = angular.element('.page-sidebar-menu');
           sidebarmenu.find('.nav-item.open').removeClass('open');
           sidebarmenu.find(' a > .arrow.open').removeClass('open');
           sidebarmenu.find('.sub-menu').slideUp();
           sidebarmenu.find('li.active').removeClass('active');
           sidebarmenu.find('li > a > .selected').remove();
        }  
        //判断是否是个人资料和头像
        if(location.hash=="#/account.html" || location.hash=="#/edit_icon.html"){
           sidebarmenu = angular.element('.page-sidebar-menu');
           var j=0
           sidebarmenu.find('a').each(function(i) {
              userUrl=sidebarmenu.find('a').eq(i).attr("href")
               if (userUrl==location.hash) { 
                    if ( sidebarmenu.find('a').eq(i).parents('.sub-menu').css("display")=="none"){
                            sidebarmenu.find('a').eq(i).parents('.sub-menu').css({"display":""})
                    }
                    j=i
               };
            }); 
            if(j==0){
               sidebarmenu = angular.element('.page-sidebar-menu');
               sidebarmenu.find('.nav-item.open').removeClass('open');
               sidebarmenu.find(' a > .arrow.open').removeClass('open');
               sidebarmenu.find('.sub-menu').slideUp();
               sidebarmenu.find('li.active').removeClass('active');
               sidebarmenu.find('li > a > .selected').remove();

            }    
        }    
    });
      $rootScope.closeModel=$modal({
                scope : $scope,              
                templateUrl : 'packages/index/tpl/closePrompt.html',               
                show : false,
                backdrop : "static"
            });
  
        $scope.determine_close = function(flge){//点击确定放弃执行隐藏弹框
          $rootScope.closeModel.$promise.then($rootScope.closeModel.hide);
          $rootScope.$broadcast('hideModel',flge);        
         }

}]);

MetronicApp.controller('HeaderController', ['$scope','$http','$timeout', "$rootScope","$q", function($scope,$http,$timeout, $rootScope,$q) {
    $scope.$on('$includeContentLoaded', function() {
        Layout.initHeader();
    });

    //消息提醒的功能暂时不需要所以注释掉
	// $scope.messagePngShow = true;
 //    $scope.getMessage = function(getMessageUrl){
 //    	if(getMessageUrl != undefined && getMessageUrl != ""){
 //    	 	$http.get(getMessageUrl).success(function(res){
 //                $scope.messageCount = res.total;
 //                if($scope.messageCount == undefined || $scope.messageCount == "" || $scope.messageCount == 0 ){
 //            		$scope.messageCountShow = false;
 //            		$scope.messageGifShow = false;
 //            		$scope.messagePngShow = true;
 //            	}else{
 //            		$scope.messageCountShow = true;
 //            		$scope.messagePngShow = false;
 //            		$scope.messageGifShow = true;
 //            	}
 //        	});
 //    	}    	
 //    	$timeout(function(){
 //    		$scope.getMessage(getMessageUrl);
 //    	},60000)
    	
 //    }
    $scope.getMessageConfig = function(){
    	var getMessageUrl,clickUrl;
    	$.ajaxSettings.async = false;
        $.getJSON('packages/messageConfig.json', function(data) {
        	$.each(data, function(i, val) {
        		if(val != "" && val != undefined && val != null){
        			if(val.getMessageUrl != undefined && val.getMessageUrl != ""
        				&& val.clickUrl != undefined && val.clickUrl != ""){
        				getMessageUrl = val.getMessageUrl;
        				clickUrl = val.clickUrl;
        				$scope.clickUrl = clickUrl;
        			}else{
        				$scope.messagePngShow = false;
        			}
        		}
        	})
        });
       if(getMessageUrl != undefined && getMessageUrl != ""){
        	$scope.getMessage(getMessageUrl);
       }
    }
  

    $scope.$on('headerImg_task', function() {        
         var userId  = getCookie("userId");
         var getPhotoResult = pageService($http, $q, 'UserController',
                 'getUserPhoto', null, userId, null);
         getPhotoResult.then(function(success) {
             $scope.userPhotoData = StrParesJSON(success).result == null ? null
                     : StrParesJSON(success).result;
             if($scope.userPhotoData != "" && $scope.userPhotoData!=null){
                 $("#userIcon").attr("src", $scope.userPhotoData);
             }else{                        
                 angular.element('#userIcon').remove();             
             }
          });   
      }) 
}]);

MetronicApp.controller('SidebarController', ['$scope','$http','$rootScope','$location','$q','$window','$modal','$interval',function($scope , $http,$rootScope,$location,$q,$window,$modal,$interval) {
    
    $scope.tokenExtlogin = function(){
             timeoutTip.$promise.then(timeoutTip.hide);        
             $window.location.href = "./login.html";
    }
     var restoken = JSON.parse(storage.getItem("restoken"));
     if (restoken==undefined || restoken== null || restoken==""){
          $scope.$on('$includeContentLoaded', function() {            
            setTimeout(function(){
                Layout.initSidebar();   
            }, 500)   
          });  
         var token = mergeReauestData('LoginController','registerOrGetCurrentToken');
         var tokenResult = sendPost($http,token,$q);
         tokenResult.then(function(res){
         var res = JSON.parse(res)  
         $rootScope.token=res.token;  
      }); 
     }else{ 
    	 
            var res = restoken;              
            $rootScope.token=res.token;  
            $scope.$on('$includeContentLoaded', function() { 
              setTimeout(function(){
                Layout.initSidebar(); 
                  }, 100)    
          });   
     }

    $.ajaxSettings.async = false;
    $.getJSON('packages/sidebarfile.json', function(data) {
    	var treeSource = {};
    	$.each(data, function(i, val) {
    		if(val != undefined && val != null && val != ""){
    			if(val.name != undefined && val.name != null && val.name !="" &&
    					val.url != undefined && val.url != null && val.url !="" ){
    				$.getJSON(val.url, function(childrenData) {
    					$.extend(treeSource, childrenData);
    					$scope.sidebarConfig = treeSource;
    				})
    			}
    		}
    	})
    });
    /*
     * 链接跳转
     */
    $scope.skip = function(url){
        	   var urlLength = url.length;
        	   if(url.substr(0,2) == "//"){
        		   $window.open("http://"+url,"_blank");
        	   }else if(url.substr(0,1) == "/" || url.substr(0,7) == "http://" || url.substr(0,8) == "https://"){
        		   $window.open(url,"_blank");
        	   }else if(url.substring(urlLength-3,urlLength) =="com" || url.substring(urlLength-2,urlLength) =="cn"){
        		   $window.open("http://"+url,"_blank");
             }else{
            	   $window.open(url,"_self");
            }
     } 
}]);



MetronicApp.controller('QuickSidebarController', ['$scope', function($scope) {
    $scope.$on('$includeContentLoaded', function() {
       setTimeout(function(){
            QuickSidebar.init();
        }, 2000)
    });
}]);

MetronicApp.controller('ThemePanelController', ['$scope','$http','$q','$rootScope', function($scope,$http,$q,$rootScope) {
    $scope.$on('$includeContentLoaded', function() {
        Demo.init();
    });
   
    $scope.themeData=[];
    $scope.globalData=[];
    $scope.globalData['setColorNewData']={};
    $scope.globalData['setSidebarPostion']={};
    $scope.globalData['SidebarPostionTop']={};
    $scope.globalData['SidebarPostionLeft']={};
    $scope.globalData['SidebarPostionRight']={};
    $scope.globalData['setThemeStyle']={};        
    $scope.getGlobalset = function(globalCode,globalVal) { 
      var getGlobalParams = mergeJson('loginItem', globalCode);     
      var getGlobalData = mergeReauestData('GlobalListController',
            'queryLoginList', getGlobalParams); 
      var getGlobalResult = sendPost($http, getGlobalData, $q);
      getGlobalResult.then(function(success) {       
         var getGlobalResponse = StrParesJSON(success);
         $scope.globalData[globalVal] = getGlobalResponse.result[0]; 
         if (globalVal!="setColorNewData") {
            themeDatastr=globalVal.substring(3);
            $scope.themeData[themeDatastr] = $scope.globalData[globalVal].variableDescribe
         };
          
      }, function(error) {
        console.info(error);
      });
    }    
   $scope.getGlobalset(10000,'setColorNewData');
   $scope.getGlobalset(10001,'setSidebarPostion');
   $scope.getGlobalset(10003,'SidebarPostionTop');
   $scope.getGlobalset(10004,'SidebarPostionRight');
   $scope.getGlobalset(10002,'SidebarPostionLeft');
   $scope.getGlobalset(10010,'setThemeStyle');
   $scope.getGlobalset(10011,'setThemeLayout');
   $scope.getGlobalset(10012,'setThemeHeader');
   $scope.getGlobalset(10013,'setTopMenuDropDown'); 
   $scope.getGlobalset(10014,'setSidebarMode'); 
   $scope.getGlobalset(10015,'setPageSidebarClosed');   
    $scope.setGlobalTheme = function(globaldata) { 
      var setGlobalThemeParams = mergeJson('globalItme',globaldata);
          var setGlobalThemeData = mergeReauestData('GlobalListController',
                'updateGlobalList', setGlobalThemeParams);
          var setGlobalThemeResult = sendPost($http, setGlobalThemeData, $q);
          setGlobalThemeResult.then(function(success) {
          }, function(error) {
            console.info(error);
          });
    } 
    $scope.setTheme= function(newTheme,themeName) {
         if (newTheme == 'top') {
                postionDir =  $scope.globalData.SidebarPostionTop.variableDescribe
                $rootScope.settings.tplPath = postionDir;
                $rootScope.settings.layout.pageFullWidth = true;
           }else if (newTheme == 'left') {
                postionDir =  $scope.globalData.SidebarPostionLeft.variableDescribe
                $rootScope.settings.tplPath = postionDir;
                $rootScope.settings.layout.pageFullWidth = false;
           }else if (newTheme == 'right') {
                postionDir = $scope.globalData.SidebarPostionRight.variableDescribe
                $rootScope.settings.tplPath = postionDir;
                $rootScope.settings.layout.pageFullWidth = false;
           }
           if(themeName=="setSidebarMode" && newTheme == "fixed" &&  $rootScope.settings.pageHeaderFixed=='default'){
                    $scope.globalData["setThemeHeader"].variableDescribe = "fixed";                    
                    $scope.setGlobalTheme($scope.globalData["setThemeHeader"])
           }
            if(themeName=="setThemeHeader" && newTheme == "default" &&  $rootScope.settings.pageSidebarFixed=='fixed'){
                    $scope.globalData["setThemeHeader"].variableDescribe = "fixed";                    
                    $scope.setGlobalTheme($scope.globalData["setThemeHeader"])
           } 
          $scope.globalData[themeName].variableDescribe = newTheme;
          $scope.setGlobalTheme($scope.globalData[themeName])
          if (themeName=="setSidebarPostion") {
                  $rootScope.$broadcast('headerImg_task')

          };
          
    }  
    $scope.setThemeColor= function(newColor) {
          $scope.globalData.setColorNewData.variableDescribe= newColor;
          $scope.setGlobalTheme($scope.globalData.setColorNewData)
    }
}]);

MetronicApp.controller('FooterController', ['$scope', function($scope) {
    $scope.$on('$includeContentLoaded', function() {
        Layout.initFooter();
    });
}]);


MetronicApp.config(['$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider) {
	 var userId  = getCookie("userId");
	 
	 if(userId!=undefined && userId !=null){
	    //获取路由的配置文件2016
	        $stateProvider
	            .state("home", {
	                url:"/home.html",
	                templateUrl: "packages/index/views/home.html",
	                data: {pageTitle: "首页",name:"home"}
	    })
	    $.ajaxSettings.async = false;
	        
	    var restoken = JSON.parse(storage.getItem("restoken"));
	    /**
	     * 1.如果cookie中存在值，但是session回话中没有存在缓存的值。
	     *    这种场景是当用通过tab页面直接访问页面信息的时候，需要进行login时间的更换
	     * 2.如果session中存在token的值，但是用户的cookie值发生了改变，需要用第二个登录用户的token值代替第一个用户token值
	     *    这种场景用来针对一个浏览器多个用于登录   
	     */ 
	    
	    function configState(func){
	        $.getJSON('packages/sidebarfile.json', function(data){
	            $.each(data, function(i, val){
	                if(val != undefined && val != null && val != ""){
	                    if(val.name != undefined && val.name != null && val.name !="" &&
	                            val.url != undefined && val.url != null && val.url !="" ){
	                         $.getJSON(val.url, function(data) {
	                            $.each(data, function(children, childrenVal) {
	                                //如果拿到这个children  =url
	                                 $.each(func,function(i,val){
	                                     if(val.code == children || children == "root" || children == "sys"){
	                                        if(childrenVal.name!=undefined && childrenVal.name!=null && childrenVal.name!=""){
	                                            if(childrenVal.files!=undefined && childrenVal.files!=null && childrenVal.files!=""){
	                                                $stateProvider.state(childrenVal.name, {
	                                                        url:"/"+childrenVal.url,
	                                                        templateUrl: childrenVal.templateUrl,
	                                                        data: {pageTitle: childrenVal.pageTitle,name:children},
	                                                        resolve: {
	                                                            deps: ['$ocLazyLoad', function($ocLazyLoad){
	                                                                return $ocLazyLoad.load({
	                                                                    files:
	                                                                        childrenVal.files,
                                                                          cache: true
	                                                                });
	                                                            }]
	                                                        }
	                                                });
	                                            }else{
	                                                $stateProvider.state(childrenVal.name, {
	                                                    url:"/"+childrenVal.url,
	                                                    templateUrl: childrenVal.templateUrl,
	                                                    data: {pageTitle: childrenVal.pageTitle,name:children}
	                                                });
	                                            }
	                                          }
	                                     }
	                                 })
	                            })
	                         })
	                    }
	                }
	            })
	         }) 
	         
	         $urlRouterProvider.otherwise("/home.html");
	    }
	    }else{
	    	 window.location.href = "./login.html";
	    }

       if((restoken == undefined || restoken == null || restoken == "") && (userId != undefined && userId!=null)){
           $.post("mvc/dispatch",{controller:"LoginController",method:"registerTokenCookie",userId:userId,dateTime:new Date().getTime()},
                     function(data) {
             storage.setItem('restoken',JSON.stringify(data));
             storageLocal.setItem('loginTime',data.logintime);
               configState(data.token.func.allCloneSubFuncs);
           })
        }else if(restoken != undefined && restoken != null &&  restoken.token.user.id != userId){
           //不需要更新用户信息
           $.post("mvc/dispatch",{controller:"LoginController",method:"registerTokenCookie",userId:userId,dateTime:""},
                     function(data) {
             storage.setItem('restoken',JSON.stringify(data));
               configState(data.token.func.allCloneSubFuncs);
           })
        }else{
           var res = restoken;              
           configState(res.token.func.allCloneSubFuncs);
        }

}]);

MetronicApp.run(["$rootScope", "settings", "$state", function($rootScope, settings, $state) {
    $rootScope.$state = $state;
    $rootScope.$settings = settings;  
   
}]);
MetronicApp.controller('exitCtrl',['$scope','$http','$q','$window','$modal','$state','$location', function($scope,$http,$q,$window,$modal,$state,$location) {	
	// 点击删除按钮触发事件
	$scope.exitSysModal = function() {
		promptRxit.$promise.then(promptRxit.show);
	};
	var promptRxit = $modal({
		scope : $scope,
		templateUrl : 'packages/index/tpl/exit_modal.html',
		show : false,
		backdrop:"static"  
	});
	
	$scope.confirmExitSys = function(){
		//清楚相关用户信息
		var requestLogoutController= mergeReauestData('LoginController',
				'logout', "");
		var responseLogoutResult = sendPost($http, requestLogoutController,
				$q);
		responseLogoutResult.then(function(success) {             
			promptRxit.$promise.then(promptRxit.hide);
			$window.location.href = "./login.html";
		})
	}
	
}]);

//监听窗口变化的方法，用于解决当拖动过弹框后，再缩小窗口，恢复坐标
function tellAngular() {   
    var domElt = document.getElementsByClassName('t-modal-portlet')
    scope = angular.element(domElt).scope();
    attrStyle = angular.element(".t-modal-portlet .modal-content").attr("style")
    if (scope!=undefined && attrStyle!=undefined) {
      scope.$apply(function() {
             angular.element(".t-modal-portlet .t-modal-drags-fixed").removeAttr("style")
       });
    };    
}

//dom加载的时候调用 tellAngular
document.addEventListener("DOMContentLoaded", tellAngular, false);

//resize被触发是调用 tellAngular
window.onresize = tellAngular;


