var complaintModule = angular.module('complaintModule', []);

complaintModule.controller('complaintController', ['$scope','$window','$rootScope','complaintService','$state','$filter','contextObject','Operation','masterdataService','$modal',
                                                   function($scope,$window,$rootScope,complaintService,$state,filter,contextObject,Operation,masterdataService,$modal) {


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
	$scope.workOrder = {};
	$scope.workOrders = [];
	$scope.workOrderResolution={};
	$scope.workOrderAssignment={};
	$scope.dummyEquipmentDetails ={};
	$scope.equipment={};
	$scope.replaceEquip = false;
	$scope.equipments=[];	


	$scope.toggleReadOnly = function(form) {

		if($scope.workOrder.edited){
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
			$scope.workOrder.edited = !$scope.workOrder.edited;
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
			$scope.workOrder.edited = !$scope.workOrder.edited;
		}
	};

	$scope.isCreateOrUpdatePrivileged=function(){
		return ($rootScope.isPrivileged('CREATE_COMPLAINT(PAID ONLY)')) || (!$scope.isNew && $rootScope.isPrivileged('CREATE_COMPLAINT'));
	}

	$scope.isViewPrivileged=function(){
		return ($rootScope.isPrivileged('CREATE_COMPLAINT(PAID ONLY)')) || ($rootScope.isPrivileged('CREATE_COMPLAINT')) || ($rootScope.isPrivileged('VIEW_COMPLAINT'));
	}

	$scope.getCustomerForComplaint = function(){
		complaintService.getCustomerForComplaint(contextObject.customerId)
		.then(function(response) {
			console.log('customer Data received in controller : ');
			console.log(response);
			if (response) {
				$scope.customer = response;
				$scope.workOrder.customerId = $scope.customer.customerId;
				$scope.workOrder.customerCode = $scope.customer.customerCode;
				$scope.workOrder.contactNo = $scope.customer.contactNo;
				$scope.workOrder.emailId = $scope.customer.emailId;
				$scope.workOrder.customerName = $scope.customer.customerName;
			} 
		})
	}

	if(Operation && Operation == 'newComplaintFromUnit'){

		$scope.getCustomerForComplaint();
		$scope.newComplaint = true;
		$scope.isEdit=true;
		$scope.isNew = false;
		$scope.workOrder.unit = contextObject;
	}

	if(Operation && Operation =='viewComplaint'){
		$scope.newComplaint=false;
		$scope.showStatus = true;
		$scope.isEdit = true;
		$scope.isNew = false;
		$scope.workOrder = contextObject;
		$scope.toggleReadOnly('COMPLAINT');
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

	$scope.resetAlert = function(){
		$scope.alerts=[];	 
	}


	$scope.redirectToCustomerDtlScreen=function(currentCustomerId){
		if($rootScope.isPrivileged("VIEW_CUSTOMER") || $rootScope.isPrivileged("CREATE_CUSTOMER")){
			$scope.alerts=[];
			$state.go('viewCustomer',{entityId:currentCustomerId});
		}

		else{
			$rootScope.showNotHavePrivilegeModel();
		}
	}


	$scope.redirectToComplaintScreen=function(currentComplaintId ){
		if($rootScope.isPrivileged("VIEW_COMPLAINT") || $rootScope.isPrivileged("CREATE_COMPLAINT")){
			$state.go('viewComplaint',{entityId:currentComplaintId});
		}
		else{
			$rootScope.showNotHavePrivilegeModel();
		}
	}

	$scope.redirectToComplaint=function(){
		if($rootScope.isPrivileged("CREATE_COMPLAINT(PAID ONLY)")){
			$state.go('createNewComplaint');
		}
		else{
			$rootScope.showNotHavePrivilegeModel();
		}		

	}



	$scope.redirectToComplaintScreenByUnitId=function(unit, currentUnitId ){
		if($rootScope.isPrivileged("CREATE_COMPLAINT")){
			if(unit.unitCode==null){
				$scope.alerts=[];
				$scope.alerts.push({msg: 'Non Of Details Are Saved For This Unit'})
				return;
			}
			$state.go('newComplaintFromUnit',{entityId:currentUnitId});
		}
		else{
			$rootScope.showNotHavePrivilegeModel();
		}
	}

	$scope.showconfirmboxComplaint = function () {
		if ($rootScope.showConfirmModal('No Record Found ! Want To Create New Complaint?', 'No Complaint Found')){
			
			$scope.redirectToComplaint();
		}
	}


	$scope.getComplaintByCriteria=function(){
		
		if($scope.isViewPrivileged){
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
		else{
			$rootScope.showNotHavePrivilegeModel();
		}
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
				$scope.workOrders = response;
				unit.complaints = $scope.workOrders;
			} 
		})
	}

	$scope.getAllComplaintsForUnit = function(unitId){
		complaintService.getAllComplaintsForUnit(unitId)
		.then(function(response) {
			console.log('getting all Complaint for single unit in controller : ');
			console.log(response);
			if (response) {
				$scope.workOrders = response;
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

	$scope.saveWorkOrder = function() {

		if(!$scope.form.COMPLAINT.$valid){
			$scope.alerts=[];
			$scope.alerts.push({msg: 'Some of the fields are invalid! please verify again'})
			return;
		}

		$scope.resetAleart = function(){
			$scope.alerts=[];
		}

		console.log('save complaint called in controller');
		complaintService.saveWorkOrder($scope.workOrder)
		.then(function(response) {
			console.log('complaint Data received from service : ');
			console.log(response);
			if (response) {
				$scope.workOrder = response;
				$rootScope.showAlertModel('Complaint Saved Successfully With Complaint Code '+ $scope.workOrder.complaintCode, 'Operation Successful')
				$state.go('complaintScreen',{entityId:$scope.workOrder.workOrderId});
			} 
		})
	};

	$scope.updateWorkOrder = function() {

		console.log('update complaint called in controller');
		complaintService.updateWorkOrder($scope.workOrder)
		.then(function(response) {
			console.log('update Data received from service : ');
			console.log(response);
			if (response) {
				$scope.workOrder = response;
				$scope.showStatus = true;
				$rootScope.showAlertModel('Complaint Updated Successfully With Complaint Code '+ $scope.workOrder.complaintCode, 'Operation Successful')
				$scope.redirectToComplaintScreen($scope.workOrder.workOrderId);
			} 
		})
	};

	$scope.saveAndUpdateComplaint = function(){

		if($scope.isCreateOrUpdatePrivileged){

			if(!$scope.workOrder.workOrderId){
				$scope.saveWorkOrder();			
			}
			else{
				$scope.updateWorkOrder();
			}
		}

		else{
			$rootScope.showNotHavePrivilegeModel();
		}
	}

	$scope.saveWorkOrderResolution = function() {
		console.log('save workOrderResolution called in controller');
		complaintService.saveworkOrderResolution($scope.workOrder.workOrderId,$scope.workOrder.workOrderResolution)
		.then(function(response) {
			console.log('workOrderResolution Data received from service : ');
			console.log(response);
			if (response) {
				$scope.workOrder = response;
				$rootScope.showAlertModel('Complaint Resolution Saved Successfully', 'Operation Successful')
			} 
		})
	};

	$scope.saveWorkOrderAssignment = function() {
		console.log('save workOrderAssignment called in controller');
		complaintService.saveWorkOrderAssignment($scope.workOrder.workOrderId,$scope.workOrder.workOrderAssignment)
		.then(function(response) {
			console.log('workOrderAssignment Data received from service : ');
			console.log(response);
			if (response) {
				$scope.workOrder = response;
				$scope.newComplaint=false;
				var name = $scope.workOrder.workOrderAssignment.user.firstName +" "+ $scope.workOrder.workOrderAssignment.user.lastName;
				$rootScope.showAlertModel('Complaint Assign To : ' + name, 'Operation Successful')
			} 
		})
	};

	$scope.lockComplaintEntity = function()
	{
		$scope.entityLock = {};
		$scope.entityLock.entityId = $scope.workOrder.workOrderId;
		$scope.entityLock.entityType = 'COMPLAINT';
		complaintService.lockEntity($scope.entityLock)
		.then(
				function(complaintFromDB) {
					console
					.log('Locking complaint entity in controller');
					console.log(complaintFromDB);
					if (complaintFromDB) {
						$scope.workOrder = complaintFromDB;
					}
				})

	}

	$scope.unlockComplaintEntity = function()
	{
		$scope.entityLock = {};
		$scope.entityLock.entityId = $scope.workOrder.workOrderId;
		$scope.entityLock.entityType = 'COMPLAINT';

		complaintService.unlockEntity($scope.entityLock)
		.then(
				function(complaint) {
					console
					.log('Unlocking complaint entity in controller');
					console.log(complaint);
					if (complaint) {
						$scope.workOrder=complaint;
					}
				})
	}


	$scope.getEquipments = function(unit, equipType){

		complaintService.getEquipments(equipType, unit.unitId)
		.then(
				function(equipments) {
					console
					.log('getting equipments in controller : ');
					console.log(equipments);
					if (equipments) {
						$scope.workOrder.unit.equipmentDetails = equipments;

						$scope.showEquipmentModel('lg');
					}
				})

	}

	$scope.showAddEquipmentModel = function(object, editEquipment) {
		$scope.workOrder.unit.equipmentDetails=[];
		$rootScope.curModal = $modal.open({

			templateUrl: 'complaint/Equipment_Complaint_Model.html',
			controller: function (complaintService, masterdataService) {

				$scope.getEquipments = function(equipType){

					complaintService.getEquipments(equipType, object.unitId)
					.then(
							function(equipments) {
								console
								.log('getting equipments in controller : ');
								console.log(equipments);
								if (equipments) {
									$scope.workOrder.unit.equipmentDetails = equipments;
								}
							})
				}

				$scope.addMachine = function() {

					var equipmentDetail = angular
					.copy($scope.dummyEquipmentDetails);
					equipmentDetail.unitId = object.unitId;

					if(equipmentDetail.serialNo !=null && !angular.isUndefined(equipmentDetail.serialNo)){
						$scope.equipments
						.push(equipmentDetail);
					}

					$scope.dummyEquipmentDetails={};
					$rootScope.curModal.close();

				};

				$scope.saveEquipment = function() {

					complaintService.saveEquipment($scope.equipments, $scope.workOrder.workOrderId)
					.then(function(equipment) {
						console.log('equipment Data received from service : ');
						console.log(equipment);
						if (equipment) {
							$scope.ComplaintEquipments = equipment;
							$scope.alerts=[];
							$rootScope.showAlertModel('equipment Saved Successfully', 'Operation Successful')
						} 
					})
				};



				$scope.deleteEquipments = function() {
					complaintService.deleteEquipments($scope.workOrder.unit.equipmentDetails, object.unitId, $scope.workOrder.workOrderId)
					.then(function(response) {
						console.log('delete Equipments Data received from service : ');
						console.log(equipment);
						if (equipment) {
							$scope.ComplaintEquipments = equipment;
						} 
					})
				};

				$scope.closeModal = function(){
					$rootScope.curModal.close();
				}

			},

			size:'lg',
			scope:$scope,
			backdrop:'static',
			keyboard: false
		});

	};

	$scope.checkComplaintEquipments = function(){
		if(ComplaintEquipments.length<=0 || angular.isUndefined($scope.ComplaintEquipments)){
			return true;
		} 

		else{
			return false;
		}
	};

	$scope.getWorkOrderEquipment =  function(){

		if($scope.workOrder.workOrderId){
			complaintService.getWorkOrderEquipment($scope.workOrder.workOrderId)
			.then(function(response) {
				console.log('getWorkOrderEquipment Data received from service : ');
				console.log(response);
				if (response) {
					$scope.ComplaintEquipments = response;
				} 
			})
		}
	}
	
} ]);