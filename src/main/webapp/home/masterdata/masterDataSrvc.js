masterModule.service('masterService', function($http, $q) {

	// Return public API.
	return ({
		getEntity:getEntity,
		getClientMasterData:getClientMasterData,
		getQuestionMasterData:getQuestionMasterData,
		getStateMasterData:getStateMasterData,
		getPrivilegeData:getPrivilegeData,
		getDepartmentMasterData:getDepartmentMasterData,
		getDesignationMasterData:getDesignationMasterData,
		getEquipmentMasterData:getEquipmentMasterData,
		getConfigData:getConfigData,
		getIssueMasterData:getIssueMasterData,
		getResolutionMasterData:getResolutionMasterData,
		getUnitCategoryMasterData:getUnitCategoryMasterData,
		getServiceProviderMasterData:getServiceProviderMasterData,
		getCustomerTypeMasterData:getCustomerTypeMasterData,
		getInvoiceTaxesMasterData:getInvoiceTaxesMasterData,
	});

	function getEntity(object,type){

		console.log('Getting Entity');
		var request = $http({
			method : "post",
			url : "../service/masterData/getentitydata/" + type,
			params : "",
			data : object

		});

		return (request.then(handleSuccess, handleError));
	}

	function getClientMasterData(){

		console.log('Getting all Clients in service');
		var request = $http({
			method : "get",
			url : "../service/masterData/getclientdata/",
			params : {
				action : "get"
			}
		});
		return (request.then(handleSuccess, handleError));
	}

	function getQuestionMasterData(){

		console.log('Getting all Questions in service');
		var request = $http({
			method : "get",
			url : "../service/masterData/getquestiondata/",
			params : {
				action : "get"
			}
		});
		return (request.then(handleSuccess, handleError));
	}

	function getStateMasterData(){

		console.log('Getting all States in service');
		var request = $http({
			method : "get",
			url : "../service/masterData/getstatedata/",
			params : {
				action : "get"
			}
		});
		return (request.then(handleSuccess, handleError));
	}

	function getPrivilegeData(){

		console.log('Getting all Privileges in service');
		var request = $http({
			method : "get",
			url : "../service/masterData/getprivilegedata/",
			params : {
				action : "get"
			}
		});
		return (request.then(handleSuccess, handleError));
	}

	function getDepartmentMasterData(){

		console.log('Getting all Departments in service');
		var request = $http({
			method : "get",
			url : "../service/masterData/getdepartmentdata/",
			params : {
				action : "get"
			}
		});
		return (request.then(handleSuccess, handleError));
	}

	function getDesignationMasterData(){

		console.log('Getting all Designations in service');
		var request = $http({
			method : "get",
			url : "../service/masterData/getdesignationdata/",
			params : {
				action : "get"
			}
		});
		return (request.then(handleSuccess, handleError));
	}

	function getEquipmentMasterData(){

		console.log('Getting all Equipments in service');
		var request = $http({
			method : "get",
			url : "../service/masterData/getequipmentdata/",
			params : {
				action : "get"
			}
		});
		return (request.then(handleSuccess, handleError));
	}

	function getConfigData(){

		console.log('Getting all Configs in service');
		var request = $http({
			method : "get",
			url : "../service/masterData/getconfigdata/",
			params : {
				action : "get"
			}
		});
		return (request.then(handleSuccess, handleError));
	}

	function getIssueMasterData(){

		console.log('Getting all Issues in service');
		var request = $http({
			method : "get",
			url : "../service/masterData/getissuedata/",
			params : {
				action : "get"
			}
		});
		return (request.then(handleSuccess, handleError));
	}

	function getResolutionMasterData(){

		console.log('Getting all Resolutions in service');
		var request = $http({
			method : "get",
			url : "../service/masterData/getresolutiondata/",
			params : {
				action : "get"
			}
		});
		return (request.then(handleSuccess, handleError));
	}

	function getUnitCategoryMasterData(){

		console.log('Getting all Unit Category in service');
		var request = $http({
			method : "get",
			url : "../service/masterData/getunitcategorydata/",
			params : {
				action : "get"
			}
		});
		return (request.then(handleSuccess, handleError));
	}

	function getServiceProviderMasterData(){

		console.log('Getting all Service Provider in service');
		var request = $http({
			method : "get",
			url : "../service/masterData/getserviceproviderdata/",
			params : {
				action : "get"
			}
		});
		return (request.then(handleSuccess, handleError));
	}

	function getCustomerTypeMasterData(){

		console.log('Getting all Customer Type in service');
		var request = $http({
			method : "get",
			url : "../service/masterData/getcustomertypedata/",
			params : {
				action : "get"
			}
		});
		return (request.then(handleSuccess, handleError));
	}

	function getInvoiceTaxesMasterData(){

		console.log('Getting all Invoice Taxes in service');
		var request = $http({
			method : "get",
			url : "../service/masterData/gettnvoicetaxesdata/",
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
