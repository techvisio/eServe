var masterDataModule=angular.module('masterdataModule',[]);

masterDataModule.service('masterdataService', function($http, $q){

	return ({
		getAdmissionMasterData : getAdmissionMasterData,
		getEnquiryMasterData : getEnquiryMasterData,
		getConsultantMasterData : getConsultantMasterData,
		getFeeMasterData : getFeeMasterData,
		getTransportMasterData : getTransportMasterData,
		getHostelMasterData : getHostelMasterData,
		getUserMasterData : getUserMasterData
	});

	function getAdmissionMasterData() {

		console.log('Getting masterdata for user module');
		var request = $http({
			method : "get",
			url : "service/masterdata/user",
			params : "",
			data : ""
		});

		return (request.then(handleSuccess, handleError));

	}

	function getAdmissionMasterData() {

		console.log('Getting masterdata for admission module');
		var request = $http({
			method : "get",
			url : "service/masterdata/admission",
			params : "",
			data : ""
		});

		return (request.then(handleSuccess, handleError));

	}

	function getEnquiryMasterData() {

		console.log('Getting masterdata for enquiry module');
		var request = $http({
			method : "get",
			url : "service/masterdata/enquiry",
			params : "",
			data : ""
		});

		return (request.then(handleSuccess, handleError));

	}

	function getConsultantMasterData() {

		console.log('Getting masterdata for consultant module');
		var request = $http({
			method : "get",
			url : "service/masterdata/consultant",
			params : "",
			data : ""
		});
		return (request.then(handleSuccess, handleError));
	}

	function  getFeeMasterData() {

		console.log('Getting masterdata for fee module');
		var request = $http({
			method : "get",
			url : "service/masterdata/fee",
			params : "",
			data : ""
		});
		return (request.then(handleSuccess, handleError));
	}

	function  getTransportMasterData() {

		console.log('Getting masterdata for transport module');
		var request = $http({
			method : "get",
			url : "service/masterdata/transport",
			params : "",
			data : ""
		});
		return (request.then(handleSuccess, handleError));
	}

	function  getHostelMasterData() {

		console.log('Getting masterdata for hostel module');
		var request = $http({
			method : "get",
			url : "service/masterdata/hostel",
			params : "",
			data : ""
		});
		return (request.then(handleSuccess, handleError));
	}


	function  getUserMasterData() {

		console.log('Getting masterdata for user module');
		var request = $http({
			method : "get",
			url : "service/masterdata/user",
			params : "",
			data : ""
		});
		return (request.then(handleSuccess, handleError));
	}

	function handleError(response) {
		console.log('handle error');
		console.log(response);

		if (!angular.isObject(response.data) || !response.data.message) {

			return ($q.reject("An unknown error occurred."));

		}

		return ($q.reject(response.data.message));

	}


	function handleSuccess(response) {
		console.log('handle success');
		console.log(response);
		return (response.data);

	}

});