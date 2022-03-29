package com.ConstructionManagement.system.service.impl;

import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.ConstructionManagement.common.exception.ServiceException;
import com.ConstructionManagement.common.utils.SecurityUtils;
import com.ConstructionManagement.system.domain.*;
import com.ConstructionManagement.system.mapper.WmEquipmentEntryKitMapper;
import com.ConstructionManagement.system.mapper.WmEquipmentEntryMapper;
import com.ConstructionManagement.system.mapper.WmEquipmentEntryPartMapper;
import com.ConstructionManagement.system.service.IWmEquipmentEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class WmEquipmentEntryServiceImpl implements IWmEquipmentEntryService {
    @Autowired
    WmEquipmentEntryMapper we;
    @Autowired
    WmEquipmentEntryKitMapper wek;
    @Autowired
    WmEquipmentEntryPartMapper wep;

    @Override
    public int deleteByIds(Long[] ids) {
        return we.deleteByIds(ids);
    }

    @Override
    public int insertSelective(WmEquipmentEntry record) {
        return we.insertSelective(record);
    }

    @Override
    public List<WmEquipmentEntry> selectBySelective(WmEquipmentEntry record) {
        return we.selectBySelective(record);
    }

    @Override
    public WmEquipmentEntry selectById(Long id) {
        return we.selectById(id);
    }

    @Override
    public int updateByPrimaryKeySelective(WmEquipmentEntry record) {
        return we.updateByPrimaryKeySelective(record);
    }

    @Override
    public int confirmByIds(Long[] ids) {
        return we.confirmByIds(ids);
    }

    @Override
    public int confirmById(Long id) {
        return we.confirmById(id);
    }

    @Override
    public int AntiConfirmByIds(Long[] ids) {
        return we.AntiConfirmByIds(ids);
    }

    @Override
    public String importData(ExcelImportResult<WmEquipmentEntry> result, Boolean isUpdateSupport, String operName) {

        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        //校验正确的数据
        List<WmEquipmentEntry> Succeslist = result.getList();
        for (WmEquipmentEntry e : Succeslist) {
            e.setInsertPerson( SecurityUtils.getLoginUser().getUsername());
            e.setInsertPersonDepartId( SecurityUtils.getLoginUser().getDeptId());
            e.setInsertDate(new Date());
            this.insertSelective(e);
            List<WmEquipmentEntryKit> kits =e.getWmEquipmentEntryKits();
            List<WmEquipmentEntryPart> parts=e.getWmEquipmentEntryParts();
            if(kits != null && !kits.isEmpty() && kits.size() > 0) {
                for (WmEquipmentEntryKit kit : kits) {
                    kit.setEquimentId(e.getId());
                    wek.insertSelective(kit);
                }
            }
            if(parts != null && !parts.isEmpty() && parts.size() > 0){
                for (WmEquipmentEntryPart part : parts){
                    part.setEquipmentId(e.getId());
                    wep.insertSelective(part);
                }
            }
            successNum++;
            successMsg.append("<br/>" + successNum + "、设备类别 " + e.getEquipmentType() +" "+"产品编号 "+e.getEquipmentNumber()+" 导入成功");
        }
        if(result.isVerifyFail()){
            //失败的数据
            List<WmEquipmentEntry> failList = result.getFailList();
            for(WmEquipmentEntry fail:failList){
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
