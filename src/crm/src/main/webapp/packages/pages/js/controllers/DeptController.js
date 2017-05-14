/**
 * 组织机构 ---控制器
 */
 MetronicApp.controller('addDeptCtrl', ['$scope','$http','$q',function($scope,$http,$q) {
    $scope.submitted = false;
    $scope.isSame=false;
    $scope.tncShow=true;
    $scope.isSub=false;
    $scope.isChecked=false;
    $scope.submitForm = function(){
    	if($scope.addDeptForm.$valid){
    		$scope.formData = $scope.creater;
    	}
    }
	 
 }])


//  function ExpandedNodes($scope) {
//     $scope.treedata=createSubTree(3, 4, "");
//    //返回的是一个大数组
///*    $scope.expandedNodes = [$scope.treedata[1]];
//    $scope.setExpanded = function() {
//        $scope.expandedNodes = [$scope.treedata[1],
//            $scope.treedata[2]
//        ];
//    };*/
//}
/*
 * 树模型接受控制层  -------没有完全展开的树模型
 */
 function FileStyle($scope) {
	/*
	 * 怎样查出这样的json呢？
	 *   看看是否包含：
	 */  
	//{\"id\":1,\"postalcode\":\"10000\",\"address\":\"中国北京\",\"email\":\"453@qq.com\",\"tel\":\"8817883\",\"name\":\"总公司\"}
    //{\"id\":4,\"postalcode\":\"10000\",\"address\":\"中国北京\",\"email\":\"433@qq.com\",\"tel\":\"8817884\",\"name\":\"市场部\"}
	 
	 
    $scope.treedata=[
        {label: "总公司", type: "folder", children: [
            {label: "财务部", type: "doc"},
            {label: "技术部", type: "doc"},
            {label: "市场部", type: "doc"},
        ]},
        {label: "日本分公司", type: "folder", children: [
            {label: "财务部", type: "doc"},
            {label: "销售部", type: "doc"}
        ]},
        {label: "郑州分公司", type: "doc"}
    ];
    //返回的是一个大数组
    $scope.expandedNodes = [$scope.treedata[3],
        $scope.treedata[2]];
    
    $scope.showSelected = function(sel) {
        $scope.selectedNode = sel;
    };
}

