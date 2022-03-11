package com.ConstructionManagement.system.domain;

import java.io.Serializable;
import lombok.Data;

/**
 * am_part_require_kit
 * @author
 */
@Data
public class AmPartRequireKit implements Serializable {
    private Long id;

    //部件需求id
    private Long partRequireId;
    //配件id
    private Long kit_id;
   //配件数量
    private Integer kitCount;
    private AmKitParam amKitParam;
    private static final long serialVersionUID = 1L;

    public AmKitParam getAmKitParam() {
        return amKitParam;
    }

    public void setAmKitParam(AmKitParam amKitParam) {
        this.amKitParam = amKitParam;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPartRequireId() {
        return partRequireId;
    }

    public void setPartRequireId(Long partRequireId) {
        this.partRequireId = partRequireId;
    }

    public Long getKit_id() {
        return kit_id;
    }

    public void setKit_id(Long kit_id) {
        this.kit_id = kit_id;
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
