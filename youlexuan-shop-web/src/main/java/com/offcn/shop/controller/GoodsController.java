package com.offcn.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.offcn.goods.service.GoodsService;
import com.offcn.goods.service.ItemCatService;
import com.offcn.goods.service.TypeService;
import com.offcn.pojo.TbGoods;
import com.offcn.pojo.TbItemCat;
import com.offcn.pojo.TbTypeTemplate;
import com.offcn.vo.resp.CommonResult;
import com.offcn.vo.resp.GoodsVO;
import com.offcn.vo.resp.PageResult;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Reference
    private ItemCatService itemCatService;
    @Reference
    private TypeService typeService;
    @Reference
    private GoodsService goodsService;

    @RequestMapping("/getItemCatByParentId")
    public List<TbItemCat> getItemCatByParentId(Long parentId){

        return itemCatService.getItemCatByParentId(parentId);
    }

    @RequestMapping("/getOneItemCat")
    public TbItemCat getOneItemCat(Long id){

        return itemCatService.findOne(id);
    }

    @RequestMapping("/getType")
    public TbTypeTemplate getType(Long id){

        return typeService.findOne(id);
    }

    @RequestMapping("/add")
    public CommonResult add(@RequestBody GoodsVO goodsVO){

        try{
            //获取sellerId
            String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
            goodsVO.getGoods().setSellerId(sellerId);
            goodsService.add(goodsVO);

            return new CommonResult(true,"添加成功",null);
        }catch (Exception e){
            e.printStackTrace();
            return new CommonResult(false,"添加失败",null);
        }
    }

    @RequestMapping("/getSpecAndOptionList")
    public List<Map> getSpecAndOptionList(Long templateId){

        return typeService.getSpecAndOptionList(templateId);
    }

    @RequestMapping("/getGoodsListBySellerId")
    public PageResult getGoodsListBySellerId(int pageNum, int pageSize, @RequestBody TbGoods searchEntity){

        String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
        return goodsService.getGoodsList(pageNum,pageSize,searchEntity,sellerId);
    }

    @RequestMapping("/getItemCatListSeller")
    public List<TbItemCat> getItemCatList(){

        return itemCatService.getItemCatList();
    }

    @RequestMapping("/updateMarket")
    public CommonResult updateMarket(Long id,String isId){

        try{
            goodsService.updateMarket(id,isId);
            return new CommonResult(true,"操作成功",null);
        }catch (Exception e){
            e.printStackTrace();
            return new CommonResult(false,"操作失败",null);
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
}
