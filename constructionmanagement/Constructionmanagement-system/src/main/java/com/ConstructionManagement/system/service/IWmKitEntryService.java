package com.ConstructionManagement.system.service;

import com.ConstructionManagement.system.domain.AmKitRequire;
import com.ConstructionManagement.system.domain.WmKitEntry;

import java.util.List;

public interface IWmKitEntryService {
    int deleteByIds(Long[] ids);

    int insertSelective(WmKitEntry record);

    List<WmKitEntry> selectBySelective(WmKitEntry record);

    WmKitEntry selectById(Long id);

    int updateByPrimaryKeySelective(WmKitEntry record);

    int confirmById(Long id);

    int confirmByIds(Long[] ids);

    int AntiConfirmByIds(Long[] ids);

    public String importData(List<WmKitEntry> list, Boolean isUpdateSupport, String operName);
}
