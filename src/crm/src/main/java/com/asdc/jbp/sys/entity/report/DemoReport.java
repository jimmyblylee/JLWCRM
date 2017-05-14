/**
 * Project Name jbp-features-sys
 * File Name DemoReportEntity.java
 * Package Name com.asdc.jbp.sys.entity.report
 * Create Time 2016年6月16日
 * Create by name：liujie -- email: jie_liu1@asdc.com.cn
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.sys.entity.report;

import java.io.Serializable;

import org.hibernate.envers.Audited;

/** 
 * ClassName: DemoReportEntity.java <br>
 * Description: <br>
 * Create by: name：liujie <br>email: jie_liu1@asdc.com.cn <br>
 * Create Time: 2016年6月16日<br>
 */
@Audited
public class DemoReport implements Serializable{

    /**
     * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
     */
    private static final long serialVersionUID = 1L;
    private String name;        //姓名
    private String sex;         //性别
    private String currentage;  //年龄
    private String phone;       //电话
    private String deptname;    //部门
    private String contact;     //紧急联系人
    private String contactphone;//紧急联系人电话
    private String test;        //test
    private String staffnumber; //员工编号
    private String startdate;//开始时间
    private String enddate;//结束时间
    private String company;//公司
    private String img;//图片
    private String headerimg; //头像
    private String desc;//说明
    private String seal;//电子签章
    private String demoreport;//报告模板
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the sex
     */
    public String getSex() {
        return sex;
    }
    /**
     * @param sex the sex to set
     */
    public void setSex(String sex) {
        this.sex = sex;
    }
    /**
     * @return the currentage
     */
    public String getCurrentage() {
        return currentage;
    }
    /**
     * @param currentage the currentage to set
     */
    public void setCurrentage(String currentage) {
        this.currentage = currentage;
    }
    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }
    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
    /**
     * @return the deptname
     */
    public String getDeptname() {
        return deptname;
    }
    /**
     * @param deptname the deptname to set
     */
    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }
    /**
     * @return the contact
     */
    public String getContact() {
        return contact;
    }
    /**
     * @param contact the contact to set
     */
    public void setContact(String contact) {
        this.contact = contact;
    }
    /**
     * @return the contactphone
     */
    public String getContactphone() {
        return contactphone;
    }
    /**
     * @param contactphone the contactphone to set
     */
    public void setContactphone(String contactphone) {
        this.contactphone = contactphone;
    }
    /**
     * @return the test
     */
    public String getTest() {
        return test;
    }
    /**
     * @param test the test to set
     */
    public void setTest(String test) {
        this.test = test;
    }
    /**
     * @return the staffnumber
     */
    public String getStaffnumber() {
        return staffnumber;
    }
    /**
     * @param staffnumber the staffnumber to set
     */
    public void setStaffnumber(String staffnumber) {
        this.staffnumber = staffnumber;
    }
    /**
     * @return the startdate
     */
    public String getStartdate() {
        return startdate;
    }
    /**
     * @param startdate the startdate to set
     */
    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }
    /**
     * @return the enddate
     */
    public String getEnddate() {
        return enddate;
    }
    /**
     * @param enddate the enddate to set
     */
    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }
    /**
     * @return the company
     */
    public String getCompany() {
        return company;
    }
    /**
     * @param company the company to set
     */
    public void setCompany(String company) {
        this.company = company;
    }
    /**
     * @return the img
     */
    public String getImg() {
        return img;
    }
    /**
     * @param img the img to set
     */
    public void setImg(String img) {
        this.img = img;
    }
    /**
     * @return the headerimg
     */
    public String getHeaderimg() {
        return headerimg;
    }
    /**
     * @param headerimg the headerimg to set
     */
    public void setHeaderimg(String headerimg) {
        this.headerimg = headerimg;
    }
    /**
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }
    /**
     * @param desc the desc to set
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }
    /**
     * @return the seal
     */
    public String getSeal() {
        return seal;
    }
    /**
     * @param seal the seal to set
     */
    public void setSeal(String seal) {
        this.seal = seal;
    }
    /**
     * @return the demoreport
     */
    public String getDemoreport() {
        return demoreport;
    }
    /**
     * @param demoreport the demoreport to set
     */
    public void setDemoreport(String demoreport) {
        this.demoreport = demoreport;
    }
    /**
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }
    /**
     * 
     */
    public DemoReport() {
    }

    
}
