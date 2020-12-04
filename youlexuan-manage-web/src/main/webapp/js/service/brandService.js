//定义一个service
app.service('brandService',function ($http) {

    this.brandList=function () {
        return $http.get("/brand/list.do");
    }
    this.update=function (entity) {
        return $http.post("/brand/update.do",entity);
    }
    this.add=function (entity) {
        return $http.post("/brand/add.do",entity);
    }
    this.getOne=function (id) {
        return $http.get("/brand/findOne.do?id="+id);
    }
    this.deleteIds=function (selectIds) {
        return $http.get("/brand/deleteByIds.do?ids="+selectIds);
    }
    this.search=function (url,entity) {
        return $http.post(url,entity);
    }
});