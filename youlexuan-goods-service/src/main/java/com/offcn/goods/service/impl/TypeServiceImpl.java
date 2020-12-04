package com.offcn.goods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.offcn.goods.service.TypeService;
import com.offcn.mapper.TbBrandMapper;
import com.offcn.mapper.TbSpecificationMapper;
import com.offcn.mapper.TbSpecificationOptionMapper;
import com.offcn.mapper.TbTypeTemplateMapper;
import com.offcn.pojo.*;
import com.offcn.vo.resp.PageResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TbTypeTemplateMapper typeTemplateMapper;
    @Autowired
    private TbBrandMapper brandMapper;
    @Autowired
    private TbSpecificationMapper specificationMapper;
    @Autowired
    private TbSpecificationOptionMapper specificationOptionMapper;

    @Override
    public List<TbTypeTemplate> typeList() {

        return typeTemplateMapper.selectByExample(null);
    }

    @Override
    public PageResult search(int pageNum, int pageSize, TbTypeTemplate tbTypeTemplate) {

        PageHelper.startPage(pageNum,pageSize);

        TbTypeTemplateExample typeTemplateExample=new TbTypeTemplateExample();
        TbTypeTemplateExample.Criteria criteria = typeTemplateExample.createCriteria();
        //模糊查询
        if(tbTypeTemplate.getName()!=null && tbTypeTemplate.getName().length()>0){
            criteria.andNameLike("%"+tbTypeTemplate.getName()+"%");
        }
        Page<TbTypeTemplate> page = (Page<TbTypeTemplate>) typeTemplateMapper.selectByExample(typeTemplateExample);
        PageResult pageResult=new PageResult();
        pageResult.setRows(page.getResult());
        pageResult.setTotal(page.getTotal());
        return pageResult;
    }

    @Override
    public TbTypeTemplate findOne(Long id) {

        return typeTemplateMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(TbTypeTemplate tbTypeTemplate) {

        typeTemplateMapper.updateByPrimaryKey(tbTypeTemplate);
    }

    @Override
    public void add(TbTypeTemplate tbTypeTemplate) {

        typeTemplateMapper.insert(tbTypeTemplate);
    }

    @Override
    public void delete(Long id) {

        typeTemplateMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteByIds(Long[] ids) {

        for(Long id :ids){
            delete(id);
        }
    }

    @Override
    public Map getBrandAndSpecList() {

        List<TbBrand> brandList=brandMapper.selectByExample(null);
        List<Map> brandMapList= new ArrayList<>();
        for(TbBrand brand:brandList){

            Map map=new HashMap();
            map.put("id",brand.getId());
            map.put("text",brand.getName());
            brandMapList.add(map);
        }

        List<TbSpecification> specList=specificationMapper.selectByExample(null);
        List<Map> specMapList=new ArrayList<>();
        for(TbSpecification specification:specList){

            Map map=new HashMap();
            map.put("id",specification.getId());
            map.put("text",specification.getSpecName());
            specMapList.add(map);
        }
        Map result=new HashMap();
        result.put("brandMapList",brandMapList);
        result.put("specMapList",specMapList);
        return result;
    }

    @Override
    public List<Map> getSpecAndOptionList(Long templateId) {

        //先根据模板id查询到模板
        TbTypeTemplate template = findOne(templateId);
        String specIds=template.getSpecIds();
        List<Map> list = JSON.parseArray(specIds, Map.class);

        for (Map map:list){
            Long id=Long.parseLong(map.get("id").toString());
            //查询规格选项
            TbSpecificationOptionExample example=new TbSpecificationOptionExample();
            TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
            criteria.andSpecIdEqualTo(id);
            List<TbSpecificationOption> options = specificationOptionMapper.selectByExample(example);
            map.put("options",options);
        }
        return list;
    }
}
