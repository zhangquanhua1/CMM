package com.ConstructionManagement.system.service.impl;

import com.ConstructionManagement.system.domain.AmElevatorParam;
import com.ConstructionManagement.system.mapper.AmElevatorParamMapper;
import com.ConstructionManagement.system.service.IAmElevatorParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AmElevatorParamServiceImpl implements IAmElevatorParamService {
    @Autowired
    AmElevatorParamMapper amElevatorParamMapper;
    @Override
    public int deleteById(Long id) {
        return amElevatorParamMapper.deleteById(id);
    }

    @Override
    public int deleteByIds(Long[] ids) {
        return amElevatorParamMapper.deleteByIds(ids);
    }

    @Override
    public int insertSelective(AmElevatorParam record) {
        return amElevatorParamMapper.insertSelective(record);
    }

    @Override
    public List<AmElevatorParam> selectBySelective(AmElevatorParam record) {
        return amElevatorParamMapper.selectBySelective(record);
    }

    @Override
    public int updateByPrimaryKeySelective(AmElevatorParam record) {
        return amElevatorParamMapper.updateByPrimaryKeySelective(record);
    }
}
