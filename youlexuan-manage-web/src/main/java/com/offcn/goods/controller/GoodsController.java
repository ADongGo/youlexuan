package com.offcn.goods.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.offcn.goods.service.BrandService;
import com.offcn.goods.service.GoodsService;
import com.offcn.goods.service.ItemCatService;
import com.offcn.pojo.TbGoods;
import com.offcn.pojo.TbItemCat;
import com.offcn.vo.resp.CommonResult;
import com.offcn.vo.resp.PageResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Reference(timeout = 10000)
    private GoodsService goodsService;
    @Reference(timeout = 10000)
    private ItemCatService itemCatService;

    @RequestMapping("/getGoodsList")
    public PageResult getGoodsList(int pageNum, int pageSize, @RequestBody TbGoods searchEntity){

        return goodsService.getGoodsList(pageNum,pageSize,searchEntity,"");
    }

    @RequestMapping("/getItemCatList")
    public List<TbItemCat> getItemCatList(){

        return itemCatService.getItemCatList();
    }

    @RequestMapping("/updateGoodsStatus")
    public CommonResult updateGoodsStatus(Long[] ids,String status){
        try{
            goodsService.updateGoodsStatus(ids,status);
            return new CommonResult(true,"审核成功",null);
        }catch (Exception e){
            e.printStackTrace();
            return new CommonResult(false,"审核失败",null);
        }
    }

    @RequestMapping("/deleteGoods")
    public CommonResult deleteGoods(Long[] ids){
        try{
            goodsService.deleteGoods(ids);
            return new CommonResult(true,"删除成功",null);
        }catch (Exception e){
            e.printStackTrace();
            return new CommonResult(false,"删除失败",null);
        }
    }

    @RequestMapping("/getBrandName")
    public String getBrandName(Long id){

        return goodsService.getBrandName(id);
    }
}
