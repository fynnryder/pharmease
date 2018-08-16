Pharma.factory('User',["$http","$httpParamSerializerJQLike",function($http,$httpParamSerializerJQLike){
	var User = {
			
	}
	
	var reqObject = {
			method : 'POST',
			headers: {
			    'Content-Type': 'application/x-www-form-urlencoded' // Note the appropriate header
			  }
	};
	
	User.userData = null;
	
	User.loginUser = function(userObject){

		reqObject.method = 'POST';
		reqObject.url = "services/action";
		reqObject.data =$httpParamSerializerJQLike( {
			method : "LOGIN",
			payload : JSON.stringify(userObject)
		});
		
		return $http(reqObject).then(function(response){
			return response;
		});
	}
	
	User.registerUser = function(userObject){

		reqObject.method = 'POST';
		reqObject.url = "services/action";
		reqObject.data =$httpParamSerializerJQLike({
			method : "REGISTER",
			payload : JSON.stringify(userObject)
		});
		
		
		return $http(reqObject).then(function(response){
			return response;
		});
	}
	
	User.getPatientsList = function(userObject){

		reqObject.method = 'POST';
		reqObject.url = "services/action";
		reqObject.data =$httpParamSerializerJQLike({
			method : "GET_PATIENT_LIST",
			payload : JSON.stringify(userObject)
		});
		
		
		return $http(reqObject).then(function(response){
			return response;
		});
	}
	
	User.performLogout = function(userObject){

		reqObject.method = 'POST';
		reqObject.url = "services/action";
		reqObject.data =$httpParamSerializerJQLike({
			method : "LOGOUT"
		});
		
		
		return $http(reqObject).then(function(response){
			return response;
		});
	}
	
	User.viewPrescription = function(userObject){

		reqObject.method = 'POST';
		reqObject.url = "services/action";
		reqObject.data =$httpParamSerializerJQLike({
			method : "VIEW_PRESCRIPTION",
			payload : JSON.stringify(userObject)
		});
		
		
		return $http(reqObject).then(function(response){
			return response;
		});
	}
	User.getPermission = function(userObject){

		reqObject.method = 'POST';
		reqObject.url = "services/action";
		reqObject.data =$httpParamSerializerJQLike({
			method : "GET_PERMISSION",
			payload : JSON.stringify(userObject)
		});
		
		
		return $http(reqObject).then(function(response){
			return response;
		});
	}
	
	
	User.getPendingApprovals = function(userObject){

		reqObject.method = 'POST';
		reqObject.url = "services/action";
		reqObject.data =$httpParamSerializerJQLike({
			method : "LIST_APPROVALS",
			payload : JSON.stringify(userObject)
		});
		
		
		return $http(reqObject).then(function(response){
			return response;
		});
	}
	
	User.approveRequest = function(userObject){

		reqObject.method = 'POST';
		reqObject.url = "services/action";
		reqObject.data =$httpParamSerializerJQLike({
			method : "APPROVE_REQUEST",
			payload : JSON.stringify(userObject)
		});
		
		
		return $http(reqObject).then(function(response){
			return response;
		});
	}
	
	return User;
	
}]);