package com.offcn.vo.resp;

import com.offcn.pojo.TbGoods;
import com.offcn.pojo.TbGoodsDesc;
import com.offcn.pojo.TbItem;

import java.io.Serializable;
import java.util.List;

public class GoodsVO implements Serializable {

    //spu
    private TbGoods goods;
    //spu的扩展表
    private TbGoodsDesc goodsDesc;
    //一个spu对应多个sku
    private List<TbItem> skuList;

    public TbGoods getGoods() {
        return goods;
    }

    public void setGoods(TbGoods goods) {
        this.goods = goods;
    }

    public TbGoodsDesc getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(TbGoodsDesc goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    public List<TbItem> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<TbItem> skuList) {
        this.skuList = skuList;
    }
}
