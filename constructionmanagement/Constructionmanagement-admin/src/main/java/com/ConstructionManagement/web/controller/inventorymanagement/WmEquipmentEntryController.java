package com.ConstructionManagement.web.controller.inventorymanagement;

import com.ConstructionManagement.common.annotation.Log;
import com.ConstructionManagement.common.core.controller.BaseController;
import com.ConstructionManagement.common.core.domain.AjaxResult;
import com.ConstructionManagement.common.core.page.TableDataInfo;
import com.ConstructionManagement.common.core.redis.RedisCache;
import com.ConstructionManagement.common.enums.BusinessType;
import com.ConstructionManagement.system.domain.WmEquipmentEntry;
import com.ConstructionManagement.system.service.IWmEquipmentEntryService;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/inventory/equipmentEntry")
public class WmEquipmentEntryController extends BaseController {
    @Autowired
    private IWmEquipmentEntryService iw;
    @Autowired
    private RedisCache redisCache;
    public static  final String wmEquipmentEntryKey="WmEquipmentEntryKey";
    /**
     * 获取列表
     */
    @PreAuthorize("@ss.hasPermi('inventory:manage:equipmententry:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmEquipmentEntry wee,@Param("objectValueAllEmpty") boolean objectValueAllEmpty ) {
       // System.out.println(objectValueAllEmpty);
        List<WmEquipmentEntry> wees;
        startPage();
        wees=redisCache.getCacheObject(wmEquipmentEntryKey);
        if(objectValueAllEmpty&&(wees!=null&&wees.size()>0)&&!wees.isEmpty())
            return getDataTable(wees);
        else if(objectValueAllEmpty){
            wees = iw.selectBySelective(null);
            redisCache.setCacheObject(wmEquipmentEntryKey,wees);
            return getDataTable(wees);
        }
        wees = iw.selectBySelective(wee);
        return getDataTable(wees);
    }

    /**
     * 删除
     */
    @PreAuthorize("@ss.hasPermi('inventory:manage:equipmententry:remove')")
    @Log(title = "设备录入", businessType = BusinessType.DELETE)
    @DeleteMapping("/{Ids}")
    public AjaxResult remove(@PathVariable Long[] Ids)
    {
        redisCache.deleteObject(wmEquipmentEntryKey);
        return toAjax(iw.deleteByIds(Ids));
    }

    /**
     * 新增
     */
    @PreAuthorize("@ss.hasPermi('inventory:manage:equipmententry:add')")
    @Log(title = "设备录入", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody WmEquipmentEntry wee)
    {
        if(wee==null)
            return AjaxResult.error("全空设备录入禁止插入");
        wee.setInsertPerson(getUsername());
        wee.setInsertPersonDepartId(getDeptId());
        wee.setInsertDate(new Date());
        redisCache.deleteObject(wmEquipmentEntryKey);
        int result=iw.insertSelective(wee);
        return toAjax(result);
    }
    /**
     * 修改保存
     */
    @PreAuthorize("@ss.hasPermi('inventory:manage:equipmententry:edit') " )
    @Log(title = "设备录入", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody WmEquipmentEntry wee)
    {
        redisCache.deleteObject(wmEquipmentEntryKey);
        wee.setUpdatePerson(getUsername());
        wee.setUpdatePersonDepartId(getDeptId());
        wee.setUpdateDate(new Date());
        int result=iw.updateByPrimaryKeySelective(wee);
        return toAjax(result);
    }

    /**
     * 设备信息确认
     */
    @PreAuthorize("@ss.hasPermi('inventory:manage:equipmententry:confirm')" )
    @Log(title = "设备信息确认", businessType = BusinessType.UPDATE)
    @PutMapping("/confirm")
    public AjaxResult confirm(@RequestBody Long[] ids)
    {
        System.out.println("ids"+ids);
        if(ids==null||ids.length==0) return AjaxResult.error("确认失败");
        redisCache.deleteObject(wmEquipmentEntryKey);
        int result=iw.confirmByIds(ids);
        return toAjax(result);
    }

    /**
     * 设备信息反确认
     */
    @PreAuthorize("@ss.hasPermi('inventory:manage:equipmententry:confirm')" )
    @Log(title = "设备信息反确认", businessType = BusinessType.UPDATE)
    @PutMapping("/AntiConfirm")
    public AjaxResult AntiConfirm(@RequestBody Long[] ids)
    {
        if(ids==null||ids.length==0) return AjaxResult.error("反确认失败");
        redisCache.deleteObject(wmEquipmentEntryKey);
        int result=iw.AntiConfirmByIds(ids);
        return toAjax(result);
    }
}
