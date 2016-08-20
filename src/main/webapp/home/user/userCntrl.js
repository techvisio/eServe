var userModule = angular.module('userModule', ['ui.bootstrap.buttons']);

userModule
.controller(
		'userController',
		[
		 '$scope',
		 '$state',
		 '$rootScope',
		 'userService',
		 'contextObject',
		 'Operation',
		 'masterdataService',
		 '$modal',
		 '$http',
		 'NgTableParams',
		 function($scope, $state, $rootScope,userService,contextObject,Operation,masterdataService,$modal,$http,NgTableParams) {
			 $scope.form={};
//			 $scope.isUserCollapsed= true;
//			 $scope.isPrivilegesCollapsed= false;
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


			 $scope.toggleReadOnly = function(form,isEdit) {
				 if(!isEdit){
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
				 }
			 };



			 $scope.allUserRoles=[];

			 $scope.createPriviledgeGrp=function(priviledgesList){
				 $scope.priviledgeGrp={};
				 angular.forEach(priviledgesList, function(privilege) {
					 var priviledgeTypeGrp=$scope.priviledgeGrp[privilege.privilege.type];
					 if(!priviledgeTypeGrp){
						 $scope.priviledgeGrp[privilege.privilege.type]=[[]];
						 priviledgeTypeGrp=$scope.priviledgeGrp[privilege.privilege.type];
					 }
					 var lastList=priviledgeTypeGrp[priviledgeTypeGrp.length-1];
					 if(lastList.length>1)
					 {
						 priviledgeTypeGrp.push([privilege]);
					 }
					 else
					 {
						 lastList.push(privilege);
					 }
				 });
			 }

			 if(Operation && Operation=='createNewUser' ){
				 if(!contextObject){
					 userService.getUserprivileges()
					 .then(
							 function(userPrivileges) {
								 console
								 .log('user privilegs received from service in controller : ');
								 console.log(userPrivileges);
								 if (userPrivileges) {
									 $scope.isNew = true;
									 $scope.user.privileges=userPrivileges;
									 $scope.createPriviledgeGrp(userPrivileges);
								 }
							 })
				 }			 
			 }

			 if(Operation && Operation=='passresetmodal' ){
				 if(contextObject){
					 $scope.isNew = false;
					 $scope.user = contextObject;
					 $scope.toggleReadOnly('USER', contextObject.edited);
				 }
			 }

			 if(Operation && Operation=='viewUser' ){
				 if(contextObject){
					 $scope.isNew = false;
					 $scope.user = contextObject;
					 $scope.createPriviledgeGrp(contextObject.privileges);
					 $scope.toggleReadOnly('USER',false);
				 }			 
			 }

			 $scope.authenticateUser=function(){
				 userService.authenticateUser($scope.form);
			 }

			 $scope.redirectToUser=function(currentUserId){
				 if($rootScope.isPrivileged('VIEW_USER') || $rootScope.isPrivileged('CREATE_USER')){
					 $state.go('viewUser',{entityId:currentUserId});
				 }
				 else{
					 $rootScope.showNotHavePrivilegeModel();
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
				 if($rootScope.isPrivileged('USER_ADMINISTRATION')){
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
							 })}
				 else{
					 $rootScope.showNotHavePrivilegeModel();
				 }
			 }

			 $scope.logout=function(){

				 window.location=document.getElementById('logout').href;
			 }

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

				 if(!$scope.USER.$valid){
					 $scope.alerts=[];
					 $scope.alerts.push({msg: 'Some of the fields are invalid! please verify again'})
					 return;
				 }
				 userService.saveUser($scope.user)
				 .then(
						 function(response) {
							 if(response){
								 $scope.user = response;
								 $scope.alerts=[];
								 $rootScope.showAlertModel('User Saved Successfully With User Name '+ $scope.user.userName, 'Operation Successful');
								 $scope.redirectToUser($scope.user.userId);
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
								 $scope.user = response;
								 $scope.alerts=[];
								 $rootScope.showAlertModel('User Updated Successfully With User Name '+$scope.user.userName, 'Operation Successful')
								 $state.reload('viewUser',{entityId:$scope.user.userId});
							 }
						 })
			 }

			 $scope.saveAndUpdateUser = function(){
				 if($scope.isCreateOrUpdatePrivileged()){
					 if(!$scope.user.userId){
						 $scope.saveUser();
					 }
					 else{
						 $scope.updateUser();
					 }
				 }
				 else{
					 $rootScope.showNotHavePrivilegeModel();
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


			 $scope.isCreateOrUpdatePrivileged=function(){
				 return ($rootScope.isPrivileged('CREATE_USER'));
			 }

			 $scope.isViewPrivileged=function(){
				 return ($rootScope.isPrivileged('CREATE_USER')) || ($rootScope.isPrivileged('VIEW_USER')) || ($rootScope.isPrivileged('USER_ADMINISTRATION'));
			 }

			 $scope.filterUser = function() {

				 if($scope.isViewPrivileged){
					 $rootScope.curModal = $modal.open({
						 templateUrl: 'user/userCriteria.html',
						 scope:$scope,
						 controller: function (userService,$scope) {

							 $scope.getUserByCriteria = function(){
								 $scope.tableParams.reload();
								 $rootScope.curModal.close();
							 }

						 },
					 });
				 }

				 else{
					 $rootScope.showNotHavePrivilegeModel();
				 }
			 };



			 $scope.tableParams = new NgTableParams({}, {
				 getData: function($defer,params) {
					 var sortBy="USER_NAME";
					 var isAsc=false;
					 var pageNo=params.page();
					 var pageSize=params.count();
					 if(params.sorting()){
						 for (var attribute in params.sorting()) {
							 if (params.sorting().hasOwnProperty(attribute)) {
								 sortBy=attribute;
								 var ascDsc=params.sorting()[attribute];
								 if(ascDsc==='asc'){
									 isAsc=true;  
								 }
							 }
						 }
					 }
					 $scope.searchCriteria.pageSize=pageSize;
					 $scope.searchCriteria.pageNo=pageNo;
					 $scope.searchCriteria.sortBy=sortBy;
					 $scope.searchCriteria.isAscending=isAsc;

					 userService.getUserByCriteria($scope.searchCriteria)
					 .then(
							 function(users) {
								 if(users){
									 params.total(users.totalCount);
									 $defer.resolve(users.objectData);
								 }
							 })

				 }
			 });

			 $scope.lockUserEntity = function()
			 {
				 $scope.entityLock = {};
				 $scope.entityLock.entityId = $scope.user.userId;
				 $scope.entityLock.entityType = 'USER';

				 if($scope.isCreateOrUpdatePrivileged){
					 userService.lockEntity($scope.entityLock)
					 .then(
							 function(user) {
								 console
								 .log('Locking user entity in controller');
								 console.log(user);
								 if (user) {
									 $scope.user=user;
									 $scope.createPriviledgeGrp($scope.user.privileges);
									 $scope.toggleReadOnly('USER', $scope.user.edited);
								 }
							 })
				 }
				 else{
					 $rootScope.showNotHavePrivilegeModel();					 
				 }
			 }

			 $scope.unlockUserEntity = function(){

				 $scope.entityLock = {};
				 $scope.entityLock.entityId = $scope.user.userId;
				 $scope.entityLock.entityType = 'USER';

				 userService.unlockEntity($scope.entityLock)
				 .then(
						 function(user) {
							 console
							 .log('Unlocking user entity in controller');
							 console.log(user);
							 if (user) {
								 $scope.user=user;
								 $scope.toggleReadOnly('USER', $scope.user.edited);
							 }
						 })
			 }

			 $scope.getEmailId = function(){
				 userService.getEmailId($scope.user.emailId)
				 .then(function(response) {
					 console.log('getting user by emailId in controller : ');
					 console.log(response);
					 if (response) {
						 $scope.userForEmail = response;

						 if(angular.isUndefined($scope.user.userId) || $scope.userForEmail.userId != $scope.user.userId){
							 $rootScope.showAlertModel('This Email Id is Already Exists, Choose Different Email Id', 'Duplicate Record');
							 return;
						 }
					 } 
				 })
			 }

			 $scope.getUserName = function(){
				 userService.getUserName($scope.user.userName)
				 .then(function(response) {
					 console.log('all users Data received in controller : ');
					 console.log(response);
					 if (response) {
						 $scope.userForUserName = response;

						 if(angular.isUndefined($scope.user.userId) || $scope.userForUserName.userId != $scope.user.userId){
							 $rootScope.showAlertModel('This User Name is Already Exists, Choose Different User Name', 'Duplicate Record');
							 return;
						 }
					 } 
				 })
			 }


		 } ]);
