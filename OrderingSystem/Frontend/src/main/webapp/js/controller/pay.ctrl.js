/**
 * Created by HZK on 2016/3/22.
 */

Demoapp.controller("payCtrl", function ($rootScope, $scope, $http, Util, $state, SERVER, VERSION, $timeout, $q) {
    $scope.user = $rootScope.user;
    $scope.ordering = $rootScope.ordering;

    $scope.payWay = "动力加支付";

    $scope.paying = function() {
        var parameters = {
            orderingId : $scope.ordering.id,
            userId : $scope.user.id
        }
        var defer = $q.defer();
        var url = SERVER.url.url + "/OrderingSystem" + "/ordering/pay";
        var jsonObject = angular.toJson(parameters);
        $http.post(url, jsonObject)
            .success(function (result) {
                $rootScope.user = result.user;
                $rootScope.ordering = result.ordering;
                //跳转到生成订单页面
                $state.go('createOrder');
                defer.resolve(result);

                console.log("successUrl:[" + url + "]" + " returnData：" + JSON.stringify(result));
                console.log("sendData: " + JSON.stringify(jsonObject));
            })
            .error(function (err) {
                defer.reject(err);

                console.log("successUrl:[" + url + "]" + " returnData：" + JSON.stringify(result));
                console.log("sendData: " + JSON.stringify(jsonObject));
            });
    }

    $scope.cancelPaying = function() {
        var parameters = {
            orderingId: $scope.ordering.id
        }
        var defer = $q.defer();
        var url = SERVER.url.url + "/OrderingSystem" + "/ordering/cancelPay";
        var jsonObject = angular.toJson(parameters);
        $http.post(url, jsonObject)
            .success(function (result) {
                defer.resolve(result);

                $rootScope.ordering = {
                    id : 0,
                    userId : 0,
                    orderTime : "",
                    menuType : "",
                    location : "",
                    price : "",
                    isTaken : ""
                }
                //跳转到点餐页面
                $state.go('order');

                console.log("successUrl:[" + url + "]" + " returnData：" + JSON.stringify(result));
                console.log("sendData: " + JSON.stringify(jsonObject));
            })
            .error(function (err) {
                defer.reject(err);

                console.log("successUrl:[" + url + "]" + " returnData：" + JSON.stringify(result));
                console.log("sendData: " + JSON.stringify(jsonObject));
            });
    }
});
