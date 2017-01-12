function ConfirmResultController($scope, $stateParams, $cookies, $http, $timeout, $location, BASE_PATH, API_END_POINT, HOME_PAGE, appServices) {

    $scope.state = {
        subscribeEmail: $cookies.get("couponweb_subscribe_email"),
        subscribeError: ''
    };
    $scope.subscribeMailSent = false;

    $scope.result = null;
    $scope.gotoLink = {
        url: '',
        txt: ''
    };

    $scope.working = true;

    appServices.confirm($stateParams.confirmId).then(function(result) {
        $scope.result = result;

        $scope.gotoLink = result.success
            ?
                {
                    url: HOME_PAGE,
                    txt: 'Goto home page'
                }
            :
                {
                    url: '#/',
                    txt: 'Resend confirmation email'
                };
        $scope.working = false;
    }, function() {
        $scope.working = false;
    });

    $scope.execute = function() {

        if( $scope.result.success) {
            window.location.href = BASE_PATH + "/#/mainDeal";
            //$location.path(BASE_PATH + "/#/mainDeal");
            return;
        }

        $scope.working = true;
        appServices.okCancelDialog("Confirmation", "Do you want to resend confirmation message to address: " + $scope.state.subscribeEmail + "?")
        .then( function() {
            $http.post( BASE_PATH + API_END_POINT + "resendConfirmation", $scope.state.subscribeEmail )
                .success(function() {
                    $scope.subscribeMailSent = true;
                }).error(function(error) {
                    $scope.state.subscribeError = error.applicationMessage;
                    $timeout(function () { $scope.state.subscribeError = ''; $scope.state.subscribeEmail = '' }, 6000);
                }).finally(function() {
                    $scope.working = false;
                });
            }
        );

    };

    $scope.subscribeError = function(){
        return angular.isDefined($scope.state.subscribeError) && $scope.state.subscribeError != null && $scope.state.subscribeError.length > 0;
    };
    $scope.subscribeErrorText = function() {
        return angular.isDefined($scope.state.subscribeError) && $scope.state.subscribeError != null ? $scope.state.subscribeError : '';
    };

}