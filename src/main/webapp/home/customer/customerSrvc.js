customerModule.service('customerService', function($http, $q) {

	// Return public API.
	return ({
		saveCustomer  : saveCustomer,
		updateCustomer : updateCustomer,
		getCustomer : getCustomer,
		getCustomers:getCustomers,
		saveUnit : saveUnit,
		getUnit : getUnit,
		getCustomerByCriteria : getCustomerByCriteria,
		getAllComplaints : getAllComplaints,
		updateServiceAgreement : updateServiceAgreement,
		getServiceAgreementHistoryForUnit : getServiceAgreementHistoryForUnit,
		approveUnit : approveUnit,
		getUnitForApproval : getUnitForApproval,
		getWorkItemByUserIdAndWorkType : getWorkItemByUserIdAndWorkType,
		getEmailId : getEmailId,
		getContactNo : getContactNo,
		rejectUnitApproval : rejectUnitApproval,
		lockEntity : lockEntity,
		unlockEntity:unlockEntity,
		getUnitActionItems : getUnitActionItems,	
		getLatestCommentBycommentType : getLatestCommentBycommentType
	});


	
	function rejectUnitApproval(genericRequest){
		console.log('rejecting unit approval in service');
		var request = $http({
			method : "put",
			url : "../service/customer/rejectunit",
			params : "",
			data: genericRequest

		});
		return (request.then(handleSuccess, handleError));
	}
	
	function getWorkItemByUserIdAndWorkType(userId, type, status){
		console.log('get work Item by user Id and type in service');

		var request = $http({
			method : "get",
			url : "../service/workitem/user/" + userId+"?type="+type+"&status="+status,
			params : {
				action : "get"
			},

		});
		return (request.then(handleSuccess, handleError));
	}

	function getServiceAgreementHistoryForUnit(unitId){
		console.log('get Agreement History Data in service');

		var request = $http({
			method : "get",
			url : "../service/customer/servicehistory/" + unitId,
			params : {
				action : "get"
			}
		});
		return (request.then(handleSuccess, handleError));
	}


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

	function saveCustomer(genericRequest, context){
		console.log('add new customer');
		var request = $http({
			method : "post",
			url : "../service/customer/"+ context,
			params : "",
			data: genericRequest

		});
		return (request.then(handleSuccess, handleError));
	}

	function updateCustomer(customer){
		console.log('add new customer');
		var request = $http({
			method : "put",
			url : "../service/customer/",
			params : "",
			data: customer

		});
		return (request.then(handleSuccess, handleError));
	}

	function approveUnit(unit){
		console.log('approve unit called in service');
		var request = $http({
			method : "put",
			url : "../service/customer/approveunit/",
			params : "",
			data: unit

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

	function saveUnit(genericRequest, customerId, context){
		console.log('save units');
		var request = $http({
			method : "put",
			url : "../service/customer/unit/" + customerId + "/"+context,
			params : "",
			data: genericRequest

		});
		return (request.then(handleSuccess, handleError));
	}

	function updateServiceAgreement(serviceAgreement, unitId){
		console.log('renewService called in service');
		var request = $http({
			method : "put",
			url : "../service/customer/renewService/" + unitId,
			params : "",
			data: serviceAgreement

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

	function getAllComplaints(customerId){
		console.log('getting all customer complaints in service');

		var request = $http({
			method : "get",
			url : "../service/complaint/complaints/"+customerId,
			params : {
				action : "get"
			}
		});
		return (request.then(handleSuccess, handleError));
	}

	function getUnitForApproval(unitId){
		console.log('getUnitForApproval in service');

		var request = $http({
			method : "get",
			url : "../service/customer/unitapproval/"+unitId,
			params : {
				action : "get"
			}
		});
		return (request.then(handleSuccess, handleError));
	}

	function getEmailId(emailId){
		console.log('get all customers data');

		var request = $http({
			method : "get",
			url : "../service/customer/emailId?emailId="+emailId,
			params : {
				action : "get"
			}
		});
		return (request.then(handleSuccess, handleError));
	}

	function getContactNo(contactNo){
		console.log('get all customers data');

		var request = $http({
			method : "get",
			url : "../service/customer/contactNo?contactNo="+contactNo,
			params : {
				action : "get"
			}
		});
		return (request.then(handleSuccess, handleError));
	}
	
	function getUnitActionItems(entityId){
		console.log('get action items in service');

		var request = $http({
			method : "get",
			url : "../service/workitem/unitworkitem/"+entityId,
			params : {
				action : "get"
			}
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

	function getLatestCommentBycommentType(entityId, entityType, commentType){
		console.log('get latest comment in service');

		var request = $http({
			method : "get",
			url : "../service/workitem/latestcomment/" + entityId +"/"+entityType+"/"+commentType ,
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