package com.ConstructionManagement.system.domain;

import java.io.Serializable;
import java.util.List;

public class KitAndPart {
    /**
     * 零配件
     */
    private List<AmTowerMachineParamKit> amTowerMachineParamkits;
    /**
     * 部件
     */
    private List<AmTowerMachineParamPart> amTowerMachineParamParts;

    private static final long serialVersionUID = 1L;

    public List<AmTowerMachineParamKit> getAmTowerMachineParamkits() {
        return amTowerMachineParamkits;
    }

    public void setAmTowerMachineParamkits(List<AmTowerMachineParamKit> amTowerMachineParamkits) {
        this.amTowerMachineParamkits = amTowerMachineParamkits;
    }

    public List<AmTowerMachineParamPart> getAmTowerMachineParamParts() {
        return amTowerMachineParamParts;
    }

    public void setAmTowerMachineParamParts(List<AmTowerMachineParamPart> amTowerMachineParamParts) {
        this.amTowerMachineParamParts = amTowerMachineParamParts;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
