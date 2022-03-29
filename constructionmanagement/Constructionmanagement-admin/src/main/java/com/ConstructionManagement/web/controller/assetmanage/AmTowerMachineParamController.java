package com.ConstructionManagement.web.controller.assetmanage;

import com.ConstructionManagement.common.annotation.Log;
import com.ConstructionManagement.common.core.controller.BaseController;
import com.ConstructionManagement.common.core.domain.AjaxResult;
import com.ConstructionManagement.common.core.page.TableDataInfo;
import com.ConstructionManagement.common.enums.BusinessType;
import com.ConstructionManagement.system.domain.*;
import com.ConstructionManagement.system.mapper.AmTowerMachineParamMapper;
import com.ConstructionManagement.system.service.*;
import com.ConstructionManagement.web.controller.ExportUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/asset/manage")
public class AmTowerMachineParamController extends BaseController {
    @Autowired
    private IAmTowerMachineParamService amTowerMachineParamService;
    @Autowired
    private IAmTowerMachineParamPartService amTowerMachineParamPartService;
    @Autowired
    IAmPartParamService ipps;
    @Autowired
    private IAmPartParamKitService iamPartParamKitService;

    /**
     * 获取列表
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:towermachine:list')")
    @GetMapping("/towermachine/list")
    public TableDataInfo list(AmTowerMachineParam amTowerMachineParam) {
        startPage();
        List<AmTowerMachineParam> amTowerMachineParams = amTowerMachineParamService.selectBySelective(amTowerMachineParam);
        return getDataTable(amTowerMachineParams);
    }

    //根据设备型号和设备类别 获取设备参数
    @PreAuthorize("@ss.hasPermi('asset:manage:towermachine:list')")
    @GetMapping("/equipmentParam")
    public AjaxResult listByParam(@RequestParam("towerMachineModel") String towerMachineModel, @RequestParam("towerMachineType") String towerMachineType) {
        AmTowerMachineParam amTowerMachineParams = amTowerMachineParamService.selectByParam(towerMachineModel, towerMachineType);
        return AjaxResult.success(amTowerMachineParams);
    }

    /**
     * 获取部件 配件
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:towermachine:list')")
    @GetMapping("/kitandpart/{pid}")
    public AjaxResult getlist(@PathVariable Long pid) {
        if (pid <= 0 || pid == null) return AjaxResult.error("该设备不存在配件、部件");
        List<AmTowerMachineParamPart> listParts = amTowerMachineParamPartService.selectByPid(pid);
        List<AmPartParamKit> listKits = new ArrayList<AmPartParamKit>();
        for (AmTowerMachineParamPart part : listParts) {
            List<AmPartParamKit> Kits = iamPartParamKitService.selectByPid(part.getPart_id());
            for (AmPartParamKit appk : Kits) {
                appk.setKitCount(appk.getKitCount() * part.getPartCount());
                listKits.add(appk);
            }
        }
        KitAndPart kp = new KitAndPart();
        kp.setAmPartParamKits(listKits);
        kp.setAmTowerMachineParamParts(listParts);
        return AjaxResult.success(kp);
    }

    /**
     * 获取可用的部件
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:towermachine:list')")
    @GetMapping("/getUsePart")
    public AjaxResult getUsePart(AmTowerMachineParam amTowerMachineParam) {
        AmPartParam ap = new AmPartParam();
        ap.setApplicableDeviceType(amTowerMachineParam.getTowerMachineType());
        List<AmPartParam> listParts = ipps.selectBySelective(ap);
        return AjaxResult.success(listParts);
    }

    /**
     * 删除
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:towermachine:remove')")
    @Log(title = "设备参数", businessType = BusinessType.DELETE)
    @DeleteMapping("/{towermachineIds}")
    public AjaxResult remove(@PathVariable Long[] towermachineIds) {
        return toAjax(amTowerMachineParamService.deleteByIds(towermachineIds));
    }

    /**
     * 新增
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:towermachine:add')")
    @Log(title = "设备参数", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody AmTowerMachineParam amTowerMachineParam) {
        if (amTowerMachineParam == null) return AjaxResult.error("全空设备参数禁止插入");
        Long pid;
        amTowerMachineParam.setInsertPerson(getUsername());
        amTowerMachineParam.setInsertPersonDepartId(getDeptId());
        amTowerMachineParam.setInsertDate(new Date());
        int result = amTowerMachineParamService.insertSelective(amTowerMachineParam);
        if (result < 0)
            return AjaxResult.error("添加失败");
        pid = amTowerMachineParam.getId();
        List<AmTowerMachineParamPart> parts = amTowerMachineParam.getAmTowerMachineParamParts();
        if (parts != null && parts.size() > 0 && !parts.isEmpty()) {
            for (AmTowerMachineParamPart part : parts) {
                part.setPid(pid);
                result *= amTowerMachineParamPartService.insertSelective(part);
            }
        }
        return toAjax(result);
    }

    /**
     * 修改保存
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:towermachine:edit')")
    @Log(title = "设备参数", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody AmTowerMachineParam amTowerMachineParam) {
        Long pid = amTowerMachineParam.getId();
        List<AmTowerMachineParamPart> parts = amTowerMachineParam.getAmTowerMachineParamParts();
        amTowerMachineParam.setUpdatePerson(getUsername());
        amTowerMachineParam.setUpdatePersonDepartId(getDeptId());
        amTowerMachineParam.setUpdateDate(new Date());
        int result = amTowerMachineParamService.updateByPrimaryKeySelective(amTowerMachineParam);
        result *= amTowerMachineParamPartService.deleteByPid(pid);
        for (AmTowerMachineParamPart part : parts) {
            part.setPid(pid);
            result *= amTowerMachineParamPartService.insertSelective(part);
        }
        return toAjax(result);
    }


    @Log(title = "设备参数导出", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('asset:manage:protector:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, AmTowerMachineParam wwh) {
        List<AmTowerMachineParam> list = amTowerMachineParamService.selectBySelective(wwh);
        if (list != null && list.size() > 0 && !list.isEmpty()) {
            for(AmTowerMachineParam atmp:list){
                List<AmTowerMachineParamPart> parts=amTowerMachineParamPartService.selectByPid(atmp.getId());
                List<AmTowerMachinePartExport> partsExport=new ArrayList<AmTowerMachinePartExport>();
                //把根据设备id选出的部件构造成要导出的AmTowerMachinePartExport
                if(parts!=null&&parts.size() > 0 && !parts.isEmpty()) {
                    for(AmTowerMachineParamPart part:parts){
                        AmTowerMachinePartExport tmp=new AmTowerMachinePartExport();
                        tmp.setPartName(part.getAmPartParam().getPartName());
                        tmp.setApplicableDeviceType(part.getAmPartParam().getApplicableDeviceType());
                        tmp.setPartCode(part.getAmPartParam().getPartCode());
                        tmp.setPartModel(part.getAmPartParam().getPartModel());
                        tmp.setPart_type(part.getAmPartParam().getPart_type());
                        tmp.setMeasurementUnit(part.getAmPartParam().getMeasurementUnit());
                        tmp.setPartCount(part.getPartCount());

                        List<AmPartParamKit> kits=iamPartParamKitService.selectByPid(part.getAmPartParam().getId());
                        List<AmTowerMachineKitExport> kitsExport=new ArrayList<AmTowerMachineKitExport>();
                        //根据部件id查出该部件绑定的配件，把配件构造成要导出的AmTowerMachineKitExport
                        if(kits!=null&&kits.size() > 0 && !kits.isEmpty()){
                            for(AmPartParamKit kit:kits) {
                                AmTowerMachineKitExport tmp2 = new AmTowerMachineKitExport();
                                tmp2.setKitName(kit.getAmKitParam().getKitName());
                                tmp2.setKitCode(kit.getAmKitParam().getKitCode());
                                tmp2.setKitModel(kit.getAmKitParam().getKitModel());
                                tmp2.setMeasurementUnit(kit.getAmKitParam().getMeasurementUnit());
                                tmp2.setApplicableKitType(kit.getAmKitParam().getApplicableKitType());
                                tmp2.setTechnicalParam(kit.getAmKitParam().getTechnicalParam());
                                tmp2.setKitCount(kit.getKitCount());
                                kitsExport.add(tmp2);
                            }

                        }
                        tmp.setKits(kitsExport);
                        partsExport.add(tmp);
                    }
                }
                atmp.setPartsExport(partsExport);
            }

        }
        new ExportUtil().outPut(response, "设备参数表", list, AmTowerMachineParam.class);
    }
}
