package com.ConstructionManagement.system.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.io.Serializable;
import java.util.Date;


/** 防坠器
 * am_protector
 * @author
 */

public class AmProtector implements Serializable {
    @Excel(name = "id",isColumnHidden = true,needMerge = true)
    private Long id;

    /**
     * 防坠器规格
     */
    @Excel(name = "防坠器规格",width = 20,needMerge = true)
    private String standard;

    /**
     * 防坠器型号
     */
    @Excel(name = "防坠器型号",width = 20,needMerge = true)
    private String model;

    /**
     * 防坠器出厂日期
     */
    @Excel(name = "防坠器出厂日期", exportFormat = "yyyy-MM-dd", width = 20, needMerge = true)
    private Date productDate;

    /**
     * 防坠器检测日期
     */
    @Excel(name = "防坠器检测日期", exportFormat = "yyyy-MM-dd", width = 20, needMerge = true)
    private Date checkDate;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Date getProductDate() {
        return productDate;
    }

    public void setProductDate(Date productDate) {
        this.productDate = productDate;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
