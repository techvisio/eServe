var customerModule = angular.module('customerModule', []);

customerModule.controller('customerController', ['$scope','customerService','$state','$filter','injectedData','masterdataService', 
                                                 function($scope,customerService,$state,filter,injectedData,masterdataService) {


	$scope.customer={};
	$scope.customers=[];
	
	$scope.dummyEquipmentDetails ={};
	$scope.dummyUnit ={
			"equipmentDetails" : [ {} ]
	};
	
	$scope.unit={};
	$scope.customer.units=[];
	
	$scope.customer.units.push(angular
			.copy($scope.dummyUnit));

	if(injectedData){
		$scope.customer = injectedData;
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


	$scope.addMachine = function(object) {

		var equipmentDetail = angular
		.copy($scope.dummyEquipmentDetails);
		equipmentDetail.unitId = object.unitId;
		 object.equipmentDetails
		 .push(equipmentDetail);

	};

	$scope.removeMachine = function(object, index) {
		 console.log(index);
		 object.equipmentDetails.splice(index, 1);
	 };
	 
	$scope.addUnit = function() {

		var unitDtl = angular
		.copy($scope.dummyUnit);
		$scope.customer.units.push(unitDtl);
	};

	$scope.removeUnit = function(index) {

		$scope.customer.units.splice(index, 1);
	};

	$scope.redirectToCustomerDtlScreen=function(currentCustomerId){
		$state.go('customer',{customerId:currentCustomerId});
	}
	
	$scope.redirectToComplaintScreen=function(currentCustomerId){
		$state.go('complaint',{customerId:currentCustomerId});
	}

	$scope.getCustomer = function(){
		customerService.getCustomer($scope.customer.customerId)
		.then(function(response) {
			console.log('customer Data received in controller : ');
			console.log(response);
			if (response) {
				$scope.customer = response;
			} 
		})
	}

	$scope.getCustomers = function(){
		customerService.getCustomers()
		.then(function(response) {
			console.log('all customers Data received in controller : ');
			console.log(response);
			if (response) {
				$scope.customers = response;
			} 
		})
	}

	$scope.saveCustomer = function() {
		console.log('save customer called');
		customerService.saveCustomer($scope.customer)
		.then(function(response) {
			console.log('customer Data received from service : ');
			console.log(response);
			if (response) {
				$scope.customer = response;
				alert("Your Records Saved Successfully")
				$scope.redirectToCustomerDtlScreen($scope.customer.customerId);
			} 
		})
	};

	$scope.getUnit = function(){
		customerService.getUnit($scope.customer.customerId)
		.then(function(response) {
			console.log('unit Data received in controller : ');
			console.log(response);
			if (response) {
				$scope.units = response;
			} 
		})
	}

	$scope.saveUnit = function(object) {
		console.log('save unit called');
		customerService.saveUnit(object, $scope.customer.customerId)
		.then(function(response) {
			console.log('unit Data received from service : ');
			console.log(response);
			if (response) {
				$scope.units = response;
				alert("Your Records Saved Successfully")
			} 
		})
	};

} ]);