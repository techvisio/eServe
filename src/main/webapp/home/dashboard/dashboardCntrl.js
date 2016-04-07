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

			$scope.complaintsBySLA = {
					labels : ["Past", "Today", "Due"],
					data : [3, 6, 8],
					options:{legendTemplate : "<ul class=\"<%=name.toLowerCase()%>-legend\"><% for (var i=0; i<segments.length; i++){%><li><span style=\"background-color:<%=segments[i].fillColor%>\"></span><%if(segments[i].label){%><%=segments[i].label%><%}%></li><%}%></ul>"}
			};

			$scope.complaintsByAssignment = {
					labels : ["Assigned", "Unassigned"],
					data : [10, 6],
					options:{legendTemplate : "<ul class=\"<%=name.toLowerCase()%>-legend\"><% for (var i=0; i<segments.length; i++){%><li><span style=\"background-color:<%=segments[i].fillColor%>\"></span><%if(segments[i].label){%><%=segments[i].label%><%}%></li><%}%></ul>"}
			};
			$scope.complaintsByPriority = {
					labels : ["High ","Midium", "Low"],
					data : [3,5,6],
					options:{legendTemplate : "<ul class=\"<%=name.toLowerCase()%>-legend\"><% for (var i=0; i<segments.length; i++){%><li><span style=\"background-color:<%=segments[i].fillColor%>\"></span><%if(segments[i].label){%><%=segments[i].label%><%}%></li><%}%></ul>"}
			};

		} ]);