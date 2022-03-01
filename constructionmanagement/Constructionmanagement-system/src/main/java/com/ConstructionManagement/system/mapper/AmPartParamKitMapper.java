package com.ConstructionManagement.system.mapper;

import com.ConstructionManagement.system.domain.AmPartParamKit;
import com.ConstructionManagement.system.domain.AmTowerMachineParamPart;

import java.util.List;

public interface AmPartParamKitMapper {
    int deleteByPid(Long pid);
    int insertSelective(AmPartParamKit record);
    List<AmPartParamKit> selectByPid(Long pid);
}
