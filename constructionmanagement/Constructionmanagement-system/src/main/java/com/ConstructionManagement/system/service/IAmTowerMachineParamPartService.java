package com.ConstructionManagement.system.service;

import com.ConstructionManagement.system.domain.AmTowerMachineParamPart;
import org.springframework.stereotype.Service;

import java.util.List;


public interface IAmTowerMachineParamPartService {

    int deleteByPid(Long id);


    int insertSelective(AmTowerMachineParamPart record);
    List<AmTowerMachineParamPart> selectByPid(Long pid);

}
