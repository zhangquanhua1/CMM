package com.ConstructionManagement.system.service;

import com.ConstructionManagement.system.domain.WmEquipmentEntryKit;

import java.util.List;

public interface IWmEquipmentEntryKitService {
    int deleteByEquipmentId(Long equimentId);

    int insertSelective(WmEquipmentEntryKit record);

    List<WmEquipmentEntryKit> selectByEquipmentId(Long equimentId);
}
