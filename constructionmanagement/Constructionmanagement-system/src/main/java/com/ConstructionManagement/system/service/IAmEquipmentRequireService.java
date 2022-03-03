package com.ConstructionManagement.system.service;

import com.ConstructionManagement.system.domain.AmEquipmentRequire;

import java.util.List;

public interface IAmEquipmentRequireService {
    int deleteByIds(Long []ids);
    int insertSelective(AmEquipmentRequire record);
    List<AmEquipmentRequire> selectBySelective(AmEquipmentRequire record);
    int updateByPrimaryKeySelective(AmEquipmentRequire record);
}
