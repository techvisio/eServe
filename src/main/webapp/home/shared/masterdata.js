var masterDataModule=angular.module('masterdataModule',[]);

masterDataModule.service('masterdataService', function($http, $q){

	return ({
		getCustomerMasterData : getCustomerMasterData,
		getUserMasterData : getUserMasterData
	});

		function getCustomerMasterData() {

		console.log('Getting masterdata for customer');
		var request = $http({
			method : "get",
			url : "../service/masterdata/customer",
			params : "",
			data : ""
		});

		return (request.then(handleSuccess, handleError));

	}

	function  getUserMasterData() {

		console.log('Getting masterdata for user module');
		var request = $http({
			method : "get",
			url : "../service/masterdata/user",
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