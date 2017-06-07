package com.asdc.jbp.crm.entity;

import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.annotations.JoinColumnsOrFormulas;
import org.hibernate.annotations.JoinFormula;

import javax.persistence.*;

@Entity
@Table(name = "CRM_PROJECT")
public class Project {
    @Id
    @Column(name = "PROJ_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PACT_ID")
    private Pact pact;

    @Column(name = "PROJ_PROCESS")
    private Integer process;

    @Column(name = "PROJ_START")
    private String start;

    @Column(name = "PROJ_END")
    private String end;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumnsOrFormulas({
        @JoinColumnOrFormula(column = @JoinColumn(name = "PROJ_STATUS", referencedColumnName = "DICT_CODE", nullable = false)),
        @JoinColumnOrFormula(formula = @JoinFormula(referencedColumnName = "DICT_NATURE", value = "'PROJ_STATUS'")) })
    private Dict status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROJ_IMPL_MGMT_ID")
    private Implementer charger;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Pact getPact() {
        return pact;
    }

    public void setPact(Pact pact) {
        this.pact = pact;
    }

    public Integer getProcess() {
        return process;
    }

    public void setProcess(Integer process) {
        this.process = process;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public Dict getStatus() {
        return status;
    }

    public void setStatus(Dict status) {
        this.status = status;
    }

    public Implementer getCharger() {
        return charger;
    }

    public void setCharger(Implementer charger) {
        this.charger = charger;
    }
}
