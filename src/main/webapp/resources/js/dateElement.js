
function prepareDateElements($scope) {

    $scope.df = "dd.MM.yyyy";

    $scope.dtf = "dd.MM.yyyy HH:mm:ss";

    $scope.openDate = function ($event, openDateControlState) {
        $event.preventDefault();
        $event.stopPropagation();

        for(var i=0; i < $scope.state.openedDate.length; i++) {
            $scope.state.openedDate[i].opened = openDateControlState.id === $scope.state.openedDate[i].id;
        }
    };

    $scope.weekEnds = function (date, mode) {
        return ( mode === 'day' && ( date.getDay() === 0 || date.getDay() === 6 ) );
    };

    $scope.dateOptions = {
        'year-format': "'yy'",
        'starting-day': 1
    };

    $scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'shortDate'];
    $scope.format = $scope.formats[0];
}