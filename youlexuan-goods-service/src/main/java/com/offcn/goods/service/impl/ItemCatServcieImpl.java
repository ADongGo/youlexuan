package com.offcn.goods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.offcn.goods.service.ItemCatService;
import com.offcn.mapper.TbItemCatMapper;
import com.offcn.pojo.TbItemCat;
import com.offcn.pojo.TbItemCatExample;
import com.offcn.pojo.TbSellerExample;
import com.offcn.vo.resp.PageResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class ItemCatServcieImpl implements ItemCatService {

    @Autowired
    private TbItemCatMapper itemCatMapper;

    @Override
    public PageResult findItemcatByParentId(int pageNum, int pageSize, Long parentId) {

        PageHelper.startPage(pageNum,pageSize);

        TbItemCatExample itemCatExample=new TbItemCatExample();
        TbItemCatExample.Criteria criteria = itemCatExample.createCriteria();
        criteria.andParentIdEqualTo(parentId);

        Page<TbItemCat> page=(Page<TbItemCat>)itemCatMapper.selectByExample(itemCatExample);
        PageResult pageResult=new PageResult();
        pageResult.setTotal(page.getTotal());
        pageResult.setRows(page.getResult());
        return pageResult;
    }

    @Override
    public void add(TbItemCat itemCat) {

        itemCatMapper.insert(itemCat);
    }

    @Override
    public void update(TbItemCat itemCat) {

        itemCatMapper.updateByPrimaryKey(itemCat);
    }

    @Override
    public TbItemCat findOne(Long id) {

        return itemCatMapper.selectByPrimaryKey(id);
    }

    @Override
    public void delete(Long id) {

        TbItemCatExample example=new TbItemCatExample();
        TbItemCatExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(id);
        List<TbItemCat> tbItemCats = itemCatMapper.selectByExample(example);
        //判断是否有下一级
        if(tbItemCats!=null && tbItemCats.size()>0){
            itemCatMapper.deleteByExample(example);
        }
        itemCatMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteByIds(Long[] ids) {

        for(Long id :ids){
            delete(id);
        }
    }

    @Override
    public List<TbItemCat> getItemCatByParentId(Long parentId) {

        TbItemCatExample example=new TbItemCatExample();
        TbItemCatExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        return itemCatMapper.selectByExample(example);
    }

    @Override
    public List<TbItemCat> getItemCatList() {

        return itemCatMapper.selectByExample(null);
    }
}
