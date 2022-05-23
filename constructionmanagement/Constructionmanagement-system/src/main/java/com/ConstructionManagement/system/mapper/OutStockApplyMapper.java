package com.ConstructionManagement.system.mapper;

import com.ConstructionManagement.system.domain.OutStockApply;

import java.util.List;

public interface OutStockApplyMapper {
    int deleteByPrimaryKey(Long id);

    int deleteByIds(Long[] ids);

    int insertSelective(OutStockApply record);

    int insertBatch( List<OutStockApply> list);

    OutStockApply selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OutStockApply record);

    List<OutStockApply> selectBySelective(OutStockApply record);
}
