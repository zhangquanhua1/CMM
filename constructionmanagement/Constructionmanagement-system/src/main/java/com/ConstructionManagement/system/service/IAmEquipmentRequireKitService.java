package com.ConstructionManagement.system.service;

import com.ConstructionManagement.system.domain.AmEquipmentRequireKit;

import java.util.List;

public interface IAmEquipmentRequireKitService {
    int deleteByEquipmentId(Long equimentId);
    int insertSelective(AmEquipmentRequireKit record);
    List<AmEquipmentRequireKit> selectByEquipmentId(Long equimentId);
}