/*
 * 查找所有的部门
 */
 function DepttableCtrl($scope, $filter, $http, $q){
	
    	$scope.filteredItems = [];
    	$scope.groupedItems = [];
    	$scope.currentPage = 1;
    	$scope.itemsPerPage = 5;
    	$scope.Pagenum = [ 5, 10, 15 ];
    	$scope.totalItems = 0;
    	$scope.groupedItems = [];
    	$scope.order = "id";
    	$scope.reverse = false;
    	
    	/*
    	 * 从数据库中的到数据啦的数据,查询方法不需要使用参数啦
    	 */
        var data = mergeReauestData('DeptController','queryAllSysDept',"");
        console.log("data:"+data);
    	//发送get请求
    	var result = sendPost($http,data, $q);

    	result.then(function(value) {
    		console.log("后台请求的数据："+value);//josn字符串
            console.log("后台请求的数据进行第一次json转换："+JSON.parse(value));//json对象
    		
            value = JSON.parse(value);
    		var deptArray = value.token;
    		
    		console.log("数据列表：--字符串形式"+deptArray);
    		console.log("数据列表：--json形式"+JSON.parse(deptArray));
    		
    		$scope.items = JSON.parse(deptArray);
    		
    /*=============================================*/	
    		 //检索
    	    var searchMatch = function (haystack, needle) {
    	        if (!needle) {
    	            return true;
    	        }
    	            return haystack.toLowerCase().indexOf(needle.toLowerCase()) !== -1;
    	    };
    	    // init the filtered items
    	    $scope.search = function () {
    	        $scope.filteredItems = $filter('filter')($scope.items, function (items) {
    	            for(var attr in items) {
    	                if (searchMatch(items[attr],$scope.query))
    	                    return true;
    	            }
    	            return false;
    	        });            
    	         $scope.totalItemsLength();
    	         $scope.groupToPages();
    	         $scope.totalItems = $scope.totalItemsLength();       
    	         $scope.groupedItems = $scope.groupToPages();  
    	         console.log("groupedItems:"+$scope.groupedItems);
    	     
    	    }; 
    	     //获取总条数 
    	    $scope.totalItemsLength = function(){
    	        var lengths = 0
    	        for (var item in $scope.filteredItems) {
    	            lengths++
    	        };
    	        return lengths       
    	    };
    	    //页面数据
    	    $scope.groupToPages = function () { 
    	        $scope.pagedItems = [];   
    	        for (var i = 0; i < $scope.totalItems; i++) {              
    	               if (i % $scope.itemsPerPage === 0) {
    	                    $scope.pagedItems[Math.floor(i / $scope.itemsPerPage)] = [ $scope.filteredItems[i] ];
    	                } else {
    	                    $scope.pagedItems[Math.floor(i / $scope.itemsPerPage)].push($scope.filteredItems[i]);
    	                }
    	         }  
    	         return  $scope.pagedItems;
    	    };
    	    $scope.search();  
    	    
    	    $scope.setPagenum = function(itemsPerPage) {          
    	        $scope.itemsPerPage = itemsPerPage;
    	        $scope.groupedItems = $scope.groupToPages(); 
    	    };
    	    $scope.sort_by = function(newSortingOrder){
    	      if ($scope.order == newSortingOrder){
    	        $scope.reverse = !$scope.reverse;
    	    }
    	        $scope.order = newSortingOrder;
//    	    // icon setup
    	    $('th a').each(function(){
    	        // icon reset
    	        $(this).removeClass().addClass('fa fa-sort');
    	    });
    	    if ($scope.reverse){
    	        $('th.'+newSortingOrder+' a').removeClass().addClass('fa fa-sort-down');
    	    }else{
    	        $('th.'+newSortingOrder+' a').removeClass().addClass('fa fa-sort-up');
    	    }

    	    }

    	 }), function(error) {
    		 console.info(error);
    	 };
	
//    $scope.items = [
//                    {"name":"杨宣","email":"shuxer@gmail.com ","address":"中国博爱","postalCode":"454450"}, 
//                    {"name":"杨宣","email":"shuxer@gmail.com ","address":"中国博爱","postalCode":"454450"}, 
//                    {"name":"杨宣","email":"shuxer@gmail.com ","address":"中国博爱","postalCode":"454450"}, 
//                    {"name":"杨宣","email":"shuxer@gmail.com ","address":"中国博爱","postalCode":"454450"}, 
//                ];
   
 } 
    function ModalDemoCtrl($scope, $modal, $log)
    {
        $scope.items = ['item1', 'item2', 'item3'];
        $scope.open = function(size)
        {
            var modalInstance = $modal.open(
            {
                templateUrl: 'views/editor_modal.html',
                controller: 'ModalInstanceCtrl',
                size: size,
                resolve:
                {
                    items: function()
                    {
                        return $scope.items;
                    },
                    deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load([{
                        name: 'MetronicApp',
                        files: [
                            "../assets/global/plugins/bootstrap-daterangepicker/daterangepicker.min.css" ,
                            "../assets/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css",
                            "../assets/global/plugins/moment.min.js" ,
                            "../assets/global/plugins/bootstrap-daterangepicker/daterangepicker.min.js" ,
                            "../assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" ,
                            "../assets/pages/scripts/components-date-time-pickers.min.js" ,
                             '../assets/global/plugins/angularjs/plugins/angular-file-upload/angular-file-upload.min.js',                          
                           
                        ] 
                    },{
                        name: 'MetronicApp',
                        files: [
                            'js/controllers/GeneralPageController.js'
                        ]
                    }
                    ]);
                }] 
                }
            });
            modalInstance.result.then(function(selectedItem)
            {
                $scope.selected = selectedItem;
            }, function()
            {
                $log.info('Modal dismissed at: ' + new Date());
            });
        };

       
    }
    function ModalInstanceCtrl($scope, $modalInstance, items)
    {
                
        $scope.submitForm = function() {
        if ($scope.editorForm.$valid) {
             $modalInstance.close($scope.selected.item);
        } else {
            $scope.editorForm.submitted = true;
        }
        }   
        $scope.items = items;
        $scope.selected = {
            item: $scope.items[0]
        };
        // $scope.ok = function()
        // {
        //     $modalInstance.close($scope.selected.item);
        // };
        $scope.cancel = function()
        {
            $modalInstance.dismiss('cancel');
        };
      
        $scope.config = {
        //这里可以选择自己需要的工具按钮名称,此处仅选择如下五个
            //'fullscreen',不可用 就会有冲突
     toolbars: [
            [
                'anchor', //锚点
                'undo', //撤销
                'redo', //重做
                'bold', //加粗
                'indent', //首行缩进
                'snapscreen', //截图
                'italic', //斜体
                'underline', //下划线
                'strikethrough', //删除线
                'subscript', //下标
                'fontborder', //字符边框
                'superscript', //上标
                'formatmatch', //格式刷
                'source', //源代码
                'blockquote', //引用
                'pasteplain', //纯文本粘贴模式
                'selectall', //全选
                'print', //打印
                'preview', //预览
                'horizontal', //分隔线
                'removeformat', //清除格式
                'time', //时间
                'date', //日期
                'unlink', //取消链接
                'insertrow', //前插入行
                'insertcol', //前插入列
                'mergeright', //右合并单元格
                'mergedown', //下合并单元格
                'deleterow', //删除行
                'deletecol', //删除列
                'splittorows', //拆分成行
                'splittocols', //拆分成列
                'splittocells', //完全拆分单元格
                'deletecaption', //删除表格标题
                'inserttitle', //插入标题
                'mergecells', //合并多个单元格
                'deletetable', //删除表格
                'cleardoc', //清空文档
                'insertparagraphbeforetable', //"表格前插入行"
                'insertcode', //代码语言
                'fontfamily', //字体
                'fontsize', //字号
                'paragraph', //段落格式
                'simpleupload', //单图上传
                'insertimage', //多图上传
                'edittable', //表格属性
                'edittd', //单元格属性
                'link', //超链接
                'emotion', //表情
                'spechars', //特殊字符
                'searchreplace', //查询替换
                'map', //Baidu地图
                'gmap', //Google地图
                'insertvideo', //视频
                'help', //帮助
                'justifyleft', //居左对齐
                'justifyright', //居右对齐
                'justifycenter', //居中对齐
                'justifyjustify', //两端对齐
                'forecolor', //字体颜色
                'backcolor', //背景色
                'insertorderedlist', //有序列表
                'insertunorderedlist', //无序列表              
                'directionalityltr', //从左向右输入
                'directionalityrtl', //从右向左输入
                'rowspacingtop', //段前距
                'rowspacingbottom', //段后距
                'pagebreak', //分页
                'insertframe', //插入Iframe
                'imagenone', //默认
                'imageleft', //左浮动
                'imageright', //右浮动
                'attachment', //附件
                'imagecenter', //居中
                'wordimage', //图片转存
                'lineheight', //行间距
                'edittip ', //编辑提示
                'customstyle', //自定义标题
                'autotypeset', //自动排版
                'webapp', //百度应用
                'touppercase', //字母大写
                'tolowercase', //字母小写
                'background', //背景
                'template', //模板
                'scrawl', //涂鸦
                'music', //音乐
                'inserttable', //插入表格
                'drafts', // 从草稿箱加载
                'charts', // 图表
            ]
        ],
             //focus时自动清空初始化时的内容
             autoClearinitialContent: true,
             //关闭字数统计
             wordCount: false,
             //关闭elementPath
             elementPathEnabled: false,

             zIndex : 99999999,

             initialFrameWidth:'100%',

             initialFrameHeight:300,
        }
    $scope.ready = function(editor){
      editor.getContent();
    }

}