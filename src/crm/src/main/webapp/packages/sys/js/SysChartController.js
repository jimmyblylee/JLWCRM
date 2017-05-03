
function echartlineCtr($http, $scope){//����ͼ
 
        $scope.echartData={};//����һ������ͼ���ݶ���
       $scope.chartsName = echarts.init(document.getElementById("echarts_line"),'macarons'); //��ȡҪ��ʾ����ͼͼ���id   
       $http
              .post("packages/sys/json/echart/echart_line.json") //��ȡ����ͼ����
              .success(function (response) {  
                   echarts_line($scope.chartsName,response,"statistical_objects","statistical_data","xAxis");         
              })    

};

function echartsRadarCtr($http, $scope){//�״�ͼ
       $scope.echartData={};//����һ���״�ͼ���ݶ���
       $scope.chartsName = echarts.init(document.getElementById("echarts_map"),'macarons'); //��ȡҪ��ʾ��ͼ�ֲ����id     
       $http
              .post("packages/sys/json/echart/echart_map.json")//��ȡ��ͼ�ֲ�����
              .success(function (response) { 
                 $.get('resources/global/plugins/echart/map/china.json', function (chinaJson) {
                      echarts.registerMap('china', chinaJson);
                      charts_map($scope.chartsName,response,"statistical_objects","statistical_data","statistical_region","5500","0"); //���ɵ�ͼ  
                  });
              
                            
              })   

};
function echartPieCtr($http, $scope){ //��״ͼ
         $scope.echartData={};//������״ͼһ�����ݶ���
       $scope.chartsName = echarts.init(document.getElementById("echarts_pie"),'macarons'); //��ȡҪ��ʾ��״ͼͼ���id       
       $http
              .post("packages/sys/json/echart/echart_pie.json")//��ȡ��״ͼ����
              .success(function (response) {   
                   echarts_pie($scope.chartsName,response,"statistical_objects","statistical_data");  //���ɱ�״ͼ                
                            
              })      

};

function echartCtr($http, $scope){//��״ͼ
       $scope.echartData={};//����һ����״ͼ���ݶ���
       $scope.chartsName = echarts.init(document.getElementById("echarts_bar"),'macarons');     //��ȡҪ��ʾ��״ͼͼ���id
       $http
              .post("packages/sys/json/echart/echart_bar.json") //��ȡ��״ͼ����
              .success(function (response) {  
                   echarts_bar($scope.chartsName,response,"statistical_objects","statistical_data","xAxis");  //������״ͼ
                            
              }) 
      
};



