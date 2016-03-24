/**
 * Created by HZK on 2016/3/22.
 */

/**
 * Created by HZK on 2016/3/22.
 */

Demoapp.controller("orderCtrl", function ($rootScope, $scope, $http, Util, $state, SERVER, VERSION, $timeout, $q) {
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
                }
                if(result.ordering != null) {
                    $rootScope.ordering = result.ordering;
                }

                if(result.user == null) {
                    $state.go('login');
                } else {
                    if(result.ordering != null) {
                        if(result.ordering.isTaken == 0) {
                            $state.go('pay');
                        } else {
                            $state.go('createOrder');
                        }
                    }
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
        if($rootScope.user.id == 0) {
            $state.go('login');
        } else {
            if($rootScope.ordering.id != 0) {
                if($rootScope.ordering.isTaken == 0) {
                    $state.go('pay');
                } else {
                    $state.go('createOrder');
                }
            }
        }
    }

    $scope.menu = {
        A : "",
        B : "",
        C : ""
    };
    var price = {
        A : 0,
        B : 0,
        C : 0,
        selected : 0
    };
    $scope.date = new Date(new Date().getTime() + 8*60*60*1000);

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

    $scope.checkout = function() {
        if($scope.location == null || $scope.menu_type == null) {
            alert("请重新确认地点或者套餐种类");
        } else {
            if($scope.menu_type == "A") {
                price.selected = price.A;
            } else if($scope.menu_type == "B") {
                price.selected = price.B;
            } else {
                price.selected = price.C;
            }
            var parameters = {
                userId : $rootScope.user.id,
                location: $scope.location,
                menu_type: $scope.menu_type,
                price : price.selected
            }
            var defer = $q.defer();
            var url = SERVER.url.url + "/OrderingSystem" + "/ordering/order";
            var jsonObject = angular.toJson(parameters);
            $http.post(url, jsonObject)
                .success(function (result) {
                    defer.resolve(result);
                    //跳转到支付页面
                    $rootScope.ordering = result.ordering;
                    $state.go("pay");

                    console.log("successUrl:[" + url + "]" + " returnData：" + JSON.stringify(result));
                    console.log("sendData: " + JSON.stringify(jsonObject));
                })
                .error(function (err) {
                    defer.reject(err);

                    console.log("errorUrl:[" + url + "]" + JSON.stringify(err));
                    console.log("sendData: " + JSON.stringify(jsonObject));
                });
        }
    }
});