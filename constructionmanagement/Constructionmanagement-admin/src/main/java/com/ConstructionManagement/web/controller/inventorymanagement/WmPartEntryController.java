package com.ConstructionManagement.web.controller.inventorymanagement;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.hutool.core.date.DateUtil;
import com.ConstructionManagement.common.annotation.Log;
import com.ConstructionManagement.common.core.controller.BaseController;
import com.ConstructionManagement.common.core.domain.AjaxResult;
import com.ConstructionManagement.common.core.page.TableDataInfo;
import com.ConstructionManagement.common.enums.BusinessType;
import com.ConstructionManagement.common.utils.poi.ExcelUtil;
import com.ConstructionManagement.system.domain.*;
import com.ConstructionManagement.system.service.IInventoryService;
import com.ConstructionManagement.system.service.IWmPartEntryKitService;
import com.ConstructionManagement.system.service.IWmPartEntryService;
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
import java.util.*;

@RestController
@RequestMapping("/inventory/partEntry")
public class WmPartEntryController extends BaseController {
    @Autowired
    IWmPartEntryService pes;
    @Autowired
    IWmPartEntryKitService peks;
    @Autowired
    IInventoryService iis;

    /**
     * 获取部件录入列表
     */
    @PreAuthorize("@ss.hasPermi('inventory:manage:partEntry:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmPartEntry wee) {
        List<WmPartEntry> wees;
        startPage();
        wees = pes.selectBySelective(wee);
        return getDataTable(wees);
    }

    /**
     * 根据部件id获取部件详情
     *
     * @param id
     * @return
     */
    @PreAuthorize("@ss.hasPermi('inventory:manage:partEntry:list')")
    @GetMapping("/{id}")
    public AjaxResult selectById(@PathVariable Long id) {
        WmPartEntry result = pes.selectByPrimaryKey(id);
        return AjaxResult.success(result);
    }

    /**
     * 根据配件id获取部件绑定的配件的详情
     *
     * @param id
     * @return
     */
    @PreAuthorize("@ss.hasPermi('inventory:manage:partEntry:list')")
    @GetMapping("/kitDetail/{id}")
    public AjaxResult selectKitById(@PathVariable Long id) {
        WmPartEntryKit result = peks.selectByPrimaryKey(id);
        return AjaxResult.success(result);
    }


    /**
     * 部件录入的配件详情
     */
    @PreAuthorize("@ss.hasPermi('inventory:manage:partEntry:list')")
    @GetMapping("/getKits/{pid}")
    public AjaxResult getlistKitsAndParts(@PathVariable Long pid) {
        if (pid <= 0 || pid == null) return AjaxResult.error("不存在配件、部件");
        List<WmPartEntryKit> kits = peks.selectByPartId(pid);
        return AjaxResult.success(kits);
    }


    /**
     * 删除
     */
    @PreAuthorize("@ss.hasPermi('inventory:manage:partEntry:remove')")
    @Log(title = "部件录入", businessType = BusinessType.DELETE)
    @DeleteMapping("/{Ids}")
    public AjaxResult remove(@PathVariable Long[] Ids) {
        return toAjax(pes.deleteByIds(Ids));
    }

    /**
     * 新增
     */
    @PreAuthorize("@ss.hasPermi('inventory:manage:partEntry:add')")
    @Log(title = "部件录入", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody WmPartEntry wee) {
        wee.setInsertPerson(getUsername());
        wee.setInsertPersonDepartId(getDeptId());
        wee.setInsertDate(new Date());
        int result = pes.insertSelective(wee);
        result *= insertKitAndPart(wee);

        return toAjax(result);
    }

    //部件录入的配件插入
    public int insertKitAndPart(WmPartEntry wee) {
        int result = 1;
        long equipmentId = wee.getId();
        List<WmPartEntryKit> kits = wee.getKits();
        if (kits != null && !kits.isEmpty() && kits.size() > 0) {
            for (WmPartEntryKit kit : kits) {
                kit.setPartId(equipmentId);
                result *= peks.insertSelective(kit);
            }
        }
        return result;
    }

