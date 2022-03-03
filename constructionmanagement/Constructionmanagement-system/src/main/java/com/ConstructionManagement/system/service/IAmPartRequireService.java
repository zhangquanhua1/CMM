package com.ConstructionManagement.system.service;

import com.ConstructionManagement.system.domain.AmPartRequire;

import java.util.List;

public interface IAmPartRequireService {
    int deleteByIds(Long [] ids);
    int insertSelective(AmPartRequire record);

    List<AmPartRequire> selectBySelective(AmPartRequire record);

    int updateByPrimaryKeySelective(AmPartRequire record);
}
