package com.ConstructionManagement.system.mapper;

import com.ConstructionManagement.system.domain.AmElevatorParam;

import java.util.List;

public interface AmElevatorParamMapper {
    int deleteById(Long id);
    int deleteByIds(Long [] ids);
    int insertSelective(AmElevatorParam record);
    List<AmElevatorParam> selectBySelective(AmElevatorParam record);
    int updateByPrimaryKeySelective(AmElevatorParam record);
}
