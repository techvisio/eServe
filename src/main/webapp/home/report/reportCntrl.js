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
		 '$http',
		 'masterdataService',
		 function($scope, $state, $rootScope,reportService,$modal,$http,masterdataService) {

			 $scope.customerReportAttribute = {};

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
							 $scope.getPagedDataAsync($scope.customerReportAttribute.pageSize,$scope.customerReportAttribute.pageNo);
							 $rootScope.curModal.close();
						 }

					 },
				 });
			 };

			 $scope.filterOptions = {
					 filterText: "",
					 useExternalFilter: true
			 }; 
			 $scope.totalServerItems = {};
			 $scope.pagingOptions = {
					 pageSizes: [2,3,5,10, 20, 50],
					 pageSize: 2,
					 currentPage: 1
			 };	
			 $scope.setPagingData = function(data, page, pageSize){	
				 //var pagedData = data.slice((page - 1) * pageSize, page * pageSize);
				 $scope.customerReports = data.objectData;
				 $scope.totalServerItems = data.totalCount;
				 if (!$scope.$$phase) {
					 $scope.$apply();
				 }
			 };
			 $scope.getPagedDataAsync = function (pageSize, pageNo) {
				 $scope.customerReportAttribute.pageSize=pageSize;
				 $scope.customerReportAttribute.pageNo=pageNo;
				 reportService.getCustomerReportByCriteria($scope.customerReportAttribute)
				 .then(
						 function(response) {
							 if(response){
								 $scope.customerReports = response;
								 $scope.setPagingData(response,pageNo,pageSize);
							 }
						 })

			 };

			 $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);

			 $scope.$watch('pagingOptions', function (newVal, oldVal) {
				 if ((newVal !== oldVal && newVal.currentPage !== oldVal.currentPage) || newVal !== oldVal && newVal.pageSize !== oldVal.pageSize) {
					 $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, $scope.filterOptions.filterText);
				 }
			 }, true);
			 $scope.$watch('filterOptions', function (newVal, oldVal) {
				 if (newVal !== oldVal) {
					 $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, $scope.filterOptions.filterText);
				 }
			 }, true);

			 // Watch for sorting option change
			 $scope.$watch('gridOptions.sortInfo', function (newVal, oldVal) {
				 if (newVal !== oldVal) {
					 if (newVal.fields != oldVal.fields || newVal.directions != oldVal.directions) {
						 $scope.customerReportAttribute.sortBy=newVal.fields[0];
						 if(newVal.directions=='asc'){
							 $scope.customerReportAttribute.isAscending=true;
						 }else{
							 $scope.customerReportAttribute.isAscending=false;
						 }
					 }
					 $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, $scope.filterOptions.filterText);
				 }
			 }, true);  

			 $scope.gridOptions = {
					 multiSelect:false,
					 data: 'customerReports',
					 sortInfo: { fields: [], columns: [], directions: [] },
					 useExternalSorting: true,
					 showFooter: true,
					 totalServerItems: 'totalServerItems',
					 columnDefs: [


					              { field: "customerCode", width: 100,displayName :"Customer Code"},
					              { field: "customerName", width: 105,displayName :"Customer Name"},
					              { field: "customerType", width: 100,displayName :"Customer Type"},
					              { field: "contactNo", width: 100,displayName :"Contact No"},
					              { field: "emailId", width: 140,displayName :"Email Id" },
					              { field: "unitCode", width: 180,displayName :"Unit Code" },
					              { field: "unitType", width: 140,displayName :"Unit Type" },
					              { field: "assetNo", width: 100,displayName :"Asset No"},
					              { field: "machineSerialNo", width: 140,displayName :"Machine Serial No" },
					              { field: "modelNo", width: 140,displayName :"Model No" },
					              { field: "serviceCategory", width: 140,displayName :"Service Category" },
					              { field: "contractStartOn", width: 150,displayName :"Contract Start On"},
					              { field: "contractExpireOn", width: 150,displayName :"Contract Expire On"},
					              { field: "approvalStatus", width: 105,displayName :"Approval Status"},
					              { field: "lastApprovalDate", width: 180,displayName :"Last Approval Date" },					              
					              { field: "lastApprovedBy", width: 100,displayName :"Last Approved By"}]
			 };

			 $scope.downloadReport=function(reportName){
				 $http({
						method : "post",
						url : "../service/report/downloadReport/"+reportName,
						params : "",
						data : $scope.customerReportAttribute,
						responseType: 'arraybuffer'
					}).success(function (data, status, headers, config) {
			            var blob = new Blob([data], {type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"});
			            var objectUrl = URL.createObjectURL(blob);
			            var anchor=document.createElement("a");
			            anchor.download="Customer Unit Report";
			            anchor.href=objectUrl;
			            anchor.click();
			            //window.open(objectUrl);
			        }).error(function (data, status, headers, config) {
			            console.log('Failed to download Excel')
			        });
			 };

		 } ]);
