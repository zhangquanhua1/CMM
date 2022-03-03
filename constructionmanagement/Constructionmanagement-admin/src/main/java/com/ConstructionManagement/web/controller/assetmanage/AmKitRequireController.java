package com.ConstructionManagement.web.controller.assetmanage;

import com.ConstructionManagement.common.annotation.Log;
import com.ConstructionManagement.common.core.controller.BaseController;
import com.ConstructionManagement.common.core.domain.AjaxResult;
import com.ConstructionManagement.common.core.page.TableDataInfo;
import com.ConstructionManagement.common.enums.BusinessType;
import com.ConstructionManagement.system.domain.AmKitRequire;
import com.ConstructionManagement.system.service.IAmKitRequireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 零配件需求管理
 */
@RestController
@RequestMapping("/asset/kitrequire")
public class AmKitRequireController extends BaseController {
    @Autowired
    IAmKitRequireService iAmKitRequireService;

    /**
     * 获取列表
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:kitrequire:list')")
    @GetMapping("/list")
    public TableDataInfo list(AmKitRequire amKitRequire) {
        startPage();
        List<AmKitRequire> amKitRequires = iAmKitRequireService.selectBySelective(amKitRequire);
        return getDataTable(amKitRequires);
    }

    /**
     * 删除
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:kitrequire:remove')")
    @Log(title = "零配件需求", businessType = BusinessType.DELETE)
    @DeleteMapping("/{Ids}")
    public AjaxResult remove(@PathVariable Long[] Ids)
    {
        return toAjax(iAmKitRequireService.deleteByIds(Ids));
    }

    /**
     * 新增
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:kitrequire:add')")
    @Log(title = "零配件需求", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody AmKitRequire amKitRequire)
    {
        if(amKitRequire==null)
            return AjaxResult.error("全空零配件需求禁止插入");
        amKitRequire.setInsertPerson(getUsername());
        amKitRequire.setInsertPersonDepartId(getDeptId());
        amKitRequire.setInsertDate(new Date());
        int result=iAmKitRequireService.insertSelective(amKitRequire);
        return toAjax(result);
    }

    /**
     * 修改保存
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:kitrequire:edit') or @ss.hasPermi('asset:manage:kitrequire:audit')")
    @Log(title = "零配件需求", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody AmKitRequire amKitRequire)
    {
        amKitRequire.setUpdatePerson(getUsername());
        amKitRequire.setUpdatePersonDepartId(getDeptId());
        amKitRequire.setUpdateDate(new Date());
        int result=iAmKitRequireService.updateByPrimaryKeySelective(amKitRequire);
        return toAjax(result);
    }
}
