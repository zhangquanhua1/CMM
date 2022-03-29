package com.ConstructionManagement.system.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;

import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * am_part_require_kit
 *
 * @author
 */
@ExcelTarget("AmPartRequire")
public class AmPartRequireKit implements Serializable {

   // @Excel(name = "id", isColumnHidden = true, needMerge = true)
    private Long id;
   // @Excel(name = "PartId", isColumnHidden = true, needMerge = true)
    private Long partID;

    /**
     * 零配件名称
     */
    @Excel(name = "配件名称", width = 20, needMerge = true)
    private String kitName;

    /**
     * 代码
     */
    @Excel(name = "配件代码", width = 20, needMerge = true)
    private String kitCode;

    /**
     * 型号
     */
    @Excel(name = "配件型号", width = 20, needMerge = true)
    private String kitModel;

    /**
     * 技术参数
     */
    @Excel(name = "技术参数", width = 20, needMerge = true)
    private String technicalParam;

    /**
     * 适用配件类型
     */
    @Excel(name = "适用部件类别", width = 20, needMerge = true)
    private String applicableKitType;

    /**
     * 计量单位
     */
    @Excel(name = "计量单位", width = 20, needMerge = true)
    private String measurementUnit;
    //配件数量
    @Excel(name = "配件数量", width = 20, needMerge = true)
    @Min(0)
    private Integer kitCount;
    public Long getPartID() {
        return partID;
    }

    public void setPartID(Long partID) {
        this.partID = partID;
    }

    public Integer getKitCount() {
        return kitCount;
    }

    public void setKitCount(Integer kitCount) {
        this.kitCount = kitCount;
    }

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEquimentId() {
        return partID;
    }

    public void setEquimentId(Long partID) {
        this.partID = partID;
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

    public String getMeasurementUnit() {
        return measurementUnit;
    }

    public void setMeasurementUnit(String measurementUnit) {
        this.measurementUnit = measurementUnit;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
