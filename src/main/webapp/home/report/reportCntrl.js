var reportModule = angular.module('reportModule', []);

reportModule
.controller(
		'reportController',
		[
		 '$scope',
		 '$state',
		 '$rootScope',
		 'reportService',
		 '$modal',
		 'masterdataService',
		 function($scope, $state, $rootScope,reportService,$modal,masterdataService) {

			 $scope.customerReportAttribute = {};
			 $scope.customerReports = [];
			 $scope.startDate;
			 $scope.endDate;


			 $scope.searchResultList=[];
			 $scope.filteredSearch=[];
			 $scope.itemsPerPage = 3;
			 $scope.currentPage = 0;
			 $scope.totalItems = 0;

			 $scope.pageCount = function () {
				 return Math.ceil($scope.customerReports.length / $scope.itemsPerPage);
			 };

			 $scope.numPages = function () {
				 return Math.ceil($scope.customerReports.length / $scope.numPerPage);
			 };

			 $scope.$watch('currentPage + numPerPage', function() {
				 var begin = (($scope.currentPage - 1) * $scope.itemsPerPage)
				 , end = begin + $scope.itemsPerPage;

				 $scope.filteredSearch = $scope.searchResultList.slice(begin, end);
			 });



			 $scope.currentPage=1;
			 $scope.totalItems = $scope.searchResultList.length;



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



			 $scope.filterCustomerReport = function() {

				 $rootScope.curModal = $modal.open({
					 templateUrl: 'report/ReportCriteria.html',
					 scope:$scope,
					 controller: function (reportService,$scope) {

						 $scope.getCustomerReportByCriteria = function(){

							 reportService.getCustomerReportByCriteria($scope.customerReportAttribute)
							 .then(
									 function(response) {
										 if(response){
											 $scope.customerReports = response;
											 $scope.currentPage=1;
											 $scope.totalItems = $scope.customerReports.length;
											 $scope.gridOptions = {
													 multiSelect:false,
													 data: 'customerReports',
													 columnDefs: [{ field: "customerName", width: 100,displayName :"Customer Name"},
													              { field: "customerType", width: 100,displayName :"Customer Type"},
													              { field: "contactNo", width: 180,displayName :"Contact No" },
													              { field: "emailId", width: 140,displayName :"Email Id" },
													              { field: "unitCode", width: 180,displayName :"Unit Code" }]
											 };
											 $rootScope.curModal.close();
										 }
									 })
						 }

					 },
				 });
			 };

		 } ]);
