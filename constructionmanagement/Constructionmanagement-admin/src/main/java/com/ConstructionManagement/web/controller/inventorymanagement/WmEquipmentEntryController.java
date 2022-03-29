package com.ConstructionManagement.web.controller.inventorymanagement;

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
import io.lettuce.core.dynamic.annotation.Param;
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

    @Autowired
    IInventoryService iis;

    /**
     * 获取设备录入列表
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
     * 根据id获取设备详情
     *
     * @param id
     * @return
     */
    @PreAuthorize("@ss.hasPermi('inventory:manage:equipmententry:list')")
    @GetMapping("/{id}")
    public AjaxResult selectById(@PathVariable Long id) {
        WmEquipmentEntry result = iw.selectById(id);
        return AjaxResult.success(result);
    }

    /**
     * 根据配件id获取设备绑定的配件的详情
     *
     * @param id
     * @return
     */
    @PreAuthorize("@ss.hasPermi('inventory:manage:equipmententry:list')")
    @GetMapping("/kit/{id}")
    public AjaxResult selectKitById(@PathVariable Long id) {
        WmEquipmentEntryKit result = ieeks.selectById(id);
        return AjaxResult.success(result);
    }

    /**
     * 根据部件id获取设备绑定的部件的详情
     *
     * @param id
     * @return
     */
    @PreAuthorize("@ss.hasPermi('inventory:manage:equipmententry:list')")
    @GetMapping("/part/{id}")
    public AjaxResult selectPartById(@PathVariable Long id) {
        WmEquipmentEntryPart result = ieeps.selectById(id);
        return AjaxResult.success(result);
    }

    /**
     * 设备录入的部件和配件详情
     */
    @PreAuthorize("@ss.hasPermi('inventory:manage:equipmententry:list')")
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
        long indexPart = 0;
        long indexKit = 0;
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
    public AjaxResult confirm(@RequestBody List<WmEquipmentEntry> eqe) {
        redisCache.deleteObject(wmEquipmentEntryKey);
        int result = 0;
        if (eqe != null && !eqe.isEmpty() && eqe.size() > 0) {
            for (WmEquipmentEntry wke : eqe) {
                Inventory ivt = new Inventory();
                ivt.setName(wke.getEquipmentName());
                //设置为设备
                ivt.setType(1);
                ivt.setModel(wke.getStandardModel());
                ivt.setAmount(wke.getAmount());
                ivt.setTypeId(wke.getId());
                ivt.setBelongWarehouse(wke.getBelongWarehouse());
                result = iw.confirmById(wke.getId());
                if (result > 0) {
                    result = iis.insertSelective(ivt);
                    List<WmEquipmentEntryKit> kits = ieeks.selectByEquipmentId(wke.getId());
                    List<WmEquipmentEntryPart> parts = ieeps.selectByEquipmentId(wke.getId());
                    if (kits != null && !kits.isEmpty() && kits.size() > 0) {
                        for (WmEquipmentEntryKit kit : kits) {
                            Inventory ivt1 = new Inventory();
                            ivt1.setName(kit.getKitName());
                            //设置为设备-配件
                            ivt1.setType(4);
                            ivt1.setModel(kit.getKitModel());
                            ivt1.setAmount((long) (kit.getKitCount()));
                            ivt1.setTypeId(kit.getId());
                            ivt1.setBelongWarehouse(wke.getBelongWarehouse());
                            ivt1.setPid(wke.getId());
                            result = iis.insertSelective(ivt1);
                        }
                    }
                    if (parts != null && !parts.isEmpty() && parts.size() > 0) {
                        for (WmEquipmentEntryPart part : parts) {
                            Inventory ivt1 = new Inventory();
                            ivt1.setName(part.getPartName());
                            //设置为设备-部件
                            ivt1.setType(5);
                            ivt1.setModel(part.getPartModel());
                            ivt1.setAmount((long) (part.getPartCount()));
                            ivt1.setTypeId(part.getId());
                            ivt1.setBelongWarehouse(wke.getBelongWarehouse());
                            ivt1.setPid(wke.getId());
                            result = iis.insertSelective(ivt1);
                        }
                    }
                }
            }
        }
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

    /**
     * 设备录入导出
     * @param response
     * @param wwh
     */
    @Log(title = "设备录入", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('inventory:manage:equipmententry:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmEquipmentEntry wwh) {
        List<WmEquipmentEntry> list = iw.selectBySelective(wwh);
        if (list != null && list.size() > 0 && !list.isEmpty()) {
            for (WmEquipmentEntry wpe : list) {
                List<WmEquipmentEntryKit> kits = ieeks.selectByEquipmentId(wpe.getId());
                List<WmEquipmentEntryPart> parts = ieeps.selectByEquipmentId(wpe.getId());
                wpe.setWmEquipmentEntryKits(kits);
                wpe.setWmEquipmentEntryParts(parts);
            }

        }
        new ExportUtil().outPut(response, "设备录入表", list, WmEquipmentEntry.class);
    }

    /**
     * 设备信息确认导出
     * @param response
     * @param wwh
     */

    @Log(title = "设备信息确认", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('inventory:manage:equipmententry:export')")
    @PostMapping("/exportConfirm")
    public void exportConfirm(HttpServletResponse response, WmEquipmentEntry wwh) {
        List<WmEquipmentEntry> list = iw.selectBySelective(wwh);
        if (list != null && list.size() > 0 && !list.isEmpty()) {
            for (WmEquipmentEntry wpe : list) {
                List<WmEquipmentEntryKit> kits = ieeks.selectByEquipmentId(wpe.getId());
                List<WmEquipmentEntryPart> parts = ieeps.selectByEquipmentId(wpe.getId());
                wpe.setWmEquipmentEntryKits(kits);
                wpe.setWmEquipmentEntryParts(parts);
            }

        }
        new ExportUtil().outPut(response, "设备信息确认表", list, WmEquipmentEntry.class);
    }

    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        try {
            InputStream fis = Thread.currentThread().getContextClassLoader().getResourceAsStream("static/EETemplate.xlsx");
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

    @Log(title = "设备录入", businessType = BusinessType.IMPORT)
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
        ExcelImportResult<WmEquipmentEntry> result = ExcelImportUtil.importExcelMore(file.getInputStream(), WmEquipmentEntry.class, params);
        String operName = getUsername();
        String message = iw.importData(result, updateSupport, operName);
        return AjaxResult.success(message);
    }

}
