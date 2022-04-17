package com.ConstructionManagement.system.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.handler.inter.IExcelDataModel;
import cn.afterturn.easypoi.handler.inter.IExcelModel;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
/**
 * out_stock_apply
 * @author
 */

public class OutStockApply implements Serializable, IExcelDataModel, IExcelModel {
    /**
     * 行号
     */
    private int rowNum;

    /**
     * 错误消息
     */
    private String errorMsg;

    private Long id;

    /**
     * 名称
     */
    @Excel(name = "名称",width = 20)
    @NotNull(message="名称不能为空")
    private String name;

    /**
     * 类别 1设备 2 配件 3 部件 4设备配件 5设备部件 6部件配件
     */
    @Excel(name = "类别",width = 20,replace = {"设备_1","配件_2","部件_3"})
    @NotNull(message="类别不能为空")
    private Integer type;

    /**
     * 型号
     */
    @Excel(name = "型号",width = 20)
    @NotNull(message="型号不能为空")
    private String model;

    /**
     * 申请发货数量
     */
    @Excel(name = "申请发货数量",width = 30)
    @Min(value=0,message="发货数量最小为0")
    private Long amount;

    /**
     * 申请日期
     */
    @Excel(name = "申请日期",width = 30,importFormat = "yyyy-MM-dd",exportFormat = "yyyy-MM-dd")
    private Date applyDate;

    /**
     * 申请人
     */
    @Excel(name = "申请人",width = 30)
    @Length(max =20)
    @NotNull(message="申请人不能为空")
    private String applicant;

    /**
     * 是否出库 0 否 1 是
     */
    @Excel(name = "是否出库",replace={"否_0","是_1"})
    private Integer status;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
