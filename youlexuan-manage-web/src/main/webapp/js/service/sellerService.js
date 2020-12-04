app.service('sellerService',function ($http) {

    //展示买家列表用户信息
    this.sellerList=function (url,entity) {
        return $http.post(url,entity);
    };

    //审核商家
    this.updateStatus=function (sellerId,status) {
      return $http.post("/seller/updateStatus.do?sellerId="+sellerId+"&status="+status);
    };

    //所有商家
    this.search=function (entity) {
        return $http.post("/seller/search.do",entity);
    };
});