var userModule = angular.module('activityModule', ['ui.bootstrap.buttons']);

userModule
.controller(
		'activityController',
		[
		 '$scope',
		 '$state',
		 'activityService',
		 'user',
		 
		 function($scope, $state,activityService,user) {

		 } ]);
