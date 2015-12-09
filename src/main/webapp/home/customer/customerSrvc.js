customerModule.service('customerService', function($http, $q) {

	// Return public API.
	return ({
		saveCustomer  : saveCustomer,
		getCustomer : getCustomer,
		getCustomers:getCustomers,
		saveUnit : saveUnit,
		getUnit : getUnit,
		getCustomerByCriteria : getCustomerByCriteria,
		saveComplaint : saveComplaint,
		getCustomerForComplaint : getCustomerForComplaint,
		getUnitForComplaint : getUnitForComplaint
	});

	
	function getCustomers(){
		console.log('get all customers data');

		var request = $http({
			method : "get",
			url : "../service/customer/",
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
			url : "../service/customer/"+customerId,
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
			url : "../service/customer/",
			params : "",
			data: customer

		});
		return (request.then(handleSuccess, handleError));
	}

	function getUnit(unitId){
		console.log('get unit');

		var request = $http({
			method : "get",
			url : "../service/customer/unit/"+unitId,
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
			url : "../service/customer/unit/" + customerId,
			params : "",
			data: units

		});
		return (request.then(handleSuccess, handleError));
	}

	function getCustomerByCriteria(searchCriteria){

		console.log('getting customer by search criteria');
		var request = $http({
			method : "post",
			url : "../service/customer/search/",
			params : "",
			data : searchCriteria

		});
		return (request.then(handleSuccess, handleError));
	}

	function saveComplaint(customerComplaint){
		console.log('save complaint call in service');
		var request = $http({
			method : "post",
			url : "../service/customer/complaint/",
			params : "",
			data: customerComplaint

		});
		return (request.then(handleSuccess, handleError));
	}

	function getCustomerForComplaint(customerId){
		console.log('get customer for complaint');

		var request = $http({
			method : "get",
			url : "../service/customer/complaint/"+customerId,
			params : {
				action : "get"
			}
		});
		return (request.then(handleSuccess, handleError));
	}
	
	function getUnitForComplaint(unitId){
		console.log('get unit for complaint');

		var request = $http({
			method : "get",
			url : "../service/customer/unitcomplaint/"+unitId,
			params : {
				action : "get"
			}
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