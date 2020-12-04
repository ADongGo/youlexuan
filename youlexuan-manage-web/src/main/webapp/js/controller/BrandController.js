//在该模块下定义一个控制器
app.controller('brandController',function ($scope,brandService,$controller) {

    //当前控制器中,我们可以定义一些方法以及变量等.这个内容保存在$scope对象中

    //在brandController中继承baseController
    //通多controller对象完成控制器之间的继承
    $controller('baseController',{$scope:$scope});

    $scope.count=2;

    //查询brand
    $scope.getBrandList=function () {

        brandService.brandList().success(function (result) {
            if(result.success){
                $scope.list=result.data;
            }
        });
    };
    //弹出框数据的json对象
    $scope.entity={"name":"","firstChar":""};
    //添加
    $scope.save=function () {
        //判断本地调用save方法是添加还是修改
        if($scope.entity.id!=null){
            //修改
            brandService.update($scope.entity).success(function (result) {
                if(result.success){
                    $scope.reloadList();
                    //清空
                    $scope.entity={"name":"","firstChar":""};
                }else{
                    alert("修改失败");
                }
            });
        }else{
            //保存
            brandService.add($scope.entity).success(function (result) {
                if(result.success){
                    $scope.reloadList();
                    //清空
                    $scope.entity={"name":"","firstChar":""};
                }else{
                    alert("保存失败");
                }
            });
        }
    };


    //编辑回显
    $scope.getBrandById=function (id) {
        brandService.getOne(id).success(function (result) {
            if(result.success){
                $scope.entity=result.data;
            }else{
                alert("查询失败");
            }
        });
    };

    //点击删除按钮调用方法
    $scope.deleteByIds=function () {
        if($scope.selectIds.length==0){
            alert("请选择要删除的商品");
        }else{
            brandService.deleteIds($scope.selectIds).success(function (result) {
                if(result.success){
                    alert(result.message);
                    $scope.reloadList();
                    //清空数组
                    $scope.selectIds=[];
                }else{
                    alert(result.message);
                }
            });
        }

    };

    $scope.searchEntity={"name":"","firstChar":""};

    $scope.reloadList=function () {

        var url = "/brand/search.do?pageNum="+$scope.paginationConf.currentPage+"&pageSize="+$scope.paginationConf.itemsPerPage;
        var entity = $scope.searchEntity;

        brandService.search(url,entity).success(function (result) {
            $scope.paginationConf.totalItems=result.total;
            $scope.list=result.rows;
        })
    }
});