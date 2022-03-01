package com.ConstructionManagement.system.service.impl;

import com.ConstructionManagement.system.domain.AmKitParam;
import com.ConstructionManagement.system.mapper.AmKitParamMapper;
import com.ConstructionManagement.system.service.IAmKitParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AmKitParamServiceImpl implements IAmKitParamService {
    @Autowired
    AmKitParamMapper amKitParamMapper;
    @Override
    public int deleteById(Long id) {
        return amKitParamMapper.deleteById(id);
    }

    @Override
    public int deleteByIds(Long[] ids) {
        return amKitParamMapper.deleteByIds(ids);
    }

    @Override
    public List<AmKitParam> selectBySelective(AmKitParam record) {
        return amKitParamMapper.selectBySelective(record);
    }

    @Override
    public int updateByPrimaryKeySelective(AmKitParam record) {
        return amKitParamMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int insertSelective(AmKitParam record) {
        return amKitParamMapper.insertSelective(record);
    }
}
