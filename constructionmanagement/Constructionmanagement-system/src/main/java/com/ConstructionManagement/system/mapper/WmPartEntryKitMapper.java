package com.ConstructionManagement.system.mapper;

import com.ConstructionManagement.system.domain.WmEquipmentEntryKit;
import com.ConstructionManagement.system.domain.WmPartEntryKit;

import java.util.List;

public interface WmPartEntryKitMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(WmPartEntryKit record);

    WmPartEntryKit selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WmPartEntryKit record);

    List<WmPartEntryKit> selectByPartId(Long partId);

    int deleteByPartId(Long partId);
}
