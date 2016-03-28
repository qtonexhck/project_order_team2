/**
 * Created by HZK on 2016/3/19.
 */
Demoapp.controller("IndexCtrl", function ($rootScope, $scope,$http, Util, $state, SERVER, VERSION, $timeout,$q) {
    $rootScope.showTop = true;

    //$scope.onSubmit = function() {
    //    $scope.testInput = '123';
    //    console.log("123");
    //    //将传送到服务器上的参数以json格式封装
    //    var jsonObject={
    //        test : '123',
    //        testTest : '123456'
    //    };

        //var defer = $q.defer();
        //////连接服务器
        //var url = SERVER.url.url + "/OrderingSystem" + "/test/test";
        //var jd=formatJson(jsonObject);
        //$http.post(url, jd)
        //    .success(function(result){
        //        defer.resolve(result);
        //
        //        $state.go('index');
        //
        //        console.log("successUrl:["+url+"]"+" returnData："+JSON.stringify(result));
        //        console.log("sendData: "+JSON.stringify(jd));
        //    })
        //    .error(function(err){
        //        defer.reject(err);
        //
        //        console.log("errorUrl:["+url+"]"+JSON.stringify(err));
        //        console.log("sendData: "+JSON.stringify(jd));
        //    });
    //}

});