package com.ConstructionManagement.system.mapper;

import com.ConstructionManagement.system.domain.AmTowerMachineParamKit;

import java.util.List;

public interface AmTowerMachineParamKitMapper {
    int deleteByPid(Long pid);

    int insertSelective(AmTowerMachineParamKit record);

    List<AmTowerMachineParamKit> selectByPid(Long pid);
}
