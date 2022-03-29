package com.ConstructionManagement.system.service.impl;

import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.ConstructionManagement.common.exception.ServiceException;
import com.ConstructionManagement.common.utils.SecurityUtils;
import com.ConstructionManagement.system.domain.AmKitRequire;
import com.ConstructionManagement.system.domain.AmPartRequire;
import com.ConstructionManagement.system.domain.AmPartRequireKit;
import com.ConstructionManagement.system.mapper.AmPartRequireKitMapper;
import com.ConstructionManagement.system.mapper.AmPartRequireMapper;
import com.ConstructionManagement.system.service.IAmPartRequireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class AmPartRequireServiceImpl implements IAmPartRequireService {
    @Autowired
    AmPartRequireMapper amPartRequireMapper;
    @Autowired
    AmPartRequireKitMapper aprk;
    @Override
    public int deleteByIds(Long[] ids) {
        return amPartRequireMapper.deleteByIds(ids);
    }

    @Override
    public int insertSelective(AmPartRequire record) {
        return amPartRequireMapper.insertSelective(record);
    }

    @Override
    public List<AmPartRequire> selectBySelective(AmPartRequire record) {
        return amPartRequireMapper.selectBySelective(record);
    }

    @Override
    public int updateByPrimaryKeySelective(AmPartRequire record) {
        return amPartRequireMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int isBuyIds(Long[] ids) {
        return amPartRequireMapper.isBuyIds(ids);
    }

    @Override
    @Transactional
    public String importData(ExcelImportResult<AmPartRequire> result, Boolean isUpdateSupport, String operName) {

        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        //校验正确的数据
        List<AmPartRequire> Succeslist = result.getList();
        for (AmPartRequire e : Succeslist) {
                e.setInsertPerson( SecurityUtils.getLoginUser().getUsername());
                e.setInsertPersonDepartId( SecurityUtils.getLoginUser().getDeptId());
                e.setInsertDate(new Date());
                this.insertSelective(e);
                List<AmPartRequireKit> kits =e.getAmPartRequireKits();
                if(kits != null && !kits.isEmpty() && kits.size() > 0) {
                    for (AmPartRequireKit kit : kits) {
                        kit.setPartID(e.getId());
                        aprk.insertSelective(kit);
                    }
                }
            successNum++;
            successMsg.append("<br/>" + successNum + "、产品编号 " + e.getProductNum() + " 导入成功");
        }
        if(result.isVerifyFail()){
            //失败的数据
            List<AmPartRequire> failList = result.getFailList();
            for(AmPartRequire fail:failList){
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
