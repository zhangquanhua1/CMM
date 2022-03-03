package com.ConstructionManagement.system.mapper;

import com.ConstructionManagement.system.domain.AmElevatorParamKit;
import com.ConstructionManagement.system.domain.AmElevatorParamPart;

import java.util.List;

public interface AmElevatorParamPartMapper {

    int deleteByPid(Long pid);

    int insertSelective(AmElevatorParamPart record);

    List<AmElevatorParamPart> selectByPid(Long pid);
}
