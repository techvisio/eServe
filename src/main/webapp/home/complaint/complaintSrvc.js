complaintModule.service('complaintService', function($http, $q) {

	// Return public API.
	return ({
		saveWorkOrder : saveWorkOrder,
		updateWorkOrder : updateWorkOrder,
		getCustomerForComplaint : getCustomerForComplaint,
		getUnitForComplaint : getUnitForComplaint,
		getWorkOrder : getWorkOrder,
		saveWorkOrderAssignment :saveWorkOrderAssignment,
		saveWorkOrderResolution :saveWorkOrderResolution,
		getComplaintByCriteria : getComplaintByCriteria,
		getSearchUnitByCustomerId : getSearchUnitByCustomerId,
		getComplaintByUnitId : getComplaintByUnitId,
		getAllComplaintsForUnit : getAllComplaintsForUnit,
		lockEntity : lockEntity,
		unlockEntity : unlockEntity,
		getEquipments:getEquipments,
		saveEquipment:saveEquipment,
		getWorkOrderEquipment : getWorkOrderEquipment,
		deleteEquipments:deleteEquipments,
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

	function saveWorkOrder(workOrder){
		console.log('save complaint call in service');
		var request = $http({
			method : "post",
			url : "../service/complaint",
			params : "",
			data: workOrder

		});
		return (request.then(handleSuccess, handleError));
	}

	function updateWorkOrder(workOrder){
		console.log('update complaint call in service');
		var request = $http({
			method : "put",
			url : "../service/complaint",
			params : "",
			data: workOrder

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

	function getWorkOrder(workOrderId){
		console.log('getting customer complaint in service');

		var request = $http({
			method : "get",
			url : "../service/complaint/"+workOrderId,
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

	function saveWorkOrderResolution(workOrderId,workOrderResolution){
		console.log('save workOrderResolution call in service');
		var request = $http({
			method : "post",
			url : "../service/complaint/complaintresolution/"+workOrderId,
			params : "",
			data: workOrderResolution

		});
		return (request.then(handleSuccess, handleError));
	}

	function saveWorkOrderAssignment(workOrderId, workOrderAssignment){
		console.log('save workOrderAssignment call in service');
		var request = $http({
			method : "post",
			url : "../service/complaint/complaintassignment/"+workOrderId,
			params : "",
			data: workOrderAssignment

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

	function saveEquipment(equipment, complaintId){
		console.log('save equipment call in service');
		var request = $http({
			method : "post",
			url : "../service/complaint/equipment/" + complaintId,
			params : "",
			data: equipment

		});
		return (request.then(handleSuccess, handleError));
	}
	
	function getWorkOrderEquipment(workOrderId){
		console.log('getting getComplaintEquipment in service');

		var request = $http({
			method : "get",
			url : "../service/complaint/complaintequipment/"+workOrderId,
			params : {
				action : "get"
			}
		});
		return (request.then(handleSuccess, handleError));
	}	
	
	function deleteEquipments(equipments, unitId, complaintId){
		console.log('delete equipment call in service');
		var request = $http({
			method : "post",
			url : "../service/complaint/deleteequipment/" + unitId + "/" + complaintId,
			params : "",
			data: equipments

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