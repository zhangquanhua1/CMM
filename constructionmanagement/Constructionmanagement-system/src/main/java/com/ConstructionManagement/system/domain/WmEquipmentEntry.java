package com.ConstructionManagement.system.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.handler.inter.IExcelDataModel;
import cn.afterturn.easypoi.handler.inter.IExcelModel;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * wm_equipment_entry
 *
 * @author
 */

public class WmEquipmentEntry implements Serializable, IExcelDataModel, IExcelModel {

    /**
     * 行号
     */
    private int rowNum;

    /**
     * 错误消息
     */
    private String errorMsg;
    //isColumnHidden表示此栏是否在Excel表里面展示
    // @Excel(name = "id", isColumnHidden = true, needMerge = true)
    private Long id;

    /**
     * 设备名称
     */
    //isColumnHidden表示此栏是否在Excel表里面展示
    @Excel(name = "设备名称", width = 20, needMerge = true)
    private String equipmentName;

    /**
     * 设备编号
     */
    @Excel(name = "设备编号", width = 40, needMerge = true)
    @Length(min = 0, max = 50, message = "设备编号不能超过50个字符长度")
    private String equipmentNumber;

    /**
     * 设备自编号
     */
    @Excel(name = "设备自编号", width = 40, needMerge = true)
    @Length(min = 0, max = 50, message = "设备编号不能超过50个字符长度")
    private String equipmentSelfNumber;

    /**
     * 规格型号
     */
    @Excel(name = "规格型号", width = 20, needMerge = true)
    private String standardModel;

    /**
     * 生产厂家
     */
    @Excel(name = "生产厂家", width = 20, needMerge = true)
    private String vender;

    /**
     * 设备类别
     */
    @Excel(name = "设备类别", width = 20, needMerge = true)
    private String equipmentType;

    /**
     * 产权单位
     */
    @Excel(name = "产权单位", width = 20, needMerge = true)
    private String rightsUnit;

    /**
     * 备案编号
     */
    @Excel(name = "备案编号", width = 20, needMerge = true)
    private String recordNumber;

    /**
     * 归属仓库
     */
    @Excel(name = "归属仓库", width = 20, needMerge = true)
    private String belongWarehouse;

    /**
     * 项目地址
     */
    @Excel(name = "项目地址", width = 20, needMerge = true)
    private String projectAddress;

    /**
     * 是否融资租赁 0 否 1是
     */
    @Excel(name = "是否融资租赁", replace = {"否_0", "是_1"}, width = 20, needMerge = true)
    private Integer financeLease;

    /**
     * 发票号
     */
    @Excel(name = "发票号", width = 20, needMerge = true)
    @Length(min = 0, max = 50, message = "发票号不能超过50个字符")
    private String invoiceNumber;

    /**
     * 购买合同
     */
    @Excel(name = "购买合同", width = 20, needMerge = true)
    private String purchaseContract;

    /**
     * 制造许可证号
     */
    @Excel(name = "制造许可证号", width = 20, needMerge = true)
    private String manufacturingLicenseNo;

    /**
     * 出厂日期
     */
    @Excel(name = "出厂日期", exportFormat = "yyyy-MM-dd", width = 20, needMerge = true)
    private Date productDate;

    /**
     * 采购日期
     */
    @Excel(name = "采购日期", exportFormat = "yyyy-MM-dd", width = 20, needMerge = true)
    private Date purchaseDate;

    /**
     * 报废日期
     */
    @Excel(name = "报废日期", exportFormat = "yyyy-MM-dd", width = 20, needMerge = true)
    private Date retireDate;

    /**
     * 单位责任人
     */
    @Excel(name = "单位负责人", width = 20, needMerge = true)
    private String unitResponsiblePerson;

    /**
     * 所属部门
     */
    @Excel(name = "所属部门", width = 20, needMerge = true)
    private String department;

    /**
     * 楼号
     */
    @Excel(name = "楼号", width = 20, needMerge = true)
    @Min(0L)
    private Integer buildingNo;

    /**
     * 设备来源
     */
    @Excel(name = "设备来源", width = 20, needMerge = true)
    private String equipmentSource;

    /**
     * 登记人
     */
    @Excel(name = "登记人", width = 20, needMerge = true)
    private String registrant;

    /**
     * 确认人
     */
    @Excel(name = "确认人", width = 20, needMerge = true)
    private String confirmPerson;

