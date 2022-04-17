package com.ConstructionManagement.system.service.impl;

import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.ConstructionManagement.common.exception.ServiceException;
import com.ConstructionManagement.common.utils.SecurityUtils;
import com.ConstructionManagement.system.domain.AmPartRequire;
import com.ConstructionManagement.system.domain.AmPartRequireKit;
import com.ConstructionManagement.system.domain.WmPartEntry;
import com.ConstructionManagement.system.domain.WmPartEntryKit;
import com.ConstructionManagement.system.mapper.WmPartEntryKitMapper;
import com.ConstructionManagement.system.mapper.WmPartEntryMapper;
import com.ConstructionManagement.system.service.IWmPartEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class WmPartEntryServiceImpl implements IWmPartEntryService {
    @Autowired
    WmPartEntryMapper wpem;
    @Autowired
    WmPartEntryKitMapper wpemk;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return wpem.deleteByPrimaryKey(id);
    }

    @Override
    public int deleteByIds(Long[] ids) {
        return wpem.deleteByIds(ids);
    }

    @Override
    public int insertSelective(WmPartEntry record) {
        return wpem.insertSelective(record);
    }

    @Override
    public WmPartEntry selectByPrimaryKey(Long id) {
        return wpem.selectByPrimaryKey(id);
    }

    @Override
    public List<WmPartEntry> selectBySelective(WmPartEntry record) {
        return wpem.selectBySelective(record);
    }

    @Override
    public int updateByPrimaryKeySelective(WmPartEntry record) {
        return wpem.updateByPrimaryKeySelective(record);
    }

    @Override
    public int confirmByIds(Long[] ids) {
        return wpem.confirmByIds(ids);
    }

    @Override
    public int confirmById(Long id) {
        return wpem.confirmById(id);
    }

    @Override
    public int AntiConfirmByIds(Long[] ids) {
        return wpem.AntiConfirmByIds(ids);
    }

    @Override
    public String importData(ExcelImportResult<WmPartEntry> result, Boolean isUpdateSupport, String operName) {

        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        //校验正确的数据
        List<WmPartEntry> Succeslist = result.getList();
        for (WmPartEntry e : Succeslist) {
            e.setInsertPerson( SecurityUtils.getLoginUser().getUsername());
            e.setInsertPersonDepartId( SecurityUtils.getLoginUser().getDeptId());
            e.setInsertDate(new Date());
            this.insertSelective(e);
            List<WmPartEntryKit> kits =e.getKits();
            if(kits != null && !kits.isEmpty() && kits.size() > 0) {
                for (WmPartEntryKit kit : kits) {
                    kit.setPartId(e.getId());
                    wpemk.insertSelective(kit);
                }
            }
            successNum++;
            successMsg.append("<br/>" + successNum + "、产品编号 " + e.getProductNum() + " 导入成功");
        }
        if(result.isVerifyFail()){
            //失败的数据
            List<WmPartEntry> failList = result.getFailList();
            for(WmPartEntry fail:failList){
                failureMsg.append("<br/>" + "第"+(fail.getRowNum()+1)+"行，"+fail.getErrorMsg());
            }
            failureMsg.insert(0, "共 " + failList.size() + " 条数据导入失败，错误如下：");
            throw new ServiceException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }
}
