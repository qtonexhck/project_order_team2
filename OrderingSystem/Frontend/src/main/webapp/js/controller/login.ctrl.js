/**
 * Created by HZK on 2016/3/22.
 */

Demoapp.controller("loginCtrl", function ($rootScope, $scope, $http, Util, $state, SERVER, VERSION, $timeout, $q) {
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

    $scope.login = function() {
        if($scope.identityingCode != null && $scope.identityingCode == identityingCode) {
            var parameters = {
                phone: $scope.phone,
                weChat : $rootScope.weChat
            }
            var defer = $q.defer();
            //连接服务器
            var url = SERVER.url.url + "/OrderingSystem" + "/userCenter/login";
            var jsonObject = angular.toJson(parameters);
            $http.post(url, jsonObject)
                .success(function (result) {
                    defer.resolve(result);

                    if(result.msg == "登录成功") {
                        $rootScope.user = result.user;
                        //跳转到对应页面
                        if($rootScope.nextPage != null) {
                            $state.go($rootScope.nextPage);
                        } else {
                            $state.go('order');
                        }
                    } else {
                        alert("用户不存在");
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