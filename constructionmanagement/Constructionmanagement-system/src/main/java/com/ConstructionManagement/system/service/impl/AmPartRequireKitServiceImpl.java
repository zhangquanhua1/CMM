package com.ConstructionManagement.system.service.impl;

import com.ConstructionManagement.system.domain.AmPartRequireKit;
import com.ConstructionManagement.system.mapper.AmPartRequireKitMapper;
import com.ConstructionManagement.system.service.IAmPartRequireKitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AmPartRequireKitServiceImpl implements IAmPartRequireKitService {
    @Autowired
    AmPartRequireKitMapper amPartRequireKitMapper;
    @Override
    public int deleteByPid(Long partRequireId) {
        return amPartRequireKitMapper.deleteByPid(partRequireId);
    }

    @Override
    public int insertSelective(AmPartRequireKit record) {
        return amPartRequireKitMapper.insertSelective(record);
    }

    @Override
    public List<AmPartRequireKit> selectByPid(Long partRequireId) {
        return amPartRequireKitMapper.selectByPid(partRequireId);
    }
}
