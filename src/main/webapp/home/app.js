var erp = angular
.module('erp', [
                'ngRoute',
                'ui.bootstrap',
                'ui.router',
                //'smart-table',
                ]);

erp.run(['$rootScope', '$location',
      function ( $rootScope, $location) {
	
	 $rootScope.showSidebar = true;
	 
	 $rootScope.isSel = function (page) {
	        var currentRoute = $location.path().substring(1) || 'home',
	            pageR = new RegExp(page + "\/");
	        return page === currentRoute || currentRoute.match(pageR)
	            ? 'selected' : '';
	    };
	    
}]
);

