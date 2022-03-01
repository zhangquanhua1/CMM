package com.ConstructionManagement.system.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * am_tower_machine_param
 * @author
 */
@Data
public class AmTowerMachineParam  implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 序号
     */
    private Long id;
    /**
     * 塔机型号
     */
    private String towerMachineModel;
    /**
     * 塔机名称
     */
    private String towerMachineName;

    /**
     * 设备类别
     */
    private String towerMachineType;

    /**
     * 厂家
     */
    private String vender;

    /**
     * 额定起重力距
     */
    private Long ratedLiftingDistance;

    /**
     * 最大起重量
     */
    private Double maximumLiftingWeight;

    /**
     * 最大幅度额定起重量
     */
    private Double maximumRatedWeight;

    /**
     * 最大起重量工作幅度
     */
    private Integer maximumStartingWeightWorkingRange;

    /**
     * 最大工作幅度
     */
    private Integer maximumRangeWork;

    /**
     * 最大起升高度
     */
    private Integer maximumHeightLift;

    /**
     * 起升速度
     */
    private Integer upDownSpeed;

    /**
     * 变幅速度
     */
    private Integer variableAmplitudeSpeed;

    /**
     * 回转速度
     */
    private Integer swivellingSpeed;

    /**
     * 顶升速度
     */
    private Integer upSpeed;

    /**
     * 塔机自重
     */
    private Double towerMachineWeight;

    /**
     * 平衡重量
     */
    private Double balanceWeight;

    /**
     * 工作电压
     */
    private Integer workingVoltage;

    /**
     * 装机总容量
     */
    private Integer totalInstalledCapacity;

    /**
     * 电机功率
     */
    private Integer motorPower;

    /**
     * 变频器功率
     */
    private Integer frequencyConverterPower;

    /**
     * 标准节规格
     */
    private String standardSectionSpecifications;

    /**
     * 附墙框规格
     */
    private String attachedWallFrameSpecifications;

    /**
     * 规格归类
     */
    private String specificationClassification;

    /**
     * 备注
     */
    private String remark;

    /**
     * 插入人
     */
    private String insertPerson;

    /**
     * 插入人部门id
     */
    private Long insertPersonDepartId;

    /**
     * 更新人
     */
    private String updatePerson;

    /**
     * 更新人部门id
     */
    private Long updatePersonDepartId;

    /**
     * 插入时间
     */
    private Date insertDate;

    /**
     * 更新时间
     */
    private Date updateDate;
    /**
     * 零配件
     */
    private List<AmTowerMachineParamKit>  amTowerMachineParamkits;
    /**
     * 部件
     */
    private List<AmTowerMachineParamPart> amTowerMachineParamParts;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTowerMachineModel() {
        return towerMachineModel;
    }

    public void setTowerMachineModel(String towerMachineModel) {
        this.towerMachineModel = towerMachineModel;
    }

    public String getTowerMachineName() {
        return towerMachineName;
    }

    public void setTowerMachineName(String towerMachineName) {
        this.towerMachineName = towerMachineName;
    }

    public String getTowerMachineType() {
        return towerMachineType;
    }

    public void setTowerMachineType(String towerMachineType) {
        this.towerMachineType = towerMachineType;
    }

    public String getVender() {
        return vender;
    }

    public void setVender(String vender) {
        this.vender = vender;
    }

    public Long getRatedLiftingDistance() {
        return ratedLiftingDistance;
    }

    public void setRatedLiftingDistance(Long ratedLiftingDistance) {
        this.ratedLiftingDistance = ratedLiftingDistance;
    }

    public Double getMaximumLiftingWeight() {
        return maximumLiftingWeight;
    }

    public void setMaximumLiftingWeight(Double maximumLiftingWeight) {
        this.maximumLiftingWeight = maximumLiftingWeight;
    }

    public Double getMaximumRatedWeight() {
        return maximumRatedWeight;
    }

    public void setMaximumRatedWeight(Double maximumRatedWeight) {
        this.maximumRatedWeight = maximumRatedWeight;
    }

    public Integer getMaximumStartingWeightWorkingRange() {
        return maximumStartingWeightWorkingRange;
    }

    public void setMaximumStartingWeightWorkingRange(Integer maximumStartingWeightWorkingRange) {
        this.maximumStartingWeightWorkingRange = maximumStartingWeightWorkingRange;
    }

    public Integer getMaximumRangeWork() {
        return maximumRangeWork;
    }

    public void setMaximumRangeWork(Integer maximumRangeWork) {
        this.maximumRangeWork = maximumRangeWork;
    }

    public Integer getMaximumHeightLift() {
        return maximumHeightLift;
    }

    public void setMaximumHeightLift(Integer maximumHeightLift) {
        this.maximumHeightLift = maximumHeightLift;
    }

    public Integer getUpDownSpeed() {
        return upDownSpeed;
    }

    public void setUpDownSpeed(Integer upDownSpeed) {
        this.upDownSpeed = upDownSpeed;
    }

    public Integer getVariableAmplitudeSpeed() {
        return variableAmplitudeSpeed;
    }

    public void setVariableAmplitudeSpeed(Integer variableAmplitudeSpeed) {
        this.variableAmplitudeSpeed = variableAmplitudeSpeed;
    }

    public Integer getSwivellingSpeed() {
        return swivellingSpeed;
    }

    public void setSwivellingSpeed(Integer swivellingSpeed) {
        this.swivellingSpeed = swivellingSpeed;
    }

    public Integer getUpSpeed() {
        return upSpeed;
    }

    public void setUpSpeed(Integer upSpeed) {
        this.upSpeed = upSpeed;
    }

    public Double getTowerMachineWeight() {
        return towerMachineWeight;
    }

    public void setTowerMachineWeight(Double towerMachineWeight) {
        this.towerMachineWeight = towerMachineWeight;
    }

    public Double getBalanceWeight() {
        return balanceWeight;
    }

    public void setBalanceWeight(Double balanceWeight) {
        this.balanceWeight = balanceWeight;
    }

    public Integer getWorkingVoltage() {
        return workingVoltage;
    }

    public void setWorkingVoltage(Integer workingVoltage) {
        this.workingVoltage = workingVoltage;
    }

    public Integer getTotalInstalledCapacity() {
        return totalInstalledCapacity;
    }

    public void setTotalInstalledCapacity(Integer totalInstalledCapacity) {
        this.totalInstalledCapacity = totalInstalledCapacity;
    }

    public Integer getMotorPower() {
        return motorPower;
    }

    public void setMotorPower(Integer motorPower) {
        this.motorPower = motorPower;
    }

    public Integer getFrequencyConverterPower() {
        return frequencyConverterPower;
    }

    public void setFrequencyConverterPower(Integer frequencyConverterPower) {
        this.frequencyConverterPower = frequencyConverterPower;
    }

    public String getStandardSectionSpecifications() {
        return standardSectionSpecifications;
    }

    public void setStandardSectionSpecifications(String standardSectionSpecifications) {
        this.standardSectionSpecifications = standardSectionSpecifications;
    }

    public String getAttachedWallFrameSpecifications() {
        return attachedWallFrameSpecifications;
    }

    public void setAttachedWallFrameSpecifications(String attachedWallFrameSpecifications) {
        this.attachedWallFrameSpecifications = attachedWallFrameSpecifications;
    }

    public String getSpecificationClassification() {
        return specificationClassification;
    }

    public void setSpecificationClassification(String specificationClassification) {
        this.specificationClassification = specificationClassification;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getInsertPerson() {
        return insertPerson;
    }

    public void setInsertPerson(String insertPerson) {
        this.insertPerson = insertPerson;
    }

    public Long getInsertPersonDepartId() {
        return insertPersonDepartId;
    }

    public void setInsertPersonDepartId(Long insertPersonDepartId) {
        this.insertPersonDepartId = insertPersonDepartId;
    }

    public String getUpdatePerson() {
        return updatePerson;
    }

    public void setUpdatePerson(String updatePerson) {
        this.updatePerson = updatePerson;
    }

    public Long getUpdatePersonDepartId() {
        return updatePersonDepartId;
    }

    public void setUpdatePersonDepartId(Long updatePersonDepartId) {
        this.updatePersonDepartId = updatePersonDepartId;
    }

    public Date getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

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
}
