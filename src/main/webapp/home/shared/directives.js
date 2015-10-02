ky3p
.directive('ngEnter', function () {
    return function (scope, element, attrs) {
        element.bind("keydown keypress", function (event) {
            if (event.which === 13) {
                scope.$apply(function () {
                    scope.$eval(attrs.ngEnter);
                });

                event.preventDefault();
            }
        });
    };
})
.directive('vectorMap', ['$timeout', function ($timeout) {
    return {
        transclude: true,
        template: '<div id="america"></div><div id="eaaa"></div>',
        link: function (scope, element, attrs) {
            $timeout(function () {
                $('#america').vectorMap({
                    map: 'america_en',
                    backgroundColor: '#4c525b',
                    color: '#ffffff',
                    hoverOpacity: 0.7,
                    selectedColor: '#1cb7d9',
                    enableZoom: false,
                    showTooltip: false,
                    scaleColors: ['#C8EEFF', '#006491'],
                    normalizeFunction: 'polynomial',
                    multiSelectRegion: true
                });
                $('#eaaa').vectorMap({
                    map: 'eaaa_en',
                    backgroundColor: '#4c525b',
                    color: '#ffffff',
                    hoverOpacity: 0.7,
                    selectedColor: '#1cb7d9',
                    enableZoom: false,
                    showTooltip: false,
                    scaleColors: ['#C8EEFF', '#006491'],
                    normalizeFunction: 'polynomial',
                    multiSelectRegion: true
                });
            }, 500);
        }
    };
}])
.directive('defaultImage', function () {
    return {
        restrict: 'A',
        scope: {
            defaultImage: '@'
        },
        link: function (scope, element, attrs) {
            element
                .on('load', ngSrcImageLoaded)
                .on('error', ngSrcImageLoadFailed);

            scope.$on("$destroy", handleDestroyEvent);

            function ngSrcImageLoaded(event) {
                element
                    .unbind('load', ngSrcImageLoaded)
                    .unbind('error', ngSrcImageLoadFailed);
            };

            function ngSrcImageLoadFailed(event) {
                element
                    .unbind('load', ngSrcImageLoaded)
                    .unbind('error', ngSrcImageLoadFailed)
                    .on('load', defaultImageLoaded)
                    .on('error', defaultImageLoadFailed)
                    .attr('src', scope.defaultImage);
            };

            function defaultImageLoaded(event) {
                element
                    .unbind('load', defaultImageLoaded)
                    .unbind('error', defaultImageLoadFailed);
            };

            function defaultImageLoadFailed(event) {
                element
                    .unbind('load', defaultImageLoaded)
                    .unbind('error', defaultImageLoadFailed);
            };

            function handleDestroyEvent(event) {
                element
                    .unbind('load')
                    .unbind('error');
            };

            scope.$on('updateImage', function (event, url) {
                element.hide();
                element.attr('src', '..');

                element
                    .bind('load', updateCompanyLogo)
                    .bind('error', updateCompanyLogo);

                function updateCompanyLogo(event) {
                    element
                        .unbind('load')
                        .unbind('error')
                        .bind('load', function (event) {
                            element.show();
                        })
                        .attr('src', url);
                };
            });
        }
    }
})
.directive('uploadFile', function () {
    return {
        restrict: 'A',
        require: '?ngModel',
        scope: {
            model: '=ngModel',
            change: '&uploadFileChange'
        },
        link: function (scope, element, attrs, ngModel) {
            if (!ngModel) {
                throw Error('no model');
            }
            scope.filename = '';

            element.on('change', getFileLink);

            var viewValue = {
                name: scope.filename,
                element: element
            };

            function getFileLink(event) {
                if (event.target.value) {
                    var fileInputMatch =
                        event.target.value.match(/[^\/\\]+$/);

                    viewValue.name = fileInputMatch ? fileInputMatch[0] : '';
                    if (scope.filename == viewValue.name) {
                        scope.change();
                    }
                    scope.filename = viewValue.name;
                    setValidity();
                    ngModel.$setViewValue(viewValue);
                    ngModel.$render();
                }
            }

            scope.$watch('filename', function (n, o) {
                if (n && ngModel.$valid) {
                    scope.change();
                }
            });

            function setValidity() {
                if (attrs.fileTypes) {
                    var lastIndex = scope.filename.lastIndexOf('.');

                    if (lastIndex === -1) {
                        return ngModel.$setValidity('extension', false);
                    }
                    ;

                    var extension = scope.filename
                        .substr(lastIndex + 1, scope.filename.length)
                        .toLowerCase();

                    if (attrs.fileTypes.split(',').indexOf(extension) === -1) {
                        return ngModel.$setValidity('extension', false);
                    }
                    ;
                }
                ;

                return ngModel.$setValidity('extension', true);
            }
        }
    }
})
.directive('datepickerLocaldate', ['$parse', function ($parse) {
    var directive = {
        restrict: 'A',
        require: ['ngModel'],
        link: link
    };
    return directive;

    function link(scope, element, attr, ctrls) {
        var ngModelController = ctrls[0];

        // called with a JavaScript Date object when picked from the datepicker
        ngModelController.$parsers.push(function (viewValue) {
            if (!viewValue) {
                return;
            }
            // undo the timezone adjustment we did during the formatting
            viewValue.setMinutes(viewValue.getMinutes() - viewValue.getTimezoneOffset());
            // we just want a local date in ISO format
            return viewValue.toISOString().substring(0, 10);
        });

        // called with a 'yyyy-mm-dd' string to format
        ngModelController.$formatters.push(function (modelValue) {
            if (!modelValue) {
                return undefined;
            }
            // date constructor will apply timezone deviations from UTC (i.e. if locale is behind UTC 'dt' will be one day behind)
            var dt = new Date(modelValue);
            // 'undo' the timezone offset again (so we end up on the original date again)
            dt.setMinutes(dt.getMinutes() + dt.getTimezoneOffset());
            return dt.getTime();
        });
    }
}])
/**
* @ngdoc directive
* @name compile
* @description
* Compiles current element
* @example
* var someContentToCompile = '<input type="text" datepicker-popup ng-model="somemodel">'
*  <div compile ng-bind-html="someContentToCompile | unsafe"></div>
*/
.directive('compile',function($compile, $timeout){
    return{
        restrict:'A',
        link: function(scope,elem,attrs){
            $timeout(function(){
                $compile(elem.contents())(scope);
            });
        }
    };
})
/**
* @description Directive for form fields comparison
*
* @example
*
* <form name="someForm">
*     <input ng-model="data.password" type="password">
*     <input ng-model="data.passwordConfirmation" match-with="data.password" type="password">
*     <span ng-show="data.passwordConfirmation.$error.matchWith">Passwords don't match</span>
* </form>
*/
.directive("matchWith", function () {
    return {
        require: "ngModel",
        scope: {
            matchWith: "="
        },
        link: function(scope, element, attributes, ngModel) {

            ngModel.$validators.matchWith = function(modelValue) {
                return modelValue == scope.matchWith;
            };

            scope.$watch("matchWith", function() {
                ngModel.$validate();
            });
        }
    }
})
.directive('focusMe', function($timeout, $rootScope, $parse, $document) {
    return {
        scope: { 
            trigger: '=focusMe'
        },
        //priority: Number.MIN_SAFE_INTEGER,
        link: function(scope, element, attrs) {
            scope.$watch(function(){
                return scope.trigger;
            }, function(new_value, old_value) {
                $timeout(function() {
                    if(new_value)
                        element[0].focus();
                }, 100);
            }, true);
            element.bind('blur', function() {
                scope.trigger = false;
            });
        }
    };
})

.directive('autofocus', ['$timeout',
    function ($timeout) {
      return {
        restrict: 'A',
        link: function ($scope, $element) {
          $timeout(function () {
            $element[0].focus();
          });
        }
      };
    }
])

.directive('isValid', function ($timeout){ 
    return {
        require: 'ngModel',
        link: function(scope, elem, attr, ngModel) {
            var soValid = function (value) {
                // check happening earlier than attribute change
                $timeout(function(){
                    // from attribute always 'string'
                    ngModel.$setValidity('isValid', attr.isValid == 'true');    
                });
                return value;
            };
            // check on change
            ngModel.$parsers.unshift(soValid);
            // check on load
            ngModel.$formatters.unshift(soValid);
        }
    };
});
