app.controller('goodsController',function ($scope,goodsService,$controller) {

    //继承
    $controller('baseController',{$scope:$scope});
    //商品组合实体类
    $scope.entity={goods: {isEnableSpec:0}, goodsDesc: {itemImages:[],specificationItems:[]}, skuList: []};

    //通过parentId获取分类
    $scope.getItemCat=function () {
        //category1Id
        goodsService.getItemCat(0).success(function (list) {
            $scope.category1List=list;
        })
    };

    //监听category1Id
    $scope.$watch('entity.goods.category1Id',function (newValue,oldValue) {

        if(newValue){

            goodsService.getItemCat(newValue).success(function (list) {
                $scope.category2List=list;
            })
        }
    });

    //监听category2Id
    $scope.$watch('entity.goods.category2Id',function (newValue,oldValue) {

        if(newValue){
            goodsService.getItemCat(newValue).success(function (list) {
                $scope.category3List=list;
            })
        }
    });

    //监听category3Id,设置模板id
    $scope.$watch('entity.goods.category3Id',function (newValue,oldValue) {

        if(newValue){
            goodsService.getOne(newValue).success(function (result) {
                $scope.entity.goods.typeTemplateId=result.typeId;
            })
        }
    });

    //监听模板id,设置品牌列表
    $scope.$watch('entity.goods.typeTemplateId',function (newValue,oldValue) {

        if(newValue){
            goodsService.getType(newValue).success(function (result) {
                $scope.typeTemplate=result;
                $scope.typeTemplate.brandIds=JSON.parse($scope.typeTemplate.brandIds);
                //设置扩展属性
                $scope.entity.goodsDesc.customAttributeItems=JSON.parse($scope.typeTemplate.customAttributeItems);
                //获取规格选项
                $scope.getSpecAndOptionList(newValue);
            })
        }
    });

    //保存商品对象
    $scope.save=function () {
        //提取富文本编辑器中的内容赋值给entity.goodsDesc.introduction(商品介绍)
        //editor.html('');清空编辑器中的内容
        $scope.entity.goodsDesc.introduction=editor.html();
        if($scope.entity.goods.typeTemplateId==null){
            alert("商品模板不能为空");
            return;
        }
        goodsService.add($scope.entity).success(function (result) {
            if(result.success){
                alert(result.message);
                //清json对象
                $scope.entity={goods: {isEnableSpec:0}, goodsDesc: {itemImages:[],specificationItems:[]}, skuList: []};
                //清富文本编辑器
                editor.html("");
            }
        })
    };

    //图片json对象
    $scope.image_entity={color:'',url:''};
    
    //弹出框上传按钮调用该方法
    $scope.uploadFile=function () {
        goodsService.uploadFile().success(function (result) {
            //将message赋值给url
            $scope.image_entity.url=result.message;
        })
    };
    
    //弹出框保存按钮调用该方法
    $scope.addItemImages=function () {

        $scope.entity.goodsDesc.itemImages.push($scope.image_entity);
    };
    //删除goodsDesc中的itemsImage
    $scope.deleteImage=function (index) {
        $scope.entity.goodsDesc.itemImages.splice(index,1);
    };

    //根据模板id获取规格和规格选项
    $scope.getSpecAndOptionList=function (id) {

        goodsService.getSpecAndOptionList(id).success(function (result) {
            $scope.specList=result;
        })
    };
    // [{"attributeName":"网络制式","attributeValue":["移动3G","移动4G"]},{"attributeName":"屏幕尺寸","attributeValue":["5.5寸","5寸"]}]
    //根据规格名称从entity.goodsDesc.specificationItems 查询json对象
    $scope.searchJsonFromSpecificationItems=function (specName) {
      for(var i=0;i<$scope.entity.goodsDesc.specificationItems.length;i++){
          var obj=$scope.entity.goodsDesc.specificationItems[i];
          if(obj.attributeName==specName){
              return obj;
          }
      }
      return null;
    };

    //勾选或取消勾选规格选项
    $scope.checkOptionName=function ($event,specName,optionName) {

        var obj=$scope.searchJsonFromSpecificationItems(specName);
        //勾选
        if($event.target.checked){
            if(obj!=null){
                obj.attributeValue.push(optionName);
            }else{
                var newObj={attributeName:specName,attributeValue:[optionName]};
                $scope.entity.goodsDesc.specificationItems.push(newObj);
            }
        //取消勾选
        }else{
            obj.attributeValue.splice(obj.attributeValue.indexOf(optionName),1);
            //判断value数组长度是否为0,为0则删除规格json对象
            if(obj.attributeValue.length==0){
                var idx=$scope.entity.goodsDesc.specificationItems.indexOf(obj);
                $scope.entity.goodsDesc.specificationItems.splice(idx,1);
            }
        }
    };

    //动态生成sku列表
    $scope.createSkuList=function () {

        $scope.entity.skuList=[{price:'',num:'',is_default:'',spec:{}}];
        var items=$scope.entity.goodsDesc.specificationItems;
        for(var i=0;i<items.length;i++){
            $scope.entity.skuList=$scope.addColumn($scope.entity.skuList,items[i].attributeName,items[i].attributeValue);
        }
    };
    $scope.addColumn=function (skuList,attributeName,attributeValue) {

        var newSkuList=[];
        for(var i=0;i<skuList.length;i++){
            var oldSku=skuList[i];

            for(var j=0;j<attributeValue.length;j++){
                //根据oldSku克隆一个新对象
                var newSku=JSON.parse(JSON.stringify(oldSku));
                newSku.spec[attributeName]=attributeValue[j];
                newSkuList.push(newSku);
            }
        }
        return newSkuList;
    };
    //查询对象
    $scope.searchEntity={};
    //分页显示商家后台的商品列表
    $scope.reloadList=function () {
        //显示分类名称
        $scope.getItemCatList();
        var url="/goods/getGoodsListBySellerId.do?pageNum="+$scope.paginationConf.currentPage+"&pageSize="+$scope.paginationConf.itemsPerPage;
        goodsService.reloadList(url,$scope.searchEntity).success(function (result) {
            $scope.goodsList=result.rows;
            $scope.paginationConf.totalItems=result.total;
        })
    };
    //categoryName=[];商品分类名称数组
    $scope.categoryName=[];

    $scope.getItemCatList=function () {

        goodsService.getItemCatList().success(function (result) {
            for(var i=0;i<result.length;i++){
                $scope.categoryName[result[i].id]=result[i].name;
            }
        })
    };
    //审核状态对象和上架状态对象
    $scope.goodsStatus=['未审核','通过','驳回'];
    $scope.isMarket=['下架','上架'];

    //上架和下架商品
    $scope.updateMarket=function (isId,goods) {
        if(goods.auditStatus==1){
            goodsService.updateMarket(goods.id,isId).success(function (result) {
                if(result.success){
                    alert(result.message);
                    $scope.reloadList();
                }
            })
        }
        if(goods.auditStatus==0){
            alert("商品未审核不能上架");
        }
        if(goods.auditStatus==2){
            alert("商品被驳回不能上架");
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
});