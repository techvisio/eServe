var workItemModule = angular.module('workItemModule', []);

workItemModule
.controller(
		'workItemController',
		[
		 '$scope',
		 '$state',
		 '$rootScope',
		 'workItemService',
		 'customerService',
		 '$modal',
		 '$http',
		 'masterdataService',
		 'workitem',
		 function($scope, $state, $rootScope,workItemService,customerService,$modal,$http,masterdataService,workitem) {

			 $scope.workItemReminder = {};
			 $scope.workItems = [];
			 $scope.workitem = {};


			 $scope.getUnitInfoByEntityIdAndEntityType = function(entityId, entityType){
				 console.log('getting units');

				 workItemService.getUnitInfoByEntityIdAndEntityType(entityId, entityType)
				 .then(function(response) {
					 console.log(response);
					 if (response) {
						 $scope.unitInfo = response;
					 } 
				 })	
			 };

			 if(workitem){
				 $scope.getUnitInfoByEntityIdAndEntityType(workitem.entityId,workitem.entityType);
				 $scope.workitem = workitem;
			 }			 
			 $scope.setWorkitem = function(workitem){
				 $scope.workitem = workitem;
			 };			 

			 $scope.init = function() {
				 console
				 .log('getting masterdata for customer');

				 masterdataService
				 .getCustomerMasterData()
				 .then(
						 function(data) {
							 console.log(data);
							 if (data) {
								 $scope.serverModelData = data.responseBody;
							 } else {
								 console.log('error');
							 }
						 });
			 };

			 $scope.getWorkItemByUserIdAndWorkType = function(){
				 console.log('getting work Item');
				 if(angular.isUndefined($scope.workitem.workType)){
					 $scope.workitem.workType = "";
				 }
				 if(angular.isUndefined($scope.workitem.status)){
					 $scope.workitem.status = "";
				 }

				 customerService.getWorkItemByUserIdAndWorkType($rootScope.user.userId, $scope.workitem.workType, $scope.workitem.status)
				 .then(function(response) {
					 console.log(response);
					 if (response) {
						 $scope.workItems = response;
					 } 
				 })	
			 };

			 $scope.createComplaintByPms = function(){

				 workItemService.createComplaintByPms($scope.unitInfo, $scope.workitem.workItemId)
				 .then(function(response) {
					 console.log(response);
					 if (response) {
						 $scope.customerComplaint = response;
						 $scope.redirectToComplaintScreen($scope.customerComplaint.complaintId);
					 } 
				 })	
			 }

			 $scope.redirectToComplaintScreen=function(complaintId){
				 $state.go('complaintScreen',{entityId:complaintId} );
			 }

			 $scope.redirectToWorkitemScreen=function(currentWorkitemId){
				 $state.go('workitem',{workitemId:currentWorkitemId});
			 }

			 $scope.addComment = function() {

				 $rootScope.curModal = $modal.open({
					 templateUrl: 'workitem/workItemAddComment.html',
					 scope:$scope,
					 controller: function (workItemService,$scope) {

						 $scope.comment = "";
						 $scope.saveComment = function() {
							 console.log('save comment called');
							 var genericRequest={"bussinessObject":$scope.workitem,"contextInfo":{"comment":$scope.comment}};
							 workItemService.saveComment(genericRequest)
							 .then(function(response) {
								 console.log('comment Data received from service : ');
								 console.log(response);
								 if (response) {
									 $scope.commentList = response;
									 $rootScope.curModal.close();
									 alert("Comment Saved Successfully")
								 } 
							 })
						 };

						 $scope.closePopup = function(){
							 $rootScope.curModal.close();
						 }
					 },
					 backdrop:'static',
					 keyboard: false
				 });
			 };

		 } ]);