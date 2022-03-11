package com.ConstructionManagement.system.domain;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * am_part_param
 * @author
 */
@Data
public class AmPartParam implements Serializable {
    private Long id;

    /**
     * 部件名称
     */
    private String partName;

    /**
     * 部件类型
     */
    private String part_type;

    /**
     * 部件代码
     */
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
    private String partModel;

    /**
     * 计量单位
     */
    private String measurementUnit;

    /**
     * 适用设备类型
     */
    private String applicableDeviceType;

    /**
     * 备注
     */
    private String remark;
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
