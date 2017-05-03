
function echartlineCtr($http, $scope){//折线图
 
        $scope.echartData={};//声明一个折线图数据对象
       $scope.chartsName = echarts.init(document.getElementById("echarts_line"),'macarons'); //获取要显示折线图图表的id   
       $http
              .post("packages/sys/json/echart/echart_line.json") //获取折线图数据
              .success(function (response) {  
                   echarts_line($scope.chartsName,response,"statistical_objects","statistical_data","xAxis");         
              })    

};

function echartsRadarCtr($http, $scope){//雷达图
       $scope.echartData={};//声明一个雷达图数据对象
       $scope.chartsName = echarts.init(document.getElementById("echarts_map"),'macarons'); //获取要显示地图分布表的id     
       $http
              .post("packages/sys/json/echart/echart_map.json")//获取地图分布数据
              .success(function (response) { 
                 $.get('resources/global/plugins/echart/map/china.json', function (chinaJson) {
                      echarts.registerMap('china', chinaJson);
                      charts_map($scope.chartsName,response,"statistical_objects","statistical_data","statistical_region","5500","0"); //生成地图  
                  });
              
                            
              })   

};
function echartPieCtr($http, $scope){ //饼状图
         $scope.echartData={};//声明饼状图一个数据对象
       $scope.chartsName = echarts.init(document.getElementById("echarts_pie"),'macarons'); //获取要显示饼状图图表的id       
       $http
              .post("packages/sys/json/echart/echart_pie.json")//获取饼状图数据
              .success(function (response) {   
                   echarts_pie($scope.chartsName,response,"statistical_objects","statistical_data");  //生成饼状图                
                            
              })      

};

function echartCtr($http, $scope){//柱状图
       $scope.echartData={};//声明一个柱状图数据对象
       $scope.chartsName = echarts.init(document.getElementById("echarts_bar"),'macarons');     //获取要显示柱状图图表的id
       $http
              .post("packages/sys/json/echart/echart_bar.json") //获取柱状图数据
              .success(function (response) {  
                   echarts_bar($scope.chartsName,response,"statistical_objects","statistical_data","xAxis");  //生成柱状图
                            
              }) 
      
};



