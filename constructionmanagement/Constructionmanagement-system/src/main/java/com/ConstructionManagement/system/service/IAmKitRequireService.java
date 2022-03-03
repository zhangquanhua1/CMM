package com.ConstructionManagement.system.service;

import com.ConstructionManagement.system.domain.AmKitRequire;

import java.util.List;

public interface IAmKitRequireService {
    int deleteByIds(Long ids[]);

    int insertSelective(AmKitRequire record);

    List<AmKitRequire> selectBySelective(AmKitRequire record);

    int updateByPrimaryKeySelective(AmKitRequire record);
}
