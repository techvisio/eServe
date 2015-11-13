var erp = angular
.module('erp', [
                'ngRoute',
                'ui.bootstrap',
                'ui.router',
                'erp.services',
                'googlechart',
                'customerModule',
                'userModule',
                'ngTable',
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
		url: "/complaint",
		templateUrl: 'customer/complaint.html'
	})
	.state('newcustomer', {
		url: "/customer/new",
		templateUrl: 'customer/amc.html',
		controller:"customerController",
			resolve:{
				injectedData: ['$stateParams', function($stateParams){
					return {};
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
				return userService.getUser($stateParams.userId);
			}]
		}
	})
	
	.state('createUser', {
		url: "/user",
		templateUrl: 'user/user.html',
		controller: "userController",
		resolve:{
			user: ['$stateParams', function($stateParams){
				return {};
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

erp
.controller("GenericChartCtrl", function ($scope) {
    $scope.chartObject = {};
    
    $scope.chartObject.type = "PieChart";
    
    $scope.chartObject.data = {"cols": [
        {id: "t", label: "Topping", type: "string"},
        {id: "s", label: "Slices", type: "number"}
    ], "rows": [
        {c: [
            {v: "Overdue Previous"},
            {v: 3},
        ]},
        {c: [
            {v: "Due current month"},
            {v: 1},
        ]},
        {c: [
            {v: "Due next month"},
            {v: 2},
        ]}
    ]};

    $scope.chartObject.options = {
    		tooltip:{textStyle:{fontName:'"Arial"'}},
    	      title: 'PMS Schedules Status',titleTextStyle:{fontName:'"Arial"'},
    };
    
 $scope.workOrder = {};
    
    $scope.workOrder.type = "PieChart";
    
    $scope.workOrder.data = {"cols": [
        {id: "t", label: "Open", type: "string"},
        {id: "s", label: "Closed", type: "number"}
    ], "rows": [
        {c: [
            {v: "Opened Today"},
            {v: 3},
        ]},
        {c: [
            {v: "Pending Previous"},
            {v: 1},
        ]}
    ]};

    $scope.workOrder.options = {
    		tooltip:{textStyle:{fontName:'"Arial"'}},
    	      title: 'Open Work Order Status',titleTextStyle:{fontName:'"Arial"'},
    };
});


