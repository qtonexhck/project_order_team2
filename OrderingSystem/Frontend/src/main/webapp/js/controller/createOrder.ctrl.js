/**
 * Created by HZK on 2016/3/22.
 */

Demoapp.controller("createOrderCtrl", function ($rootScope, $scope, $http, Util, $state, SERVER, VERSION, $timeout, $q) {
    $scope.user = $rootScope.user;
    $scope.ordering = $rootScope.ordering;

    $('#typeImage').attr("src", "images/menu_" + $rootScope.ordering.menuType +".png");

    var parameters = {
        menu_type : $scope.ordering.menuType
    }
    var defer = $q.defer();
    var url = SERVER.url.url + "/OrderingSystem" + "/menu/getMenuContent";
    var jsonObject = angular.toJson(parameters);
    $http.post(url, jsonObject)
        .success(function (result) {
            defer.resolve(result);
            $scope.menu = result.menu.menu;

            console.log("successUrl:[" + url + "]" + " returnData：" + JSON.stringify(result));
            console.log("sendData: " + JSON.stringify(jsonObject));
        })
        .error(function (err) {
            defer.reject(err);

            console.log("errorUrl:[" + url + "]" + JSON.stringify(err));
            console.log("sendData: " + JSON.stringify(jsonObject));
        });

    $scope.modifyOrder = function() {
        //跳转到修改订单页面
        $state.go("modifyOrder");
    }
});
