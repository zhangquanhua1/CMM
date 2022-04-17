package com.ConstructionManagement.web.controller.assetmanage;

import com.ConstructionManagement.common.annotation.Log;
import com.ConstructionManagement.common.core.controller.BaseController;
import com.ConstructionManagement.common.core.domain.AjaxResult;
import com.ConstructionManagement.common.core.page.TableDataInfo;
import com.ConstructionManagement.common.core.redis.RedisCache;
import com.ConstructionManagement.common.enums.BusinessType;
import com.ConstructionManagement.common.utils.poi.ExcelUtil;
import com.ConstructionManagement.system.domain.RepairScrap;
import com.ConstructionManagement.system.service.IRepairScrapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/asset/RepairScrap")
public class RepairScrapController extends BaseController {
    @Autowired
    IRepairScrapService irps;
    @Autowired
    private RedisCache redisCache;
    public static final String RepairScrapKey = "RepairScrapKey";

    /**
     * 获取列表
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:RepairScrap:list')")
    @GetMapping("/list")
    public TableDataInfo list(RepairScrap sor) {
        startPage();
        List<RepairScrap> lists;
//        lists = redisCache.getCacheObject(RepairScrapKey);
//        if ((lists != null && lists.size() > 0) && !lists.isEmpty())
//            return getDataTable(lists);
        lists = irps.selectBySelective(sor);
        //redisCache.setCacheObject(RepairScrapKey, lists);
        return getDataTable(lists);

    }


    /**
     * 删除
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:RepairScrap:remove')")
    @Log(title = "维修或报废申请", businessType = BusinessType.DELETE)
    @DeleteMapping("/{Ids}")
    public AjaxResult remove(@PathVariable Long[] Ids) {
        int result = irps.deleteByIds(Ids);
//        if (result > 0)
//            redisCache.deleteObject(RepairScrapKey);
        return toAjax(result);
    }

    /**
     * 新增
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:RepairScrap:add')")
    @Log(title = "维修或报废申请", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody RepairScrap wwh) {
        int result = irps.insertSelective(wwh);
//        if (result > 0)
//            redisCache.deleteObject(RepairScrapKey);
        return toAjax(result);
    }


    /**
     * 修改保存
     */
    @PreAuthorize("@ss.hasPermi('asset:manage:RepairScrap:edit') or @ss.hasPermi('asset:manage:RepairScrap:audit')")
    @Log(title = "维修或报废申请", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody RepairScrap wwh) {
        int result = irps.updateByPrimaryKeySelective(wwh);
//        if (result > 0)
//            redisCache.deleteObject(RepairScrapKey);
        return toAjax(result);
    }

    @Log(title = "维修或报废申请", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('asset:manage:RepairScrap:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, RepairScrap wwh) {
        List<RepairScrap> list = irps.selectBySelective(wwh);
        ExcelUtil<RepairScrap> util = new ExcelUtil<RepairScrap>(RepairScrap.class);
        util.exportExcel(response, list, "维修或报废申请表");
    }
}
