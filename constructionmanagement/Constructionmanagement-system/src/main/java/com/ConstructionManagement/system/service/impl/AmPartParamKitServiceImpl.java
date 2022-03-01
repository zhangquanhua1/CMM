package com.ConstructionManagement.system.service.impl;

import com.ConstructionManagement.system.domain.AmPartParamKit;
import com.ConstructionManagement.system.mapper.AmPartParamKitMapper;
import com.ConstructionManagement.system.service.IAmPartParamKitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AmPartParamKitServiceImpl implements IAmPartParamKitService {
    @Autowired
    AmPartParamKitMapper amPartParamKitMapper;
    @Override
    public int deleteByPid(Long pid) {
        return amPartParamKitMapper.deleteByPid(pid);
    }

    @Override
    public int insertSelective(AmPartParamKit record) {
        return amPartParamKitMapper.insertSelective(record);
    }

    @Override
    public List<AmPartParamKit> selectByPid(Long pid) {
        return amPartParamKitMapper.selectByPid(pid);
    }
}
