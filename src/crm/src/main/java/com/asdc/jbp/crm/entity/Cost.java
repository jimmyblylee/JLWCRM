package com.asdc.jbp.crm.entity;

import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.annotations.JoinColumnsOrFormulas;
import org.hibernate.annotations.JoinFormula;

import javax.persistence.*;

@Entity
@Table(name = "CRM_COST")
public class Cost {
    @Id
    @Column(name = "COST_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "COST_NUM")
    private Integer num;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumnsOrFormulas({
        @JoinColumnOrFormula(column = @JoinColumn(name = "COST_TYPE", referencedColumnName = "DICT_CODE", nullable = false)),
        @JoinColumnOrFormula(formula = @JoinFormula(referencedColumnName = "DICT_NATURE", value = "'COST_TYPE'")) })
    private Dict type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PACT_ID")
    private Pact pact;

    @Column(name = "COST_TIME")
    private String time;

    @Column(name = "COST_COMMENT")
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

    public Dict getType() {
        return type;
    }

    public void setType(Dict type) {
        this.type = type;
    }

    public Pact getPact() {
        return pact;
    }

    public void setPact(Pact pact) {
        this.pact = pact;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
