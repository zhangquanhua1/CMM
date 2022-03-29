package com.ConstructionManagement.system.mapper;

import com.ConstructionManagement.system.domain.AmEquipmentRequireKit;
import com.ConstructionManagement.system.domain.WmEquipmentEntryKit;

import java.util.List;

public interface WmEquipmentEntryKitMapper {
    int deleteByEquipmentId(Long equimentId);

    int insertSelective(WmEquipmentEntryKit record);

    List<WmEquipmentEntryKit> selectByEquipmentId(Long equimentId);

    WmEquipmentEntryKit selectById(Long id);
}
