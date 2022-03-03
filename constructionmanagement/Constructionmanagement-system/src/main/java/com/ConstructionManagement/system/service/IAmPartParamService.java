package com.ConstructionManagement.system.service;

import com.ConstructionManagement.system.domain.AmPartParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IAmPartParamService {
    int deleteByPrimaryKey(Long id);
    int deleteByIds(Long[] ids);
    int insertSelective(AmPartParam record);

    List<AmPartParam> selectBySelective(AmPartParam amPartParam);

    int updateByPrimaryKeySelective(AmPartParam record);

    AmPartParam selectByParam(@Param("partCode")String partCode, @Param("partModel")String partModel,
                              @Param("partName")String partName);
}
