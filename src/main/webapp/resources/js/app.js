// Main application modules
var showyourtraceApp = angular.module('showyourtraceApp', ['ui.router', 'ui.bootstrap', 'restangular', 'ngRoute', 'ngAnimate',
                            'ngCookies', 'angularFileUpload', 'LocalStorageModule', 'directive.loading',
    'showyourtrace.configuration',
    'showyourtraceApp.authenticate',
    'showyourtraceApp.services',

    'showyourtraceApp.search'
]);

showyourtraceApp.filter('unsafe', ['$sce', function ($sce) {
    return function (val) {
        return $sce.trustAsHtml(val);
    };
}]);

showyourtraceApp.config(function ($httpProvider, RestangularProvider, $stateProvider, $urlRouterProvider, $locationProvider, $parseProvider, $sceProvider, authenticateProvider, BASE_PATH, IS_AUTH, API_END_POINT) {
    $sceProvider.enabled(false);
    RestangularProvider.setBaseUrl(BASE_PATH + API_END_POINT);
    //$parseProvider.unwrapPromises(true);

    if (!$httpProvider.defaults.headers.get) {
        $httpProvider.defaults.headers.get = {};
    }
    $httpProvider.defaults.headers.get['If-Modified-Since'] = 'Sat, 01 Jan 2000 00:00:00 GMT';
    $httpProvider.defaults.headers.get['Cache-Control'] = 'no-cache';
    $httpProvider.defaults.headers.get['Pragma'] = 'no-cache';

    authenticateProvider.setConfig({
        unauthorizedPage: 'public.error',
        targetPage: 'app.search',
        loginPage: IS_AUTH ? 'public.login' : 'public.402'
    });

    $stateProvider.state('public', {
        abstract: true,
        template: "<ui-view/>"
    })
    .state('public.login', {
        url: '/login',
        templateUrl: BASE_PATH + '/page/common/login.html',
        controller: 'LoginCtrl'
    })
    .state('app', {
        abstract: true,
        controller: 'MainPageController',
        url: '/',
        templateUrl: BASE_PATH + '/page/public/main.html'
    })
    .state('public.confirmResult', {
        controller: 'ConfirmResultController',
        url: '/confirm/{confirmId}',
        templateUrl: BASE_PATH + '/page/public/confirmResult.html'
    });

    // FIX for trailing slashes. Gracefully "borrowed" from https://github.com/angular-ui/ui-router/issues/50
    $urlRouterProvider.rule(function($injector, $location) {
        if($location.protocol() === 'file')
            return;

        var path = $location.path()
        // Note: misnomer. This returns a query object, not a search string
            , search = $location.search()
            , params
            ;

        // check to see if the path already ends in '/'
        if (path[path.length - 1] === '/') {
            return;
        }

        // If there was no search string / query params, return with a `/`
        if (Object.keys(search).length === 0) {
            return path + '/';
        }

        // Otherwise build the search string and return a `/?` prefix
        params = [];
        angular.forEach(search, function(v, k){
            params.push(k + '=' + v);
        });
        return path + '/?' + params.join('&');
    });

    $httpProvider.interceptors.push(function ($q, $rootScope, $location, IS_AUTH) {
            var incApi = function(url) {
                if (!angular.isDefined($rootScope.apiResolve)) {
                    $rootScope.apiResolve = 0;
                }
                if (url != null && url.indexOf('/api/') > -1) {
                    $rootScope.apiResolve++;
                }
            };
            var decApi = function(url) {
                if (!angular.isDefined($rootScope.apiResolve)) {
                    $rootScope.apiResolve = 0;
                } else {
                    if (url != null && url.indexOf('/api/') > -1) {
                        $rootScope.apiResolve--;
                    }
                }
            };

            return {
                'responseError': function(rejection) {
                    decApi(rejection.config.url);

                    var status = rejection.status;
                    var config = rejection.config;
                    var method = config.method;
                    var url = config.url;

                    if (status == 401 || status == 403) {
                        $location.path(IS_AUTH ? '/login' : '402');  // Redirection to login page
                    } else {
                        console.error("Response " + method + " on " + url + " failed with status " + status);
                    }
                    return $q.reject(rejection);
                },
                'requestError': function(rejection) {
                    decApi(rejection.config.url);

                    var status = rejection.status;
                    var config = rejection.config;
                    var method = config.method;
                    var url = config.url;
                    console.error("Request " + method + " on " + url + " failed with status " + status);
                    return $q.reject(rejection);
                },
                'response': function(response) {
                    decApi(response.config.url);
                    return response;
                },
                'request': function(config) {
                    incApi(config.url);
                    return config;
                }
            };
        }
    );

});


