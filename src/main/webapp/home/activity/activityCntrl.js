var activityModule = angular.module('activityModule', []);

activityModule
.controller(
		'activityController',['$scope','$state','activityService',
		                      function($scope, $state,activityService) {
			 
			 $scope.startDate=false;
			 $scope.endDate=false;
			 
			 $scope.activitySearchCriteria={};
			 $scope.activities=[];

			 $scope.getActivityByDate=function(){
					activityService.getActivityByDate($scope.activitySearchCriteria)
					.then(
							function(data) {
								console.log(data);
								if (data) {
									
									 $scope.activities = data;
									 
								} else {
									console.log('error');
								}
							})
				}

			 
			 
		 } ]);
