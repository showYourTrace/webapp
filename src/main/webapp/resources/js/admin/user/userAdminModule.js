var app  = angular.module('languagelearnAdmin.user', ['languagelearn.configuration', 'languagelearnAdmin.user.services']);
app.config(function ($stateProvider, $urlRouterProvider, BASE_PATH) {
    $stateProvider
        .state('adminApp.home.user', {
            abstract: true,
            template: "<ui-view></ui-view>"
        })
        .state('adminApp.home.user.search', {
            controller: 'UserAdminSearchCtrl',
            url: 'user/search',
            templateUrl: BASE_PATH + '/page/admin/user/userSearch.html',
            resolve: {
            }
        })
});
app.controller('UserAdminEditCtrl', UserAdminEditCtrl);
app.controller('UserAdminSearchCtrl', UserAdminSearchCtrl);
app.controller('dialogUserAdminSearchCtrl', dialogUserAdminSearchCtrl);