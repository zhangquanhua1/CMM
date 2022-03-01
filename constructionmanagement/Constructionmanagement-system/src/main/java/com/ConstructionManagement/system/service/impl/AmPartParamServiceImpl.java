package com.ConstructionManagement.system.service.impl;

import com.ConstructionManagement.system.domain.AmPartParam;
import com.ConstructionManagement.system.mapper.AmPartParamMapper;
import com.ConstructionManagement.system.service.IAmPartParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AmPartParamServiceImpl implements IAmPartParamService {
    @Autowired
    AmPartParamMapper amPartParamMapper;
    @Override
    public int deleteByPrimaryKey(Long id) {
        return amPartParamMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int deleteByIds(Long[] ids) {
        return amPartParamMapper.deleteByIds(ids);
    }

    @Override
    public int insertSelective(AmPartParam record) {
        return amPartParamMapper.insertSelective(record);
    }

    @Override
    public List<AmPartParam> selectBySelective(AmPartParam amPartParam) {
        return amPartParamMapper.selectBySelective(amPartParam);
    }

    @Override
    public int updateByPrimaryKeySelective(AmPartParam record) {
        return amPartParamMapper.updateByPrimaryKeySelective(record);
    }
}
