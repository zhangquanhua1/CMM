package com.ConstructionManagement.system.service.impl;

import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.ConstructionManagement.common.exception.ServiceException;
import com.ConstructionManagement.common.utils.SecurityUtils;
import com.ConstructionManagement.system.domain.*;
import com.ConstructionManagement.system.mapper.AmEquipmentRequireKitMapper;
import com.ConstructionManagement.system.mapper.AmEquipmentRequireMapper;
import com.ConstructionManagement.system.mapper.AmEquipmentRequirePartMapper;
import com.ConstructionManagement.system.service.IAmEquipmentRequireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AmEquipmentRequireServiceImpl implements IAmEquipmentRequireService {
    @Autowired
    AmEquipmentRequireMapper am;
    @Autowired
    AmEquipmentRequireKitMapper amk;
    @Autowired
    AmEquipmentRequirePartMapper amp;

    @Override
    public int deleteByIds(Long[] ids) {
        return am.deleteByIds(ids);
    }

    @Override
    public int insertSelective(AmEquipmentRequire record) {
        return am.insertSelective(record);
    }

    @Override
    public List<AmEquipmentRequire> selectBySelective(AmEquipmentRequire record) {
        return am.selectBySelective(record);
    }

    @Override
    public int updateByPrimaryKeySelective(AmEquipmentRequire record) {
        return am.updateByPrimaryKeySelective(record);
    }

    @Override
    public int isBuyIds(Long[] ids) {
        return am.isBuyIds(ids);
    }

    @Override
    public String importData(ExcelImportResult<AmEquipmentRequire> result, Boolean isUpdateSupport, String operName) {

        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        //校验正确的数据
        List<AmEquipmentRequire> Succeslist = result.getList();
        for (AmEquipmentRequire e : Succeslist) {
            e.setInsertPerson( SecurityUtils.getLoginUser().getUsername());
            e.setInsertPersonDepartId( SecurityUtils.getLoginUser().getDeptId());
            e.setInsertDate(new Date());
            this.insertSelective(e);
            List<AmEquipmentRequireKit> kits =e.getAmEquipmentRequireKits();
            List<AmEquipmentRequirePart> parts=e.getAmEquipmentRequireParts();
            if(kits != null && !kits.isEmpty() && kits.size() > 0) {
                for (AmEquipmentRequireKit kit : kits) {
                    kit.setEquimentId(e.getId());
                    amk.insertSelective(kit);
                }
            }
            if(parts != null && !parts.isEmpty() && parts.size() > 0){
                for (AmEquipmentRequirePart part : parts){
                    part.setEquipmentId(e.getId());
                    amp.insertSelective(part);
                }
            }
            successNum++;
            successMsg.append("<br/>" + successNum + "、设备类别 " + e.getEquipmentType() +" "+"产品编号 "+e.getEquipmentNumber()+" 导入成功");
        }
        if(result.isVerifyFail()){
            //失败的数据
            List<AmEquipmentRequire> failList = result.getFailList();
            for(AmEquipmentRequire fail:failList){
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
