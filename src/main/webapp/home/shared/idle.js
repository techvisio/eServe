(function () {
    angular.module('ky3p.idle', [])
    /**
     * @description
     * HTTP Interceptor that emits 'idle.updateSession' event on all uncached calls to KY3P API
     */
        .config(['$httpProvider', '$sceProvider',
            function ($httpProvider) {
                $httpProvider.interceptors.push(function ($rootScope, $window) {

                    function getDomain(url) {
                        var prefix = /^(?:[a-z]+:)?\/\//i;
                        var domain = /^[^\/]+/;
                        // remove any prefix
                        url = url.replace(prefix, "");
                        // assume any URL that starts with a / is on the current page's domain
                        if (url.charAt(0) === "/") {
                            url = $window.location.hostname + url;
                        }
                        // now extract just the domain
                        var match = url.match(domain);
                        if (match) {
                            return (match[0]);
                        }
                        return (null);
                    }

                    return {
                        response: function (response) {
                            if (!response.config.cached && (getDomain(response.config.url) === $window.location.hostname)) {
                                $rootScope.$emit('idle.updateSession');
                            }
                            return response;
                        }
                    };
                });
            }])

    /**
     * @ngdoc service
     * @name idle
     * @description
     * Service to handle user session expiration and warn user if session is about to expire
     */
        .service('idle', function ($modal, $window, $rootScope, $timeout, $interval, $http, User, appConfig) {
            var _modalInstance = null,
                _config = appConfig.idle,
                _idleTimeout,
                _timeoutTimeout,
                self = this;

            this.start = start;

            $rootScope.$on('idle.updateSession', function () {
                self.start();
            });

            if (!_config.idle || !_config.timeout) {
                throw new Error('ky3p.idle module need to be configured!', 'Current config is', _config);
            }

            /**
             * @ngdoc
             * @name idle#start
             * @methodOf idle
             *
             * @description
             * Method to start/restart session expiration countdown
             */
            function start() {
                $timeout.cancel(_idleTimeout);
                $timeout.cancel(_timeoutTimeout);

                _idleTimeout = $timeout(function () {
                    _logout();
                }, _config.idle);

                _timeoutTimeout = $timeout(function () {
                    _showWarningModal();
                }, _config.idle - _config.timeout);
            }

            /**
             *
             * @private
             *
             * @description
             * Loges user out and redirects him to login page with explanation message
             */
            function _logout() {
                $timeout.cancel(_idleTimeout);
                $timeout.cancel(_timeoutTimeout);
                $http.get('/kyv-web/j_myApplication_logout', {
                    ignoreLoadingBar: true
                });
                //TODO: check if we can change this
                //prevent redirection from '/kyv-web/j_myApplication_logout' caused in kyv-parent/trunk/kyv-web/src/main/src/apps/main/app.js line 95
                //    .success(function () {
                //    $window.location.href = '/kyv-web/index.html?code=logoutmsg=Your session has timed out because of inactivity, please login again';
                //});
                window.location.href = '/kyv-web/index.html?code=logoutmsg=Your session has timed out because of inactivity, please login again';
            }

            /**
             *
             * @private
             *
             * @description
             * Method to update user session
             */
            function _extendSession() {
                User.getLoggedInUserDetail();
            }

            /**
             *
             * @private
             *
             * @description
             * Handles Warning modal dialog
             */
            function _showWarningModal() {
                _modalInstance = $modal.open({
                    templateUrl: './modals/idleWarningModal.html',
                    controller: function ($scope, $modalInstance) {
                        var _interval;
                        $scope.timeout = _config.timeout;
                        $scope.yes = $modalInstance.close;
                        $scope.no = $modalInstance.dismiss;

                        _interval = $interval(function () {
                            $scope.timeout = $scope.timeout - 1000;
                            if ($scope.timeout <= 0) {
                                $interval.cancel(_interval);
                            }
                        }, 1000);
                    }
                });
                _modalInstance.result.then(_extendSession, _logout);
            }
        })
        .run(function (idle) {
            idle.start();
        });
})();