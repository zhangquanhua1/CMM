package com.ConstructionManagement.system.service.impl;

import com.ConstructionManagement.system.domain.WmEquipmentEntryPart;
import com.ConstructionManagement.system.mapper.WmEquipmentEntryPartMapper;
import com.ConstructionManagement.system.service.IWmEquipmentEntryPartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class WmEquipmentEntryPartServiceImpl implements IWmEquipmentEntryPartService {
    @Autowired
    WmEquipmentEntryPartMapper weep;
    @Override
    public int deleteByEquipmentId(Long equimentId) {
        return weep.deleteByEquipmentId(equimentId);
    }

    @Override
    public int insertSelective(WmEquipmentEntryPart record) {
        return weep.insertSelective(record);
    }

    @Override
    public List<WmEquipmentEntryPart> selectByEquipmentId(Long equimentId) {
        return weep.selectByEquipmentId(equimentId);
    }

    @Override
    public WmEquipmentEntryPart selectById(Long id) {
        return weep.selectById(id);
    }
}
