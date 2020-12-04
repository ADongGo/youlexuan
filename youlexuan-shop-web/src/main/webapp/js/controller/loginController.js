app.controller('loginController',function ($scope,$http) {

    //设置登录名
    $scope.showSellerName=function(){
        $http.post("/showSellerName.do").success(function (map) {

            $scope.name=map.name;
        })
    };
    //设置最后登录时间
    $scope.showSellerTime=function(){
        $http.post("/showSellerTime.do").success(function (map) {

            $scope.time=map.time;
        })
    };
    //设置登录时间
    $scope.setLoginTime=function () {

        $http.post("/setLoginTime.do");
    }
});