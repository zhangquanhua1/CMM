package com.ConstructionManagement.web.controller.inventorymanagement;

import com.ConstructionManagement.common.annotation.Log;
import com.ConstructionManagement.common.core.controller.BaseController;
import com.ConstructionManagement.common.core.domain.AjaxResult;
import com.ConstructionManagement.common.core.domain.entity.SysUser;
import com.ConstructionManagement.common.core.page.TableDataInfo;
import com.ConstructionManagement.common.core.redis.RedisCache;
import com.ConstructionManagement.common.enums.BusinessType;
import com.ConstructionManagement.common.utils.poi.ExcelUtil;
import com.ConstructionManagement.system.domain.StockOutRecord;
import com.ConstructionManagement.system.domain.StockOutRecord;
import com.ConstructionManagement.system.service.IStockOutRecordService;
import com.ConstructionManagement.system.service.IStockOutRecordService;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/inventory/StockOutRecord")
public class StockOutRecordController extends BaseController {
    @Autowired
    IStockOutRecordService isor;
    @Autowired
    private RedisCache redisCache;
    public static final String StockOutRecordKey = "StockOutRecordKey";

    /**
     * 获取列表
     */
    @PreAuthorize("@ss.hasPermi('inventory:manage:StockOutRecord:list')")
    @GetMapping("/list")
    public TableDataInfo list(StockOutRecord sor) {
        startPage();
        List<StockOutRecord> wwhs;
//        wwhs = redisCache.getCacheObject(StockOutRecordKey);
//        if ((wwhs != null && wwhs.size() > 0) && !wwhs.isEmpty())
//            return getDataTable(wwhs);
        wwhs = isor.selectBySelective(sor);
        //redisCache.setCacheObject(StockOutRecordKey, wwhs);
        return getDataTable(wwhs);

    }


    /**
     * 删除
     */
    @PreAuthorize("@ss.hasPermi('inventory:manage:StockOutRecord:remove')")
    @Log(title = "出库记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{Ids}")
    public AjaxResult remove(@PathVariable Long[] Ids) {
        redisCache.deleteObject(StockOutRecordKey);
        return toAjax(isor.deleteByIds(Ids));
    }

    /**
     * 新增
     */
    @PreAuthorize("@ss.hasPermi('inventory:manage:StockOutRecord:add')")
    @Log(title = "出库记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody StockOutRecord wwh) {
        redisCache.deleteObject(StockOutRecordKey);
        int result = isor.insertSelective(wwh);
        return toAjax(result);
    }

    /**
     * 新增
     */
    @PreAuthorize("@ss.hasPermi('inventory:manage:StockOutRecord:add')")
    @Log(title = "出库记录", businessType = BusinessType.INSERT)
    @PostMapping("/addList")
    public AjaxResult addList(@Validated @RequestBody List<StockOutRecord> wwh) {
        redisCache.deleteObject(StockOutRecordKey);
        int result=1;
        if(wwh!=null&&!wwh.isEmpty()&&wwh.size() > 0){
            for(StockOutRecord w : wwh){
                w.setBegindate(new Date());
                result*=isor.insertSelective(w);
            }
        }
        return toAjax(result);
    }

    /**
     * 修改保存
     */
    @PreAuthorize("@ss.hasPermi('inventory:manage:StockOutRecord:edit')")
    @Log(title = "出库记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody StockOutRecord wwh) {
        redisCache.deleteObject(StockOutRecordKey);
        int result = isor.updateByPrimaryKeySelective(wwh);
        return toAjax(result);
    }

    @Log(title = "出库记录", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('inventory:manage:StockOutRecord:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, StockOutRecord wwh)
    {
        List<StockOutRecord> list = isor.selectBySelective(wwh);
        ExcelUtil<StockOutRecord> util = new ExcelUtil<StockOutRecord>(StockOutRecord.class);
        util.exportExcel(response, list, "出库记录表");
    }

}
