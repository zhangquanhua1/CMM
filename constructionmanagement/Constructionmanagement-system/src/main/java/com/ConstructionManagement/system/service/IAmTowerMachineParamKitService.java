package com.ConstructionManagement.system.service;

import com.ConstructionManagement.system.domain.AmTowerMachineParamKit;

import java.util.List;

public interface IAmTowerMachineParamKitService {
    int deleteByPid(Long pid);

    int insertSelective(AmTowerMachineParamKit record);
    List<AmTowerMachineParamKit> selectByPid(Long pid);

}
