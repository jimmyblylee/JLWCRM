/**
 * Project Name jbp-features-sys
 * File Name DemoReportController.java
 * Package Name com.asdc.jbp.sys.controller
 * Create Time 2016年6月16日
 * Create by name：liujie -- email: jie_liu1@asdc.com.cn
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.sys.controller.report;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.asdc.jbp.framework.action.helper.ControllerHelper;
import com.asdc.jbp.framework.dto.SessionDTO;
import com.asdc.jbp.framework.dto.WorkDTO;
import com.asdc.jbp.framework.exception.ServiceException;
import com.asdc.jbp.sys.entity.report.DemoReport;
import com.asdc.jbp.sys.jasper.MockDataFactory;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/** 
 * ClassName: DemoReportController.java <br>
 * Description: <br>
 * Create by: name：liujie <br>email: jie_liu1@asdc.com.cn <br>
 * Create Time: 2016年6月16日<br>
 */
@Controller("DemoReportController")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DemoReportController extends ControllerHelper{

    private WorkDTO workDTO;
    private SessionDTO sessionDTO;
    private HttpServletRequest servletRequest;
    public void setWorkDTO(WorkDTO workDTO) {
        this.workDTO = workDTO;
    }
    public void setSessionDTO(SessionDTO sessionDTO) {
        this.sessionDTO = sessionDTO;
    }
    public void setServletRequest(HttpServletRequest servletRequest) {
        this.servletRequest = servletRequest;
    }
    
    @RequestMapping(value = "/nihao", method = RequestMethod.GET)
    public void nihao(){
    }
   
    @RequestMapping("/initData")
    public void initData(){
        HttpSession session = servletRequest.getSession();
        List<DemoReport> converJsonToBeanListByKey = workDTO.converJsonToBeanListByKey("report", DemoReport.class);
        session.setAttribute("dataSource", converJsonToBeanListByKey);
        workDTO.setResult(true);
    }
    @RequestMapping("/testDemo")
    public String demoReport(Model model,HttpServletRequest request) throws ServiceException{
        String modelName = request.getParameter("modelName");
        String reportModelUrl = "packages/report/"+modelName;
        HttpSession session = request.getSession();
        List<Object> listD = (List<Object>) session.getAttribute("dataSource");
        MockDataFactory dataprovider = new MockDataFactory();
        JRDataSource categoryData  = new JRBeanCollectionDataSource(getList());
        JREmptyDataSource emptyData = new JREmptyDataSource();
        if(modelName.equalsIgnoreCase("fishreport.jrxml")){
            model.addAttribute("reportModelUrl", reportModelUrl);  
            model.addAttribute("format", "pdf"); // 报表格式  
            model.addAttribute("jrMainDataSource", emptyData); 
            model.addAttribute("datasource", emptyData);
            model.addAttribute("JasperfishSubReportDatasource", categoryData);
            model.addAttribute("JasperfishSummaryInfo", dataprovider.getSummaryInfo());
        } if(modelName.equalsIgnoreCase("reportDemo01.jrxml")){
            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listD);
            JREmptyDataSource data = new JREmptyDataSource();
            model.addAttribute("reportModelUrl", reportModelUrl);  
            model.addAttribute("format", "pdf"); // 报表格式  
            model.addAttribute("jrMainDataSource", beanColDataSource); 
            model.addAttribute("datasource", emptyData);
            model.addAttribute("JasperfishSubReportDatasource", categoryData);
            model.addAttribute("JasperfishSummaryInfo", dataprovider.getSummaryInfo());
        } else {
            List<DemoReport> list = getList();
            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(list);
            model.addAttribute("reportModelUrl", reportModelUrl);  
            model.addAttribute("format", "pdf"); // 报表格式  
            model.addAttribute("jrMainDataSource", beanColDataSource); 
            model.addAttribute("datasource", emptyData);
            model.addAttribute("JasperfishSubReportDatasource", categoryData);
            
        }
        return "iReportView";
    }
    
    private List<DemoReport> getList(){
        List<DemoReport> list = new ArrayList<DemoReport>();
        for (int i = 0; i < 1; i++) {
            /*list.add(new DemoReportEntity("张三"+i,"男","2","123","移动","李四","321","test",
                    "00123","2016-01-01","2016-02-02","ASDC","http://localhost:8080/jbp/packages/report/test.jpg",
                    "http://localhost:8080/jbp/packages/report/test.jpg",
                    "蓝色铸造的苍穹之巅几道云路铺展。不舍归去的夕阳，"+"缓缓洒着晚霞镶嵌天边。绿色装饰的草原版图，( 文章阅读网：www.sanwen.net )几层草毯起伏。恋恋不舍的牛羊，迟迟追赶牧笛遗落尾端。"
                            + "游荡的骆驼群，荡着驼铃落平沙，依偎在柳林的湖畔。自由翱翔的雄鹰，振动着矫健的两翼，点缀染成火焰的长空。我骑在奔驰的骏马上，感动，前方纵马驰骋的身影，"
                            + "挥洒一笔豪迈的风景，惊讶于怦然心动的美，含笑泪满盈。嗅着泥土与芳草混淆的气息，眷恋一幅柔和的夕阳容，灵隽一份安宁的风光情。"
                            + "岂知我痴醉的水墨丹青，你仿佛早已算定，久久等舞动的篝火缭眼眸，袅袅的炊烟暖心窝。魂牵西拉木伦的传说，浓墨重彩颂一代天骄。"
                            + "念往昔，恋上你沙漠的号角。草原夜幕下，谁的歌等谁来和？节奏轻快之伏，编织幽梦，离弦情，折弯皎月的弓。曲调婉转之谷，呼唤心灵，"
                            + "无墨倾，荡起心泼一泓。品着香醇的茶酒，聆听沙湾的梵音，舒展开沉郁的魂，虔诚于古老的图腾。几番思量，在神秘的摇篮里，沉入梦。"
                            + "挽光阴，留得草原夕阳盼黎明。首发散文网：http://www.sanwen.net/subject/3848527/",
                    "张三","moban"));*/
        }
        return list;
    }
    public ModelAndView reportDemo() throws ServiceException{
        String reportModelUrl = workDTO.get("reportModelUrl");
        reportModelUrl = "classpath:jasper/" + reportModelUrl;
        log.debug("模板路径{}",reportModelUrl);
        List<DemoReport> list = getList();
        return new ModelAndView("iReportView");
    }
    
    @RequestMapping(value = "/getpdfReport", method = RequestMethod.GET)
    public ModelAndView doSalesReportPDF(ModelAndView modelAndView) {
        
        // Retrieve our data from a mock data provider
        MockDataFactory dataprovider = new MockDataFactory();
        
        // Assign the datasource to an instance of JRDataSource
        // JRDataSource is the datasource that Jasper understands
        // This is basically a wrapper to Java's collection classes
        JRDataSource categoryData  = dataprovider.getCategoriesData();

        // parameterMap is the Model of our application
        Map<String,Object> parameterMap = new HashMap<String,Object>();
        
        // must have the empty data source!!!
        JREmptyDataSource emptyData = new JREmptyDataSource();
        parameterMap.put("datasource", emptyData);
        parameterMap.put("JasperfishSubReportDatasource", categoryData);
        parameterMap.put("JasperfishSummaryInfo", dataprovider.getSummaryInfo());
        
        // pdfReport is the View of our application
        // This is declared inside the /WEB-INF/jasper-views.xml
        modelAndView = new ModelAndView("pdfReport", parameterMap);
        
        // Return the View and the Model combined
        return modelAndView;
    }
}
