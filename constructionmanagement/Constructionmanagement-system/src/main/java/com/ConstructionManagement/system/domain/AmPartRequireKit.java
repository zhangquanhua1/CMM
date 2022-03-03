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

    private Long partRequireId;

    private String kitModel;

    private String kitName;

    private Integer kitCount;

    private static final long serialVersionUID = 1L;

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

    public String getKitModel() {
        return kitModel;
    }

    public void setKitModel(String kitModel) {
        this.kitModel = kitModel;
    }

    public String getKitName() {
        return kitName;
    }

    public void setKitName(String kitName) {
        this.kitName = kitName;
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
