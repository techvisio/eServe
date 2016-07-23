var erp = angular
.module('erp', [
                'ngRoute',
                'ui.bootstrap',
                'ui.router',
                'erp.services',
                'customerModule',
                'complaintModule',
                'userModule',
                'masterdataModule',
                'dashboardModule',
                'activityModule',
                'reportModule',
                'masterModule',
                'ngGrid',
                'ngTable'
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
		templateUrl: 'dashboard/dashboard.html',
		controller:"dashboardController",
		resolve:{
			complaint: ['$stateParams', function($stateParams){
				return null;
			}],
			isDashboard: ['$stateParams', function($stateParams){
				return true;
			}]
		}
	})

	.state('activity',{

		url: "/activity",
		templateUrl: 'activity/Activity.html',
		controller:"activityController",
		resolve:{

		}
	})

	.state('workItem',{

		url: "/workitem",
		templateUrl: 'customer/workItem.html',
		controller:"customerController",
		resolve:{
			customer: ['$stateParams', function($stateParams){
				return null;
			}],
			unit: ['$stateParams', function($stateParams){
				return null;
			}],
			isCustomerSearch: ['$stateParams', function($stateParams){
				return 'workItem';
			}]
		}
	})

//	.state('logout', {
//	url: "/loggedout",
//	templateUrl: 'index.html',
//	})

	.state('complaint', {
		url: "/complaint",
		templateUrl: 'complaint/sampleComplaint.html',
		controller:"complaintController",
		resolve:{
			complaint: ['$stateParams', function($stateParams){
				return null;
			}],
			unitComplaint: ['$stateParams', function($stateParams){
				return null;
			}],
			isComplaintSearch: ['$stateParams', function($stateParams){
				return false;
			}]
		}
	})

//	.state('amcMain', {
//	url: "/customer/new",
//	templateUrl: 'customer/amcMain.html',
//	controller:"customerController",
//	resolve:{
//	customer: ['$stateParams', function($stateParams){
//	return null;
//	}],

