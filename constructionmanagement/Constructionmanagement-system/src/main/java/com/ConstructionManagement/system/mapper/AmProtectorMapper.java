package com.ConstructionManagement.system.mapper;

import com.ConstructionManagement.system.domain.AmProtector;

import java.util.List;

public interface AmProtectorMapper {

    int deleteByIds(Long[] ids);


    int insertSelective(AmProtector record);

    List<AmProtector> selectBySelective(AmProtector record);

    int updateByPrimaryKeySelective(AmProtector record);
}
