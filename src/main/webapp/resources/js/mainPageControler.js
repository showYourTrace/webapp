function MainPageController($rootScope, $scope, appServices) {

    $scope.loggedIn = function() {
        return $rootScope.user;
    };

    $scope.openLogin = function() {
        appServices.showLogin().then(function() {

            },
            function(error) {});
    };

    $scope.openRegister = function() {
        appServices.showRegisterUser().then(function() {

            },
            function(error) {});
    };
}
