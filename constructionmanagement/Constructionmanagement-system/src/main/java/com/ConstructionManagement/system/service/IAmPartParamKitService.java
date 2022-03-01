package com.ConstructionManagement.system.service;

import com.ConstructionManagement.system.domain.AmPartParamKit;

import java.util.List;

public interface IAmPartParamKitService {
    int deleteByPid(Long pid);
    int insertSelective(AmPartParamKit record);
    List<AmPartParamKit> selectByPid(Long pid);
}
