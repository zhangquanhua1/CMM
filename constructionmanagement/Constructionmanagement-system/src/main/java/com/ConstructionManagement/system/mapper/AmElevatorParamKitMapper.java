package com.ConstructionManagement.system.mapper;

import com.ConstructionManagement.system.domain.AmElevatorParamKit;
import com.ConstructionManagement.system.domain.AmTowerMachineParamKit;

import java.util.List;

public interface AmElevatorParamKitMapper {

    int deleteByPid(Long pid);

    int insertSelective(AmElevatorParamKit record);

    List<AmElevatorParamKit> selectByPid(Long pid);
}
