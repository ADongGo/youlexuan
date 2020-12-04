package com.offcn.goods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.offcn.goods.service.SpecifictionServcie;
import com.offcn.mapper.TbSpecificationMapper;
import com.offcn.mapper.TbSpecificationOptionMapper;
import com.offcn.pojo.TbSpecification;
import com.offcn.pojo.TbSpecificationExample;
import com.offcn.pojo.TbSpecificationOption;
import com.offcn.pojo.TbSpecificationOptionExample;
import com.offcn.vo.resp.PageResult;
import com.offcn.vo.resp.SpecVo;
import jdk.nashorn.internal.runtime.Specialization;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Iterator;
import java.util.List;

@Service
public class SpecifictionServiceImpl implements SpecifictionServcie {

    @Autowired
    private TbSpecificationMapper tbSpecificationMapper;
    @Autowired
    private TbSpecificationOptionMapper tbSpecificationOptionMapper;

    @Override
    public List<TbSpecification> specList() {

        return tbSpecificationMapper.selectByExample(null);
    }

    @Override
    public PageResult search(int pageNum, int pageSize, TbSpecification tbSpecification) {

        PageHelper.startPage(pageNum,pageSize);

        TbSpecificationExample tbSpecificationExample=new TbSpecificationExample();
        TbSpecificationExample.Criteria criteria = tbSpecificationExample.createCriteria();
        if(tbSpecification!=null && tbSpecification.getSpecName()!=null && tbSpecification.getSpecName().length()>0){
            criteria.andSpecNameLike("%"+tbSpecification.getSpecName()+"%");
        }
        PageResult pageResult=new PageResult();
        Page<TbSpecification> page=(Page<TbSpecification>)tbSpecificationMapper.selectByExample(tbSpecificationExample);
        pageResult.setTotal(page.getTotal());
        pageResult.setRows(page.getResult());
        return pageResult;
    }

    @Override
    public SpecVo findOne(Long specId) {

        SpecVo specVo=new SpecVo();
        TbSpecification tbSpecification = tbSpecificationMapper.selectByPrimaryKey(specId);
        specVo.setTbSpecification(tbSpecification);

        TbSpecificationOptionExample tbSpecificationOptionExample=new TbSpecificationOptionExample();
        TbSpecificationOptionExample.Criteria criteria = tbSpecificationOptionExample.createCriteria();
        criteria.andSpecIdEqualTo(specId);
        List<TbSpecificationOption> optionList = tbSpecificationOptionMapper.selectByExample(tbSpecificationOptionExample);
        specVo.setOptionList(optionList);

        return specVo;
    }

    @Override
    public void add(SpecVo specVo) {

        TbSpecification tbSpecification = specVo.getTbSpecification();
        tbSpecificationMapper.insert(tbSpecification);
        Long specId=tbSpecification.getId();
        for(TbSpecificationOption option:specVo.getOptionList()){
            option.setSpecId(specId);
            tbSpecificationOptionMapper.insert(option);
        }
    }

    @Override
    public void update(SpecVo specVo) {

        tbSpecificationMapper.updateByPrimaryKey(specVo.getTbSpecification());

        TbSpecificationOptionExample example = new TbSpecificationOptionExample();
        TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
        Long specId=specVo.getTbSpecification().getId();
        criteria.andSpecIdEqualTo(specId);
        tbSpecificationOptionMapper.deleteByExample(example);

        for(TbSpecificationOption option:specVo.getOptionList()){
            option.setSpecId(specId);
            tbSpecificationOptionMapper.insert(option);
        }
    }

    @Override
    public void delete(Long specId) {

        TbSpecificationOptionExample example = new TbSpecificationOptionExample();
        TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
        criteria.andSpecIdEqualTo(specId);
        tbSpecificationOptionMapper.deleteByExample(example);

        tbSpecificationMapper.deleteByPrimaryKey(specId);
    }

    @Override
    public void deleteByIds(Long[] ids) {

        for(Long id :ids){
            delete(id);
        }
    }
}
