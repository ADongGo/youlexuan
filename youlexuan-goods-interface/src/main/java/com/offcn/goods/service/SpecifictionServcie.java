package com.offcn.goods.service;

import com.offcn.pojo.TbSpecification;
import com.offcn.vo.resp.CommonResult;
import com.offcn.vo.resp.PageResult;
import com.offcn.vo.resp.SpecVo;
import jdk.nashorn.internal.runtime.Specialization;

import java.util.List;

public interface SpecifictionServcie {

    /**
     * 获取全部规格列表
     * @return
     */
    public List<TbSpecification> specList();

    /**
     * 分页获取规格列表
     * @param pageNum
     * @param pageSize
     * @param tbSpecification
     * @return
     */
    public PageResult search(int pageNum, int pageSize, TbSpecification tbSpecification);

    /**
     * 编辑回显获取一个规格和规格选项
     * @param specId
     * @return
     */
    public SpecVo findOne(Long specId);

    /**
     * 添加规格和规格选项
     * @param specVo
     */
    public void add(SpecVo specVo);

    /**
     * 更新规格和规格选项
     * @param specVo
     */
    public void update(SpecVo specVo);

    /**
     * 通过规格id删除规格和规格选项
     * @param specId
     */
    public void delete(Long specId);

    /**
     * 批量删除规格和规格选项
     * @param ids
     */
    public void deleteByIds(Long[] ids);

}
