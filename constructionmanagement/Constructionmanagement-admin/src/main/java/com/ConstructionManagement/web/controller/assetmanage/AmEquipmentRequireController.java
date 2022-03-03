package com.ConstructionManagement.web.controller.assetmanage;

import com.ConstructionManagement.common.annotation.Log;
import com.ConstructionManagement.common.core.controller.BaseController;
import com.ConstructionManagement.common.core.domain.AjaxResult;
import com.ConstructionManagement.common.core.page.TableDataInfo;
import com.ConstructionManagement.common.enums.BusinessType;
import com.ConstructionManagement.system.domain.AmEquipmentRequire;
import com.ConstructionManagement.system.service.IAmEquipmentRequireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/asset/equipmentrequire")
public class AmEquipmentRequireController extends BaseController {
    @Autowired
    IAmEquipmentRequireService ier;
    /**
     * 获取列表
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:equipmentrequire:list')")
    @GetMapping("/list")
    public TableDataInfo list(AmEquipmentRequire aer) {
        startPage();
        List<AmEquipmentRequire> aers = ier.selectBySelective(aer);
        return getDataTable(aers);
    }

    /**
     * 删除
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:equipmentrequire:remove')")
    @Log(title = "设备需求", businessType = BusinessType.DELETE)
    @DeleteMapping("/{Ids}")
    public AjaxResult remove(@PathVariable Long[] Ids)
    {
        return toAjax(ier.deleteByIds(Ids));
    }

    /**
     * 新增
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:equipmentrequire:add')")
    @Log(title = "设备需求", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody AmEquipmentRequire aer)
    {
        if(aer==null)
            return AjaxResult.error("全空设备需求禁止插入");
        aer.setInsertPerson(getUsername());
        aer.setInsertPersonDepartId(getDeptId());
        aer.setInsertDate(new Date());
        int result=ier.insertSelective(aer);
        return toAjax(result);
    }
    /**
     * 修改保存
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:equipmentrequire:edit') or @ss.hasPermi('asset:manage:equipmentrequire:audit')" )
    @Log(title = "设备需求", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody AmEquipmentRequire aer)
    {
        aer.setUpdatePerson(getUsername());
        aer.setUpdatePersonDepartId(getDeptId());
        aer.setUpdateDate(new Date());
        int result=ier.updateByPrimaryKeySelective(aer);
        return toAjax(result);
    }
}
