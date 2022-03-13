package com.ConstructionManagement.system.service.impl;

import com.ConstructionManagement.system.domain.WmEquipmentEntryKit;
import com.ConstructionManagement.system.mapper.WmEquipmentEntryKitMapper;
import com.ConstructionManagement.system.service.IWmEquipmentEntryKitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WmEquipmentEntryKitServiceImpl implements IWmEquipmentEntryKitService {
    @Autowired
    WmEquipmentEntryKitMapper week;

    @Override
    public int deleteByEquipmentId(Long equimentId) {
        return week.deleteByEquipmentId(equimentId);
    }

    @Override
    public int insertSelective(WmEquipmentEntryKit record) {
        return week.insertSelective(record);
    }

    @Override
    public List<WmEquipmentEntryKit> selectByEquipmentId(Long equimentId) {
        return week.selectByEquipmentId(equimentId);
    }
}
