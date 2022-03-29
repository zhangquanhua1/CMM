package com.ConstructionManagement.system.mapper;

import com.ConstructionManagement.system.domain.AmEquipmentRequire;

import java.util.List;

public interface AmEquipmentRequireMapper {
    int deleteByIds(Long[] ids);
    int insertSelective(AmEquipmentRequire record);
    List<AmEquipmentRequire> selectBySelective(AmEquipmentRequire record);
    int updateByPrimaryKeySelective(AmEquipmentRequire record);
    int isBuyIds(Long ids[]);
}
