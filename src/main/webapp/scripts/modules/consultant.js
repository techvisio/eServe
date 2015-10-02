var consultantModule = angular.module('consultantModule', []);

consultantModule.controller('consultantController', ['$scope','consultantService','masterdataService',function($scope,consultantService,masterdataService) {

	// Data variables.
	$scope.form={};
	$scope.consultantAdmissionDetail={};
	$scope.consultantAdmissionDetail.consultantDetails=[];
	$scope.consultantAdmissionDetail.consultantDetails.push(angular.copy($scope.dummyConsultantDetails));

	$scope.searchCriteria={};
	$scope.dueEnquiries=[];
	$scope.consultant={};
	$scope.consultantList=[];
	$scope.searchRes=[];
	$scope.searchResultList=[];
	$scope.filteredSearch=[];
	$scope.form.content='dashboard';
	// Variables for show and hiding.
	$scope.processing=false;
	$scope.showCriteria=false;
	$scope.form.isNew=true;
	$scope.form.isEdit=false;
	$scope.dashboard=true;
	$scope.addNew=false;
	$scope.getConslt=false;
	$scope.searchResult=false;
	$scope.searchType="STUDENT";


	$scope.dummyConsultantDetails = {"consultant":{},"consultantPaymentCriterias" : [ {} ], "consultantPaymentDetail" : [ {} ]};

	$scope.dummyConsultantPaymentCriterias = {};

	$scope.dummyConsultantPaymentDetail = {};

	$scope.itemsPerPage = 3;
	$scope.currentPage = 0;
	$scope.totalItems = 0;

	$scope.pageCount = function () {
		return Math.ceil($scope.searchResultList.length / $scope.itemsPerPage);
	};

	$scope.numPages = function () {
		return Math.ceil($scope.searchResultList.length / $scope.numPerPage);
	};

	$scope.$watch('currentPage + numPerPage', function() {
		var begin = (($scope.currentPage - 1) * $scope.itemsPerPage)
		, end = begin + $scope.itemsPerPage;

		$scope.filteredSearch = $scope.searchResultList.slice(begin, end);
	});

	$scope.gridOptions = {
			multiSelect:false,
			data: 'filteredSearch',
			rowTemplate: '<div ng-dblclick="getConsultantAdmissionDetail(row.config.selectedItems[0].fileNo)" ng-style="{\'cursor\': row.cursor, \'z-index\': col.zIndex() }" ng-repeat="col in renderedColumns" ng-class="col.colIndex()" class="ngCell {{col.cellClass}}" ng-cell></div>',
			columnDefs: [{ field: "firstName", width: 100,displayName :"FirstName"},
			             { field: "lastName", width: 100,displayName :"LastName"},
			             { field: "fatherName", width: 180,displayName :"Father Name" },
			             { field: "course.course", width: 140,displayName :"Course" },
			             { field: "branch.branchName", width: 180,displayName :"Branch" },
			             {field:"applicationStatus",width:200,displayName :"Status"}]
	};

	$scope.consultantGridOptions = {
			multiSelect:false,
			data: 'searchRes',
			columnDefs: [{ field: "name", width: 100,displayName :"Consultant Name"},
			             { field: "primaryContactNo", width: 180,displayName :"Primary Contact No" },
			             { field: "secondaryContactNo", width: 140,displayName :"Secondary Contact No" },
			             { field: "emailId", width: 180,displayName :"Email Id" },
			             {field:"address",width:200,displayName :"Address"}]
	};

	$scope.getStudentByCriteria = function() {
		console.log('get student by search criteria in controller');
		console.log($scope.searchCriteria);
		$scope.processing=true;
		$scope.currentPage=0;
		consultantService.getStudentByCriteria($scope.searchCriteria)
		.then(function(response) {
			console.log('get student Data received from service in controller : ');
			console.log(response);
			if (response != null && response.data != null && response.data.responseBody != null) {
				$scope.searchResultList=response.data.responseBody;
				$scope.consultantSearch=false;
				$scope.studentSearch=false;
				$scope.currentPage=1;
				$scope.totalItems = $scope.searchResultList.length;
			} else {
				console.log(response.data.error);
				alert(response.data.error);
			}

			$scope.processing=false;
		})
	}


	$scope.addConsultantPaymentCriteria = function(object) {
		var consultantPaymentCriteria = angular
		.copy($scope.dummyConsultantPaymentCriterias);
		consultantPaymentCriteria.consultantId = object.consultant.consultantId;
		object.consultantPaymentCriterias
		.push(consultantPaymentCriteria);
	};

	$scope.removeConsultantPaymentCriteria = function(object, index) {
		console.log(index);
		object.consultantPaymentCriterias.splice(index, 1);
	};

	$scope.addConsultantPaymentDtl = function(object) {
		var consultantPaymentDtl = angular
		.copy($scope.dummyConsultantPaymentDetail);
		consultantPaymentDtl.consultantId = object.consultant.consultantId;
		object.consultantPaymentDetail
		.push(consultantPaymentDtl);
	};

	$scope.removeConsultantPaymentDtl = function(object, index) {
		console.log(index);
		object.consultantPaymentDetail.splice(index, 1);
	};

	$scope.getConsultantByCriteria = function(){
		consultantService.getConsultantByCriteria($scope.searchCriteria)
		.then(function(response) {
			console.log('Data searched in controller : ');
			console.log(response);
			if (response != null && response.data != null && response.data.responseBody != null) {
				$scope.searchRes = response.data.responseBody;
				$scope.consultantSearch=false;
				$scope.studentSearch=false;
			} else {
				console.log(response.data.error);
				alert(response.data.error);
			}

		})
		searchResult = true;
	}

	$scope.saveConsultant=function(){
		if($scope.consultant.consultantId){
			$scope.updateConsultant();
		}else{
			$scope.addConsultant();
		}
	}

	$scope.init=function(){

		console.log('getting masterdata for admission module in init block');

		masterdataService.getAdmissionMasterData()
		.then(function(data) {
			console.log(data);
			if (data != null) {
				$scope.serverModelData = data;
			} else {
				console.log('error');
			}
		})
	};

	$scope.addConsultant = function() {
		console.log('add consultant called');
		consultantService.addConsultant($scope.consultant)
		.then(function(response) {
			console.log('Data received from service : ');
			console.log(response);
			if (response != null && response.data != null && response.data.responseBody != null) {
				$scope.consultant = response.data.responseBody;

				alert("Your Records Saved Successfully")
			} else {
				console.log(response.data.error);
				alert(response.data.error);
			}
		})
	};

	$scope.updateConsultant=function(){
		consultantService.updateConsultant($scope.consultant);
	}

	$scope.getConsultantAdmissionDetail = function(fileNo){

		console.log('getConsultantAdmissionDetail called in controller');
		consultantService.getConsultantAdmissionDetail(fileNo)
		.then(function(response) {
			console.log('consultantAdmission Data received from service : ');
			console.log(response);
			if (response != null && response.data != null && response.data.responseBody != null) {

				$scope.consultantAdmissionDetail = response.data.responseBody;
				$scope.form.content='consultantAdmission';
			} 
		})
	};

	$scope.addConsultantAdmissionDetail = function(){
		console.log('addConsultantAdmissionDetail called in controller');
		consultantService.addConsultantAdmissionDetail($scope.consultantAdmissionDetail)
		.then(function(response) {
			console.log('addConsultantAdmissionDetail Data received from service : ');
			console.log(response);
			if (response != null && response.data != null && response.data.responseBody != null) {
				$scope.consultantAdmisssulkionDetail = response.data.responseBody;
				alert("Your Records Saved Successfully")
			} 
		})
	};

	$scope.resetForm = function(){
		consultant = {};
	}

	$scope.showDetail = function(index){
		var consoltantId = $scope.searchRes[index].consultantId;
		consultantService.getConsultant(consoltantId)
		.then(function(response) {
			console.log('Data received by consultantID in controller : ');
			console.log(response);
			if (response != null && response.data != null && response.data.responseBody != null) {
				$scope.consultant = response.data.responseBody;

			} else {
				console.log(response.data.error);
				alert(response.data.error);
			}

		})
		searchResult = false;
		addNew = true;
		form.isEdit=true;
	}

} ]);

