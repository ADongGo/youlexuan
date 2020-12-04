package com.offcn.vo.resp;

import java.util.List;

public class PageResult extends CommonResult {

    private Long total;

    private List rows;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }
}
