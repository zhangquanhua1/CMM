package com.ConstructionManagement.system.service.impl;

import com.ConstructionManagement.common.exception.ServiceException;
import com.ConstructionManagement.common.utils.SecurityUtils;
import com.ConstructionManagement.common.utils.StringUtils;
import com.ConstructionManagement.system.domain.AmKitRequire;
import com.ConstructionManagement.system.domain.WmKitEntry;
import com.ConstructionManagement.system.mapper.WmKitEntryMapper;
import com.ConstructionManagement.system.service.IWmKitEntryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class WmKitEntryServiceImpl implements IWmKitEntryService {
    private static Logger log = LoggerFactory.getLogger(AmKitRequireServiceImpl.class);
    @Autowired
    WmKitEntryMapper wke;

    @Override
    public int deleteByIds(Long[] ids) {
        return wke.deleteByIds(ids);
    }

    @Override
    public int insertSelective(WmKitEntry record) {
        return wke.insertSelective(record);
    }

    @Override
    public List<WmKitEntry> selectBySelective(WmKitEntry record) {
        return wke.selectBySelective(record);
    }

    @Override
    public WmKitEntry selectById(Long id) {
        return wke.selectById(id);
    }

    @Override
    public int updateByPrimaryKeySelective(WmKitEntry record) {
        return wke.updateByPrimaryKeySelective(record);
    }

    @Override
    public int confirmById(Long id) {
        return wke.confirmById(id);
    }

    @Override
    public int confirmByIds(Long[] ids) {
        return wke.confirmByIds(ids);
    }

    @Override
    public int AntiConfirmByIds(Long[] ids) {
        return wke.AntiConfirmByIds(ids);
    }

    @Override
    public String importData(List<WmKitEntry> list, Boolean isUpdateSupport, String operName) {

        if (StringUtils.isNull(list) || list.size() == 0) {
            throw new ServiceException("导入内容不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (WmKitEntry user : list) {
            try {
                user.setInsertPerson(SecurityUtils.getLoginUser().getUsername());
                user.setInsertPersonDepartId(SecurityUtils.getLoginUser().getDeptId());
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
