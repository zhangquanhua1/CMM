package com.ConstructionManagement.system.mapper;

import com.ConstructionManagement.system.domain.WmWarehouse;

import java.util.List;

public interface WmWarehouseMapper {
    int deleteByIds(Long[] ids);

    int insertSelective(WmWarehouse record);

    List<WmWarehouse> selectBySelective (WmWarehouse record);

    int updateByPrimaryKeySelective(WmWarehouse record);
    List<WmWarehouse> selectByAllUse ();
}
