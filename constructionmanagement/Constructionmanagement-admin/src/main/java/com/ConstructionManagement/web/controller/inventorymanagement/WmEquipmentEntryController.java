package com.ConstructionManagement.web.controller.inventorymanagement;

import com.ConstructionManagement.common.annotation.Log;
import com.ConstructionManagement.common.core.controller.BaseController;
import com.ConstructionManagement.common.core.domain.AjaxResult;
import com.ConstructionManagement.common.core.page.TableDataInfo;
import com.ConstructionManagement.common.core.redis.RedisCache;
import com.ConstructionManagement.common.enums.BusinessType;
import com.ConstructionManagement.system.domain.*;
import com.ConstructionManagement.system.service.*;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/inventory/equipmentEntry")
public class WmEquipmentEntryController extends BaseController {
    @Autowired
    private IWmEquipmentEntryService iw;
    @Autowired
    private RedisCache redisCache;
    public static final String wmEquipmentEntryKey = "WmEquipmentEntryKey";
    @Autowired
    private IAmTowerMachineParamPartService amTowerMachineParamPartService;
    @Autowired
    IAmPartParamService ipps;
    @Autowired
    private IAmPartParamKitService iamPartParamKitService;
    @Autowired
    IWmEquipmentEntryKitService ieeks;
    @Autowired
    IWmEquipmentEntryPartService ieeps;

    /**
     * 获取列表
     */
    @PreAuthorize("@ss.hasPermi('inventory:manage:equipmententry:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmEquipmentEntry wee, @Param("objectValueAllEmpty") boolean objectValueAllEmpty) {
        List<WmEquipmentEntry> wees;
        startPage();
        wees = redisCache.getCacheObject(wmEquipmentEntryKey);
        if (objectValueAllEmpty && (wees != null && wees.size() > 0) && !wees.isEmpty())
            return getDataTable(wees);
        else if (objectValueAllEmpty) {
            wees = iw.selectBySelective(null);
            redisCache.setCacheObject(wmEquipmentEntryKey, wees);
            return getDataTable(wees);
        }
        wees = iw.selectBySelective(wee);
        return getDataTable(wees);
    }
    /**
     * 设备录入的部件和配件详情
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:towermachine:list')")
    @GetMapping("/kitandpart2/{pid}")
    public AjaxResult getlistKitsAndParts(@PathVariable Long pid) {
        if (pid <= 0 || pid == null) return AjaxResult.error("不存在配件、部件");
        List<WmEquipmentEntryKit> kits = ieeks.selectByEquipmentId(pid);
        List<WmEquipmentEntryPart> parts = ieeps.selectByEquipmentId(pid);
        KitAndPart kp = new KitAndPart();
        kp.setWmEquipmentEntryKits(kits);
        kp.setWmEquipmentEntryParts(parts);
        return AjaxResult.success(kp);
    }

    /**
     * 获取所选设备类型及型号的部件、配件
     */
    @PreAuthorize("@ss.hasPermi('inventory:manage:equipmententry:add') or @ss.hasPermi('inventory:manage:equipmententry:edit') ")
    @GetMapping("/kitandpart/{pid}")
    public AjaxResult getlist(@PathVariable Long pid) {
        if (pid <= 0 || pid == null) return AjaxResult.error("不存在配件、部件");
        List<AmTowerMachineParamPart> listParts = amTowerMachineParamPartService.selectByPid(pid);
        //设备配件
        List<WmEquipmentEntryKit> aerKits = new ArrayList<>();
        //设备部件
        List<WmEquipmentEntryPart> aerParts = new ArrayList<>();
         long indexPart=0;
         long indexKit=0;
        //遍历部件获取部件的配件
        for (AmTowerMachineParamPart part : listParts) {
            WmEquipmentEntryPart rp = new WmEquipmentEntryPart();
            AmPartParam Param = part.getAmPartParam();
            rp.setId(indexPart++);
            rp.setEquipmentId(pid);
            rp.setPartName(Param.getPartName());
            rp.setPartType(Param.getPart_type());
            rp.setPartCode(Param.getPartCode());
            rp.setPartModel(Param.getPartModel());
            rp.setApplicableDeviceType(Param.getApplicableDeviceType());
            rp.setMeasurementUnit(Param.getMeasurementUnit());
            rp.setPartCount(part.getPartCount());
            aerParts.add(rp);
            List<AmPartParamKit> Kits = iamPartParamKitService.selectByPid(part.getPart_id());
            for (AmPartParamKit appk : Kits) {
                WmEquipmentEntryKit ek = new WmEquipmentEntryKit();
                AmKitParam amKitParam = appk.getAmKitParam();
                ek.setId(indexKit++);
                ek.setEquimentId(pid);
                ek.setKitCode(amKitParam.getKitCode());
                ek.setKitName(amKitParam.getKitName());
                ek.setKitModel(amKitParam.getKitModel());
                ek.setTechnicalParam(amKitParam.getTechnicalParam());
                ek.setApplicableKitType(amKitParam.getApplicableKitType());
                ek.setMeasurementUnit(amKitParam.getMeasurementUnit());
                ek.setKitCount(appk.getKitCount() * part.getPartCount());
                aerKits.add(ek);
            }
        }
        KitAndPart kp = new KitAndPart();
        kp.setWmEquipmentEntryKits(aerKits);
        kp.setWmEquipmentEntryParts(aerParts);
        return AjaxResult.success(kp);
    }

