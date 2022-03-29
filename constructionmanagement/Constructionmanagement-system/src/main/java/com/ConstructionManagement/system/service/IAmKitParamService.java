package com.ConstructionManagement.system.service;

import com.ConstructionManagement.system.domain.AmKitParam;

import java.util.List;

public interface IAmKitParamService {
    int deleteById(Long id);

    int deleteByIds(Long[] ids);

    AmKitParam selectByPrimaryKey(Long id);

    List<AmKitParam> selectBySelective(AmKitParam record);

    int updateByPrimaryKeySelective(AmKitParam record);

    int insertSelective(AmKitParam record);
}
