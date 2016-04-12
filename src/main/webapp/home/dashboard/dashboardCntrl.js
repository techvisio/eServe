var dashboardModule=angular.module('dashboardModule',['chart.js']);

dashboardModule
.controller(
		'dashboardController',
		['dashboardService','$scope', function(dashboardService,$scope) {

			$scope.selectedModule="complaint";
			$scope.noticeBoardCount;

			$scope.updateModule=function(module){
				$scope.selectedModule=module;
			};
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

//			$scope.complaintsByPriority = {
//			labels : ["High ","Midium", "Low"],
//			data : [3,5,6],

//			};

		} ]);