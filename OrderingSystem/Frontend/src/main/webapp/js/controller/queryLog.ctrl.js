/**
 * Created by stupidkid on 2016/3/23.
 */
Demoapp.controller("queryLogCtrl", function ($rootScope, $scope, $http, Util, $state, SERVER, VERSION, $timeout, $q) {
    function GetQueryString() {
        var args = new Object();
        var query = window.location.hash.substr(window.location.hash.indexOf("?") + 1, window.location.hash.length);
        var pairs = query.split("&");
        for(var i = 0; i < pairs.length; ++i) {
            var pos=pairs[i].indexOf('=');//查找name=value
            if(pos==-1){//如果没有找到就跳过
                continue;
            }
            var argname=pairs[i].substring(0,pos);//提取name
            var value=pairs[i].substring(pos+1);//提取value
            args[argname]=value;//存为属性
        }
        console.log(args);
        return args;
    }
    if($rootScope.weChat == null) {
        var code = GetQueryString().code;
        var parameters = {
            code : code
        }
        var defer = $q.defer();
        var url = SERVER.url.url + "/OrderingSystem" + "/userCenter/getWeChat";
        var jsonObject = angular.toJson(parameters);
        $http.post(url, jsonObject)
            .success(function (result) {
                defer.resolve(result);

                $rootScope.weChat = result.weChat;
                if(result.user != null) {
                    $rootScope.user = result.user;
                    $scope.user = $rootScope.user;
                    QueryLog();
                } else {
                    //跳转到登录页面
                    $rootScope.nextPage = "queryLog";
                    $state.go('login');
                }

                console.log("successUrl:[" + url + "]" + " returnData：" + JSON.stringify(result));
                console.log("sendData: " + JSON.stringify(jsonObject));
            })
            .error(function (err) {
                defer.reject(err);

                console.log("errorUrl:[" + url + "]" + JSON.stringify(err));
                console.log("sendData: " + JSON.stringify(jsonObject));
            });
    }

    function QueryLog() {
        var defer = $q.defer();
        var parameters = {
            id: $scope.user.id
        }
        var url = SERVER.url.url + "/OrderingSystem" + "/LogCenter/queryLog";
        var jsonObject = angular.toJson(parameters);
        $http.post(url, jsonObject)
            .success(function (result) {
                defer.resolve(result);

                $scope.recharges = result.recharge;
                $scope.costs = result.ordering;
                $scope.yearmonth = result.yearmonth;

                console.log("successUrl:[" + url + "]" + " returnData：" + JSON.stringify(result));
                console.log("sendData: " + JSON.stringify(jsonObject));
            })
            .error(function (err) {
                defer.reject(err);

                console.log("errorUrl:[" + url + "]" + JSON.stringify(err));
                console.log("sendData: " + JSON.stringify(jsonObject));
            });
    }
});