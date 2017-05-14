/**
 * Project Name jbp-framework
 * File Name ApplicationIReportView.java
 * Package Name com.asdc.jbp.framework.jasper
 * Create Time 2016年6月16日
 * Create by name：liujie -- email: jie_liu1@asdc.com.cn
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.sys.jasper;

import java.util.Map;

import org.springframework.web.servlet.view.jasperreports.JasperReportsMultiFormatView;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

/** 
 * ClassName: ApplicationIReportView.java <br>
 * Description: <br>
 * Create by: name：liujie <br>email: jie_liu1@asdc.com.cn <br>
 * Create Time: 2016年6月16日<br>
 */
public class ApplicationIReportView extends JasperReportsMultiFormatView {
    private JasperReport jasperReport;
    
    public ApplicationIReportView() {
        super();  
    }  
  
    protected JasperPrint fillReport(Map<String, Object> model) throws Exception {
        if (model.containsKey("reportModelUrl")) {
            setUrl(String.valueOf(model.get("reportModelUrl")));
            this.jasperReport = loadReport();
        }  
          
        return super.fillReport(model); //TODO  
    }  
      
    protected JasperReport getReport() {
        return this.jasperReport;
    }  
}
