package com.ConstructionManagement.system.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;

import java.io.Serializable;


/**
 * am_part_param_kit
 * @author
 */

public class AmPartParamKit implements Serializable {
    @Excel(name = "id", isColumnHidden = true)
    private Long id;
    //部件id
    @Excel(name = "pid", isColumnHidden = true)
    private Long pid;
    //配件id
    @Excel(name = "kid", isColumnHidden = true)
    private Long kid;
    //配件数量
    @Excel(name = "配件数量", width = 20)
    private Integer kitCount;
    @ExcelEntity
    AmKitParam amKitParam;

    public AmKitParam getAmKitParam() {
        return amKitParam;
    }

    public void setAmKitParam(AmKitParam amKitParam) {
        this.amKitParam = amKitParam;
    }

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Long getKid() {
        return kid;
    }

    public void setKid(Long kid) {
        this.kid = kid;
    }

    public Integer getKitCount() {
        return kitCount;
    }

    public void setKitCount(Integer kitCount) {
        this.kitCount = kitCount;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
