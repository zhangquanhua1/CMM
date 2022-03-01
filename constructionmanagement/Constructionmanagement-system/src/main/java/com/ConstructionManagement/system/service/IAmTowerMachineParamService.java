package com.ConstructionManagement.system.service;

import com.ConstructionManagement.system.domain.AmTowerMachineParam;

import java.util.List;

public interface IAmTowerMachineParamService {
    int deleteById(Long id);
    int deleteByIds(Long [] ids);

    int insertSelective(AmTowerMachineParam record);

    List<AmTowerMachineParam> selectBySelective(AmTowerMachineParam amTowerMachineParam);

    AmTowerMachineParam selectByParam(String towerMachineModel,String towerMachineName,String vender);

    int updateByPrimaryKeySelective(AmTowerMachineParam record);
}
