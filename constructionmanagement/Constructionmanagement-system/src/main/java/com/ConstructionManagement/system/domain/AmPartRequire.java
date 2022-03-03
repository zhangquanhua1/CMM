package com.ConstructionManagement.system.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * am_part_require
 * @author
 */
@Data
public class AmPartRequire implements Serializable {
    private Long id;

    /**
     * 产品编号
     */
    private Long productNum;

    /**
     * 生产厂家
     */
    private String vender;

    /**
     * 部件类别
     */
    private String partType;

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
     * 部件名称
     */
    private String partName;

    /**
     * 配件规格
     */
    private String kitStandard;

    /**
     * 臂长(米)
     */
    private Integer brachium;

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
     * 部件型号
     */
    private String partModel;

    /**
     * 部件代码
     */
    private String partCode;

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
     * 需求发起人
     */
    private String demandSponsors;

    /**
     * 采购原因
     */
    private String procurementCauses;

    /**
     * 审核状态 0代表未审核 1 审核 通过 其他审核未通过
     */
    private Integer state;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getAuditAdvice() {
        return auditAdvice;
    }

    public void setAuditAdvice(String auditAdvice) {
        this.auditAdvice = auditAdvice;
    }

    /**
     * 审核意见
     */
    private String auditAdvice;

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



    List<AmPartRequireKit>  amPartRequireKits;
    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPartType() {
        return partType;
    }

    public void setPartType(String partType) {
        this.partType = partType;
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

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
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

    public String getPartModel() {
        return partModel;
    }

    public void setPartModel(String partModel) {
        this.partModel = partModel;
    }

    public String getPartCode() {
        return partCode;
    }

    public void setPartCode(String partCode) {
        this.partCode = partCode;
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

    public String getDemandSponsors() {
        return demandSponsors;
    }

    public void setDemandSponsors(String demandSponsors) {
        this.demandSponsors = demandSponsors;
    }

    public String getProcurementCauses() {
        return procurementCauses;
    }

    public void setProcurementCauses(String procurementCauses) {
        this.procurementCauses = procurementCauses;
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

    public List<AmPartRequireKit> getAmPartRequireKits() {
        return amPartRequireKits;
    }

    public void setAmPartRequireKits(List<AmPartRequireKit> amPartRequireKits) {
        this.amPartRequireKits = amPartRequireKits;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
