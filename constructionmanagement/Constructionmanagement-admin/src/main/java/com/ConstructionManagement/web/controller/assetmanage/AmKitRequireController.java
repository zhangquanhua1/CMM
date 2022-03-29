package com.ConstructionManagement.web.controller.assetmanage;

import com.ConstructionManagement.common.annotation.Log;
import com.ConstructionManagement.common.core.controller.BaseController;
import com.ConstructionManagement.common.core.domain.AjaxResult;
import com.ConstructionManagement.common.core.domain.entity.SysUser;
import com.ConstructionManagement.common.core.page.TableDataInfo;
import com.ConstructionManagement.common.enums.BusinessType;
import com.ConstructionManagement.common.utils.poi.ExcelUtil;
import com.ConstructionManagement.system.domain.AmKitRequire;
import com.ConstructionManagement.system.service.IAmKitRequireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
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

    @PreAuthorize("@ss.hasPermi('asset:manage:kitrequire:isBuy')")
    @GetMapping("/listPass")
    public TableDataInfo listPass(AmKitRequire amKitRequire) {
        startPage();
        amKitRequire.setState(1);
        List<AmKitRequire> amKitRequires = iAmKitRequireService.selectBySelective(amKitRequire);
        return getDataTable(amKitRequires);
    }
    /**
     * 配件已采购
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:kitrequire:isBuy')")
    @Log(title = "零配件需求", businessType = BusinessType.UPDATE)
    @PostMapping("/{Ids}")
    public AjaxResult isBuy(@PathVariable Long[] Ids)
    {
        int result=iAmKitRequireService.isBuyIds(Ids);
        return toAjax(result);
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

    @Log(title = "配件需求", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('asset:manage:kitrequire:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, AmKitRequire wwh)
    {
        List<AmKitRequire> list = iAmKitRequireService.selectBySelective(wwh);
        ExcelUtil<AmKitRequire> util = new ExcelUtil<>(AmKitRequire.class);
        util.exportExcel(response, list, "配件需求表");
    }

    @Log(title = "配件采购", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('asset:manage:kitrequire:export')")
    @PostMapping("/exportBuy")
    public void exportBuy(HttpServletResponse response, AmKitRequire wwh)
    {
        wwh.setState(1);
        List<AmKitRequire> list = iAmKitRequireService.selectBySelective(wwh);
        ExcelUtil<AmKitRequire> util = new ExcelUtil<>(AmKitRequire.class);
        util.exportExcel(response, list, "配件采购表");
    }

    @Log(title = "配件需求", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('asset:manage:kitrequire:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<AmKitRequire> util = new ExcelUtil<AmKitRequire>(AmKitRequire.class);
        List<AmKitRequire> list = util.importExcel(file.getInputStream());
        String operName = getUsername();
        String message = iAmKitRequireService.importData(list, updateSupport, operName);
        return AjaxResult.success(message);
    }

    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<AmKitRequire> util = new ExcelUtil<AmKitRequire>(AmKitRequire.class);
        util.importTemplateExcel(response, "配件需求导入样例表");
    }

}
