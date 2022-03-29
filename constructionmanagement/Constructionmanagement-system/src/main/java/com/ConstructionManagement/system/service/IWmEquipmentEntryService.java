package com.ConstructionManagement.system.service;

import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.ConstructionManagement.system.domain.AmPartRequire;
import com.ConstructionManagement.system.domain.WmEquipmentEntry;

import java.util.List;

public interface IWmEquipmentEntryService {

    int deleteByIds(Long[] ids);

    int insertSelective(WmEquipmentEntry record);

    List<WmEquipmentEntry> selectBySelective(WmEquipmentEntry record);

    WmEquipmentEntry selectById(Long id);

    int updateByPrimaryKeySelective(WmEquipmentEntry record);

    int confirmByIds(Long[] ids);

    int confirmById(Long id);

    int AntiConfirmByIds(Long[] ids);

    public String importData(ExcelImportResult<WmEquipmentEntry> result, Boolean isUpdateSupport, String operName);
}
