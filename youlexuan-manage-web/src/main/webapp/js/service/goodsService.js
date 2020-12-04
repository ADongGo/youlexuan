app.service('goodsService',function ($http) {

    //分页加载商品列表
    this.getGoodsList=function (url,entity) {
        return $http.post(url,entity);
    };
    //商品名称数组
    this.getItemCatList=function () {
        return $http.get("/goods/getItemCatList.do");
    };
    //修改商品状态
    this.updateGoodsStatus=function (ids,status) {
        return $http.post("/goods/updateGoodsStatus.do?ids="+ids+"&status="+status);
    };
    //批量删除
    this.deleteGoods=function (ids) {
        return $http.post("/goods/deleteGoods.do?ids="+ids);
    };
    //获取品牌名称
    this.getBrandName=function (id) {
        return $http.get("/goods/getBrandName.do?id="+id);
    }
});