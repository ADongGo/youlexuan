package com.offcn.goods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.offcn.common.enums.goods.GoodsStatusEnum;
import com.offcn.goods.service.GoodsService;
import com.offcn.mapper.*;
import com.offcn.pojo.*;
import com.offcn.vo.resp.GoodsVO;
import com.offcn.vo.resp.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Transactional
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private TbGoodsMapper goodsMapper;
    @Autowired
    private TbGoodsDescMapper goodsDescMapper;
    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private TbItemCatMapper itemCatMapper;
    @Autowired
    private TbBrandMapper brandMapper;
    @Autowired
    private TbSellerMapper sellerMapper;

    @Override
    public void add(GoodsVO goodsVO) {

        //添加商品
        TbGoods goods = goodsVO.getGoods();

        //定义了一个枚举,设置商品审和状态 0:未审核,1:审和通过,2:驳回
        goods.setAuditStatus(GoodsStatusEnum.GOODS_DEFAULT_STATUS.getStatus());
        //设置未上架
        goods.setIsMarketable("0");
        //设置是否删除
        goods.setIsDelete("0");
        goodsMapper.insert(goods);
        //修改mapper文件,添加映射主键进实体类
        TbGoodsDesc goodsDesc=goodsVO.getGoodsDesc();
        goodsDesc.setGoodsId(goods.getId());
        //添加商品详情
        goodsDescMapper.insert(goodsDesc);
        //添加skuList
        List<TbItem> skuList = goodsVO.getSkuList();
        for(TbItem item:skuList){
            item.setTitle(createSkuTitle(goods,item));
            //将goodDesc中的itemImages字段中的第一个图片url赋值给image
            item.setImage(setImage(goodsDesc));
            item.setCategoryid(goods.getCategory3Id());//设置叶子（末级分类id、第三级）
            item.setCreateTime(new Date());
            item.setUpdateTime(new Date());

            item.setGoodsId(goods.getId());
            item.setSellerId(goods.getSellerId());

            item.setCategory(itemCatMapper.selectByPrimaryKey(goods.getCategory3Id()).getName());//第三级分类名称
            item.setBrand(brandMapper.selectByPrimaryKey(goods.getBrandId()).getName());//品牌名称
            item.setSeller(sellerMapper.selectByPrimaryKey(goods.getSellerId()).getName());//商家名称

            itemMapper.insert(item);
        }
    }

    @Override
    public PageResult getGoodsList(int pageNum,int pageSize,TbGoods searchEntity,String sellerId) {

        TbGoodsExample goodsExample=new TbGoodsExample();
        TbGoodsExample.Criteria criteria = goodsExample.createCriteria();
        criteria.andIsDeleteEqualTo("0");
        //商家后台根据sellerId显示
        if(!sellerId.equals("")){
            criteria.andSellerIdEqualTo(sellerId);
        }
        if(searchEntity!=null){
            if(searchEntity.getGoodsName()!=null && searchEntity.getGoodsName().length()>0){
                criteria.andGoodsNameLike("%"+searchEntity.getGoodsName()+"%");
            }
            if(searchEntity.getAuditStatus()!=null && searchEntity.getAuditStatus().length()>0){
                criteria.andAuditStatusEqualTo(searchEntity.getAuditStatus());
            }
        }
        PageHelper.startPage(pageNum,pageSize);
        Page<TbGoods> page=(Page<TbGoods>) goodsMapper.selectByExample(goodsExample);
        PageResult pageResult=new PageResult();
        pageResult.setRows(page.getResult());
        pageResult.setTotal(page.getTotal());
        return pageResult;
    }

    @Override
    public void updateGoodsStatus(Long[] ids, String status) {

        for(Long id:ids){
            TbGoods goods=new TbGoods();
            goods.setId(id);
            goods.setAuditStatus(status);
            goodsMapper.updateByPrimaryKeySelective(goods);
        }
    }

    @Override
    public void deleteGoods(Long[] ids) {

        for(Long id:ids){
            TbGoods goods=new TbGoods();
            goods.setId(id);
            goods.setIsDelete("1");
            goodsMapper.updateByPrimaryKeySelective(goods);
        }
    }

    @Override
    public String getBrandName(Long id) {

        return brandMapper.selectByPrimaryKey(id).getName();
    }

    @Override
    public void updateMarket(Long id,String isId) {

        TbGoods goods=new TbGoods();
        goods.setId(id);
        goods.setIsMarketable(isId);
        goodsMapper.updateByPrimaryKeySelective(goods);
    }

    //创建skuTitle
    private String createSkuTitle(TbGoods goods,TbItem item){
        String title=goods.getGoodsName();
        String spec=item.getSpec();
        Map map= JSON.parseObject(spec,Map.class);
        for(Object key:map.keySet()){
            Object value=map.get(key);
            title+=value+" ";
        }
        return title;
    }
    //获取image地址
    private String setImage(TbGoodsDesc goodsDesc){

        String itemImages=goodsDesc.getItemImages();
        List<Map> list = JSON.parseArray(itemImages, Map.class);
        if(list!=null && list.size()>0){
            return list.get(0).get("url").toString();
        }
        return "";
    }
}
