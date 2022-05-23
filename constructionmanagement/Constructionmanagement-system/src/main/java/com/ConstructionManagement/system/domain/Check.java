package com.ConstructionManagement.system.domain;


import com.ConstructionManagement.common.annotation.Excel;
import com.ConstructionManagement.common.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 盘点对象 check
 *
 * @author ruoyi
 * @date 2022-05-22
 */
public class Check extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 盘点名称 */
    @Excel(name = "盘点名称")
    private String name;

    /** 盘点完成时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "盘点完成时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date checkDate;

    /** 盘点添加时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "盘点添加时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date addDate;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String principal;

    private Long[] ids;

    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setCheckDate(Date checkDate)
    {
        this.checkDate = checkDate;
    }

    public Date getCheckDate()
    {
        return checkDate;
    }
    public void setAddDate(Date addDate)
    {
        this.addDate = addDate;
    }

    public Date getAddDate()
    {
        return addDate;
    }
    public void setPrincipal(String principal)
    {
        this.principal = principal;
    }

    public String getPrincipal()
    {
        return principal;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("checkDate", getCheckDate())
            .append("addDate", getAddDate())
            .append("principal", getPrincipal())
            .toString();
    }
}
