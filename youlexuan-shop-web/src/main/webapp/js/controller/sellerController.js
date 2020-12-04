app.controller('sellerController',function ($scope,sellerService) {

    /*//继承baseController
    $controller('baseController',{$scope:$scope});*/

    //注册对象
    $scope.entity={};

    //密码对象
    $scope.rawPassword="";
    $scope.newPassword="";
    $scope.againPassword="";

    //注册
    $scope.register=function () {

        sellerService.register($scope.entity).success(function (result) {

            if(result.success){
                alert(result.message);
                location.href="shoplogin.html";
                //清一下
                $scope.entity={};
            }else{
                alert(result.message);
            }
        })
    };
    //验证密码
    $scope.checkRawPassword=function () {

        //验证原密码是否正确
        sellerService.checkRawPassword($scope.rawPassword).success(function (bol) {

            if (bol == 'true') {
                //输入原密码正确

                if($scope.newPassword != "" && $scope.againPassword != ""){
                    if ($scope.newPassword == $scope.againPassword) {

                        sellerService.updateNewPassword($scope.newPassword).success(function (bool) {
                            if (bool == 'true') {
                                alert("修改密码成功!");
                                //注销登录
                                parent.location.href = "/logout";
                            } else {
                                alert("修改密码失败!");
                            }
                        })
                    }else{
                        //新密码与确认密码输入不一致
                        alert("新密码与确认密码输入不一致!");
                    }
                }else{
                    alert("新密码和确认密码不能为空!");
                }

            } else {
                alert("原密码输入有误,请重新输入!");
            }

        })
    };
    //返回index页面
    $scope.goIndex=function () {

        parent.location.href="index.html";
    };

    //修改seller资料
    $scope.updateSeller=function () {
        sellerService.updateSeller($scope.entity).success(function (result) {

            if(result.success){
                alert(result.message);
                parent.location.href="index.html";
            }else{
                alert(result.message);
            }
        })
    };
    //修改回显
    $scope.getSeller=function () {

        sellerService.getSeller().success(function (seller) {

            $scope.entity=seller;
            alert($scope.entity.nickName);
        })
    }
});