package com.ConstructionManagement.system.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;

import java.io.Serializable;
import java.util.List;



/**
 * am_part_param
 * @author
 */

public class AmPartParam implements Serializable {
    @Excel(name = "id", isColumnHidden = true, needMerge = true)
    private Long id;

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

    public String getPart_type() {
        return part_type;
    }

    public void setPart_type(String part_type) {
        this.part_type = part_type;
    }

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

    /**
     * 备注
     */
    @Excel(name = "备注", width = 20, needMerge = true)
    private String remark;

    @ExcelCollection(name = "配件信息")
    private List<AmPartParamKit> amPartParamKits;

    public List<AmPartParamKit> getAmPartParamKits() {
        return amPartParamKits;
    }

    public void setAmPartParamKits(List<AmPartParamKit> amPartParamKits) {
        this.amPartParamKits = amPartParamKits;
    }

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
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
