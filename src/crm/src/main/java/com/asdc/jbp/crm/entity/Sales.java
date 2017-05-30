package com.asdc.jbp.crm.entity;

import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.annotations.JoinColumnsOrFormulas;
import org.hibernate.annotations.JoinFormula;

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumnsOrFormulas({
        @JoinColumnOrFormula(column = @JoinColumn(name = "SALES_LEVEL", referencedColumnName = "DICT_CODE", nullable = false)),
        @JoinColumnOrFormula(formula = @JoinFormula(referencedColumnName = "DICT_NATURE", value = "'SALES_LEVEL'")) })
    private Dict level;

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

    public Dict getLevel() {
        return level;
    }

    public void setLevel(Dict level) {
        this.level = level;
    }
}
