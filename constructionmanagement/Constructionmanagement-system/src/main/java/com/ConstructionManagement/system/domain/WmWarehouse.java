package com.ConstructionManagement.system.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * wm_warehouse
 * @author
 */
@Data
public class WmWarehouse implements Serializable {
    private Long id;

    /**
     * 仓库编码
     */
    private String code;

    /**
     * 仓库名称
     */
    private String name;

    /**仓库类型  0 虚拟仓库 1是实体仓库
     *
     */
    private Integer type;


    /**
     * 仓库面积大小
     */
    private String area;

    /**
     * 所属部门id
     */
    private Long depart_id;

    /**
     * 仓库地址
     */
    private String address;

    /**
     * 仓库负责人
     */
    private String chargePerson;

    /**
     * 联系电话
     */
    private String contractNum;
    /**
     * 备注
     */
    private String remark;

    //仓库状态 0 可用 1 维护 2 已满
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Long getDepart_id() {
        return depart_id;
    }

    public void setDepart_id(Long depart_id) {
        this.depart_id = depart_id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    private static final long serialVersionUID = 1L;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getChargePerson() {
        return chargePerson;
    }

    public void setChargePerson(String chargePerson) {
        this.chargePerson = chargePerson;
    }

    public String getContractNum() {
        return contractNum;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
