app.service('sellerService',function ($http) {

    //注册
    this.register=function (entity) {
        return $http.post("/seller/register.do",entity);
    };
    //修改密码验证原密码
    this.checkRawPassword=function (rawPassword) {
      return $http.post("/seller/checkRawPassword.do?rawPassword="+rawPassword);
    };
    //修改新密码
    this.updateNewPassword=function (newPassword) {
      return $http.post("/seller/updateNewPassword.do?newPassword="+newPassword);
    };
    //修改seller信息
    this.updateSeller=function (entity) {
      return $http.post("/seller/updateSeller.do",entity);
    };
    //编辑回显
    this.getSeller=function () {
      return $http.post("/seller/getSeller.do");
    }
});