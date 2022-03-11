package com.ConstructionManagement.system.domain;

import java.util.List;

public class ElevatorKitPart {
    private List<AmPartParamKit> amPartParamKits;
    private List<AmElevatorParamPart> amElevatorParamParts;

    public List<AmPartParamKit> getAmPartParamKits() {
        return amPartParamKits;
    }

    public void setAmPartParamKits(List<AmPartParamKit> amPartParamKits) {
        this.amPartParamKits = amPartParamKits;
    }

    public List<AmElevatorParamPart> getAmElevatorParamParts() {
        return amElevatorParamParts;
    }

    public void setAmElevatorParamParts(List<AmElevatorParamPart> amElevatorParamParts) {
        this.amElevatorParamParts = amElevatorParamParts;
    }
}