showyourtraceApp.run(function ($rootScope, $state, $stateParams, $http, authenticate, CHECK_LOGIN_URL, CHECK_LOGIN_METHOD, BASE_PATH, IS_AUTH) {
    $rootScope.apiResolve = 0;
    $rootScope.doingResolve = true;
    $rootScope.$state = $state;
    $rootScope.$stateParams = $stateParams;
    $rootScope.user = null;

    var checkPromise = $http({
        url: BASE_PATH + CHECK_LOGIN_URL,
        method: CHECK_LOGIN_METHOD
    });

    authenticate.check(checkPromise).then(function(){
        $rootScope.user = authenticate.getUser();
        if($state.current.name == 'public.login') {
            $state.go(authenticate.targetPage);
        }

        $rootScope.doingResolve = false;
        $state.go(authenticate.targetPage);

    }, function(error) {
        //$rootScope.user = { role: accessLevels.ANONYMOUS };
        $rootScope.doingResolve = false;
    });

    var resolveDone = function () {
        $rootScope.doingResolve = false;
    };
    $rootScope.$on('$stateChangeSuccess', resolveDone);
    $rootScope.$on('$stateChangeError', resolveDone);
    $rootScope.$on('$statePermissionError', resolveDone);

    $rootScope.$on("$stateChangeStart", function (event, toState, toParams, fromState, fromParams) {
        $rootScope.doingResolve = true;
        if(angular.isDefined(toState.access)) {
            if (!authenticate.authorize(toState.access)) {
                event.preventDefault();
                if (authenticate.isLoggedIn()) {
                    $state.go(authenticate.targetPage);
                    $rootScope.doingResolve = false;
                } else {
                    $state.go(authenticate.loginPage);
                    $rootScope.doingResolve = false;
                }
            }
        } else {
            if(toState.name == 'public.login' && authenticate.isLoggedIn()) {
                $state.go(authenticate.targetPage);
            }
        }
    });
});

showyourtraceApp.controller('LogoutCtrl', function($rootScope, $scope, $state, $http, authenticate, LOGOUT_URL, LOGOUT_METHOD, BASE_PATH) {
    $scope.logoutMe = function () {
        var logoutPromise = $http({
            url: BASE_PATH + LOGOUT_URL,
            method: LOGOUT_METHOD
        });
        authenticate.logout(logoutPromise);
        //$rootScope.user = {role:accessLevels.ANONYMOUS};
        $rootScope.user = null;
        $state.go(authenticate.targetPage);
    };
});


showyourtraceApp.controller('LoginCtrl', function ($rootScope, $scope, $modalInstance, $state, $http, $timeout, authenticate, mode,
                                                 $modal, $modalStack, $cookies, appServices, LOGIN_URL, LOGIN_METHOD, API_END_POINT, BASE_PATH) {

    $scope.working = false;

    $scope.isError = false;
    $scope.errMsg = '';
    $scope.user = {};

    $scope.mode = mode;

    $scope.subscribeState = {
        email: '',
        sent : false
    };

    $modalStack.dismissAll();

    $scope.loginMe = function () {
        // setup promise, and 'working' flag
        var loginPromise = $http({
            url: BASE_PATH + LOGIN_URL,
            method: LOGIN_METHOD,
            data: $scope.user
        });
        //var loginPromise = $http.post('/login', $scope.login);
        $scope.working = true;
        $scope.isError = false;
        authenticate.login(loginPromise);

        loginPromise.success(function () {
            $rootScope.user = authenticate.getUser();
            $modalInstance.close();
        });

        loginPromise.error(function (error) {
            //$scope.wrongCredentials = true;
            $scope.isError = true;
            $scope.errMsg = error.applicationMessage;
            $timeout(function () { $scope.isError = false; }, 6000);
        });
        loginPromise["finally"](function () {
            $scope.working = false;
        });
        return true;
    };

    $scope.registerMe = function() {
        if($scope.user.pwd != $scope.user.repeatPwd) {
            $scope.isError = true;
            $scope.errMsg = 'Passwords did not match';
            $timeout(function () { $scope.errMsg = ''; $scope.isError = false }, 6000);
            return;
        }
        $scope.working = true;

        appServices.registerUser($scope.user.login, $scope.user.pwd, $scope.user.email).then(function(result) {
            $rootScope.user = result;
            $modalInstance.close();
        }, function(error) {
            $scope.working = false;
            $scope.isError = true;
            $scope.errMsg = error.data.applicationMessage;
            $timeout(function () { $scope.errMsg = ''; $scope.isError = false }, 6000);
        });
    };

    $scope.subscribe = function() {
        $scope.working = true;
        $scope.wrongCredentials = false;

        $cookies.put('couponweb_subscribe_email', $scope.subscribeState.email);

        $http.post( BASE_PATH + API_END_POINT + "subscribe", $scope.subscribeState.email )
        .success(function() {
            $scope.subscribeState.sent = true;
            $scope.mode = 'SUBSCRIBED';
        }).error(function(error) {
            $scope.isError = true;
            $scope.errMsg = error.applicationMessage;
            $timeout(function () { $scope.errMsg = ''; $scope.isError = false }, 6000);
        }).finally(function() {
            $scope.working = false;
        });
    };

    $scope.switchToLogin = function() {
        $scope.mode = 'LOGIN';
    };

    $scope.switchToRegister = function() {
        $scope.mode = 'REGISTER';
    };

    $scope.ok = function() {
        switch($scope.mode) {
            case 'LOGIN':
                $scope.loginMe();
                break;
            case 'REGISTER':
                $scope.registerMe();
                break;
        }

    };

    $scope.cancel = function() {
        $modalInstance.dismiss('cancel');
    };
});


showyourtraceApp.controller('MainPageController', MainPageController);
showyourtraceApp.controller('ConfirmResultController', ConfirmResultController);


//angular.module("template/popover/popover.html", []).run(["$templateCache", function ($templateCache) {
//    $templateCache.put("template/popover/popover.html",
//        "anyHtml");
//}]);

