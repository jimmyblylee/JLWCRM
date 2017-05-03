/**
 * 发送get请求
 *
 * @param request
 *            AngularJS自定义service $http
 * @param controller
 *            需要请求的***Controller
 * @param method
 *            该Controller中的方法
 * @param params
 *            参数
 * @return 返回当前服务的处理信息
 */
function sendGet(request, data, $q) {
    sendPost(request, data, $q);
}

/**
 * 发送POST请求
 *
 * @param request
 *            AngularJS自定义service $http
 * @param data
 *            表单数据
 * @param $q
 *          AngularJS自定义service 用于异步提交接受返回数据
 * @return 返回当前服务的处理信息
 */

function sendPost(request, data, $q) {
    var deferred = $q.defer();

    request({
        method : 'post',
        url : 'mvc/dispatch',
        data : data,
        headers : {
            'Content-Type' : 'application/x-www-form-urlencoded'
        },
        transformRequest : function(obj) {
            var str = [];
            for ( var p in obj) {
                str.push(encodeURIComponent(p) + "="
                        + encodeURIComponent(obj[p]));
            }
            
            return str.join("&");
        }
    }).success(function(req,status, headers, cfg) {
        deferred.resolve(ObjParesJSON(req));
    }).error(function(rep, status, headers, cfg) {
        deferred.reject(ObjParesJSON(rep));
    })
    return deferred.promise;
}


/**
 * @param key 用于后台获取请求参数值
 * @param data
 * @returns json数组
 */
function mergeList(key,data){
    var array = [];
    var flog = false;
    for(var i in data){
        var n = 0
        for (var j in data[i]){
            var temp = {};
            temp[i] = data[i][j];
            if(!flog){
                array.push(temp);
            }else{
                array[n++][i] = data[i][j];
            }
        }
        flog= true;
    }
    var params = [];
    for(var a in array){
        params.push(ObjParesJSON(array[a]));
    }
    var ListParams = "\""+key+"\":["+params+"]";
    return ListParams;
}

/**
 * @param key 用于后台获取请求参数值
 * @param value 该值必须是String 型的json
 * @returns {String}
 */
function mergeJson (key,value){
    //ObjParesJSON(value);
     return StrParesJSON("{\""+key+"\":"+ObjParesJSON(value)+"}");
}

function mergeStr (key,value){
    //ObjParesJSON(value);
     return StrParesJSON("{\""+key+"\":"+value+"}");
}


/**
 * 拼装请求数据data
 *
 * @param controller
 *            需要请求的***Controller
 * @param method
 *            该Controller中的方法
 * @param params 可为空 <br/>
 *            表单数据 参数
 * @returns {Array}
 */
function mergeReauestData(controller,method,params){
    var json = {};
    json["controller"] = controller;
    json["method"] = method;
    for(var key in params){
        json[key] = ObjParesJSON(params[key]);
    }
    return json;
}

function mergeReauestDataByJsonParams(controller,method,JsonParams){
    var json = {};
    json["controller"] = controller;
    json["method"] = method;
    for(var key in JsonParams){
        json[key] = JsonParams[key];
    }
    return json;
}

function mergeReauestDataByJsonParams(controller,method,JsonParams){
    var json = {};
    json["controller"] = controller;
    json["method"] = method;
    for(var key in JsonParams){
        json[key] = JsonParams[key];
    }
    return json;
}

/**
 * 用于分页查询时封装data
 *
 * @param controller
 * @param method
 * @param start 起始于
 * @param limit 查询数量
 * @param query 查询条件
 * @returns {Array}
 */
function mergePageData(controller,method,start,limit,pageQuery){
    var data = {};
    data["controller"] = controller;
    data["method"] = method;
    data["start"] = start;
    data["limit"] = limit;
    data["pageQuery"] = pageQuery;
    return data;
}


/**
 * 分页功能
 * @param $http
 * @param $q
 * @param controller
 * @param method
 * @param currentPage 当前页
 * @param itemsPerPage  每页显示条数
 * @param pageQuery 查询条件
 */
