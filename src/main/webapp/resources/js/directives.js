languagelearnApp.directive('gsZebraRows', function() {
    var rowColor = function(element, index, isSelected) {
        if (angular.isDefined(index) && index != null) {
            if (angular.isDefined(isSelected) && isSelected != null && isSelected === true) {
                element.addClass('active');
                element.removeClass('gs-row-default');
                element.removeClass('gs-row-hover');
            } else {
                if (index % 2 == 0) {
                    element.addClass('gs-row-default');
                    element.removeClass('active');
                    element.removeClass('gs-row-hover');
                } else {
                    element.addClass('gs-row-hover');
                    element.removeClass('gs-row-default');
                    element.removeClass('active');
                }
            }
        } else {
            element.addClass('gs-row-default');
            element.removeClass('active');
            element.removeClass('gs-row-hover');
        }
    };

    var link = function(scope, element, attrs) {
        if (angular.isDefined(attrs) && attrs != null && angular.isDefined(attrs.gsIndex)) {
            rowColor(element, attrs.gsIndex, false);
        }
    };

    return {
        link: link
    };
});

// Execute on enter keypress event
languagelearnApp.directive('ngEnter', function() {
    return function(scope, element, attrs) {
        element.bind("keydown", function(event) {
            if(angular.isDefined(event) && angular.isDefined(event.which) && event.which === 13) {
                scope.$apply(function(){
                    if (angular.isDefined(attrs) && angular.isDefined(attrs.ngEnter)) {
                        scope.$eval(attrs.ngEnter, {'event': event});
                    }
                });

                if (angular.isDefined(event.preventDefault)) {
                    event.preventDefault();
                }
            }
        });
    };
});

languagelearnApp.directive('angTab', function() {
    var showHidePanels = function(element, elemA) {
        if (angular.isDefined(elemA.attr('ang-ng-tab-index')) && elemA.attr('ang-ng-tab-index') != null) {
            var tabPanelIndex = elemA.attr('ang-ng-tab-index');
            var panelVisible = false;

            angular.forEach(element.find('div.ang-tab-body'), function(iterPanelElement, index) {
                var panel = angular.element(iterPanelElement);
                if (angular.isDefined(panel.attr('ang-ng-tab-index')) && panel.attr('ang-ng-tab-index') != null) {
                    if (tabPanelIndex == panel.attr('ang-ng-tab-index')) {
                        panel.removeClass('ng-hide');
                        panelVisible = true;
                        panel.show();
                    }
                }
            });

            if (panelVisible) {
                angular.forEach(element.find('div.ang-tab-body'), function(iterPanelElement, index) {
                    var panel = angular.element(iterPanelElement);
                    if (angular.isDefined(panel.attr('ang-ng-tab-index')) && panel.attr('ang-ng-tab-index') != null) {
                        if (tabPanelIndex != panel.attr('ang-ng-tab-index')) {
                            panel.hide();
                        }
                    }
                });
            }

            if (panelVisible) {
                angular.forEach(element.find('ul.nav-tabs').find('li.ang-tab'), function(iterLi) {
                    var tempIndex = angular.element(iterLi).find('a').attr('ang-ng-tab-index');
                    if (angular.equals(tempIndex, tabPanelIndex)) {
                        angular.element(iterLi).addClass('active');
                    } else {
                        angular.element(iterLi).removeClass('active');
                    }
                });
            }
        }

    };

    var link = function(scope, element, attrs) {
        angular.forEach(element.find('ul.nav-tabs')/*.find('li')*/, function(iterTabElement, index) {
            var currentUl = angular.element(iterTabElement);
            currentUl.bind("click", function(event) {
                var elemA = angular.element(event.originalEvent.target || event.originalEvent.srcElement); //Fix for Internet Explorer
                if (!angular.isDefined(elemA.attr('disabled'))) {
                    showHidePanels(element, /*event.currentTarget,*/ elemA);
                }
                return false;
            });
        });
    };

    return {
        link: link
    };
});

languagelearnApp.directive('ngThumb', ['$window', function($window) {
    var helper = {
        support: !!($window.FileReader && $window.CanvasRenderingContext2D),
        isFile: function(item) {
            return angular.isObject(item) && item instanceof $window.File;
        },
        isImage: function(file) {
            var type =  '|' + file.type.slice(file.type.lastIndexOf('/') + 1) + '|';
            return '|jpg|png|jpeg|bmp|gif|'.indexOf(type) !== -1;
        }
    };

    return {
        restrict: 'A',
        template: '<canvas/>',
        link: function(scope, element, attributes) {
            if (!helper.support) return;

            var params = scope.$eval(attributes.ngThumb);

            if (!helper.isFile(params.file)) return;
            if (!helper.isImage(params.file)) return;

            var canvas = element.find('canvas');
            var reader = new FileReader();

            reader.onload = onLoadFile;
            reader.readAsDataURL(params.file);

            function onLoadFile(event) {
                var img = new Image();
                img.onload = onLoadImage;
                img.src = event.target.result;
            }

            function onLoadImage() {
                var width = params.width || this.width / this.height * params.height;
                var height = params.height || this.height / this.width * params.width;
                canvas.attr({ width: width, height: height });
                canvas[0].getContext('2d').drawImage(this, 0, 0, width, height);
            }
        }
    };
}]);
