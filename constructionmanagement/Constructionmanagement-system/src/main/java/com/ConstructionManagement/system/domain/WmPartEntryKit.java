package com.ConstructionManagement.system.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;

import javax.validation.constraints.Min;
import java.io.Serializable;


/**
 * wm_part_entry_kit
 * @author
 */
@ExcelTarget("WmPartEntry")
public class WmPartEntryKit implements Serializable {
   // @Excel(name = "id",isColumnHidden = true)
    private Long id;
   // @Excel(name = "partId",isColumnHidden = true)
    private Long partId;

    /**
     * 零配件名称
     */
    @Excel(name = "配件名称", width = 20)
    private String kitName;

    /**
     * 代码
     */
    @Excel(name = "配件代码", width = 20)
    private String kitCode;

    /**
     * 型号
     */
    @Excel(name = "配件型号", width = 20)
    private String kitModel;

    /**
     * 技术参数
     */
    @Excel(name = "技术参数", width = 20)
    private String technicalParam;

    /**
     * 适用配件类型
     */
    @Excel(name = "适用部件类别", width = 20)
    private String applicableKitType;

    /**
     * 计量单位
     */
    @Excel(name = "计量单位", width = 20)
    private String measurementUnit;
    @Excel(name = "配件数量", width = 20)
    @Min(value=0,message="配件数量必须为数字")
    private Integer kitCount;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPartId() {
        return partId;
    }

    public void setPartId(Long partId) {
        this.partId = partId;
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
