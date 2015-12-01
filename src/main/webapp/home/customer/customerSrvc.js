customerModule.service('customerService', function($http, $q) {

	// Return public API.
	return ({
		saveCustomer  : saveCustomer,
		getCustomer : getCustomer,
		getCustomers:getCustomers,
		saveUnit : saveUnit,
		getUnit : getUnit
	});

	
	function getCustomers(){
		console.log('get all customers data');

		var request = $http({
			method : "get",
			url : "../customer/",
			params : {
				action : "get"
			}
		});
		return (request.then(handleSuccess, handleError));
	}
	
	function getCustomer(customerId){
		console.log('get customer');

		var request = $http({
			method : "get",
			url : "../customer/"+customerId,
			params : {
				action : "get"
			}
		});
		return (request.then(handleSuccess, handleError));
	}

	function saveCustomer(customer){
		console.log('add new customer');
		var request = $http({
			method : "post",
			url : "../customer/",
			params : "",
			data: customer

		});
		return (request.then(handleSuccess, handleError));
	}

	function getUnit(customerId){
		console.log('get unit');

		var request = $http({
			method : "get",
			url : "../customer/unit/"+customerId,
			params : {
				action : "get"
			}
		});
		return (request.then(handleSuccess, handleError));
	}

	function saveUnit(units, customerId){
		console.log('save units');
		var request = $http({
			method : "put",
			url : "../customer/unit/" + customerId,
			params : "",
			data: units

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