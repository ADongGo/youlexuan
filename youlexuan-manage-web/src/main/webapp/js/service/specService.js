app.service('specService',function ($http) {
    
    //分页
    this.search=function (url,entity) {

        return $http.post(url,entity);
    }
    
    //回显
    this.findOne=function (id) {

        return $http.post("/spec/findOne.do?id="+id);
    }

    //修改
    this.update=function (entity) {

        return $http.post("/spec/update.do",entity);
    }

    //添加
    this.add=function (entity) {

        return $http.post("/spec/add.do",entity);
    }

    //批量删除
    this.deleteByIds=function (ids) {

        return $http.post("/spec/deleteByIds.do?ids="+ids);
    }
});