package com.ConstructionManagement.web.controller.inventorymanagement;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.ConstructionManagement.common.annotation.Log;
import com.ConstructionManagement.common.core.controller.BaseController;
import com.ConstructionManagement.common.core.domain.AjaxResult;
import com.ConstructionManagement.common.core.page.TableDataInfo;
import com.ConstructionManagement.common.enums.BusinessType;
import com.ConstructionManagement.common.utils.Threads;
import com.ConstructionManagement.common.utils.poi.ExcelUtil;
import com.ConstructionManagement.system.domain.Check;
import com.ConstructionManagement.system.domain.Inventory;
import com.ConstructionManagement.system.domain.InventoryListAndId;
import com.ConstructionManagement.system.service.ICheckService;
import com.ConstructionManagement.system.service.IInventoryService;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 盘点Controller
 *
 * @author ruoyi
 * @date 2022-05-22
 */
@RestController
@RequestMapping("/system/check")
public class CheckController extends BaseController
{
    private static final Logger logger = LoggerFactory.getLogger(CheckController.class);
    @Autowired
    private ICheckService checkService;
    @Autowired
    IInventoryService iis;

    /**
     * 查询盘点列表
     */
    @PreAuthorize("@ss.hasPermi('system:check:list')")
    @GetMapping("/list")
    public TableDataInfo list(Check check)
    {
        startPage();
        List<Check> list = checkService.selectCheckList(check);
        return getDataTable(list);
    }
    @PreAuthorize("@ss.hasPermi('system:check:list')")
    @GetMapping("/list/{id}")
    public TableDataInfo listById(@PathVariable Long id)
    {
        //startPage();
        logger.info("id /////// "+id );
        List<Inventory> list = checkService.selectInventoryByCheckId(id);
        return getDataTable(list);
    }

    /**
     * 导出盘点列表
     */
    @PreAuthorize("@ss.hasPermi('system:check:export')")
    @Log(title = "盘点", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Check check)
    {
        List<Check> list = checkService.selectCheckList(check);
        ExcelUtil<Check> util = new ExcelUtil<Check>(Check.class);
        util.exportExcel(response, list, "盘点数据");
    }

    /**
     * 获取盘点详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:check:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(checkService.selectCheckById(id));
    }

    /**
     * 新增盘点
     */
    @PreAuthorize("@ss.hasPermi('system:check:add')")
    @Log(title = "盘点", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Check check)
    {
        return toAjax(checkService.insertCheck(check));
    }

    /**
     * 修改盘点
     */
    @PreAuthorize("@ss.hasPermi('system:check:edit')")
    @Log(title = "盘点", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Check check)
    {
        logger.info("Check "+check.toString());
        return toAjax(checkService.updateCheck(check));
    }

    /**
     * 删除盘点
     */
    @PreAuthorize("@ss.hasPermi('system:check:remove')")
    @Log(title = "盘点", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(checkService.deleteCheckByIds(ids));
    }

    /**
     * 确认盘点
     */
    @PreAuthorize("@ss.hasPermi('system:check:edit')")
    @Log(title = "盘点", businessType = BusinessType.UPDATE)
    @PutMapping("/submitCheck")
    public AjaxResult edit2(@RequestBody InventoryListAndId lists)
    {
        int res =1;
        List<Inventory> list = lists.getList();
        Long id=lists.getId();
        Check check=new Check();
        check.setId(id);
        check.setStatus(1);
        if(list!=null&&list.size() > 0 && !list.isEmpty()){
            for(Inventory inv : list){
                res*=iis.updateByPrimaryKeySelective(inv);
            }
        }
        res*=checkService.updateCheck(check);
        return toAjax(res);
    }

}
