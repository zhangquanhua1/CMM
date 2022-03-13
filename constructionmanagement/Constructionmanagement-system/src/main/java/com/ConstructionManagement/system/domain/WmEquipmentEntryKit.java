package com.ConstructionManagement.system.domain;

import java.io.Serializable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * wm_equipment_entry_kit
 *
 * @author
 */
@Data
@Getter
@Setter
public class WmEquipmentEntryKit implements Serializable {
    private Long id;

    private Long equimentId;

    /**
     * 零配件名称
     */
    private String kitName;

    /**
     * 代码
     */
    private String kitCode;

    /**
     * 型号
     */
    private String kitModel;

    /**
     * 技术参数
     */
    private String technicalParam;

    /**
     * 适用配件类型
     */
    private String applicableKitType;

    /**
     * 计量单位
     */
    private String measurementUnit;

    private Integer kitCount;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEquimentId() {
        return equimentId;
    }

    public void setEquimentId(Long equimentId) {
        this.equimentId = equimentId;
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
