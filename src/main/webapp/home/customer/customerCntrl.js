var customerModule = angular.module('customerModule', []);

customerModule.controller('customerController', ['$scope','$window','$rootScope','customerService','$state','$filter','contextObject','Operation','masterdataService','complaintService','$modal','$http','NgTableParams',
                                                 function($scope,$window,$rootScope,customerService,$state,filter,contextObject,Operation,masterdataService,complaintService,$modal,$http,NgTableParams) {


	$scope.form={};
	$scope.isCollapsed = true;

	$scope.workItem = {}
//	$scope.editEquipment=false;
	$scope.isNew=true;
	$scope.isEdit=false;
	$scope.startDate=false;
	$scope.expireDate=false;
	$scope.showStatus=false;
	$scope.previewContext = false;
	$scope.customerScreen = true;
	//	$scope.getAllComplaints=false;
	$scope.unitApproval = {};
	$scope.customer={};
	$scope.customer.edited = false;
	$scope.customers=[];
	$scope.searchCriteria = {};
	$scope.dummyEquipmentDetails ={};
	$scope.unit = {
			"equipmentDetails" : []
	};

	$scope.dummyUnit ={
			"equipmentDetails" : []
	};

	$scope.customer.address = {};
	$scope.customer.address = angular.copy($scope.dummyAddress);
	$scope.dummyAddress= {};

	$scope.serviceRenewalBean = {};

	$scope.customer.units=[];

//	$scope.isPrivileged = function(role){
//
//		var userPrivilege = $rootScope.user.privileges;
//		var result=false;
//		angular.forEach(userPrivilege, function(privilege) {
//			if (privilege.privilege.privilege===role) result= true;
//		});
//		return result;		
//	}

	$scope.toggleReadOnly = function(form,isEdit) {
		if(!isEdit){
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
		}
	};

	
	if(Operation && Operation == 'viewCustomer'){
		$scope.customer = contextObject;
		$scope.toggleReadOnly('CUSTOMER',contextObject.edited);
	}


	if(Operation && Operation == 'unitApproval'){
		$scope.unitApproval = contextObject;
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
				});

		$scope.toggleReadOnly('CUSTOMER');
		$scope.toggleReadOnly('UNIT');
	};

	$scope.getServiceAgreementHistoryForUnit = function(unitId){

		if(!unitId){
			return;
		}

		customerService.getServiceAgreementHistoryForUnit(unitId)
		.then(function(response) {
			console.log('getting Service Agreement History for single unit in controller : ');
			console.log(response);
			if (response) {
				$scope.serviceHistories = response;
			} 
		})
	}



	$scope.getAllComplaintsForUnit = function(unitId){

		if(!unitId){
			return;
		}

		complaintService.getAllComplaintsForUnit(unitId)
		.then(function(response) {
			console.log('getting all Complaint for single unit in controller : ');
			console.log(response);
			if (response) {
				$scope.customerComplaints = response;
			} 
		})
	}

//	$scope.addMachine = function(object) {

//	var equipmentDetail = angular
//	.copy($scope.dummyEquipmentDetails);
//	equipmentDetail.unitId = object.unitId;
//	object.equipmentDetails
//	.push(equipmentDetail);

//	};

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
		$state.go('viewCustomer',{entityId:currentCustomerId});
	}

	$scope.redirectfromWorkItemtoEntity=function(url,entityId){
		$state.go(url,{entityId:entityId});
	}

	
		$scope.redirectToComplaintScreen=function(currentComplaintId ){
			if($rootScope.isPrivileged("VIEW_COMPLAINT") || $rootScope.isPrivileged("CREATE_COMPLAINT")){
			$state.go('viewComplaint',{entityId:currentComplaintId});
		}
	}

	$scope.redirectToComplaintScreenByUnitId=function(unit, currentUnitId ){

		if(unit.unitCode==null){
			$scope.alerts=[];
			$scope.alerts.push({msg: 'Non Of Details Are Saved For This Unit'})
			return;
		}
		$state.go('newComplaintFromUnit',{entityId:currentUnitId});
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

	$scope.saveCustomer = function(context) {


		$scope.addcurrentUnittoCustomer();

		console.log('save customer called');

		$scope.comment = "";

		var genericRequest={"bussinessObject":$scope.customer,"contextInfo":{"comment":$scope.comment}};

		customerService.saveCustomer(genericRequest, context)
		.then(function(response) {
			console.log('customer Data received from service : ');
			console.log(response);
			if (response) {
				$scope.customer = response;
				$scope.alerts=[];
				alert("Customer Saved Successfully");
				$scope.redirectToCustomerDtlScreen($scope.customer.customerId);
			}
		})
	};

	$scope.saveAndUpdateCustomer = function(context){

		if(!$scope.customer.customerId){
			$scope.saveCustomer(context);			
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
				$scope.customer = response;
				$scope.alerts=[];
				alert("Customer Updated Successfully");
				$scope.redirectToCustomerDtlScreen($scope.customer.customerId);
			}
		})
	};


	$scope.approveUnit = function(unit) {

		customerService.approveUnit(unit)
		.then(function(response) {
			console.log('update unit called in controller: ');
			console.log(response);
			if (response) {
				unit= response;
				$scope.redirectToCustomerDtlScreen(unit.customerId);
			} 
		})
	};

