package com.ConstructionManagement.system.domain;

import java.io.Serializable;
import lombok.Data;

/**
 * am_part_param_kit
 * @author
 */
@Data
public class AmPartParamKit implements Serializable {
    private Long id;
    //部件id
    private Long pid;
    //配件id
    private Long kid;
    //配件数量
    private Integer kitCount;
    AmKitParam amKitParam;

    public AmKitParam getAmKitParam() {
        return amKitParam;
    }

    public void setAmKitParam(AmKitParam amKitParam) {
        this.amKitParam = amKitParam;
    }

    private static final long serialVersionUID = 1L;

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

    public Long getKid() {
        return kid;
    }

    public void setKid(Long kid) {
        this.kid = kid;
    }

    public Integer getKitCount() {
        return kitCount;
    }

    public void setKitCount(Integer kitCount) {
        this.kitCount = kitCount;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
