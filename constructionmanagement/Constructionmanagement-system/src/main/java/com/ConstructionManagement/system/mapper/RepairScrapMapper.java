package com.ConstructionManagement.system.mapper;

import com.ConstructionManagement.system.domain.RepairScrap;
import com.ConstructionManagement.system.domain.StockOutRecord;

import java.util.List;

public interface RepairScrapMapper {
    int deleteByPrimaryKey(Long repairId);

    int deleteByIds(Long[] repairIds);

    int insertSelective(RepairScrap record);

    RepairScrap selectByPrimaryKey(Long repairId);

    List<RepairScrap> selectBySelective(RepairScrap record);

    int updateByPrimaryKeySelective(RepairScrap record);

}
