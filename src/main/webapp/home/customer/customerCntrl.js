var customerModule = angular.module('customerModule', []);

customerModule.controller('customerController', ['$scope','$window','$rootScope','customerService','$state','$filter','customer','masterdataService','userService',
                                                 function($scope,$window,$rootScope,customerService,$state,filter,customer,masterdataService,userService) {


	$scope.form={};
	$scope.isNew=true;
	$scope.isEdit=false;
	$scope.startDate=false;
	$scope.expireDate=false;
	$scope.showStatus=false;
	$scope.getAllComplaints=false;
	$scope.customer={};
	$scope.customers=[];
	$scope.searchCriteria = {};
	$scope.dummyEquipmentDetails ={};
	$scope.dummyUnit ={
			"equipmentDetails" : [ {} ]
	};

	$scope.customer.address = {};
	$scope.customer.address = angular.copy($scope.dummyAddress);
	$scope.dummyAddress= {};

	$scope.unit={};
	$scope.customer.units=[];

	$scope.customer.units.push(angular
			.copy($scope.dummyUnit));

	if(customer){
		$scope.getAllComplaints = true;
		$scope.isEdit = true;
		$scope.isNew=false;
		angular.forEach(customer.units, function(unit) {
			if (unit.equipmentDetails.length<=0){
				unit.equipmentDetails.push(angular
						.copy($scope.dummyEquipmentDetails));
			}
		});
		$scope.customer = customer;
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

	$scope.getUsers = function() {
		console
		.log('getting users for customer');

		userService
		.getUsers()
		.then(
				function(data) {
					console.log(data);
					if (data) {
						$scope.users = data;
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
		$scope.alerts=[];
		$state.go('customer',{customerId:currentCustomerId});
	}

	$scope.redirectToComplaintScreenByUnitId=function(unit, currentUnitId ){

		if(unit.unitCode==null){
			$scope.alerts=[];
			$scope.alerts.push({msg: 'Non Of Details Are Saved For This Unit'})
			return;
		}
		$state.go('customerToComplaint',{unitId:currentUnitId});
	}
	
	$scope.getCustomerByCriteria=function(){
		customerService.getCustomerByCriteria($scope.searchCriteria)
		.then(
				function(customers) {
					console
					.log('getting customer by criteria in controller : ');
					console.log(customers);
					if (customers) {
						$scope.customers=customers;
					}
				})
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

		if(!$scope.CUSTOMER.$valid || !$scope.UNIT.$valid){

			$scope.alerts=[];
			$scope.alerts.push({msg: 'Some of the fields are invalid! please verify again'})
			return;
		}

		$scope.resetAleart = function(){
			$scope.alerts=[];
		}

		console.log('save customer called');
		customerService.saveCustomer($scope.customer)
		.then(function(response) {
			console.log('customer Data received from service : ');
			console.log(response);
			if (response) {
				var success=response.success;
				if(success){
					$scope.customer = response.customer;
					$scope.alerts=[];
					alert("Customer Saved Successfully");
					$scope.redirectToCustomerDtlScreen($scope.customer.customerId);
				}

				if(!success){
					$scope.alerts=[];
					$scope.alerts.push({msg: 'This Contact No Or Email Id Already Exists!! Enter Different Contact No Or Email Id'});
					return;
				}
			} 
		})
	};

	$scope.saveAndUpdateCustomer = function(){

		if(!$scope.customer.customerId){
			$scope.saveCustomer();			
		}
		else{
			$scope.updateCustomer();
		}
	}

	$scope.updateCustomer = function() {

		customerService.updateCustomer($scope.customer)
		.then(function(response) {
			console.log('customer Data received from service : ');
			console.log(response);
			if (response) {
				var success=response.success;
				if(success){
					$scope.customer = response.customer;
					$scope.alerts=[];
					alert("Customer Saved Successfully");
					$scope.redirectToCustomerDtlScreen($scope.customer.customerId);
				}
			} 
		})
	};

	$scope.countUnit = function(customer) {
		var count = 0;
		for(var i = 0; i <customer.units.length; i++){
			count++;
		}
		return count; 
	}

	
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

		if(!$scope.CUSTOMER.$valid){

			$scope.alerts=[];
			$scope.alerts.push({msg: 'Some of the fields are invalid! please verify again'})
			return;
		}

		$scope.resetAleart = function(){
			$scope.alerts=[];
		}

		customerService.saveUnit(object, $scope.customer.customerId)
		.then(function(response) {
			console.log('unit Data received from service : ');
			console.log(response);
			if (response) {
				$scope.customer.units = response;
				alert("Your Records Saved Successfully")
			} 
		})
	};
	
	if($scope.getAllComplaints){
		$scope.getAllComplaints = function(){
			customerService.getAllComplaints(customer.customerId)
			.then(function(response) {
				console.log('getting all complaints in controller : ');
				console.log(response);
				if (response) {
					$scope.customerComplaints = response;
				} 
			})
		}
	}

	$scope.toggleReadOnly = function(form) {

		if($scope.isEdit){
			$('#' + form + ' *').attr('readonly',
					true);
			$('#' + form + ' input[type="radio"]')
			.attr('disabled', true);
			$('#' + form + ' input[type="checkbox"]')
			.attr('disabled', true);
			$('#' + form + ' input[type="button"]')
			.attr('disabled', true);
			$scope.isEdit = !$scope.isEdit;
		}

		else{
			$('#' + form + ' *').attr('readonly',
					false);
			$('#' + form + ' input[type="radio"]')
			.attr('disabled', false);
			$('#' + form + ' input[type="checkbox"]')
			.attr('disabled', false);
			$('#' + form + ' input[type="button"]')
			.attr('disabled', false);
			$scope.isEdit = !$scope.isEdit;
		}
	};

	$scope.copyCustomerAddress = function(unit,sameAsAbove) {
		console.log('same as above');
		console.log(sameAsAbove);

		var customerAddressClone = angular.copy($scope.customer.address);
		if(unit.address != null || !angular.isUndefined(unit.address)){
			customerAddressClone.addressId = unit.address.addressId;
		}

		else{
			customerAddressClone.addressId = null;
		}

		if (sameAsAbove) {
			console.log('same as above');
			unit.address = customerAddressClone;
		}
		if (!sameAsAbove){
			unit.address = {};
			unit.address.addressId=customerAddressClone.addressId;
		}
	}

	$scope.isPrivileged = function(role){

		var userPrivilege = $rootScope.user.privileges;
		var result=false;
		angular.forEach(userPrivilege, function(privilege) {
			if (privilege.privilege.privilege===role) result= true;
		});
		return result;		
	}

} ]);