var erp = angular
.module('erp', [
                'ngRoute',
                'ui.bootstrap',
                'ui.router',
                'googlechart'
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

erp
.controller("GenericChartCtrl", function ($scope) {
    $scope.chartObject = {};
    
    $scope.chartObject.type = "PieChart";
    
    $scope.chartObject.data = {"cols": [
        {id: "t", label: "Topping", type: "string"},
        {id: "s", label: "Slices", type: "number"}
    ], "rows": [
        {c: [
            {v: "Overdue Previous"},
            {v: 3},
        ]},
        {c: [
            {v: "Due current month"},
            {v: 1},
        ]},
        {c: [
            {v: "Due next month"},
            {v: 2},
        ]}
    ]};

    $scope.chartObject.options = {
    		tooltip:{textStyle:{fontName:'"Arial"'}},
    	      title: 'PMS Schedules Status',titleTextStyle:{fontName:'"Arial"'},
    };
    
 $scope.workOrder = {};
    
    $scope.workOrder.type = "PieChart";
    
    $scope.workOrder.data = {"cols": [
        {id: "t", label: "Open", type: "string"},
        {id: "s", label: "Closed", type: "number"}
    ], "rows": [
        {c: [
            {v: "Opened Today"},
            {v: 3},
        ]},
        {c: [
            {v: "Pending Previous"},
            {v: 1},
        ]}
    ]};

    $scope.workOrder.options = {
    		tooltip:{textStyle:{fontName:'"Arial"'}},
    	      title: 'Open Work Order Status',titleTextStyle:{fontName:'"Arial"'},
    };
});


b