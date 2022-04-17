package com.ConstructionManagement.web.controller.inventorymanagement;

import com.ConstructionManagement.common.annotation.Log;
import com.ConstructionManagement.common.core.controller.BaseController;
import com.ConstructionManagement.common.core.domain.AjaxResult;
import com.ConstructionManagement.common.core.page.TableDataInfo;
import com.ConstructionManagement.common.core.redis.RedisCache;
import com.ConstructionManagement.common.enums.BusinessType;
import com.ConstructionManagement.common.exception.ServiceException;
import com.ConstructionManagement.system.domain.Inventory;
import com.ConstructionManagement.system.domain.StockOutRecord;
import com.ConstructionManagement.system.domain.WmPartEntry;
import com.ConstructionManagement.system.domain.WmPartEntryKit;
import com.ConstructionManagement.system.service.IInventoryService;
import com.ConstructionManagement.system.service.IStockOutRecordService;
import com.ConstructionManagement.web.controller.ExportUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/inventory/query")
public class InventoryController extends BaseController {
    @Autowired
    IInventoryService iis;
    @Autowired
    IStockOutRecordService isor;
    @Autowired
    private RedisCache redisCache;

    private final String InventoryQueryKey = "InventoryQueryKey";
    //库存记录
    public static final String StockOutRecordKey = "StockOutRecordKey";
    /**
     * 获取列表
     */
    @PreAuthorize("@ss.hasPermi('inventory:manage:query:list')")
    @GetMapping("/list")
    public TableDataInfo list(Inventory inventory) {
        startPage();
        List<Inventory> its;
//        if ((its = redisCache.getCacheObject(InventoryQueryKey)) != null&&!its.isEmpty())
//            return getDataTable(its);
        its = iis.selectBySelective(inventory);
        redisCache.setCacheObject(InventoryQueryKey, its);
        return getDataTable(its);
    }

    /**
     * 删除
     */
    @PreAuthorize("@ss.hasPermi('inventory:manage:query:remove')")
    @Log(title = "库存查询", businessType = BusinessType.DELETE)
    @DeleteMapping("/{Ids}")
    public AjaxResult remove(@PathVariable Long[] Ids) {
        redisCache.deleteObject(InventoryQueryKey);
        return toAjax(iis.deleteByIds(Ids));
    }

    /**
     * 新增
     */
    @PreAuthorize("@ss.hasPermi('inventory:manage:query:add')")
    @Log(title = "库存查询", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody Inventory inventory) {
        redisCache.deleteObject(InventoryQueryKey);
        int result = iis.insertSelective(inventory);
        return toAjax(result);
    }

    /**
     * 修改保存
     */
    @PreAuthorize("@ss.hasPermi('inventory:manage:query:edit')")
    @Log(title = "库存查询", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody Inventory inventory) {
        redisCache.deleteObject(InventoryQueryKey);
        int result = iis.updateByPrimaryKeySelective(inventory);
        return toAjax(result);
    }

    /**
     * 出库操作
     */
    @PreAuthorize("@ss.hasPermi('inventory:manage:query:out')")
    @Log(title = "库存出库", businessType = BusinessType.UPDATE)
    @PutMapping("/outStock")
    @Transactional
    public AjaxResult outStock(@Validated @RequestBody List<Inventory> inventory) {
        redisCache.deleteObject(InventoryQueryKey);
        StringBuilder failureMsg = new StringBuilder();
        if(inventory!=null&&inventory.size() > 0 && !inventory.isEmpty()){
            for(Inventory e: inventory){
                if(e.getAmount()== 0)
                    continue;
                else if(e.getAmount()<0){
                    String type="";
                    switch(e.getType()){
                        case 1:{
                            type="设备";
                            break;
                        }
                        case 2:{

                        }
                        case 4:{}
                        case 6:{
                            type="配件";
                            break;
                        }
                        case 3:{}
                        case 5:{
                            type="部件";
                            break;
                        }
                    }
                    failureMsg.append("<br/>" +e.getName()+" "+e.getModel()+" "+type+" "+e.getBelongWarehouse()+" 出库数量不能为负数");
                    continue;
                }
               int result=iis.outStock(e.getId(), e.getAmount());
               if(result<0){
                   String type="";
                   switch(e.getType()){
                       case 1:{
                           type="设备";
                           break;
                       }
                       case 2:{

                       }
                       case 4:{}
                       case 6:{
                           type="配件";
                           break;
                       }
                       case 3:{}
                       case 5:{
                           type="部件";
                           break;
                       }
                   }
                   failureMsg.append("<br/>" +e.getName()+" "+e.getModel()+" "+type+" "+e.getBelongWarehouse());
               }else{
                   redisCache.deleteObject(StockOutRecordKey);
                  StockOutRecord st=new StockOutRecord();
                  st.setName(e.getName());
                  st.setModel(e.getModel());
                  st.setBelongWarehouse(e.getBelongWarehouse());
                  st.setType(e.getType());
                  st.setPid(e.getPid());
                  st.setTypeId(e.getTypeId());
                  st.setAmount(e.getAmount());
                  st.setBegindate(new Date());
                  isor.insertSelective(st);
               }
            }
        }
        return AjaxResult.success(failureMsg.toString());
    }

    @Log(title = "库存查询", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('inventory:manage:query:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, Inventory inventory) {
        List<Inventory> list = iis.selectBySelective(inventory);
        new ExportUtil().outPut(response,"库存查询表",list,Inventory.class);
    }

}