consultantModule.service('consultantService', function($http, $q) {

	// Return public API.
	return ({
		addConsultant  : addConsultant,
		getConsultantByCriteria : getConsultantByCriteria,
		getStudentByCriteria : getStudentByCriteria,
		updateConsultant : updateConsultant,
		getConsultant : getConsultant,
		getConsultantAdmissionDetail : getConsultantAdmissionDetail,
		addConsultantAdmissionDetail : addConsultantAdmissionDetail
	});

	function addConsultant(consultant){
		console.log('add new enquiry');
		var request = $http({
			method : "post",
			url : "consultant/consultantMaster",
			params : "",
			data: consultant

		});

		return (request.then(handleSuccess, handleError));
	}

	function getConsultant(consultantId){
		console.log('get due enquiries');
		var request = $http({
			method : "get",
			url : "consultant/consultantMaster/"+consultantId,
			params : "",
			data: ""

		});

		return (request.then(handleSuccess, handleError));
	}

	function getConsultantByCriteria(searchCriteria){
		console.log('search enquiries');
		var request = $http({
			method : "post",
			url : "consultant/search/",
			params : "",
			data: searchCriteria

		});

		return (request.then(handleSuccess, handleError));
	}

	function addConsultant(consultant){
		console.log('add new enquiry');
		var request = $http({
			method : "post",
			url : "consultant/consultantMaster/",
			params : "",
			data: consultant

		});

		return (request.then(handleSuccess, handleError));
	}

	function updateConsultant(consultant){
		console.log('update consultant called in service');
		var request = $http({
			method : "put",
			url : "consultant/consultantMaster/",
			params : "",
			data: consultant

		});

		return (request.then(handleSuccess, handleError));
	}

	function getConsultantAdmissionDetail(fileNo){
		console.log('getConsultantAdmissionDetail called in service')
		var request = $http({
			method : "get",
			url : "consultant/consultantAdmission/"+fileNo,
			params : {
				action : "get"
			}
		});

		return (request.then(handleSuccess, handleError));
	}

	function addConsultantAdmissionDetail(consultantAdmissionDetail){
		console.log('addConsultantAdmissionDetail called in service');
		var request = $http({
			method : "post",
			url : "consultant/consultantAdmission/",
			params : "",
			data: consultantAdmissionDetail

		});

		return (request.then(handleSuccess, handleError));

	}

	function getStudentByCriteria(searchCriteria){

		console.log('Getting student by search criteria in service');
		var request = $http({
			method : "post",
			url : "consultant/searchStudent/",
			params : "",
			data : searchCriteria

		});

		return (request.then(handleSuccess, handleError));
	}


	function handleError(response) {
		console.log('Error occured while calling service');
		console.log(response);
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