package com.ConstructionManagement.system.service;

import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.ConstructionManagement.system.domain.AmPartRequire;
import com.ConstructionManagement.system.domain.WmPartEntry;

import java.util.List;

public interface IWmPartEntryService {
    int deleteByPrimaryKey(Long id);

    int deleteByIds(Long[] ids);

    int insertSelective(WmPartEntry record);

    WmPartEntry selectByPrimaryKey(Long id);

    List<WmPartEntry> selectBySelective(WmPartEntry record);

    int updateByPrimaryKeySelective(WmPartEntry record);

    int confirmByIds(Long[] ids);

    int confirmById(Long id);

    int AntiConfirmByIds(Long[] ids);

    public String importData(ExcelImportResult<WmPartEntry> result, Boolean isUpdateSupport, String operName);
}
