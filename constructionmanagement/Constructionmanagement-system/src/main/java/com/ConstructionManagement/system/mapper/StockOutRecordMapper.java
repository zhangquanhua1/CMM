package com.ConstructionManagement.system.mapper;

import com.ConstructionManagement.system.domain.StockOutRecord;

import java.util.List;

public interface StockOutRecordMapper {
    int deleteByPrimaryKey(Long id);

    int deleteByIds(Long[] ids);

    int insertSelective(StockOutRecord record);

    StockOutRecord selectByPrimaryKey(Long id);

    List<StockOutRecord> selectBySelective(StockOutRecord record);

    int updateByPrimaryKeySelective(StockOutRecord record);
}
