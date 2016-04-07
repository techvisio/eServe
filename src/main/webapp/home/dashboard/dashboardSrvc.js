dashboardModule.service('dashboardService', function($http, $q) {

	// Return public API.
	return ({
		getNoticeBoardCount : getNoticeBoardCount		
	});

	
	function getNoticeBoardCount(){

		console.log('getting Notice board count');
		var request = $http({
			method : "get",
			url : "../service/dashbord/totalcount/",
			params : ""

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