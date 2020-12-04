// 将controller中公用的代码抽取到baseController中
app.controller('baseController',function ($scope) {

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

    //分页控件参数
    $scope.paginationConf={
        currentPage:1, //当前查询第几页
        totalItems:29,//总记录数
        itemsPerPage:5,//默认每页显示5条
        perPageOptions:[2,5,10,15,20],
        onChange:function () {
            $scope.reloadList();
        }
    };
});