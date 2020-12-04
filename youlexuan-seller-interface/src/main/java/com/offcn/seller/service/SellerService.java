package com.offcn.seller.service;

import com.offcn.pojo.TbSeller;
import com.offcn.vo.resp.PageResult;

import java.util.List;
import java.util.Map;

public interface SellerService {

    /**
     * 获取所有的买家列表
     * @return
     */
    public PageResult getSellerList(int pageNum,int pageSize,TbSeller tbSeller);

    /**
     * 通过sellerId获取TbSeller对象
     * @param sellerId
     * @return
     */
    public TbSeller getSellerBySellerId(String sellerId);

    /**
     * 注册商家用户信息
     * @param tbSeller
     */
    public void register(TbSeller tbSeller);

    /**
     * 后台审核商家信息
     * @param sellerId
     * @param status
     */
    public void updateStatus(String sellerId, String status);

    /**
     * 获取所有的seller对象List
     * @return
     */
    public List<TbSeller> search(TbSeller tbSeller);

    /**
     * 修改密码
     * @param newPassword
     * @param sellerId
     * @return
     */
    public boolean updateNewPassword(String newPassword,String sellerId);

    /**
     * 获取用户最后一次登陆时间
     * @return
     */
    public Map showSellerTime(String sellerId);

    /**
     * 设置最后登录时间
     */
    public void setLoginTime(String sellerId);

    /**
     * 修改seller信息
     * @param seller
     */
    public void updateSeller(TbSeller seller);
}
