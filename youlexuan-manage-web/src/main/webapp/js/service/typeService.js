app.service('typeService',function ($http) {

    //分页查询
    this.search=function (url,entity) {

        return $http.post(url,entity);
    }

    //编辑回显一个对象
    this.findOne=function (id) {

        return $http.post("/type/findOne.do?id="+id);
    }

    //获取所有品牌和规格
    this.getBrandAndSpecList=function () {

        return $http.get("/type/getBrandAndSpec.do");
    }

    //修改
    this.update=function (entity) {

        return $http.post("/type/update.do",entity);
    }

    //添加
    this.add=function (entity) {

        return $http.post("/type/add.do",entity);
    }

    //删除
    this.deleteByIds=function (ids) {

        return $http.post("/type/deleteByIds.do?ids="+ids);
    }
});