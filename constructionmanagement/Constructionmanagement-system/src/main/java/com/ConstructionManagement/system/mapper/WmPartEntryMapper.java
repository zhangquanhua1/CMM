package com.ConstructionManagement.system.mapper;

import com.ConstructionManagement.system.domain.WmKitEntry;
import com.ConstructionManagement.system.domain.WmPartEntry;

import java.util.List;

public interface WmPartEntryMapper {
    int deleteByPrimaryKey(Long id);

    int deleteByIds(Long[] ids);

    int insertSelective(WmPartEntry record);

    WmPartEntry selectByPrimaryKey(Long id);

    List<WmPartEntry> selectBySelective(WmPartEntry record);

    int updateByPrimaryKeySelective(WmPartEntry record);

    int confirmByIds(Long[] ids);

    int confirmById(Long id);

    int AntiConfirmByIds(Long[] ids);
}
