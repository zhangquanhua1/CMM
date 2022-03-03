package com.ConstructionManagement.system.domain;

import java.util.List;

public class ElevatorKitPart {
    private List<AmElevatorParamKit> amElevatorParamKits;
    private List<AmElevatorParamPart> amElevatorParamParts;

    public List<AmElevatorParamKit> getAmElevatorParamKits() {
        return amElevatorParamKits;
    }

    public void setAmElevatorParamKits(List<AmElevatorParamKit> amElevatorParamKits) {
        this.amElevatorParamKits = amElevatorParamKits;
    }

    public List<AmElevatorParamPart> getAmElevatorParamParts() {
        return amElevatorParamParts;
    }

    public void setAmElevatorParamParts(List<AmElevatorParamPart> amElevatorParamParts) {
        this.amElevatorParamParts = amElevatorParamParts;
    }
}
