package com.ConstructionManagement.system.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;

import java.io.Serializable;

/**
 * am_tower_machine_param_part
 * @author
 */
public class AmTowerMachineParamPart implements Serializable {
    @Excel(name = "id", isColumnHidden = true,needMerge = true)
    private Long id;
    //塔机id
    @Excel(name = "pid", isColumnHidden = true, needMerge = true)
    private Long pid;
    //部件id
   // @Excel(name = "part_id", isColumnHidden = true)
    private Long part_id;
    //部件数量
    @Excel(name = "部件数量", width = 20, needMerge = true)
    private Integer partCount;
    private static final long serialVersionUID = 1L;
    @ExcelEntity
    AmPartParam amPartParam;

    public AmPartParam getAmPartParam() {
        return amPartParam;
    }

    public void setAmPartParam(AmPartParam amPartParam) {
        this.amPartParam = amPartParam;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Long getPart_id() {
        return part_id;
    }

    public void setPart_id(Long part_id) {
        this.part_id = part_id;
    }

    public Integer getPartCount() {
        return partCount;
    }

    public void setPartCount(Integer partCount) {
        this.partCount = partCount;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
