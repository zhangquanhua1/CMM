package com.ConstructionManagement.system.service.impl;

import com.ConstructionManagement.system.domain.WmKitEntry;
import com.ConstructionManagement.system.mapper.WmKitEntryMapper;
import com.ConstructionManagement.system.service.IWmKitEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WmKitEntryServiceImpl implements IWmKitEntryService {
    @Autowired
    WmKitEntryMapper wke;
    @Override
    public int deleteByIds(Long[] ids) {
        return wke.deleteByIds(ids);
    }

    @Override
    public int insertSelective(WmKitEntry record) {
        return wke.insertSelective(record);
    }

    @Override
    public List<WmKitEntry> selectBySelective(WmKitEntry record) {
        return wke.selectBySelective(record);
    }

    @Override
    public int updateByPrimaryKeySelective(WmKitEntry record) {
        return wke.updateByPrimaryKeySelective(record);
    }
}
