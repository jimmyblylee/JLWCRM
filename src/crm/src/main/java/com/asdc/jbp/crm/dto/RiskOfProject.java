package com.asdc.jbp.crm.dto;

public class RiskOfProject {
    private Integer projId;
    private String projName;
    private Integer projSum;
    /** 产值. */
    private double outputValue;
    /** 回款. */
    private Integer recPay;
    /** 成本. */
    private Integer cost;
    /** 进度 */
    private Integer process;
    /** 实时利润. */
    private Integer gain;
    /** 实时利润率. */
    private double gainRate;

    public Integer getProjId() {
        return projId;
    }

    public void setProjId(Integer projId) {
        this.projId = projId;
    }

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

    public Integer getProjSum() {
        return projSum;
    }

    public void setProjSum(Integer projSum) {
        this.projSum = projSum;
    }

    public double getOutputValue() {
        return outputValue;
    }

    public void setOutputValue(double outputValue) {
        this.outputValue = outputValue;
    }

    public Integer getRecPay() {
        return recPay;
    }

    public void setRecPay(Integer recPay) {
        this.recPay = recPay;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Integer getProcess() {
        return process;
    }

    public void setProcess(Integer process) {
        this.process = process;
    }

    public Integer getGain() {
        return gain;
    }

    public void setGain(Integer gain) {
        this.gain = gain;
    }

    public double getGainRate() {
        return gainRate;
    }

    public void setGainRate(double gainRate) {
        this.gainRate = gainRate;
    }
}
