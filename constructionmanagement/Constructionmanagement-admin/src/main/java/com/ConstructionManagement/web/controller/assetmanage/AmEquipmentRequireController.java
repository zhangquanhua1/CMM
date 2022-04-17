package com.ConstructionManagement.web.controller.assetmanage;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.ConstructionManagement.common.annotation.Log;
import com.ConstructionManagement.common.core.controller.BaseController;
import com.ConstructionManagement.common.core.domain.AjaxResult;
import com.ConstructionManagement.common.core.page.TableDataInfo;
import com.ConstructionManagement.common.core.redis.RedisCache;
import com.ConstructionManagement.common.enums.BusinessType;
import com.ConstructionManagement.system.domain.*;
import com.ConstructionManagement.system.service.*;
import com.ConstructionManagement.web.controller.ExportUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
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
    private IAmTowerMachineParamPartService amTowerMachineParamPartService;
    @Autowired
    IAmPartParamService ipps;
    @Autowired
    private IAmPartParamKitService iamPartParamKitService;
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
     * 获取审核通过的列表
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:equipmentrequire:isBuy')")
    @GetMapping("/listPass")
    public TableDataInfo listPss(AmEquipmentRequire aer) {
        startPage();
        aer.setState(1);
        List<AmEquipmentRequire> aers = ier.selectBySelective(aer);
        return getDataTable(aers);
    }
    /**
     * 设备已采购
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:equipmentrequire:isBuy')")
    @Log(title = "部件需求", businessType = BusinessType.UPDATE)
    @PostMapping("/{Ids}")
    public AjaxResult isBuy(@PathVariable Long[] Ids)
    {
        int result=ier.isBuyIds(Ids);
        return toAjax(result);
    }

    /**
     * 获取所选设备类型及型号的部件、配件
     */
    @PreAuthorize("@ss.hasPermi('inventory:manage:equipmentrequire:add') or @ss.hasPermi('inventory:manage:equipmententry:edit') ")
    @GetMapping("/kitandpart2/{pid}")
    public AjaxResult getByEquipentId(@PathVariable Long pid) {
        if (pid <= 0 || pid == null) return AjaxResult.error("不存在配件、部件");
        List<AmTowerMachineParamPart> listParts = amTowerMachineParamPartService.selectByPid(pid);
        //设备配件
        List<AmEquipmentRequireKit> aerKits = new ArrayList<>();
        //设备部件
        List<AmEquipmentRequirePart> aerParts = new ArrayList<>();
        long indexPart=0;
        long indexKit=0;
        //遍历部件获取部件的配件
        for (AmTowerMachineParamPart part : listParts) {
            AmEquipmentRequirePart rp = new AmEquipmentRequirePart();
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
                AmEquipmentRequireKit ek = new AmEquipmentRequireKit();
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
        kp.setAmEquipmentRequireKits(aerKits);
        kp.setAmEquipmentRequireParts(aerParts);
        return AjaxResult.success(kp);
    }
    /**
     * 获取部件 配件
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:equipmentrequire:list')")
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

    //设备需求的部件和配件插入
    public int insertKitAndPart(AmEquipmentRequire wee) {
        int result = 1;
        long equipmentId = wee.getId();
        List<AmEquipmentRequireKit> kits = wee.getAmEquipmentRequireKits();
        List<AmEquipmentRequirePart> parts = wee.getAmEquipmentRequireParts();
        if (kits != null && !kits.isEmpty() && kits.size() > 0) {
            for (AmEquipmentRequireKit kit : kits) {
                kit.setEquimentId(equipmentId);
                result *= ierk.insertSelective(kit);
            }
        }
        if (parts != null && parts.size() > 0 && !parts.isEmpty()) {
            for (AmEquipmentRequirePart part : parts) {
                part.setEquipmentId(equipmentId);
                result *= ierp.insertSelective(part);
            }
        }
        return result;
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
        result *= insertKitAndPart(aer);
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
        result *= ierk.deleteByEquipmentId(aer.getId());
        result *= ierp.deleteByEquipmentId(aer.getId());
        result *= insertKitAndPart(aer);
        return toAjax(result);
    }

    /**
     * 设备需求导出
     * @param response
     * @param wwh
     */
    @Log(title = "设备需求", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('asset:manage:equipmentrequire:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, AmEquipmentRequire wwh) {
        List<AmEquipmentRequire> list = ier.selectBySelective(wwh);
        if (list != null && list.size() > 0 && !list.isEmpty()) {
            for (AmEquipmentRequire wpe : list) {
                List<AmEquipmentRequireKit> kits = ierk.selectByEquipmentId(wpe.getId());
                List<AmEquipmentRequirePart> parts = ierp.selectByEquipmentId(wpe.getId());
                wpe.setAmEquipmentRequireKits(kits);
                wpe.setAmEquipmentRequireParts(parts);
            }
        }
        new ExportUtil().outPut(response, "设备需求表", list, AmEquipmentRequire.class);
    }

    /**
     * 部件需求导出
     * @param response
     * @param wwh
     */
    @Log(title = "部件采购", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('asset:manage:equipmentrequire:export')")
    @PostMapping("/exportBuy")
    public void exportBuy(HttpServletResponse response, AmEquipmentRequire wwh) {
        wwh.setState(1);
        List<AmEquipmentRequire> list = ier.selectBySelective(wwh);
        if (list != null && list.size() > 0 && !list.isEmpty()) {
            for (AmEquipmentRequire wpe : list) {
                List<AmEquipmentRequireKit> kits = ierk.selectByEquipmentId(wpe.getId());
                List<AmEquipmentRequirePart> parts = ierp.selectByEquipmentId(wpe.getId());
                wpe.setAmEquipmentRequireKits(kits);
                wpe.setAmEquipmentRequireParts(parts);
            }
        }
        new ExportUtil().outPut(response, "设备采购表", list, AmEquipmentRequire.class);
    }

    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        try {
            InputStream fis = Thread.currentThread().getContextClassLoader().getResourceAsStream("static/ERTemplate.xlsx");
            Workbook workbook = WorkbookFactory.create(fis);
            response.setContentType("application/binary;charset=ISO8859-1");
            response.setHeader("Content-disposition", "attachment;  ");
            ServletOutputStream out = null;
            out = response.getOutputStream();
            workbook.write(out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭文件输出流
        }
        return;

    }

    /**
     * 设备需求导入
     * @param response
     * @param file
     * @param updateSupport
     * @return
     * @throws Exception
     */

    @Log(title = "设备需求", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('asset:manage:partrequire:import')")
    @PostMapping("/importData")
    public AjaxResult importData(HttpServletResponse response, MultipartFile file, boolean updateSupport) throws Exception {
        if (file == null)
            return AjaxResult.error("导入的文件内容不能为空");
        ImportParams params = new ImportParams();
        params.setNeedVerify(true);
        params.setVerifyFileSplit(false);
        params.setTitleRows(0);
        params.setHeadRows(2);
        ExcelImportResult<AmEquipmentRequire> result = ExcelImportUtil.importExcelMore(file.getInputStream(), AmEquipmentRequire.class, params);
        String operName = getUsername();
        String message = ier.importData(result, updateSupport, operName);
        return AjaxResult.success(message);
    }


}
