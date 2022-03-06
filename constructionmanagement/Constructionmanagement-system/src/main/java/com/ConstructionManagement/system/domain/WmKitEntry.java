package com.ConstructionManagement.system.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * wm_kit_entry
 * @author
 */
@Data
public class WmKitEntry implements Serializable {
    private Long id;

    /**
     * 零配件名称
     */
    private String kitName;

    /**
     * 产品编号
     */
    private Long productNum;

    /**
     * 生产厂家
     */
    private String vender;

    /**
     * 零配件类别
     */
    private String kitType;

    /**
     * 所属设备
     */
    private String equipment;

    /**
     * 资产总计
     */
    private Double totalAssets;

    /**
     * 产权单位
     */
    private String rightsUnit;

    /**
     * 配件属性
     */
    private String kitProperties;

    /**
     * 配件规格
     */
    private String kitStandard;

    /**
     * 臂长(米)
     */
    private Integer brachium;

    /**
     * 折旧率(%)
     */
    private Integer depreciationRate;

    /**
     * 设备型号
     */
    private String unitType;

    /**
     * 计量单位
     */
    private String measurementUnit;

    /**
     * 标准节高度(米)
     */
    private Double standardSectionHeight;

    /**
     * 配件型号
     */
    private String kitModel;

    /**
     * 配件代码
     */
    private String kitCode;

    /**
     * 数量
     */
    private Long amount;

    /**
     * 单价
     */
    private Double singlePrice;

    /**
     * 是否整机配件 0否 1是
     */
    private Integer wholeMachineAccessories;

    /**
     * 防坠器
     */
    private Long protectorId;

    /**
     * 插入人
     */
    private String insertPerson;

    /**
     * 插入人部门id
     */
    private Long insertPersonDepartId;

    /**
     * 更新人
     */
    private String updatePerson;

    /**
     * 更新人部门id
     */
    private Long updatePersonDepartId;

    /**
     * 插入时间
     */
    private Date insertDate;

    /**
     * 更新时间
     */
    private Date updateDate;

    /**
     * 采购日期
     */
    private Date purchaseDate;

    /**
     * 报废日期
     */
    private Date retireDate;

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Date getRetireDate() {
        return retireDate;
    }

    public void setRetireDate(Date retireDate) {
        this.retireDate = retireDate;
    }

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKitName() {
        return kitName;
    }

    public void setKitName(String kitName) {
        this.kitName = kitName;
    }

    public Long getProductNum() {
        return productNum;
    }

    public void setProductNum(Long productNum) {
        this.productNum = productNum;
    }

    public String getVender() {
        return vender;
    }

    public void setVender(String vender) {
        this.vender = vender;
    }

    public String getKitType() {
        return kitType;
    }

    public void setKitType(String kitType) {
        this.kitType = kitType;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public Double getTotalAssets() {
        return totalAssets;
    }

    public void setTotalAssets(Double totalAssets) {
        this.totalAssets = totalAssets;
    }

    public String getRightsUnit() {
        return rightsUnit;
    }

    public void setRightsUnit(String rightsUnit) {
        this.rightsUnit = rightsUnit;
    }

    public String getKitProperties() {
        return kitProperties;
    }

    public void setKitProperties(String kitProperties) {
        this.kitProperties = kitProperties;
    }

    public String getKitStandard() {
        return kitStandard;
    }

    public void setKitStandard(String kitStandard) {
        this.kitStandard = kitStandard;
    }

    public Integer getBrachium() {
        return brachium;
    }

    public void setBrachium(Integer brachium) {
        this.brachium = brachium;
    }

    public Integer getDepreciationRate() {
        return depreciationRate;
    }

    public void setDepreciationRate(Integer depreciationRate) {
        this.depreciationRate = depreciationRate;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public String getMeasurementUnit() {
        return measurementUnit;
    }

    public void setMeasurementUnit(String measurementUnit) {
        this.measurementUnit = measurementUnit;
    }

    public Double getStandardSectionHeight() {
        return standardSectionHeight;
    }

    public void setStandardSectionHeight(Double standardSectionHeight) {
        this.standardSectionHeight = standardSectionHeight;
    }

    public String getKitModel() {
        return kitModel;
    }

    public void setKitModel(String kitModel) {
        this.kitModel = kitModel;
    }

    public String getKitCode() {
        return kitCode;
    }

    public void setKitCode(String kitCode) {
        this.kitCode = kitCode;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Double getSinglePrice() {
        return singlePrice;
    }

    public void setSinglePrice(Double singlePrice) {
        this.singlePrice = singlePrice;
    }

    public Integer getWholeMachineAccessories() {
        return wholeMachineAccessories;
    }

    public void setWholeMachineAccessories(Integer wholeMachineAccessories) {
        this.wholeMachineAccessories = wholeMachineAccessories;
    }

    public Long getProtectorId() {
        return protectorId;
    }

    public void setProtectorId(Long protectorId) {
        this.protectorId = protectorId;
    }

    public String getInsertPerson() {
        return insertPerson;
    }

    public void setInsertPerson(String insertPerson) {
        this.insertPerson = insertPerson;
    }

    public Long getInsertPersonDepartId() {
        return insertPersonDepartId;
    }

    public void setInsertPersonDepartId(Long insertPersonDepartId) {
        this.insertPersonDepartId = insertPersonDepartId;
    }

    public String getUpdatePerson() {
        return updatePerson;
    }

    public void setUpdatePerson(String updatePerson) {
        this.updatePerson = updatePerson;
    }

    public Long getUpdatePersonDepartId() {
        return updatePersonDepartId;
    }

    public void setUpdatePersonDepartId(Long updatePersonDepartId) {
        this.updatePersonDepartId = updatePersonDepartId;
    }

    public Date getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
