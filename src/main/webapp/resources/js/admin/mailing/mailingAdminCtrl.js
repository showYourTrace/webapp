function MailingAdminCtrl($scope, mailingAdminRepository, fileRepository, appServices, BASE_PATH, API_END_POINT_ADMIN) {

    $scope.state = {
        subject: '',
        body: '',
        files: [],
        maxFileCount: 5
    };

    //============================================= FILE UPLOADER ======================================================
    $scope.imgUploader = null;

    var uploadUrl = BASE_PATH + API_END_POINT_ADMIN + 'file/upload';

    $scope.imgUploader = fileRepository.uploader($scope, uploadUrl);

    $scope.imgUploader.onSuccessItem  = function(item, response, status, headers) {
        $scope.state.files.push(response.path);
    };

    $scope.uploading = function(uploader) {
        return !!(angular.isDefined(uploader) && angular.isDefined(uploader.isUploading) && uploader.isUploading == true);
    };

    $scope.shadeButton = function($event) {
        var button = $event.currentTarget.children[0];
        var bkpClassName = button.className;
        button.className = button.className + ' buttonShaded';
        setTimeout(function(){
            button.className = bkpClassName;
        },150);
    };

    $scope.removeImage = function(item) {
        $scope.imgUploader.removeFromQueue(item);
    };
    //============================================= FILE UPLOAD ======================================================

    $scope.send = function() {
        appServices.okCancelDialog("Mailing","Please, confirm sending promo email").then(function() {
            var msg = {
                subject: $scope.state.subject,
                body: $scope.state.body,
                files: $scope.state.files
            };
            mailingAdminRepository.sendPromoMail(msg).then(function() {
                appServices.notification("Mailing","Sending message started in background").then(function() {
                    $scope.state = {
                        subject: '',
                        body: '',
                        files: [],
                        maxFileCount: 5
                    };
                    $scope.imgUploader.queue = [];
                });
            });
        });
    };

    $scope.sendButtonDisabled = function() {
        return $scope.state.subject.length == 0 || $scope.state.body.length == 0;
    }
}