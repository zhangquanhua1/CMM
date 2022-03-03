package com.ConstructionManagement.system.service.impl;

import com.ConstructionManagement.system.domain.AmEquipmentRequire;
import com.ConstructionManagement.system.mapper.AmEquipmentRequireMapper;
import com.ConstructionManagement.system.service.IAmEquipmentRequireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AmEquipmentRequireServiceImpl implements IAmEquipmentRequireService {
    @Autowired
    AmEquipmentRequireMapper am;
    @Override
    public int deleteByIds(Long[] ids) {
        return am.deleteByIds(ids);
    }

    @Override
    public int insertSelective(AmEquipmentRequire record) {
        return am.insertSelective(record);
    }

    @Override
    public List<AmEquipmentRequire> selectBySelective(AmEquipmentRequire record) {
        return am.selectBySelective(record);
    }

    @Override
    public int updateByPrimaryKeySelective(AmEquipmentRequire record) {
        return am.updateByPrimaryKeySelective(record);
    }
}
