app.service('goodsService',function ($http) {

    //通过parentId获取分类
    this.getItemCat=function (id) {
        return $http.post("/goods/getItemCatByParentId.do?parentId="+id);
    };

    //通过parentId获取三级分类的对象
    this.getOne=function (id) {
      return $http.post("/goods/getOneItemCat.do?id="+id) ;
    };

    //通过主键id获取模板对象
    this.getType=function (id) {
        return $http.post("/goods/getType.do?id="+id);
    };
    
    //上传图片
    this.uploadFile=function () {
        //文件上传(spring)
        var format=new FormData();
        //参数名:type=file的id 参数值:去除的第一个文件
        format.append("file",file.files[0]);

        return $http({
            url:'/fileUpload.do',
            method:'post',
            data:format,
            headers:{'Content-Type':undefined},
            //表单对象序列化
            transformRequest:angular.identity
        });
    };

    //获取规格和规格选项
    this.getSpecAndOptionList=function (id) {
        return $http.post("/goods/getSpecAndOptionList.do?templateId="+id);
    };
    
    //保存
    this.add=function (entity) {
      return $http.post("/goods/add.do",entity);
    };

    //分页显示商家后台的的商品列表
    this.reloadList=function (url,entity) {
        return $http.post(url,entity);
    };

    //商品名称数组
    this.getItemCatList=function () {
        return $http.get("/goods/getItemCatListSeller.do");
    };

    //上架和下架商品
    this.updateMarket=function (id,isId) {
        return $http.post("/goods/updateMarket.do?id="+id+"&isId="+isId);
    };

    //删除商品
    this.deleteGoods=function (ids) {
        return $http.post("/goods/deleteGoods.do?ids="+ids);
    }
});