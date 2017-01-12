function EditPageCtrl($scope, $modalInstance, entityAdminRepository, entityId) {
    $scope.state = {
        model : null
    };

    $scope.load = function() {
        if(!angular.isDefined(entityId) || !entityId) {
            return;
        }

        entityAdminRepository.get(entityId).then(function(result) {
                $scope.state.model = result;
            },
            function(error) {

            });
    };

    // Save button
    $scope.ok = function() {
        if($scope.state.model.id != null) {
            entityAdminRepository.update($scope.state.model).then(function() {
                $modalInstance.close(true);
            }, function(error) {});
        }
        else {
            entityAdminRepository.create($scope.state.model).then(function() {
                $modalInstance.close(true);
            }, function(error) {});
        }
    };

    // Cancel button
    $scope.cancel = function() {
        $modalInstance.close(false);
    };

    $scope.load();
}