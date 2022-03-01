package com.ConstructionManagement.web.controller.assetmanage;

import com.ConstructionManagement.common.annotation.Log;
import com.ConstructionManagement.common.constant.UserConstants;
import com.ConstructionManagement.common.core.controller.BaseController;
import com.ConstructionManagement.common.core.domain.AjaxResult;
import com.ConstructionManagement.common.core.domain.entity.SysMenu;
import com.ConstructionManagement.common.core.domain.entity.SysRole;
import com.ConstructionManagement.common.core.domain.model.LoginUser;
import com.ConstructionManagement.common.core.page.TableDataInfo;
import com.ConstructionManagement.common.enums.BusinessType;
import com.ConstructionManagement.common.utils.StringUtils;
import com.ConstructionManagement.system.domain.AmTowerMachineParam;
import com.ConstructionManagement.system.domain.AmTowerMachineParamKit;
import com.ConstructionManagement.system.domain.AmTowerMachineParamPart;
import com.ConstructionManagement.system.mapper.AmTowerMachineParamMapper;
import com.ConstructionManagement.system.service.IAmTowerMachineParamKitService;
import com.ConstructionManagement.system.service.IAmTowerMachineParamPartService;
import com.ConstructionManagement.system.service.IAmTowerMachineParamService;
import com.ConstructionManagement.system.service.ISysRoleService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/asset/manage")
public class AmTowerMachineParamController extends BaseController {
    @Autowired
    private IAmTowerMachineParamService amTowerMachineParamService;
    @Autowired
    private IAmTowerMachineParamKitService amTowerMachineParamKitService;
    @Autowired
    private IAmTowerMachineParamPartService amTowerMachineParamPartService;

    /**
     * 获取列表
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:towermachine:list')")
    @GetMapping("/towermachine/list")
    public TableDataInfo list(AmTowerMachineParam amTowerMachineParam) {
        //System.out.println("TowerMachineModel"+amTowerMachineParam.getTowerMachineModel());
        startPage();
        List<AmTowerMachineParam> amTowerMachineParams = amTowerMachineParamService.selectBySelective(amTowerMachineParam);
        return getDataTable(amTowerMachineParams);
    }

    /**
     * 删除
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:towermachine:remove')")
    @Log(title = "塔机参数", businessType = BusinessType.DELETE)
    @DeleteMapping("/{towermachineIds}")
    public AjaxResult remove(@PathVariable Long[] towermachineIds)
    {
        //System.out.println("删除");
        return toAjax(amTowerMachineParamService.deleteByIds(towermachineIds));
    }

    /**
     * 新增角色
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:towermachine:add')")
    @Log(title = "塔机参数", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody  AmTowerMachineParam amTowerMachineParam)
    {
        if(amTowerMachineParam==null) return AjaxResult.error("全空塔机参数禁止插入");
        String towerMachineModel=amTowerMachineParam.getTowerMachineModel();
        String towerMachineName=amTowerMachineParam.getTowerMachineName();
        String vender=amTowerMachineParam.getVender();
        Long pid= amTowerMachineParam.getId();
        if (towerMachineModel!=null&&towerMachineName!=null&&null!=vender&&amTowerMachineParamService.selectByParam(towerMachineModel,towerMachineName, vender)!=null) {
            return AjaxResult.error("该塔机参数已存在");
        }
        List<AmTowerMachineParamKit> kits=amTowerMachineParam.getAmTowerMachineParamkits();
        List<AmTowerMachineParamPart> parts=amTowerMachineParam.getAmTowerMachineParamParts();
        amTowerMachineParam.setInsertPerson(getUsername());
        amTowerMachineParam.setInsertPersonDepartId(getDeptId());
        amTowerMachineParam.setInsertDate(new Date());
        int result=amTowerMachineParamService.insertSelective(amTowerMachineParam);
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
    @PreAuthorize("@ss.hasPermi('asset:manage:towermachine:edit')")
    @Log(title = "塔机参数", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody  AmTowerMachineParam amTowerMachineParam)
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
