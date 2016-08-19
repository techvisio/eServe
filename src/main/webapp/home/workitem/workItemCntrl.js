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

			 $scope.workItems = [];
			 $scope.workitem = {};
			 $scope.workItemSearchCriteria={};
			 $scope.workItemSearchCriteria.workType="";
			 $scope.workItemSearchCriteria.status="";
			 $scope.showAllComments=false;

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
				 var workItemType=$scope.workItemSearchCriteria.workType;
				 var workItemStatus=$scope.workItemSearchCriteria.status;
				 if(angular.isUndefined(workItemType)){
					 workItemType = "";
				 }
				 if(angular.isUndefined(workItemStatus)){
					 workItemStatus = "";
				 }

				 customerService.getWorkItemByUserIdAndWorkType($rootScope.user.userId, workItemType, workItemStatus)
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
						 $scope.pmsWorkOrder = response;
						 $scope.redirectToComplaintScreen($scope.pmsWorkOrder.workOrderId);
					 } 
				 })	
			 }

			 $scope.redirectToComplaintScreen=function(complaintId){
				 $state.go('pmsComplaint',{entityId:complaintId} );
			 }

			 $scope.redirectToWorkitemScreen=function(currentWorkitemId){
				 $state.go('workitem',{workitemId:currentWorkitemId});
			 }
			 
			 $scope.redirectToUnit=function(unitId){
				 $state.go('unitApproval',{entityId:unitId} );
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
									 $scope.workitem.comments = response;
									 $rootScope.curModal.close();
									 alert("Comment Saved Successfully");
									 $scope.redirectToworkItemScreen($scope.workitem.workItemId);
								 } 
							 })
						 };

						 $scope.redirectToworkItemScreen = function(currentWorkitemId){
								$state.reload('workitem',{workitemId:currentWorkitemId});
								 
							}
						 
						 $scope.closePopup = function(){
							 $rootScope.curModal.close();
						 }
					 },
					 backdrop:'static',
					 keyboard: false
				 });
			 };

			 $scope.getCommentList = function(){
					workItemService.getCommentList($scope.workitem.workItemId)
					.then(function(response) {
						console.log('getting all comments  received in controller : ');
						console.log(response);
						if (response) {
							$scope.workitem.comments = response;
						} 
					})
				};
			 
				$scope.createWorkItemForSalesRenewal = function(unitInfo){
					workItemService.createWorkItemForSalesRenewal(unitInfo);
				}
		 } ]);