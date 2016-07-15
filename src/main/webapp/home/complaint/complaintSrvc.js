complaintModule.service('complaintService', function($http, $q) {

	// Return public API.
	return ({
		saveComplaint : saveComplaint,
		updateComplaint : updateComplaint,
		getCustomerForComplaint : getCustomerForComplaint,
		getUnitForComplaint : getUnitForComplaint,
		getCustomerComplaint : getCustomerComplaint,
		saveComplaintAssignment :saveComplaintAssignment,
		saveComplaintResolution :saveComplaintResolution,
		getComplaintByCriteria : getComplaintByCriteria,
		getSearchUnitByCustomerId : getSearchUnitByCustomerId,
		getComplaintByUnitId : getComplaintByUnitId,
		getAllComplaintsForUnit : getAllComplaintsForUnit,
		lockEntity : lockEntity,
		unlockEntity : unlockEntity,
		getEquipments:getEquipments,
		saveEquipment:saveEquipment
	});

	

	function getComplaintByCriteria(searchCriteria){

		console.log('getting complaint customer by search criteria');
		var request = $http({
			method : "post",
			url : "../service/complaint/searchcomplaintcustomer/",
			params : "",
			data : searchCriteria

		});
		return (request.then(handleSuccess, handleError));
	}

	function saveComplaint(customerComplaint){
		console.log('save complaint call in service');
		var request = $http({
			method : "post",
			url : "../service/complaint",
			params : "",
			data: customerComplaint

		});
		return (request.then(handleSuccess, handleError));
	}

	function updateComplaint(customerComplaint){
		console.log('update complaint call in service');
		var request = $http({
			method : "put",
			url : "../service/complaint",
			params : "",
			data: customerComplaint

		});
		return (request.then(handleSuccess, handleError));
	}

	function getCustomerForComplaint(customerId){
		console.log('get customer for complaint');

		var request = $http({
			method : "get",
			url : "../service/complaint/customercomplaint/"+customerId,
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
			url : "../service/complaint/unitcomplaint/"+unitId,
			params : {
				action : "get"
			}
		});
		return (request.then(handleSuccess, handleError));
	}

	function getCustomerComplaint(complaintId){
		console.log('getting customer complaint in service');

		var request = $http({
			method : "get",
			url : "../service/complaint/"+complaintId,
			params : {
				action : "get"
			}
		});
		return (request.then(handleSuccess, handleError));
	}	


	function getSearchUnitByCustomerId(customerId){
		console.log('getting units by customerId in service');

		var request = $http({
			method : "get",
			url : "../service/complaint/searchcomplaintunit/"+customerId,
			params : {
				action : "get"
			}
		});
		return (request.then(handleSuccess, handleError));
	}	

	function getComplaintByUnitId(unitId){
		console.log('getting SearchComplaint by unitId in service');

		var request = $http({
			method : "get",
			url : "../service/complaint/searchcomplaint/"+unitId,
			params : {
				action : "get"
			}
		});
		return (request.then(handleSuccess, handleError));
	}	


	function getAllComplaintsForUnit(unitId){
		console.log('getting all complaints for single unit in service');

		var request = $http({
			method : "get",
			url : "../service/complaint/complaint/"+unitId,
			params : {
				action : "get"
			}
		});
		return (request.then(handleSuccess, handleError));
	}	

	function saveComplaintResolution(complaintId,complaintResolution){
		console.log('save ComplaintResolution call in service');
		var request = $http({
			method : "post",
			url : "../service/complaint/complaintresolution/"+complaintId,
			params : "",
			data: complaintResolution

		});
		return (request.then(handleSuccess, handleError));
	}

	function saveComplaintAssignment(complaintId, complaintAssignment){
		console.log('save CustomerComplaint call in service');
		var request = $http({
			method : "post",
			url : "../service/complaint/complaintassignment/"+complaintId,
			params : "",
			data: complaintAssignment

		});
		return (request.then(handleSuccess, handleError));
	}

	function lockEntity(entityLock){
		console.log('lock entity');
		var request = $http({
			method : "post",
			url : "../service/entityLock/lockEntity",
			params : "",
			data: entityLock

		});
		return (request.then(handleSuccess, handleError));
	}

	function unlockEntity(entityLock){
		console.log('unlock entity');
		var request = $http({
			method : "post",
			url : "../service/entityLock/unlockEntity",
			params : "",
			data: entityLock

		});
		return (request.then(handleSuccess, handleError));
	}

	function getEquipments(type, unitId){
		console.log('getting equipmentDetails in service');

		var request = $http({
			method : "get",
			url : "../service/complaint/equipment/"+type+"/"+unitId,
			params : {
				action : "get"
			}
		});
		return (request.then(handleSuccess, handleError));
	}	

	function saveEquipment(equipment){
		console.log('save equipment call in service');
		var request = $http({
			method : "post",
			url : "../service/complaint/equipment",
			params : "",
			data: equipment

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