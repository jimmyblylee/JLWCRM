package com.asdc.jbp.crm.entity;

import javax.persistence.*;

@Entity
@Table(name = "CRM_REC_PAY")
public class RecPay {
    @Id
    @Column(name = "REC_PAY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "REC_PAY_NUM")
    private Integer num;

    @Column(name = "REC_PAY_DATE")
    private String date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PACT_ID")
    private Pact pact;

    @Column(name = "REC_PAY_COMMENT")
    private String comment;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Pact getPact() {
        return pact;
    }

    public void setPact(Pact pact) {
        this.pact = pact;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
