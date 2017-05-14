package com.asdc.jbp.sys.jasper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class MockDataFactory {
	
	public MockDataFactory()
	{
	}
	
	public GloomyFishSummaryInfoBean getSummaryInfo()
	{
		GloomyFishSummaryInfoBean summaryBean = new GloomyFishSummaryInfoBean();
		summaryBean.setBlogURL("http://blog.csdn.net/jia20003");
		summaryBean.setMajorDomain("J2SE, J2EE, WEB developer");
		summaryBean.setName("jia20003");
		summaryBean.setNickName("gloomyfish");
		summaryBean.setRegDate("2003-03-02");
		summaryBean.setWorkYears(8);
		return summaryBean;
	}
	
	public JRDataSource getCategoriesData()
	{
		List<ArticlesCategory> listData = new ArrayList<ArticlesCategory>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy, hh:mm:ss");
		Date createDte = new Date();
		ArticlesCategory category1 = new ArticlesCategory();
		ArticlesCategory category2 = new ArticlesCategory();
		ArticlesCategory category3 = new ArticlesCategory();
		ArticlesCategory category4 = new ArticlesCategory();
		ArticlesCategory category5 = new ArticlesCategory();
		ArticlesCategory category6 = new ArticlesCategory();

		category1.setCategoryName("Android Development");
		category1.setCount(6);
		category1.setCreateDate(sdf.format(createDte));
		category2.setCategoryName("Swing Desktop Development");
		category2.setCount(21);
		category2.setCreateDate(sdf.format(createDte));
		category3.setCategoryName("JAVA 2D Image Process");
		category3.setCount(56);
		category3.setCreateDate(sdf.format(createDte));
		category4.setCategoryName("J2EE");
		category4.setCount(8);
		category4.setCreateDate(sdf.format(createDte));
		category5.setCategoryName("HTML5");
		category5.setCount(4);
		category5.setCreateDate(sdf.format(createDte));
		category6.setCategoryName("Network Protocols Research");
		category6.setCount(4);
		category6.setCreateDate(sdf.format(createDte));
		category6.setCategoryName("ExtJS Learning");
		category6.setCount(2);
		category6.setCreateDate(sdf.format(createDte));
		listData.add(category1);
		listData.add(category2);
		listData.add(category3);
		listData.add(category4);
		listData.add(category5);
		listData.add(category6);
		JRBeanCollectionDataSource data = new JRBeanCollectionDataSource(listData);
		return data;
	}

}