    /**
     * 开票时间
     */
    @Excel(name = "开票时间", exportFormat = "yyyy-MM-dd HH:mm:ss", width = 20, needMerge = true)
    private Date invoiceTime;

    /**
     * 融资到期日期
     */
    @Excel(name = "融资到期日期", exportFormat = "yyyy-MM-dd", width = 20, needMerge = true)
    private Date financingMaturityDate;

    /**
     * 出厂编号
     */
    @Excel(name = "出厂编号", width = 20, needMerge = true)
    private String manufacturingNo;

    /**
     * 生产厂家
     */
    @Excel(name = "生产厂家", width = 20, needMerge = true)
    private String manufacturer;

    /**
     * 合同单价(元)
     */
    @Excel(name = "合同单价", suffix = "元", width = 20, needMerge = true)
    @Min(0)
    private Long contractUnitPrice;

    /**
     * 数量
     */
    @Min(0)
    @Excel(name = "数量", width = 20, needMerge = true)
    private Long amount;

    /**
     * 税率
     */
    @Excel(name = "税率", suffix = "%", width = 20, needMerge = true)
    @Min(0)
    @Max(100)
    private Integer rate;

    /**
     * 不含税价(元)
     */
    @Excel(name = "不含税价", suffix = "元", width = 20, needMerge = true)
    @Min(0)
    private Long exclusiveTaxPrice;

    /**
     * 税金(元)
     */
    @Excel(name = "税金", suffix = "元", width = 20, needMerge = true)
    @Min(0)
    private Long taxation;

    /**
     * 折旧率(%)
     */
    @Excel(name = "折旧率", suffix = "%", width = 20, needMerge = true)
    @Min(0)
    @Max(100)
    private Integer depreciationRate;

