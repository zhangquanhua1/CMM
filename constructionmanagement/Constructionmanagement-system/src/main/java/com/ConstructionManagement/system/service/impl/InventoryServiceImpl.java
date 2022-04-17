package com.ConstructionManagement.system.service.impl;

import com.ConstructionManagement.system.domain.Inventory;
import com.ConstructionManagement.system.mapper.InventoryMapper;
import com.ConstructionManagement.system.service.IInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryServiceImpl implements IInventoryService {
    @Autowired
    InventoryMapper im;
    @Override
    public int deleteByIds(Long[] ids) {
        return im.deleteByIds(ids);
    }

    @Override
    public int insertSelective(Inventory record) {
        return im.insertSelective(record);
    }

    @Override
    public List<Inventory> selectBySelective(Inventory record) {
        return im.selectBySelective(record);
    }

    @Override
    public int updateByPrimaryKeySelective(Inventory record) {
        return im.updateByPrimaryKeySelective(record);
    }

    @Override
    public int outStock(Long id, Long amount) {
        return im.outStock(id,amount);
    }

    @Override
    public List<Inventory> selectByParam(String name, int[] ids, String model) {
        return im.selectByParam(name, ids, model);
    }
}
