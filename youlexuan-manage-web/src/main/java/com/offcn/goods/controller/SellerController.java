package com.offcn.goods.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.offcn.pojo.TbSeller;
import com.offcn.seller.service.SellerService;
import com.offcn.vo.resp.CommonResult;
import com.offcn.vo.resp.PageResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/seller")
public class SellerController {

    @Reference
    private SellerService sellerService;

    /**
     * c查询所有商家列表,只显示status=0
     * @return
     */
    @RequestMapping("/sellerList")
    public PageResult sellerList(int pageNum, int pageSize, @RequestBody TbSeller tbSeller){

        return sellerService.getSellerList(pageNum,pageSize,tbSeller);
    }

    @RequestMapping("/updateStatus")
    public CommonResult updateStatus(String sellerId,String status){

        try {
            sellerService.updateStatus(sellerId,status);
            return new CommonResult(true,"审核成功",null);
        } catch (Exception e) {
            e.printStackTrace();
            return new CommonResult(false,"审核失败",null);
        }
    }

    @RequestMapping("/search")
    public List<TbSeller> search(@RequestBody TbSeller tbSeller){

        return sellerService.search(tbSeller);
    }
}
