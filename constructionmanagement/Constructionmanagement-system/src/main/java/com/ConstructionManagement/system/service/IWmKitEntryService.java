package com.ConstructionManagement.system.service;

import com.ConstructionManagement.system.domain.WmKitEntry;

import java.util.List;

public interface IWmKitEntryService {
    int deleteByIds(Long []ids);
    int insertSelective(WmKitEntry record);

    List<WmKitEntry> selectBySelective(WmKitEntry record);

    int updateByPrimaryKeySelective(WmKitEntry record);
}