//	$scope.rejectUnitApproval= function(unit){
//
//		customerService.rejectUnitApproval(unit)
//		.then(function(response) {
//			console.log('reject unit called in controller ');
//			console.log(response);
//			if (response) {
//				unit= response;
//				$scope.redirectToCustomerDtlScreen(unit.customerId);
//			} 
//		})
//	}

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

	$scope.saveUnit = function(object, context,formId) {
		console.log('save unit called');

		if(!$scope.UNIT_+formId.$valid){

			$scope.alerts=[];
			$scope.alerts.push({msg: 'Some of the fields are invalid! please verify again'})
			return;
		}

		$scope.resetAleart = function(){
			$scope.alerts=[];
		}
		$scope.comment = "";
		var genericRequest={"bussinessObject":object,"contextInfo":{"comment":$scope.comment}};
		customerService.saveUnit(genericRequest, $scope.customer.customerId, context)
		.then(function(response) {
			console.log('unit Data received from service : ');
			console.log(response);
			if (response) {
				$scope.customer.units = response;
				$scope.alerts=[];
				alert("Unit Saved Successfully")
			} 
		})
	};

//	if($scope.getAllComplaints){
//	$scope.getAllComplaints = function(){
//	customerService.getAllComplaints(customer.customerId)
//	.then(function(response) {
//	console.log('getting all complaints in controller : ');
//	console.log(response);
//	if (response) {
//	$scope.customerComplaints = response;
//	} 
//	})
//	}
//	}

	$scope.getWorkItemByUserIdAndWorkType = function(){
		console.log('getting work Item');
		if(angular.isUndefined($scope.workItem.workType)){
			$scope.workItem.workType = "";
		}
		if(angular.isUndefined($scope.workItem.status)){
			$scope.workItem.status = "";
		}

		customerService.getWorkItemByUserIdAndWorkType($rootScope.user.userId, $scope.workItem.workType, $scope.workItem.status)
		.then(function(response) {
			console.log(response);
			if (response) {
				$scope.workItem = response;
			} 
		})	
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

	$scope.isCreateOrUpdatePrivileged=function(){
		return !($rootScope.isPrivileged('CREATE_CUSTOMER'));
	}

	$scope.isViewPrivileged=function(){
		return !($rootScope.isPrivileged('CREATE_CUSTOMER')) && !($rootScope.isPrivileged('VIEW_CUSTOMER'));
	}

	$scope.navigationContextNext = {'customer':'unitDtl', 
			'unitDtl': 'equipmntDtl'};

	$scope.navigationContextPrevious = {'equipmntDtl':'unitDtl', 
			'unitDtl':'customer'};

	$scope.selection="customer";
	$scope.switchToNextPage = function(){

		if($scope.selection="customer"){


			if(!$scope.form.CUSTOMER.$valid || $scope.contactNoError || $scope.emailError){
				$scope.alerts=[];
				$scope.alerts.push({msg: 'Some of the fields are invalid! please verify again'})
				return;
			}
		}

		if($scope.selection="unitDtl"){
			if(!$scope.form.UNIT.$valid){
				$scope.alerts=[];
				$scope.alerts.push({msg: 'Some of the fields are invalid! please verify again'})
				return;
			}
		}
		$scope.selection = $scope.navigationContextNext[$scope.selection];
	};

	$scope.switchToPreviousPage = function(){

		$scope.selection = $scope.navigationContextPrevious[$scope.selection];
	};

	$scope.getEmailId = function(){
		customerService.getEmailId($scope.customer.emailId)
		.then(function(response) {
			console.log('getting customer by emailId in controller : ');
			console.log(response);
			if (response) {
				$scope.customerForEmail = response;

				if(angular.isUndefined($scope.customer.customerId) || $scope.customerForEmail.customerId != $scope.customer.customerId){
					$scope.emailError=true;
					$scope.alerts=[];
					$scope.alerts.push({msg: 'EMAIL ID ALREADY EXIST, TRY ANOTHER ONE!!'})
				}
			} 

			else{
				$scope.emailError=false;
				$scope.alerts=[];
			}
		})
	}

	$scope.getContactNo = function(){
		customerService.getContactNo($scope.customer.contactNo)
		.then(function(response) {
			console.log('all customers Data received in controller : ');
			console.log(response);
			if (response) {
				$scope.customerForContactNo = response;

				if(angular.isUndefined($scope.customer.customerId) || $scope.customerForContactNo.customerId != $scope.customer.customerId){
					$scope.contactNoError=true;
					$scope.alerts=[];
					$scope.alerts.push({msg: 'CONTACT NUMBER EXIST, TRY ANOTHER ONE!!'})
				}
			} 
			else{
				$scope.contactNoError=false;
				$scope.alerts=[];
			}

		})
	}


	$scope.showRejectionCommentBox = function(unit) {

		$rootScope.curModal = $modal.open({
			templateUrl: 'customer/Comment_Box_Reject_Unit.html',
			scope:$scope,
			controller: function (customerService,$scope) {

				$scope.comment = "";

				$scope.rejectUnitApproval = function() {
					var genericRequest={"bussinessObject":unit,"contextInfo":{"comment":$scope.comment}};
					customerService.rejectUnitApproval(genericRequest)
					.then(function(response) {
						console.log('reject approval called from service : ');
						console.log(response);
						if (response) {
							unit= response;
							$rootScope.curModal.close();
						} 
					})
				};
				
				$scope.closeModal=function(){
					$rootScope.curModal.close();
				}
			},
			backdrop:'static',
			keyboard: false
		});
	};


	$scope.showCommentBoxUnitPublish = function(unit) {

		$rootScope.curModal = $modal.open({
			templateUrl: 'customer/Comment_Box_Unit_Publish.html',
			scope:$scope,
			controller: function (customerService,$scope) {

				$scope.comment = "";

				$scope.saveUnit = function(context) {
					var genericRequest={"bussinessObject":unit,"contextInfo":{"comment":$scope.comment}};
					console.log('save unit called');
					customerService.saveUnit(genericRequest, $scope.customer.customerId, context)
					.then(function(response) {
						console.log('unit Data received from service : ');
						console.log(response);
						if (response) {
							$scope.customer.units = response;
							$rootScope.curModal.close();
							alert("Unit Saved Successfully")
						} 
					})
				};
				$scope.closeModal=function(){
					$rootScope.curModal.close();
				}
			},
			backdrop:'static',
			keyboard: false
		});
	};


	$scope.showCommentBoxCustomerPublish = function() {

		$rootScope.curModal = $modal.open({
			templateUrl: 'customer/Comment_Box_Customer_Publish.html',
			scope:$scope,
			controller: function (customerService,$scope) {

				$scope.comment = "";

				$scope.saveCustomer = function(context) {
					$scope.addcurrentUnittoCustomer();
					var genericRequest={"bussinessObject":$scope.customer,"contextInfo":{"comment":$scope.comment}};

					customerService.saveCustomer(genericRequest, context)
					.then(function(response) {
						console.log('customer Data received from service : ');
						console.log(response);
						if (response) {
							$scope.customer = response;
							$scope.alerts=[];
							$rootScope.curModal.close();
							alert("Customer Saved Successfully");
							$scope.redirectToCustomerDtlScreen($scope.customer.customerId);
						}
					})
				};
				$scope.closeModal=function(){
					$rootScope.curModal.close();
				}
			},
			backdrop:'static',
			keyboard: false
		});
	};

	$scope.showUnitExpireModal = function(unit) {

		$rootScope.curModal = $modal.open({
			templateUrl: 'customer/Unit_Expiration_Model.html',
			controller: function (customerService, masterdataService) {
				$scope.serviceRenewalBean = unit.serviceAgreement;
			},
			scope:$scope,
			backdrop:'static',
			keyboard: false
		});

		$scope.updateServiceAgreement = function(){
			$rootScope.curModal.close();
		};

	};


	$scope.showAddEquipmentModel = function(object, editEquipment) {
		$scope.dummyEquipmentDetails={};
		$rootScope.curModal = $modal.open({
			templateUrl: 'customer/Equipment_Model.html',
			controller: function (customerService, masterdataService) {

				if(editEquipment==='true'){
					$scope.dummyEquipmentDetails = object;
				}
			},
			scope:$scope,
			backdrop:'static',
			keyboard: false
		});

		$scope.closeModal=function(){
			$rootScope.curModal.close();
		}

		$scope.addMachine = function() {

			if(editEquipment==='true'){
				$rootScope.curModal.close();
			}

			else{
				var equipmentDetail = angular
				.copy($scope.dummyEquipmentDetails);
				equipmentDetail.unitId = object.unitId;
				object.equipmentDetails
				.push(equipmentDetail);

				$scope.dummyEquipmentDetails={};
				$rootScope.curModal.close();
			};
		}

	};

	$scope.addcurrentUnittoCustomer = function(){

		$scope.previewContext =true;
		var units = $scope.customer.units;
		var currentUnit=$scope.unit;
		for (var i = 0; i < units.length; i++)
		{
			if (units[i].machineSerialNo === currentUnit.machineSerialNo){
				units.splice(i, 1);
			}
		}
		$scope.customer.units.push(angular.copy(currentUnit));
	};

	$scope.addMoreUnit = function(){

		$scope.addcurrentUnittoCustomer();

		$scope.unit = {
				"equipmentDetails" : [ {} ]
		};
	};

	$scope.closePreview = function(){

		$scope.previewContext=false;
	};


	$scope.showAndHidePreview = function(){

		var result=false;
		if($scope.previewContext){
			result = true;
			return result;
		}
		return result;
	};


	$scope.resetAleart = function(){
		$scope.alerts=[];
	}

	$scope.filterCustomer = function() {

		$rootScope.curModal = $modal.open({
			templateUrl: 'customer/Customer_Search_Filter_Model.html',
			scope:$scope,
			controller: function ($scope) {

				$scope.getCustomerByCriteria = function(){
					$scope.tableParams.reload();
					$rootScope.curModal.close();
				}

			},
		});
	};

	$scope.tableParams = new NgTableParams({}, {
		getData: function($defer,params) {
			var sortBy="CUSTOMER_NAME";
			var isAsc=false;
			var pageNo=params.page();
			var pageSize=params.count();
			if(params.sorting()){
				for (var attribute in params.sorting()) {
					if (params.sorting().hasOwnProperty(attribute)) {
						sortBy=attribute;
						var ascDsc=params.sorting()[attribute];
						if(ascDsc==='asc'){
							isAsc=true;  
						}
					}
				}
			}
			$scope.searchCriteria.pageSize=pageSize;
			$scope.searchCriteria.pageNo=pageNo;
			$scope.searchCriteria.sortBy=sortBy;
			$scope.searchCriteria.isAscending=isAsc;

			customerService.getCustomerByCriteria($scope.searchCriteria)
			.then(
					function(customers) {
						if(customers){
							params.total(customers.totalCount);
							$defer.resolve(customers.objectData);
						}
					})

		}
	});

	$scope.lockCustomerEntity = function()
	{
		$scope.entityLock = {};
		$scope.entityLock.entityId = $scope.customer.customerId;
		$scope.entityLock.entityType = 'CUSTOMER';
		customerService.lockEntity($scope.entityLock)
		.then(
				function(customer) {
					console
					.log('Locking customer entity in controller');
					console.log(customer);
					if (customer) {
						$scope.customer=customer;
						$scope.toggleReadOnly('CUSTOMER',customer.edited);
					}
				})

	}

	$scope.lockUnitEntity = function(unitIndex,formId)
	{
		$scope.entityLock = {};
		$scope.entityLock.entityId = $scope.customer.units[unitIndex].unitId;
		$scope.entityLock.entityType = 'UNIT';
		customerService.lockEntity($scope.entityLock)
		.then(
				function(unitFromDB) {
					console
					.log('Locking unit entity in controller');
					console.log(unitFromDB);
					if (unitFromDB) {
						$scope.customer.units[unitIndex] = unitFromDB;	
						$scope.toggleReadOnly('UNIT_'+formId,unitFromDB.edited);
					}
				})

	}

	$scope.unlockCustomerEntity = function()
	{
		$scope.entityLock = {};
		$scope.entityLock.entityId = $scope.customer.customerId;
		$scope.entityLock.entityType = 'CUSTOMER';

		customerService.unlockEntity($scope.entityLock)
		.then(
				function(customer) {
					console
					.log('Unlocking customer entity in controller');
					console.log(customer);
					if (customer) {
						$scope.customer=customer;
						$scope.toggleReadOnly('CUSTOMER',customer.edited);
					}
				})
	}

	$scope.unlockUnitEntity = function(unitIndex,formId)
	{
		$scope.entityLock = {};
		$scope.entityLock.entityId =  $scope.customer.units[unitIndex].unitId;
		$scope.entityLock.entityType = 'UNIT';

		customerService.unlockEntity($scope.entityLock)
		.then(
				function(unitFromDB) {
					console
					.log('Unlocking unit entity in controller');
					console.log(unitFromDB);
					if (unitFromDB) {
						$scope.customer.units[unitIndex] = unitFromDB;
						$scope.toggleReadOnly('UNIT_'+formId,unitFromDB.edited);
					}
				})
	}

	$scope.makeAllReadOnly=function(formId){
		$scope.toggleReadOnly('UNIT_'+formId,false);
	}
	
	$scope.getClassForUnitStatus=function(status){
		if(status==="R")
		{
			return "btn btn-danger btn-xs"
		}
		if(status==="A")
		{
			return "btn btn-success btn-xs"
		}
		if(status==="P")
		{
			return "btn btn-warning btn-xs"
		}
		
		return "btn btn-info btn-xs"
	}
	
	$scope.getUnitStatusText=function(status){
		if(status==="R")
		{
			return "Rejected"
		}
		if(status==="A")
		{
			return "Approved"
		}
		if(status==="P")
		{
			return "Published"
		}
			return "Draft"
	}
	
	$scope.addUnitPopup = function(){
		$rootScope.curModal = $modal.open({
			templateUrl: 'customer/unit.html',
			controller: function (customerService, masterdataService) {

				
				
			},
			scope:$scope,
			backdrop:'static',
			keyboard: false,
			size:'lg'
		});
		
		
	};
	
	
} ]);