function pageService($http,$q,controller,method,currentPage,itemsPerPage,pageQuery){
    if(currentPage == undefined){
        currentPage = 1;
    }
    if(itemsPerPage == undefined){
        itemsPerPage = 5;
    }
    if(pageQuery == undefined){
        pageQuery = "";
    }
    var start = (currentPage-1)*itemsPerPage;
    var limit = itemsPerPage;
    var requestData = mergePageData(controller,method,start,limit,pageQuery);
    return sendPost($http,requestData,$q);
}


/**Object pares JSON
 * 会有浏览器兼容问题不支持ie9以下
 * @param jsonObj
 * @returns json
 */
function ObjParesJSON(jsonObj){
    return JSON.stringify(jsonObj);
}


/**
 * 把 string 型的JSON转换为 objJson
 * @param data jsonString
 * @returns json
 */
function StrParesJSON(jsonStr){
    return  JSON.parse(jsonStr);
}


/**
 * 排序
 */
function sort_global($scope,order,reverse,newSortingOrder){
	if(newSortingOrder == null){
		
		$('th a').each(function(){
	        // icon reset
	        $(this).removeClass().addClass('fa fa-sort');
	    });
		
	}else{
	    if (order == newSortingOrder){
	       $scope.reverse = !reverse;
	    }
	      $scope.order = newSortingOrder;
	    // icon setup
	    $('th a').each(function(){
	        // icon reset
	        $(this).removeClass().addClass('fa fa-sort');
	    });
	    if ($scope.reverse){
            if(newSortingOrder=="dept.name"){
                $('th.dept a').removeClass().addClass('fa fa-sort-down');
            }else{
               $('th.'+newSortingOrder+' a').removeClass().addClass('fa fa-sort-down'); 
            }
	       
	    }else{  
             if(newSortingOrder=="dept.name"){
                    $('th.dept a').removeClass().addClass('fa fa-sort-up');
                }else{
    	       $('th.'+newSortingOrder+' a').removeClass().addClass('fa fa-sort-up');
             }
	    }
	}
}
/**
 * Description：获取字典表信息---状态下拉框
 * @author name：yuruixin
 */
function getDictList($http,$q,nature) {
	var resultDict = pageService($http,$q,'UserController', 'queryDictsByNature',
			1, -1, nature);
	return resultDict;
};

/**
 * Description：获取字典表信息---菜单是否可见下拉框
 * @author name：yuruixin
 */
function getSelectValueByDictList($http,$q,nature,code){
	
	var finalMarge = {
			"nature" : nature,
			"code":code
	};
	var requestDictData = mergeReauestDataByJsonParams('DictController', 'getSelectDictInfo',
			finalMarge);
	
	var responseDictResult = sendPost($http, requestDictData, $q);
	
	return responseDictResult;
};


/**
 * Description：设置图表数据 *
 */
 function charts(chartsName,echartData){         
         chartsName.setOption(echartData);
 }

/**
 * Description：数据转换 *
 * arr 格式：[值,值]
 */

function dataConversion(arr) {
    var result = [], hash = {};
    for (var i = 0, elem; (elem = arr[i]) != null; i++) {
        if (!hash[elem]) {
            result.push(elem);
            hash[elem] = true;
        }
    }
    return result;

}
/**
 * Description：横向柱状图方法 *
 * chartsName ：显示图表的位置的id名称
 * echartData：请求来的json数据  例如[{key:value,{key:value}},{key:value}] *
 * statistical_objects：统计对象key  
 * statistical_data：统计数据key
 * statistical_categories：纵坐标显示的值key //如果横坐标值 为 统计对象 不用传递这个参数
 * IShide:是否显示图例  bool值   默认值为false 显示
 */
