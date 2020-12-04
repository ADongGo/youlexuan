app.controller('itemCatController',function ($scope,$http,itemCatService) {

    //当前表格中数据的上级分类id
    $scope.parentId=0;

    $scope.entity = {name:'',typeId:'',parentId:$scope.parentId};

    $scope.tempalteName = [];

    $scope.allTemplate = function(){
        $http.get("/itemCat/allTemplate.do").success(function (resp) {
            $scope.templateList = resp;//所有模板
            for(var i = 0 ; i < $scope.templateList.length ; i++){
                var idx = $scope.templateList[i].id;
                var value = $scope.templateList[i].name;
                $scope.tempalteName[idx]=value;
            }
        });
    };

    //记录当前商品分类列表的级别
    $scope.grade=1;
    //级别更新
    $scope.updateGrade=function(newGrade){
        $scope.grade=newGrade;
    };
    //面包屑分类:entity1.name  entity2.name
    $scope.entity1={name:''};
    $scope.entity2={name:''};
    //查询时调用selectList方法
    $scope.selectList=function(itemCat){

        //更新parentId
        $scope.parentId=itemCat.id;

        //itemCat赋值给面包屑对象
        if($scope.grade == 1){
            $scope.entity1={name:''};
            $scope.entity2={name:''};
        }
        if($scope.grade == 2){
            $scope.entity1 = itemCat;
            $scope.entity2 = {name:''};
        }
        if($scope.grade == 3){
            $scope.entity2 = itemCat;
        }
        $scope.reloadList(itemCat.id);
    };

    //分页控件参数
    $scope.paginationConf={
        currentPage:1, //当前查询第几页
        totalItems:29,//总记录数
        itemsPerPage:5,//默认每页显示5条
        perPageOptions:[2,5,10,15,20],
        onChange:function () {
            $scope.reloadList($scope.parentId);
            $scope.allTemplate();
        }
    };

    //用来记录所勾选的行的主键
    $scope.selectIds=[];
    //勾选或者取消勾选时调用方法
    $scope.updateSelectIds=function ($event,id) {
        if($event.target.checked){
            $scope.selectIds.push(id);
        }else{
            //先找到索引,然后删除
            var index=$scope.selectIds.indexOf(id);
            $scope.selectIds.splice(index,1);
        }
    };
    //分页加载顶级数据

    $scope.reloadList=function (id) {

        var url="/itemCat/search.do?pageNum="+$scope.paginationConf.currentPage+"&pageSize="+$scope.paginationConf.itemsPerPage+"&parentId="+id;
        itemCatService.search(url).success(function (result) {
            $scope.paginationConf.totalItems=result.total;
            $scope.list=result.rows;
        })
    };

    //编辑回显
    $scope.getOne=function(id){

        itemCatService.findOne(id).success(function (result) {
            $scope.entity=result;
        })
    };
    
    $scope.save=function () {

        if($scope.entity.id!=null){
            //修改
            itemCatService.update($scope.entity).success(function (result) {

                if(result.success){
                    alert(result.message);
                    $scope.reloadList($scope.parentId);
                    $scope.entity = {name:'',typeId:'',parentId:$scope.parentId};
                }else{
                    alert(result.message);
                }
            })
        }else{
            //保存
            itemCatService.add($scope.entity).success(function (result) {

                if(result.success){
                    alert(result.message);
                    $scope.reloadList($scope.parentId);
                    $scope.entity = {name:'',typeId:'',parentId:$scope.parentId};
                }else{
                    alert(result.message);
                }
            })
        }
    };

    //批量删除
    $scope.deleteIds=function () {

        itemCatService.deleteByIds($scope.selectIds).success(function (result) {

            if(result.success){
                alert(result.message);
                $scope.reloadList($scope.parentId);
                $scope.selectIds=[];
            }
        })
    }
});