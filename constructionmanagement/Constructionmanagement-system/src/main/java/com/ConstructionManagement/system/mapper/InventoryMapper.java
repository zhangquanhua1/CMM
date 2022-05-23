package com.ConstructionManagement.system.mapper;

import com.ConstructionManagement.system.domain.Inventory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InventoryMapper {
    int deleteByIds(Long[] ids);

    int insertSelective(Inventory record);

    List<Inventory> selectBySelective(Inventory record);

    int updateByPrimaryKeySelective(Inventory record);

    int outStock(Long id,Long amount);

    List<Inventory> selectByParam(String name, int[]ids,String model);
    List<Inventory> selectByIds(List<Long> ids);
}
