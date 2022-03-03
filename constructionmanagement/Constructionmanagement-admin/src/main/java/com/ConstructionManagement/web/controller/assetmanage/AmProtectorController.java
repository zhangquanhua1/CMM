package com.ConstructionManagement.web.controller.assetmanage;

import com.ConstructionManagement.common.annotation.Log;
import com.ConstructionManagement.common.core.controller.BaseController;
import com.ConstructionManagement.common.core.domain.AjaxResult;
import com.ConstructionManagement.common.core.page.TableDataInfo;
import com.ConstructionManagement.common.enums.BusinessType;
import com.ConstructionManagement.system.domain.AmKitParam;
import com.ConstructionManagement.system.domain.AmProtector;
import com.ConstructionManagement.system.service.IAmKitParamService;
import com.ConstructionManagement.system.service.IAmProtectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/asset/protector")
public class AmProtectorController extends BaseController {
    @Autowired
    IAmProtectorService iAmPartParamService;
    /**
     * 获取列表
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:protector:list')")
    @GetMapping("/list")
    public TableDataInfo list(AmProtector amProtector) {
        startPage();
        List<AmProtector> amProtectors = iAmPartParamService.selectBySelective(amProtector);
        return getDataTable(amProtectors);
    }

    @PreAuthorize("@ss.hasPermi('asset:manage:protector:list')")
    @GetMapping("/listall")
    public AjaxResult listAll() {
        List<AmProtector> amProtectors = iAmPartParamService.selectBySelective(null);
        return AjaxResult.success(amProtectors);
    }

    /**
     * 删除
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:protector:remove')")
    @Log(title = "防坠器参数", businessType = BusinessType.DELETE)
    @DeleteMapping("/{Ids}")
    public AjaxResult remove(@PathVariable Long[] Ids)
    {
        return toAjax(iAmPartParamService.deleteByIds(Ids));
    }

    /**
     * 新增
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:protector:add')")
    @Log(title = "防坠器参数", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody AmProtector amProtector)
    {
        int result=iAmPartParamService.insertSelective(amProtector);
        return toAjax(result);
    }

    /**
     * 修改保存
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:protector:edit')")
    @Log(title = "防坠器参数", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody AmProtector amProtector)
    {
        int result=iAmPartParamService.updateByPrimaryKeySelective(amProtector);
        return toAjax(result);
    }
}
