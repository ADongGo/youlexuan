package com.offcn.goods.service;

import com.offcn.pojo.TbItemCat;
import com.offcn.vo.resp.PageResult;

import java.util.List;

public interface ItemCatService {

    /**
     * 通过上一级id获取分类(分页)
     * @param parentId
     * @return
     */
    public PageResult findItemcatByParentId(int pageNum, int pageSize, Long parentId);

    /**
     * 添加
     * @param itemCat
     */
    public void add(TbItemCat itemCat);

    /**
     * 修改
     * @param itemCat
     */
    public void update(TbItemCat itemCat);

    /**
     * 编辑回显
     * @param id
     * @return
     */
    public TbItemCat findOne(Long id);

    /**
     * 删除
     * @param id
     */
    public void delete(Long id);

    /**
     * 批量删除
     * @param ids
     */
    public void deleteByIds(Long[] ids);

    /**
     * 根据上一级id获取分类
     * @param parentId
     * @return
     */
    public List<TbItemCat> getItemCatByParentId(Long parentId);

    /**
     * 获取所有的分类对象,显示在商品审核的列表中
     * @return
     */
    public List<TbItemCat> getItemCatList();
}
