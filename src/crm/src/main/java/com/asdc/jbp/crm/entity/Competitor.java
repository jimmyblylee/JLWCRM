package com.asdc.jbp.crm.entity;

import javax.persistence.*;

@Entity
@Table(name = "CRM_COMPETITOR")
public class Competitor {
    @Id
    @Column(name = "COMP_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "COMP_NAME")
    private String name;
    @Column(name = "COMP_MAIN_BIZ")
    private String biz;
    @Column(name = "COMP_MAIN_ADD")
    private String add;
    @Column(name = "COMP_MAIN_CUSTOMER")
    private String customers;

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

    public String getBiz() {
        return biz;
    }

    public void setBiz(String biz) {
        this.biz = biz;
    }

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public String getCustomers() {
        return customers;
    }

    public void setCustomers(String customers) {
        this.customers = customers;
    }
}
