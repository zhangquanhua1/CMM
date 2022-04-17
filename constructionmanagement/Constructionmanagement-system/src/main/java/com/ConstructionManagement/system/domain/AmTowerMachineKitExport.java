package com.ConstructionManagement.system.domain;


import cn.afterturn.easypoi.excel.annotation.Excel;

public class AmTowerMachineKitExport {
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
    //配件数量
    @Excel(name = "配件数量", width = 20)
    private Integer kitCount;

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

    public String getMeasurementUnit() {
        return measurementUnit;
    }

    public void setMeasurementUnit(String measurementUnit) {
        this.measurementUnit = measurementUnit;
    }

    public Integer getKitCount() {
        return kitCount;
    }

    public void setKitCount(Integer kitCount) {
        this.kitCount = kitCount;
    }
}
