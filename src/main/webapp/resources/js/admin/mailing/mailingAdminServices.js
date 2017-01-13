var app  = angular.module('showyourtraceAdmin.mailing.services', ['restangular', 'showyourtrace.configuration']);
app.factory('mailingAdminRepository', function ($q, Restangular) {
    return {
        sendPromoMail: function(msg) {
            return Restangular.all("mailing/sendPromo").post(msg);
        }
    }
});