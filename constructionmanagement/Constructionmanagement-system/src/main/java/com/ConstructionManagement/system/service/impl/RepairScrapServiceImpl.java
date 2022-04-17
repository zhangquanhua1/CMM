package com.ConstructionManagement.system.service.impl;

import com.ConstructionManagement.system.domain.RepairScrap;
import com.ConstructionManagement.system.domain.StockOutRecord;
import com.ConstructionManagement.system.mapper.RepairScrapMapper;
import com.ConstructionManagement.system.service.IRepairScrapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepairScrapServiceImpl implements IRepairScrapService {

    @Autowired
    RepairScrapMapper rsm;


    @Override
    public int deleteByPrimaryKey(Long repairId) {
        return rsm.deleteByPrimaryKey(repairId);
    }

    @Override
    public int deleteByIds(Long[] repairIds) {
        return rsm.deleteByIds(repairIds);
    }

    @Override
    public int insertSelective(RepairScrap record) {
        return rsm.insertSelective(record);
    }

    @Override
    public RepairScrap selectByPrimaryKey(Long repairId) {
        return rsm.selectByPrimaryKey(repairId);
    }

    @Override
    public List<RepairScrap> selectBySelective(RepairScrap record) {
        return rsm.selectBySelective(record);
    }

    @Override
    public int updateByPrimaryKeySelective(RepairScrap record) {
        return rsm.updateByPrimaryKeySelective(record);
    }
}
