package com.ConstructionManagement.system.service;

import com.ConstructionManagement.system.domain.WmWarehouse;

import java.util.List;

public interface IWmWarehouseService {

    int deleteByIds(Long[] ids);

    int insertSelective(WmWarehouse record);

    List<WmWarehouse> selectBySelective (WmWarehouse record);

    List<WmWarehouse> selectByAllUse ();

    int updateByPrimaryKeySelective(WmWarehouse record);
}
