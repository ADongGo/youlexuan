package com.offcn.goods.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.offcn.goods.service.ItemCatService;
import com.offcn.goods.service.TypeService;
import com.offcn.pojo.TbItemCat;
import com.offcn.pojo.TbTypeTemplate;
import com.offcn.vo.resp.CommonResult;
import com.offcn.vo.resp.PageResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/itemCat")
public class ItemCatController {

    @Reference
    private ItemCatService itemcatService;
    @Reference
    private TypeService typeService;

    @RequestMapping("/search")
    public PageResult findItemcatByParentId(int pageNum, int pageSize,Long parentId){

        return itemcatService.findItemcatByParentId(pageNum,pageSize,parentId);
    }

    @RequestMapping("/add")
    public CommonResult add(@RequestBody TbItemCat itemCat){

        try{
            itemcatService.add(itemCat);
            return new CommonResult(true,"添加成功",null);
        }catch (Exception e){
            e.printStackTrace();
            return new CommonResult(false,"添加失败",null);
        }
    }

    @RequestMapping("/update")
    public CommonResult update(@RequestBody TbItemCat itemCat){

        try{
            itemcatService.update(itemCat);
            return new CommonResult(true,"修改成功",null);
        }catch (Exception e){
            e.printStackTrace();
            return new CommonResult(false,"修改失败",null);
        }
    }

    @RequestMapping("/findOne")
    public TbItemCat findOne(Long id){

        return itemcatService.findOne(id);
    }

    @RequestMapping("/deleteByIds")
    public CommonResult deleteByIds(Long[] ids){

        try{
            itemcatService.deleteByIds(ids);
            return new CommonResult(true,"删除成功",null);
        }catch (Exception e){
            e.printStackTrace();
            return new CommonResult(false,"删除失败",null);
        }
    }

    @RequestMapping("/allTemplate")
    public List<TbTypeTemplate> allTemplate(){

        return typeService.typeList();
    }
}
