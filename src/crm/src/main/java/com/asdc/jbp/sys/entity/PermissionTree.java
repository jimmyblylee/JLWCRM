package com.asdc.jbp.sys.entity;

import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class PermissionTree {

    private List<Map> lm;
    private String falg;

    public List<Map> getLm() {
        return lm;
    }
    public void setLm(List<Map> lm) {
        this.lm = lm;
    }
    public String getFalg() {
        return falg;
    }
    public void setFalg(String falg) {
        this.falg = falg;
    }
}
