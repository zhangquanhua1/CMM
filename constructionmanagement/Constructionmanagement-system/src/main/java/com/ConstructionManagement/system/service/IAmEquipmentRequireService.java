package com.ConstructionManagement.system.service;

import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.ConstructionManagement.system.domain.AmEquipmentRequire;
import com.ConstructionManagement.system.domain.AmPartRequire;

import java.util.List;

public interface IAmEquipmentRequireService {
    int deleteByIds(Long[] ids);

    int insertSelective(AmEquipmentRequire record);

    List<AmEquipmentRequire> selectBySelective(AmEquipmentRequire record);

    int updateByPrimaryKeySelective(AmEquipmentRequire record);

    int isBuyIds(Long ids[]);

    public String importData(ExcelImportResult<AmEquipmentRequire> result, Boolean isUpdateSupport, String operName);
}
