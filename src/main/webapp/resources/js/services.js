var languagelearnApp  = angular.module('languagelearnApp.services', ['restangular', 'languagelearn.configuration']);

languagelearnApp.controller('OkCancelDialogCtrl', OkCancelDialogCtrl);

languagelearnApp.factory('appServices', function ($q, $modal, Restangular, BASE_PATH) {

    return {
        showSubscribe: function() {
            var modalInstanceDialog = $modal.open({
                templateUrl: BASE_PATH + '/page/common/loginPopup.html',
                windowClass: 'modal-middle',
                controller: 'LoginCtrl',
                resolve: {
                    mode: function() { return 'SUBSCRIBE' }
                }
            });
            return modalInstanceDialog.result;
        },
        showLogin: function() {
            var modalInstanceDialog = $modal.open({
                templateUrl: BASE_PATH + '/page/common/loginPopup.html',
                windowClass: 'modal-middle',
                controller: 'LoginCtrl',
                resolve: {
                    mode: function() { return 'LOGIN' }
                }
            });
            return modalInstanceDialog.result;
        },
        showRegisterUser: function() {
            var modalInstanceDialog = $modal.open({
                templateUrl: BASE_PATH + '/page/common/loginPopup.html',
                windowClass: 'modal-middle',
                controller: 'LoginCtrl',
                resolve: {
                    mode: function() { return 'REGISTER' }
                }
            });
            return modalInstanceDialog.result;
            //var modalInstanceDialog = $modal.open({
            //    templateUrl: BASE_PATH + '/page/common/registerUserPopup.html',
            //    windowClass: 'modal-middle',
            //    controller: 'RegisterUserCtrl',
            //    resolve: {
            //    }
            //});
            //return modalInstanceDialog.result;
        },
        okCancelDialog: function(header, message) {
            var modalInstanceDialog = $modal.open({
                templateUrl: BASE_PATH + '/page/common/confirmation.html',
                windowClass: 'modal-mini',
                controller: 'OkCancelDialogCtrl',
                resolve: {
                    header: function () {
                        return header;
                    },
                    message: function () {
                        return message;
                    }
                }
            });
            return modalInstanceDialog.result;
        },
        notification: function(header, message) {
            var modalInstanceDialog = $modal.open({
                templateUrl: BASE_PATH + '/page/common/okPopup.html',
                windowClass: 'modal-mini',
                controller: 'OkCancelDialogCtrl',
                resolve: {
                    header: function () {
                        return header;
                    },
                    message: function () {
                        return message;
                    }
                }
            });
            return modalInstanceDialog.result;
        },
        registerUser: function(username, password, email) {
            return Restangular.all("/user/register").post({ username: username, password: password, email: email });
        },
        confirm: function(confirmId) {
            return Restangular.one("confirm", confirmId).get();
        }
    }
});

languagelearnApp.factory('fileRepository', function ($window, Restangular, API_END_POINT_ADMIN, FileUploader) {

    return {
        uploader: function ($scope, url) {

            var uploadUrl = angular.isDefined(url) && url != null ?  url : API_END_POINT_ADMIN + '/file/upload';

            var getHtml5 = function() {
                return !!($window.File && $window.FormData);
            };

            var uploader = new FileUploader(
                {
                    scope: $scope, // to automatically update the html. Default: $rootScope
                    url: uploadUrl,
                    autoUpload: true,
                    formData: [{html5: getHtml5()}]
                }
            );

            $scope.uploading = function() {
                return !!(angular.isDefined($scope.uploader) && angular.isDefined($scope.uploader.isUploading) && $scope.uploader.isUploading == true);
            };

            return uploader;
        },

        noAutoUploader: function ($scope, url) {

            var uploadUrl = angular.isDefined(url) && url != null ?  url : API_END_POINT_ADMIN + '/file/upload';

            var getHtml5 = function() {
                return !!($window.File && $window.FormData);
            };

            var uploader = new FileUploader(
                {
                    scope: $scope, // to automatically update the html. Default: $rootScope
                    url: uploadUrl,
                    autoUpload: false,
                    formData: [{html5: getHtml5()}]
                }
            );

            $scope.uploading = function() {
                return !!(angular.isDefined($scope.uploader) && angular.isDefined($scope.uploader.isUploading) && $scope.uploader.isUploading == true);
            };

            return uploader;
        },

        deleteFile: function(fileId) {
            //return Restangular.one("file", fileId).remove();
        }
    }
});

