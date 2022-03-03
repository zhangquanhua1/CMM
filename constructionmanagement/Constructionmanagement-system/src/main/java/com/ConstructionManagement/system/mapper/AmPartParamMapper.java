package com.ConstructionManagement.system.mapper;

import com.ConstructionManagement.system.domain.AmPartParam;
import com.ConstructionManagement.system.domain.AmTowerMachineParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AmPartParamMapper {
    int deleteByPrimaryKey(Long id);
    int deleteByIds(Long[] ids);
    int insertSelective(AmPartParam record);

    List<AmPartParam> selectBySelective(AmPartParam amPartParam);

    int updateByPrimaryKeySelective(AmPartParam record);

    AmPartParam selectByParam(@Param("partCode")String partCode, @Param("partModel")String partModel,
                                      @Param("partName")String partName);

}
