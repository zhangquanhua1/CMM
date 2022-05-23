package com.ConstructionManagement.system.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.handler.inter.IExcelDataModel;
import cn.afterturn.easypoi.handler.inter.IExcelModel;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.util.Date;
import java.util.List;


/**
 * am_equipment_require
 *
 * @author
 */

public class AmEquipmentRequire implements Serializable, IExcelDataModel, IExcelModel {
    /**
     * 行号
     */
    private int rowNum;

    /**
     * 错误消息
     */
    private String errorMsg;
   // @Excel(name = "id", isColumnHidden = true, needMerge = true)
    private Long id;

    /**
     * 生产厂家
     */
    @Excel(name = "生产厂家", width = 20, needMerge = true)
    private String vender;

    /**
     * 设备编号
     */
    @Excel(name = "产品编号", width = 20, needMerge = true)
    @NotNull(message = "产品编号不能为空")
    @Length(min = 0, max = 50, message = "设备编号的长度不能超过50个字符")
    private String equipmentNumber;

    /**
     * 设备名称
     */
    @Excel(name = "设备名称", width = 20, needMerge = true)
    private String equipmentName;

    /**
     * 设备型号
     */
    @Excel(name = "设备型号", width = 20, needMerge = true)
    private String standardModel;

    /**
     * 设备类别
     */
    @Excel(name = "设备类别", width = 20, needMerge = true)
    private String equipmentType;

    /**
     * 设备数量
     */
    @Excel(name = "设备数量", width = 20, needMerge = true)
    @Min(0)
    private Long equipmentCount;

    /**
     * 产权单位
     */
    @Excel(name = "产权单位", width = 20, needMerge = true)
    private String rightsUnit;

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
     * 项目日期
     */
    @Excel(name = "项目日期", exportFormat = "yyyy-MM-dd", width = 20, needMerge = true)
    private Date projectDate;

    /**
     * 发起日期
     */
    @Excel(name = "发起日期", exportFormat = "yyyy-MM-dd", width = 20, needMerge = true)
    private Date initDate;

    /**
     * 需求发起人
     */
    @Excel(name = "需求发起人", width = 20, needMerge = true)
    private String demandSponsors;

    /**
     * 采购原因
     */
    @Excel(name = "采购原因", width = 20, needMerge = true)
    private String procurementCauses;

    /**
     * 审核状态 0代表未审核 1 审核 通过 其他审核未通过
     */
    @Excel(name = "审核状态", replace = {"未审核_0", "通过_1", "未通过_2"}, width = 20, needMerge = true)
    private Integer state;

    /**
     * 零配件
     */
    private List<AmPartParamKit> amPartParamKits;
    /**
     * 部件
     */
    private List<AmTowerMachineParamPart> amTowerMachineParamParts;

    //设备需求配件
    @ExcelCollection(name = "配件清单")
    private List<AmEquipmentRequireKit> amEquipmentRequireKits;
    //设备需求部件
    @ExcelCollection(name = "部件清单")
    private List<AmEquipmentRequirePart> amEquipmentRequireParts;

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
    /**
     * 0 未购 1 已购
     */
    @Excel(name = "是否已采购", replace = {"待采购_0", "已采购_1"}, width = 20, needMerge = true)
    private Integer isBuy;

    /**
     * 审核时间
     */
    private Date auditDate;
    /**
     * 采购时间
     */
    private Date buyDate;

    public Date getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(Date auditDate) {
        this.auditDate = auditDate;
    }

    public Date getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Date buyDate) {
        this.buyDate = buyDate;
    }
    private static final long serialVersionUID = 1L;

    public List<AmEquipmentRequireKit> getAmEquipmentRequireKits() {
        return amEquipmentRequireKits;
    }

    public void setAmEquipmentRequireKits(List<AmEquipmentRequireKit> amEquipmentRequireKits) {
        this.amEquipmentRequireKits = amEquipmentRequireKits;
    }

    public List<AmEquipmentRequirePart> getAmEquipmentRequireParts() {
        return amEquipmentRequireParts;
    }

    public void setAmEquipmentRequireParts(List<AmEquipmentRequirePart> amEquipmentRequireParts) {
        this.amEquipmentRequireParts = amEquipmentRequireParts;
    }

    @Override
    public Integer getRowNum() {
        return rowNum;
    }

    @Override
    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }

    @Override
    public String getErrorMsg() {
        return errorMsg;
    }

    @Override
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Integer getIsBuy() {
        return isBuy;
    }

    public void setIsBuy(Integer isBuy) {
        this.isBuy = isBuy;
    }

    public List<AmPartParamKit> getAmPartParamKits() {
        return amPartParamKits;
    }

    public void setAmPartParamKits(List<AmPartParamKit> amPartParamKits) {
        this.amPartParamKits = amPartParamKits;
    }

    public List<AmTowerMachineParamPart> getAmTowerMachineParamParts() {
        return amTowerMachineParamParts;
    }

    public void setAmTowerMachineParamParts(List<AmTowerMachineParamPart> amTowerMachineParamParts) {
        this.amTowerMachineParamParts = amTowerMachineParamParts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVender() {
        return vender;
    }

    public void setVender(String vender) {
        this.vender = vender;
    }

    public String getEquipmentNumber() {
        return equipmentNumber;
    }

    public void setEquipmentNumber(String equipmentNumber) {
        this.equipmentNumber = equipmentNumber;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getStandardModel() {
        return standardModel;
    }

    public void setStandardModel(String standardModel) {
        this.standardModel = standardModel;
    }

    public String getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(String equipmentType) {
        this.equipmentType = equipmentType;
    }

    public Long getEquipmentCount() {
        return equipmentCount;
    }

    public void setEquipmentCount(Long equipmentCount) {
        this.equipmentCount = equipmentCount;
    }

    public String getRightsUnit() {
        return rightsUnit;
    }

    public void setRightsUnit(String rightsUnit) {
        this.rightsUnit = rightsUnit;
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

    public Date getProjectDate() {
        return projectDate;
    }

    public void setProjectDate(Date projectDate) {
        this.projectDate = projectDate;
    }

    public Date getInitDate() {
        return initDate;
    }

    public void setInitDate(Date initDate) {
        this.initDate = initDate;
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

    public static long getSerialVersionUID() {
        return serialVersionUID;
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
}
