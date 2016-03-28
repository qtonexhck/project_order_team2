Demoapp.controller("LoginCtrl", function ($rootScope, $scope,$http, Util, $state, SERVER, VERSION, $timeout,$q) {

    $scope.manager={
        account:"",
        password:"",
        saveme:true
    };

    $scope.onLogin=function(){
        if($scope.manager.account != null && $scope.manager.password !== null) {
            var jsonObjec={
                account:$scope.manager.account,
                password:$scope.manager.password,
                saveme:$scope.manager.saveme
            };
            }
        var defer = $q.defer();
        //连接服务器
        var url=SERVER.url.url+"/OrderingSystem" + "/Manager/Managerlogin";
        var jd=angular.toJson(jsonObjec);
        $http.post(url,jd)
            .success(function(result){
                defer.resolve(result);
                if(result.msg == "登录成功") {
                    $rootScope.account = result.account;
                    //跳转到主页页面
                    $state.go('main');
                } else {
                    alert("用户不存在");
                }
                console.log("successUrl:["+url+"]"+" returnData："+JSON.stringify(result));
                console.log("sendData: "+JSON.stringify(jd));
            })
            .error(function(err){
                defer.reject(err);
                console.log("errorUrl:["+url+"]"+JSON.stringify(err));
                console.log("sendData: "+JSON.stringify(jd));
            });

    }

});