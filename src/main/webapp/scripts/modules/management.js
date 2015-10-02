
var managementModule = angular.module('managementModule', ['ui.bootstrap']);

managementModule.controller('managementController',['$scope','managementService', function($scope,managementService) {
	
	$scope.unapprovedRecords=[];
	$scope.currentFetchLimit=20;
	$scope.admissionDetailManagement={};
	$scope.content="dashboard";
	$scope.feeTransactionAdmissionBean = {};
	$scope.fileNo=null;
	
//	$scope.getAdmissionDetailsManagement = function(){
//		var fileNo = $scope.feeTransactionAdmissionBean.basicInfo.fileNo;
//		managementService.getPreviousBalace(fileNo).then(function(response) {
//			 console.log('get privious balance data received from service : ');
//			 console.log(response);
//			 if (response != null && response.data != null && response.data.responseBody != null) {
//				 $scope.feeTransactionAdmissionBean = response.data.responseBody;
//			 } else {
//				 console.log(response.data.error);
//			 }
//		 })
//};

//	$scope.form.fileNo=$scope.feeTransactionAdmissionBean.studentBasicInfo.fileNo;
	$scope.managementData={};
	
	

}]);


