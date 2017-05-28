package com.asdc.jbp.crm.entity;

import javax.persistence.*;

@Entity
@Table(name = "CRM_ACTIVITY")
public class Activity {

    @Id
    @Column(name = "ACTIVITY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "ACTIVITY_CONTENT")
    private String content;
    @Column(name = "ACTIVITY_EFFECT")
    private String effect;
    @Column(name = "ACTIVITY_DATE")
    private String date;
    @Column(name = "ACTIVITY_COST")
    private Integer cost;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }
}
