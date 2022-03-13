package com.ConstructionManagement.system.domain;

import java.io.Serializable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * wm_equipment_entry_part
 *
 * @author
 */
@Data
public class WmEquipmentEntryPart implements Serializable {
    private Long id;

    private Long equipmentId;

    /**
     * 部件名称
     */
    private String partName;

    /**
     * 部件类型
     */
    private String partType;

    /**
     * 部件代码
     */
    private String partCode;

    /**
     * 部件型号
     */
    private String partModel;

    /**
     * 计量单位
     */
    private String measurementUnit;

    /**
     * 适用设备类型
     */
    private String applicableDeviceType;

    private Integer partCount;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Long equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public String getPartType() {
        return partType;
    }

    public void setPartType(String partType) {
        this.partType = partType;
    }

    public String getPartCode() {
        return partCode;
    }

    public void setPartCode(String partCode) {
        this.partCode = partCode;
    }

    public String getPartModel() {
        return partModel;
    }

    public void setPartModel(String partModel) {
        this.partModel = partModel;
    }

    public String getMeasurementUnit() {
        return measurementUnit;
    }

    public void setMeasurementUnit(String measurementUnit) {
        this.measurementUnit = measurementUnit;
    }

    public String getApplicableDeviceType() {
        return applicableDeviceType;
    }

    public void setApplicableDeviceType(String applicableDeviceType) {
        this.applicableDeviceType = applicableDeviceType;
    }

    public Integer getPartCount() {
        return partCount;
    }

    public void setPartCount(Integer partCount) {
        this.partCount = partCount;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
