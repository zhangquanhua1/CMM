package com.ConstructionManagement.system.service.impl;

import com.ConstructionManagement.system.domain.AmElevatorParamPart;
import com.ConstructionManagement.system.mapper.AmElevatorParamPartMapper;
import com.ConstructionManagement.system.service.IAmElevatorParamPartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AmElevatorParamPartServiceImpl implements IAmElevatorParamPartService {
    @Autowired
    AmElevatorParamPartMapper amElevatorParamPartMapper;
    @Override
    public int deleteByPid(Long pid) {
        return amElevatorParamPartMapper.deleteByPid(pid);
    }

    @Override
    public int insertSelective(AmElevatorParamPart record) {
        return amElevatorParamPartMapper.insertSelective(record);
    }

    @Override
    public List<AmElevatorParamPart> selectByPid(Long pid) {
        return amElevatorParamPartMapper.selectByPid(pid);
    }
}