function echarts_bar_transverse(chartsName,echartData,statistical_objects, statistical_data ,statistical_categories,IShide){ 
            var statistical_objects_arr=[];  //定义图例数据
            var statistical_data_arr=[];   //统计数据
            var Statistical_categories_arr=[]; //定义x轴数据
            var series_arr=[]; 
            for(var i=0;i<echartData.length;i++){ 
                statistical_objects_arr.push(echartData[i][statistical_objects]); 
                
                if (statistical_categories==undefined || statistical_categories==null) {
                       statistical_data_arr.push(echartData[i][statistical_data]);
                       Statistical_categories_arr= statistical_objects_arr;
                }else{
                    Statistical_categories_arr.push(echartData[i][statistical_categories]);
                }
            }
            statistical_objects_arr=dataConversion(statistical_objects_arr);
            Statistical_categories_arr=dataConversion(Statistical_categories_arr); 

            if (statistical_categories==undefined || statistical_categories==null) {
                     var objJson = {};                           
                            objJson.type = "bar";
                            objJson.data=statistical_data_arr;  
                            series_arr.push(objJson); //存放的是对象
                }else{

                    for(var i=0;i<statistical_objects_arr.length;i++){              
                                    var objJson = {};
                                        objJson.name = statistical_objects_arr[i];
                                        objJson.type = "bar";
                                        objJson.data=[];                  
                                    for(var j=0;j<Statistical_categories_arr.length;j++){
                                        for(var z=0;z<echartData.length;z++){                        
                                            if (echartData[z][statistical_objects]==statistical_objects_arr[i] && echartData[z][statistical_categories]==Statistical_categories_arr[j]) {
                                                    objJson.data.push(echartData[z][statistical_data]); 
                                            };
                                        }
                                    }
                                   series_arr.push(objJson); //存放的是对象
                                   
                                }

                }            
       option = {                         
              tooltip : {
                  trigger: 'axis',
                  axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                      type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                  }
              },
              legend: {
                  x: "right", 
                  data:statistical_objects_arr,                
              },             
              yAxis: [
                  {
                      axisLabel:{
                           interval:0,
                         rotate:45,
                         margin:2                         
                     },
                      type : 'category',
                      data : Statistical_categories_arr
                  }
              ],
              grid: { // 控制图的大小，调整下面这些值就可以，
                     x: 40,
                     x2: 100,
                     y2: 150,// y2可以控制 X轴跟Zoom控件之间的间隔，避免以为倾斜后造成 label重叠到zoom上
                 },
              xAxis: [
                  {
                      type : 'value'
                  }
              ],
              series : series_arr
          };     
             if(IShide){
                option.legend.data = [];
            }                     
         chartsName.setOption(option);
 }
/**
 * Description：柱状图方法 *
 * chartsName ：显示图表的位置的id名称
 * echartData：请求来的json数据  例如[{key:value,{key:value}},{key:value}] *
 * statistical_objects：统计对象key  
 * statistical_data：统计数据key
 * statistical_categories：横坐标显示的值key //如果横坐标值 为 统计对象 不用传递这个参数
 * IShide:是否显示图例  bool值   默认值为false 显示
 */
