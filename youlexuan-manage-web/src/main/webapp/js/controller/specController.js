app.controller('specController',function ($scope,specService,$controller) {

    //继承baseController
    $controller('baseController',{$scope:$scope});

    //查询对象
    $scope.searchEntity={"specName":""};


    //分页展示
    $scope.reloadList=function () {

        var url="/spec/search.do?pageNum="+$scope.paginationConf.currentPage+"&pageSize="+$scope.paginationConf.itemsPerPage;
        var entity=$scope.searchEntity;
        specService.search(url,entity).success(function (result) {

            $scope.paginationConf.totalItems=result.total;
            $scope.list=result.rows;
        })
    };

    $scope.entity={tbSpecification:{},optionList:[]};

    //修改回显通过id获取specOv
    $scope.getOneById=function (id) {

        specService.findOne(id).success(function (result) {
            $scope.entity=result;
        })
    };

    //新增规格选项
    $scope.addOption=function () {
        $scope.entity.optionList.push({});
    };
    //删除规格选项
    $scope.deleteOption=function (index) {
        $scope.entity.optionList.splice(index,1);
    };

    //添加或者修改
    $scope.save=function () {

        if($scope.entity.tbSpecification.id!=null){
            //修改
            specService.update($scope.entity).success(function (result) {

                if(result.success) {
                   alert(result.message);
                   //清一下
                   $scope.entity = {tbSpecification: {}, optionList: {}};
                   //加载分页
                   $scope.reloadList();
               }else{
                   alert(result.message);
               }
            })
        }else{
            //保存
            specService.add($scope.entity).success(function (result) {

                if(result.success) {
                    alert(result.message);
                    //清一下
                    $scope.entity = {tbSpecification: {}, optionList: {}};
                    //加载分页
                    $scope.reloadList();
                }else{
                    alert(result.message);
                }
            })
        }
    };

    //批量删除
    $scope.deleteIds=function () {

        specService.deleteByIds($scope.selectIds).success(function (result) {

            if(result.success){
                alert(result.message);
                //清一下selectIds
                $scope.selectIds=[];
                //加载分页
                $scope.reloadList();
            }
        })
    };
});