package com.offcn.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.offcn.pojo.TbSeller;
import com.offcn.seller.service.SellerService;
import com.offcn.vo.resp.CommonResult;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seller")
public class SellerController {

    @Reference
    private SellerService sellerService;

    @RequestMapping("/register")
    public CommonResult register(@RequestBody TbSeller tbSeller){

        try{
            //密码加密
            BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
            if(tbSeller!=null && tbSeller.getPassword()!=null && !tbSeller.getPassword().equals("")){
                String rawPassword=tbSeller.getPassword();
                String password=encoder.encode(rawPassword);
                tbSeller.setPassword(password);
            }
            sellerService.register(tbSeller);
            return new CommonResult(true,"注册成功",tbSeller);
        }catch (Exception e){
            e.printStackTrace();
            return new CommonResult(false,"注册失败",tbSeller);
        }
    }

    @RequestMapping("/checkRawPassword")
    public boolean checkRawPassword(String rawPassword){

        //从security上下文对象中获取name
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        TbSeller tbSeller=sellerService.getSellerBySellerId(name);

        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        boolean bol = encoder.matches(rawPassword, tbSeller.getPassword());
        return bol;
    }

    @RequestMapping("/updateNewPassword")
    public boolean updateNewPassword(String newPassword){

        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        String password = encoder.encode(newPassword);

        //从security上下文对象中获取name
        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        //获取sellerId
        return sellerService.updateNewPassword(password,name);
    }

    @RequestMapping("/updateSeller")
    public CommonResult updateSeller(@RequestBody TbSeller seller){

        try {
            //从上下文获取sellerId
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            seller.setSellerId(name);
            sellerService.updateSeller(seller);
            return new CommonResult(true,"保存成功",null);
        } catch (Exception e) {
            e.printStackTrace();
            return new CommonResult(false,"保存失败",null);
        }
    }

    @RequestMapping("/getSeller")
    public TbSeller getSeller(){

        //从上下文获取sellerId
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return sellerService.getSellerBySellerId(name);
    }
}
