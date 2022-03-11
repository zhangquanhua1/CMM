package com.ConstructionManagement.system.service.impl;

import com.ConstructionManagement.system.domain.WmWarehouse;
import com.ConstructionManagement.system.mapper.WmWarehouseMapper;
import com.ConstructionManagement.system.service.IWmWarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WmWarehouseServiceImpl implements IWmWarehouseService {
    @Autowired
    WmWarehouseMapper wwh;
    @Override
    public int deleteByIds(Long[] ids) {
        return wwh.deleteByIds(ids);
    }

    @Override
    public int insertSelective(WmWarehouse record) {
        return wwh.insertSelective(record);
    }

    @Override
    public List<WmWarehouse> selectBySelective(WmWarehouse record) {
        return wwh.selectBySelective(record);
    }

    @Override
    public List<WmWarehouse> selectByAllUse() {
        return wwh.selectByAllUse();
    }

    @Override
    public int updateByPrimaryKeySelective(WmWarehouse record) {
        return wwh.updateByPrimaryKeySelective(record);
    }
}
