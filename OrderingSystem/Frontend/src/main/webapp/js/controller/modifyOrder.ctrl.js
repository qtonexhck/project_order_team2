/**
 * Created by HZK on 2016/3/22.
 */

Demoapp.controller("modifyOrderCtrl", function ($rootScope, $scope, $http, Util, $state, SERVER, VERSION, $timeout, $q) {
    $scope.user = $rootScope.user;
    $scope.ordering = $rootScope.ordering;

    $scope.menu = {
        A : "",
        B : "",
        C : ""
    }
    var price = {
        A : 0,
        B : 0,
        C : 0,
        selected : 0
    }

    var defer = $q.defer();
    var url = SERVER.url.url + "/OrderingSystem" + "/menu/getMenu";
    $http.post(url)
        .success(function (result) {
            defer.resolve(result);

            $scope.menu.A = result.menuA.menu + "(￥" + result.menuA.price + ")";
            price.A = result.menuA.price;
            $scope.menu.B = result.menuB.menu + "(￥" + result.menuB.price + ")";
            price.B = result.menuB.price;
            $scope.menu.C = result.menuC.menu + "(￥" + result.menuC.price + ")";
            price.C = result.menuC.price;

            console.log("successUrl:[" + url + "]" + " returnData：" + JSON.stringify(result));
            //console.log("sendData: " + JSON.stringify(jsonObject));
        })
        .error(function (err) {
            defer.reject(err);

            console.log("errorUrl:[" + url + "]" + JSON.stringify(err));
            console.log("sendData: " + JSON.stringify(jsonObject));
        });

    $scope.modifyOrder = function() {
        if($scope.ordering.menuType == "A") {
            price.selected = price.A;
        } else if($scope.ordering.menuType == "B") {
            price.selected = price.B;
        } else {
            price.selected = price.C;
        }
        var parameters = {
            orderingId : $scope.ordering.id,
            menuType : $scope.ordering.menuType,
            location : $scope.ordering.location,
            price : price.selected
        }
        var defer = $q.defer();
        var url = SERVER.url.url + "/OrderingSystem" + "/ordering/modifyOrder";
        var jsonObject = angular.toJson(parameters);
        $http.post(url, jsonObject)
            .success(function (result) {
                defer.resolve(result);
                $rootScope.ordering = result.ordering;
                //跳转到生成订单页面
                $state.go('createOrder');

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