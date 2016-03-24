/**
 * Created by stupidkid on 2016/3/23.
 */

Demoapp.controller("modifyPhoneCtrl", function ($rootScope, $scope, $http, Util, $state, SERVER, VERSION, $timeout, $q) {
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
                } else {
                    //跳转到登录页面
                    $rootScope.nextPage = "modifyPhone";
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

    var identityingCode;

    $scope.getIdentityingCode = function() {
        var defer = $q.defer();
        var parameters = {
            phone: $scope.phone,
        }
        //验证手机格式是否正确
        if($scope.phone != null && $scope.phone.match(/^\d{11}$/)) {
            //连接服务器
            var url = SERVER.url.url + "/OrderingSystem" + "/userCenter/getIdentityingCode";
            var jsonObject = angular.toJson(parameters);
            $http.post(url, jsonObject)
                .success(function (result) {
                    defer.resolve(result);

                    //保存验证码
                    identityingCode = result.identityingCode;
                    //提示用户已发送短信
                    alert("短信发送成功,请查看短信获取验证码");

                    console.log("successUrl:[" + url + "]" + " returnData：" + JSON.stringify(result));
                    console.log("sendData: " + JSON.stringify(jsonObject));
                })
                .error(function (err) {
                    defer.reject(err);

                    console.log("errorUrl:[" + url + "]" + JSON.stringify(err));
                    console.log("sendData: " + JSON.stringify(jsonObject));
                });
        } else {
            alert("请输入正确的手机号码");
        }
    }

    $scope.modifyPhone = function() {
        if($scope.identityingCode != null && $scope.identityingCode == identityingCode) {
            var parameters = {
                phone: $scope.phone,
                userId : $scope.user.id
            }
            var defer = $q.defer();
            //连接服务器
            var url = SERVER.url.url + "/OrderingSystem" + "/userCenter/modifyPhone";
            var jsonObject = angular.toJson(parameters);
            $http.post(url, jsonObject)
                .success(function (result) {
                    defer.resolve(result);

                    if(result.success) {
                        $rootScope.user = result.user;
                        $state.go("modifyphoneSuccess");
                    } else {
                        alert(result.msg);
                    }

                    console.log("successUrl:[" + url + "]" + " returnData：" + JSON.stringify(result));
                    console.log("sendData: " + JSON.stringify(jsonObject));
                })
                .error(function (err) {
                    defer.reject(err);

                    console.log("errorUrl:[" + url + "]" + JSON.stringify(err));
                    console.log("sendData: " + JSON.stringify(jsonObject));
                });
        } else {
            alert("验证码错误");
        }
    }
});