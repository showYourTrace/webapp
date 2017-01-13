// Main application modules
var showyourtraceAdmin = angular.module('showyourtraceAdmin', ['ui.router', 'ui.bootstrap', 'restangular', 'ngRoute',
                                    'angularFileUpload', 'LocalStorageModule', 'directive.loading',
    'showyourtrace.configuration',
    'com.htmlxprs.autocomplete.directives',
    'showyourtraceAdmin.deal',
    'showyourtraceAdmin.booking',
    'showyourtraceAdmin.vendor',
    'showyourtraceAdmin.user',
    'showyourtraceAdmin.mailing',
    'showyourtraceApp.authenticate',
    'showyourtraceApp.services'
]);

showyourtraceAdmin.filter('unsafe', ['$sce', function ($sce) {
    return function (val) {
        return $sce.trustAsHtml(val);
    };
}]);

showyourtraceAdmin.config(function ($httpProvider, RestangularProvider, $stateProvider, $urlRouterProvider, $locationProvider, $parseProvider, $sceProvider, authenticateProvider, BASE_PATH, IS_AUTH, API_END_POINT_ADMIN) {
    $sceProvider.enabled(false);
    RestangularProvider.setBaseUrl(BASE_PATH + API_END_POINT_ADMIN);
    //$parseProvider.unwrapPromises(true);

    if (!$httpProvider.defaults.headers.get) {
        $httpProvider.defaults.headers.get = {};
    }
    $httpProvider.defaults.headers.get['If-Modified-Since'] = 'Sat, 01 Jan 2000 00:00:00 GMT';
    $httpProvider.defaults.headers.get['Cache-Control'] = 'no-cache';
    $httpProvider.defaults.headers.get['Pragma'] = 'no-cache';

    authenticateProvider.setConfig({
        unauthorizedPage: 'adminLogin.error',
        targetPage: 'adminApp.home.deal.search',
        loginPage: IS_AUTH ? 'adminLogin.form' : 'adminLogin.402'
    });

    $stateProvider.state('adminApp', {
        abstract: true,
        templateUrl: BASE_PATH + '/page/admin/admin.html'
    });
    $stateProvider.state('adminApp.home', {
        url: '/',
        templateUrl: BASE_PATH + '/page/admin/home.html'
    });

    $stateProvider.state('adminLogin', {
        abstract: true,
        template: "<ui-view/>"
    })
    .state('adminLogin.form', {
        url: '/login',
        templateUrl: BASE_PATH + '/page/common/login.html',
        controller: 'LoginCtrl'
    })
    .state('adminLogin.402', {
        url: '/402',
        templateUrl: BASE_PATH + '/page/402.html'
    })
    .state('adminLogin.error', {
        url: '/error',
        templateUrl: BASE_PATH + '/page/error.html'
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

showyourtraceAdmin.run(function ($rootScope, $state, $stateParams, $http, authenticate, CHECK_LOGIN_URL_ADMIN, CHECK_LOGIN_METHOD, BASE_PATH, IS_AUTH) {
    $rootScope.apiResolve = 0;
    $rootScope.doingResolve = true;
    $rootScope.$state = $state;
    $rootScope.$stateParams = $stateParams;
    $rootScope.user = {};

    var checkPromise = $http({
        url: BASE_PATH + CHECK_LOGIN_URL_ADMIN,
        method: CHECK_LOGIN_METHOD
    });

    authenticate.check(checkPromise).then(function(){
        $rootScope.user = authenticate.getUser();
        if($state.current.name == 'adminLogin.form') {
            $state.go(authenticate.targetPage);
        }
        $rootScope.doingResolve = false;
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
            if(toState.name == 'adminLogin.form' && authenticate.isLoggedIn()) {
                $state.go(authenticate.targetPage);
            }
        }
    });
});

showyourtraceAdmin.controller('ErrorCtrl', function($rootScope, $scope, $state, Restangular, BASE_PATH) {

});


showyourtraceAdmin.controller('LogoutCtrl', function($rootScope, $scope, $state, $http, authenticate, LOGOUT_URL, LOGOUT_METHOD, BASE_PATH) {
    $scope.logoutMe = function () {
        var logoutPromise = $http({
            url: BASE_PATH + LOGOUT_URL,
            method: LOGOUT_METHOD
        });
        authenticate.logout(logoutPromise);
        //$rootScope.user = {role:accessLevels.ANONYMOUS};
        $rootScope.user = null;
        $state.go(authenticate.loginPage);
    };
});

showyourtraceAdmin.controller('LoginCtrl', function ($rootScope, $scope, $state, $http, $timeout, authenticate, $modalStack, LOGIN_URL, LOGIN_METHOD, BASE_PATH) {
    // loginService exposed and a new Object containing login user/pwd
    //$scope.auth = authenticateService;
    $scope.working = false;
    $scope.wrongCredentials = false;
    $scope.user = {

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
        $scope.wrongCredentials = false;
        authenticate.login(loginPromise);

        loginPromise.success(function () {
            $rootScope.user = authenticate.getUser();
            $state.go(authenticate.targetPage);
        });

        loginPromise.error(function () {
            $scope.wrongCredentials = true;
            $timeout(function () { $scope.wrong = false; }, 8000);
        });
        loginPromise["finally"](function () {
            $scope.working = false;
        });
        return true;
    };
    $scope.loginError = function(){
        return angular.isDefined($rootScope.loginError) && $rootScope.loginError != null && $rootScope.loginError.length > 0;
    };
    $scope.loginErrorText = function(){
        return angular.isDefined($rootScope.loginError) && $rootScope.loginError != null ? $rootScope.loginError : '';
    };
});


//angular.module("template/popover/popover.html", []).run(["$templateCache", function ($templateCache) {
//    $templateCache.put("template/popover/popover.html",
//        "anyHtml");
//}]);

