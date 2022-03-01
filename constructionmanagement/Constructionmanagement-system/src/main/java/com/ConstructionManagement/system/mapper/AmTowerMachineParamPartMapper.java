package com.ConstructionManagement.system.mapper;

import com.ConstructionManagement.system.domain.AmTowerMachineParamPart;

import java.util.List;

public interface AmTowerMachineParamPartMapper {

    int deleteByPid(Long id);
    int insertSelective(AmTowerMachineParamPart record);
    List<AmTowerMachineParamPart> selectByPid(Long pid);
}
