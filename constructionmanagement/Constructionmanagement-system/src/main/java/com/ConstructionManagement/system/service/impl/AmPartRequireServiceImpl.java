package com.ConstructionManagement.system.service.impl;

import com.ConstructionManagement.system.domain.AmPartRequire;
import com.ConstructionManagement.system.mapper.AmPartRequireMapper;
import com.ConstructionManagement.system.service.IAmPartRequireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AmPartRequireServiceImpl implements IAmPartRequireService {
    @Autowired
    AmPartRequireMapper amPartRequireMapper;
    @Override
    public int deleteByIds(Long[] ids) {
        return amPartRequireMapper.deleteByIds(ids);
    }

    @Override
    public int insertSelective(AmPartRequire record) {
        return amPartRequireMapper.insertSelective(record);
    }

    @Override
    public List<AmPartRequire> selectBySelective(AmPartRequire record) {
        return amPartRequireMapper.selectBySelective(record);
    }

    @Override
    public int updateByPrimaryKeySelective(AmPartRequire record) {
        return amPartRequireMapper.updateByPrimaryKeySelective(record);
    }

}
