package com.ConstructionManagement.system.mapper;

import com.ConstructionManagement.system.domain.WmEquipmentEntry;

import java.util.List;

public interface WmEquipmentEntryMapper {
    int deleteByIds(Long[] ids);

    int insertSelective(WmEquipmentEntry record);

    List<WmEquipmentEntry> selectBySelective(WmEquipmentEntry record);

    WmEquipmentEntry selectById(Long id);

    int updateByPrimaryKeySelective(WmEquipmentEntry record);

    int confirmByIds(Long[] ids);

    int confirmById(Long id);

    int AntiConfirmByIds(Long[] ids);
}
