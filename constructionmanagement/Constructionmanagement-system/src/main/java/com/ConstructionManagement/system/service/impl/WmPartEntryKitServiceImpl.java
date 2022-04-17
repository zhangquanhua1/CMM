package com.ConstructionManagement.system.service.impl;

import com.ConstructionManagement.system.domain.WmPartEntry;
import com.ConstructionManagement.system.domain.WmPartEntryKit;
import com.ConstructionManagement.system.mapper.WmPartEntryKitMapper;
import com.ConstructionManagement.system.service.IWmPartEntryKitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class WmPartEntryKitServiceImpl implements IWmPartEntryKitService {
    @Autowired
    WmPartEntryKitMapper wpekm;
    @Override
    public int deleteByPrimaryKey(Long id) {
        return wpekm.deleteByPrimaryKey(id);
    }

    @Override
    public int insertSelective(WmPartEntryKit record) {
        return wpekm.insertSelective(record);
    }

    @Override
    public WmPartEntryKit selectByPrimaryKey(Long id) {
        return wpekm.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(WmPartEntryKit record) {
        return wpekm.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<WmPartEntryKit> selectByPartId(Long partId) {
        return wpekm.selectByPartId(partId);
    }

    @Override
    public int deleteByPartId(Long partId) {
        return wpekm.deleteByPartId(partId);
    }
}
