package com.offcn.goods.service;

import com.offcn.pojo.TbBrand;
import com.offcn.vo.resp.PageResult;

import java.util.List;

public interface BrandService {

    /**
     * 查询所有品牌信息
     * @return
     */
    public List<TbBrand> brandList();

    /**
     * 条件查询
     * @param tbBrand
     * @return
     */
    public List<TbBrand> brandLikeList(TbBrand tbBrand);

    /**
     * 添加
     * @param tbBrand
     */
    public void add(TbBrand tbBrand);

    /**
     * 删除一个
     * @param id
     */
    public void deleteById(Long id);

    /**
     * 批量删除
     * @param ids
     */
    public void deleteByIds(Long[] ids);

    /**
     * 更新
     * @param tbBrand
     */
    public void update (TbBrand tbBrand);

    /**
     * 获取一个回显
     * @return
     */
    public TbBrand getTbBrandById(Long id);

    /**
     * 分页加模糊查询tbBrand
     * @param pageNum
     * @param pageSize
     * @param brand
     * @return
     */
    public PageResult findPage(int pageNum,int pageSize,TbBrand brand);
}
