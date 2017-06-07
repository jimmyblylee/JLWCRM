package com.asdc.jbp.crm.entity;

import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.annotations.JoinColumnsOrFormulas;
import org.hibernate.annotations.JoinFormula;

import javax.persistence.*;

@Entity
@Table(name = "CRM_IMPLEMENTER")
public class Implementer {
    @Id
    @Column(name = "IMPL_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "IMPL_NAME")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumnsOrFormulas({
        @JoinColumnOrFormula(column = @JoinColumn(name = "IMPL_ROLE", referencedColumnName = "DICT_CODE", nullable = false)),
        @JoinColumnOrFormula(formula = @JoinFormula(referencedColumnName = "DICT_NATURE", value = "'IMPL_ROLE'")) })
    private Dict role;

    @Column(name = "IMPL_PAY")
    private Integer pay;

    @Column(name = "IMPL_TEL")
    private String tel;

    @Column(name = "IMPL_MAIL")
    private String mail;

    @Column(name = "IMPL_IMG")
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

    public Dict getRole() {
        return role;
    }

    public void setRole(Dict role) {
        this.role = role;
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
