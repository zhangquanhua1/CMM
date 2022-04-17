package com.ConstructionManagement.web.controller.inventorymanagement;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.ConstructionManagement.common.annotation.Log;
import com.ConstructionManagement.common.core.controller.BaseController;
import com.ConstructionManagement.common.core.domain.AjaxResult;
import com.ConstructionManagement.common.core.page.TableDataInfo;
import com.ConstructionManagement.common.enums.BusinessType;
import com.ConstructionManagement.system.domain.Inventory;
import com.ConstructionManagement.system.domain.OutStockApply;
import com.ConstructionManagement.system.domain.StockOutRecord;
import com.ConstructionManagement.system.service.IInventoryService;
import com.ConstructionManagement.system.service.IOutStockApplyService;
import com.ConstructionManagement.system.service.IStockOutRecordService;
import com.ConstructionManagement.web.controller.ExportUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/inventory/OutStockApply")
public class OutStockApplyController extends BaseController {

    @Autowired
    IOutStockApplyService ios;
    @Autowired
    IInventoryService iis;
    @Autowired
    IStockOutRecordService isor;
    /**
     * 获取列表
     */
    @PreAuthorize("@ss.hasPermi('inventory:manage:OutStockApply:list')")
    @GetMapping("/list")
    public TableDataInfo list(OutStockApply sor) {
        startPage();
        List<OutStockApply> wwhs;
        wwhs = ios.selectBySelective(sor);
        return getDataTable(wwhs);

    }


    /**
     * 删除
     */
    @PreAuthorize("@ss.hasPermi('inventory:manage:OutStockApply:remove')")
    @Log(title = "出库申请", businessType = BusinessType.DELETE)
    @DeleteMapping("/{Ids}")
    public AjaxResult remove(@PathVariable Long[] Ids) {
        return toAjax(ios.deleteByIds(Ids));
    }

    /**
     * 新增
     */
    @PreAuthorize("@ss.hasPermi('inventory:manage:OutStockApply:add')")
    @Log(title = "出库申请", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody OutStockApply wwh) {
        int result = ios.insertSelective(wwh);
        return toAjax(result);
    }

    /**
     * 修改保存
     */
    @PreAuthorize("@ss.hasPermi('inventory:manage:OutStockApply:edit')")
    @Log(title = "出库申请", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody OutStockApply wwh) {
        int result = ios.updateByPrimaryKeySelective(wwh);
        return toAjax(result);
    }

    @Log(title = "出库申请", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('inventory:manage:OutStockApply:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, OutStockApply wwh)
    {
        List<OutStockApply> list = ios.selectBySelective(wwh);
        new ExportUtil().outPut(response, "出库申请表", list, OutStockApply.class);
    }
    @Log(title = "出库申请", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('inventory:manage:OutStockApply:import')")
    @PostMapping("/importData")
    public AjaxResult importData(HttpServletResponse response, MultipartFile file, boolean updateSupport) throws Exception {
        if (file == null)
            return AjaxResult.error("导入的文件内容不能为空");
        ImportParams params = new ImportParams();
        params.setNeedVerify(true);
        params.setVerifyFileSplit(false);
        params.setTitleRows(0);
        params.setHeadRows(1);
        ExcelImportResult<OutStockApply> result = ExcelImportUtil.importExcelMore(file.getInputStream(), OutStockApply.class, params);
        String operName = getUsername();
        String message = ios.importData(result, updateSupport, operName);
        return AjaxResult.success(message);
    }

    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        try {
            InputStream fis = Thread.currentThread().getContextClassLoader().getResourceAsStream("static/OSATemplate.xlsx");
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
     * 审核是否出库
     */
    @PreAuthorize("@ss.hasPermi('inventory:manage:OutStockApply:audit')")
    @Log(title = "出库申请", businessType = BusinessType.UPDATE)
    @PutMapping("/WhetherOutStock")
    @Transactional
    public AjaxResult Audit(@Validated @RequestBody OutStockApply wwh) {
        int result=1;
        if(wwh.getStatus()==2){
            result = ios.updateByPrimaryKeySelective(wwh);
        }else if(wwh.getStatus()==1){
            List<Inventory> list = null;
            if(wwh.getType()==1) {
                list = iis.selectByParam(wwh.getName(), new int[]{1}, wwh.getModel());
            }else if(wwh.getType()==2){
                list = iis.selectByParam(wwh.getName(), new int[]{2,4,6}, wwh.getModel());
            }else if(wwh.getType()==3){
                list = iis.selectByParam(wwh.getName(), new int[]{3,5}, wwh.getModel());
            }
            Long total= 0L;
            for(Inventory e:list){
                total += e.getAmount();
            }
            if(total<wwh.getAmount()){
                return AjaxResult.error("库存不足");
            }else{
                Long totalOut=wwh.getAmount();
                for(Inventory e:list){
                   if(totalOut>0){
                     if(e.getAmount()<=totalOut){
                         result*=iis.outStock(e.getId(), e.getAmount());
                         StockOutRecord st=new StockOutRecord();
                         st.setName(e.getName());
                         st.setModel(e.getModel());
                         st.setBelongWarehouse(e.getBelongWarehouse());
                         st.setType(e.getType());
                         st.setPid(e.getPid());
                         st.setTypeId(e.getTypeId());
                         st.setAmount(e.getAmount());
                         st.setBegindate(new Date());
                         result*=isor.insertSelective(st);
                         totalOut-=e.getAmount();
                     }else{
                         result*=iis.outStock(e.getId(), totalOut);
                         StockOutRecord st=new StockOutRecord();
                         st.setName(e.getName());
                         st.setModel(e.getModel());
                         st.setBelongWarehouse(e.getBelongWarehouse());
                         st.setType(e.getType());
                         st.setPid(e.getPid());
                         st.setTypeId(e.getTypeId());
                         st.setAmount(totalOut);
                         st.setBegindate(new Date());
                         result*=isor.insertSelective(st);
                         totalOut=0L;
                     }
                   }else{
                       break;
                   }
                }
                result*= ios.updateByPrimaryKeySelective(wwh);
            }

        }
        return toAjax(result);
    }



}
