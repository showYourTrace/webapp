function dialogUserAdminSearchCtrl($scope, $modalInstance, localStorageService, userAdminModalServices, userAdminRepository,
                                     appServices, templatesRepository) {
    UserAdminSearchCtrl($scope, localStorageService, userAdminModalServices, userAdminRepository, templatesRepository, appServices);

    $scope.ok = function() {
        if($scope.state.selected != false) {
            $modalInstance.close($scope.state.selected);
        }
    };

    $scope.cancel = function() {
        $modalInstance.dismiss("cancel");
    };

    $scope.canApply = function() {
        return  $scope.state.selected != false
    };

    $scope.state.userSearchTemplate = templatesRepository.userSearch();
}

function UserAdminSearchCtrl($scope, localStorageService, userAdminModalServices, userAdminRepository,
                             templatesRepository, appServices) {

    $scope.filters = {
        login: '',
        email: '',
        name: '',
        secondName: '',
        receivePromoOnly: false
    };

    $scope.filterversion = "userSearchFilters_v1.3";
    $scope.filters = localStorageService.get($scope.filterversion);

    $scope.checkFilters = function() {
        if (!angular.isDefined($scope.filters) || $scope.filters == null) {
            $scope.clearFilters();
        }
    };

    $scope.clearFilters = function() {
        localStorageService.remove($scope.filterversion);
        $scope.filters = {
            login: '',
            email: '',
            name: '',
            secondName: '',
            receivePromoOnly: false
        };
        $scope.state.sorts = {direction: 'asc', property: 'id'};
    };

    $scope.removeConfirmHeader = function() {
        return "Delete user";
    };
    $scope.removeConfirmMsg = function() {
        var name = typeof $scope.state.selected.name != 'undefined' && $scope.state.selected.name != null
                        ? $scope.state.selected.name
                        : typeof $scope.state.selected.login != 'undefined' && $scope.state.selected.login != null
                            ? $scope.state.selected.login
                            : typeof $scope.state.selected.email != 'undefined' && $scope.state.selected.email != null
                                ? $scope.state.selected.email
                                : "";

        return "User " + name + " will be removed";
    };

    SearchPageCtrl($scope, userAdminRepository, userAdminModalServices, templatesRepository, localStorageService, appServices);

    $scope.checkFilters();
    $scope.load();
}