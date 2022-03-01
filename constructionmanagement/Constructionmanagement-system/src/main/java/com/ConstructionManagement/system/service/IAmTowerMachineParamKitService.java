package com.ConstructionManagement.system.service;

import com.ConstructionManagement.system.domain.AmTowerMachineParamKit;

public interface IAmTowerMachineParamKitService {
    int deleteByPid(Long pid);

    int insertSelective(AmTowerMachineParamKit record);

}
