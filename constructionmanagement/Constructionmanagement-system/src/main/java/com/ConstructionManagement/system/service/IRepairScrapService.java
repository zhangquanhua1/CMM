package com.ConstructionManagement.system.service;

import com.ConstructionManagement.system.domain.RepairScrap;
import com.ConstructionManagement.system.domain.StockOutRecord;

import java.util.List;

public interface IRepairScrapService {
    int deleteByPrimaryKey(Long repairId);

    int deleteByIds(Long[] repairIds);

    int insertSelective(RepairScrap record);

    RepairScrap selectByPrimaryKey(Long repairId);

    List<RepairScrap> selectBySelective(RepairScrap record);

    int updateByPrimaryKeySelective(RepairScrap record);
}
