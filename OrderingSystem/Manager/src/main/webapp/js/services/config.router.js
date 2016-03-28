'use strict';


/**
 * 配置路由
 * @sherlock221b
 */
Demoapp.config(
      function ($stateProvider,   $urlRouterProvider,VERSION) {
          $stateProvider
              .state('login', {
                  url: '/login',
                  templateUrl: 'html/login.html?v='+VERSION.vs,
                  controller:"LoginCtrl"
              })
              .state('main', {
                  url: '/home/main',
                  templateUrl: 'html/main.html?v='+VERSION.vs,
                  controller:"MainCtrl"
              })
          $urlRouterProvider.otherwise('/login');

      }
  );


