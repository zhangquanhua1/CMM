package com.ConstructionManagement.system.service.impl;

import com.ConstructionManagement.system.domain.AmProtector;
import com.ConstructionManagement.system.mapper.AmProtectorMapper;
import com.ConstructionManagement.system.service.IAmProtectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AmProtectorServiceImpl implements IAmProtectorService {
    @Autowired
    AmProtectorMapper amProtectorMapper;
    @Override
    public int deleteByIds(Long[] ids) {
        return amProtectorMapper.deleteByIds(ids);
    }

    @Override
    public int insertSelective(AmProtector record) {
        return amProtectorMapper.insertSelective(record);
    }

    @Override
    public List<AmProtector> selectBySelective(AmProtector record) {
        return amProtectorMapper.selectBySelective(record);
    }


    @Override
    public int updateByPrimaryKeySelective(AmProtector record) {
        return amProtectorMapper.updateByPrimaryKeySelective(record);
    }
}
