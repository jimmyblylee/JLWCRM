package com.asdc.jbp.crm.entity;

import javax.persistence.*;

@Entity
@Table(name = "CRM_SALES")
public class Sales {

    @Id
    @Column(name = "SALES_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "SALES_NAME")
    private String name;
    @Column(name = "SALES_PAY")
    private Integer pay;
    @Column(name = "SALES_TEL")
    private String tel;
    @Column(name = "SALES_MAIL")
    private String mail;
    @Column(name = "SALES_IMG")
    private String img;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPay() {
        return pay;
    }

    public void setPay(Integer pay) {
        this.pay = pay;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
