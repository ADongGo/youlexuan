package com.offcn.shop.service;

import com.offcn.mapper.TbSellerMapper;
import com.offcn.pojo.TbSeller;
import com.offcn.seller.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserServcieImpl implements UserDetailsService {

    //远程服务
    private SellerService sellerService;
    public void setSellerService(SellerService sellerService){
        this.sellerService=sellerService;
    }

    //登录时,请求进入到这个方法,通过该方法查询数据库
    @Override
    public UserDetails loadUserByUsername(String sellerId) throws UsernameNotFoundException {

        TbSeller seller=sellerService.getSellerBySellerId(sellerId);

        if(seller==null){
            return null;
        }

        //status=0 商家未审核 1审核通过 通过才能登陆
        if(!seller.getStatus().equals("1")){
            return null;
        }

        //权限信息
        List<GrantedAuthority> authorities=new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_SELLER"));
        User user=new User(seller.getSellerId(),seller.getPassword(),authorities);
        return user;
    }
}
