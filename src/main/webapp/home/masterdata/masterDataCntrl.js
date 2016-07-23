var masterModule = angular.module('masterModule', []);

masterModule
.controller(
		'masterController',
		[
		 '$scope',
		 '$state',
		 '$rootScope',
		 'masterService',
		 '$modal',
		 '$http',
		 'NgTableParams',
		 'masterdataService',
		 function($scope, $state, $rootScope,masterService,$modal,$http,NgTableParams,masterdataService) {
			 
			 $scope.init = function() {
				 console
				 .log('getting masterdata for customer');

				 masterdataService
				 .getCustomerMasterData()
				 .then(
						 function(data) {
							 console.log(data);
							 if (data) {
								 $scope.serverModelData = data.responseBody;
							 } else {
								 console.log('error');
							 }
						 });
			 };
			 
			 $scope.tableParams = new NgTableParams({}, {
			      getData: function($defer,params) {
			    	  masterService.getClientMasterData()
						 .then(
								 function(response) {
									 if(response){
										 params.total(response.totalCount);
										 $defer.resolve(response.objectData);
									 }
								 })
			    	 
			      }
			    });
			
		 
		 
		 
		 } ]);
		 