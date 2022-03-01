package com.ConstructionManagement.system.service;

import com.ConstructionManagement.system.domain.AmPartParam;

import java.util.List;

public interface IAmPartParamService {
    int deleteByPrimaryKey(Long id);
    int deleteByIds(Long[] ids);
    int insertSelective(AmPartParam record);

    List<AmPartParam> selectBySelective(AmPartParam amPartParam);

    int updateByPrimaryKeySelective(AmPartParam record);
}
