var app = angular.module('showyourtraceApp.authenticate', []);

app.provider('authenticate', function() {
    var config = {
        unauthorizedPage: '/unauthorized',
        targetPage: '/',
        loginPage: '/login'
    };

    return {
        setConfig : function (configuration) {
            config = configuration;
        },
        $get: function($http, $q, localStorageService) {

            var user = null;

            return {
                targetPage: config.targetPage,
                loginPage: config.loginPage,
                unauthorizedPage: config.unauthorizedPage,

                setUser : function(usr) {
                    if(usr) {
                        user = usr;
                        localStorageService.set('user', usr);
                    } else {
                        user = null;
                        localStorageService.remove('user');
                    }
                },

                getUser: function () {
                    return user;
                },

                isLoggedIn: function () {
                    return user !== null;
                },

                authorize: function (accessLevel, role) {
                    return accessLevel & role;
                },

                login: function (httpPromise) {
                    var $this = this;
                    httpPromise.success(function (user, status, headers, config){
                        $this.setUser(user);
                    });
                },

                logout: function () {
                    var $this = this;
                    $this.setUser(null);
                },

                check: function (httpPromise) {
                    var $this = this;
                    var defer = $q.defer();
                    // When the $http is done, we register the http result into loginHandler, `data` parameter goes into loginService.loginHandler
                    httpPromise.success(function (user, status, headers, config) {
                        $this.setUser(user);
                    });

                    httpPromise.then(
                        function success() {
                            defer.resolve();
                        },
                        function reject(httpObj) {
                            defer.reject(httpObj.status.toString());
                        }
                    );
                    return defer.promise;
                }
            };
        }
    };
});
