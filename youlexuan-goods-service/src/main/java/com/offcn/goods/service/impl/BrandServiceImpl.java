package com.offcn.goods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.offcn.goods.service.BrandService;
import com.offcn.mapper.TbBrandMapper;
import com.offcn.pojo.TbBrand;
import com.offcn.pojo.TbBrandExample;
import com.offcn.vo.resp.PageResult;
import com.sun.tools.javac.code.Attribute;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Array;
import java.util.Arrays;
import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    TbBrandMapper tbBrandMapper;

    @Override
    public List<TbBrand> brandList() {

        return tbBrandMapper.selectByExample(new TbBrandExample());
    }

    @Override
    public List<TbBrand> brandLikeList(TbBrand tbBrand) {

        TbBrandExample tbBrandExample=new TbBrandExample();
        TbBrandExample.Criteria criteria = tbBrandExample.createCriteria();

        if(tbBrand!=null){
            if(tbBrand.getName()!=null && tbBrand.getName().length()>0){
                criteria.andNameLike("%"+tbBrand.getName()+"%");
            }
            if(tbBrand.getFirstChar()!=null && tbBrand.getFirstChar().length()>0){
                criteria.andFirstCharEqualTo(tbBrand.getFirstChar());
            }
        }
        return tbBrandMapper.selectByExample(tbBrandExample);
    }

    @Override
    public void add(TbBrand tbBrand) {

        if(tbBrand!=null){
            if(tbBrand.getName()!=null && tbBrand.getFirstChar()!=null && tbBrand.getFirstChar().length()==1){
                tbBrandMapper.insert(tbBrand);
            }
        }
    }

    @Override
    public void deleteById(Long id) {

        if(id!=null){
            tbBrandMapper.deleteByPrimaryKey(id);
        }
    }

    @Override
    public void deleteByIds(Long[] ids) {

        if(ids==null || ids.length==0){
            return;
        }
        TbBrandExample tbBrandExample=new TbBrandExample();
        TbBrandExample.Criteria criteria = tbBrandExample.createCriteria();
        criteria.andIdIn(Arrays.asList(ids));
        tbBrandMapper.deleteByExample(tbBrandExample);
    }

    @Override
    public void update(TbBrand tbBrand) {

        if(tbBrand==null || tbBrand.getId()==null){
            return;
        }

        tbBrandMapper.updateByPrimaryKey(tbBrand);
    }

    @Override
    public TbBrand getTbBrandById(Long id) {
        return tbBrandMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageResult findPage(int pageNum, int pageSize, TbBrand tbBrand) {

        PageHelper.startPage(pageNum,pageSize);

        //设置查询条件
        TbBrandExample tbBrandExample=new TbBrandExample();
        TbBrandExample.Criteria criteria = tbBrandExample.createCriteria();

        if(tbBrand!=null){
            if(tbBrand.getName()!=null && tbBrand.getName().length()>0){
                criteria.andNameLike("%"+tbBrand.getName()+"%");
            }
            if(tbBrand.getFirstChar()!=null && tbBrand.getFirstChar().length()>0){
                criteria.andFirstCharEqualTo(tbBrand.getFirstChar());
            }
        }
        Page<TbBrand> page=(Page<TbBrand>)tbBrandMapper.selectByExample(tbBrandExample);

        PageResult pageResult=new PageResult();
        pageResult.setRows(page.getResult());
        pageResult.setTotal(page.getTotal());
        return pageResult;
    }
}
