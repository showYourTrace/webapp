var app  = angular.module('languagelearnAdmin.mailing', ['languagelearn.configuration', 'languagelearnAdmin.mailing.services']);
app.config(function ($stateProvider, $urlRouterProvider, BASE_PATH) {
    $stateProvider
        .state('adminApp.home.mailing', {
            controller: 'MailingAdminCtrl',
            url: 'user/mailing',
            templateUrl: BASE_PATH + '/page/admin/mailing/mailing.html',
        })
});
app.controller('MailingAdminCtrl', MailingAdminCtrl);