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
		 'NgTableParams',
		 'masterdataService',
		 'isCustomerReport',
		 function($scope, $state, $rootScope,reportService,$modal,$http,NgTableParams,masterdataService,isCustomerReport ) {

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

			 if(isCustomerReport){
				 $rootScope.heading='Customer Report'	
			 }

			 $scope.filterCustomerReport = function() {

				 $rootScope.curModal = $modal.open({
					 templateUrl: 'report/ReportCriteria.html',
					 scope:$scope,
					 controller: function (reportService,$scope) {

						 $scope.getCustomerReportByCriteria = function(){
							 $scope.tableParams.reload();
							 $rootScope.curModal.close();
						 }

					 },
				 });
			 };

			 $scope.tableParams = new NgTableParams({}, {
				 getData: function($defer,params) {
					 var sortBy="customerCode";
					 var isAsc=false;
					 var pageNo=params.page();
					 var pageSize=params.count();
					 if(params.sorting()){
						 for (var attribute in params.sorting()) {
							 if (params.sorting().hasOwnProperty(attribute)) {
								 sortBy=attribute;
								 var ascDsc=params.sorting()[attribute];
								 if(ascDsc==='asc'){
									 isAsc=true;  
								 }
							 }
						 }
					 }
					 $scope.customerReportAttribute.pageSize=pageSize;
					 $scope.customerReportAttribute.pageNo=pageNo;
					 $scope.customerReportAttribute.sortBy=sortBy;
					 $scope.customerReportAttribute.isAscending=isAsc;
					 reportService.getCustomerReportByCriteria($scope.customerReportAttribute)
					 .then(
							 function(response) {
								 if(response){
									 params.total(response.totalCount);
									 $defer.resolve(response.objectData);
								 }
							 })

				 }
			 });

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
