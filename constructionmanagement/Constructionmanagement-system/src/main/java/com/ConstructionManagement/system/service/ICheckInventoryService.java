package com.ConstructionManagement.system.service;

import com.ConstructionManagement.system.domain.CheckInventory;

import java.util.List;

public interface ICheckInventoryService {
    int insertBatch(List<CheckInventory> record);

    int insertSelective(CheckInventory record);
    List<CheckInventory> selectByCheckId(Long checkId);
}
