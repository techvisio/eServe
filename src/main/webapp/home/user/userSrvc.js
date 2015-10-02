userModule.service('userService', function($http, $q) {

	// Return public API.
	return ({
		authenticateUser : authenticateUser,
		getUser : getUser,
		addUser : addUser,
		getUserRole : getUserRole,
		saveQuestion : saveQuestion 
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
			url : "service/user/loggedinuser/",
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
			url : "service/user/getuser/" + userId,
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
			url : "service/user/getuserRole/" + userId,
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
			url : "service/user/adduser/",
			params : "",
			data : user

		});

		return (request.then(handleSuccess, handleError));

	}
	
	function saveQuestion(question){

		console.log('saveSecurityQuestion called in service');
		var request = $http({
			method : "post",
			url : "service/user/saveQuestion/",
			params : "",
			data : question

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
		return (response);

	}

});