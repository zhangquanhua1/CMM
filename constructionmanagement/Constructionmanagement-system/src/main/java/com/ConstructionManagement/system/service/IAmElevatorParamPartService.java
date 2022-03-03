package com.ConstructionManagement.system.service;

import com.ConstructionManagement.system.domain.AmElevatorParamPart;

import java.util.List;

public interface IAmElevatorParamPartService {
    int deleteByPid(Long pid);

    int insertSelective(AmElevatorParamPart record);

    List<AmElevatorParamPart> selectByPid(Long pid);
}
