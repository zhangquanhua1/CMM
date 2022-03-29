package com.ConstructionManagement.system.service;

import com.ConstructionManagement.common.core.domain.entity.SysUser;
import com.ConstructionManagement.system.domain.AmKitRequire;

import java.util.List;

public interface IAmKitRequireService {
    int deleteByIds(Long ids[]);

    int insertSelective(AmKitRequire record);

    List<AmKitRequire> selectBySelective(AmKitRequire record);

    int updateByPrimaryKeySelective(AmKitRequire record);

    int isBuyIds(Long ids[]);

    public String importData(List<AmKitRequire> list, Boolean isUpdateSupport, String operName);

}
