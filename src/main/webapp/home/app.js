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
                'workItemModule',
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

	.state('workItemSearch',{

		url: "/workitem",
		templateUrl: 'workitem/workItem.html',
		controller:"workItemController",
		resolve:{
			workitem: ['$stateParams', function($stateParams){
				return null;
			}]
		}
	})

//	.state('logout', {
//	url: "/loggedout",
//	templateUrl: 'index.html',
//	})

	.state('createNewComplaint', {
		url: "/complaint",
		templateUrl: 'complaint/Complaint.html',
		controller:"complaintController",
		resolve:{
			contextObject: ['$stateParams', function($stateParams){
				return null;
			}],

			Operation: ['$stateParams', function($stateParams){
				return 'createNewComplaint';
			}]
		}
	})

	.state('createNewCustomer', {
		url: "/customer/new",
		templateUrl: 'customer/Create_Customer_Wizard.html',
		controller:"customerController",
		resolve:{
			contextObject: ['$stateParams', function($stateParams){
				return null;
			}],
			Operation: ['$stateParams', function($stateParams){
				return "createNewCustomer";
			}]
		}
	})

	.state('newComplaintFromUnit', {
		url: "/complaint/unit/{entityId:[0-9]{1,8}}",
		templateUrl: 'complaint/Complaint.html',
		controller: "complaintController",
		resolve:{
			contextObject: ['$stateParams','complaintService', function($stateParams,complaintService){
				return complaintService.getUnitForComplaint($stateParams.entityId);
			}],
			Operation: ['$stateParams', function($stateParams){
				return 'newComplaintFromUnit';
			}]
		}
	})

	.state('viewComplaint', {
		url: "/complaint/{entityId:[0-9]{1,8}}",
		templateUrl: 'complaint/Complaint.html',
		controller: "complaintController",
		resolve:{

			contextObject: ['$stateParams','complaintService', function($stateParams,complaintService){
				return complaintService.getWorkOrder($stateParams.entityId);
			}],
			Operation: ['$stateParams', function($stateParams){
				return 'viewComplaint';
			}]
		}
	})

	.state('pmsComplaint', {
		url: "/complaint/{entityId:[0-9]{1,8}}",
		templateUrl: 'complaint/Complaint.html',
		controller: "complaintController",
		resolve:{

			contextObject: ['$stateParams','complaintService', function($stateParams,complaintService){
				return complaintService.getWorkOrder($stateParams.entityId);
			}],
			Operation: ['$stateParams', function($stateParams){
				return 'pmsComplaint';
			}]
		}
	})

	.state('searchCustomer', {
		url: "/customer",
		templateUrl: 'customer/Customer_Search.html',
		controller:"customerController",
		resolve:{
			contextObject: ['$stateParams','customerService', function($stateParams){
				return null;
			}],
			Operation: ['$stateParams', function($stateParams){
				return 'searchCustomer';
			}]
		}
	})

	.state('searchComplaint', {
		url: "/search/complaint",
		templateUrl: 'complaint/Complaint_Search.html',
		controller:"complaintController",
		resolve:{
			contextObject: ['$stateParams', function($stateParams){
				return null;
			}],
			Operation: ['$stateParams', function($stateParams){
				return 'searchComplaint';
			}]
		}
	})

	.state('viewCustomer', {
		url: "/customer/{entityId:[0-9]{1,8}}",
		templateUrl: 'customer/Customer_Main.html',
		controller: "customerController",
		resolve:{
			contextObject: ['$stateParams','customerService', function($stateParams,customerService){
				return customerService.getCustomer($stateParams.entityId);
			}],
			Operation: ['$stateParams', function($stateParams){
				return 'viewCustomer';
			}]
		}
	})

	.state('unitApproval', {
		url: "/approval/unit/{entityId:[0-9]{1,8}}",
		templateUrl: 'customer/Unit_Detail_Single.html',
		controller: "customerController",
		resolve:{
			contextObject: ['$stateParams','customerService', function($stateParams,customerService){
				return customerService.getUnitForApproval($stateParams.entityId);
			}],
			Operation: ['$stateParams', function($stateParams){
				return 'unitApproval';
			}]
		}
	})
	.state('renewSalesAgreement', {
		url: "/renew/unit/{entityId:[0-9]{1,8}}",
		templateUrl: 'customer/Unit_Detail_Single.html',
		controller: "customerController",
		resolve:{
			contextObject: ['$stateParams','customerService', function($stateParams,customerService){
				return customerService.getUnitForApproval($stateParams.entityId);
			}],
			Operation: ['$stateParams', function($stateParams){
				return 'renewSalesAgreement';
			}]
		}
	})
	.state('workitem', {
		url: "/workitem/{workitemId:[0-9]{1,8}}",
		templateUrl: 'workitem/workItemReminder.html',
		controller: "workItemController",
		resolve:{
			workitem : ['$stateParams','workItemService', function($stateParams,workItemService){
				return workItemService.getWorkItemByWorkitemId($stateParams.workitemId);
			}]
		}
	})
	.state('searchUser', {
		url: "/user",
		templateUrl: 'user/userSearch.html',
		controller:"userController",
		resolve:{

			contextObject: ['$stateParams', function($stateParams){
				return null;
			}],
			Operation: ['$stateParams', function($stateParams){
				return 'SearchUser';
			}]
		}
	})

	.state('viewUser', {
		url: "/user/{entityId:[0-9]{1,8}}",
		templateUrl: 'user/user.html',
		controller: "userController",
		resolve:{

			contextObject: ['$stateParams','userService', function($stateParams,userService){
				return userService.getUserwithprivileges($stateParams.entityId);
			}],
			Operation: ['$stateParams', function($stateParams){
				return 'viewUser';
			}]
		}
	})

	.state('createNewUser', {
		url: "/user/new",
		templateUrl: 'user/user.html',
		controller: "userController",
		resolve:{
			contextObject: ['$stateParams', function($stateParams){
				return null;
			}],
			Operation: ['$stateParams', function($stateParams){
				return 'createNewUser';
			}]
		}
	})

	.state('resetpassword', {
		url: "/passwordReset",
		templateUrl: 'user/resetPassword.html',
		controller: "userController",
		resolve:{
			contextObject: ['$stateParams', function($stateParams){
				return null;
			}],
			Operation: ['$stateParams', function($stateParams){
				return 'resetpassword';
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
		['$scope', '$rootScope', '$timeout', '$modal','$http','$state','userService','alertModal','confirmModal',
		 function ($scope, $rootScope, $timeout, $modal,$http,$state,userService,alertModal,confirmModal) {

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

											contextObject: ['$stateParams', function($stateParams){
												return angular.copy($rootScope.user);
											}],
											Operation: ['$stateParams', function($stateParams){
												return 'passresetmodal';
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
						var title = "Operation Failed";
						//$scope._errorCode = type;
						$scope._errorTitle = title ;//+ " (HTTP: " + type + ")";
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

			$rootScope.$on('$stateChangeStart', 
					function(event, toState, toParams, fromState, fromParams, options){
				$rootScope.heading = 'Dashboard';

				if(toState.name=='createNewCustomer'){
					$rootScope.heading = 'Create Customer';
				}
				if(toState.name=='searchCustomer'){
					$rootScope.heading = 'Search Customer';
				}
				if(toState.name=='viewCustomer'){
					$rootScope.heading = 'Customer';
				}
				if(toState.name=='unitApproval'){
					$rootScope.heading = 'Unit Approval';
				}
				if(toState.name=='renewSalesAgreement'){
					$rootScope.heading = 'Renew Agreement';
				}
				if(toState.name=='createNewComplaint'){
					$rootScope.heading = 'Create Complaint';
				}
				if(toState.name=='newComplaintFromUnit'){
					$rootScope.heading = 'Create Complaint';
				}
				if(toState.name=='viewComplaint'){
					$rootScope.heading = 'Complaint';
				}
				if(toState.name=='searchComplaint'){
					$rootScope.heading = 'Search Complaint';
				}
				if(toState.name=='searchUser'){
					$rootScope.heading = 'Search user';
				}
				if(toState.name=='viewUser'){
					$rootScope.heading = 'User';
				}
				if(toState.name=='createNewUser'){
					$rootScope.heading = 'Create user';
				}
				if(toState.name=='complaintGraph'){
					$rootScope.heading = 'Dashboard';
				}
				if(toState.name=='customerReport'){
					$rootScope.heading = 'Customer Report';
				}
				if(toState.name=='workItemSearch'){
					$rootScope.heading = 'Search Workitem';
				}
				if(toState.name=='workitem'){
					$rootScope.heading = 'workitem';
				}
			});

			$scope.logout=function(){

				window.location=document.getElementById('logout').href;
			}


			$rootScope.isPrivileged = function(role){

				var result=false;
				if($rootScope.user && $rootScope.user.privileges){
					var userPrivilege = $rootScope.user.privileges;
					angular.forEach(userPrivilege, function(privilege) {
						if (privilege.privilege.privilege===role) result= true;
					});
				}
				return result;		
			}

			$rootScope.showNotHavePrivilegeModel = function(){
				alertModal.showAlert('You dont have required permission. Please contact system administrator','Permission Denied');
			}

			$rootScope.showAlertModel = function(content, title){
				alertModal.showAlert(content,title);
			};

			$rootScope.showConfirmModal = function(content, title){
				confirmModal.showConfirmModal(content,title);
			};

			$rootScope.redirectToComplaint=function(){
				if($rootScope.isPrivileged("CREATE_COMPLAINT(PAID ONLY)")){
					$state.go('createNewComplaint');
				}
				else{
					$rootScope.showNotHavePrivilegeModel();
				}		

			}

			$scope.createUser=function(){

				if($rootScope.isPrivileged('CREATE_USER')){
					$state.go('createNewUser');
				}
				else{
					$rootScope.showNotHavePrivilegeModel();
				}
			}
			$scope.createCustomer=function(){
				if($rootScope.isPrivileged('CREATE_CUSTOMER')){
					$state.go('createNewCustomer');
				}
				else{
					$rootScope.showNotHavePrivilegeModel();
				}
			}

			$rootScope.closeModal=function(){
				$rootScope.curModal.close();
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



