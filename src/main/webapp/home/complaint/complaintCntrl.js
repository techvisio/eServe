var complaintModule = angular.module('complaintModule', []);

complaintModule.controller('complaintController', ['$scope','$window','$rootScope','complaintService','$state','$filter','unitComplaint','complaint','masterdataService','userService',
                                                   function($scope,$window,$rootScope,complaintService,$state,filter,unitComplaint,complaint,masterdataService,userService) {


	$scope.form={};
	$scope.isNew=true;
	$scope.isEdit=false;
	$scope.isUnitCollapsed = true;
	$scope.isComplaintCollapsed = true;
	$scope.startDate=false;
	$scope.expireDate=false;
	$scope.newComplaint=true;
	$scope.showStatus=false;
	$scope.complaintCustomers = [];
	$scope.customers=[];
	$scope.searchCriteria = {};
	$scope.customerComplaint = {};
	$scope.customerComplaints = [];
	$scope.complaintResolution={};
	$scope.complaintAssignment={};

	
	$scope.isPrivileged = function(role){

		var userPrivilege = $rootScope.user.privileges;
		var result=false;
		angular.forEach(userPrivilege, function(privilege) {
			if (privilege.privilege.privilege===role) result= true;
		});
		return result;		
	}

	
	$scope.getCustomerForComplaint = function(){
		complaintService.getCustomerForComplaint(unitComplaint.customerId)
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

	if(unitComplaint){

		$scope.getCustomerForComplaint();
		$scope.newComplaint = true;
		$scope.isEdit=true;
		$scope.isNew = false;
		$scope.customerComplaint.unit = unitComplaint;
	}


	if(complaint){
		$scope.newComplaint=false;
		$scope.showStatus = true;
		$scope.isEdit = true;
		$scope.isNew = false;
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

	$scope.resetAlert = function(){
		 $scope.alerts=[];	 
	 }
	
	if($scope.isPrivileged("VIEW_CUSTOMER") || $scope.isPrivileged("CREATE_CUSTOMER")){
	$scope.redirectToCustomerDtlScreen=function(currentCustomerId){
		$scope.alerts=[];
		$state.go('customer',{customerId:currentCustomerId});
	}
	}
	
	if($scope.isPrivileged("VIEW_COMPLAINT") || $scope.isPrivileged("CREATE_COMPLAINT")){
	$scope.redirectToComplaintScreen=function(currentComplaintId ){

		$state.go('complaintScreen',{complaintId:currentComplaintId});
	}
	}

	$scope.redirectToComplaint=function(){
		$state.go('complaint');
	}

	$scope.redirectToComplaintScreenByUnitId=function(unit, currentUnitId ){

		if(unit.unitCode==null){
			$scope.alerts=[];
			$scope.alerts.push({msg: 'Non Of Details Are Saved For This Unit'})
			return;
		}
		$state.go('customerToComplaint',{unitId:currentUnitId});
	}

	$scope.showconfirmboxComplaint = function () {
		if ($window.confirm("No Record Found ! Want To Create New Complaint?")){
			$scope.redirectToComplaint();
		}
	}


	$scope.getComplaintByCriteria=function(){
		complaintService.getComplaintByCriteria($scope.searchCriteria)
		.then(
				function(customers) {
					console
					.log('getting complaint by criteria in controller : ');
					console.log(customers);
					if (customers) {
						$scope.complaintCustomers=customers;
						if($scope.complaintCustomers.length==0){
							$scope.showconfirmboxComplaint();
						}
					}
				})
	}


	$scope.getSearchUnitByCustomerId = function(customer,customerId){
		complaintService.getSearchUnitByCustomerId(customerId)
		.then(function(response) {
			console.log('getting units by customerId in controller : ');
			console.log(response);
			if (response) {
				$scope.units = response;
				customer.units = $scope.units;
			} 
		})
	}

	$scope.getComplaintByUnitId = function(unit,unitId){
		complaintService.getComplaintByUnitId(unitId)
		.then(function(response) {
			console.log('get SearchComplaint by unitId in controller : ');
			console.log(response);
			if (response) {
				$scope.customerComplaints = response;
				unit.complaints = $scope.customerComplaints;
			} 
		})
	}

	$scope.getAllComplaintsForUnit = function(unitId){
		complaintService.getAllComplaintsForUnit(unitId)
		.then(function(response) {
			console.log('getting all Complaint for single unit in controller : ');
			console.log(response);
			if (response) {
				$scope.customerComplaints = response;
			} 
		})
	}

	$scope.getComplaint = function(){
		customerService.getCustomer($scope.Complaint.customerId)
		.then(function(response) {
			console.log('customer Data received in controller : ');
			console.log(response);
			if (response) {
				$scope.customer = response;
			} 
		})
	}

	$scope.saveComplaint = function() {

		if(!$scope.COMPLAINT.$valid){
			$scope.alerts=[];
			$scope.alerts.push({msg: 'Some of the fields are invalid! please verify again'})
			return;
		}

		$scope.resetAleart = function(){
			$scope.alerts=[];
		}

		console.log('save complaint called in controller');
		complaintService.saveComplaint($scope.customerComplaint)
		.then(function(response) {
			console.log('complaint Data received from service : ');
			console.log(response);
			if (response) {
					$scope.customerComplaint = response;
					alert("Complaint Saved Successfully");
					$state.go('complaintScreen',{complaintId:$scope.customerComplaint.complaintId});
			} 
		})
	};

	$scope.updateComplaint = function() {

		console.log('update complaint called in controller');
		complaintService.updateComplaint($scope.customerComplaint)
		.then(function(response) {
			console.log('update Data received from service : ');
			console.log(response);
			if (response) {
				$scope.customerComplaint = response;
				$scope.showStatus = true;
				alert("Complaint Updated Successfully");
				$scope.redirectToComplaintScreen($scope.customerComplaint.complaintId);
			} 
		})
	};

	$scope.saveAndUpdateComplaint = function(){

		if(!$scope.customerComplaint.complaintId){
			$scope.saveComplaint();			
		}
		else{
			$scope.updateComplaint();
		}
	}

	$scope.saveComplaintResolution = function() {
		console.log('save complaintResolution called in controller');
		complaintService.saveComplaintResolution($scope.customerComplaint.complaintId,$scope.customerComplaint.complaintResolution)
		.then(function(response) {
			console.log('complaintResolution Data received from service : ');
			console.log(response);
			if (response) {
				$scope.customerComplaint = response;
				alert("Complaint Resolution Saved Successfully")
			} 
		})
	};

	$scope.saveComplaintAssignment = function() {
		console.log('save complaintAssignment called in controller');
		complaintService.saveComplaintAssignment($scope.customerComplaint.complaintId,$scope.customerComplaint.complaintAssignment)
		.then(function(response) {
			console.log('complaintAssignment Data received from service : ');
			console.log(response);
			if (response) {
				$scope.customerComplaint = response;
				$scope.newComplaint=false;
				var name = $scope.customerComplaint.complaintAssignment.user.firstName +" "+ $scope.customerComplaint.complaintAssignment.user.lastName;
				alert('Complaint Assign To : ' + name);
			} 
		})
	};

	$scope.toggleReadOnly = function(form) {

		if($scope.isEdit){
			$('#' + form + ' *').attr('readonly',
					true);
			$('#' + form + ' select')
			 .attr('disabled', true);
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
			$('#' + form + ' select')
			 .attr('disabled', false);
			$('#' + form + ' input[type="radio"]')
			.attr('disabled', false);
			$('#' + form + ' input[type="checkbox"]')
			.attr('disabled', false);
			$('#' + form + ' input[type="button"]')
			.attr('disabled', false);
			$scope.isEdit = !$scope.isEdit;
		}
	};

	$scope.isCreateOrUpdatePrivileged=function(){
		return !($scope.isPrivileged('CREATE_COMPLAINT(PAID ONLY)')) && !(!$scope.isNew && $scope.isPrivileged('CREATE_COMPLAINT'));
	}
	
	$scope.isViewPrivileged=function(){
		return !($scope.isPrivileged('CREATE_COMPLAINT(PAID ONLY)')) && !($scope.isPrivileged('CREATE_COMPLAINT')) && !($scope.isPrivileged('VIEW_COMPLAINT'));
	}

} ]);