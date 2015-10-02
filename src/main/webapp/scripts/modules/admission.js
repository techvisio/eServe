var admissionModule = angular.module('admissionModule', ['ui.bootstrap']);

admissionModule
.controller(
		'admissionController',
		[
		 '$scope',
		 'admissionService',
		 'masterdataService',
		 '$modal',
		 function($scope, admissionService,masterdataService,$modal) {

			 $scope.form = {};
			 $scope.form.sameAsAbove=false;
			 $scope.form.processing=false;
			 $scope.form.isEdit=true;
			 $scope.form.isNew=true;
			 $scope.dashboard=true;
			 $scope.showCriteria=false;
			 $scope.tab = 1;
			 $scope.serverModelData = {};

			 $scope.student = {};
			 $scope.latestAdmission=[];
			 $scope.searchCriteria={};
			 $scope.currentFetchLimit=2;

			 $scope.student.addressDtl = [];

			 $scope.student.consultantDetail=[];
			 $scope.student.consultantDetail.push(angular.copy($scope.dummyConsultantDetail));

			 $scope.student.academicDtl = [];
			 $scope.student.academicDtl.push(angular.copy($scope.dummyQualification));

			 $scope.student.discountDtl = [];
			 $scope.student.discountDtl.push(angular.copy($scope.dummyDiscountDtl));

			 $scope.student.counsellingDtl = [];
			 $scope.student.counsellingDtl.push(angular.copy($scope.dummyCounsellingDtl));

			 $scope.form.showSub = false;
			 $scope.searchResultList=[];
			 $scope.filteredSearch=[];

			 $scope.dummyAddress = {};
			 $scope.dummyQualification = {"qualificationSubDtl" : [ {} ] };
			 $scope.dummyQualificationSubDtl = {};

			 $scope.dummyDiscountDtl = {};

			 $scope.dummyCounsellingDtl = {};


			 $scope.dummyConsultantDetail={};

			 $scope.admissionMode={"C":"Counselling",
					 "W":"Walk-In",
					 "R":"Referral",
					 "A":"Consultant"	 
					};

			 $scope.itemsPerPage = 3;
			 $scope.currentPage = 0;
			 $scope.totalItems = 0;

			 $scope.pageCount = function () {
				 return Math.ceil($scope.searchResultList.length / $scope.itemsPerPage);
			 };

			 $scope.numPages = function () {
				 return Math.ceil($scope.searchResultList.length / $scope.numPerPage);
			 };

			 $scope.$watch('currentPage + numPerPage', function() {
				 var begin = (($scope.currentPage - 1) * $scope.itemsPerPage)
				 , end = begin + $scope.itemsPerPage;

				 $scope.filteredSearch = $scope.searchResultList.slice(begin, end);
			 });

			 $scope.gridOptions = {
					 multiSelect:false,
					 data: 'filteredSearch',
					 rowTemplate: '<div ng-dblclick="getStudent(row.config.selectedItems[0].fileNo)" ng-style="{\'cursor\': row.cursor, \'z-index\': col.zIndex() }" ng-repeat="col in renderedColumns" ng-class="col.colIndex()" class="ngCell {{col.cellClass}}" ng-cell></div>',
					 columnDefs: [{ field: "firstName", width: 100,displayName :"FirstName"},
					              { field: "lastName", width: 100,displayName :"LastName"},
					              { field: "fatherName", width: 180,displayName :"Father Name" },
					              { field: "course.course", width: 140,displayName :"Course" },
					              { field: "branch.branchName", width: 180,displayName :"Branch" },
					              {field:"applicationStatus",width:200,displayName :"Status"}]
			 };

			 $scope.LoadMoreData = function() {

				 $scope.currentFetchLimit += 5;

				 $scope.getLatestAdmission();

			 };


			 $scope.lessData = function() {

				 $scope.currentFetchLimit -= 5;

				 $scope.getLatestAdmission();

			 };

			 $scope.selectTab = function (setTab){
				 $scope.tab = setTab;
			 };
			 $scope.isSelected = function(checkTab) {
				 return $scope.tab === checkTab;
			 };

			 $scope.backtoDashboard = function(){
				 if($scope.form.isNew || $scope.form.isEdit ){
					 var response=confirm("Current data will be lost..\nContinue?");
					 if(!response){
						 return;
					 }
				 }

				 $scope.dashboard=true;
				 $scope.resetSearchCriteria();

			 }
			 $scope.checkAmout=function(index,type){
				 if(type=='amount'){
					 if($scope.student.discountDtl[index].amount>0){
						 $scope.student.discountDtl[index].percent=0;
					 }
				 }
				 else
				 {
					 if($scope.student.discountDtl[index].percent>0){
						 $scope.student.discountDtl[index].amount=0;
					 }
				 }

			 }

			 $scope.init=function(){

				 console.log('getting masterdata for admission module in init block');

				 masterdataService.getAdmissionMasterData()
				 .then(function(data) {
					 console.log(data);
					 if (data != null) {
						 $scope.serverModelData = data;
					 } else {
						 console.log('error');
					 }
				 })

			 }


			 $scope.click = function(arg) {

				 console.log("print on submit click");
				 console.log($scope.student);
			 }

			 $scope.addConsultantDetail = function(){

				 var consultantDetail = angular.copy($scope.dummyConsultantDetail);
				 $scope.student.consultantDetail.push(consultantDetail);
			 };       

			 $scope.removeConsultantDetail = function(index){

				 $scope.student.consultantDetail.splice(index, 1);
			 };


			 $scope.addDiscountDtl = function(){

				 var discountDtl = angular.copy($scope.dummyDiscountDtl);
				 $scope.student.discountDtl.push(discountDtl);
			 };       

			 $scope.removeDiscountDtl = function(index){

				 $scope.student.discountDtl.splice(index, 1);
			 };


			 $scope.addCounsellingDtl = function(){

				 var counsellingDtl = angular.copy($scope.dummyCounsellingDtl);
				 $scope.student.counsellingDtl.push(counsellingDtl);
			 };       

			 $scope.removeCounsellingDtl = function(index){

				 $scope.student.counsellingDtl.splice(index, 1);
			 };

			 $scope.addQualification = function(index) {
				 var copedDtl = angular
				 .copy($scope.dummyQualification);
				 $scope.student.academicDtl.push(copedDtl);
			 };

			 $scope.removeQualification = function(index) {
				 $scope.student.academicDtl.splice(index, 1);
			 };


			 $scope.resetSearchCriteria = function(){

				 $scope.searchCriteria={};
			 }

			 $scope.addSubject = function(object) {
				 var qualificationSub = angular
				 .copy($scope.dummyQualificationSubDtl);
				 qualificationSub.qualificationId = object.qualificationId;
				 object.qualificationSubDtl
				 .push(qualificationSub);
			 };

			 $scope.removeSubject = function(object, index) {
				 console.log(index);
				 object.qualificationSubDtl.splice(index, 1);
			 };

			 $scope.showSubject = function() {
				 $scope.showSub = true;
			 };

			 $scope.getStudentAddress = function(addressType) {
				 var address = null;
				 for (var i = 0; i <= $scope.student.addressDtl.length - 1; i++) {
					 var add = $scope.student.addressDtl[i];
					 if (add.addressType == addressType) {
						 address = add;
					 }
				 }
				 ;
				 if (address == null) {
					 address = angular.copy($scope.dummyAddress);
					 address.addressType = addressType;
					 $scope.student.addressDtl.push(address);
				 }

				 return address;
			 }

			 $scope.copyPermanentAddress=function(){
				 console.log('same as above');
				 console.log($scope.form.sameAsAbove);
				 if($scope.form.sameAsAbove == true){
					 console.log('same as above');
					 var cAddress=$scope.getStudentAddress('C');
					 angular.copy($scope.getStudentAddress('P'),cAddress)
					 cAddress.addressType='C';
				 }
			 }

			 $scope.saveStudent = function(){
				 if(!$scope.admissionForm.$valid){
					 alert('Form is invalid');
					 return;
				 }
				 console.log($scope.form.isEdit);
				 if(!$scope.form.isNew){
					 $scope.updateStudent();
				 }
				 else
				 {
					 $scope.addStudent();
				 }
			 }

			 $scope.submitToManagement = function(){

				 console.log('submitToManagement called in controller')
				 console.log($scope.student);
				 $scope.processing=true;
				 admissionService.submitToManagement($scope.student)
				 .then(function(response) {
					 console.log('submitToManagement Data received from service : ');
					 console.log(response);
					 if (response != null && response.data != null && response.data.responseBody != null) {
						 $scope.student = response.data.responseBody;
						 $scope.populateMissingData($scope.student);
						 $scope.getLatestAdmission();
						 $scope.form.isNew=false;
						 $scope.form.isEdit=false;
					 } else {
						 console.log(response.data.error);
						 alert(response.data.error);
					 }

					 $scope.processing=false;  
				 })
			 }

			 $scope.addStudent = function() {
				 console.log('add student called');
				 console.log($scope.student);
				 $scope.processing=true;
				 admissionService.addStudent($scope.student)
				 .then(function(response) {
					 console.log('Data received from service : ');
					 console.log(response);
					 if (response != null && response.data != null && response.data.responseBody != null) {
						 $scope.student = response.data.responseBody;
						 $scope.populateMissingData($scope.student);
						 $scope.getLatestAdmission();
						 $scope.form.isNew=false;
						 $scope.form.isEdit=false;
						 alert("Your Records Saved Successfully")
					 } else {
						 console.log(response.data.error);
						 alert(response.data.error);
					 }
					 $scope.processing=false;  
				 })
			 }

			 $scope.updateStudent = function() {
				 console.log('update student called');
				 console.log($scope.student);
				 $scope.processing=true;
				 admissionService.updateStudent($scope.student)
				 .then(function(response) {
					 console.log('Data received from service : ');
					 console.log(response);
					 if (response != null && response.data != null && response.data.responseBody != null) {
						 $scope.student = response.data.responseBody;
						 $scope.populateMissingData($scope.student);
						 $scope.getLatestAdmission();
						 $scope.form.isNew=false;
						 $scope.form.isEdit=false;
					 } else {
						 console.log(response.data.error);
						 alert(response.data.error);
					 }
					 $scope.processing=false;  
				 })
			 }

			 $scope.getStudent = function(fileNo) {
				 $scope.processing=true;
				 admissionService.getStudent(fileNo)
				 .then(function(response) {
					 console.log('Data received from service : ');
					 console.log(response);
					 if (response !=null && response.data != null && response.data.responseBody != null) {
						 $scope.student = response.data.responseBody;
						 $scope.populateMissingData($scope.student);
						 $scope.getLatestAdmission();
						 $scope.form.isNew=false;
						 $scope.form.isEdit=false;
						 $scope.dashboard=false;

					 } else {
						 console.log(response.data.error);
						 alert(response.data.error);
					 }
					 $scope.processing=false;
				 })
			 }

			 $scope.getStudentAcademicDtl = function(){

				 admissionService.getStudentAcademicDtl(student.fileNo)
				 .then(function(response) {
					 console.log('academic detail received from service : ');
					 console.log(response);
					 if (response !=null && response.data != null && response.data.responseBody != null) {
						 $scope.student.academicDtl = response.data.responseBody;

					 } else {
						 console.log(response.data.error);
						 alert(response.data.error);
					 }
				 })
			 } 
			 
			 $scope.updateStudentAcademicDtl = function(){
				 admissionService.getStudentAcademicDtl($scope.student.academicDtl, $scope.sudent.fileNo)
				 .then(function(response) {
					 console.log('updateStudentAcademicDtl called in controller : ');
					 console.log(response);
					 if (response !=null && response.data != null && response.data.responseBody != null) {
						 $scope.student.academicDtl = response.data.responseBody;

					 } else {
						 console.log(response.data.error);
						 alert(response.data.error);
					 }
				 })
			 }
			 
			 $scope.getStudentAddress = function(){
				 admissionService.getStudentAddress(student.fileNo)
				 .then(function(response) {
					 console.log('address detail received from service : ');
					 console.log(response);
					 if (response !=null && response.data != null && response.data.responseBody != null) {
						 $scope.student.addressDtl = response.data.responseBody;

					 } else {
						 console.log(response.data.error);
						 alert(response.data.error);
					 }
				 })				 
				 
			 }
			 
			 $scope.updateStudentAddress = function(){
				 admissionService.updateStudentAddress($scope.student.addressDtl, $scope.sudent.fileNo)
				 .then(function(response) {
					 console.log('updateStudentAddress called i controller : ');
					 console.log(response);
					 if (response !=null && response.data != null && response.data.responseBody != null) {
						 $scope.student.addressDtl = response.data.responseBody;

					 } else {
						 console.log(response.data.error);
						 alert(response.data.error);
					 }
				 })
			 }

			 $scope.getStudentDiscountDtl = function(){
				 admissionService.getStudentDiscountDtl(student.fileNo)
				 .then(function(response) {
					 console.log('student discount detail received from service : ');
					 console.log(response);
					 if (response !=null && response.data != null && response.data.responseBody != null) {
						 $scope.student.discountDtl = response.data.responseBody;

					 } else {
						 console.log(response.data.error);
						 alert(response.data.error);
					 }
				 })
			 }
			 
			 $scope.updateStudentDiscountDtl = function(){
				 admissionService.updateStudentDiscountDtl($scope.student.discountDtl, $scope.sudent.fileNo)
				 .then(function(response) {
					 console.log('updateStudentDiscountDtl called in controller : ');
					 console.log(response);
					 if (response !=null && response.data != null && response.data.responseBody != null) {
						 $scope.student.discountDtl = response.data.responseBody;

					 } else {
						 console.log(response.data.error);
						 alert(response.data.error);
					 }
				 })
			 }
			 
			 $scope.getBranchPref=  function(){
				 admissionService.getBranchPref(student.fileNo)
				 .then(function(response) {
					 console.log('branchpref detail received from service : ');
					 console.log(response);
					 if (response !=null && response.data != null && response.data.responseBody != null) {
						 $scope.student.branchPreference = response.data.responseBody;

					 } else {
						 console.log(response.data.error);
						 alert(response.data.error);
					 }
				 })
			 }
			 
			 $scope.updateBranchPref = function(){
				 admissionService.updateBranchPref($scope.student.branchPreference, $scope.sudent.fileNo)
				 .then(function(response) {
					 console.log('updateBranchPref called in controller : ');
					 console.log(response);
					 if (response !=null && response.data != null && response.data.responseBody != null) {
						 $scope.student.branchPreference = response.data.responseBody;

					 } else {
						 console.log(response.data.error);
						 alert(response.data.error);
					 }
				 })				 
			 }

			 $scope.getCounsellingDtl= function(){
				 admissionService.getCounsellingDtl(student.fileNo)
				 .then(function(response) {
					 console.log('counselling detail received from service : ');
					 console.log(response);
					 if (response !=null && response.data != null && response.data.responseBody != null) {
						 $scope.student.counsellingDtl = response.data.responseBody;

					 } else {
						 console.log(response.data.error);
						 alert(response.data.error);
					 }
				 })
			 }
			 
			 $scope.updateCounsellingDtl = function(){
				 admissionService.updateCounsellingDtl($scope.student.counsellingDtl, $scope.sudent.fileNo)
				 .then(function(response) {
					 console.log('updateCounsellingDtl called in controller : ');
					 console.log(response);
					 if (response !=null && response.data != null && response.data.responseBody != null) {
						 $scope.student.counsellingDtl = response.data.responseBody;

					 } else {
						 console.log(response.data.error);
						 alert(response.data.error);
					 }
				 })
			 }
			 
			 $scope.getConsultantDtl = function(){
				 admissionService.getConsultantDtl(student.fileNo)
				 .then(function(response) {
					 console.log('consulant detail received from service : ');
					 console.log(response);
					 if (response !=null && response.data != null && response.data.responseBody != null) {
						 $scope.student.consultantDetail = response.data.responseBody;

					 } else {
						 console.log(response.data.error);
						 alert(response.data.error);
					 }
				 })
			 }
			 
			 $scope.updateConsultantDtl = function(){
				 admissionService.updateConsultantDtl($scope.student.counsellingDtl, $scope.sudent.fileNo)
				 .then(function(response) {
					 console.log('updateConsultantDtl called in controller : ');
					 console.log(response);
					 if (response !=null && response.data != null && response.data.responseBody != null) {
						 $scope.student.counsellingDtl = response.data.responseBody;

					 } else {
						 console.log(response.data.error);
						 alert(response.data.error);
					 }
				 })
			 }
			 
			 $scope.getScholarshipDtl = function(){
				 admissionService.getScholarshipDtl(student.fileNo)
				 .then(function(response) {
					 console.log('scholarship detail received from service : ');
					 console.log(response);
					 if (response !=null && response.data != null && response.data.responseBody != null) {
						 $scope.student.scholarship = response.data.responseBody;

					 } else {
						 console.log(response.data.error);
						 alert(response.data.error);
					 }
				 })				 
			 }
			 
			 $scope.updateScholarshipDtl = function(){
				 admissionService.updateScholarshipDtl($scope.student.scholarship, $scope.sudent.fileNo)
				 .then(function(response) {
					 console.log('updateScholarshipDtl called in controller : ');
					 console.log(response);
					 if (response !=null && response.data != null && response.data.responseBody != null) {
						 $scope.student.scholarship = response.data.responseBody;

					 } else {
						 console.log(response.data.error);
						 alert(response.data.error);
					 }
				 })				 
			 }
			 
			 $scope.getLatestAdmission = function(){

				 admissionService.getLatestAdmission($scope.currentFetchLimit)
				 .then(function(response) {
					 console.log('Latest admission data received in controller : ');
					 console.log(response);
					 if (response !=null && response.data != null && response.data.responseBody != null) {
						 $scope.latestAdmission = response.data.responseBody;

					 } else {
						 console.log(response.data.error);
						 alert(response.data.error);
					 }
				 })
			 }
			 
			 $scope.showDetail =  function(index){

				 var fileNo=$scope.latestAdmission[index].fileNo;
				 if(fileNo){
					 $scope.getStudent(fileNo);
				 }
				 else
				 {
					 alert("No valid fileNo provided");
				 }
			 }

			 $scope.showStudentDetail =  function(index){

				 var fileNo=$scope.searchResultList[index].fileNo;
				 if(fileNo){
					 $scope.getStudent(fileNo);
				 }
				 else
				 {
					 alert("No valid fileNo provided");
				 }
			 }

			 $scope.getStudentByCriteria = function() {
				 console.log('get student by search criteria in controller');
				 console.log($scope.searchCriteria);
				 $scope.processing=true;
				 $scope.currentPage=0;
				 admissionService.getStudentByCriteria($scope.searchCriteria)
				 .then(function(response) {
					 console.log('Data received from service in controller : ');
					 console.log(response);
					 if (response != null && response.data != null && response.data.responseBody != null) {
						 $scope.searchResultList=response.data.responseBody;
						 $scope.showCriteria=false;
						 $scope.currentPage=1;
						 $scope.totalItems = $scope.searchResultList.length;
					 } else {
						 console.log(response.data.error);
						 alert(response.data.error);
					 }

					 $scope.processing=false;
				 })
			 }


			 $scope.next=function(){
				 var selectionIndex=$scope.subModules.indexOf($scope.selection);
				 if(selectionIndex != $scope.subModules.length-1){
					 selectionIndex=selectionIndex+1;
					 $scope.selection=$scope.subModules[selectionIndex];
					 $scope.form.direction=1;
				 }
			 }

			 $scope.prev=function(){
				 var selectionIndex=$scope.subModules.indexOf($scope.selection);
				 if(selectionIndex != 0){
					 selectionIndex=selectionIndex-1;
					 $scope.selection=$scope.subModules[selectionIndex];
					 $scope.form.direction=0;
				 }
			 }

			 $scope.resetForm = function(){

				 console.log("calling reset.....")
				 $scope.form.processing=false;a
				 $scope.form.isNew=true;
				 $scope.form.isEdit=true;
				 $scope.student = {};
				 $scope.student.addressDtl = [];

				 $scope.student.academicDtl = [];
				 $scope.student.academicDtl.push(angular.copy($scope.dummyQualification));

				 $scope.student.discountDtl = [];
				 $scope.student.discountDtl.push(angular.copy($scope.dummyDiscountDtl));

				 $scope.student.counsellingDtl = [];
				 $scope.student.counsellingDtl.push(angular.copy($scope.dummyCounsellingDtl));

				 $scope.student.consultantDetail=[];
				 $scope.student.consultantDetail.push(angular.copy($scope.dummyConsultantDetail));
			 }

			 $scope.saveAmenity = function(amenityStaging) {

				 console.log('saveAmenity called in controller');

				 var fileNo = $scope.student.fileNo;
				 amenityStaging.fileNo = fileNo;

				 admissionService.saveAmenity(amenityStaging)
				 .then(function(response) {
					 console.log('saveAmenity Data received from service in controller : ');
					 console.log(response);
					 if (response != null && response.data != null && response.data.responseBody != null) {

						 $scope.getStudent(fileNo);

					 } else {
						 console.log(response.data.error);
						 alert(response.data.error);
					 }
				 })
			 }


			 $scope.resetAdmissionMode = function(){

				 $scope.student.counsellingDtl = [];
				 $scope.student.counsellingDtl.push(angular.copy($scope.dummyCounsellingDtl));

				 $scope.student.referredBy = null;

			 }
			 $scope.populateMissingData = function(data){

				 if(angular.isUndefined(data.academicDtl) || data.academicDtl==null || data.academicDtl.length == 0)
				 {
					 $scope.student.academicDtl = [];
					 $scope.student.academicDtl.push(angular.copy($scope.dummyQualification))
				 }

				 if(angular.isUndefined(data.discountDtl) || data.discountDtl==null || data.discountDtl.length == 0)
				 {
					 $scope.student.discountDtl = [];
					 $scope.student.discountDtl.push(angular.copy($scope.dummyDiscountDtl));
				 }

				 if(angular.isUndefined(data.consultantDetail) || data.consultantDetail==null || data.consultantDetail.length == 0)
				 {
					 $scope.student.consultantDetail=[];
					 $scope.student.consultantDetail.push(angular.copy($scope.dummyConsultantDetail));
				 }

				 if(angular.isUndefined(data.counsellingDtl) || data.counsellingDtl==null || data.counsellingDtl.length == 0)
				 {
					 $scope.student.counsellingDtl = [];
					 $scope.student.counsellingDtl.push(angular.copy($scope.dummyCounsellingDtl))
				 }

				 for(var i=0; i<$scope.student.academicDtl.length; i++){

					 if(angular.isUndefined($scope.student.academicDtl[i].qualificationSubDtl) ||$scope.student.academicDtl[i].qualificationSubDtl==null || $scope.student.academicDtl[i].qualificationSubDtl.length==0)
					 {

						 $scope.student.academicDtl[i].qualificationSubDtl=[];
						 $scope.student.academicDtl[i].qualificationSubDtl.push(angular.copy($scope.dummyQualificationSubDtl))
					 }
				 } 
			 }

			 $scope.showTransportModal=function (size) {

				 if(!$scope.form.isEdit && !scope.form.isNew){
					 return;
				 }
				 var fileNo=$scope.student.fileNo;
				 if($scope.form.isNew){
					 alert("Please save record before reserving transport");
					 return;
				 }

				 var modalInstance = $modal.open({
					 templateUrl: 'transportReservation.html',
					 controller: 'transportController',
					 scope:$scope,
					 size: size,
					 resolve: {
						 items: function () {
							 return $scope.items;
						 }
					 }
				 });

				 modalInstance.result.then(function (selectedItem) {
					 $scope.selected = selectedItem;
				 });
			 };


			 $scope.showHostelModal=function (size) {

				 if(!$scope.form.isEdit && !scope.form.isNew){
					 return;
				 }
				 var fileNo=$scope.student.fileNo;

				 if($scope.form.isNew){
					 alert("Please save record before reserving hostel");
					 return;
				 }

				 var modalInstance = $modal.open({
					 templateUrl: 'hostelReservation.html',
					 controller: 'hostelController',
					 scope:$scope,
					 size: size,
					 resolve: {
						 items: function () {
							 return $scope.items;
						 }
					 }
				 });

				 modalInstance.result.then(function (selectedItem) {
					 $scope.selected = selectedItem;
				 });
			 };

			 $scope.saveAmenityPopup = function (size) {

				 var modalInstance = $modal.open({
					 templateUrl: 'amenity.html',
					 controller: 'feeController',
					 scope:$scope,
					 size: size,
				 });
			 };

			 $scope.today = function() {
				 $scope.dt = new Date();
			 };
			 $scope.today();

			 $scope.clear = function () {
				 $scope.dt = null;
			 };

			 // Disable weekend selection
			 $scope.disabled = function(date, mode) {
				 return ( mode === 'day' && ( date.getDay() === 0 || date.getDay() === 6 ) );
			 };

			 $scope.open = function($event) {
				 $event.preventDefault();
				 $event.stopPropagation();

				 $scope.opened = true;
			 };

			 $scope.dateOptions = {
					 formatYear: 'yy',
					 startingDay: 1
			 };
		 } ])

		 
		 admissionModule.service("admissionService", function($http, $q) {

			 // Return public API.
			 return ({
				 saveStudent : saveStudent,
				 getStudent : getStudent,
				 getStudentAcademicDtl : getStudentAcademicDtl,
				 updateStudentAcademicDtl : updateStudentAcademicDtl,
				 getStudentAddress : getStudentAddress,
				 updateStudentAddress : updateStudentAddress,
				 getStudentDiscountDtl : getStudentDiscountDtl,
				 updateStudentDiscountDtl : updateStudentDiscountDtl,
				 getBranchPref : getBranchPref,
				 updateBranchPref : updateBranchPref,
				 getCounsellingDtl : getCounsellingDtl,
				 updateCounsellingDtl : updateCounsellingDtl,
				 getConsultantDtl : getConsultantDtl,
				 updateConsultantDtl : updateConsultantDtl,
				 getScholarshipDtl : getScholarshipDtl,
				 updateScholarshipDtl : updateScholarshipDtl,
				 getLatestAdmission : getLatestAdmission,
				 getStudentByCriteria : getStudentByCriteria,
				 submitToManagement:submitToManagement,
				 saveAmenity:saveAmenity
			 });

			 function addStudent(student) {

				 console.log('add student called in service');
				 var request = $http({
					 method : "post",
					 url : "admission/student/"+fileNo,
					 params : "",
					 data : student

				 });

				 return (request.then(handleSuccess, handleError));
			 }

			 function getStudent(fileNo) {

				 var request = $http({
					 method : "get",
					 url : "admission/"+fileNo,
					 params : {
						 action : "get"
					 }
				 });
				 return (request.then(handleSuccess, handleError));
			 }

			 function getStudentAcademicDtl(fileNo){
				 var request = $http({
					 method : "get",
					 url : "admission/student/academic/"+fileNo,
					 params : {
						 action : "get"
					 }
				 });
				 return (request.then(handleSuccess, handleError));
			 }

			 function updateStudentAcademicDtl(academicDtl, fileNo){
				 var request = $http({
					 method : "put",
					 url : "admission/student/academic"+fileNo,
					 params : "",
					 data : academicDtl
				 });
				 return (request.then(handleSuccess, handleError));
			 }			 

			 function getStudentAddress(fileNo){
				 var request = $http({
					 method : "get",
					 url : "admission/student/address/"+fileNo,
					 params : {
						 action : "get"
					 }
				 });
				 return (request.then(handleSuccess, handleError));				 
			 }

			 function updateStudentAddress(addressDtl,fileNo){
				 var request = $http({
					 method : "put",
					 url : "admission/student/address/"+fileNo,
					 params : "",
					 data : addressDtl
				 });
				 return (request.then(handleSuccess, handleError));
			 }

			 function getStudentDiscountDtl(fileNo){
				 var request = $http({
					 method : "get",
					 url : "admission/student/discount/"+fileNo,
					 params : {
						 action : "get"
					 }
				 });
				 return (request.then(handleSuccess, handleError));
			 }

			 function updateStudentDiscountDtl(DiscountDtl, fileNo){

				 var request = $http({
					 method : "put",
					 url : "admission/student/discount/"+fileNo,
					 params : "",
					 data : DiscountDtl
				 });
				 return (request.then(handleSuccess, handleError));
			 }

			 function getBranchPref(fileNo){
				 var request = $http({
					 method : "get",
					 url : "admission/student/branchpref/"+fileNo,
					 params : {
						 action : "get"
					 }
				 });
				 return (request.then(handleSuccess, handleError));
			 }

			 function updateBranchPref (branchPreference, fileNo){
				 var request = $http({
					 method : "put",
					 url : "admission/student/branchpref/"+fileNo,
					 params : "",
					 data : branchPreference
				 });
				 return (request.then(handleSuccess, handleError));
			 }

			 function getCounsellingDtl(fileNo){
				 var request = $http({
					 method : "get",
					 url : "admission/student/counselling/"+fileNo,
					 params : {
						 action : "get"
					 }
				 });
				 return (request.then(handleSuccess, handleError));				 
			 }

			 function updateCounsellingDtl(counsellingDtl, fileNo){
				 var request = $http({
					 method : "put",
					 url : "admission/student/counselling/"+fileNo,
					 params : "",
					 data : counsellingDtl
				 });
				 return (request.then(handleSuccess, handleError));
			 }

			 function getScholarshipDtl(fileNo){
				 var request = $http({
					 method : "get",
					 url : "admission/student/scholarship/"+fileNo,
					 params : {
						 action : "get"
					 }
				 });
				 return (request.then(handleSuccess, handleError));
			 }

			 function updateScholarshipDtl(scholarship, fileNo){
				 var request = $http({
					 method : "put",
					 url : "admission/student/scholarship/"+fileNo,
					 params : "",
					 data : scholarship
				 });
				 return (request.then(handleSuccess, handleError));
			 }

			 function getConsultantDtl(fileNo){
				 var request = $http({
					 method : "get",
					 url : "admission/student/consultant/"+fileNo,
					 params : {
						 action : "get"
					 }
				 });
				 return (request.then(handleSuccess, handleError));
			 }
			 
			 function updateCounsellingDtl(consultantDetail, fileNo){
				 var request = $http({
					 method : "put",
					 url : "admission/student/consultant/"+fileNo,
					 params : "",
					 data : consultantDetail
				 });
				 return (request.then(handleSuccess, handleError));
			 }
			 
			 function getLatestAdmission(limit){

				 console.log("Latest Admission called from service")
				 var request = $http({
					 method : "get",
					 url : "admission/LatestAdmissionInfo/"+limit,
					 params : {
						 action : "get"
					 }
				 });

				 return (request.then(handleSuccess, handleError));
			 }

			 function getStudentByCriteria(searchCriteria){

				 console.log('Getting student by search criteria in service');
				 var request = $http({
					 method : "post",
					 url : "admission/search/",
					 params : "",
					 data : searchCriteria

				 });

				 return (request.then(handleSuccess, handleError));
			 }

			 function submitToManagement(student){

				 console.log('submit to management called in service');
				 var request = $http({
					 method : "post",
					 url : "admission/submitToManagement/",
					 params : "",
					 data : student

				 });

				 return (request.then(handleSuccess, handleError));
			 }

			 function saveAmenity(feeStaging) {

				 console.log('save amenity called in service');
				 var request = $http({
					 method : "post",
					 url : "fee/saveAmenity/",
					 params : "",
					 data : feeStaging

				 });

				 return (request.then(handleSuccess, handleError));
			 }

			 function handleError(response) {
				 console.log('Error occured while calling service');
				 console.log(response);
				 if (!angular.isObject(response.data) || !response.data.message) {

					 return ($q.reject("An unknown error occurred."));

				 }
				 // Otherwise, use expected error message.
				 return ($q.reject(response.data.message));
			 }

			 // I transform the successful response, unwrapping the application data
			 // from the API response payload.
			 function handleSuccess(response) {
				 console.log('handle success');
				 console.log(response);
				 return (response);

			 }

		 });

