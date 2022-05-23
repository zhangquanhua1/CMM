package com.ConstructionManagement.web.controller.inventorymanagement;

import com.ConstructionManagement.common.annotation.Log;
import com.ConstructionManagement.common.core.controller.BaseController;
import com.ConstructionManagement.common.core.domain.AjaxResult;
import com.ConstructionManagement.common.core.page.TableDataInfo;
import com.ConstructionManagement.common.core.redis.RedisCache;
import com.ConstructionManagement.common.enums.BusinessType;
import com.ConstructionManagement.common.utils.poi.ExcelUtil;
import com.ConstructionManagement.system.domain.*;
import com.ConstructionManagement.system.service.IInventoryService;
import com.ConstructionManagement.system.service.IWmKitEntryService;
import io.lettuce.core.dynamic.annotation.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/inventory/kitEntry")
public class WmKitEntryController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(WmKitEntryController.class);

    @Autowired
    IWmKitEntryService ke;
    @Autowired
    private RedisCache redisCache;
    public static final String wmKitEntryKey = "WmKitEntryKey";

    @Autowired
    IInventoryService iis;

    /**
     * 获取列表
     */
    @PreAuthorize("@ss.hasPermi('inventory:manage:kitentry:list')")
    @GetMapping("/list")
    public TableDataInfo list(WmKitEntry wee, @Param("objectValueAllEmpty") boolean objectValueAllEmpty) {
        List<WmKitEntry> wees;
        startPage();
        wees = redisCache.getCacheObject(wmKitEntryKey);
        if (objectValueAllEmpty && (wees != null && wees.size() > 0) && !wees.isEmpty())
            return getDataTable(wees);
        else if (objectValueAllEmpty) {
            wees = ke.selectBySelective(null);
            redisCache.setCacheObject(wmKitEntryKey, wees);
            return getDataTable(wees);
        }
        wees = ke.selectBySelective(wee);
        return getDataTable(wees);
    }

    /**
     * 根据id 获取配件详情
     * @param id
     * @return
     */
    @PreAuthorize("@ss.hasPermi('inventory:manage:kitentry:list')")
    @GetMapping("/{id}")
    public AjaxResult selectById(@PathVariable Long id) {
        if(id==null) return AjaxResult.success("参数为空");
        WmKitEntry result = ke.selectById(id);
        return AjaxResult.success(result);
    }

    /**
     * 删除
     */
    @PreAuthorize("@ss.hasPermi('inventory:manage:kitentry:remove')")
    @Log(title = "零配件录入", businessType = BusinessType.DELETE)
    @DeleteMapping("/{Ids}")
    public AjaxResult remove(@PathVariable Long[] Ids) {
        redisCache.deleteObject(wmKitEntryKey);
        return toAjax(ke.deleteByIds(Ids));
    }

    /**
     * 新增
     */
    @PreAuthorize("@ss.hasPermi('inventory:manage:kitentry:add')")
    @Log(title = "零配件录入", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody WmKitEntry wee) {
        if (wee == null)
            return AjaxResult.error("全空零配件录入禁止插入");
        wee.setInsertPerson(getUsername());
        wee.setInsertPersonDepartId(getDeptId());
        wee.setInsertDate(new Date());
        redisCache.deleteObject(wmKitEntryKey);
        int result = ke.insertSelective(wee);
        return toAjax(result);
    }

    /**
     * 修改保存
     */
    @PreAuthorize("@ss.hasPermi('inventory:manage:kitentry:edit')")
    @Log(title = "零配件录入", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody WmKitEntry wee) {
        redisCache.deleteObject(wmKitEntryKey);
        wee.setUpdatePerson(getUsername());
        wee.setUpdatePersonDepartId(getDeptId());
        wee.setUpdateDate(new Date());
        int result = ke.updateByPrimaryKeySelective(wee);
        return toAjax(result);
    }

    /**
     * 零配件信息确认
     */
    @PreAuthorize("@ss.hasPermi('inventory:manage:kitentry:confirm')")
    @Log(title = "零配件信息确认", businessType = BusinessType.UPDATE)
    @PostMapping("/confirm")
    public AjaxResult confirm(@RequestBody List<WmKitEntry> wkets) {
        logger.info("零信息确认items" + (wkets.size()));
        redisCache.deleteObject(wmKitEntryKey);
        int result = 0;
        for (int i = 0; i < wkets.size(); i++) {
            WmKitEntry wke = wkets.get(i);
            Inventory ivt = new Inventory();
            ivt.setName(wke.getKitName());
            ivt.setType(2);
            ivt.setModel(wke.getModel());
            ivt.setAmount(wke.getAmount());
            ivt.setTypeId(wke.getId());
            ivt.setBelongWarehouse(wke.getWarehouse());
            result = ke.confirmById(wke.getId());
            if (result > 0) {
                iis.insertSelective(ivt);
            }

        }
        return toAjax(result);
    }

    /**
     * 零配件信息反确认
     */
    @PreAuthorize("@ss.hasPermi('inventory:manage:kitentry:confirm')")
    @Log(title = "零配件信息反确认", businessType = BusinessType.UPDATE)
    @PutMapping("/AntiConfirm")
    public AjaxResult AntiConfirm(@RequestBody Long[] ids) {
        if (ids == null || ids.length == 0) return AjaxResult.error("反确认失败");
        redisCache.deleteObject(wmKitEntryKey);
        int result = ke.AntiConfirmByIds(ids);
        return toAjax(result);
    }

    @Log(title = "配件录入", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('inventory:manage:kitentry:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, WmKitEntry wwh)
    {
        List<WmKitEntry> list = ke.selectBySelective(wwh);
        ExcelUtil<WmKitEntry> util = new ExcelUtil<>(WmKitEntry.class);
        util.exportExcel(response, list, "配件录入表");
    }

    @Log(title = "配件信息确认", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('inventory:manage:kitentry:export')")
    @PostMapping("/exportConfirm")
    public void exportConfirm(HttpServletResponse response, WmKitEntry wwh)
    {

        List<WmKitEntry> list = ke.selectBySelective(wwh);
        ExcelUtil<WmKitEntry> util = new ExcelUtil<>(WmKitEntry.class);
        util.exportExcel(response, list, "配件信息确认表");
    }


    @Log(title = "配件录入", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('inventory:manage:kitentry:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {

        redisCache.deleteObject(wmKitEntryKey);
        ExcelUtil<WmKitEntry> util = new ExcelUtil<WmKitEntry>(WmKitEntry.class);
        List<WmKitEntry> list = util.importExcel(file.getInputStream());
        String operName = getUsername();
        String message = ke.importData(list, updateSupport, operName);
        return AjaxResult.success(message);
    }

    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<WmKitEntry> util = new ExcelUtil<WmKitEntry>(WmKitEntry.class);
        util.importTemplateExcel(response, "配件录入样例表");
    }


}
