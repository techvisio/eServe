userModule.service('userService', function($http, $q) {

	// Return public API.
	return ({
		authenticateUser : authenticateUser,
		getAuthenticatedUser : getAuthenticatedUser,
		getUser : getUser,
		getUserByCriteria : getUserByCriteria,
		saveUser: saveUser,
		updateUser : updateUser,
		getUsers : getUsers,
		getUserRole : getUserRole,
		saveQuestion : saveQuestion,
		getUserwithprivileges : getUserwithprivileges,
		getUserprivileges : getUserprivileges,
		getCurrentPassword :getCurrentPassword,
		resetPassword : resetPassword,
		getUserForEdit : getUserForEdit,
		getUserAfterEdit : getUserAfterEdit,
		getEmailId : getEmailId,
		getUserName:getUserName
	});

	function authenticateUser(form) {
		$http({
			method  : 'POST',
			url     : 'j_spring_security_check',
			data    : $.param(form),  // pass in data as strings
			headers : { 'Content-Type': 'application/x-www-form-urlencoded' }  // set the headers so angular passing info as form data (not request payload)
		})
		.success(function(data) {
			console.log(data);

			if (!data.success) {
				// if not successful, bind errors to error variables
				$scope.errorName = data.errors.name;
				$scope.errorSuperhero = data.errors.superheroAlias;
			} else {
				// if successful, bind success message to message
				$scope.message = data.message;
			}
		});
	};

	function getAuthenticatedUser(){

		console.log('Getting user in service');
		var request = $http({
			method : "get",
			url : "../service/user/loggedinuser/",
			params : {
				action : "get"
			}
		});
		return (request.then(handleSuccess, handleError));
	}

	function getUser(userId){

		console.log('Getting user in service');
		var request = $http({
			method : "get",
			url : "../service/user/" + userId,
			params : {
				action : "get"
			}
		});
		return (request.then(handleSuccess, handleError));
	}

	function getCurrentPassword(userId){

		console.log('Getting user in service');
		var request = $http({
			method : "get",
			url : "../service/user/currentpass/" + userId,
			params : {
				action : "get"
			}
		});
		return (request.then(handleSuccess, handleError));
	}
	
	function getUsers(){

		console.log('Getting all users in service');
		var request = $http({
			method : "get",
			url : "../service/user/",
			params : {
				action : "get"
			}
		});
		return (request.then(handleSuccess, handleError));
	}
	
	function getUserwithprivileges(userId){

		console.log('Getting all User privileges');
		var request = $http({
			method : "get",
			url : "../service/user/userwithprivileges/" +userId,
			params : {
				action : "get"
			}
		});
		return (request.then(handleSuccess, handleError));
	}

	function getUserprivileges(){

		console.log('ser privileges');
		var request = $http({
			method : "get",
			url : "../service/user/userprivileges/",
			params : {
				action : "get"
			}
		});
		return (request.then(handleSuccess, handleError));
	}
	
	function getUserRole(userId){
		userId=userId||0;
		console.log('Getting user in service');
		var request = $http({
			method : "get",
			url : "../service/user/userRole/" + userId,
			params : {
				action : "get"
			}
		});
		return (request.then(handleSuccess, handleError));
	}

	function saveQuestion(user){

		console.log('saveSecurityQuestion called in service');
		var request = $http({
			method : "post",
			url : "../service/user/forcepasswordchange",
			params : "",
			data : user

		});

		return (request.then(handleSuccess, handleError));

	}
	
	function getUserByCriteria(searchCriteria){

		console.log('gettin userby search criteria');
		var request = $http({
			method : "post",
			url : "../service/user/search/",
			params : "",
			data : searchCriteria

		});

		return (request.then(handleSuccess, handleError));

	}

	function saveUser(user){

		console.log('getting verified user with unique userName and EmailId in service');
		var request = $http({
			method : "post",
			url : "../service/user/",
			params : "",
			data : user

		});

		return (request.then(handleSuccess, handleError));
	}

	function updateUser(user){

		console.log('getting verified user with unique userName and EmailId in service');
		var request = $http({
			method : "put",
			url : "../service/user/",
			params : "",
			data : user

		});

		return (request.then(handleSuccess, handleError));
	}
	
	function resetPassword(user){

		console.log('Reset user password');
		var request = $http({
			method : "post",
			url : "../service/user/resetpassword/",
			params : "",
			data : user

		});

		return (request.then(handleSuccess, handleError));
	}

	function getUserForEdit(userId){
			console.log('Lock user entity in service');

			var request = $http({
				method : "get",
				url : "../service/user/lockuser/"+userId,
				params : {
					action : "get"
				}
			});
			return (request.then(handleSuccess, handleError));
		}

	function getUserAfterEdit(userId){
		console.log('unlock user entity in service');

		var request = $http({
			method : "get",
			url : "../service/user/unlockuser/"+userId,
			params : {
				action : "get"
			}
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

	function getEmailId(emailId){
		console.log('get all users data');

		var request = $http({
			method : "get",
			url : "../service/user/emailId?emailId="+emailId,
			params : {
				action : "get"
			}
		});
		return (request.then(handleSuccess, handleError));
	}

	function getUserName(userName){
		console.log('get all users data');

		var request = $http({
			method : "get",
			url : "../service/user/userName?userName="+userName,
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