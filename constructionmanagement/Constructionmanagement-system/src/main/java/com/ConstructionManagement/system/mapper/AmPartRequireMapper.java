package com.ConstructionManagement.system.mapper;

import com.ConstructionManagement.system.domain.AmPartRequire;

import java.util.List;

public interface AmPartRequireMapper {
    int deleteByIds(Long [] ids);
    int insertSelective(AmPartRequire record);

    List<AmPartRequire> selectBySelective(AmPartRequire record);

    int updateByPrimaryKeySelective(AmPartRequire record);

}
