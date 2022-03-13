package com.ConstructionManagement.system.domain;

import java.io.Serializable;
import java.util.List;

public class KitAndPart {
    /**
     * 设备参数零配件
     */
    private List<AmPartParamKit> amPartParamKits;
    /**
     * 设备参数部件
     */
    private List<AmTowerMachineParamPart> amTowerMachineParamParts;
    //设备需求配件
    private List<AmEquipmentRequireKit> amEquipmentRequireKits;
    //设备需求部件
    private List<AmEquipmentRequirePart> amEquipmentRequireParts;

    //设备录入配件
    private List<WmEquipmentEntryKit> wmEquipmentEntryKits;
    //设备录入部件
    private List<WmEquipmentEntryPart> wmEquipmentEntryParts;

    private static final long serialVersionUID = 1L;

    public List<WmEquipmentEntryKit> getWmEquipmentEntryKits() {
        return wmEquipmentEntryKits;
    }

    public void setWmEquipmentEntryKits(List<WmEquipmentEntryKit> wmEquipmentEntryKits) {
        this.wmEquipmentEntryKits = wmEquipmentEntryKits;
    }

    public List<WmEquipmentEntryPart> getWmEquipmentEntryParts() {
        return wmEquipmentEntryParts;
    }

    public void setWmEquipmentEntryParts(List<WmEquipmentEntryPart> wmEquipmentEntryParts) {
        this.wmEquipmentEntryParts = wmEquipmentEntryParts;
    }

    public List<AmEquipmentRequireKit> getAmEquipmentRequireKits() {
        return amEquipmentRequireKits;
    }

    public void setAmEquipmentRequireKits(List<AmEquipmentRequireKit> amEquipmentRequireKits) {
        this.amEquipmentRequireKits = amEquipmentRequireKits;
    }

    public List<AmEquipmentRequirePart> getAmEquipmentRequireParts() {
        return amEquipmentRequireParts;
    }

    public void setAmEquipmentRequireParts(List<AmEquipmentRequirePart> amEquipmentRequireParts) {
        this.amEquipmentRequireParts = amEquipmentRequireParts;
    }

    public List<AmPartParamKit> getAmPartParamKits() {
        return amPartParamKits;
    }

    public void setAmPartParamKits(List<AmPartParamKit> amPartParamKits) {
        this.amPartParamKits = amPartParamKits;
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
