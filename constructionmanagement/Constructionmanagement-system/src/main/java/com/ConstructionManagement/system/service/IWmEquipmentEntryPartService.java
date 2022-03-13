package com.ConstructionManagement.system.service;

import com.ConstructionManagement.system.domain.WmEquipmentEntryPart;

import java.util.List;

public interface IWmEquipmentEntryPartService {
    int deleteByEquipmentId(Long equimentId);
    int insertSelective(WmEquipmentEntryPart record);
    List<WmEquipmentEntryPart> selectByEquipmentId(Long equimentId);
}
