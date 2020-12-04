package com.offcn.vo.resp;

import com.offcn.pojo.TbSpecification;
import com.offcn.pojo.TbSpecificationOption;
import jdk.nashorn.internal.runtime.Specialization;

import java.io.Serializable;
import java.util.List;

public class SpecVo implements Serializable {

    //规格和规格选项的一个组合实体类
    private TbSpecification tbSpecification;

    private List<TbSpecificationOption> optionList;

    public TbSpecification getTbSpecification() {
        return tbSpecification;
    }

    public void setTbSpecification(TbSpecification tbSpecification) {
        this.tbSpecification = tbSpecification;
    }

    public List<TbSpecificationOption> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<TbSpecificationOption> optionList) {
        this.optionList = optionList;
    }
}
