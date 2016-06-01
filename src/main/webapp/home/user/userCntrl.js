var userModule = angular.module('userModule', ['ui.bootstrap.buttons']);

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
			 $scope.isUserCollapsed= true;
			 $scope.isPrivilegesCollapsed= true;
			 $scope.isEdit = false;
			 $scope.customQuestion = false;
			 $scope.wrongNewPass = false;
			 $scope.wrongCurrntPass = false;
			 $scope.userPassword = {};
			 $scope.isNew = true;
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

			 $scope.isPrivileged = function(role){

				 var userPrivilege = $rootScope.user.privileges;
				 var result=false;
				 angular.forEach(userPrivilege, function(privilege) {
					 if (privilege.privilege.privilege===role) result= true;
				 });
				 return result;		
			 }

			 if(!user){
				 userService.getUserprivileges()
				 .then(
						 function(userPrivileges) {
							 console
							 .log('user privilegs received from service in controller : ');
							 console.log(userPrivileges);
							 if (userPrivileges) {
								 $scope.user.privileges=userPrivileges;
							 }
						 })
			 }			 

			 if(user){
				 $scope.isEdit = true;
				 $scope.isNew = false;
				 $scope.user = user;
			 }			 

			 $scope.authenticateUser=function(){
				 userService.authenticateUser($scope.form);
			 }

			 if($scope.isPrivileged('VIEW_USER') || $scope.isPrivileged('CREATE_USER')){
				 $scope.redirectToUser=function(currentUserId){
					 $state.go('user',{entityId:currentUserId});
				 }
			 }

			 $scope.addAndRemoveRoleFromUser = function(object){

				 if($scope.user.roles.indexOf(object)<0){
					 $scope.user.roles.push(object);					 
				 }
				 else{
					 $scope.user.roles.splice(object);					 
				 }
			 }

			 if($rootScope.user.securityQuestion && $rootScope.user.securityQuestion.customQuestion){
				 $scope.chkStatus = true;
				 $scope.customQuestion = true;
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
				 userService.addUser($scope.user)
				 .then(
						 function(savedUser) {
							 console
							 .log('saving New User in controller : ');
							 console.log(savedUser);
							 if (savedUser) {
								 $scope.user=savedUser;
							 }

						 })
			 }

			 $scope.getUserprivileges = function(){

				 userService.getUserprivileges()
				 .then(
						 function(userPrivileges) {
							 console
							 .log('user privilegs received from service in controller : ');
							 console.log(userPrivileges);
							 if (userPrivileges) {
								 $scope.user.privileges=userPrivileges;
							 }

						 })
			 }

			 $scope.getUser=function(){
				 userService.getUser($scope.user.userId)
				 .then(
						 function(existingUser) {
							 console
							 .log('user received from service in controller : ');
							 console.log(existingUser);
							 if (existingUser) {
								 $scope.user=existingUser;
								 // $scope.getUserPrivileges();
								 $scope.redirectToUser($scope.user.userId)
							 }

						 })
			 }

			 $scope.getCurrentPassword=function(){
				 userService.getUser($scope.user.userId)
				 .then(
						 function(existingUser) {
							 console
							 .log('user received from service in controller : ');
							 console.log(existingUser);
							 if (existingUser) {
								 $scope.userPassword=existingUser;
							 }

						 })
			 }
//			 $scope.getCurrentPassword=function(){
//			 userService.getCurrentPassword($scope.user.userId)
//			 .then(
//			 function(userPassword) {
//			 console
//			 .log('user password received from service in controller : ');
//			 console.log(userPassword);
//			 if (userPassword) {
//			 $scope.userPassword=userPassword;
//			 }

