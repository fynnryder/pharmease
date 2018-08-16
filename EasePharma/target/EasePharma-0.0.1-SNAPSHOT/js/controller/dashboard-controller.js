
Pharma.controller('patientdash-controller',['$scope','User','$state',function($scope,User,$state){
	
	User.getPendingApprovals().then(function(response){
		//console.log(response.data.response);
		$scope.requests = JSON.parse(response.data.response).data;
	})
	
	$scope.approveRequest = function(request){
		var payload = {
				type : request.type,
				id : request.id
		}
		
		User.approveRequest(payload).then(function(response){
			Materialize.toast('Request Approved!!!', 5000);
			User.getPendingApprovals().then(function(response){
				$scope.requests = JSON.parse(response.data.response).data;
			})
		})
	}
	
}]);

Pharma.controller('dashboard-controller',['$scope','User','$state',function($scope,User,$state){
	
	
	User.getPatientsList().then(function(response){
		console.log(response);
		$scope.patients = JSON.parse(response.data.response).data;
	})
	
	$scope.viewPrescription = function(user){
		$scope.showAgree = false;
		$scope.showWait = false;
		User.viewPrescription(user).then(function(response){
			var message = JSON.parse(response.data.response).message;
			$('#modal123').modal();
			if(message === "Ask for approval"){
				$scope.header = "Ask For Permission";
				$scope.message  ="To see patients prescription you need to take his permission. Would you like to ask for permission ?";
				$('#modal123').modal('open');
				$scope.showAgree = true;
			}
			else if(message === "Not Yet Approved"){
				$scope.header = "Not Yet Approved";
				$scope.message = "Approval is still pending. You will be able to see prescription once user approves your request";
				$('#modal123').modal('open');
				$scope.showWait = true;
			}
			else{
				$('#modalpres').modal();
				$('#modalpres').modal("open");
			}
			
			$scope.userId = user.id;
		})
		
	}
	
	$scope.close = function(){
		$('#modal123').modal('close');
	}

	$scope.getPermission = function(){
		$('#modal123').modal('close');
		var obj = {
			userId : $scope.userId 
		}
		User.getPermission(obj).then(function(resp){
			Materialize.toast('Prescription sent for approval', 5000);
		})
	}
	
}]);
Pharma.controller('register-controller',['$scope','User','$state',function($scope,User,$state){
	
	$scope.loginStatus = "Register here..";
	$scope.userType = "doc";
	
	
	$scope.userObject = {
			userType : "doc"
	}
	
	$scope.validateAndSubmitForm = function(){
		$scope.loginStatus = "Checking Info...";
		
		if($scope.userObject.firstName === null || $scope.userObject.firstName === undefined || $scope.userObject.firstName.length === 0){
		//	$.notify("Access granted", "success");
			$(".name").notify(
					  "Name is requried!!", 
					  { position:"bottom left" },
					  "error"
					);
			$scope.loginStatus = "Register here..";
			return;
			
		}
		if($scope.userObject.userPrimaryId === null || $scope.userObject.userPrimaryId === undefined || $scope.userObject.userPrimaryId === 0){
			
			$(".sname").notify(
					  "License is requried!!", 
					  { position:"bottom left" },
					  "error"
					);
			$scope.loginStatus = "Register here..";
			return;
			
		}
		if($scope.userObject.email === null || $scope.userObject.email === undefined || $scope.userObject.email.length === 0){
			
			$(".email").notify(
					  "Last Name is requried!!", 
					  { position:"bottom left" },
					  "error"
					);
			$scope.loginStatus = "Register here..";
			return;
	
		}
		if($scope.userObject.password === null || $scope.userObject.password === undefined || $scope.userObject.password.length === 0){
			
			$(".pass").notify(
					  "Password is requried!!", 
					  { position:"bottom left" },
					  "error"
					);
			$scope.loginStatus = "Register here..";
			return;
	
		}
		if($scope.userObject.confirmPassword === null || $scope.userObject.confirmPassword === undefined || $scope.userObject.confirmPassword.length === 0){
			
			$(".rpass").notify(
					  "Confirm Passwrod is requried!!", 
					  { position:"bottom left" },
					  "error"
					);
			$scope.loginStatus = "Register here..";
			return;
	
		}
		
		if($scope.userObject.confirmPassword !== $scope.userObject.password){
			
			$(".repass").notify(
					  "Password doenst Match!!", 
					  { position:"bottom right" },
					  "error"
					);
			$scope.loginStatus = "Register here..";
			return;
		}
		if($scope.userObject.address === null || $scope.userObject.address === undefined || $scope.userObject.address.length === 0){
			
			$(".address").notify(
					  "Password doenst Match!!", 
					  { position:"bottom right" },
					  "error"
					);
			$scope.loginStatus = "Register here..";
			return;
		}
		User.registerUser($scope.userObject).then(function(response){
			var resp = JSON.parse(response.data.response);
			console.log(resp);
			if(resp.message == "Saved to database"){
				$('#modal2').modal('close');
				$.notify("Account Created Successfully!!", "success");
				$state.transitionTo("root");
			}
			else{
				$('#modal2').modal('close');
				$.notify("Failed to create account!!", "error");
			}
		})
		
	}
	
}]);

