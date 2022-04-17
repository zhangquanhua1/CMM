package com.ConstructionManagement.system.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;

import java.util.List;

/**
 * 设备参数部件导出信息类
 */
public class AmTowerMachinePartExport {
    /**
     * 部件名称
     */
    @Excel(name = "部件名称", width = 20, needMerge = true)
    private String partName;

    /**
     * 部件类型
     */
    @Excel(name = "部件类型", width = 20, needMerge = true)
    private String part_type;

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
    @Excel(name = "适用设备类型", width = 20, needMerge = true)
    private String applicableDeviceType;

    @Excel(name = "部件数量", width = 20, needMerge = true)
    private Integer partCount;

    @ExcelCollection(name="部件绑定的配件信息")
    List<AmTowerMachineKitExport> kits;

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public String getPart_type() {
        return part_type;
    }

    public void setPart_type(String part_type) {
        this.part_type = part_type;
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

    public List<AmTowerMachineKitExport> getKits() {
        return kits;
    }

    public void setKits(List<AmTowerMachineKitExport> kits) {
        this.kits = kits;
    }
}
