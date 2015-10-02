var sidebarModule=angular.module('sidebarModule', []);

sidebarModule
.controller(
		'sidebarController',
		[
		 '$scope',
		 '$rootScope',
		 function($scope,$rootScope) {
			    $scope.items = [
			        {
			            name: "Enquiry",
			            subItems: [
			                {name: "Dashboard"},
			                {name: "Create New"},
			                {name: "Search Existing"}
			            ]
			        },
			        {
			            name: "Admission",
			            subItems: [
							{name: "Dashboard"},
							{name: "Create New"},
							{name: "Search Existing"}
			            ]
			        },
			        {
			            name: "Transport",
			            subItems: [
			                {name: "SubItem6"}
			            ]
			        }
			    ];
			    
			    $scope.showChilds = function(index){
			        $scope.items[index].active = !$scope.items[index].active;
			        collapseAnother(index);
			    };

			    var collapseAnother = function(index){
			        for(var i=0; i<$scope.items.length; i++){
			            if(i!=index){
			                $scope.items[i].active = false;
			            }
			        }
			    };

		 } ]);