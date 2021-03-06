workItemModule.service('workItemService', function($http, $q) {
	// Return public API.
	return ({
		getUnitInfoByEntityIdAndEntityType:getUnitInfoByEntityIdAndEntityType,
		createComplaintByPms : createComplaintByPms,
		getWorkItemByWorkitemId : getWorkItemByWorkitemId,
		saveComment : saveComment,
		getCommentList : getCommentList,
		createWorkItemForSalesRenewal : createWorkItemForSalesRenewal,
		getWorkItembySearchCriteria : getWorkItembySearchCriteria,
		getWorkItemForQueuebySearchCriteria : getWorkItemForQueuebySearchCriteria
	});

	function getUnitInfoByEntityIdAndEntityType(entityId, entityType){
		console.log('getting unit details');

		var request = $http({
			method : "get",
			url : "../service/workitem/unitinfo/" + entityId +"/"+entityType,
			params : {
				action : "get"
			},

		});
		return (request.then(handleSuccess, handleError));
	}

	function createComplaintByPms(unitBasicInfo, workitemId){
		console.log('createComplaintByPms called in service');
		var request = $http({
			method : "post",
			url : "../service/complaint/pmscomplaint/"+workitemId,
			params : "",
			data: unitBasicInfo

		});
		return (request.then(handleSuccess, handleError));
	}

	function getWorkItemByWorkitemId(workitemId){
		console.log('getting workitem by workitem id in service');

		var request = $http({
			method : "get",
			url : "../service/workitem/" + workitemId,
			params : {
				action : "get"
			},

		});
		return (request.then(handleSuccess, handleError));
	}

	function saveComment(genericRequest){
		console.log('save comment');
		var request = $http({
			method : "post",
			url : "../service/workitem/comment/",
			params : "",
			data: genericRequest

		});
		return (request.then(handleSuccess, handleError));
	}

	function getCommentList(workItemId){
		console.log('get all comments');

		var request = $http({
			method : "get",
			url : "../service/workitem/comment/" + workItemId,
			params : {
				action : "get"
			}
		});
		return (request.then(handleSuccess, handleError));
	}
	
	
	
	function createWorkItemForSalesRenewal(unitInfo){
		console.log('creating workitem for sales renewal service');
		var request = $http({
			method : "post",
			url : "../service/workitem/salesrenewal",
			params : "",
			data: unitInfo

		});
		return (request.then(handleSuccess, handleError));
	}

	function getWorkItembySearchCriteria(workItemSearchCriteria){

		console.log('Reset user password');
		var request = $http({
			method : "post",
			url : "../service/workitem/workitemlist/",
			params : "",
			data : workItemSearchCriteria

		});

		return (request.then(handleSuccess, handleError));
	}

	function getWorkItemForQueuebySearchCriteria(workItemSearchCriteria){
		console.log('getting workitems for queue');
		var request = $http({
			method : "post",
			url : "../service/workitem/workitemqueuelist/",
			params : "",
			data : workItemSearchCriteria

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
