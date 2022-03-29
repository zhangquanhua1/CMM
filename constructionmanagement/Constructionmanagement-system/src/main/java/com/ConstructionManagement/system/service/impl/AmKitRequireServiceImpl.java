package com.ConstructionManagement.system.service.impl;

import com.ConstructionManagement.common.exception.ServiceException;
import com.ConstructionManagement.common.utils.SecurityUtils;
import com.ConstructionManagement.common.utils.StringUtils;
import com.ConstructionManagement.system.domain.AmKitRequire;
import com.ConstructionManagement.system.mapper.AmKitRequireMapper;
import com.ConstructionManagement.system.service.IAmKitRequireService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AmKitRequireServiceImpl implements IAmKitRequireService {
    private static Logger log = LoggerFactory.getLogger(AmKitRequireServiceImpl.class);
    @Autowired
    AmKitRequireMapper amKitRequireMapper;

    @Override
    public int deleteByIds(Long[] ids) {
        return amKitRequireMapper.deleteByIds(ids);
    }

    @Override
    public int insertSelective(AmKitRequire record) {
        return amKitRequireMapper.insertSelective(record);
    }

    @Override
    public List<AmKitRequire> selectBySelective(AmKitRequire record) {
        return amKitRequireMapper.selectBySelective(record);
    }

    @Override
    public int updateByPrimaryKeySelective(AmKitRequire record) {
        return amKitRequireMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int isBuyIds(Long[] ids) {
        return amKitRequireMapper.isBuyIds(ids);
    }

    @Override
    public String importData(List<AmKitRequire> list, Boolean isUpdateSupport, String operName) {

        if (StringUtils.isNull(list) || list.size() == 0) {
            throw new ServiceException("导入配件需求不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (AmKitRequire user : list) {
            try {
                user.setInsertPerson( SecurityUtils.getLoginUser().getUsername());
                user.setInsertPersonDepartId( SecurityUtils.getLoginUser().getDeptId());
                user.setInsertDate(new Date());
                this.insertSelective(user);
                successNum++;
                successMsg.append("<br/>" + successNum + "、产品编号 " + user.getProductNum() + " 导入成功");

            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、产品编号 " + user.getProductNum() + " 导入失败：";
                failureMsg.append(msg + "该条数据格式有误");
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }
}
