package com.ConstructionManagement.system.service.impl;

import com.ConstructionManagement.system.domain.AmTowerMachineParamPart;
import com.ConstructionManagement.system.mapper.AmTowerMachineParamPartMapper;
import com.ConstructionManagement.system.service.IAmTowerMachineParamPartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AmTowerMachineParamPartServiceImpl implements IAmTowerMachineParamPartService {
    @Autowired
    AmTowerMachineParamPartMapper amTowerMachineParamPartMapper;
    @Override
    public int deleteByPid(Long pid) {
        return amTowerMachineParamPartMapper.deleteByPid(pid);
    }


    @Override
    public int insertSelective(AmTowerMachineParamPart record) {
        return amTowerMachineParamPartMapper.insertSelective(record);
    }

}
