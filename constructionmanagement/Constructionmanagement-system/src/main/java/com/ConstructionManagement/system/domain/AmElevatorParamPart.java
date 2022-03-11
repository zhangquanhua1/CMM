package com.ConstructionManagement.system.domain;

import java.io.Serializable;
import lombok.Data;

/**
 * am_elevator_param_part
 * @author
 */
@Data
public class AmElevatorParamPart implements Serializable {
    private Long id;

    private Long elevatorId;
    private Long part_id;

    private Integer partCount;
    AmPartParam amPartParam;
    private static final long serialVersionUID = 1L;

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

    public Long getElevatorId() {
        return elevatorId;
    }

    public void setElevatorId(Long elevatorId) {
        this.elevatorId = elevatorId;
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
