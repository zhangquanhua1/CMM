package com.ConstructionManagement.system.service.impl;

import com.ConstructionManagement.system.domain.StockOutRecord;
import com.ConstructionManagement.system.mapper.StockOutRecordMapper;
import com.ConstructionManagement.system.service.IStockOutRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockOutRecordServiceImpl implements IStockOutRecordService {
    @Autowired
    StockOutRecordMapper sor;
    @Override
    public int deleteByPrimaryKey(Long id) {
        return sor.deleteByPrimaryKey(id);
    }

    @Override
    public int deleteByIds(Long[] ids) {
        return sor.deleteByIds(ids);
    }

    @Override
    public int insertSelective(StockOutRecord record) {
        return sor.insertSelective(record);
    }

    @Override
    public StockOutRecord selectByPrimaryKey(Long id) {
        return sor.selectByPrimaryKey(id);
    }

    @Override
    public List<StockOutRecord> selectBySelective(StockOutRecord record) {
        return sor.selectBySelective(record);
    }

    @Override
    public int updateByPrimaryKeySelective(StockOutRecord record) {
        return sor.updateByPrimaryKeySelective(record);
    }
}
