package com.ConstructionManagement.system.service.impl;

import com.ConstructionManagement.system.domain.AmKitRequire;
import com.ConstructionManagement.system.mapper.AmKitRequireMapper;
import com.ConstructionManagement.system.service.IAmKitRequireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AmKitRequireServiceImpl implements IAmKitRequireService {
    @Autowired
    AmKitRequireMapper amKitRequireMapper;
    @Override
    public int deleteByIds(Long[] ids) {
        return amKitRequireMapper.deleteByIds(ids);
    }

    @Override
    public int insertSelective(AmKitRequire record) {
        return amKitRequireMapper.insertSelective(record);
    }

    @Override
    public List<AmKitRequire> selectBySelective(AmKitRequire record) {
        return amKitRequireMapper.selectBySelective(record);
    }

    @Override
    public int updateByPrimaryKeySelective(AmKitRequire record) {
        return amKitRequireMapper.updateByPrimaryKeySelective(record);
    }
}
