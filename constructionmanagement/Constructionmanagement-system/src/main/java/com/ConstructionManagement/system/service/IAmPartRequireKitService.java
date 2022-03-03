package com.ConstructionManagement.system.service;

import com.ConstructionManagement.system.domain.AmPartRequireKit;
import org.springframework.stereotype.Service;

import java.util.List;


public interface IAmPartRequireKitService {
    int deleteByPid(Long partRequireId);

    int insertSelective(AmPartRequireKit record);

    List<AmPartRequireKit> selectByPid(Long partRequireId);
}