    /**
     * 删除
     */
    @PreAuthorize("@ss.hasPermi('inventory:manage:equipmententry:remove')")
    @Log(title = "设备录入", businessType = BusinessType.DELETE)
    @DeleteMapping("/{Ids}")
    public AjaxResult remove(@PathVariable Long[] Ids) {
        redisCache.deleteObject(wmEquipmentEntryKey);
        return toAjax(iw.deleteByIds(Ids));
    }

    /**
     * 新增
     */
    @PreAuthorize("@ss.hasPermi('inventory:manage:equipmententry:add')")
    @Log(title = "设备录入", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody WmEquipmentEntry wee) {
        if (wee == null)
            return AjaxResult.error("全空设备录入禁止插入");
        wee.setInsertPerson(getUsername());
        wee.setInsertPersonDepartId(getDeptId());
        wee.setInsertDate(new Date());
        redisCache.deleteObject(wmEquipmentEntryKey);
        int result = iw.insertSelective(wee);

        result *= insertKitAndPart(wee);

        return toAjax(result);
    }

    //设备录入的部件和配件插入
    public int insertKitAndPart(WmEquipmentEntry wee) {
        int result = 1;
        long equipmentId = wee.getId();
        List<WmEquipmentEntryKit> kits = wee.getWmEquipmentEntryKits();
        List<WmEquipmentEntryPart> parts = wee.getWmEquipmentEntryParts();
        if (kits != null && !kits.isEmpty() && kits.size() > 0) {
            for (WmEquipmentEntryKit kit : kits) {
                kit.setEquimentId(equipmentId);
                result *= ieeks.insertSelective(kit);
            }
        }
        if (parts != null && parts.size() > 0 && !parts.isEmpty()) {
            for (WmEquipmentEntryPart part : parts) {
                part.setEquipmentId(equipmentId);
                result *= ieeps.insertSelective(part);
            }
        }
        return result;
    }

    /**
     * 修改保存
     */
    @PreAuthorize("@ss.hasPermi('inventory:manage:equipmententry:edit') ")
    @Log(title = "设备录入", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody WmEquipmentEntry wee) {
        redisCache.deleteObject(wmEquipmentEntryKey);
        wee.setUpdatePerson(getUsername());
        wee.setUpdatePersonDepartId(getDeptId());
        wee.setUpdateDate(new Date());
        int result = iw.updateByPrimaryKeySelective(wee);
        long equipmentId = wee.getId();
        result *= ieeks.deleteByEquipmentId(equipmentId);
        result *= ieeps.deleteByEquipmentId(equipmentId);
        result *= insertKitAndPart(wee);
        return toAjax(result);
    }

    /**
     * 设备信息确认
     */
    @PreAuthorize("@ss.hasPermi('inventory:manage:equipmententry:confirm')")
    @Log(title = "设备信息确认", businessType = BusinessType.UPDATE)
    @PutMapping("/confirm")
    public AjaxResult confirm(@RequestBody Long[] ids) {

        if (ids == null || ids.length == 0) return AjaxResult.error("确认失败");
        redisCache.deleteObject(wmEquipmentEntryKey);
        int result = iw.confirmByIds(ids);
        return toAjax(result);
    }

    /**
     * 设备信息反确认
     */
    @PreAuthorize("@ss.hasPermi('inventory:manage:equipmententry:confirm')")
    @Log(title = "设备信息反确认", businessType = BusinessType.UPDATE)
    @PutMapping("/AntiConfirm")
    public AjaxResult AntiConfirm(@RequestBody Long[] ids) {
        if (ids == null || ids.length == 0) return AjaxResult.error("反确认失败");
        redisCache.deleteObject(wmEquipmentEntryKey);
        int result = iw.AntiConfirmByIds(ids);
        return toAjax(result);
    }
}
