package com.ConstructionManagement.system.service.impl;

import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.ConstructionManagement.common.exception.ServiceException;
import com.ConstructionManagement.common.utils.SecurityUtils;
import com.ConstructionManagement.system.domain.AmPartRequire;
import com.ConstructionManagement.system.domain.AmPartRequireKit;
import com.ConstructionManagement.system.domain.OutStockApply;
import com.ConstructionManagement.system.mapper.OutStockApplyMapper;
import com.ConstructionManagement.system.service.IOutStockApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OutStockApplyServiceImpl implements IOutStockApplyService {
    @Autowired
    OutStockApplyMapper os;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return os.deleteByPrimaryKey(id);
    }

    @Override
    public int deleteByIds(Long[] ids) {
        return os.deleteByIds(ids);
    }

    @Override
    public int insertSelective(OutStockApply record) {
        return os.insertSelective(record);
    }

    @Override
    public OutStockApply selectByPrimaryKey(Long id) {
        return os.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(OutStockApply record) {
        return os.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<OutStockApply> selectBySelective(OutStockApply record) {
        return os.selectBySelective(record);
    }

    @Override
    public String importData(ExcelImportResult<OutStockApply> result, Boolean isUpdateSupport, String operName) {
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        //校验正确的数据
        List<OutStockApply> Succeslist = result.getList();
        for (OutStockApply e : Succeslist) {
            this.insertSelective(e);
            successNum++;
            successMsg.append("<br/>" + successNum + "、名称 " + e.getName()+" 、型号"+e.getModel() + " 导入成功");
        }
        if(result.isVerifyFail()){
            //失败的数据
            List<OutStockApply> failList = result.getFailList();
            for(OutStockApply fail:failList){
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
