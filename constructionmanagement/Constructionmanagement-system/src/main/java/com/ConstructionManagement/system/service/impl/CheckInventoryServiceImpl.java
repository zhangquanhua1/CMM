package com.ConstructionManagement.system.service.impl;

import com.ConstructionManagement.system.domain.CheckInventory;
import com.ConstructionManagement.system.mapper.CheckInventoryMapper;
import com.ConstructionManagement.system.service.ICheckInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckInventoryServiceImpl implements ICheckInventoryService {
    @Autowired
    CheckInventoryMapper cim;
    @Override
    public int insertBatch(List<CheckInventory> record) {
        return cim.insertBatch(record);
    }

    @Override
    public int insertSelective(CheckInventory record) {
        return cim.insertSelective(record);
    }

    @Override
    public List<CheckInventory> selectByCheckId(Long checkId) {
        return cim.selectByCheckId(checkId);
    }
}
