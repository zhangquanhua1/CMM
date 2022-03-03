package com.ConstructionManagement.web.controller.assetmanage;

import com.ConstructionManagement.common.annotation.Log;
import com.ConstructionManagement.common.core.controller.BaseController;
import com.ConstructionManagement.common.core.domain.AjaxResult;
import com.ConstructionManagement.common.core.page.TableDataInfo;
import com.ConstructionManagement.common.enums.BusinessType;
import com.ConstructionManagement.system.domain.*;
import com.ConstructionManagement.system.service.IAmPartRequireKitService;
import com.ConstructionManagement.system.service.IAmPartRequireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 部件需求管理
 */
@RestController
@RequestMapping("/asset/partrequire")
public class AmPartRequireController extends BaseController {
    @Autowired
    IAmPartRequireService iAmPartRequireService;
    @Autowired
    IAmPartRequireKitService iAmPartRequireKitService;
    /**
     * 获取列表
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:partrequire:list')")
    @GetMapping("/list")
    public TableDataInfo list(AmPartRequire amPartRequire) {
        startPage();
        List<AmPartRequire> amPartRequires = iAmPartRequireService.selectBySelective(amPartRequire);
        return getDataTable(amPartRequires);
    }
    /**
     * 获取部件 配件
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:partrequire:list')")
    @GetMapping("/kit/{pid}")
    public AjaxResult getlist(@PathVariable Long pid) {
        //System.out.println("pid==="+pid);
        if(pid<=0||pid==null) return AjaxResult.error("该部件不存在配件");
        return AjaxResult.success(iAmPartRequireKitService.selectByPid(pid));
    }

    /**
     * 删除
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:partrequire:remove')")
    @Log(title = "部件需求", businessType = BusinessType.DELETE)
    @DeleteMapping("/{Ids}")
    public AjaxResult remove(@PathVariable Long[] Ids)
    {
        return toAjax(iAmPartRequireService.deleteByIds(Ids));
    }

    /**
     * 新增
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:partrequire:add')")
    @Log(title = "部件需求", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody AmPartRequire amPartRequire)
    {
        if(amPartRequire==null)
            return AjaxResult.error("全空部件需求禁止插入");
        Long pid;
        amPartRequire.setInsertPerson(getUsername());
        amPartRequire.setInsertPersonDepartId(getDeptId());
        amPartRequire.setInsertDate(new Date());
        int result=iAmPartRequireService.insertSelective(amPartRequire);
        if(result<0)
            return AjaxResult.error("添加失败");
        pid= amPartRequire.getId();
        List<AmPartRequireKit> kits=amPartRequire.getAmPartRequireKits();
        if(kits!=null&&kits.size()>0&&!kits.isEmpty()) {
            for (AmPartRequireKit kit : kits) {
                kit.setPartRequireId(pid);
                result *= iAmPartRequireKitService.insertSelective(kit);

            }
        }
        return toAjax(result);
    }

    /**
     * 修改保存角色
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:partrequire:edit') or @ss.hasPermi('asset:manage:partrequire:audit')")
    @Log(title = "部件需求", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody AmPartRequire amPartRequire)
    {
        Long pid= amPartRequire.getId();
        List<AmPartRequireKit> kits=amPartRequire.getAmPartRequireKits();
        amPartRequire.setUpdatePerson(getUsername());
        amPartRequire.setUpdatePersonDepartId(getDeptId());
        amPartRequire.setUpdateDate(new Date());
        int result=iAmPartRequireService.updateByPrimaryKeySelective(amPartRequire);
        if(kits.isEmpty()||kits.size()<=0||kits==null)
            return toAjax(result);
       result*=iAmPartRequireKitService.deleteByPid(pid);

        for (AmPartRequireKit kit: kits) {
            kit.setPartRequireId(pid);
            result*=iAmPartRequireKitService.insertSelective(kit);
        }
        return toAjax(result);
    }



}
