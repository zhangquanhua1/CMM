package com.ConstructionManagement.system.service;

import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.ConstructionManagement.system.domain.AmPartRequire;
import com.ConstructionManagement.system.domain.OutStockApply;

import java.util.List;

public interface IOutStockApplyService {
    int deleteByPrimaryKey(Long id);

    int deleteByIds(Long[] ids);

    int insertSelective(OutStockApply record);

    OutStockApply selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OutStockApply record);

    List<OutStockApply> selectBySelective(OutStockApply record);

    public String importData(ExcelImportResult<OutStockApply> result, Boolean isUpdateSupport, String operName);
}
