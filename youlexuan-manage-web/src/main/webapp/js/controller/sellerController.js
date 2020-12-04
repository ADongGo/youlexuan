app.controller('sellerController',function ($scope,sellerService,$controller) {
    
    //继承
    $controller('baseController',{$scope:$scope});

    //显示所有的列表
    $scope.getAllSellers=function(){
        var entity=$scope.searchEntity;
        sellerService.search(entity).success(function (result) {
            $scope.allList=result;
        })
    };

    //显示未审核列表
    $scope.reloadList=function () {

        var url="/seller/sellerList.do?pageNum="+$scope.paginationConf.currentPage+"&pageSize="+$scope.paginationConf.itemsPerPage;
        var entity=$scope.entity;
        sellerService.sellerList(url,entity).success(function (result) {

              $scope.list=result.rows;
              $scope.paginationConf.totalItems=result.total;
        })
    };
    //seller对象
    $scope.entity={};
    //查询对象
    $scope.searchEntity={nickName:'',name:''};
    //审核商家
    $scope.updateSeller=function (sellerId,status) {
        sellerService.updateStatus(sellerId,status).success(function (result) {

            if(result.success){
                alert(result.message);
                $scope.reloadList();
            }else{
                alert(result.message);
            }
        })
    };

    //查看seller详情
    $scope.sellerDetail=function (seller) {
        $scope.entity=seller;
    }
});