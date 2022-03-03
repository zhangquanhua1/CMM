package com.ConstructionManagement.system.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/** 防坠器
 * am_protector
 * @author
 */
@Data
public class AmProtector implements Serializable {
    private Long id;

    /**
     * 防坠器规格
     */
    private String standard;

    /**
     * 防坠器型号
     */
    private String model;

    /**
     * 防坠器出厂日期
     */
    private Date productDate;

    /**
     * 防坠器检测日期
     */
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
