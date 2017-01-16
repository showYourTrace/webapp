var app  = angular.module('showyourtraceApp.search', ['showyourtrace.configuration', 'showyourtraceApp.services']);
app.config(function ($stateProvider, $urlRouterProvider, BASE_PATH) {
    $stateProvider
        .state('app.search', {
            controller: 'SearchController',
            url: 'search',
            templateUrl: BASE_PATH + '/page/public/search.html',
            resolve: {
            }
        })
});
app.controller('SearchController', SearchController);