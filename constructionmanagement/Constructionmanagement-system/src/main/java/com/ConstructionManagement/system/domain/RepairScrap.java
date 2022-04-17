package com.ConstructionManagement.system.domain;

import com.ConstructionManagement.common.annotation.Excel;

import java.io.Serializable;
import java.util.Date;

/**
 * repair_scrap
 * @author
 */

public class RepairScrap implements Serializable {
    private Long repairId;

    /**
     * 名称
     */
    @Excel(name = "名称")
    private String name;

    /**
     * 型号
     */
    @Excel(name = "型号")
    private String model;

    /**
     * 类别 1设备 2 配件 3 部件 4设备配件 5设备部件 6部件配件
     */
    @Excel(name = "类别", readConverterExp = "1=设备,2=配件,3=部件,4=配件,5=部件,6=配件")
    private Integer type;

    /**
     * 申请类型 0维修 1报废
     */
    @Excel(name = "申请类型", readConverterExp = "0=维修,1=报废")
    private Integer applyType;

    /**
     * 数量
     */
    @Excel(name = "数量")
    private Integer amount;

    /**
     * 计量单位
     */
    @Excel(name = "计量单位")
    private String measurementUnit;

    /**
     * 申请人
     */
    @Excel(name = "申请人")
    private String applicant;

    /**
     * 维修原因
     */
    @Excel(name = "维修原因")
    private String reason;

    /**
     * 审核状态 0未审核 1通过 2 拒绝
     */
    @Excel(name = "审核状态", readConverterExp = "0=未审核,1=通过,2=拒绝")
    private Integer status;

    /**
     * 审核意见
     */
    @Excel(name = "审核意见")
    private String auditAdvice;

    /**
     * 申请日期
     */
    @Excel(name="申请日期",dateFormat = "yyyy-MM-dd", type = Excel.Type.EXPORT)
    private Date applyDate;

    private static final long serialVersionUID = 1L;

    public Long getRepairId() {
        return repairId;
    }

    public void setRepairId(Long repairId) {
        this.repairId = repairId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getApplyType() {
        return applyType;
    }

    public void setApplyType(Integer applyType) {
        this.applyType = applyType;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getMeasurementUnit() {
        return measurementUnit;
    }

    public void setMeasurementUnit(String measurementUnit) {
        this.measurementUnit = measurementUnit;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAuditAdvice() {
        return auditAdvice;
    }

    public void setAuditAdvice(String auditAdvice) {
        this.auditAdvice = auditAdvice;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
