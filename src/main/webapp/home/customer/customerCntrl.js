var customerModule = angular.module('customerModule', []);

customerModule.controller('customerController', ['$scope','$window','$rootScope','customerService','$state','$filter','customer','unitComplaint','complaint','masterdataService','userService',
                                                 function($scope,$window,$rootScope,customerService,$state,filter,customer,unitComplaint,complaint,masterdataService,userService) {


	$scope.form={};
	$scope.startDate=false;
	$scope.expireDate=false;
	$scope.newComplaint=false;
	$scope.showStatus=false;
	$scope.customer={};
	$scope.customers=[];
	$scope.searchCriteria = {};
	$scope.customerComplaint = {};
	$scope.customerComplaints = [];
	$scope.dummyEquipmentDetails ={};
	$scope.dummyUnit ={
			"equipmentDetails" : [ {} ]
	};

	$scope.unit={};
	$scope.customer.units=[];

	$scope.complaintResolution={};
	$scope.complaintAssignment={};

	$scope.customer.units.push(angular
			.copy($scope.dummyUnit));

	if(customer){
		$scope.customer = customer;
	}

	$scope.getCustomerForComplaint = function(){
		customerService.getCustomerForComplaint(unitComplaint.customerId)
		.then(function(response) {
			console.log('customer Data received in controller : ');
			console.log(response);
			if (response) {
				$scope.customer = response;
				$scope.customerComplaint.customerId = $scope.customer.customerId;
				$scope.customerComplaint.customerCode = $scope.customer.customerCode;
				$scope.customerComplaint.contactNo = $scope.customer.contactNo;
				$scope.customerComplaint.emailId = $scope.customer.emailId;
				$scope.customerComplaint.customerName = $scope.customer.customerName;
			} 
		})
	}

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

	if(unitComplaint){

		$scope.getCustomerForComplaint();
		$scope.newComplaint = true;
		$scope.customerComplaint.unit = unitComplaint;
	}

	if(complaint){

		$scope.showStatus = true;
//		$scope.newComplaint = true;
		$scope.customerComplaint = complaint;
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

	$scope.redirectToComplaintScreen=function(currentComplaintId ){

		$state.go('complaintScreen',{complaintId:currentComplaintId});
	}

	$scope.redirectToComplaint=function(){
		$state.go('complaint');
	}

	$scope.showconfirmbox = function () {
		if ($window.confirm("Do you want to continue?")){
			$scope.redirectToComplaint();
		}
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
						if($scope.customers.length==0){
							$scope.showconfirmbox();
						}
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

		if(!$scope.customerForm.$valid){

			$scope.alerts=[];
			$scope.alerts.push({msg: 'Some of the fields are invalid! please verify again'})
			return;
		}

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

	$scope.countUnit = function(customer) {

		var count = 0;

		for(var i = 0; i <customer.units.length; i++){

			count++;
		}

		return count; 
	}

	$scope.saveComplaint = function() {
		console.log('save complaint called in controller');
		customerService.saveComplaint($scope.customerComplaint)
		.then(function(response) {
			console.log('complaint Data received from service : ');
			console.log(response);
			if (response) {
				$scope.customerComplaint = response;
				$scope.showStatus = true;
				alert("Your Records Saved Successfully");
				$scope.redirectToComplaintScreen($scope.customerComplaint.complaintId);


			} 
		})
	};


	$scope.saveComplaintResolution = function() {
		console.log('save complaintResolution called in controller');
		customerService.saveComplaintResolution($scope.customerComplaint.complaintId,$scope.customerComplaint.complaintResolution)
		.then(function(response) {
			console.log('complaintResolution Data received from service : ');
			console.log(response);
			if (response) {
				$scope.complaintResolution = response;
				alert("Your Records Saved Successfully")
			} 
		})
	};

	$scope.saveComplaintAssignment = function() {
		console.log('save complaintAssignment called in controller');
		customerService.saveComplaintAssignment($scope.customerComplaint.complaintId,$scope.customerComplaint.complaintAssignment)
		.then(function(response) {
			console.log('complaintAssignment Data received from service : ');
			console.log(response);
			if (response) {
				$scope.customerComplaint = response;
				$scope.newComplaint=false;
				alert("Your Records Saved Successfully")
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
				$scope.customer.units = response;
				alert("Your Records Saved Successfully")
			} 
		})
	};

} ]);