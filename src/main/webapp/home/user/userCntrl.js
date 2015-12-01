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
			 $scope.customQuestion = false;
			 $scope.wrongNewPass = false;
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

//			 $scope.getCurrentPassword=function(){
//			 userService.getCurrentPassword($scope.user.userId)
//			 .then(
//			 function(existingUserPassword) {
//			 console
//			 .log('user Data received from service in controller : ');
//			 console.log(existingUserPassword);
//			 if (existingUserPassword) {
//			 $scope.userPassword=existingUserPassword;
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

				 if($scope.user.newPassword==null){
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

			 $scope.saveUser=function(){

				 if($scope.user.password.length != 6 && $scope.user.password.length < 6){
					 $scope.userForm.$valid = false;
					 $scope.alerts=[];
					 $scope.alerts.push({msg: 'Some of the fields are invalid! please verify again'});
				 }

				 if(!$scope.userForm.$valid){

					 $scope.alerts=[];
					 $scope.alerts.push({msg: 'Some of the fields are invalid! please verify again'})
					 return;
				 }

				 if($scope.user.password == null || $scope.confirmPassword==null || $scope.user.password != $scope.confirmPassword){
					 $scope.wrongConfirmPass = true;
					 return;
				 }
				 userService.saveUser($scope.user)
				 .then(
						 function(response) {
							 if(response){
								 var success=response.success;
								 $scope.user = response.user;
								 $scope.redirectToUser($scope.user.userId);
								 if(!success){
									 $scope.alerts=[];
									 $scope.alerts.push({msg: 'This User Name Or Email Id Already Exists!! Choose Different User Name Or Email Id'});
									 return;
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

			 //start
			 $scope.today = function() {
				 $scope.dt = new Date();
			 };
			 $scope.today();

			 $scope.clear = function () {
				 $scope.dt = null;
			 };

			 // Disable weekend selection
			 $scope.disabled = function(date, mode) {
				 return ( mode === 'day' && ( date.getDay() === 0 || date.getDay() === 6 ) );
			 };

			 $scope.toggleMin = function() {
				 $scope.minDate = $scope.minDate ? null : new Date();
			 };
			 $scope.toggleMin();
			 $scope.maxDate = new Date(2020, 5, 22);

			 $scope.open = function($event) {
				 $scope.status.opened = true;
			 };

			 $scope.setDate = function(year, month, day) {
				 $scope.dt = new Date(year, month, day);
			 };

			 $scope.dateOptions = {
					 formatYear: 'yy',
					 startingDay: 1
			 };

			 $scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
			 $scope.format = $scope.formats[0];

			 $scope.status = {
					 opened: false
			 };

			 var tomorrow = new Date();
			 tomorrow.setDate(tomorrow.getDate() + 1);
			 var afterTomorrow = new Date();
			 afterTomorrow.setDate(tomorrow.getDate() + 2);
			 $scope.events =
				 [
				  {
					  date: tomorrow,
					  status: 'full'
				  },
				  {
					  date: afterTomorrow,
					  status: 'partially'
				  }
				  ];

			 $scope.getDayClass = function(date, mode) {
				 if (mode === 'day') {
					 var dayToCheck = new Date(date).setHours(0,0,0,0);

					 for (var i=0;i<$scope.events.length;i++){
						 var currentDay = new Date($scope.events[i].date).setHours(0,0,0,0);

						 if (dayToCheck === currentDay) {
							 return $scope.events[i].status;
						 }
					 }
				 }

				 return '';
			 };
			 //end


		 } ]);
