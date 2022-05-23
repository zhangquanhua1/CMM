package com.ConstructionManagement.system.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * inventory
 * @author
 */

public class Inventory implements Serializable {
    private Long id;

    /**
     * 名称
     */
    @Excel(name = "名称", width = 20)
    private String name;

    /**
     * 类别 0设备 1 配件 2 部件
     */
    @Excel(name = "类别",replace ={"设备_1","配件_2","部件_3","配件_4","部件_5","配件_6"}, width = 20)
    private Integer type;

    /**
     * 型号
     */
    @Excel(name = "型号", width = 20)
    private String model;
    @Excel(name = "数量", width = 20)
    private Long amount;

    /**
     * 归属仓库
     */
    @Excel(name = "所属仓库", width = 20)
    private String belongWarehouse;

    /**
     * 记录 该记录的详细信息
     */
    private Long typeId;
    /**
     * 记录所属设备或部件的 id
     */
    private Long pid;

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "Inventory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", model='" + model + '\'' +
                ", amount=" + amount +
                ", belongWarehouse='" + belongWarehouse + '\'' +
                ", typeId=" + typeId +
                ", pid=" + pid +
                '}';
    }
}
