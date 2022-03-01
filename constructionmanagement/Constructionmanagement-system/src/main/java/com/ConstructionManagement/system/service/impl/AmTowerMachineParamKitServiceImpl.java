package com.ConstructionManagement.system.service.impl;

import com.ConstructionManagement.system.domain.AmTowerMachineParamKit;
import com.ConstructionManagement.system.mapper.AmTowerMachineParamKitMapper;
import com.ConstructionManagement.system.service.IAmTowerMachineParamKitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AmTowerMachineParamKitServiceImpl implements IAmTowerMachineParamKitService {
    @Autowired
    AmTowerMachineParamKitMapper amTowerMachineParamKitMapper;
    @Override
    public int deleteByPid(Long pid) {
        return amTowerMachineParamKitMapper.deleteByPid(pid);
    }



    @Override
    public int insertSelective(AmTowerMachineParamKit record) {
        return amTowerMachineParamKitMapper.insertSelective(record);
    }

}
