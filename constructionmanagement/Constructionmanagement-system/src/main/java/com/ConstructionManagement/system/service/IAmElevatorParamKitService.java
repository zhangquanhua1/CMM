package com.ConstructionManagement.system.service;

import com.ConstructionManagement.system.domain.AmElevatorParamKit;

import java.util.List;

public interface IAmElevatorParamKitService {
    int deleteByPid(Long pid);

    int insertSelective(AmElevatorParamKit record);

    List<AmElevatorParamKit> selectByPid(Long pid);
}
