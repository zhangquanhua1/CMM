package com.ConstructionManagement.web.controller.assetmanage;

import com.ConstructionManagement.common.annotation.Log;
import com.ConstructionManagement.common.core.controller.BaseController;
import com.ConstructionManagement.common.core.domain.AjaxResult;
import com.ConstructionManagement.common.core.page.TableDataInfo;
import com.ConstructionManagement.common.enums.BusinessType;
import com.ConstructionManagement.system.domain.*;
import com.ConstructionManagement.system.service.IAmElevatorParamKitService;
import com.ConstructionManagement.system.service.IAmElevatorParamPartService;
import com.ConstructionManagement.system.service.IAmElevatorParamService;
import com.ConstructionManagement.system.service.IAmPartParamKitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/asset/elevatorparam")
public class AmElevatorParamController extends BaseController {
    @Autowired
    IAmElevatorParamService iAmElevatorParamService;
    @Autowired
    IAmElevatorParamKitService iAmElevatorParamKitService;
    @Autowired
    IAmElevatorParamPartService iAmElevatorParamPartService;
    @Autowired
    private IAmPartParamKitService iamPartParamKitService;
    /**
     * 获取列表
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:elevatorparam:list')")
    @GetMapping("/list")
    public TableDataInfo list(AmElevatorParam amElevatorParam) {
        startPage();
        List<AmElevatorParam> amElevatorParams = iAmElevatorParamService.selectBySelective(amElevatorParam);
        return getDataTable(amElevatorParams);
    }
    /**
     * 获取部件 配件
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:elevatorparam:list')")
    @GetMapping("/kitandpart/{pid}")
    public AjaxResult getlist(@PathVariable Long pid) {
        if(pid<=0||pid==null) return AjaxResult.error("该电梯不存在配件、部件");

        List<AmElevatorParamPart> listParts=iAmElevatorParamPartService.selectByPid(pid);
        List<AmPartParamKit> listKits=new ArrayList<AmPartParamKit>();
        for (AmElevatorParamPart part:listParts) {
            List<AmPartParamKit> Kits=iamPartParamKitService.selectByPid(part.getPart_id());
            for (AmPartParamKit appk:Kits ) {
                appk.setKitCount(appk.getKitCount()*part.getPartCount());
                listKits.add(appk);
            }
        }
        ElevatorKitPart kp=new ElevatorKitPart();

        kp.setAmPartParamKits(listKits);
        kp.setAmElevatorParamParts(listParts);
        return AjaxResult.success(kp);
    }

    /**
     * 删除
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:elevatorparam:remove')")
    @Log(title = "电梯参数", businessType = BusinessType.DELETE)
    @DeleteMapping("/{Ids}")
    public AjaxResult remove(@PathVariable Long[] Ids)
    {
        return toAjax(iAmElevatorParamService.deleteByIds(Ids));
    }

    /**
     * 新增
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:elevatorparam:add')")
    @Log(title = "电梯参数", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody AmElevatorParam amElevatorParam)
    {
        if(amElevatorParam==null) return AjaxResult.error("全空电梯参数禁止插入");
        Long pid;
        amElevatorParam.setInsertPerson(getUsername());
        amElevatorParam.setInsertPersonDepartId(getDeptId());
        amElevatorParam.setInsertDate(new Date());
        int result=iAmElevatorParamService.insertSelective(amElevatorParam);
        if(result<0)
            return AjaxResult.error("添加失败");
        pid= amElevatorParam.getId();
        List<AmElevatorParamPart> parts=amElevatorParam.getAmElevatorParamParts();
        if(parts!=null&&parts.size()>0&&!parts.isEmpty()) {
            for (AmElevatorParamPart part : parts) {
                part.setElevatorId(pid);
                result *= iAmElevatorParamPartService.insertSelective(part);
            }
        }
        return toAjax(result);
    }

    /**
     * 修改保存角色
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:elevatorparam:edit')")
    @Log(title = "电梯参数", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody AmElevatorParam amElevatorParam)
    {
        Long pid= amElevatorParam.getId();
        List<AmElevatorParamPart> parts=amElevatorParam.getAmElevatorParamParts();
        amElevatorParam.setUpdatePerson(getUsername());
        amElevatorParam.setUpdatePersonDepartId(getDeptId());
        amElevatorParam.setUpdateDate(new Date());
        int result=iAmElevatorParamService.updateByPrimaryKeySelective(amElevatorParam);
        result*=iAmElevatorParamPartService.deleteByPid(pid);
        for (AmElevatorParamPart part:parts){
            part.setElevatorId(pid);
            result*=iAmElevatorParamPartService.insertSelective(part);
        }
        return toAjax(result);
    }




}
