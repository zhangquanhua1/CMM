package com.ConstructionManagement.system.mapper;

import com.ConstructionManagement.system.domain.AmTowerMachineParamKit;

public interface AmTowerMachineParamKitMapper {
    int deleteByPid(Long pid);

    int insertSelective(AmTowerMachineParamKit record);
}
