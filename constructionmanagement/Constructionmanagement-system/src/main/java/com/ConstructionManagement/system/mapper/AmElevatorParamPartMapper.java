package com.ConstructionManagement.system.mapper;

import com.ConstructionManagement.system.domain.AmElevatorParamKit;
import com.ConstructionManagement.system.domain.AmElevatorParamPart;

import java.util.List;

public interface AmElevatorParamPartMapper {

    int deleteByPid(Long elevatorId);

    int insertSelective(AmElevatorParamPart record);

    List<AmElevatorParamPart> selectByPid(Long elevatorId);
}
