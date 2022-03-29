package com.ConstructionManagement.system.mapper;

import com.ConstructionManagement.system.domain.AmKitParam;
import com.ConstructionManagement.system.domain.AmTowerMachineParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AmKitParamMapper {
    int deleteById(Long id);

    int deleteByIds(Long[] ids);


    List<AmKitParam> selectBySelective(AmKitParam record);

    int updateByPrimaryKeySelective(AmKitParam record);

    int insertSelective(AmKitParam record);

    AmKitParam selectByPrimaryKey(Long id);
}
