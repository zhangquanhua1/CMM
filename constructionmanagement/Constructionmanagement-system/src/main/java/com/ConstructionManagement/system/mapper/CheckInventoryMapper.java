package com.ConstructionManagement.system.mapper;

import com.ConstructionManagement.system.domain.CheckInventory;

import java.util.List;

public interface CheckInventoryMapper {
    int insertBatch(List<CheckInventory> list);

    int insertSelective(CheckInventory record);

    List<CheckInventory> selectByCheckId(Long checkId);

}
