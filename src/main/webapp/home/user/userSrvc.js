userModule.service('userService', function($http, $q) {

	// Return public API.
	return ({
		authenticateUser : authenticateUser,
		getAuthenticatedUser : getAuthenticatedUser,
		getUser : getUser,
		getUserByCriteria : getUserByCriteria,
		verifyUserNameAndEmailId: verifyUserNameAndEmailId,
		getUsers : getUsers,
		addUser : addUser,
		getUserRole : getUserRole,
		saveQuestion : saveQuestion,
		getUserPrivileges : getUserPrivileges
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
	
	function getUserPrivileges(userId){

		console.log('Getting all User privileges');
		var request = $http({
			method : "get",
			url : "../service/user/userprivileges/" +userId,
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

	function addUser(user){

		console.log('add user called in service');
		var request = $http({
			method : "post",
			url : "../service/user/",
			params : "",
			data : user

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

	function verifyUserNameAndEmailId(searchCriteria){

		console.log('getting verified user with unique userName and EmailId in service');
		var request = $http({
			method : "post",
			url : "../service/user/verify/",
			params : "",
			data : searchCriteria

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