function echarts_bar(chartsName,echartData,statistical_objects, statistical_data ,statistical_categories,IShide){ 
            var statistical_objects_arr=[];  //定义图例数据
            var statistical_data_arr=[];   //统计数据
            var Statistical_categories_arr=[]; //定义x轴数据
            var series_arr=[]; 
            for(var i=0;i<echartData.length;i++){ 
                statistical_objects_arr.push(echartData[i][statistical_objects]); 
                
                if (statistical_categories==undefined || statistical_categories==null) {
                       statistical_data_arr.push(echartData[i][statistical_data]);
                       Statistical_categories_arr= statistical_objects_arr;
                }else{
                    Statistical_categories_arr.push(echartData[i][statistical_categories]);
                }
            }
            statistical_objects_arr=dataConversion(statistical_objects_arr);
            Statistical_categories_arr=dataConversion(Statistical_categories_arr); 

            if (statistical_categories==undefined || statistical_categories==null) {
                     var objJson = {};                           
                            objJson.type = "bar";
                            objJson.data=statistical_data_arr;  
                            series_arr.push(objJson); //存放的是对象
                }else{

                    for(var i=0;i<statistical_objects_arr.length;i++){              
                                    var objJson = {};
                                        objJson.name = statistical_objects_arr[i];
                                        objJson.type = "bar";
                                        objJson.data=[];                  
                                    for(var j=0;j<Statistical_categories_arr.length;j++){
                                        for(var z=0;z<echartData.length;z++){                        
                                            if (echartData[z][statistical_objects]==statistical_objects_arr[i] && echartData[z][statistical_categories]==Statistical_categories_arr[j]) {
                                                    objJson.data.push(echartData[z][statistical_data]); 
                                            };
                                        }
                                    }
                                   series_arr.push(objJson); //存放的是对象
                                   
                                }

                }            
       option = {                         
              tooltip : {
                  trigger: 'axis',
                  axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                      type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                  }
              },
              legend: {
                  x: "right", 
                  data:statistical_objects_arr,                
              },             
              xAxis : [
                  {
                      axisLabel:{
                           interval:0,
                         rotate:45,
                         margin:2                         
                     },
                      type : 'category',
                      data : Statistical_categories_arr
                  }
              ],
              grid: { // 控制图的大小，调整下面这些值就可以，
                     x: 40,
                     x2: 100,
                     y2: 150,// y2可以控制 X轴跟Zoom控件之间的间隔，避免以为倾斜后造成 label重叠到zoom上
                 },
              yAxis : [
                  {
                      type : 'value'
                  }
              ],
              series : series_arr
          };    
             if(IShide){
                option.legend.data = [];
            }                      
         chartsName.setOption(option);
 }
 /**
 * Description：地图方法 *
 * chartsName ：显示图表的位置的id名称
 * echartData：请求来的json数据  例如[{key:value,{key:value}},{key:value}] *
 * statistical_objects：统计对象key  
 * statistical_data：统计数据key
 * statistical_region：地区名称key，
 * max：最大显示值，
 * min：最小显示值， *
 * IShide:是否显示分布对象  bool值   默认值为false 显示
 */
 function charts_map(chartsName,echartData,statistical_objects, statistical_data,statistical_region,max,min,IShide){ 
            var statistical_objects_arr=[];            
            var series_arr=[];
            for(var i=0;i<echartData.length;i++){ 
                statistical_objects_arr.push(echartData[i][statistical_objects]);               
            }
            statistical_objects_arr=dataConversion(statistical_objects_arr);
               
            for(var i=0;i<statistical_objects_arr.length;i++){              
                var objJson = {};
                    objJson.name = statistical_objects_arr[i];
                    objJson.type = "map";
                    objJson.mapType = "china";
                    objJson.roam = false;
                    objJson.label = {
                                normal: {
                                    show: true
                                },
                                emphasis: {
                                    show: true
                                }
                            };
                    objJson.data=[]; 
                    for(var z=0;z<echartData.length;z++){                        
                        if (echartData[z][statistical_objects]==statistical_objects_arr[i]) {
                                var subdata={}
                                subdata.name=echartData[z][statistical_region];
                                subdata.value=echartData[z][statistical_data];
                                objJson.data.push(subdata); 
                        };
                    }
               
               series_arr.push(objJson); //存放的是对象
               
            }                   
            option = {                  
                    tooltip: {
                        trigger: 'item'
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'left',
                        data:statistical_objects_arr
                    },
                    visualMap: {
                        min: min,
                        max: max,
                        left: 'right',
                        top: 'bottom',
                        text: ['高','低'],           // 文本，默认为数值文本
                        calculable: true
                    },                   
                    series: series_arr
                };
            if(series_arr.length == 0){
              var objJson = {};
                objJson.name = statistical_objects_arr[i];
                objJson.type = "map";
                objJson.mapType = "china";
                objJson.roam = false;
                objJson.label = {
                            normal: {
                                show: true
                            },
                            emphasis: {
                                show: true
                            }
                        };
                objJson.data=[]; 
                series_arr.push(objJson);
            }

            if(IShide){
                option.legend.data = [];
            }         
         chartsName.setOption(option);
 }
  /**
 * Description：饼图方法 *
 * chartsName ：显示图表的位置的id名称
 * echartData：请求来的json数据  例如[{key:value,{key:value}},{key:value}] *
 * statistical_objects：统计对象key  
 * statistical_data：统计数据key
 * statistical_region：地区名称key
 * IShide:是否显示图例  bool值   默认值为false 显示
 */
 function echarts_pie(chartsName,echartData,statistical_objects, statistical_data ,IShide){ 
            var statistical_objects_arr=[];            
            var series_arr=[];
            for(var i=0;i<echartData.length;i++){ 
                statistical_objects_arr.push(echartData[i][statistical_objects]); 
                var objJson = {};
                    objJson.name = echartData[i][statistical_objects];
                    objJson.value = echartData[i][ statistical_data];                    
                    series_arr.push(objJson); 
            }
           option = {               
                tooltip : {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                legend: {                  
                    orient: 'vertical',
                     left: 'left',
                    data:  statistical_objects_arr
                },
                series : [
                    {   
                        name:"详细信息:",                  
                        type: 'pie',                                       
                        data:series_arr,
                        itemStyle: {
                            emphasis: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        }
                    }
                ]
            };
               if(IShide){
                option.legend.data = [];
            }     

         chartsName.setOption(option);
 }
   /**
 * Description：空心饼图方法 *
 * chartsName ：显示图表的位置的id名称
 * echartData：请求来的json数据  例如[{key:value,{key:value}},{key:value}] *
 * statistical_objects：统计对象key  
 * statistical_data：统计数据key
 * statistical_region：地区名称key
 * IShide:是否显示图例  bool值   默认值为false 显示
 */
  function echarts_pie2(chartsName,echartData,statistical_objects, statistical_data ,IShide){ 
            var statistical_objects_arr=[];            
            var series_arr=[];
            for(var i=0;i<echartData.length;i++){ 
                statistical_objects_arr.push(echartData[i][statistical_objects]); 
                var objJson = {};
                    objJson.name = echartData[i][statistical_objects];
                    objJson.value = echartData[i][statistical_data];                    
                    series_arr.push(objJson); 
            }
           option = {               
                tooltip : {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                legend: {                  
                    orient: 'vertical',
                    left: 'left',
                    data:  statistical_objects_arr
                },
                series : [
                    {   
                        name:"详细信息:",                  
                        type: 'pie',                                       
                        data:series_arr,  
                        radius: ['50%', '70%'],                     
                        avoidLabelOverlap: false,
                        label: {
                            normal: {
                                show: false,
                                position: 'center'
                            },
                            emphasis: {
                                show: true,
                                textStyle: {
                                    fontSize: '30',
                                    fontWeight: 'bold'
                                }
                            }
                        },
                        labelLine: {
                            normal: {
                                show: false
                            }
                        },
                        itemStyle: {
                            emphasis: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        }
                    }
                ]
            };
        if(IShide){
                option.legend.data = [];
            }     
         chartsName.setOption(option);
 }
   /**
 * Description：折线图方法 *
  * chartsName ：显示图表的位置的id名称
 * echartData：请求来的json数据  例如[{key:value,{key:value}},{key:value}] *
 * statistical_objects：统计对象key  
 * statistical_data：统计数据key
 * statistical_categories：横坐标显示的值key //如果横坐标值 为 统计对象 不用传递这个参数
 * IShide:是否显示图例  bool值   默认值为false 显示
 */
 function echarts_line(chartsName,echartData,statistical_objects, statistical_data ,statistical_categories,IShide){ 
            var statistical_objects_arr=[];
            var statistical_data_arr=[];   
            var Statistical_categories_arr=[];
            var series_arr=[];
            for(var i=0;i<echartData.length;i++){ 
                statistical_objects_arr.push(echartData[i][statistical_objects]); 
                 if (statistical_categories==undefined || statistical_categories==null) {
                       statistical_data_arr.push(echartData[i][statistical_data]);
                       Statistical_categories_arr = statistical_objects_arr;
                }else{
                    Statistical_categories_arr.push(echartData[i][statistical_categories]);
                }
            }
            statistical_objects_arr=dataConversion(statistical_objects_arr);
            Statistical_categories_arr=dataConversion(Statistical_categories_arr); 
             if (statistical_categories==undefined || statistical_categories==null) {
                     var objJson = {};                           
                            objJson.type = "line";
                            objJson.data=statistical_data_arr;  
                            series_arr.push(objJson); //存放的是对象
                }else{
                    for(var i=0;i<statistical_objects_arr.length;i++){              
                            var objJson = {};
                                objJson.name = statistical_objects_arr[i];
                                objJson.type = "line";
                                objJson.data=[];                  
                            for(var j=0;j<Statistical_categories_arr.length;j++){
                                for(var z=0;z<echartData.length;z++){                        
                                    if (echartData[z][statistical_objects]==statistical_objects_arr[i] && echartData[z][statistical_categories]==Statistical_categories_arr[j]) {
                                            objJson.data.push(echartData[z][statistical_data]); 
                                    };
                                }
                            }
                           series_arr.push(objJson); //存放的是对象
                           
                        }

                }                 
           
       option = {                        
              tooltip : {
                  trigger: 'axis',
                  axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                      type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                  }
              },
              legend: {
                  x: "right", 
                  data:statistical_objects_arr,                
              },             
              xAxis : [
                  {
                      axisLabel:{
                           interval:0,
                         rotate:45,
                         margin:2                         
                     },
                      type : 'category',
                      data : Statistical_categories_arr
                  }
              ],
              grid: { // 控制图的大小，调整下面这些值就可以，
                     x: 40,
                     x2: 100,
                     y2: 150,// y2可以控制 X轴跟Zoom控件之间的间隔，避免以为倾斜后造成 label重叠到zoom上
                 },
              yAxis : [
                  {
                      type : 'value'
                  }
              ],
              series : series_arr
          };      
             if(IShide){
                option.legend.data = [];
            }                    
         chartsName.setOption(option);
 }



/**
 * Description：监听滚动条是否出现 *
 */
function scrollwatch($scope,$timeout){

  $scope.$watch(function(){       
        scrollTbodyHeight=angular.element(".table-both-scroll > tbody > tr").height();        
        return scrollTbodyHeight
     },function(scrollTbodyHeight){ 
       timeout = $timeout(function() {
          scrollTbodyMaxHeight=angular.element('.table-both-scroll>tbody').css('max-height'); 
          scrollTbodyMaxHeight=parseInt(scrollTbodyMaxHeight)
          if (scrollTbodyHeight > scrollTbodyMaxHeight) {           
               angular.element(".table-both-scroll>thead>tr>th").css({
                          'padding-right': '17px'
                       });
          }else{            
              angular.element(".table-both-scroll>thead>tr>th").css({
                          'padding-right': '0'
                       });
          };
   },0)    
  },false)
}

//验证邮件
function checkEmail(email){
	if(email != null && email != ""){
		var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
		if(!filter.test(email)) {
			return true; 
		}else{
			return false;
		}
	}else{
		return false;
	}
}
function changeEmail(email){
	if(email.length>11){
		return checkEmail(email);
	}else{
		return false;
	}
}
//验证电话号码
function checkPhone(phone){
	if(phone != null && phone != ""){
		var filter = /^0?1[3|4|5|7|8][0-9]\d{8}$/; 
		if(!filter.test(phone)) {
			return true; 
		}else{
			return false;
		}
	}else{
		return false;
	}
} 

function changePhone(phone){
	if(phone.length>11){
		return checkPhone(phone);
	}else{
		return false;
	}
}
//验证数字 --->并且只能是正整数和0
function checkNumber(number){
	if(number != null && number != ""){
		var  filter =new RegExp("^\\d+$");
		if(!filter.test(number)) {
			return true; 
		}else{
			return false;
		}
	}else{
		return false;
	}
}
//验证fax
function checkFax(fax){
	if(fax != null && fax != ""){
		var filter = /^(\d{3,4}-)?\d{7,8}$/;
		if(!filter.test(fax)) {
			return true; 
		}else{
			return false;
		}
	}else{
		return false;
	}
}
function changeFax(fax){
	if(fax.length>10){
		return checkFax(fax);
	}else{
		return false;
	}
}
//验证电话号码
function checkFaxTel(faxTel){
	if(faxTel != null && faxTel != ""){
		var filter = /^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/;
		if(!filter.test(faxTel)) {
			return true; 
		}else{
			return false;
		}
	}else{
		return false;
	}
}
function changeFaxTel(faxTel){
	if(faxTel.length>5){
		return checkFaxTel(faxTel);
	}else{
		return false;
	}
}
//验证url
function checkUrl(url){
	if(url != null && url != ""){
		var  re =new RegExp("[a-zA-z]+://[^\s]*");
		if(re.test(url)) {
			return false; 
		}else{
			return true;
		}
	}else{
		return false;
	}
}
//验证url
function changeUrl(url){
	if(url.length>10){
		return checkUrl(url);
	}else{
		return false;
	}
}
//从后台得到树的数据，并展示出来！
function getTreeAndView($scope, $http, $q,controller,method,params){
	$scope.treedata = "";
	var getTreeData = mergeReauestData(controller,method,params);
	var promiseResult = sendPost($http, getTreeData, $q);
	promiseResult.then(function(value) {
		$scope.expandedNodes = null;
		var respData = StrParesJSON(value);
		$scope.treedata = respData.result;
		$scope.expandedNodes = [$scope.treedata[0]];
		$scope.showSelected = function(sel) {
			$scope.selectedNode = sel;
		};
	}), function(error) {
		console.info(error);
	};
}
//原生js 移除、增加class属性的方法
function hasClass(obj, cls) {
	return obj.className.match(new RegExp('(\\s|^)' + cls + '(\\s|$)'));
}
function addClass(obj, cls) {
	if (!hasClass(obj, cls))
		obj.className += " " + cls;
}
function removeClass(obj, cls) {
	if (hasClass(obj, cls)) {
		var reg = new RegExp('(\\s|^)' + cls + '(\\s|$)');
		obj.className = obj.className.replace(reg, ' ');
	}
}
/**
 * 重新获取左边栏信息
 * @param userInfo
 * @param dataTime
 */
function registerTokenCookie(userId,dataTime,$window){
	   $.post("mvc/dispatch",{controller:"LoginController",method:"registerTokenCookie",userId:userId,dataTime:dataTime},
            function(data){
            /*
             * 更新下storage中的token
             */
            storage.setItem('restoken',JSON.stringify(data));
            if(dataTime !="" && dataTime != undefined && dataTime !=null){
            	 storageLocal.setItem('loginTime',data.loginTime);
            }
            $window.location.reload();
   })
}
/**
 * 页面中公共的时间插件
 * @param $scope
 */
function commonDatePlug($scope){
    //时间控件操作
	$scope.clear = function() {
		$scope.startdatevalue = null;
	};
	$scope.disabled = function(date, mode) {
		return;
	};
	/**
	 * 初始化时间插件
	 *   最大时间均为：当前天数
	 *   最小时间目前决是没有下限的。
	 */
	$scope.toggleMin = function() {
		$scope.leftminDate = "";
		$scope.rightminDate = "";
		$scope.leftmaxDate = new Date();
		$scope.rightmaxDate = new Date();
	};
	$scope.toggleMin();
	/**
	 * 点击左边的时间：
	 *   如果已经选择了右边的时间，左边最大的时间等于righttime
	 */
	$scope.openLeft = function($event) {
		$event.preventDefault();
		$event.stopPropagation();
		if($scope.endtime!=null && $scope.endtime!=undefined){
			$scope.leftmaxDate = $scope.endtime;
		}
		$scope.leftopened = true;
	};	
	/**
	 * 点击的时间：
	 *   如果已经选择了左边的时间，右边最小的时间等于righttime
	 */
	$scope.openRight = function($event){
		$event.preventDefault();
		$event.stopPropagation();
		
		if($scope.starttime !=null && $scope.starttime !=undefined){
			$scope.rightminDate = $scope.starttime;
		}
		
		$scope.rightopened = true;
	};
	$scope.dateOptions = {
		formatYear : 'yy',
		startingDay : 1
	};
	$scope.format = 'yyyy-MM-dd';	
}
/**
 * 忽略加载进度的请求方法
 *
 * @param request
 *            AngularJS自定义service $http
 * @param data
 *            表单数据
 * @param $q
 *          AngularJS自定义service 用于异步提交接受返回数据
 * @return 返回当前服务的处理信息
 */
function ignoreLoadingBarPost(request, data, $q) {
    var deferred = $q.defer();

    request({
        method : 'post',
        url : 'mvc/dispatch',
        data : data,
        headers : {
            'Content-Type' : 'application/x-www-form-urlencoded'
        },
        ignoreLoadingBar: true,
        transformRequest : function(obj) {
            var str = [];
            for ( var p in obj) {
                str.push(encodeURIComponent(p) + "="
                        + encodeURIComponent(obj[p]));
            }
            
            return str.join("&");
        }
    }).success(function(req,status, headers, cfg) {
        deferred.resolve(ObjParesJSON(req));
    }).error(function(rep, status, headers, cfg) {
        deferred.reject(ObjParesJSON(rep));
    })
    return deferred.promise;
  }

function IsPC() {
    var userAgentInfo = navigator.userAgent;
    var Agents = ["Android", "iPhone",
                "SymbianOS", "Windows Phone",
                "iPad", "iPod"];
    var flag = true;
    for (var v = 0; v < Agents.length; v++) {
        if (userAgentInfo.indexOf(Agents[v]) > 0) {
            flag = false;
            break;
        }
    }
    return flag;
}
//滚动条在Y轴上的滚动距离
function getScrollTop(){
	var scrollTop = 0, bodyScrollTop = 0, documentScrollTop = 0;
	if(document.body){
		bodyScrollTop = document.body.scrollTop;
	}
	if(document.documentElement){
		documentScrollTop = document.documentElement.scrollTop;
	}
	scrollTop = (bodyScrollTop - documentScrollTop > 0) ? bodyScrollTop : documentScrollTop;
	return scrollTop;
}
//文档的总高度
function getScrollHeight(){
	var scrollHeight = 0, bodyScrollHeight = 0, documentScrollHeight = 0;
	if(document.body){
		bodyScrollHeight = document.body.scrollHeight;
	}
	if(document.documentElement){
		documentScrollHeight = document.documentElement.scrollHeight;
	}
	scrollHeight = (bodyScrollHeight - documentScrollHeight > 0) ? bodyScrollHeight : documentScrollHeight;
	return scrollHeight;
}
//浏览器视口的高度
function getWindowHeight(){
	var windowHeight = 0;
	if(document.compatMode == "CSS1Compat"){
	windowHeight = document.documentElement.clientHeight;
	}else{
	windowHeight = document.body.clientHeight;
	}
	return windowHeight;
}
//判断滚动条是否滚动到了底部
function scrollbottom(){ 
      var flag = false;     
     if(getScrollTop() + getWindowHeight() == getScrollHeight()){
           flag=true;
      }
      
      return flag;
}


//addClass:为指定的dom元素添加样式。
function hasClass(obj, cls) {
    return obj.className.match(new RegExp('(\\s|^)' + cls + '(\\s|$)'));
}
 
//removeClass:删除指定dom元素的样式。
function addClass(obj, cls) {
    if (!this.hasClass(obj, cls)) {
        obj.className += " " + cls;
    }
}

//toggleClass:如果存在(不存在)，就删除(添加)一个样式。
function removeClass(obj, cls) {
    if (hasClass(obj, cls)) {
        var reg = new RegExp('(\\s|^)' + cls + '(\\s|$)');
        obj.className = obj.className.replace(reg, ' ');
    }
}



//点击放大缩小的区域的div时为了避免点击放大没有还原,出现bug
function fullscreenRemove(){
  node= document.body;
   if(hasClass(node,"page-portlet-fullscreen")){
        removeClass(node, "page-portlet-fullscreen");
    } 
}



           