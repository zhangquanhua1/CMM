package com.ConstructionManagement.web.controller.assetmanage;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.ConstructionManagement.common.annotation.Log;
import com.ConstructionManagement.common.core.controller.BaseController;
import com.ConstructionManagement.common.core.domain.AjaxResult;
import com.ConstructionManagement.common.core.page.TableDataInfo;
import com.ConstructionManagement.common.enums.BusinessType;
import com.ConstructionManagement.system.domain.*;
import com.ConstructionManagement.system.service.IAmKitParamService;
import com.ConstructionManagement.system.service.IAmPartRequireKitService;
import com.ConstructionManagement.system.service.IAmPartRequireService;
import com.ConstructionManagement.web.controller.ExportUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * 部件需求管理
 */
@RestController
@RequestMapping("/asset/partrequire")
public class AmPartRequireController extends BaseController {
    @Autowired
    IAmPartRequireService iAmPartRequireService;
    @Autowired
    IAmPartRequireKitService iAmPartRequireKitService;
    @Autowired
    private IAmKitParamService ikps;

    /**
     * 获取列表
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:partrequire:list')")
    @GetMapping("/list")
    public TableDataInfo list(AmPartRequire amPartRequire) {
        startPage();
        List<AmPartRequire> amPartRequires = iAmPartRequireService.selectBySelective(amPartRequire);
        return getDataTable(amPartRequires);
    }

    /**
     * 获取已审核列表
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:partrequire:isBuy')")
    @GetMapping("/listAudited")
    public TableDataInfo listAudited(AmPartRequire amPartRequire) {
        startPage();
        amPartRequire.setState(1);
        List<AmPartRequire> amKitRequires = iAmPartRequireService.selectBySelective(amPartRequire);
        return getDataTable(amKitRequires);
    }

    /**
     * 获取部件 配件
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:partrequire:list')")
    @GetMapping("/kit/{pid}")
    public AjaxResult getlist(@PathVariable Long pid) {
        if (pid <= 0 || pid == null) return AjaxResult.error("该部件不存在配件");
        return AjaxResult.success(iAmPartRequireKitService.selectByPid(pid));
    }

    /**
     * 获取所选类别的所适用配件
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:partrequire:add')")
    @GetMapping("/kits")
    public AjaxResult getKitS(AmPartRequire amPartRequire) {
        AmKitParam akp = new AmKitParam();
        akp.setApplicableKitType(amPartRequire.getPartType());
        List<AmKitParam> listKits = ikps.selectBySelective(akp);
        return AjaxResult.success(listKits);
    }

    /**
     * 部件已采购
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:partrequire:isBuy')")
    @Log(title = "零部件需求", businessType = BusinessType.UPDATE)
    @PostMapping("/{Ids}")
    public AjaxResult isBuy(@PathVariable Long[] Ids) {
        int result = iAmPartRequireService.isBuyIds(Ids);
        return toAjax(result);
    }

    /**
     * 删除
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:partrequire:remove')")
    @Log(title = "部件需求", businessType = BusinessType.DELETE)
    @DeleteMapping("/{Ids}")
    public AjaxResult remove(@PathVariable Long[] Ids) {
        return toAjax(iAmPartRequireService.deleteByIds(Ids));
    }

    /**
     * 新增
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:partrequire:add')")
    @Log(title = "部件需求", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody AmPartRequire amPartRequire) {
        if (amPartRequire == null)
            return AjaxResult.error("全空部件需求禁止插入");
        Long pid;
        amPartRequire.setInsertPerson(getUsername());
        amPartRequire.setInsertPersonDepartId(getDeptId());
        amPartRequire.setInsertDate(new Date());
        int result = iAmPartRequireService.insertSelective(amPartRequire);
        if (result < 0)
            return AjaxResult.error("添加失败");
        pid = amPartRequire.getId();
        List<AmPartRequireKit> kits = amPartRequire.getAmPartRequireKits();
        if (kits != null && kits.size() > 0 && !kits.isEmpty()) {
            for (AmPartRequireKit kit : kits) {
                kit.setPartID(pid);
                result *= iAmPartRequireKitService.insertSelective(kit);
            }
        }
        return toAjax(result);
    }

    /**
     * 修改保存角色
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:partrequire:edit') or @ss.hasPermi('asset:manage:partrequire:audit')")
    @Log(title = "部件需求", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody AmPartRequire amPartRequire) {
        Long pid = amPartRequire.getId();
        List<AmPartRequireKit> kits = amPartRequire.getAmPartRequireKits();
        amPartRequire.setUpdatePerson(getUsername());
        amPartRequire.setUpdatePersonDepartId(getDeptId());
        amPartRequire.setUpdateDate(new Date());
        int result = iAmPartRequireService.updateByPrimaryKeySelective(amPartRequire);
        if (kits.isEmpty() || kits.size() <= 0 || kits == null)
            return toAjax(result);
        result *= iAmPartRequireKitService.deleteByPid(pid);

        for (AmPartRequireKit kit : kits) {
            kit.setPartID(pid);
            result *= iAmPartRequireKitService.insertSelective(kit);
        }
        return toAjax(result);
    }

    /**
     * 部件需求导出
     *
     * @param response
     * @param wwh
     */
    @Log(title = "部件需求", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('asset:manage:partrequire:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, AmPartRequire wwh) {
        List<AmPartRequire> list = iAmPartRequireService.selectBySelective(wwh);
        if (list != null && list.size() > 0 && !list.isEmpty()) {
            for (AmPartRequire wpe : list) {
                List<AmPartRequireKit> kits = iAmPartRequireKitService.selectByPid(wpe.getId());
                wpe.setAmPartRequireKits(kits);
            }
        }
        new ExportUtil().outPut(response, "部件需求表", list, AmPartRequire.class);
    }

    /**
     * 部件需求导出
     *
     * @param response
     * @param wwh
     */
    @Log(title = "部件采购", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('asset:manage:partrequire:export')")
    @PostMapping("/exportBuy")
    public void exportBuy(HttpServletResponse response, AmPartRequire wwh) {
        wwh.setState(1);
        List<AmPartRequire> list = iAmPartRequireService.selectBySelective(wwh);
        if (list != null && list.size() > 0 && !list.isEmpty()) {
            for (AmPartRequire wpe : list) {
                List<AmPartRequireKit> kits = iAmPartRequireKitService.selectByPid(wpe.getId());
                wpe.setAmPartRequireKits(kits);
            }
        }
        new ExportUtil().outPut(response, "部件采购表", list, AmPartRequire.class);
    }

    @Log(title = "部件需求", businessType = BusinessType.IMPORT)
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
        ExcelImportResult<AmPartRequire> result = ExcelImportUtil.importExcelMore(file.getInputStream(), AmPartRequire.class, params);
        String operName = getUsername();
        String message = iAmPartRequireService.importData(result, updateSupport, operName);
        return AjaxResult.success(message);
    }

    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        try {
            InputStream fis = Thread.currentThread().getContextClassLoader().getResourceAsStream("static/PRTemplate.xlsx");
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
