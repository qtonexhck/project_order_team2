/**
 * @desc 全局ctrl
 * @auth sherlock221b
 */
Demoapp.controller("MainCtrl", function ($rootScope, $scope,$http, Util, $state, SERVER, VERSION, $timeout,$q) {
    SERVER.url = SERVER.login;

    $('#myTab a').click(function (e) {
        e.preventDefault()
        $(this).tab('show')

    })
    $(function () {
        $('#myTab li:eq(0) a').tab('show');
    });

    $('#myTab2 a').click(function (e) {
        e.preventDefault()
        $(this).tab('show')

    })
    $(function () {
        $('#myTab2 li:eq(0) a').tab('show');
    });

    $scope.checkprice=function(){
        var a=document.getElementById("aprice").value;
        var b=document.getElementById("bprice").value;
        var c=document.getElementById("cprice").value;
        var re1 = /^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/;
        if(!re1.test(a)){
            document.getElementById("aprice").value=6;
            alert("价格格式不对");
        }else if(!re1.test(b)){
                document.getElementById("bprice").value=6;
                alert("价格格式不对");
        }else{
            if(!re1.test(c)){
                document.getElementById("cprice").value=6;
                alert("价格格式不对");
            }
        }
    }
    //$scope.islogin= function () {
    //    var defer = $q.defer();
    //    //连接服务器;
    //    var url=SERVER.url.url+"/OrderingSystem" + "/Manager/islogin";
    //    var json={}
    //    var jd=angular.toJson(json);
    //    $http.post(url,jd)
    //        .success(function(result){
    //            defer.resolve(result);
    //            if(result.account==null || result.account ==""){
    //                $state.go("login");
    //            }else{
    //                $rootScope.account=result.account;
    //            }
    //            console.log("successUrl:["+url+"]"+" returnData："+JSON.stringify(result));
    //            console.log("sendData: "+JSON.stringify(jd));
    //        })
    //        .error(function(err){
    //            defer.reject(err);
    //            console.log("errorUrl:["+url+"]"+JSON.stringify(err));
    //            console.log("sendData: "+JSON.stringify(jd));
    //        });
    //}
    //$scope.islogin();
    //if($rootScope.account==null || $rootScope.account ==""){
    //    $state.go("login");
    //}

    //go state
    $scope.menu={
        time:"",
        A:"",
        Aprice:'6',
        B:"",
        Bprice:'6',
        C:"",
        Cprice:'6',
    };
    $scope.init=function(){
        var defer = $q.defer();
        //连接服务器;
        var url=SERVER.url.url+"/OrderingSystem" + "/Manager/menu";
        var jsonmenu={}
        var jd=angular.toJson(jsonmenu);
        $http.post(url,jd)
            .success(function(result){
                defer.resolve(result);
                $scope.menuAll=result.menu;
                console.log("successUrl:["+url+"]"+" returnData："+JSON.stringify(result));
                console.log("sendData: "+JSON.stringify(jd));
            })
            .error(function(err){
                defer.reject(err);
                console.log("errorUrl:["+url+"]"+JSON.stringify(err));
                console.log("sendData: "+JSON.stringify(jd));
            });
    };
    $scope.init();

    $scope.remove=function(obj){
        var defer = $q.defer();
        //连接服务器;
        var url=SERVER.url.url+"/OrderingSystem" + "/Manager/remove";
        var menuDate={
            menuDate:obj,
        }
        var jd=angular.toJson(menuDate);
        if(confirm("确认删除菜谱")){
            $http.post(url,jd)
                .success(function(result){
                    defer.resolve(result);
                    $scope.init();
                    console.log("successUrl:["+url+"]"+" returnData："+JSON.stringify(result));
                    console.log("sendData: "+JSON.stringify(jd));
                })
                .error(function(err){
                    defer.reject(err);
                    console.log("errorUrl:["+url+"]"+JSON.stringify(err));
                    console.log("sendData: "+JSON.stringify(jd));
                });
        }

    };

    $scope.menuLoad=function(){
        var defer = $q.defer();
        //连接服务器;
        var url=SERVER.url.url+"/OrderingSystem" + "/Manager/menuLoad";
        var menuLoad={
            menuDate:$scope.menu.time,
            menuA:$scope.menu.A,
            menuB:$scope.menu.B,
            menuC:$scope.menu.C,
            Aprice:$scope.menu.Aprice,
            Bprice:$scope.menu.Bprice,
            Cprice:$scope.menu.Cprice
        }
        var jd=angular.toJson(menuLoad);
        if(confirm("确认提交菜谱")){
            $http.post(url,jd)
                .success(function(result){
                    defer.resolve(result);


                    $scope.init();
                    console.log("successUrl:["+url+"]"+" returnData："+JSON.stringify(result));
                    console.log("sendData: "+JSON.stringify(jd));
                })
                .error(function(err){
                    defer.reject(err);
                    console.log("errorUrl:["+url+"]"+JSON.stringify(err));
                    console.log("sendData: "+JSON.stringify(jd));
                });
        }

    };

    $scope.checkAll=function(){
        var defer = $q.defer();
        //连接服务器;
        var url=SERVER.url.url+"/OrderingSystem" + "/Manager/checkAll";
        var check={
            querytime:$scope.querytime,
            userid:$scope.userid,
        }
        var jd=angular.toJson(check);
        $http.post(url,jd)
            .success(function(result){
                defer.resolve(result);
                for(var i=0;i<result.ordering.length;i++)
                {
                    var userid=result.ordering[i].userId;
                    for(var j=0;j<result.user.length;j++){
                        if(userid==result.user[j].id){
                            result.ordering[i].id=result.user[j].username;
                        }
                    }
                }

                for(var i=0;i<result.receiving.length;i++)
                {
                    var userid=result.receiving[i].userId;
                    for(var j=0;j<result.user.length;j++){
                        if(userid==result.user[j].id){
                            result.receiving[i].id=result.user[j].username;
                        }
                    }
                }

                for(var i=0;i<result.rece.length;i++)
                {
                    var userid=result.rece[i].userId;
                    for(var j=0;j<result.user.length;j++){
                        if(userid==result.user[j].id){
                            result.rece[i].id=result.user[j].username;
                        }
                    }
                    if(result.rece[i].isError==1)
                    {
                        result.rece[i].isError="冒领"
                    }else{
                        result.rece[i].isError="漏领"
                    }
                }
                $scope.receiving=result.receiving;
                $scope.rece=result.rece;
                $scope.orderAll=result.ordering;
                console.log("successUrl:["+url+"]"+" returnData："+JSON.stringify(result));
                console.log("sendData: "+JSON.stringify(jd));
            })
            .error(function(err){
                defer.reject(err);
                console.log("errorUrl:["+url+"]"+JSON.stringify(err));
                console.log("sendData: "+JSON.stringify(jd));
            });
    }

    $scope.AllNumber=function(){
        var nowdate=document.getElementById("nowdate").value;
        if(nowdate==null||nowdate==""){
            var date=new Date();
            if(date.getMonth()<10){
                date=(date.getYear()+1900)+"-0"+(date.getMonth()+1)+"-"+date.getDate();
            }else{
                date=(date.getYear()+1900)+"-"+(date.getMonth()+1)+"-"+date.getDate();
            }
            nowdate=document.getElementById("nowdate").value =date;
        }
        $scope.today=nowdate;
        var defer = $q.defer();
        //连接服务器;
        var url=SERVER.url.url+"/OrderingSystem" + "/Manager/AllNumber";
        var today={
            today: $scope.today,

        }

        var jd=angular.toJson(today);
        $http.post(url,jd)
            .success(function(result){
                defer.resolve(result);

                $scope.result=result;
                console.log("successUrl:["+url+"]"+" returnData："+JSON.stringify(result));
                console.log("sendData: "+JSON.stringify(jd));
            })
            .error(function(err){
                defer.reject(err);
                console.log("errorUrl:["+url+"]"+JSON.stringify(err));
                console.log("sendData: "+JSON.stringify(jd));
            });
    }
    $scope.loginOut=function(){
        $state.go("login");
    }
});

