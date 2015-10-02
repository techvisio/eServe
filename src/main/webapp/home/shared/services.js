angular.module('erp.services',[])
    .service('deferredManager', ['$location', '$q', '$timeout',
        function ($location, $q, $timeout) {
            var deferredQueries = {},
                httpRequests = {},
                ignoreServices = [],
                globalIgnore = /LoggedInUserDetail/;
            return {
                /* for $q */
                cancel: function (service, defer_name) {
                    //console.log(service, defer_name)
                    if (angular.isObject(deferredQueries[service][defer_name])) {
                        //console.log(service, defer_name)
                        deferredQueries[service][defer_name].resolve("User cancelled");
                    }
                },
                defer: function (name, section, ignore) {
                    var service = $location.path(),
                        section = section || '',
                        ignore = ignore || false,
                        defer_name = (section ? section + "_" : '' ) + name,
                        deferred = $q.defer();

                    if (!ignore) {
                        if (!deferredQueries[service]) {
                            deferredQueries[service] = {};
                        }
                        this.cancel(service, defer_name);
                        deferredQueries[service][defer_name] = deferred;
                    }

                    // this aborts the request!
                    /*
                     $timeout(function() {
                     deferred.resolve("timeout");
                     }, 20000);*/

                    return deferred;
                },

                /* for ngResource */
                isIgnoreService: function (url) {
                    return ignoreServices.indexOf(url) > -1;
                },
                ignoreService: function (url) {
                    if (!this.isIgnoreService(url)) {
                        ignoreServices.push(url);
                    }
                },
                deferService: function (url) {
                    var deferred = $q.defer();
                    if (!url.match(globalIgnore)
                        && !this.isIgnoreService(url)) {
                        httpRequests[url] = deferred;
                    }
                    return deferred.promise;
                },

                /* cancel both cases */
                cancelAll: function (path) {
                    var self = this;
                    angular.forEach(deferredQueries, function (v, k) {
                        if (k != path) {
                            angular.forEach(v, function (promise, name) {
                                promise.resolve("user cancelled");
                            });
                            delete deferredQueries[k];
                        }
                    });
                    angular.forEach(httpRequests, function (promise, url) {
                        promise && promise.resolve("Cancelled upon navigation");
                        delete httpRequests[url];
                    });
                }
            }
        }])
/**
 * @ngdoc service
 * @name alertModal
 * @description
 * Factory that handles alertModal instantiation
 * @example
 *  function exampleCtrl(alertModal){
 *      alertModal('<div>some content</div>', 'some title').then(function(){
 *          console.log('this is "yes' callback');
 *      });
 *  }
 */
    .factory('alertModal', function ($modal) {
        return alertModal;

        function alertModal(content, title){
            var _modalInstance;

            _modalInstance = $modal.open({
                templateUrl: './modals/alertModal.html',
                controller: function ($scope, $modalInstance) {
                    $scope.content = content;
                    $scope.title = title || 'Please pay attention';
                    $scope.ok = $modalInstance.close;
                }
            });
            return _modalInstance.result;
        }
    })
/**
 * @ngdoc service
 * @name confirmModal
 * @description
 * Factory that handles confirmModal instantiation
 * @example
 *  function exampleCtrl(confirmModal){
 *      confirmModal('<div>some content</div>', 'some title').then(function(){
 *          console.log('this is "yes' callback');
 *      }, function(){
 *          console.log('this is "no" callback');
 *      });
 *  }
 */
    .factory('confirmModal', function ($modal) {
        return confirmModal;

        function confirmModal(content, title){
            var _modalInstance;

            _modalInstance = $modal.open({
                templateUrl: './modals/confirmModal.html',
                controller: function ($scope, $modalInstance) {
                    $scope.content = content;
                    $scope.title = title || 'Confirm please';
                    $scope.yes = $modalInstance.close;
                    $scope.no = $modalInstance.dismiss;
                }
            });
            return _modalInstance.result;
        }
    });