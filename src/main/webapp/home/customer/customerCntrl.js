var customerModule = angular.module('customerModule', []);

customerModule.controller('customerController', ['$scope','customerService','$state','$filter','injectedData','NgTableParams','masterdataService', 
                                                 function($scope,customerService,$state,filter,injectedData,NgTableParams,masterdataService) {

	
	$scope.customer={};
	$scope.customers=[];
	
	$scope.dummyEquipmentDetails ={};
	$scope.dummyUnit ={
			 "equipmentDetails" : [ {} ]
	};
	$scope.customer.units=[];
	$scope.customer.units.push(angular
			 .copy($scope.dummyUnit));
	
	if(injectedData && injectedData.data){
		 $scope.customer = injectedData.data.responseBody;
		 }
	
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
				 })
	 };

	
	$scope.addMachine = function() {
		for (var i = 0; i < $scope.customer.units.length; i++) {

			 				var equipmentDetail = angular
							 .copy($scope.dummyEquipmentDetails);
			 				 $scope.customer.units[i].equipmentDetails
							 .push(equipmentDetail);
		 }

	 };

	 $scope.removeMachine = function(index) {

		 for (var i = 0; i < $scope.customer.units.length; i++) {
			 $scope.customer.units[i].equipmentDetails
		 .splice(index, 1);
		 }
	 };

	 $scope.addUnit = function() {

		 var unitDtl = angular
		 .copy($scope.dummyUnit);
		 $scope.customer.units.push(unitDtl);
	 };

	 $scope.redirectToCustomerDtlScreen=function(currentCustomerId){
			$state.go('customer',{customerId:currentCustomerId});
		}
	 
	$scope.getCustomer = function(){
		customerService.getCustomer($scope.customer.customerId)
		.then(function(response) {
			console.log('customer Data received in controller : ');
			console.log(response);
			if (response != null && response.data != null && response.data.responseBody != null) {
				$scope.customer = response.data.responseBody;
			} 
		})
	}

	$scope.getCustomers = function(){
		customerService.getCustomers()
		.then(function(response) {
			console.log('all customers Data received in controller : ');
			console.log(response);
			if (response != null && response.data != null && response.data.responseBody != null) {
				$scope.customers = response.data.responseBody;
			} 
		})
	}
	
	$scope.saveCustomer = function() {
		console.log('save customer called');
		customerService.saveCustomer($scope.customer)
		.then(function(response) {
			console.log('customer Data received from service : ');
			console.log(response);
			if (response != null && response.data != null && response.data.responseBody != null) {
				$scope.customer = response.data.responseBody;
				alert("Your Records Saved Successfully")
			} 
		})
	};

	$scope.getUnit = function(){
		customerService.getUnit($scope.customer.customerId)
		.then(function(response) {
			console.log('unit Data received in controller : ');
			console.log(response);
			if (response != null && response.data != null && response.data.responseBody != null) {
				$scope.units = response.data.responseBody;
			} 
		})
	}

	$scope.saveUnit = function() {
		console.log('save unit called');
		customerService.saveUnit($scope.customer.units, $scope.customer.customerId)
		.then(function(response) {
			console.log('unit Data received from service : ');
			console.log(response);
			if (response != null && response.data != null && response.data.responseBody != null) {
				$scope.units = response.data.responseBody;
				alert("Your Records Saved Successfully")
			} 
		})
	};
	
	var self = this;
	var data = $scope.customers;
	self.tableParams = new NgTableParams({}, { data: data});
	
} ]);