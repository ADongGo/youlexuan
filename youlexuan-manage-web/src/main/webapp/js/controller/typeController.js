app.controller('typeController',function ($scope,typeService,$controller) {

    //继承baseController
    $controller('baseController',{$scope:$scope});

    //查询对象
    $scope.searchEntity={name:""};

    //添加和修改时的查询对象
    $scope.entity={name:"",specIds:[],brandIds:[],customAttributeItems:[]};

    //json格式字符串返回一个json对象
    $scope.returnJson=function(str){
        var json="";
        var arr=[];
        var list=JSON.parse(str);
        for(var i=0;i<list.length;i++){
            arr.push(list[i].text);
        }
        return arr.toString();
    };
    
    //分页查询
    $scope.reloadList=function () {

        var url="/type/search.do?pageNum="+$scope.paginationConf.currentPage+"&pageSize="+$scope.paginationConf.itemsPerPage;
        var entity=$scope.searchEntity;
        typeService.search(url,entity).success(function (result) {

            $scope.paginationConf.totalItems=result.total;
            $scope.list=result.rows;
        })
    };

    //编辑回显一个模板对象
    $scope.getOne=function (id) {
        
        typeService.findOne(id).success(function (result) {

            $scope.entity=result;
            //重新赋值
            $scope.entity.brandIds=JSON.parse($scope.entity.brandIds);
            $scope.entity.specIds=JSON.parse($scope.entity.specIds);
            $scope.entity.customAttributeItems=JSON.parse($scope.entity.customAttributeItems);
        })
    };

    //添加data属性
    $scope.brandList={data:[]};
    $scope.specList={data:[]};

    //扩展属性添加option的方法
    $scope.addOption=function(){

        $scope.entity.customAttributeItems.push({});
    };

    //扩展属性删除option
    $scope.deleteOption=function(index){

        $scope.entity.customAttributeItems.splice(index,1);
    };
    
    //获取所有的品牌和规格list的map
    $scope.getBrandAndSpec=function () {

        typeService.getBrandAndSpecList().success(function (result) {

            $scope.brandList.data=result.brandMapList;
            $scope.specList.data=result.specMapList;
        })
    };

    $scope.save=function () {
        //修改
        if($scope.entity.id!=null){

            typeService.update($scope.entity).success(function (result) {

                if(result.success){
                    //加载分页
                    $scope.reloadList();
                    //清一下
                    $scope.entity={name:"",specIds:[],brandIds:[],customAttributeItems:[]};
                }else{
                    alert(result.message);
                }
            })
        }else{
            //添加
            alert("11");
            typeService.add($scope.entity).success(function (result) {

                if(result.success){
                    //加载分页
                    $scope.reloadList();
                    //清一下
                    $scope.entity={name:"",specIds:[],brandIds:[],customAttributeItems:[]};
                }else{
                    alert(result.message);
                }
            })
        }
    };

    //批量删除
    $scope.deleteIds=function () {
        alert("..");
        if($scope.selectIds.length==0){
            alert("请选择要删除的模板");
        }else{
            typeService.deleteByIds($scope.selectIds).success(function (result) {

                alert(result.message);
                $scope.reloadList();
                $scope.selectIds=[];
            })
        }

    }
});