package com.offcn.goods.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.offcn.goods.service.BrandService;
import com.offcn.pojo.TbBrand;
import com.offcn.vo.resp.CommonResult;

import com.offcn.vo.resp.PageResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/brand")
public class BrandController {

    @Reference
    BrandService brandService;

    @RequestMapping(path = "/list",method = RequestMethod.GET)
    public CommonResult showTbBrandList(){

        CommonResult result=new CommonResult();

        try {
            List<TbBrand> brandList = brandService.brandList();
            result.setSuccess(true);
            result.setMessage("查询成功");
            result.setData(brandList);
            return result;

        } catch (Exception e){
            e.printStackTrace();

            result.setSuccess(false);
            result.setMessage("查询失败");
            result.setData(null);
            return  result;
        }
    }

    @RequestMapping(path = "/list",method = RequestMethod.POST)
    public CommonResult showLikeList(@RequestBody TbBrand tbBrand){

        CommonResult result = new CommonResult();

        List<TbBrand> brandList = null;
        try {
            brandList = brandService.brandLikeList(tbBrand);
            result.setSuccess(true);
            result.setMessage("查询成功");
            result.setData(brandList);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("查询失败，出现查询异常");
            result.setData(null);
            return result;
        }
    }

    @RequestMapping("/add")
    public CommonResult add(@RequestBody TbBrand brand){
        try {
            brandService.add(brand);

            return new CommonResult(true,"添加成功",null);
        } catch (Exception e) {
            e.printStackTrace();
            return new CommonResult(false,"添加失败",null);
        }
    }

    @RequestMapping("/update")
    public CommonResult update(@RequestBody TbBrand brand){
        try {
            brandService.update(brand);
            return new CommonResult(true,"修改成功",null);
        } catch (Exception e) {
            e.printStackTrace();
            return new CommonResult(false,"修改失败",null);
        }

    }

    @RequestMapping("/deleteById")
    public CommonResult delete(Long id){
        try {
            brandService.deleteById(id);
            return new CommonResult(true,"删除成功",null);
        } catch (Exception e) {
            e.printStackTrace();
            return new CommonResult(false,"删除失败",null);
        }
    }

    @RequestMapping("/deleteByIds")
    public CommonResult delete(Long[] ids){
        try {
            brandService.deleteByIds(ids);
            return new CommonResult(true,"批量删除成功",null);
        } catch (Exception e) {
            e.printStackTrace();
            return new CommonResult(false,"批量删除失败",null);
        }
    }

    @RequestMapping("/findOne")
    public CommonResult findOne(Long id){
        TbBrand brand = brandService.getTbBrandById(id);
        return  new CommonResult(true,"查询成功",brand);
    }

    @RequestMapping("/search")
    public PageResult search(int pageNum,int pageSize,@RequestBody TbBrand tbBrand){

        return brandService.findPage(pageNum,pageSize,tbBrand);
    }

}
