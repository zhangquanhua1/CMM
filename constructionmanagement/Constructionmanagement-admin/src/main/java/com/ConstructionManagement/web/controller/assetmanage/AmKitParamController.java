package com.ConstructionManagement.web.controller.assetmanage;

import com.ConstructionManagement.common.annotation.Log;
import com.ConstructionManagement.common.core.controller.BaseController;
import com.ConstructionManagement.common.core.domain.AjaxResult;
import com.ConstructionManagement.common.core.page.TableDataInfo;
import com.ConstructionManagement.common.enums.BusinessType;
import com.ConstructionManagement.system.domain.*;
import com.ConstructionManagement.system.service.IAmKitParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/asset/kitparam")
public class AmKitParamController extends BaseController {
    @Autowired
    IAmKitParamService iamKitParamService;
    /**
     * 获取列表
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:kitparam:list')")
    @GetMapping("/list")
    public TableDataInfo list(AmKitParam amKitParam) {
        //System.out.println("TowerMachineModel"+amTowerMachineParam.getTowerMachineModel());
        startPage();
        List<AmKitParam> amKitParams = iamKitParamService.selectBySelective(amKitParam);
        return getDataTable(amKitParams);
    }

    /**
     * 删除
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:kitparam:remove')")
    @Log(title = "零件参数", businessType = BusinessType.DELETE)
    @DeleteMapping("/{Ids}")
    public AjaxResult remove(@PathVariable Long[] Ids)
    {
        return toAjax(iamKitParamService.deleteByIds(Ids));
    }

    /**
     * 新增
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:kitparam:add')")
    @Log(title = "零件参数", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody AmKitParam amKitParam)
    {
        int result=iamKitParamService.insertSelective(amKitParam);
        return toAjax(result);
    }

    /**
     * 修改保存角色
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:kitparam:edit')")
    @Log(title = "零件参数", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody AmKitParam amKitParam)
    {
        int result=iamKitParamService.updateByPrimaryKeySelective(amKitParam);
        return toAjax(result);
    }

}