    /**
     * 联系电话
     */
    @Excel(name = "联系电话", width = 20, needMerge = true)
    @Pattern(regexp = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$", message = "手机号非法")
    private String contactNumber;

    /**
     * 登记时间
     */
    @Excel(name = "登记日期", exportFormat = "yyyy-MM-dd", width = 20, needMerge = true)
    private Date registrationTime;

    /**
     * 确认时间
     */
    @Excel(name = "确认时间", exportFormat = "yyyy-MM-dd", width = 20, needMerge = true)
    private Date acknowledgingTime;

    /**
     * 融资批次
     */
    @Excel(name = "融资批次", width = 20, needMerge = true)
    private String financingBatch;

    /**
     * 融资金额
     */
    @Excel(name = "融资金额", suffix = "元", width = 20, needMerge = true)
    @Min(0)
    private Long financingAmount;

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
     * 确认状态 0 未确认 1 确认 2 反确认
     */
    @Excel(name = "确认状态", replace = {"未确认_0", "确认_1", "反确认_2"}, width = 20, needMerge = true)
    private Integer status;
    //设备录入配件
    @ExcelCollection(name = "配件清单")
    private List<WmEquipmentEntryKit> wmEquipmentEntryKits;
    //设备录入部件
    @ExcelCollection(name = "部件清单")
    private List<WmEquipmentEntryPart> wmEquipmentEntryParts;

    public List<WmEquipmentEntryKit> getWmEquipmentEntryKits() {
        return wmEquipmentEntryKits;
    }

    public void setWmEquipmentEntryKits(List<WmEquipmentEntryKit> wmEquipmentEntryKits) {
        this.wmEquipmentEntryKits = wmEquipmentEntryKits;
    }

    public List<WmEquipmentEntryPart> getWmEquipmentEntryParts() {
        return wmEquipmentEntryParts;
    }

    public void setWmEquipmentEntryParts(List<WmEquipmentEntryPart> wmEquipmentEntryParts) {
        this.wmEquipmentEntryParts = wmEquipmentEntryParts;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getEquipmentNumber() {
        return equipmentNumber;
    }

    public void setEquipmentNumber(String equipmentNumber) {
        this.equipmentNumber = equipmentNumber;
    }

    public String getEquipmentSelfNumber() {
        return equipmentSelfNumber;
    }

    public void setEquipmentSelfNumber(String equipmentSelfNumber) {
        this.equipmentSelfNumber = equipmentSelfNumber;
    }

    public String getStandardModel() {
        return standardModel;
    }

    public void setStandardModel(String standardModel) {
        this.standardModel = standardModel;
    }

    public String getVender() {
        return vender;
    }

    public void setVender(String vender) {
        this.vender = vender;
    }

    public String getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(String equipmentType) {
        this.equipmentType = equipmentType;
    }

    public String getRightsUnit() {
        return rightsUnit;
    }

    public void setRightsUnit(String rightsUnit) {
        this.rightsUnit = rightsUnit;
    }

    public String getRecordNumber() {
        return recordNumber;
    }

    public void setRecordNumber(String recordNumber) {
        this.recordNumber = recordNumber;
    }

    public String getBelongWarehouse() {
        return belongWarehouse;
    }

    public void setBelongWarehouse(String belongWarehouse) {
        this.belongWarehouse = belongWarehouse;
    }

    public String getProjectAddress() {
        return projectAddress;
    }

    public void setProjectAddress(String projectAddress) {
        this.projectAddress = projectAddress;
    }

    public Integer getFinanceLease() {
        return financeLease;
    }

    public void setFinanceLease(Integer financeLease) {
        this.financeLease = financeLease;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getPurchaseContract() {
        return purchaseContract;
    }

    public void setPurchaseContract(String purchaseContract) {
        this.purchaseContract = purchaseContract;
    }

    public String getManufacturingLicenseNo() {
        return manufacturingLicenseNo;
    }

    public void setManufacturingLicenseNo(String manufacturingLicenseNo) {
        this.manufacturingLicenseNo = manufacturingLicenseNo;
    }

    public Date getProductDate() {
        return productDate;
    }

    public void setProductDate(Date productDate) {
        this.productDate = productDate;
    }

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

    public String getUnitResponsiblePerson() {
        return unitResponsiblePerson;
    }

    public void setUnitResponsiblePerson(String unitResponsiblePerson) {
        this.unitResponsiblePerson = unitResponsiblePerson;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Integer getBuildingNo() {
        return buildingNo;
    }

    public void setBuildingNo(Integer buildingNo) {
        this.buildingNo = buildingNo;
    }

    public String getEquipmentSource() {
        return equipmentSource;
    }

    public void setEquipmentSource(String equipmentSource) {
        this.equipmentSource = equipmentSource;
    }

    public String getRegistrant() {
        return registrant;
    }

    public void setRegistrant(String registrant) {
        this.registrant = registrant;
    }

    public String getConfirmPerson() {
        return confirmPerson;
    }

    public void setConfirmPerson(String confirmPerson) {
        this.confirmPerson = confirmPerson;
    }

    public Date getInvoiceTime() {
        return invoiceTime;
    }

    public void setInvoiceTime(Date invoiceTime) {
        this.invoiceTime = invoiceTime;
    }

    public Date getFinancingMaturityDate() {
        return financingMaturityDate;
    }

    public void setFinancingMaturityDate(Date financingMaturityDate) {
        this.financingMaturityDate = financingMaturityDate;
    }

    public String getManufacturingNo() {
        return manufacturingNo;
    }

    public void setManufacturingNo(String manufacturingNo) {
        this.manufacturingNo = manufacturingNo;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Long getContractUnitPrice() {
        return contractUnitPrice;
    }

    public void setContractUnitPrice(Long contractUnitPrice) {
        this.contractUnitPrice = contractUnitPrice;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Long getExclusiveTaxPrice() {
        return exclusiveTaxPrice;
    }

    public void setExclusiveTaxPrice(Long exclusiveTaxPrice) {
        this.exclusiveTaxPrice = exclusiveTaxPrice;
    }

    public Long getTaxation() {
        return taxation;
    }

    public void setTaxation(Long taxation) {
        this.taxation = taxation;
    }

    public Integer getDepreciationRate() {
        return depreciationRate;
    }

    public void setDepreciationRate(Integer depreciationRate) {
        this.depreciationRate = depreciationRate;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Date getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(Date registrationTime) {
        this.registrationTime = registrationTime;
    }

    public Date getAcknowledgingTime() {
        return acknowledgingTime;
    }

    public void setAcknowledgingTime(Date acknowledgingTime) {
        this.acknowledgingTime = acknowledgingTime;
    }

    public String getFinancingBatch() {
        return financingBatch;
    }

    public void setFinancingBatch(String financingBatch) {
        this.financingBatch = financingBatch;
    }

    public Long getFinancingAmount() {
        return financingAmount;
    }

    public void setFinancingAmount(Long financingAmount) {
        this.financingAmount = financingAmount;
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
