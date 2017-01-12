var app  = angular.module('languagelearnAdmin.mailing.services', ['restangular', 'languagelearn.configuration']);
app.factory('mailingAdminRepository', function ($q, Restangular) {
    return {
        sendPromoMail: function(msg) {
            return Restangular.all("mailing/sendPromo").post(msg);
        }
    }
});