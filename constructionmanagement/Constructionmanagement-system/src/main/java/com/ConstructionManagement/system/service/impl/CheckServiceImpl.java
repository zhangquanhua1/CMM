package com.ConstructionManagement.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.ConstructionManagement.common.utils.StringUtils;
import com.ConstructionManagement.system.domain.Check;
import com.ConstructionManagement.system.domain.CheckInventory;
import com.ConstructionManagement.system.domain.Inventory;
import com.ConstructionManagement.system.domain.SysUserPost;
import com.ConstructionManagement.system.mapper.CheckInventoryMapper;
import com.ConstructionManagement.system.mapper.CheckMapper;
import com.ConstructionManagement.system.mapper.InventoryMapper;
import com.ConstructionManagement.system.service.ICheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 盘点Service业务层处理
 *
 * @author ruoyi
 * @date 2022-05-22
 */
@Service
public class CheckServiceImpl implements ICheckService
{
    Logger log = Logger.getLogger(String.valueOf(CheckServiceImpl.class));
    @Autowired
    private CheckMapper checkMapper;
    @Autowired
    private CheckInventoryMapper cim;
    @Autowired
    private InventoryMapper im;
    /**
     * 查询盘点
     *
     * @param id 盘点主键
     * @return 盘点
     */
    @Override
    public Check selectCheckById(Long id)
    {
        return checkMapper.selectCheckById(id);
    }

    /**
     * 查询盘点列表
     *
     * @param check 盘点
     * @return 盘点
     */
    @Override
    public List<Check> selectCheckList(Check check)
    {
        return checkMapper.selectCheckList(check);
    }

    /**
     * 新增盘点
     *
     * @param check 盘点
     * @return 结果
     */
    @Override
    public int insertCheck(Check check)
    {
        Long[] inventoryIds=check.getIds();
        int res= checkMapper.insertCheck(check);

        Long checkId=check.getId();
        log.info("checkId "+checkId);
        if(res>0){
            if (StringUtils.isNotNull(inventoryIds))
            {
                List<CheckInventory> list = new ArrayList<CheckInventory>();
                for (Long Id : inventoryIds)
                {
                    CheckInventory up = new CheckInventory();
                    up.setCheckId(checkId);
                    up.setInventoryId(Id);
                    list.add(up);
                }
                if (list.size() > 0)
                {
                    res*=cim.insertBatch(list);
                }
            }
        }
        return res;
    }

    /**
     * 修改盘点
     *
     * @param check 盘点
     * @return 结果
     */
    @Override
    public int updateCheck(Check check)
    {
        return checkMapper.updateCheck(check);
    }

    /**
     * 批量删除盘点
     *
     * @param ids 需要删除的盘点主键
     * @return 结果
     */
    @Override
    public int deleteCheckByIds(Long[] ids)
    {
        return checkMapper.deleteCheckByIds(ids);
    }

    /**
     * 删除盘点信息
     *
     * @param id 盘点主键
     * @return 结果
     */
    @Override
    public int deleteCheckById(Long id)
    {
        return checkMapper.deleteCheckById(id);
    }

    @Override
    public List<Inventory> selectInventoryByCheckId(Long id) {
        List<CheckInventory> list=cim.selectByCheckId(id);
        List<Long> inventoryIds=new ArrayList<Long>();
        for(CheckInventory inventory : list){
            inventoryIds.add(inventory.getInventoryId());
            log.info("inventory.getInventoryId() "+inventory.getInventoryId());
        }
        List<Inventory> res=new ArrayList<Inventory>();
        if(inventoryIds.size()>0)
                res=im.selectByIds(inventoryIds);
        return res;
    }
}
