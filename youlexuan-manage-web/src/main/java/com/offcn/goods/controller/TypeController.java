package com.offcn.goods.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.offcn.goods.service.TypeService;
import com.offcn.pojo.TbTypeTemplate;
import com.offcn.vo.resp.CommonResult;
import com.offcn.vo.resp.PageResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/type")
public class TypeController {

    @Reference
    private TypeService typeService;

    @RequestMapping("/search")
    public PageResult search(int pageNum, int pageSize, @RequestBody TbTypeTemplate typeTemplate){

        return typeService.search(pageNum,pageSize,typeTemplate);
    }

    @RequestMapping("/findOne")
    public TbTypeTemplate findOne(Long id){

        return typeService.findOne(id);
    }

    @RequestMapping("/add")
    public CommonResult add(@RequestBody TbTypeTemplate typeTemplate){

        try{
            typeService.add(typeTemplate);
            return new CommonResult(true,"保存成功",null);
        }catch (Exception e){
            e.printStackTrace();
            return new CommonResult(false,"保存失败",null);
        }
    }

    @RequestMapping("/update")
    public CommonResult update(@RequestBody TbTypeTemplate typeTemplate){

        try{
            typeService.update(typeTemplate);
            return new CommonResult(true,"修改成功",null);
        }catch (Exception e){
            e.printStackTrace();
            return new CommonResult(false,"修改失败",null);
        }
    }

    @RequestMapping("/deleteByIds")
    public CommonResult deleteByIds(Long[] ids){

        try{
            typeService.deleteByIds(ids);
            return new CommonResult(true,"删除成功",null);
        }catch (Exception e){
            e.printStackTrace();
            return new CommonResult(false,"删除失败",null);
        }
    }

    @RequestMapping("/getBrandAndSpec")
    public Map getBrandAndSpec(){

        return typeService.getBrandAndSpecList();
    }
}
