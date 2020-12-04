app.controller('loginController',function ($scope,$http) {

    $scope.showName=function(){
        $http.get("/showName.do").success(function (result) {

            $scope.name=result.name;
        })
    }

});