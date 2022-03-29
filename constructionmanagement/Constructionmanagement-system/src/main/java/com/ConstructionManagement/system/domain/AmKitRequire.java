package com.ConstructionManagement.system.domain;

import com.ConstructionManagement.common.annotation.Excel;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Date;


/**
 * am_kit_require
 * @author
 */

public class AmKitRequire implements Serializable {
    private Long id;

    /**
     * 零配件名称
     */
    @Excel(name = "配件名称")
    private String kitName;

    /**
     * 产品编号
     */
    @Excel(name="产品编号")
    @Length(min=0,max=50,message="产品编号不能超过50个字符")
    private String productNum;

    /**
     * 生产厂家
     */
    @Excel(name="生产厂家")
    private String vender;

    /**
     * 零配件类别
     */
    @Excel(name="适用部件类别")
    private String kitType;

    /**
     * 所属设备
     */
    @Excel(name="所属设备")
    private String equipment;

    /**
     * 资产总计
     */
    @Excel(name="资产总计")
    private Double totalAssets;

    /**
     * 产权单位
     */
    @Excel(name="产权单位")
    private String rightsUnit;

    /**
     * 配件属性
     */
    @Excel(name="配件属性")
    private String kitProperties;

    /**
     * 配件规格
     */
    @Excel(name="配件规格")
    private String kitStandard;

    /**
     * 臂长(米)
     */
    @Excel(name="臂长",suffix="米")
    @Min(0)
    private Integer brachium;

    /**
     * 设备型号
     */
    @Excel(name="设备型号")
    private String unitType;

    /**
     * 计量单位
     */
    @Excel(name="计量单位")
    private String measurementUnit;

    /**
     * 标准节高度(米)
     */
    @Excel(name="标准节高度",suffix="米")
    @Min(0)
    private Double standardSectionHeight;

    /**
     * 配件型号
     */
    @Excel(name="配件型号")
    private String kitModel;

    /**
     * 配件代码
     */
    @Excel(name="配件代码")
    private String kitCode;

    /**
     * 数量
     */
    @Excel(name="配件数量")
    @Min(value=0,message="配件数量要为数值，最小为0")
    private Long amount;

    /**
     * 单价
     */
    @Excel(name="单价")
    @Min(value=0,message="单价要为数值，最小为0")
    private Double singlePrice;

    /**
     * 是否整机配件 0否 1是
     */
    @Excel(name="是否整机配件",readConverterExp = "0=否,1=是", prompt="0否，1是")
    private Integer wholeMachineAccessories;

    /**
     * 需求发起人
     */
    @Excel(name="需求发起人")
    private String demandSponsors;

    /**
     * 发起日期
     */
    @Excel(name="发起日期",dateFormat = "yyyy-MM-dd", type = Excel.Type.ALL)
    private Date initDate;

    /**
     * 采购原因
     */
    @Excel(name="采购原因")
    private String procurementCauses;

    /**
     * 审核状态 0代表未审核 1 审核 通过 其他审核未通过
     */
    @Excel(name="审核状态",readConverterExp = "0=未审核,1=通过,2=未通过",type = Excel.Type.EXPORT)
    private Integer state;

    /**
     * 审核意见
     */
    @Excel(name = "审核意见",type = Excel.Type.EXPORT)
    private String auditAdvice;

    /**
     * 归属仓库
     */
    @Excel(name = "归属仓库")
    private String belongWarehouse;

    public String getBelongWarehouse() {
        return belongWarehouse;
    }

    public void setBelongWarehouse(String belongWarehouse) {
        this.belongWarehouse = belongWarehouse;
    }

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
     * 是否已采购 0 待采购 1已采购
     */
    @Excel(name = "是否已采购",readConverterExp = "0=待采购,1=已采购",type = Excel.Type.EXPORT)
    private Integer isBuy;

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

    public String getProductNum() {
        return productNum;
    }

    public void setProductNum(String productNum) {
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

    public String getDemandSponsors() {
        return demandSponsors;
    }

    public void setDemandSponsors(String demandSponsors) {
        this.demandSponsors = demandSponsors;
    }

    public Date getInitDate() {
        return initDate;
    }

    public void setInitDate(Date initDate) {
        this.initDate = initDate;
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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

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

    public Integer getIsBuy() {
        return isBuy;
    }

    public void setIsBuy(Integer isBuy) {
        this.isBuy = isBuy;
    }
}
