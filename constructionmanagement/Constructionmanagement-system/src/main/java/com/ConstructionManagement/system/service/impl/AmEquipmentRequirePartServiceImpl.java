package com.ConstructionManagement.system.service.impl;

import com.ConstructionManagement.system.domain.AmEquipmentRequireKit;
import com.ConstructionManagement.system.domain.AmEquipmentRequirePart;
import com.ConstructionManagement.system.mapper.AmEquipmentRequirePartMapper;
import com.ConstructionManagement.system.service.IAmEquipmentRequirePartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AmEquipmentRequirePartServiceImpl implements IAmEquipmentRequirePartService {
    @Autowired
    AmEquipmentRequirePartMapper AERP;
    @Override
    public int deleteByEquipmentId(Long equimentId) {
        return AERP.deleteByEquipmentId(equimentId);
    }

    @Override
    public int insertSelective(AmEquipmentRequirePart record) {
        return AERP.insertSelective(record);
    }

    @Override
    public List<AmEquipmentRequirePart> selectByEquipmentId(Long equimentId) {
        return AERP.selectByEquipmentId(equimentId);
    }
}
