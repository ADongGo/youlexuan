package com.offcn.goods.service;

import com.offcn.pojo.TbGoods;
import com.offcn.vo.resp.GoodsVO;
import com.offcn.vo.resp.PageResult;

import java.util.List;

public interface GoodsService {

    /**
     * 添加商品组合实体类
     * @param goodsVO
     */
    public void add(GoodsVO goodsVO);

    /**
     * 分页查询商品列表
     * @param pageNum
     * @param pageSize
     * @param searchEntity
     * @return
     */
    public PageResult getGoodsList(int pageNum,int pageSize,TbGoods searchEntity,String sellerId);

    /**
     * 批量审核商品
     * @param ids
     * @param status
     */
    public void updateGoodsStatus(Long[] ids,String status);

    /**
     * 批量删除商品
     * @param ids
     */
    public void deleteGoods(Long[] ids);

    /**
     * 获取品牌名称显示在商品详情页面
     * @param id
     * @return
     */
    public String getBrandName(Long id);

    /**
     * 商家后台对商品进行上架和下架的操作
     * @param isId
     */
    public void updateMarket(Long id,String isId);
}
