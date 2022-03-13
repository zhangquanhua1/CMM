package com.ConstructionManagement.web.controller.inventorymanagement;

import com.ConstructionManagement.common.annotation.Log;
import com.ConstructionManagement.common.core.controller.BaseController;
import com.ConstructionManagement.common.core.domain.AjaxResult;
import com.ConstructionManagement.common.core.page.TableDataInfo;
import com.ConstructionManagement.common.core.redis.RedisCache;
import com.ConstructionManagement.common.enums.BusinessType;
import com.ConstructionManagement.system.domain.WmKitEntry;
import com.ConstructionManagement.system.service.IWmKitEntryService;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/inventory/kitEntry")
public class WmKitEntryController extends BaseController {
    @Autowired
    IWmKitEntryService ke;
    @Autowired
    private RedisCache redisCache;
    public static  final String wmKitEntryKey="WmKitEntryKey";
    /**
     * 获取列表
     */
    @PreAuthorize("@ss.hasPermi('inventory:manage:kitentry:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmKitEntry wee, @Param("objectValueAllEmpty") boolean objectValueAllEmpty ) {
        List<WmKitEntry> wees;
        startPage();
        wees=redisCache.getCacheObject(wmKitEntryKey);
        if(objectValueAllEmpty&&(wees!=null&&wees.size()>0)&&!wees.isEmpty())
            return getDataTable(wees);
        else if(objectValueAllEmpty){
            wees = ke.selectBySelective(null);
            redisCache.setCacheObject(wmKitEntryKey,wees);
            return getDataTable(wees);
        }
        wees = ke.selectBySelective(wee);
        return getDataTable(wees);
    }

    /**
     * 删除
     */
    @PreAuthorize("@ss.hasPermi('inventory:manage:kitentry:remove')")
    @Log(title = "零配件录入", businessType = BusinessType.DELETE)
    @DeleteMapping("/{Ids}")
    public AjaxResult remove(@PathVariable Long[] Ids)
    {
        redisCache.deleteObject(wmKitEntryKey);
        return toAjax(ke.deleteByIds(Ids));
    }

    /**
     * 新增
     */
    @PreAuthorize("@ss.hasPermi('inventory:manage:kitentry:add')")
    @Log(title = "零配件录入", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody WmKitEntry wee)
    {
        if(wee==null)
            return AjaxResult.error("全空零配件录入禁止插入");
        wee.setInsertPerson(getUsername());
        wee.setInsertPersonDepartId(getDeptId());
        wee.setInsertDate(new Date());
        redisCache.deleteObject(wmKitEntryKey);
        int result=ke.insertSelective(wee);
        return toAjax(result);
    }
    /**
     * 修改保存
     */
    @PreAuthorize("@ss.hasPermi('inventory:manage:kitentry:edit')" )
    @Log(title = "零配件录入", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody WmKitEntry wee)
    {
        redisCache.deleteObject(wmKitEntryKey);
        wee.setUpdatePerson(getUsername());
        wee.setUpdatePersonDepartId(getDeptId());
        wee.setUpdateDate(new Date());
        int result=ke.updateByPrimaryKeySelective(wee);
        return toAjax(result);
    }

    /**
     * 零配件信息确认
     */
    @PreAuthorize("@ss.hasPermi('inventory:manage:kitentry:confirm')" )
    @Log(title = "零配件信息确认", businessType = BusinessType.UPDATE)
    @PutMapping("/confirm")
    public AjaxResult confirm(@RequestBody Long[] ids)
    {

        if(ids==null||ids.length==0) return AjaxResult.error("确认失败");
        redisCache.deleteObject(wmKitEntryKey);
        int result=ke.confirmByIds(ids);
        return toAjax(result);
    }

    /**
     * 零配件信息反确认
     */
    @PreAuthorize("@ss.hasPermi('inventory:manage:kitentry:confirm')" )
    @Log(title = "零配件信息反确认", businessType = BusinessType.UPDATE)
    @PutMapping("/AntiConfirm")
    public AjaxResult AntiConfirm(@RequestBody Long[] ids)
    {
        if(ids==null||ids.length==0) return AjaxResult.error("反确认失败");
        redisCache.deleteObject(wmKitEntryKey);
        int result=ke.AntiConfirmByIds(ids);
        return toAjax(result);
    }
}
