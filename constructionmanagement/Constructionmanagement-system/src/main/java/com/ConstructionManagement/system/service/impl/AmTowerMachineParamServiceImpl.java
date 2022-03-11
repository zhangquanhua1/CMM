package com.ConstructionManagement.system.service.impl;

import com.ConstructionManagement.system.domain.AmTowerMachineParam;
import com.ConstructionManagement.system.mapper.AmTowerMachineParamMapper;
import com.ConstructionManagement.system.service.IAmTowerMachineParamService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AmTowerMachineParamServiceImpl implements IAmTowerMachineParamService {

    @Autowired
    AmTowerMachineParamMapper amTowerMachineParamMapper;
    @Override
    public int deleteById(Long id) {
        return amTowerMachineParamMapper.deleteById(id);
    }

    @Override
    public int deleteByIds(Long[] ids) {
        return amTowerMachineParamMapper.deleteByIds(ids);
    }

    @Override
    public int insertSelective(AmTowerMachineParam record) {
        return amTowerMachineParamMapper.insertSelective(record);
    }

    @Override
    public List<AmTowerMachineParam> selectBySelective(AmTowerMachineParam amTowerMachineParam) {
        return amTowerMachineParamMapper.selectBySelective(amTowerMachineParam);
    }

    @Override
    public AmTowerMachineParam selectByParam(String towerMachineModel, String towerMachineType) {
        return amTowerMachineParamMapper.selectByParam(towerMachineModel, towerMachineType);
    }

    @Override
    public int updateByPrimaryKeySelective(AmTowerMachineParam record) {
        return amTowerMachineParamMapper.updateByPrimaryKeySelective(record);
    }
}
