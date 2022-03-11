package com.ConstructionManagement.system.domain;

import java.io.Serializable;
import lombok.Data;

/**
 * am_tower_machine_param_part
 * @author
 */
public class AmTowerMachineParamPart implements Serializable {
    private Long id;
    //塔机id
    private Long pid;
    //部件id
    private Long part_id;
    //部件数量
    private Integer partCount;
    private static final long serialVersionUID = 1L;

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
