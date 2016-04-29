reportModule.service('reportService', function($http, $q) {

	// Return public API.
	return ({
		getCustomerReportByCriteria:getCustomerReportByCriteria
	});
	
	function getCustomerReportByCriteria(customerReportAttribute){

		console.log('Reset user password');
		var request = $http({
			method : "post",
			url : "../service/report/customerreport/",
			params : "",
			data : customerReportAttribute

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
		return (response.data.responseBody);

	}

});