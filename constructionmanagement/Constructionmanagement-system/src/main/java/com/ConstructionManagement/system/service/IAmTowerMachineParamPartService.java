package com.ConstructionManagement.system.service;

import com.ConstructionManagement.system.domain.AmTowerMachineParamPart;
import org.springframework.stereotype.Service;


public interface IAmTowerMachineParamPartService {

    int deleteByPid(Long id);


    int insertSelective(AmTowerMachineParamPart record);

}