//	unitApproval: ['$stateParams', function($stateParams){
//	return null;
//	}]
//	}
//	})

	.state('newcustomer', {
		url: "/customer/new",
		templateUrl: 'customer/slideScreenCustomer.html',
		controller:"customerController",
		resolve:{
			customer: ['$stateParams', function($stateParams){
				return null;
			}],
			unit: ['$stateParams', function($stateParams){
				return null;
			}],
			isCustomerSearch: ['$stateParams', function($stateParams){
				return false;
			}]
		}
	})

	.state('customerToComplaint', {
		url: "/complaint/unit/{entityId:[0-9]{1,8}}",
		templateUrl: 'complaint/sampleComplaint.html',
		controller: "complaintController",
		resolve:{
			unitComplaint: ['$stateParams','complaintService', function($stateParams,complaintService){
				return complaintService.getUnitForComplaint($stateParams.entityId);
			}],
			complaint: ['$stateParams', function($stateParams){
				return null;
			}],
			isComplaintSearch: ['$stateParams', function($stateParams){
				return false;
			}]
		}
	})

	.state('complaintScreen', {
		url: "/complaint/{entityId:[0-9]{1,8}}",
		templateUrl: 'complaint/sampleComplaint.html',
		controller: "complaintController",
		resolve:{
			complaint: ['$stateParams','complaintService', function($stateParams,complaintService){
				return complaintService.getCustomerComplaint($stateParams.entityId);
			}],
			unitComplaint: ['$stateParams', function($stateParams){
				return null;
			}],
			isComplaintSearch: ['$stateParams', function($stateParams){
				return false;
			}]
		}
	})

	.state('searchcustomer', {
		url: "/customer",
		templateUrl: 'customer/customerSearch.html',
		controller:"customerController",
		resolve:{
			customer: ['$stateParams','customerService', function($stateParams){
				return null;
			}],
			unit: ['$stateParams', function($stateParams){
				return null;
			}],
			isCustomerSearch: ['$stateParams', function($stateParams){
				return true;
			}]
		}
	})

	.state('searchcomplaint', {
		url: "/search/complaint",
		templateUrl: 'complaint/complaintSearch.html',
		controller:"complaintController",
		resolve:{
			complaint: ['$stateParams', function($stateParams){
				return null;
			}],
			unitComplaint: ['$stateParams', function($stateParams){
				return null;
			}],
			isComplaintSearch: ['$stateParams', function($stateParams){
				return true;
			}]
		}
	})

	.state('customer', {
		url: "/customer/{entityId:[0-9]{1,8}}",
		templateUrl: 'customer/amcMain.html',
		controller: "customerController",
		resolve:{
			customer: ['$stateParams','customerService', function($stateParams,customerService){
				return customerService.getCustomer($stateParams.entityId);
			}],
			unit: ['$stateParams', function($stateParams){
				return null;
			}],
			isCustomerSearch: ['$stateParams', function($stateParams){
				return false;
			}]
		}
	})

	.state('unit', {
		url: "/unit/{entityId:[0-9]{1,8}}",
		templateUrl: 'customer/unitApproval.html',
		controller: "customerController",
		resolve:{
			unit: ['$stateParams','customerService', function($stateParams,customerService){
				return customerService.getUnitForApproval($stateParams.entityId);
			}],
			customer: ['$stateParams', function($stateParams){
				return null;
			}],
			isCustomerSearch: ['$stateParams', function($stateParams){
				return false;
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
			}],
			isUserSearch: ['$stateParams', function($stateParams){
				return true;
			}]
		}
	})

	.state('user', {
		url: "/user/{entityId:[0-9]{1,8}}",
		templateUrl: 'user/user.html',
		controller: "userController",
		resolve:{
			user: ['$stateParams','userService', function($stateParams,userService){
				return userService.getUserwithprivileges($stateParams.entityId);
			}],
			isUserSearch: ['$stateParams', function($stateParams){
				return false;
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
			}],
			isUserSearch: ['$stateParams', function($stateParams){
				return false;
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
			}],

			isUserSearch: ['$stateParams', function($stateParams){
				return false;
			}]
		}
	})

	.state('complaintGraph', {
		url: "/complaintgraph/:type/:code",
		templateUrl: 'dashboard/complaintgraphdata.html',
		controller:"dashboardController",
		resolve:{
			complaint: ['$stateParams','dashboardService', function($stateParams,dashboardService){
				return dashboardService.getComplaintDataforDashboard($stateParams.type,$stateParams.code);
			}]	
		}
	})

	.state('customerReport', {
		url: "/customerreport",
		templateUrl: 'report/CustomerReport.html',
		controller:"reportController",
		resolve:{

			isCustomerReport: ['$stateParams', function($stateParams){
				return true;
			}]
		}
	})
	
	.state('masterData', {
		url: "/masterData",
		templateUrl: 'masterdata/MasterData.html',
		controller:"masterController",
		resolve:{
			
		}
	});
});


erp.controller('ApplicationController',
		['$scope', '$rootScope', '$timeout', '$modal','$http','$state','userService',
		 function ($scope, $rootScope, $timeout, $modal,$http,$state,userService) {

			$rootScope.user=null;
			$rootScope.heading = 'Dashboard';

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
											}],
											isUserSearch: ['$stateParams', function($stateParams){
												return false;
											}]
										},

										backdrop:'static',
//										keyboard: false
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
			$rootScope.processingCount = 0;
			$rootScope.isProcessing = false;

			$rootScope.$on('showError', function (o, e, type) {
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

			$scope.isPrivileged = function(role){

				var result=false;
				if($rootScope.user && $rootScope.user.privileges){
					var userPrivilege = $rootScope.user.privileges;
					angular.forEach(userPrivilege, function(privilege) {
						if (privilege.privilege.privilege===role) result= true;
					});
				}
				return result;		
			}

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
						$rootScope.processingCount=$rootScope.processingCount+1;
						$rootScope.isProcessing = true;
						if(config.url.search('/service/') > -1) {
							config.timeout = deferredManager.deferService(config.url);
						}
						return config;
					},
					response: function (response) {
						$rootScope.processingCount=$rootScope.processingCount-1;
						$rootScope.isProcessing = false;
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
						$rootScope.processingCount=$rootScope.processingCount-1;
						$rootScope.isProcessing = false;
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



