package com.ConstructionManagement.system.service.impl;

import com.ConstructionManagement.system.domain.AmElevatorParamKit;
import com.ConstructionManagement.system.mapper.AmElevatorParamKitMapper;
import com.ConstructionManagement.system.service.IAmElevatorParamKitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AmElevatorParamKitServiceImpl implements IAmElevatorParamKitService {
    @Autowired
    AmElevatorParamKitMapper amElevatorParamKitMapper;
    @Override
    public int deleteByPid(Long pid) {
        return amElevatorParamKitMapper.deleteByPid(pid);
    }

    @Override
    public int insertSelective(AmElevatorParamKit record) {
        return amElevatorParamKitMapper.insertSelective(record);
    }

    @Override
    public List<AmElevatorParamKit> selectByPid(Long pid) {
        return amElevatorParamKitMapper.selectByPid(pid);
    }
}
