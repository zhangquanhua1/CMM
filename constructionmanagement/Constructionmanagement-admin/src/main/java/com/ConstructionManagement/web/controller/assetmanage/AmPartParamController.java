package com.ConstructionManagement.web.controller.assetmanage;

import com.ConstructionManagement.common.annotation.Log;
import com.ConstructionManagement.common.core.controller.BaseController;
import com.ConstructionManagement.common.core.domain.AjaxResult;
import com.ConstructionManagement.common.core.page.TableDataInfo;
import com.ConstructionManagement.common.enums.BusinessType;
import com.ConstructionManagement.system.domain.*;
import com.ConstructionManagement.system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/asset/partparam")
public class AmPartParamController extends BaseController {
    @Autowired
    private IAmPartParamService iAmPartParamService;
    @Autowired
    private IAmPartParamKitService iamPartParamKitService;

    /**
     * 获取列表
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:partparam:list')")
    @GetMapping("/list")
    public TableDataInfo list(AmPartParam amPartParam) {
        //System.out.println("TowerMachineModel"+amTowerMachineParam.getTowerMachineModel());
        startPage();
        List<AmPartParam> amPartParams = iAmPartParamService.selectBySelective(amPartParam);
        return getDataTable(amPartParams);
    }
    /**
     * 获取 配件
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:partparam:list')")
    @GetMapping("/kit/{pid}")
    public AjaxResult getlist(@PathVariable Long pid) {
        if(pid<=0||pid==null) return AjaxResult.error("该部件不存在配件");
        List<AmPartParamKit> listKits=iamPartParamKitService.selectByPid(pid);;
        return AjaxResult.success(listKits);
    }

    /**
     * 删除
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:partparam:remove')")
    @Log(title = "配件参数", businessType = BusinessType.DELETE)
    @DeleteMapping("/{Ids}")
    public AjaxResult remove(@PathVariable Long[] Ids)
    {
        //System.out.println("删除");
        return toAjax(iAmPartParamService.deleteByIds(Ids));
    }

    /**
     * 新增
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:partparam:add')")
    @Log(title = "塔机参数", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody AmKitParam amKitParam)
    {
        if(amKitParam==null) return AjaxResult.error("全空部件参数禁止插入");
        String towerMachineModel=amTowerMachineParam.getTowerMachineModel();
        String towerMachineName=amTowerMachineParam.getTowerMachineName();
        String vender=amTowerMachineParam.getVender();
        Long pid;
        if (towerMachineModel!=null&&towerMachineName!=null&&null!=vender&&amTowerMachineParamService.selectByParam(towerMachineModel,towerMachineName, vender)!=null) {
            return AjaxResult.error("该塔机参数已存在");
        }
        amTowerMachineParam.setInsertPerson(getUsername());
        amTowerMachineParam.setInsertPersonDepartId(getDeptId());
        amTowerMachineParam.setInsertDate(new Date());
        int result=amTowerMachineParamService.insertSelective(amTowerMachineParam);
        //System.out.println("result:"+amTowerMachineParam.getId());
        if(result<0)
            return AjaxResult.error("添加失败");
        pid= amTowerMachineParam.getId();
        List<AmTowerMachineParamKit> kits=amTowerMachineParam.getAmTowerMachineParamkits();
        List<AmTowerMachineParamPart> parts=amTowerMachineParam.getAmTowerMachineParamParts();

        if(kits!=null&&kits.size()>0&&!kits.isEmpty()) {
            for (AmTowerMachineParamKit kit : kits) {
                kit.setPid(pid);
                result *= amTowerMachineParamKitService.insertSelective(kit);

            }
        }
        if(parts!=null&&parts.size()>0&&!parts.isEmpty()) {
            for (AmTowerMachineParamPart part : parts) {
                part.setPid(pid);
                result *= amTowerMachineParamPartService.insertSelective(part);
            }
        }
        return toAjax(result);
    }

    /**
     * 修改保存角色
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:partparam:edit')")
    @Log(title = "塔机参数", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody AmTowerMachineParam amTowerMachineParam)
    {
        Long pid= amTowerMachineParam.getId();
        List<AmTowerMachineParamKit> kits=amTowerMachineParam.getAmTowerMachineParamkits();
        List<AmTowerMachineParamPart> parts=amTowerMachineParam.getAmTowerMachineParamParts();
        amTowerMachineParam.setUpdatePerson(getUsername());
        amTowerMachineParam.setUpdatePersonDepartId(getDeptId());
        amTowerMachineParam.setUpdateDate(new Date());
        int result=amTowerMachineParamService.updateByPrimaryKeySelective(amTowerMachineParam);
        result*=amTowerMachineParamKitService.deleteByPid(pid);
        result*=amTowerMachineParamPartService.deleteByPid(pid);
        for (AmTowerMachineParamKit kit: kits) {
            kit.setPid(pid);
            result*=amTowerMachineParamKitService.insertSelective(kit);

        }
        for (AmTowerMachineParamPart part:parts){
            part.setPid(pid);
            result*=amTowerMachineParamPartService.insertSelective(part);
        }
        return toAjax(result);
    }
}
