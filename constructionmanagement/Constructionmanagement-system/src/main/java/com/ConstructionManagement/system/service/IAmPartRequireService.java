package com.ConstructionManagement.system.service;

import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.ConstructionManagement.system.domain.AmKitRequire;
import com.ConstructionManagement.system.domain.AmPartRequire;

import java.util.List;

public interface IAmPartRequireService {
    int deleteByIds(Long[] ids);

    int insertSelective(AmPartRequire record);

    List<AmPartRequire> selectBySelective(AmPartRequire record);

    int updateByPrimaryKeySelective(AmPartRequire record);

    int isBuyIds(Long ids[]);

    public String importData(ExcelImportResult<AmPartRequire> result, Boolean isUpdateSupport, String operName);
}