Pharma.controller('login-controller',["$scope","User","$state",function($scope,User,$state){
	

	$scope.isError = false;
	$scope.loginStatus = "Login..."
	
	$scope.loginUser = function(){
		$scope.loginStatus = "Authenticating..";
		$scope.isError = false;
		var userObj = {
				email :  $scope.userName,
				password : $scope.password
		}
		
		if(userObj.email === null || userObj.email === undefined || userObj.email.length === 0){
			$.notify("Please recheck username and password you have entered!!", "error");
			$scope.loginStatus = "Login..."
			return;
			
		}
		if(userObj.password === null || userObj.password === undefined || userObj.password.length === 0){
			$.notify("Please recheck username and password you have entered!!", "error");
			$scope.loginStatus = "Login..."
			return;
		}
		User.loginUser(userObj).then(function(response){
			var resp = JSON.parse(response.data.response);
			if(resp.status == "success"){
				$('#modal1').modal('close');
				
				if(resp.userType === "doc")
					$state.transitionTo("dashboard");
				else if(resp.userType === "pharm")
					$state.transitionTo("dashboard");
				else
					$state.transitionTo("patientdash");
				
			}
			else{
				$scope.loginStatus = "Login..."
				$scope.isError = true;
				$.notify("Please recheck username and password you have entered!!", "error");
			}
			
		}).then(function(response){
			$scope.loginStatus = "Login..."
		})
	}
	$scope.registerUser = function(){
		$state.transitionTo("register");
	}
	
}]);

Pharma.controller('root-controller',["$scope",function($scope){

	
}])



Pharma.controller('navigation-controller',["$scope","User","$state",function($scope,User,$state){

	$scope.performLogout = function(){
		User.performLogout().then(function(){
			$state.transitionTo("root");
		})
	}
	
}]);

Pharma.controller('landing-navigation-controller',["$scope","User","$state",function($scope,User,$state){

	$(function(){
		$('.modal').modal({
		      dismissible: true, // Modal can be dismissed by clicking outside of the modal
		      opacity: .5, // Opacity of modal background
		      inDuration: 300, // Transition in duration
		      outDuration: 200, // Transition out duration
		      startingTop: '4%', // Starting top style attribute
		      endingTop: '10%'
		}// Ending top style attribute
		  );
		
		$scope.modalStyle = {
				width:'40%'
		}
		
	})
	
	$scope.performLogout = function(){
		User.performLogout().then(function(){
			$state.transitionTo("login");
		})
	}
	
	$scope.openSettings = function(){
		$state.transitionTo("settings");
	}
	
	$scope.navigateToDashBoard = function(){
		$('#modal1').modal('open');
	}

	$scope.navigateToLogs = function(){
		$('#modal2').modal('open');
	}
}]);

