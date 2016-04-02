var dashboardModule=angular.module('dashboardModule',['chart.js']);

dashboardModule
.controller(
		'dashboardController',
		['$scope', function($scope) {
			 
			    $scope.complaintsBySLA = {
			        	labels : ["Past", "Today", "Due"],
			        	data : [3, 6, 8],
			        	options:{legendTemplate : "<ul class=\"<%=name.toLowerCase()%>-legend\"><% for (var i=0; i<segments.length; i++){%><li><span style=\"background-color:<%=segments[i].fillColor%>\"></span><%if(segments[i].label){%><%=segments[i].label%><%}%></li><%}%></ul>"}
			        };
			 
			 
		 } ]);