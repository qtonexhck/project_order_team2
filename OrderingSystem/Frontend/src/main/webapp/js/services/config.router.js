'use strict';


/**
 * 配置路由
 * @sherlock221b
 */
Demoapp.config(
      function ($stateProvider, $urlRouterProvider, VERSION) {
          $stateProvider
              .state('login', {
                  url : '/login',
                  templateUrl : 'html/login.html?v=' + VERSION.vs,
                  controller : "loginCtrl"
              })
              .state('order', {
                  url : '/order',
                  templateUrl : 'html/order.html?v=' + VERSION.vs,
                  controller : 'orderCtrl'
              })
              .state('pay', {
                  url : '/pay',
                  templateUrl : 'html/pay.html?v=' + VERSION.vs,
                  controller : 'payCtrl'
              })
              .state('createOrder', {
                  url : '/createOrder',
                  templateUrl : 'html/createOrder.html?v=' + VERSION.vs,
                  controller : 'createOrderCtrl'
              })
              .state('modifyOrder', {
                  url : '/modifyOrder',
                  templateUrl : 'html/modifyOrder.html?v=' + VERSION.vs,
                  controller : 'modifyOrderCtrl'
              })
              .state('unbind', {
                  url : '/unbind',
                  templateUrl : 'html/unbind.html?v=' + VERSION.vs,
                  controller : 'unbindCtrl'
              })
              .state('unbindSuccess', {
                  url : '/unbindSuccess',
                  templateUrl : 'html/unbindSuccess.html?v=' + VERSION.vs,
                  controller : 'unbindSuccessCtrl'
              })
              .state('modifyPhone', {
                  url : '/modifyPhone',
                  templateUrl : 'html/modifyPhone.html?v=' + VERSION.vs,
                  controller : 'modifyPhoneCtrl'
              })
              .state('modifyphoneSuccess', {
                  url : '/modifyphoneSuccess',
                  templateUrl : 'html/modifyphoneSuccess.html?v=' + VERSION.vs,
                  controller : 'modifyphoneSuccessCtrl'
              })
              .state('queryLog', {
                  url : '/queryLog',
                  templateUrl : 'html/queryLog.html?v=' + VERSION.vs,
                  controller : 'queryLogCtrl'
              })
              .state('recharge', {
                  url : '/recharge',
                  templateUrl : 'html/recharge.html?v=' + VERSION.vs,
                  controller : 'rechargeCtrl'
              })
              .state('rechargeSuccess', {
                  url : '/rechargeSuccess',
                  templateUrl : 'html/rechargeSuccess.html?v=' + VERSION.vs,
                  controller : 'rechargeSuccessCtrl'
              })

          $urlRouterProvider.otherwise('/unbind');

      }
  );


