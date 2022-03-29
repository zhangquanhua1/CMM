package com.ConstructionManagement.system.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;


import java.io.Serializable;


/**
 * am_kit_param
 * @author
 */
public class AmKitParam implements Serializable {
   @Excel(name = "id",isColumnHidden = true)
    private Long id;

    /**
     * 零配件名称
     */
    @Excel(name="配件名称",width = 20)
    private String kitName;

    /**
     * 代码
     */
    @Excel(name="配件代码",width = 20)
    private String kitCode;

    /**
     * 型号
     */
    @Excel(name="配件型号",width = 20)
    private String kitModel;

    /**
     * 技术参数
     */
    @Excel(name="技术参数",width = 20)
    private String technicalParam;

    /**
     * 适用配件类型
     */
    @Excel(name="适用部件类别",width = 20)
    private String applicableKitType;
    /**
     * 计量单位
     */
    @Excel(name="计量单位",width = 20)
    private String measurementUnit;

    public String getMeasurementUnit() {
        return measurementUnit;
    }

    public void setMeasurementUnit(String measurementUnit) {
        this.measurementUnit = measurementUnit;
    }

    /**
     * 备注
     */
    private String remark;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKitName() {
        return kitName;
    }

    public void setKitName(String kitName) {
        this.kitName = kitName;
    }

    public String getKitCode() {
        return kitCode;
    }

    public void setKitCode(String kitCode) {
        this.kitCode = kitCode;
    }

    public String getKitModel() {
        return kitModel;
    }

    public void setKitModel(String kitModel) {
        this.kitModel = kitModel;
    }

    public String getTechnicalParam() {
        return technicalParam;
    }

    public void setTechnicalParam(String technicalParam) {
        this.technicalParam = technicalParam;
    }

    public String getApplicableKitType() {
        return applicableKitType;
    }

    public void setApplicableKitType(String applicableKitType) {
        this.applicableKitType = applicableKitType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
