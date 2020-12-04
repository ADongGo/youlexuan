app.controller('goodsController',function ($scope,$controller,goodsService) {

    //继承
    $controller('baseController',{$scope:$scope});
    //查询对象
    $scope.searchEntity={auditStatus:0};
    //分页加载商品列表
    $scope.reloadList=function () {
        $scope.getItemCatList();
        var url="/goods/getGoodsList.do?pageNum="+$scope.paginationConf.currentPage+"&pageSize="+$scope.paginationConf.itemsPerPage;
        goodsService.getGoodsList(url,$scope.searchEntity).success(function (result) {
            $scope.list=result.rows;
            $scope.paginationConf.totalItems=result.total;
        })
    };
    //状态显示
    $scope.goodsStatus=['未审核','通过','驳回'];
    //categoryName=[];商品分类名称数组
    $scope.categoryName=[];
    
    $scope.getItemCatList=function () {

        goodsService.getItemCatList().success(function (result) {
            for(var i=0;i<result.length;i++){
                $scope.categoryName[result[i].id]=result[i].name;
            }
        })
    };

    //批量审核
    $scope.updateGoodsStatus=function (status) {
        if($scope.selectIds.length==0){
            alert("请选择要审核的商品");
        }else{
            goodsService.updateGoodsStatus($scope.selectIds,status).success(function (result) {
                if(result.success){
                    alert(result.message);
                    $scope.reloadList();
                    //清一下数组
                    $scope.selectIds=[];
                }else{
                    alert(result.message);
                    $scope.selectIds=[];
                }
            })
        }
    };

    //批量删除
    $scope.deleteGoods=function () {
        if($scope.selectIds.length==0){
            alert("请选择要删除的商品");
        }else{
            goodsService.deleteGoods($scope.selectIds).success(function (result) {
                if(result.success){
                    alert(result.message);
                    $scope.reloadList();
                    $scope.selectIds=[];
                }else{
                    alert(result.message);
                    $scope.selectIds=[];
                }
            })
        }
    };
    //查看详情
    $scope.findGoods=function (goods) {
        $scope.goods=goods;
        $scope.getBrandName(goods.brandId);
    };
    //查询品牌名称
    $scope.getBrandName=function (id) {
        goodsService.getBrandName(id).success(function (result) {
            $scope.goods.brandName=result;
        })
    }
});