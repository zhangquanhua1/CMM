package com.ConstructionManagement.system.mapper;

import com.ConstructionManagement.system.domain.AmEquipmentRequireKit;
import com.ConstructionManagement.system.domain.AmEquipmentRequirePart;

import java.util.List;

public interface AmEquipmentRequirePartMapper {



    int deleteByEquipmentId(Long equimentId);
    int insertSelective(AmEquipmentRequirePart record);
    List<AmEquipmentRequirePart> selectByEquipmentId(Long equimentId);
}
