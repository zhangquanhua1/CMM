package com.ConstructionManagement.system.service.impl;

import com.ConstructionManagement.system.domain.WmEquipmentEntry;
import com.ConstructionManagement.system.mapper.WmEquipmentEntryMapper;
import com.ConstructionManagement.system.service.IWmEquipmentEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WmEquipmentEntryServiceImpl implements IWmEquipmentEntryService {
    @Autowired
    WmEquipmentEntryMapper we;
    @Override
    public int deleteByIds(Long[] ids) {
        return we.deleteByIds(ids);
    }

    @Override
    public int insertSelective(WmEquipmentEntry record) {
        return we.insertSelective(record);
    }

    @Override
    public List<WmEquipmentEntry> selectBySelective(WmEquipmentEntry record) {
        return we.selectBySelective(record);
    }

    @Override
    public int updateByPrimaryKeySelective(WmEquipmentEntry record) {
        return we.updateByPrimaryKeySelective(record);
    }

    @Override
    public int confirmByIds(Long[] ids) {
        return we.confirmByIds(ids);
    }

    @Override
    public int AntiConfirmByIds(Long[] ids) {
        return we.AntiConfirmByIds(ids);
    }
}
