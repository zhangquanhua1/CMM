package com.ConstructionManagement.web.controller.inventorymanagement;

import com.ConstructionManagement.common.annotation.Log;
import com.ConstructionManagement.common.core.controller.BaseController;
import com.ConstructionManagement.common.core.domain.AjaxResult;
import com.ConstructionManagement.common.core.page.TableDataInfo;
import com.ConstructionManagement.common.core.redis.RedisCache;
import com.ConstructionManagement.common.enums.BusinessType;
import com.ConstructionManagement.common.utils.poi.ExcelUtil;
import com.ConstructionManagement.system.domain.StockOutRecord;
import com.ConstructionManagement.system.domain.WmWarehouse;
import com.ConstructionManagement.system.service.IWmWarehouseService;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * 仓库管理
 */
@RestController
@RequestMapping("/asset/warehouse")
public class WmWarehouseController extends BaseController {
    @Autowired
    IWmWarehouseService iwwh;
    @Autowired
    private RedisCache redisCache;
    public static  final String wmWarehouseKey="WmWarehouseKey";
    /**
     * 获取列表
     */
    @PreAuthorize("@ss.hasPermi('inventory:manage:warehouse:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmWarehouse wwh, @Param("objectValueAllEmpty") boolean objectValueAllEmpty ) {
        List<WmWarehouse> wwhs;
        startPage();
        wwhs=redisCache.getCacheObject(wmWarehouseKey);
        if(objectValueAllEmpty&&(wwhs!=null&&wwhs.size()>0)&&!wwhs.isEmpty())
            return getDataTable(wwhs);
        else if(objectValueAllEmpty){
            wwhs = iwwh.selectBySelective(null);
            redisCache.setCacheObject(wmWarehouseKey,wwhs);
            return getDataTable(wwhs);
        }
        wwhs = iwwh.selectBySelective(wwh);
        return getDataTable(wwhs);
    }

    /**
     * 获取所有可用仓库
     */
    @PreAuthorize("@ss.hasPermi('inventory:manage:warehouse:list')")
    @GetMapping("/listAll")
    public AjaxResult listAll() {
           List<WmWarehouse> wwhs;
           //选出所有可用的仓库
           wwhs = iwwh.selectByAllUse();
        return AjaxResult.success(wwhs);
    }

    /**
     * 删除
     */
    @PreAuthorize("@ss.hasPermi('inventory:manage:warehouse:remove')")
    @Log(title = "仓库管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{Ids}")
    public AjaxResult remove(@PathVariable Long[] Ids)
    {
        redisCache.deleteObject(wmWarehouseKey);
        return toAjax(iwwh.deleteByIds(Ids));
    }

    /**
     * 新增
     */
    @PreAuthorize("@ss.hasPermi('inventory:manage:warehouse:add')")
    @Log(title = "仓库管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody WmWarehouse wwh)
    {
        if(wwh==null)
            return AjaxResult.error("全空仓库管理禁止插入");
        wwh.setInsertPerson(getUsername());
        wwh.setInsertPersonDepartId(getDeptId());
        wwh.setInsertDate(new Date());
        redisCache.deleteObject(wmWarehouseKey);
        int result=iwwh.insertSelective(wwh);
        return toAjax(result);
    }
    /**
     * 修改保存
     */
    @PreAuthorize("@ss.hasPermi('inventory:manage:warehouse:edit')" )
    @Log(title = "仓库管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody WmWarehouse wwh)
    {
        redisCache.deleteObject(wmWarehouseKey);
        wwh.setUpdatePerson(getUsername());
        wwh.setUpdatePersonDepartId(getDeptId());
        wwh.setUpdateDate(new Date());
        int result=iwwh.updateByPrimaryKeySelective(wwh);
        return toAjax(result);
    }
    @Log(title = "仓库管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('inventory:manage:warehouse:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmWarehouse wwh)
    {
        List<WmWarehouse> list = iwwh.selectBySelective(wwh);
        ExcelUtil<WmWarehouse> util = new ExcelUtil<WmWarehouse>(WmWarehouse.class);
        util.exportExcel(response, list, "仓库维护表");
    }
}
