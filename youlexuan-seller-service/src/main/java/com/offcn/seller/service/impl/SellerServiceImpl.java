package com.offcn.seller.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.offcn.mapper.TbSellerMapper;
import com.offcn.pojo.TbSeller;
import com.offcn.pojo.TbSellerExample;
import com.offcn.seller.service.SellerService;
import com.offcn.vo.resp.PageResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private TbSellerMapper tbSellerMapper;

    @Override
    public PageResult getSellerList(int pageNum, int pageSize,TbSeller tbSeller) {

        PageHelper.startPage(pageNum,pageSize);
        TbSellerExample example=new TbSellerExample();
        TbSellerExample.Criteria criteria = example.createCriteria();
        if(tbSeller!=null){
            //模糊查询公司名称
            if(tbSeller.getNickName()!=null){
                criteria.andNickNameLike("%"+tbSeller.getNickName()+"%");
            }
            if(tbSeller.getName()!=null){
                criteria.andNameLike("%"+tbSeller.getName()+"%");
            }
        }
        criteria.andStatusEqualTo("0");

        Page<TbSeller> page=(Page<TbSeller>)tbSellerMapper.selectByExample(example);
        PageResult pageResult=new PageResult();
        pageResult.setRows(page.getResult());
        pageResult.setTotal(page.getTotal());
        return pageResult;
    }

    @Override
    public TbSeller getSellerBySellerId(String sellerId) {

        return tbSellerMapper.selectByPrimaryKey(sellerId);
    }

    @Override
    public void register(TbSeller tbSeller) {

        if(tbSeller==null){
            throw new RuntimeException("商家信息不能为空");
        }
        if(tbSeller.getSellerId()==null && tbSeller.getSellerId().equals("")){
            throw new RuntimeException("登录名不能为空");
        }
        if(tbSeller.getPassword()==null && tbSeller.getPassword().equals("")){
            throw new RuntimeException("密码不能为空");
        }
        //设置未审核状态
        tbSeller.setStatus("0");
        tbSeller.setCreateTime(new Date());
        tbSellerMapper.insert(tbSeller);
    }

    @Override
    public void updateStatus(String sellerId, String status) {

        TbSeller seller=new TbSeller();
        seller.setSellerId(sellerId);
        seller.setStatus(status);
        tbSellerMapper.updateByPrimaryKeySelective(seller);
    }

    @Override
    public List<TbSeller> search(TbSeller tbSeller) {
        TbSellerExample example=new TbSellerExample();
        TbSellerExample.Criteria criteria = example.createCriteria();
        if(tbSeller!=null){
            //模糊查询公司名称
            if(tbSeller.getNickName()!=null){
                criteria.andNickNameLike("%"+tbSeller.getNickName()+"%");
            }
            if(tbSeller.getName()!=null){
                criteria.andNameLike("%"+tbSeller.getName()+"%");
            }
        }
        return tbSellerMapper.selectByExample(example);
    }

    @Override
    public boolean updateNewPassword(String newPassword, String sellerId) {

        TbSeller tbSeller=new TbSeller();
        tbSeller.setSellerId(sellerId);
        tbSeller.setPassword(newPassword);
        int i = tbSellerMapper.updateByPrimaryKeySelective(tbSeller);
        return i>0;
    }

    @Override
    public Map showSellerTime(String sellerId) {

        TbSeller tbSeller = tbSellerMapper.selectByPrimaryKey(sellerId);
        Map map=new HashMap();

        map.put("time",tbSeller.getLogoPic());
        return map;
    }

    @Override
    public void setLoginTime(String sellerId) {

        TbSeller seller=new TbSeller();

        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(new Date());
        seller.setLogoPic(time);
        seller.setSellerId(sellerId);
        tbSellerMapper.updateByPrimaryKeySelective(seller);
    }

    @Override
    public void updateSeller(TbSeller seller) {

        tbSellerMapper.updateByPrimaryKeySelective(seller);
    }
}
