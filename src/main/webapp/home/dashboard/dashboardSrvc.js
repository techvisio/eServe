dashboardModule.service('dashboardService', function($http, $q) {

	// Return public API.
	return ({
		getNoticeBoardCount : getNoticeBoardCount,
		getComplaintCountByAssignment : getComplaintCountByAssignment,
		getComplaintCountByPriority : getComplaintCountByPriority,
		getComplaintCountBySlaDate : getComplaintCountBySlaDate,
		getComplaintDataforDashboard : getComplaintDataforDashboard
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
	
	function getComplaintCountByAssignment(){

		console.log('getting Notice board count');
		var request = $http({
			method : "get",
			url : "../service/dashbord/countbyassignment/",
			params : ""

		});
		return (request.then(handleSuccess, handleError));
	}
	
	function getComplaintCountByPriority(){

		console.log('getting Notice board count');
		var request = $http({
			method : "get",
			url : "../service/dashbord/countbypriority/",
			params : ""

		});
		return (request.then(handleSuccess, handleError));
	}
	
	function getComplaintCountBySlaDate(){

		console.log('getting Notice board count');
		var request = $http({
			method : "get",
			url : "../service/dashbord/countbysla/",
			params : ""

		});
		return (request.then(handleSuccess, handleError));
	}

	function getComplaintDataforDashboard(type, code){
		console.log('getComplaintDataforDashboard');

		var request = $http({
			method : "get",
			url : "../service/dashbord/complaintdata/"+type+"/"+code,
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