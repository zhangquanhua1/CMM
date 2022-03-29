package com.ConstructionManagement.system.domain;

import java.io.Serializable;


/**
 * am_elevator_param_kit
 * @author
 */

public class AmElevatorParamKit implements Serializable {
    private Long id;

    private Long elevatorId;

    private String kitName;

    private String kitUnit;

    private Integer kitCount;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getElevatorId() {
        return elevatorId;
    }

    public void setElevatorId(Long elevatorId) {
        this.elevatorId = elevatorId;
    }

    public String getKitName() {
        return kitName;
    }

    public void setKitName(String kitName) {
        this.kitName = kitName;
    }

    public String getKitUnit() {
        return kitUnit;
    }

    public void setKitUnit(String kitUnit) {
        this.kitUnit = kitUnit;
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
