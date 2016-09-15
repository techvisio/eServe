var dashboardModule=angular.module('dashboardModule',['chart.js']);

dashboardModule
.controller(
		'dashboardController',
		['dashboardService','workItemService','$scope','$rootScope','$state','complaint','isDashboard', function(dashboardService,workItemService,$scope,$rootScope,$state,complaint,isDashboard) {

			
			$scope.workItemSearchCriteria={};
			$scope.selectedModule="complaint";
			$scope.noticeBoardCount;

			$scope.updateModule=function(module){
				$scope.selectedModule=module;
			};
			
			if(!complaint){
				if(isDashboard){
					$rootScope.heading='Dashboard'
				}
			}
			if(complaint){
				if(isDashboard){
					$rootScope.heading='Dashboard'
				}
				$scope.complaintSearchData = complaint;
			}
			
			
			$scope.init=function(){
				$scope.getNoticeBoardCount();
				$scope.getComplaintCountByAssignment();
				$scope.getComplaintCountByPriority();
				$scope.getComplaintCountBySlaDate();
			}


			$scope.getNoticeBoardCount=function(){

				dashboardService.getNoticeBoardCount()
				.then(
						function(data) {
							console.log(data);
							if (data) {
								$scope.noticeBoardCount = data;
							} else {
								console.log('error');
							}
						})
			}


			$scope.getComplaintCountBySlaDate=function(){

				dashboardService.getComplaintCountBySlaDate()
				.then(
						function(data) {
							console.log(data);
							if (data) {
								$scope.complaintsBySLA = data;
								$scope.complaintsBySLA.options={legendTemplate : "<ul class=\"<%=name.toLowerCase()%>-legend\"><% for (var i=0; i<segments.length; i++){%><li><span style=\"background-color:<%=segments[i].fillColor%>\"></span><%if(segments[i].label){%><%=segments[i].label%><%}%></li><%}%></ul>"}

							} else {
								console.log('error');
							}
						})
			}


			$scope.getComplaintCountByPriority=function(){

				dashboardService.getComplaintCountByPriority()
				.then(
						function(data) {
							console.log(data);
							if (data) {
								$scope.complaintsByPriority = data;
								$scope.complaintsByPriority.options={legendTemplate : "<ul class=\"<%=name.toLowerCase()%>-legend\"><% for (var i=0; i<segments.length; i++){%><li><span style=\"background-color:<%=segments[i].fillColor%>\"></span><%if(segments[i].label){%><%=segments[i].label%><%}%></li><%}%></ul>"}
							} else {
								console.log('error');
							}
						})
			}


			$scope.getComplaintCountByAssignment=function(){

				dashboardService.getComplaintCountByAssignment()
				.then(
						function(data) {
							console.log(data);
							if (data) {
								$scope.complaintsByAssignment = data;
								$scope.complaintsByAssignment.options={legendTemplate : "<ul class=\"<%=name.toLowerCase()%>-legend\"><% for (var i=0; i<segments.length; i++){%><li><span style=\"background-color:<%=segments[i].fillColor%>\"></span><%if(segments[i].label){%><%=segments[i].label%><%}%></li><%}%></ul>"}

							} else {
								console.log('error');
							}
						})
			}

			$scope.redirectToComplaintGraphScreen=function(paramType, paramCode){
				$state.go('complaintGraph',{type:paramType,code:paramCode});
			}
			
			$scope.redirectToCustomerDtlScreen=function(currentCustomerId){
				$state.go('customer',{entityId:currentCustomerId});
			}
			
			$scope.redirectToComplaintScreen=function(currentComplaintId ){

				$state.go('complaintScreen',{entityId:currentComplaintId});
			}
			
			$scope.getComplaintBySlaCount=function(points, evt){

				console.log('Complaint data by sla type')
				var label = points[0]["label"];
				var type = "SLA";
				$scope.redirectToComplaintGraphScreen(type,label);

			}

			$scope.getComplaintByAssignmentCount=function(points, evt){
				console.log('Complaint data by Assignment type')
				var label = points[0]["label"];
				var type = "ASSIGNMENT";
				$scope.redirectToComplaintGraphScreen(type,label);

			}

			$scope.getComplaintByPriorityCount=function(points, evt){
				console.log('Complaint data by Priority type')
				var label = points[0]["label"];
				var type = "PRIORITY";
				$scope.redirectToComplaintGraphScreen(type,label);
			}

			$scope.getWorkItembySearchCriteria = function(){
				console.log('getting work Item');
				
				 $scope.workItemSearchCriteria.pageSize=5;
				 $scope.workItemSearchCriteria.pageNo=1;
				 $scope.workItemSearchCriteria.sortBy="UPDATED_ON";
				 $scope.workItemSearchCriteria.isAscending=false;
				 $scope.workItemSearchCriteria.userId=$rootScope.user.userId;

				workItemService.getWorkItembySearchCriteria($scope.workItemSearchCriteria)
				.then(function(response) {
					console.log(response);
					if (response) {
						$scope.workItem = response.objectData;
					} 
				})	
			};
			
			$scope.redirectToWorkitemScreen=function(currentWorkitemId){
				 $state.go('workitem',{workitemId:currentWorkitemId});
			 }
			
		} ]);