package com.ConstructionManagement.system.mapper;

import com.ConstructionManagement.system.domain.AmEquipmentRequireKit;

import java.util.List;

public interface AmEquipmentRequireKitMapper {
    int deleteByEquipmentId(Long equimentId);
    int insertSelective(AmEquipmentRequireKit record);
    List<AmEquipmentRequireKit> selectByEquipmentId(Long equimentId);

}
