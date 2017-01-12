function SearchPageCtrl($scope, entityAdminRepository, entityAdminModalRepository, templatesRepository, localStorageService, appServices, selectFilter) {

    $scope.state = {
        model: { itemArray: [] },
        sorts: {
            direction: 'asc',
            property: 'id'
        },
        page: 1,
        size: 25,
        selected: false,
        pagerTemplate: templatesRepository.pager()
    };

    $scope.references = {};

    // (id, name) select filter service
    $scope.selectFilterService = selectFilter;

    $scope.load = function() {
        if(angular.isDefined($scope.filters) && $scope.filters) {
            localStorageService.set($scope.filterversion, $scope.filters);
        }

        var sorts = [{
            direction: $scope.state.sorts.direction ? 'asc' : 'desc',
            property: $scope.state.sorts.property
        }];

        entityAdminRepository.search($scope.filters, sorts, $scope.state.page, $scope.state.size).then(function(result) {
                $scope.state.model.itemArray = result.content;
            },
            function(error) {

            });

        if(angular.isDefined($scope.filters) && $scope.filters) {
            localStorageService.set($scope.filterversion, $scope.filters);
        }
    };

    $scope.openInsert = function() {
        entityAdminModalRepository.edit(null).then(function() {
            $scope.load();
        });
    };

    $scope.openEdit = function() {
        if(!angular.isDefined($scope.state.selected) || !$scope.state.selected) {
            return;
        }
        entityAdminModalRepository.edit($scope.state.selected.id).then(function() {
            $scope.load();
        });
    };

    $scope.isSelected = function (item) {
        return $scope.state.selected == item;
    };

    $scope.setSelected = function (item) {
        if ($scope.state.selected == item) {
            $scope.state.selected = false;
        } else {
            $scope.state.selected = item;
        }
    };

    $scope.canSort = function (property, ascending) {
        if ($scope.state.sorts.property == property) {
            if ($scope.state.sorts.direction == (ascending == 'asc')) {
                return true;
            }
        }
        return false;
    };

    $scope.setSort = function (property) {
        if ($scope.state.sorts.property == property) {
            $scope.state.sorts.direction = !$scope.state.sorts.direction;
        } else {
            $scope.state.sorts.property = property;
            $scope.state.sorts.direction = true;
        }
        $scope.load();
    };

    $scope.canDelete = function () {
        return $scope.state.selected != false;
    };
    $scope.canEdit = function () {
        return $scope.state.selected != false;
    };

    $scope.confirmDelete = function() {
        appServices.okCancelDialog($scope.removeConfirmHeader(), $scope.removeConfirmMsg())
            .then(function() {
                entityAdminRepository.delete($scope.state.selected.id).then(function() {
                    $scope.load();
                }, function(error) {

                });
            });
    };

    $scope.onEnter = function() {
        $scope.load();
    }
}