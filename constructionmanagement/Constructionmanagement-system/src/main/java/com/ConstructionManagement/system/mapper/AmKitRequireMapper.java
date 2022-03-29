package com.ConstructionManagement.system.mapper;

import com.ConstructionManagement.system.domain.AmKitRequire;

import java.util.List;

public interface AmKitRequireMapper {
    int deleteByIds(Long ids[]);

    int insertSelective(AmKitRequire record);

    List<AmKitRequire> selectBySelective(AmKitRequire record);

    int updateByPrimaryKeySelective(AmKitRequire record);

    int isBuyIds(Long ids[]);

}
