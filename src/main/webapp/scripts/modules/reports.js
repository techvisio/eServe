var reportsModule = angular.module('reportsModule', []);

reportsModule.controller('reportsController', ['$scope','reportsService',function($scope,reportsService) {

	$scope.dashboard = true;
	$scope.enquiryRpt = false;
	$scope.constRpt=false;
	$scope.admssnRpt=false;
	$scope.admissionReport= [];
	$scope.enquiryreportCriteria={};

	$scope.consultantReport=[];
	$scope.studentData =[];
	$scope.reportType="ENQUIRY";
	
	$scope.enquiryGridOptions = { data: 'enquiryReport.enquiryReports',
			showGroupPanel: true ,
			columnDefs: [{ field: "name", width: 120,displayName :"Student Name"},
	                     { field: "fatherName", width: 120,displayName :"Father Name"},
	                    { field: "course", width: 100,displayName :"Course" },
	                    { field: "branch", width: 200,displayName :"Branch" },
	                    { field: "contactNo", width: 150,displayName :"Contact No." },
	                    {field:"applicationStatus",width:200,displayName :"Status"},
	                    {field:"createdDate",width:150,displayName :"Created On"}
	                    ]
};

	$scope.admissionGridOptions = { data: 'admissionReport.admissionReports',
			showGroupPanel: true ,
			columnDefs: [{ field: "registrationNo", width: 200,displayName :"Registration No"},
			             { field: "firstName", width: 100,displayName :"First Name"},
			             { field: "lastName", width: 100,displayName :"Last Name"},
	                     { field: "fatherName", width: 200,displayName :"Father Name"},
	                     { field: "gender", width: 50,displayName :"Gender"},
	                     { field: "emailId", width: 200,displayName :"Email Id"},
	                    { field: "course", width: 100,displayName :"Course" },
	                    { field: "branch", width: 180,displayName :"Branch" },
	                    { field: "referredBy", width: 180,displayName :"Referred By" },
	                    { field: "consultantName", width: 180,displayName :"Consultant Name" },
	                    { field: "selfMobileNo", width: 150,displayName :"Contact No." },
	                    {field:"applicationStatus",width:200,displayName :"Status"},
	                    {field:"createdOn",width:150,displayName :"Created On"},
	                    {field:"discountAmount",width:200,displayName :"Discounted Amount"},
	                    {field:"feeDeposite",width:200,displayName :"Deposited Amount"},
	                    {field:"consultantPayment",width:250,displayName :"Payment To Consultant"},
	                    {field:"code",width:250,displayName :"Quota Code"}
	                    
	                    ]
};

	
	$scope.getEnquiryReportByCriteria = function() {
		console.log('get enquiry report by search criteria in controller');
		reportsService.getEnquiryReportByCriteria($scope.enquiryreportCriteria)
		.then(function(response) {
			console.log('enquiry report Data received from service in controller : ');
			console.log(response);
			if (response != null && response.data != null && response.data.responseBody != null) {
				$scope.enquiryReport = response.data.responseBody;
			}
			else {
				console.log(response.data.error);
				alert(response.data.error);
			}
		})
	}
	
	$scope.getAdmissionReportByCriteria = function() {
		console.log('get admission report by search criteria in controller');
		reportsService.getAdmissionReportByCriteria($scope.enquiryreportCriteria)
		.then(function(response) {
			console.log('admission report Data received from service in controller : ');
			console.log(response);
			if (response != null && response.data != null && response.data.responseBody != null) {
				$scope.admissionReport = response.data.responseBody;
				$scope.enquiryRpt = false;
			}
			else {
				console.log(response.data.error);
				alert(response.data.error);
			}
		})
	}

		
	$scope.downloadReport=function(reportName){
		reportsService.downloadReport(reportName);
	}



} ]);

reportsModule.service('reportsService', function($http, $q) {

	// Return public API.
	return ({
		getConsultantReport : getConsultantReport,
		getEnquiryReportByCriteria : getEnquiryReportByCriteria ,
		downloadReport:downloadReport,
		getAdmissionReportByCriteria : getAdmissionReportByCriteria
	});

	function getEnquiryReportByCriteria(enquiryreportCriteria){

		console.log('Getting enquiry report by search criteria in service');
		var request = $http({
			method : "post",
			url : "report/searchEnquiryReportByCriteria/",
			params : "",
			data : enquiryreportCriteria

		});

		return (request.then(handleSuccess, handleError));
	}


	function getConsultantReport() {

		console.log('getConsultantReport called in service');
		var request = $http({
			method : "get",
			url : "report/consutantReport/",
			params : {
				action : "get"
			}
		});

		return (request.then(handleSuccess, handleError));

	}

	function getAdmissionReportByCriteria(enquiryreportCriteria){

		console.log('Getting admission report by search criteria in service');
		var request = $http({
			method : "post",
			url : "report/searchAdmissionReportByCriteria/",
			params : "",
			data : enquiryreportCriteria

		});

		return (request.then(handleSuccess, handleError));
	}

	function downloadReport(reportName){
		var request = $http({
			method : "get",
			url : "report/downloadEnquiryReport/"+reportName,
			params : {
				action : "get"
			}
		});
	}

	function handleError(response) {
		console.log('handle error');
		console.log(response);
		// The API response from the server should be returned in a
		// nomralized format. However, if the request was not handled by the
		// server (or what not handles properly - ex. server error), then we
		// may have to normalize it on our end, as best we can.
		if (!angular.isObject(response.data) || !response.data.message) {

			return ($q.reject("An unknown error occurred."));

		}

		// Otherwise, use expected error message.
		return ($q.reject(response.data.message));

	}

	// I transform the successful response, unwrapping the application data
	// from the API response payload.
	function handleSuccess(response) {
		console.log('handle success');
		console.log(response);
		return (response);

	}

});