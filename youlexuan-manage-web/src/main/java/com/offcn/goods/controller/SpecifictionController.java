package com.offcn.goods.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.offcn.goods.service.SpecifictionServcie;
import com.offcn.pojo.TbSpecification;
import com.offcn.vo.resp.CommonResult;
import com.offcn.vo.resp.PageResult;
import com.offcn.vo.resp.SpecVo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/spec")
public class SpecifictionController {

    @Reference
    private SpecifictionServcie specifictionServcie;

    @RequestMapping("/search")
    public PageResult search(int pageNum, int pageSize, @RequestBody TbSpecification tbSpecification){

       return specifictionServcie.search(pageNum,pageSize,tbSpecification);
    }

    @RequestMapping("/findOne")
    public SpecVo findOne(Long id){

        return specifictionServcie.findOne(id);
    }

    @RequestMapping("/add")
    public CommonResult add(@RequestBody SpecVo specVo){

        try {
            specifictionServcie.add(specVo);
            return new CommonResult(true,"添加成功",null);
        }catch(Exception e){
            e.printStackTrace();
            return new CommonResult(false,"添加失败",null);
        }
    }

    @RequestMapping("/update")
    public CommonResult update(@RequestBody SpecVo specVo){

        try {
            specifictionServcie.update(specVo);
            return new CommonResult(true,"修改成功",null);
        }catch(Exception e){
            e.printStackTrace();
            return new CommonResult(false,"修改失败",null);
        }
    }

    @RequestMapping("/deleteByIds")
    public CommonResult deleteByIds(Long[] ids){

        try {
            specifictionServcie.deleteByIds(ids);
            return new CommonResult(true,"删除成功",null);
        } catch (Exception e) {
            e.printStackTrace();
            return new CommonResult(false,"删除失败",null);
        }
    }
}
