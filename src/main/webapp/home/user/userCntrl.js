var userModule = angular.module('userModule', ['ui.bootstrap']);

userModule
.controller(
		'userController',
		[
		 '$scope',
		 '$state',
		 '$rootScope',
		 'userService',
		 'user',
		 'masterdataService',
		 function($scope, $state, $rootScope,userService,user,masterdataService) {
			 $scope.form={};
			 $scope.customQuestion = false;
			 $scope.wrongNewPass = false;
			 $scope.oldConfirmed = true;
			 $scope.wrongConfirmPass = false;
			 $scope.newPassMust = false;
			 $scope.allUserPrivileges=[];
			 $scope.users=[];
			 $scope.user={};
			 $scope.securityQuestion={};
			 $scope.user.roles=[];
			 $scope.openedFrom=false;
			 $scope.searchCriteria = {};
			 $scope.verifiedUser = {};

			 $scope.allUserRoles=[];

			 if(user){
				 $scope.user = user;
			 }			 

			 $scope.authenticateUser=function(){
				 userService.authenticateUser($scope.form);
			 }


			 $scope.redirectToUser=function(currentUserId){
				 $state.go('user',{userId:currentUserId});
			 }

			 $scope.addAndRemoveRoleFromUser = function(object){

				 if($scope.user.roles.indexOf(object)<0){
					 $scope.user.roles.push(object);					 
				 }
				 else{
					 $scope.user.roles.splice(object);					 
				 }
			 }

			 $scope.showCustom = function() {

				 if ($scope.chkStatus) {
					 $scope.customQuestion = true;
				 }
				 else {
					 $scope.customQuestion = false;		

				 }
			 };


			 $scope.alerts = [
			                  { type: 'danger', msg:' ' },
			                  ];


			 $scope.init = function() {
				 console
				 .log('getting masterdata for user');

				 masterdataService
				 .getUserMasterData()
				 .then(
						 function(data) {
							 console.log(data);
							 if (data) {
								 $scope.serverModelData = data.responseBody;
							 } else {
								 console.log('error');
							 }
						 })
			 };


			 $scope.getAuthenticatedUser=function(){
				 userService.getAuthenticatedUser()
				 .then(
						 function(response) {
							 console
							 .log('Data received from service in controller : ');
							 console.log(response);
							 if (response != null) {
								 $rootScope.user=response;
							 }
						 })
			 }

			 $scope.addUser=function(){

				 if($scope.user.password != $scope.confirmPassword){
					 $scope.wrongConfirmPass = true;
					 return;
				 }
				 $scope.user.privileges=$scope.allUserPrivileges;
				 userService.addUser($scope.user)
				 .then(
						 function(savedUser) {
							 console
							 .log('user Data received from service in controller : ');
							 console.log(savedUser);
							 if (savedUser) {
								 $scope.user=savedUser;
							 }

						 })
			 }

			 $scope.getUser=function(){
				 userService.getUser($scope.user.userId)
				 .then(
						 function(existingUser) {
							 console
							 .log('user Data received from service in controller : ');
							 console.log(existingUser);
							 if (existingUser) {
								 $scope.user=existingUser;
								 // $scope.getUserPrivileges();
								 $scope.redirectViewUser($scope.user.userId)
							 }

						 })
			 }


			 $scope.getUsers=function(){
				 userService.getUsers()
				 .then(
						 function(users) {
							 console
							 .log('user Data received from service in controller : ');
							 console.log(users);
							 if (users) {
								 $scope.users=users;
							 }

						 })
			 }

			 $scope.getUserByCriteria=function(){
				 userService.getUserByCriteria($scope.searchCriteria)
				 .then(
						 function(users) {
							 console
							 .log('getting user by criteria in controller : ');
							 console.log(users);
							 if (users) {
								 $scope.users=users;
							 }
						 })
			 }

			 $scope.verifyUserNameAndEmailId=function(){

				 if($scope.user.password == null || $scope.confirmPassword==null || $scope.user.password != $scope.confirmPassword){
					 $scope.wrongConfirmPass = true
					 return;
				 }

				 $scope.searchCriteria.userName=$scope.user.userName;
				 $scope.searchCriteria.emailId=$scope.user.emailId;
				 userService.verifyUserNameAndEmailId($scope.searchCriteria)
				 .then(
						 function(existingUser) {
							 console
							 .log('getting verified user with unique userName and EmailId in controller : ');
							 console.log(existingUser);
							 if (existingUser) {
								 
								 $scope.alerts=[];
								 $scope.verifiedUser=existingUser;

								 if($scope.verifiedUser.userName==$scope.user.userName || $scope.verifiedUser.emailId==$scope.user.emailId){
									 $scope.alerts.push({msg: 'This User Name Or Email Id Already Exists!! Choose Different User Name Or Email Id'})
									 
									 return;
								 }
							 }
							 $scope.addUser();
						 })
			 }
			 $scope.getUserPrivileges=function(){
				 userService.getUserPrivileges($scope.user.userId||0)
				 .then(
						 function(privileges) {
							 console
							 .log('Privilege Data received from service in controller : ');
							 console.log(privileges);
							 if (privileges != null) {
								 $scope.allUserPrivileges=privileges;
							 }

						 })
			 }

			 $scope.saveQuestion=function(){

				 if($scope.user.password == $scope.user.newPassword){
					 $scope.wrongNewPass = true;
					 return;
				 }

				 if($scope.user.newPassword==null){
					 $scope.newPassMust = true;
					 return;
				 }

				 if($scope.user.newPassword != $scope.confirmPassword){

					 $scope.wrongConfirmPass = true;
					 return;
				 }
				 userService.saveQuestion($scope.user)
				 .then(
						 function(response) {
							 if(response){
								 var success=response.success;
								 $scope.oldConfirmed=response.passwordMatch;
								 if(success){
									 $rootScope.curModal.close();
								 }
							 }

						 })
			 }


//			 $scope.getUserRole=function(){
//			 userService.getUserRole($scope.user.userId)
//			 .then(
//			 function(roles) {
//			 console
//			 .log('userRole Data received from service in controller : ');
//			 console.log(response);
//			 if (response != null && response.data != null && response.data.responseBody != null) {
//			 $scope.allUserRoles=response.data.responseBody;
//			 }
//			 })
//			 }


		 } ]);
