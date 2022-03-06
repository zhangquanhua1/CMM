package com.ConstructionManagement.system.mapper;

import com.ConstructionManagement.system.domain.WmKitEntry;

import java.util.List;

public interface WmKitEntryMapper {
    int deleteByIds(Long []ids);
    int insertSelective(WmKitEntry record);

    List<WmKitEntry> selectBySelective(WmKitEntry record);

    int updateByPrimaryKeySelective(WmKitEntry record);

}
