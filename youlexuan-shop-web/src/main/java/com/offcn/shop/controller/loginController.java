package com.offcn.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.offcn.seller.service.SellerService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class loginController {

    @Reference
    private SellerService sellerService;

    @RequestMapping("/showSellerName")
    public Map showSellerName(){

        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Map map=new HashMap();
        map.put("name",name);
        return map;
    }

    @RequestMapping("/showSellerTime")
    public Map showSellerTime(){

        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return sellerService.showSellerTime(name);
    }

    @RequestMapping("/setLoginTime")
    public void setLoginTime(){

        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        sellerService.setLoginTime(name);
    }

}
