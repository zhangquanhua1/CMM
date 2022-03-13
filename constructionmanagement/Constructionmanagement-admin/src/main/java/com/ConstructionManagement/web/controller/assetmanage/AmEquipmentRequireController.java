package com.ConstructionManagement.web.controller.assetmanage;

import com.ConstructionManagement.common.annotation.Log;
import com.ConstructionManagement.common.core.controller.BaseController;
import com.ConstructionManagement.common.core.domain.AjaxResult;
import com.ConstructionManagement.common.core.page.TableDataInfo;
import com.ConstructionManagement.common.core.redis.RedisCache;
import com.ConstructionManagement.common.enums.BusinessType;
import com.ConstructionManagement.system.domain.*;
import com.ConstructionManagement.system.service.IAmEquipmentRequireKitService;
import com.ConstructionManagement.system.service.IAmEquipmentRequirePartService;
import com.ConstructionManagement.system.service.IAmEquipmentRequireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/asset/equipmentrequire")
public class AmEquipmentRequireController extends BaseController {
    @Autowired
    IAmEquipmentRequireService ier;
    @Autowired
    IAmEquipmentRequireKitService ierk;
    @Autowired
    IAmEquipmentRequirePartService ierp;
    @Autowired
    private RedisCache redisCache;

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
     * 获取部件 配件
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:towermachine:list')")
    @GetMapping("/kitandpart/{pid}")
    public AjaxResult getlist(@PathVariable Long pid) {
        if (pid <= 0 || pid == null) return AjaxResult.error("不存在配件、部件");
        List<AmEquipmentRequireKit> kits = ierk.selectByEquipmentId(pid);
        List<AmEquipmentRequirePart> parts = ierp.selectByEquipmentId(pid);
        KitAndPart kp = new KitAndPart();
        kp.setAmEquipmentRequireKits(kits);
        kp.setAmEquipmentRequireParts(parts);
        return AjaxResult.success(kp);
    }

    /**
     * 删除
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:equipmentrequire:remove')")
    @Log(title = "设备需求", businessType = BusinessType.DELETE)
    @DeleteMapping("/{Ids}")
    public AjaxResult remove(@PathVariable Long[] Ids) {
        return toAjax(ier.deleteByIds(Ids));
    }

    /**
     * 新增
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:equipmentrequire:add')")
    @Log(title = "设备需求", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody AmEquipmentRequire aer) {
        if (aer == null)
            return AjaxResult.error("全空设备需求禁止插入");
        aer.setInsertPerson(getUsername());
        aer.setInsertPersonDepartId(getDeptId());
        aer.setInsertDate(new Date());
        int result = ier.insertSelective(aer);
        List<AmPartParamKit> kits = aer.getAmPartParamKits();
        if (kits != null && !kits.isEmpty() && kits.size() > 0) {
            long equipmentId = aer.getId();
            AmEquipmentRequireKit ek = new AmEquipmentRequireKit();
            ek.setEquimentId(equipmentId);
            for (AmPartParamKit k : kits) {
                AmKitParam amKitParam = k.getAmKitParam();
                ek.setKitCode(amKitParam.getKitCode());
                ek.setKitName(amKitParam.getKitName());
                ek.setKitModel(amKitParam.getKitModel());
                ek.setTechnicalParam(amKitParam.getTechnicalParam());
                ek.setApplicableKitType(amKitParam.getApplicableKitType());
                ek.setMeasurementUnit(amKitParam.getMeasurementUnit());
                ek.setKitCount(k.getKitCount());
                result *= ierk.insertSelective(ek);
            }
        }
        List<AmTowerMachineParamPart> parts = aer.getAmTowerMachineParamParts();
        if (parts != null && parts.size() > 0 && !parts.isEmpty()) {
            long equipmentId = aer.getId();
            AmEquipmentRequirePart ek = new AmEquipmentRequirePart();
            ek.setEquipmentId(equipmentId);
            for (AmTowerMachineParamPart k : parts) {
                AmPartParam amKitParam = k.getAmPartParam();
                ek.setPartName(amKitParam.getPartName());
                ek.setPartType(amKitParam.getPart_type());
                ek.setPartCode(amKitParam.getPartCode());
                ek.setPartModel(amKitParam.getPartModel());
                ek.setApplicableDeviceType(amKitParam.getApplicableDeviceType());
                ek.setMeasurementUnit(amKitParam.getMeasurementUnit());
                ek.setPartCount(k.getPartCount());
                result *= ierp.insertSelective(ek);
            }

        }
        return toAjax(result);
    }

    /**
     * 修改保存
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:equipmentrequire:edit') or @ss.hasPermi('asset:manage:equipmentrequire:audit')")
    @Log(title = "设备需求", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody AmEquipmentRequire aer) {
        aer.setUpdatePerson(getUsername());
        aer.setUpdatePersonDepartId(getDeptId());
        aer.setUpdateDate(new Date());
        int result = ier.updateByPrimaryKeySelective(aer);
        List<AmEquipmentRequireKit> kits = aer.getAmEquipmentRequireKits();
        List<AmEquipmentRequirePart> parts = aer.getAmEquipmentRequireParts();
        result *= ierk.deleteByEquipmentId(aer.getId());
        if (kits != null && kits.size() > 0 && !kits.isEmpty()) {
            for (AmEquipmentRequireKit kit : kits) {
                kit.setEquimentId(aer.getId());
                result *= ierk.insertSelective(kit);
            }
        }
        result *= ierp.deleteByEquipmentId(aer.getId());
        if (parts != null && parts.size() > 0 && !parts.isEmpty()) {
            for (AmEquipmentRequirePart part : parts) {
                part.setEquipmentId(aer.getId());
                result *= ierp.insertSelective(part);
            }
        }
        return toAjax(result);
    }
}
