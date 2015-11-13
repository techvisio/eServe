var userModule = angular.module('userModule', []);

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
						 function(response) {
							 console
							 .log('user Data received from service in controller : ');
							 console.log(response);
							 if (response != null) {
								 $scope.user=response;
							 }

						 })
			 }

			 $scope.getUser=function(){
				 userService.getUser($scope.user.userId)
				 .then(
						 function(response) {
							 console
							 .log('user Data received from service in controller : ');
							 console.log(response);
							 if (response != null) {
								 $scope.user=response;
								 // $scope.getUserPrivileges();
								 $scope.redirectViewUser($scope.user.userId)
							 }

						 })
			 }


			 $scope.getUsers=function(){
				 userService.getUsers()
				 .then(
						 function(response) {
							 console
							 .log('user Data received from service in controller : ');
							 console.log(response);
							 if (response != null) {
								 $scope.users=response;
							 }

						 })
			 }

			 $scope.getUserByCriteria=function(){
				 userService.getUserByCriteria($scope.searchCriteria)
				 .then(
						 function(response) {
							 console
							 .log('getting user by criteria in controller : ');
							 console.log(response);
							 if (response != null) {
								 $scope.users=response;
							 }
						 })
			 }

			 $scope.verifyUserNameAndEmailId=function(){
				
				 $scope.searchCriteria.userName=$scope.user.userName;
				 $scope.searchCriteria.emailId=$scope.user.emailId;
				 userService.verifyUserNameAndEmailId($scope.searchCriteria)
				 .then(
						 function(response) {
							 console
							 .log('getting verified user with unique userName and EmailId in controller : ');
							 console.log(response);
							 if (response != null) {
								 $scope.verifiedUser=response;
							 }
						 })
			 }
			 $scope.getUserPrivileges=function(){
				 userService.getUserPrivileges($scope.user.userId||0)
				 .then(
						 function(response) {
							 console
							 .log('Privilege Data received from service in controller : ');
							 console.log(response);
							 if (response != null) {
								 $scope.allUserPrivileges=response;
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


			 $scope.getUserRole=function(){
				 userService.getUserRole($scope.user.userId)
				 .then(
						 function(response) {
							 console
							 .log('userRole Data received from service in controller : ');
							 console.log(response);
							 if (response != null) {
								 $scope.allUserRoles=response;
							 }
						 })
			 }


		 } ]);
