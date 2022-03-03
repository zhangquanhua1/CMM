package com.ConstructionManagement.system.service;

import com.ConstructionManagement.system.domain.AmElevatorParam;

import java.util.List;

public interface IAmElevatorParamService {
    int deleteById(Long id);
    int deleteByIds(Long [] ids);
    int insertSelective(AmElevatorParam record);
    List<AmElevatorParam> selectBySelective(AmElevatorParam record);
    int updateByPrimaryKeySelective(AmElevatorParam record);
}
