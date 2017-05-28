package com.asdc.jbp.crm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SYS_DICT")
public class Dict {
    @Id
    @Column(name = "DICT_ID")
    private Integer id;
    @Column(name = "DICT_NATURE")
    private String nature;
    @Column(name = "DICT_CODE")
    private String code;
    @Column(name = "DICT_VALUE")
    private String value;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
