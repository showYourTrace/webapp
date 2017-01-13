var app  = angular.module('showyourtraceAdmin.mailing', ['showyourtrace.configuration', 'showyourtraceAdmin.mailing.services']);
app.config(function ($stateProvider, $urlRouterProvider, BASE_PATH) {
    $stateProvider
        .state('adminApp.home.mailing', {
            controller: 'MailingAdminCtrl',
            url: 'user/mailing',
            templateUrl: BASE_PATH + '/page/admin/mailing/mailing.html',
        })
});
app.controller('MailingAdminCtrl', MailingAdminCtrl);