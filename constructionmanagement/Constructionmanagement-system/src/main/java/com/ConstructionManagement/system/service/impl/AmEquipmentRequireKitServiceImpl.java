package com.ConstructionManagement.system.service.impl;

import com.ConstructionManagement.system.domain.AmEquipmentRequireKit;
import com.ConstructionManagement.system.mapper.AmEquipmentRequireKitMapper;
import com.ConstructionManagement.system.service.IAmEquipmentRequireKitService;
import com.ConstructionManagement.system.service.IAmEquipmentRequireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AmEquipmentRequireKitServiceImpl implements IAmEquipmentRequireKitService {
    @Autowired
    AmEquipmentRequireKitMapper aerk;
    @Override
    public int deleteByEquipmentId(Long equimentId) {
        return aerk.deleteByEquipmentId(equimentId);
    }

    @Override
    public int insertSelective(AmEquipmentRequireKit record) {
        return aerk.insertSelective(record);
    }

    @Override
    public List<AmEquipmentRequireKit> selectByEquipmentId(Long equimentId) {
        return aerk.selectByEquipmentId(equimentId);
    }
}