//			 })
//			 }

			 $scope.getUsers=function(){
				 userService.getUsers()
				 .then(
						 function(users) {
							 console
							 .log('All Users received from service in controller : ');
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

			 $scope.resetPassword=function(user){
				 userService.resetPassword(user)
				 .then(
						 function(user) {
							 console
							 .log('reset user password in controller : ');
							 console.log(user);
							 if (user) {
								 $scope.user=user;
								 alert("Resetting User Password Is Done");
							 }
						 })
			 }

			 $scope.logout=function(){

				 window.location=document.getElementById('logout').href;
			 }



//			 return;
//			 }
//			 }
//			 if($scope.user.password==null){
//			 $scope.userPassword = $scope.getCurrentPassword();
//			 $scope.user.password = $scope.userPassword.password;
//			 $scope.addUser();
//			 }

//			 })
//			 }
//			 $scope.getUserPrivileges=function(){
//			 userService.getUserPrivileges($scope.user.userId||0)
//			 .then(
//			 function(privileges) {
//			 console
//			 .log('Privilege Data received from service in controller : ');
//			 console.log(privileges);
//			 if (privileges != null) {
//			 $scope.allUserPrivileges=privileges;
//			 }

//			 })
//			 }

			 $scope.saveQuestion=function(){

				 if($scope.user.password == $scope.user.newPassword){
					 $scope.wrongNewPass = true;
					 return;
				 }

				 if($scope.user.newPassword==null || $scope.user.newPassword==""){
					 $scope.newPassMust = true;
					 return;
				 }

				 if($scope.user.newPassword != $scope.confirmPassword){

					 $scope.wrongConfirmPass = true;
					 return;
				 }

				 if($scope.chkStatus){

					 $scope.user.securityQuestion.customQuestion = true;
				 }

				 if(!$scope.chkStatus){

					 $scope.user.securityQuestion.customQuestion = false;
				 }
				 userService.saveQuestion($scope.user)
				 .then(
						 function(response) {
							 if(response){
								 var success=response.success;
								 $scope.oldConfirmed=response.passwordMatch;
								 if(success){
									 $rootScope.curModal.close();
									 $scope.logout();
								 }
							 }

						 })
			 }

			 $scope.saveUser=function(){

//				 if($scope.user.password.length != 6 && $scope.user.password.length < 6){
//				 $scope.`.$valid = false;
//				 }

				 if(!$scope.USER.$valid){

					 $scope.alerts=[];
					 $scope.alerts.push({msg: 'Some of the fields are invalid! please verify again'})
					 return;
				 }

//				 if($scope.user.password == null || $scope.confirmPassword==null || $scope.user.password != $scope.confirmPassword){
//				 $scope.wrongConfirmPass = true;
//				 return;
//				 }
				 userService.saveUser($scope.user)
				 .then(
						 function(response) {
							 if(response){
								 var success=response.success;
								 if(success){
									 $scope.user = response.user;
									 $scope.alerts=[];
									 alert("User Saved Successfully");
									 $scope.redirectToUser($scope.user.userId);
								 }

								 if(!success){
									 $scope.alerts=[];
									 $scope.alerts.push({msg: 'This User Name Or Email Id Already Exists!! Choose Different User Name Or Email Id'});
									 return;
								 }
							 }
						 })
			 };

			 $scope.resetAlert = function(){
				 $scope.alerts=[];	 
			 }

			 $scope.updateUser=function(){

				 userService.updateUser($scope.user)
				 .then(
						 function(response) {
							 if(response){
								 var success=response.success;
								 if(success){
									 $scope.user = response.user;
									 $scope.isEdit = false;
									 $scope.alerts=[];
									 alert("User Updated Successfully");
								 }
							 }
						 })
			 }

			 $scope.saveAndUpdateUser = function(){

				 if(!$scope.user.userId){
					 $scope.saveUser();
				 }
				 else{
					 $scope.updateUser();
				 }
			 }

			 $scope.allLetter = function(inputtxt)  
			 {  
				 var letters = /^[A-Za-z]+$/;  
				 if($scope.user.firstName.value.match(letters))  
				 {  
					 return true;  
				 }  
				 else  
				 {  
					 alert("Fields are not valid");  
					 return false;  
				 }  
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


			 $scope.toggleReadOnly = function(form) {

				 if($scope.isEdit){
					 $('#' + form + ' *').attr('readonly',
							 true);
					 $('#' + form + ' select')
					 .attr('disabled', true);
					 $('#' + form + ' input[type="radio"]')
					 .attr('disabled', true);
					 $('#' + form + ' input[type="checkbox"]')
					 .attr('disabled', true);
					 $('#' + form + ' input[type="button"]')
					 .attr('disabled', true);
					 $scope.isEdit = !$scope.isEdit;
				 }

				 else{
					 $('#' + form + ' *').attr('readonly',
							 false);
					 $('#' + form + ' select')
					 .attr('disabled', false);
					 $('#' + form + ' input[type="radio"]')
					 .attr('disabled', false);
					 $('#' + form + ' input[type="checkbox"]')
					 .attr('disabled', false);
					 $('#' + form + ' input[type="button"]')
					 .attr('disabled', false);
					 $scope.isEdit = !$scope.isEdit;
				 }
			 };

			 $scope.toggleReadOnly('USER');

			 $scope.isCreateOrUpdatePrivileged=function(){
				 return !($scope.isPrivileged('CREATE_USER'));
			 }

			 $scope.isViewPrivileged=function(){
				 return !($scope.isPrivileged('CREATE_USER')) && !($scope.isPrivileged('VIEW_USER')) && !($scope.isPrivileged('USER_ADMINISTRATION'));
			 }
		 } ]);
