package com.ConstructionManagement.system.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.handler.inter.IExcelDataModel;
import cn.afterturn.easypoi.handler.inter.IExcelModel;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * wm_part_entry
 *
 * @author
 */

public class WmPartEntry implements Serializable, IExcelDataModel, IExcelModel {
    /**
     * 行号
     */
    private int rowNum;

    /**
     * 错误消息
     */
    private String errorMsg;

    //isColumnHidden表示此栏是否在Excel表里面展示
    //@Excel(name = "id",isColumnHidden = true,needMerge = true)
    private Long id;

    /**
     * 产品编号
     */
    @Excel(name = "产品编号",width = 20,needMerge = true)
    @NotNull(message="产品编号不能为空")
    private String productNum;

    /**
     * 生产厂家
     */
    @Excel(name = "生产厂家",width = 20,needMerge = true)
    private String vender;

    /**
     * 部件类别
     */
    @Excel(name = "部件类别",width = 20,needMerge = true)
    private String partType;

    /**
     * 所属设备
     */
    @Excel(name = "所属设备",width = 20,needMerge = true)
    private String equipment;

    /**
     * 资产总计
     */
    @Excel(name = "资产总计",width = 20,needMerge = true)
    private Double totalAssets;

    /**
     * 产权单位
     */
    @Excel(name = "产权单位",width = 20,needMerge = true)
    private String rightsUnit;

    /**
     * 配件属性
     */
    @Excel(name = "配件属性",width = 20,needMerge = true)
    private String kitProperties;

    /**
     * 部件名称
     */
    @Excel(name = "部件名称",width = 20,needMerge = true)
    private String partName;

    /**
     * 配件规格
     */
    @Excel(name = "配件规格",width = 20,needMerge = true)
    private String kitStandard;

    /**
     * 臂长(米)
     */
    @Excel(name = "臂长",suffix = "米",width = 20,needMerge = true)
    private Integer brachium;

    /**
     * 设备型号
     */
    @Excel(name = "设备型号",width = 20,needMerge = true)
    private String unitType;

    /**
     * 计量单位
     */
    @Excel(name = "计量单位",width = 20,needMerge = true)
    private String measurementUnit;

    /**
     * 标准节高度(米)
     */
    @Excel(name = "标准节高度",suffix="米",width = 20,needMerge = true)
    private Double standardSectionHeight;

    /**
     * 部件型号
     */
    @Excel(name = "部件型号",width = 20,needMerge = true)
    private String partModel;

    /**
     * 部件代码
     */
    @Excel(name = "部件代码",width = 20,needMerge = true)
    private String partCode;

    /**
     * 数量
     */
    @Excel(name = "数量",width = 20,needMerge = true)
    private Long amount;

    /**
     * 单价
     */
    @Excel(name = "单价",width = 20,needMerge = true)
    private Double singlePrice;

    /**
     * 是否整机配件 0否 1是
     */
    @Excel(name = "是否整机配件",replace={"否_0","是_1"},width = 20,needMerge = true)
    private Integer wholeMachineAccessories;

    /**
     * 需求发起人
     */
    @Excel(name = "需求发起人",width = 20,needMerge = true)
    private String demandSponsors;

    /**
     * 采购原因
     */
    @Excel(name = "采购原因",width = 20,needMerge = true)
    private String procurementCauses;

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
     * 归属仓库
     */
    @Excel(name = "归属仓库",width = 20,needMerge = true)
    private String belongWarehouse;

    /**
     * 确认状态 0 未确认 1 确认 2 反确认
     */
    @Excel(name = "确认状态",replace = {"未确认_0","确认_1","反确认_2"},width = 20,needMerge = true)
    private Integer status;

    /**
     * 录入批次号
     */
    @Excel(name = "录入批次号",width = 20,needMerge = true)
    private String batchNumber;

    /**
     * 供应商
     */
    @Excel(name = "供应商",width = 20,needMerge = true)
    private String supplierId;

    /**
     * 经办人
     */
    @Excel(name = "经办人",width = 20,needMerge = true)
    private String practiName;

    /**
     * 供应商联系地址
     */
    @Excel(name = "供应商联系地址",width = 30,needMerge = true)
    private String supplierAdd;

    /**
     * 供应商联系电话
     */
    @Excel(name = "供应商联系电话",width = 30,needMerge = true)
    private String supplierTel;

    /**
     * 经办人员电话
     */
    @Excel(name = "经办人员电话",width = 20,needMerge = true)
    private String mobile;

    /**
     * 附件
     */
    private String enclosure;
    @ExcelCollection(name = "配件清单")
    List<WmPartEntryKit> kits;
    private static final long serialVersionUID = 1L;

    public List<WmPartEntryKit> getKits() {
        return kits;
    }

    public void setKits(List<WmPartEntryKit> kits) {
        this.kits = kits;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getBelongWarehouse() {
        return belongWarehouse;
    }

    public void setBelongWarehouse(String belongWarehouse) {
        this.belongWarehouse = belongWarehouse;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getPractiName() {
        return practiName;
    }

    public void setPractiName(String practiName) {
        this.practiName = practiName;
    }

    public String getSupplierAdd() {
        return supplierAdd;
    }

    public void setSupplierAdd(String supplierAdd) {
        this.supplierAdd = supplierAdd;
    }

    public String getSupplierTel() {
        return supplierTel;
    }

    public void setSupplierTel(String supplierTel) {
        this.supplierTel = supplierTel;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEnclosure() {
        return enclosure;
    }

    public void setEnclosure(String enclosure) {
        this.enclosure = enclosure;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
    @Override
    public Integer getRowNum() {
        return this.rowNum;
    }

    @Override
    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }

    @Override
    public String getErrorMsg() {
        return this.errorMsg;
    }

    @Override
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
