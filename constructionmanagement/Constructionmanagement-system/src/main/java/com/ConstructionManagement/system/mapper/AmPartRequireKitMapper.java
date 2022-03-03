package com.ConstructionManagement.system.mapper;

import com.ConstructionManagement.system.domain.AmElevatorParamKit;
import com.ConstructionManagement.system.domain.AmPartRequireKit;

import java.util.List;

public interface AmPartRequireKitMapper {
    int deleteByPid(Long partRequireId);

    int insertSelective(AmPartRequireKit record);

    List<AmPartRequireKit> selectByPid(Long partRequireId);
}
