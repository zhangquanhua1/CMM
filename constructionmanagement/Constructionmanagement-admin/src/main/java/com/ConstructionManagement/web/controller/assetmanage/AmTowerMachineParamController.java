package com.ConstructionManagement.web.controller.assetmanage;

import com.ConstructionManagement.common.annotation.Log;
import com.ConstructionManagement.common.core.controller.BaseController;
import com.ConstructionManagement.common.core.domain.AjaxResult;
import com.ConstructionManagement.common.core.page.TableDataInfo;
import com.ConstructionManagement.common.enums.BusinessType;
import com.ConstructionManagement.system.domain.*;
import com.ConstructionManagement.system.mapper.AmTowerMachineParamMapper;
import com.ConstructionManagement.system.service.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
        System.out.println("amTowerMachineParam.getTowerMachineType()"+amTowerMachineParam.getTowerMachineType());
        startPage();
        List<AmTowerMachineParam> amTowerMachineParams = amTowerMachineParamService.selectBySelective(amTowerMachineParam);
        return getDataTable(amTowerMachineParams);
    }

    //根据设备型号和设备类别 获取设备参数
    @PreAuthorize("@ss.hasPermi('asset:manage:towermachine:list')")
    @GetMapping("/equipmentParam")
    public AjaxResult listByParam(@RequestParam("model") String towerMachineModel,@RequestParam("type") String towerMachineType) {
        //System.out.println("amTowerMachineParam.getTowerMachineType()"+towerMachineType);
        AmTowerMachineParam amTowerMachineParams = amTowerMachineParamService.selectByParam(towerMachineModel,towerMachineType);
        return AjaxResult.success(amTowerMachineParams);
    }

    /**
     * 获取部件 配件
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:towermachine:list')")
    @GetMapping("/kitandpart/{pid}")
    public AjaxResult getlist(@PathVariable Long pid) {
        if(pid<=0||pid==null) return AjaxResult.error("该塔机不存在配件、部件");
        List<AmTowerMachineParamPart> listParts=amTowerMachineParamPartService.selectByPid(pid);
        List<AmPartParamKit> listKits=new ArrayList<AmPartParamKit>();
        for (AmTowerMachineParamPart part:listParts) {
            //System.out.println("AmTowerMachineParamPart"+part.getPart_id());
            List<AmPartParamKit> Kits=iamPartParamKitService.selectByPid(part.getPart_id());
            for (AmPartParamKit appk:Kits ) {
                appk.setKitCount(appk.getKitCount()*part.getPartCount());
                listKits.add(appk);
            }
        }
        KitAndPart kp=new KitAndPart();
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
       // System.out.println("ap"+amTowerMachineParam.getTowerMachineName());
        AmPartParam ap=new AmPartParam();
        ap.setApplicableDeviceType(amTowerMachineParam.getTowerMachineType());
        List<AmPartParam> listParts=ipps.selectBySelective(ap);
        return AjaxResult.success(listParts);
    }
    /**
     * 删除
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:towermachine:remove')")
    @Log(title = "塔机参数", businessType = BusinessType.DELETE)
    @DeleteMapping("/{towermachineIds}")
    public AjaxResult remove(@PathVariable Long[] towermachineIds)
    {
        return toAjax(amTowerMachineParamService.deleteByIds(towermachineIds));
    }

    /**
     * 新增
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:towermachine:add')")
    @Log(title = "塔机参数", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody  AmTowerMachineParam amTowerMachineParam)
    {
        if(amTowerMachineParam==null) return AjaxResult.error("全空塔机参数禁止插入");
        Long pid;
        amTowerMachineParam.setInsertPerson(getUsername());
        amTowerMachineParam.setInsertPersonDepartId(getDeptId());
        amTowerMachineParam.setInsertDate(new Date());
        int result=amTowerMachineParamService.insertSelective(amTowerMachineParam);
        if(result<0)
            return AjaxResult.error("添加失败");
        pid= amTowerMachineParam.getId();
        List<AmTowerMachineParamPart> parts=amTowerMachineParam.getAmTowerMachineParamParts();
        if(parts!=null&&parts.size()>0&&!parts.isEmpty()) {
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
    @Log(title = "塔机参数", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody  AmTowerMachineParam amTowerMachineParam)
    {
        Long pid= amTowerMachineParam.getId();
        List<AmTowerMachineParamPart> parts=amTowerMachineParam.getAmTowerMachineParamParts();
        amTowerMachineParam.setUpdatePerson(getUsername());
        amTowerMachineParam.setUpdatePersonDepartId(getDeptId());
        amTowerMachineParam.setUpdateDate(new Date());
        int result=amTowerMachineParamService.updateByPrimaryKeySelective(amTowerMachineParam);
        result*=amTowerMachineParamPartService.deleteByPid(pid);
        for (AmTowerMachineParamPart part:parts){
            part.setPid(pid);
            result*=amTowerMachineParamPartService.insertSelective(part);
        }
        return toAjax(result);
    }

}
