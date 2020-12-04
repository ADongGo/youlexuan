package com.offcn.common.enums.goods;

public enum GoodsStatusEnum {

    GOODS_DEFAULT_STATUS("0","未审核"),
    GOODS_AUDIT_PASS("1","审核通过"),
    GOODS_AUDIT_FAIL("2","驳回");

    private String status;
    private String msg;

    GoodsStatusEnum(){}

    GoodsStatusEnum(String status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
