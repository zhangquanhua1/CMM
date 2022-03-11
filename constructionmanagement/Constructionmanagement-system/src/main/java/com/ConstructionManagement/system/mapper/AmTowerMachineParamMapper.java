package com.ConstructionManagement.system.mapper;

import com.ConstructionManagement.system.domain.AmTowerMachineParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AmTowerMachineParamMapper {

    int deleteById(Long id);
    int deleteByIds(Long [] ids);

    int insertSelective(AmTowerMachineParam record);

    List<AmTowerMachineParam> selectBySelective(AmTowerMachineParam amTowerMachineParam);
    AmTowerMachineParam selectByParam(String towerMachineModel,
                                      String towerMachineType);

    int updateByPrimaryKeySelective(AmTowerMachineParam record);

}
