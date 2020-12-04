package com.offcn.goods.service;

import com.offcn.pojo.TbTypeTemplate;
import com.offcn.vo.resp.PageResult;

import java.util.List;
import java.util.Map;

public interface TypeService {

    /**
     * 获取所有的模板列表
     * @return
     */
    public List<TbTypeTemplate> typeList();

    /**
     * 分页获取所有模板
     * @param pageNum
     * @param pageSize
     * @param tbTypeTemplate
     * @return
     */
    public PageResult search(int pageNum,int pageSize,TbTypeTemplate tbTypeTemplate);

    /**
     * 通过id找到模块对象
     * @param id
     * @return
     */
    public TbTypeTemplate findOne(Long id);

    /**
     * 修改
     * @param tbTypeTemplate
     */
    public void update(TbTypeTemplate tbTypeTemplate);

    /**
     * 添加
     * @param tbTypeTemplate
     */
    public void add(TbTypeTemplate tbTypeTemplate);

    /**
     * 通过id删除
     * @param id
     */
    public void delete(Long id);

    /**
     * 批量删除
     * @param ids
     */
    public void deleteByIds(Long[] ids);

    /**
     * 获取所有的brand和spec list集合的map集合
     * @return
     */
    public Map getBrandAndSpecList();

    /**
     * 根据模板id获取规格和规格选项
     * @param templateId
     * @return
     */
    public List<Map> getSpecAndOptionList(Long templateId);
}
