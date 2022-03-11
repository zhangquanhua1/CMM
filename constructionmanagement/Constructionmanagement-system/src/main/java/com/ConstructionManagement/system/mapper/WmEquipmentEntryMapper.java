package com.ConstructionManagement.system.mapper;

import com.ConstructionManagement.system.domain.WmEquipmentEntry;

import java.util.List;

public interface WmEquipmentEntryMapper {
    int deleteByIds(Long[] ids);

    int insertSelective(WmEquipmentEntry record);

    List<WmEquipmentEntry> selectBySelective(WmEquipmentEntry record);

    int updateByPrimaryKeySelective(WmEquipmentEntry record);
    int confirmByIds(Long []ids);
    int AntiConfirmByIds(Long []ids);
}