languagelearnApp.factory('referenceService', function(dealAdminRepository) {
    var selector = {
        select: function(referenceType) {
            switch(referenceType) {
                case 'dealType':
                    return dealAdminRepository.loadTypes();
                case 'dealCategory':
                    return dealAdminRepository.loadCategories();
            }
        }
    };
    return {
        loadReference: function(type, container) {
            selector.select(type).then(function(result) {
                for(var i=0; i<result.length; i++) {
                    container.push(result[i]);
                }
            }, function(error) {

            });
        }
    }
});

languagelearnApp.factory('selectFilter', function(vendorAdminModalServices, userAdminModalServices) {
    var selector = {
        select: function(filterType) {
            switch(filterType) {
                case 'vendor':
                    return vendorAdminModalServices.search();
                case 'user':
                    return userAdminModalServices.search();
            }
        }
    };

    return {
        selectValue: function(filter, filterType, displayedProperty, modelProperty) {
            selector.select(filterType).then(function(result) {
                if(angular.isDefined(result) && result) {
                    filter.id = result.id;
                    if(angular.isDefined(displayedProperty) && displayedProperty && displayedProperty.length > 0) {
                        if(angular.isDefined(modelProperty) && modelProperty && modelProperty.length > 0) {
                            filter[displayedProperty] = result[modelProperty];
                        }
                        else {
                            filter[displayedProperty] = result[displayedProperty];
                        }
                    }
                    else {
                        filter.value = result.name;
                    }
                }
            });
        },
        clearValue: function(filter, displayedProperty) {
            if (angular.isDefined(displayedProperty) && displayedProperty && displayedProperty.length > 0) {
                filter.id = null;
                filter[displayedProperty] = null;
            } else {
                filter.id = null;
                filter.value = '';
            }
        }
    }
});

languagelearnApp.factory('templatesRepository', function (BASE_PATH) {
    return {
        pager: function() { return BASE_PATH + "/page/common/table-pagination.html" },
        vendorSearch: function() { return BASE_PATH + "/page/admin/vendor/vendorSearch.html" },
        userSearch: function() { return BASE_PATH + "/page/admin/user/userSearch.html" }
    }
});


// Set focus on input element
languagelearnApp.factory('focusInput', function($timeout) {
    return function(id) {
        // timeout makes sure that is invoked after any other event has been triggered.
        // e.g. click events that need to run before the focus or
        // inputs elements that are in a disabled state but are enabled when those events
        // are triggered.
        $timeout(function() {
            var element = document.getElementById(id);
            if(element)
                element.focus();
            element.select();
        });
    };
});

languagelearnApp.factory('queueService', function($rootScope){
    var queue = new createjs.LoadQueue(true);

    function loadManifest(manifest) {
        queue.loadManifest(manifest);

        queue.on('progress', function(event) {
            $rootScope.$broadcast('queueProgress', event);
        });

        queue.on('complete', function() {
            $rootScope.$broadcast('queueComplete', manifest);
        });
    }

    return {
        loadManifest: loadManifest
    }
});

languagelearnApp.animation('.fade-in-animation', function ($window) {
    return {
        enter: function (element, done) {
            TweenMax.fromTo(element, 1, { opacity: 0}, {opacity: 1, onComplete: done});
        },

        leave: function (element, done) {
            TweenMax.to(element, 1, {opacity: 0, onComplete: done});
        }
    };
});

languagelearnApp.directive('couponslider', function(IMG_STORE_PATH) {
    return {
        restrict: 'AE',
        replace: true,
        transclude: true,
        scope: {
            categorydeals: '=',
        },
        controller: ['$scope', function($scope) {


        }],
        link: function(scope, elem, attrs) {

            scope.typeId = scope.categorydeals.typeId;
            scope.index = 0;
            scope.visibleCount = 4;
            scope.endIndex = scope.categorydeals.deals.length - scope.visibleCount;

            scope.getImageUrl = function(itemUrl) {
                return IMG_STORE_PATH + itemUrl;
            };

            scope.clickLeft = function(typeId) {
                if(scope.index < scope.endIndex ){
                    $('#feedType' + scope.typeId).children().animate({'left':'-=190px'});
                    scope.index++;
                }
            };

            scope.clickRight = function() {
                if(scope.index > 0){
                    scope.index--;
                    $('#feedType' + scope.typeId).children().animate({'left':'+=190px'});
                }
            };
        },
        templateUrl: 'page/public/deal/main/topDealFeed.html'
    };
});

function OkCancelDialogCtrl($scope, $modalInstance, message, header) {
    $scope.confirmation = {message: message, header: header};

    $scope.yes = function () {
        $modalInstance.close(true);
    };

    $scope.no = function () {
        $modalInstance.dismiss(false);
    };
}
