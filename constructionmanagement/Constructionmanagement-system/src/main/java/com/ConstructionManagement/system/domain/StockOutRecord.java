package com.ConstructionManagement.system.domain;

import java.io.Serializable;
import java.util.Date;

import com.ConstructionManagement.common.annotation.Excel;


/**
 * stock_out_record
 * @author
 */
public class StockOutRecord implements Serializable {
    private Long id;

    /**
     * 名称
     */
    @Excel(name="名称")
    private String name;

    /**
     * 类别 1设备 2 配件 3 部件 4设备配件 5设备部件 6部件配件
     */
    /** 用户性别 */
    @Excel(name = "类别", readConverterExp = "1=设备,2=配件,3=部件,4=配件,5=部件,6=配件")
    private Integer type;

    /**
     * 型号
     */
    @Excel(name="型号")
    private String model;
    @Excel(name="数量")
    private Long amount;

    /**
     * 归属仓库
     */
    @Excel(name="归属仓库")
    private String belongWarehouse;

    /**
     * 记录 该记录的详细信息
     */
    private Long typeId;

    /**
     * 记录所属设备或部件的 id
     */
    private Long pid;

    /**
     * 出库日期
     */
    @Excel(name="出库日期",dateFormat = "yyyy-MM-dd", type = Excel.Type.EXPORT)
    private Date begindate;

    /**
     * 入库的日期
     */
    private Date backdate;

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

    public String getBelongWarehouse() {
        return belongWarehouse;
    }

    public void setBelongWarehouse(String belongWarehouse) {
        this.belongWarehouse = belongWarehouse;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Date getBegindate() {
        return begindate;
    }

    public void setBegindate(Date begindate) {
        this.begindate = begindate;
    }

    public Date getBackdate() {
        return backdate;
    }

    public void setBackdate(Date backdate) {
        this.backdate = backdate;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
