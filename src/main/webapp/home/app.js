var erp = angular
.module('erp', [
                'ngRoute',
                'ui.bootstrap',
                'ui.router',
                'erp.services',
                'customerModule',
                'userModule',
                'masterdataModule',
                ]);

erp.run(['$rootScope', '$location',
         function ( $rootScope, $location) {

	$rootScope.showSidebar = true;

	$rootScope.isSel = function (page) {
		var currentRoute = $location.path().substring(1) || 'home',
		pageR = new RegExp(page + "\/");
		return page === currentRoute || currentRoute.match(pageR)
		? 'selected' : '';
	};


}]
);

erp.config(function ($stateProvider, $urlRouterProvider) {
	$urlRouterProvider.otherwise("/home");
	// Now set up the states
	$stateProvider
	.state('home', {
		// Use a url of "/" to set a state as the "index".
		url: "/home",
	})

	.state('complaint', {
		url: "/complaint/{customerId:[0-9]{1,8}}",
		templateUrl: 'customer/complaint.html',
		controller: "customerController",
		resolve:{
			injectedData: ['$stateParams','customerService', function($stateParams,customerService){
				return customerService.getCustomer($stateParams.customerId);
			}]
		}

	})
	
	.state('newcustomer', {
		url: "/customer/new",
		templateUrl: 'customer/amc.html',
		controller:"customerController",
		resolve:{
			injectedData: ['$stateParams', function($stateParams){
				return null;
			}]
		}
	})

	.state('searchcustomer', {
		url: "/customer",
		templateUrl: 'customer/customerSearch.html',
		controller:"customerController",
		resolve:{
			injectedData: ['$stateParams','customerService', function($stateParams,customerService){
				return null;
			}]
		}
	})
	.state('customer', {
		url: "/customer/{customerId:[0-9]{1,8}}",
		templateUrl: 'customer/amc.html',
		controller: "customerController",
		resolve:{
			injectedData: ['$stateParams','customerService', function($stateParams,customerService){
				return customerService.getCustomer($stateParams.customerId);
			}]
		}
	})

	.state('searchUser', {
		url: "/user",
		templateUrl: 'user/userSearch.html',
		controller:"userController",
		resolve:{
			user: ['$stateParams','userService', function($stateParams,userService){
				return null;
			}]
		}
	})

	.state('user', {
		url: "/user/{userId:[0-9]{1,8}}",
		templateUrl: 'user/user.html',
		controller: "userController",
		resolve:{
			user: ['$stateParams','userService', function($stateParams,userService){
				return userService.getUserwithprivileges($stateParams.userId);
			}]
		}
	})

	.state('createUser', {
		url: "/user/new",
		templateUrl: 'user/user.html',
		controller: "userController",
		resolve:{
			user: ['$stateParams', function($stateParams){
				return null;
			}]
		}
	})

	.state('resetpassword', {
		url: "/passwordReset",
		templateUrl: 'user/resetPassword.html',
		controller: "userController",
		resolve:{
			user: ['$stateParams', function($stateParams){
				return {};
			}]
		}
	});

});


erp.controller('ApplicationController',
		['$scope', '$rootScope', '$timeout', '$modal','$http','$state','userService',
		 function ($scope, $rootScope, $timeout, $modal,$http,$state,userService) {

			$rootScope.user=null;

			$scope.getAuthenticatedUser = function() {
				console
				.log('getting user in app.js');
				userService
				.getAuthenticatedUser()
				.then(
						function(response) {
							if (response) {
								$rootScope.user = response;
								if($rootScope.user.forcePasswordChange){

									$rootScope.curModal = $modal.open({
										templateUrl: 'user/resetPassword.html',
										controller: 'userController',
										resolve: {
											user: ['$stateParams', function($stateParams){
												return angular.copy($rootScope.user);
											}]
										},

										backdrop:false
									});
								}
							} else {
								console.log('error');
							}
						})
			};

			if($rootScope.user==null){

				$scope.getAuthenticatedUser();

			}			

			$rootScope.enableSidebar = true;

			$rootScope.$on('showError', function (o, e, type) {
				if (!$.isEmptyObject($rootScope.curModal)) {
					return;
				}
				$rootScope.curModal = $modal.open({
					templateUrl: 'modals/errorModalContent.html',
					controller: function ($scope) {
						var title = "Failed to Load Data";
						$scope._errorCode = type;
						$scope._errorTitle = title + " (HTTP: " + type + ")";
						$scope._errorMessage = e.data ? e.data : (e.message ? e.message : e);
						$scope.resetCurModal = function(time) {
							$timeout(function () {
								$rootScope.curModal = {};
							}, time || 500);
						};
						$scope.closeErrorModal = function (back) {
							if(back)
								history.go(-1);
							$rootScope.curModal.close();
							$scope.resetCurModal();
						};
					}
				// keyboard: false,
				// backdrop: 'static'
				});
				$rootScope.curModal.result.finally(
						function() {
							$rootScope.curModal = {}
						}
				);
			});

			$scope.newCustomer = function(){
				$state.go("newcustomer");
			}
		}]);

erp.config(['$httpProvider', '$sceProvider',
            function ($httpProvider, $sceProvider) {
	$sceProvider.enabled(false);
	$httpProvider.interceptors.push(
			['$q', '$location', '$rootScope', 'deferredManager',
			 function ($q, $location, $rootScope, deferredManager) {
				return {
					request: function (config) {
						if(config.url.search('/service/') > -1) {
							config.timeout = deferredManager.deferService(config.url);
						}
						return config;
					},
					response: function (response) {
						if(response.data) {
							if (response.data.error) {
								$rootScope.$broadcast('showError', response.data.error || 'Error '+response.status, response.status);
								return $q.reject(response);
							}
						}
						if(response.status == 202) {
							$rootScope.$broadcast('authorized',null, response.status);
						}
						return response;
					},
					responseError: function (response) {
						if(response.status == 401) {
							$rootScope.$broadcast('unauthorized',null, response.status);
							return $q.reject(response);
						} else if (response && [400,403, 404, 405, 415, 500, 501, 502, 503, 504].indexOf(response.status) > -1) {
							$rootScope.$broadcast('showError', response.data.error || 'Error '+response.status, response.status);
						}
						return $q.reject(response);
					}
				};
			}]);
}]);



