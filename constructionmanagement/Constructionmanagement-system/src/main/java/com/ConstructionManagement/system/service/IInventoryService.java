package com.ConstructionManagement.system.service;

import com.ConstructionManagement.system.domain.Inventory;

import java.util.List;

public interface IInventoryService {
    int deleteByIds(Long[] ids);

    int insertSelective(Inventory record);

    List<Inventory> selectBySelective(Inventory record);

    int updateByPrimaryKeySelective(Inventory record);

    int outStock(Long id,Long amount);

    List<Inventory> selectByParam(String name, int[]ids,String model);
}
