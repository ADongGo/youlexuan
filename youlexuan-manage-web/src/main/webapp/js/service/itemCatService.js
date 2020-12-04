app.service('itemCatService',function ($http) {

    //分页获取
    this.search=function (url) {

        return $http.post(url);
    };

    //回显
    this.findOne=function (id) {

        return $http.post("/itemCat/findOne.do?id="+id);
    };

    //修改
    this.update=function (entity) {

        return $http.post("/itemCat/update.do",entity);
    };

    //添加
    this.add=function (entity) {

        return $http.post("/itemCat/add.do",entity);
    };

    //批量删除
    this.deleteByIds=function (ids) {

        return $http.post("/itemCat/deleteByIds.do?ids="+ids);
    }
});