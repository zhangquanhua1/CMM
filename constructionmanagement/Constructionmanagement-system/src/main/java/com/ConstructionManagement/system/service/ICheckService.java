package com.ConstructionManagement.system.service;


import com.ConstructionManagement.system.domain.Check;
import com.ConstructionManagement.system.domain.Inventory;

import java.util.List;

/**
 * 盘点Service接口
 *
 * @author ruoyi
 * @date 2022-05-22
 */
public interface ICheckService
{
    /**
     * 查询盘点
     *
     * @param id 盘点主键
     * @return 盘点
     */
    public Check selectCheckById(Long id);

    /**
     * 查询盘点列表
     *
     * @param check 盘点
     * @return 盘点集合
     */
    public List<Check> selectCheckList(Check check);

    /**
     * 新增盘点
     *
     * @param check 盘点
     * @return 结果
     */
    public int insertCheck(Check check);

    /**
     * 修改盘点
     *
     * @param check 盘点
     * @return 结果
     */
    public int updateCheck(Check check);

    /**
     * 批量删除盘点
     *
     * @param ids 需要删除的盘点主键集合
     * @return 结果
     */
    public int deleteCheckByIds(Long[] ids);

    /**
     * 删除盘点信息
     *
     * @param id 盘点主键
     * @return 结果
     */
    public int deleteCheckById(Long id);


    public List<Inventory> selectInventoryByCheckId(Long id);
}