    /**
     * 修改保存
     */
    @PreAuthorize("@ss.hasPermi('inventory:manage:partEntry:edit') ")
    @Log(title = "部件录入", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody WmPartEntry wee) {
        wee.setUpdatePerson(getUsername());
        wee.setUpdatePersonDepartId(getDeptId());
        wee.setUpdateDate(new Date());
        int result = pes.updateByPrimaryKeySelective(wee);
        long equipmentId = wee.getId();
        result *= peks.deleteByPartId(equipmentId);
        result *= insertKitAndPart(wee);
        return toAjax(result);
    }

    /**
     * 部件信息确认
     */
    @PreAuthorize("@ss.hasPermi('inventory:manage:partEntry:confirm')")
    @Log(title = "部件信息确认", businessType = BusinessType.UPDATE)
    @PutMapping("/confirm")
    public AjaxResult confirm(@RequestBody List<WmPartEntry> eqe) {
        int result = 0;
        if (eqe != null && !eqe.isEmpty() && eqe.size() > 0) {
            for (WmPartEntry wke : eqe) {
                Inventory ivt = new Inventory();
                ivt.setName(wke.getPartName());
                //设置为部件
                ivt.setType(3);
                ivt.setModel(wke.getPartModel());
                ivt.setAmount(wke.getAmount());
                ivt.setTypeId(wke.getId());
                ivt.setBelongWarehouse(wke.getBelongWarehouse());
                result = pes.confirmById(wke.getId());
                if (result > 0) {
                    result = iis.insertSelective(ivt);
                    List<WmPartEntryKit> kits = peks.selectByPartId(wke.getId());
                    if (kits != null && !kits.isEmpty() && kits.size() > 0) {
                        for (WmPartEntryKit kit : kits) {
                            Inventory ivt1 = new Inventory();
                            ivt1.setName(kit.getKitName());
                            //设置为部件-配件
                            ivt1.setType(6);
                            ivt1.setModel(kit.getKitModel());
                            ivt1.setAmount((long) (kit.getKitCount()));
                            ivt1.setTypeId(kit.getId());
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
     * 部件信息反确认
     */
    @PreAuthorize("@ss.hasPermi('inventory:manage:partEntry:confirm')")
    @Log(title = "部件信息反确认", businessType = BusinessType.UPDATE)
    @PutMapping("/AntiConfirm")
    public AjaxResult AntiConfirm(@RequestBody Long[] ids) {
        int result = pes.AntiConfirmByIds(ids);
        return toAjax(result);
    }

    @Log(title = "部件录入", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('inventory:manage:partEntry:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmPartEntry wwh) {
        List<WmPartEntry> list = pes.selectBySelective(wwh);
        if (list != null && list.size() > 0 && !list.isEmpty()) {
            for (WmPartEntry wpe : list) {
                List<WmPartEntryKit> kits = peks.selectByPartId(wpe.getId());
                wpe.setKits(kits);
            }
        }
        new ExportUtil().outPut(response,"部件录入表",list,WmPartEntry.class);
    }

    @Log(title = "部件录入", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('inventory:manage:partEntry:export')")
    @PostMapping("/exportConfirm")
    public void exportConfirm(HttpServletResponse response, WmPartEntry wwh) {
        List<WmPartEntry> list = pes.selectBySelective(wwh);
        if (list != null && list.size() > 0 && !list.isEmpty()) {
            for (WmPartEntry wpe : list) {
                List<WmPartEntryKit> kits = peks.selectByPartId(wpe.getId());
                wpe.setKits(kits);
            }
        }
        new ExportUtil().outPut(response,"部件信息确认表",list,WmPartEntry.class);
    }

    @Log(title = "部件录入", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('inventory:manage:partEntry:import')")
    @PostMapping("/importData")
    public AjaxResult importData(HttpServletResponse response, MultipartFile file, boolean updateSupport) throws Exception {
        if (file == null)
            return AjaxResult.error("导入的文件内容不能为空");
        ImportParams params = new ImportParams();
        params.setNeedVerify(true);
        params.setVerifyFileSplit(false);
        params.setTitleRows(0);
        params.setHeadRows(2);
        ExcelImportResult<WmPartEntry> result = ExcelImportUtil.importExcelMore(file.getInputStream(), WmPartEntry.class, params);
        String operName = getUsername();
        String message = pes.importData(result, updateSupport, operName);
        return AjaxResult.success(message);
    }

    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        try {
            InputStream fis = Thread.currentThread().getContextClassLoader().getResourceAsStream("static/PETemplate.xlsx");
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
}
