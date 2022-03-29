package com.ConstructionManagement.system.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;

import javax.validation.constraints.Min;
import java.io.Serializable;


/**
 * wm_equipment_entry_part
 *
 * @author
 */
@ExcelTarget("WmEquipmentEntry")
public class WmEquipmentEntryPart implements Serializable {
    //@Excel(name = "id",isColumnHidden = true)
    private Long id;
    //@Excel(name = "equipmentId",isColumnHidden = true)
    private Long equipmentId;

    /**
     * 部件名称
     */
    @Excel(name = "部件名称", width = 20)
    private String partName;

    /**
     * 部件类型
     */
    @Excel(name = "部件类型", width = 20)
    private String partType;

    /**
     * 部件代码
     */
    @Excel(name = "部件代码", width = 20)
    private String partCode;

    /**
     * 部件型号
     */
    @Excel(name = "部件型号", width = 20)
    private String partModel;

    /**
     * 计量单位
     */
    @Excel(name = "计量单位", width = 20)
    private String measurementUnit;

    /**
     * 适用设备类型
     */
    @Excel(name = "适用设备类型", width = 20)
    private String applicableDeviceType;
    @Excel(name = "部件数量", width = 20)
    @Min(value=0,message="部件数量必须为数字")
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
