package com.ConstructionManagement.system.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;

import javax.validation.constraints.Min;
import java.io.Serializable;


/**
 * am_equipment_require_part
 * @author
 */

public class AmEquipmentRequirePart implements Serializable {
   // @Excel(name = "id", isColumnHidden = true, needMerge = true)
    private Long id;
   // @Excel(name = "equipmentId", isColumnHidden = true, needMerge = true)
    private Long equipmentId;

    /**
     * 部件名称
     */
    @Excel(name = "部件名称", width = 20, needMerge = true)
    private String partName;

    /**
     * 部件类型
     */
    @Excel(name = "部件类别", width = 20, needMerge = true)
    private String partType;

    /**
     * 部件代码
     */
    @Excel(name = "部件代码", width = 20, needMerge = true)
    private String partCode;

    /**
     * 部件型号
     */
    @Excel(name = "部件型号", width = 20, needMerge = true)
    private String partModel;

    /**
     * 计量单位
     */
    @Excel(name = "计量单位", width = 20, needMerge = true)
    private String measurementUnit;

    /**
     * 适用设备类型
     */
    @Excel(name = "设用设备类型", width = 20, needMerge = true)
    private String applicableDeviceType;
    //部件数量
    @Excel(name = "部件数量", width = 20, needMerge = true)
    @Min(0)
    private Integer partCount;

    public Integer getPartCount() {
        return partCount;
    }

    public void setPartCount(Integer partCount) {
        this.partCount = partCount;
    }

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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
