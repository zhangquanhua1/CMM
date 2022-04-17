package com.ConstructionManagement.system.service;

import com.ConstructionManagement.system.domain.WmPartEntryKit;

import java.util.List;

public interface IWmPartEntryKitService {
    int deleteByPrimaryKey(Long id);

    int insertSelective(WmPartEntryKit record);

    WmPartEntryKit selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WmPartEntryKit record);

    List<WmPartEntryKit> selectByPartId(Long partId);

    int deleteByPartId(Long partId);
}
