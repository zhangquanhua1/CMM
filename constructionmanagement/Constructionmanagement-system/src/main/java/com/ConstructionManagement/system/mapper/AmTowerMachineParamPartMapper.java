package com.ConstructionManagement.system.mapper;

import com.ConstructionManagement.system.domain.AmTowerMachineParamPart;

public interface AmTowerMachineParamPartMapper {

    int deleteByPid(Long id);
    int insertSelective(AmTowerMachineParamPart record);
}
