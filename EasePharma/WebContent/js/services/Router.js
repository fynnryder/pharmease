Pharma.config(['$stateProvider', '$urlRouterProvider','$httpProvider',function($stateProvider, $urlRouterProvider,$httpProvider) {
	    $urlRouterProvider.otherwise('/');
	   
	    $stateProvider
		    .state('root', {
	            url:'/',
	            views :{
		        	'navigation' :{
		        		templateUrl: "views/landingnav.html",
		        		controller :'landing-navigation-controller'
		        	},
	            	'container':{
	            		controller : "root-controller"
	            	},
	            	'footer':{
	            		
	            	}
	            }
	        })
		    .state('login', {
	            url:'/login',
	            views :{
		        	'navigation' :{

		        	},
	            	'container':{
	            		templateUrl: "views/login.html",
	            		controller : "login-controller"
	            	},
	            	'footer':{
	            		
	            	}
	            }
	        })
	        .state('dashboard', {
	            url:'/dashboard',
	            views :{
		        	'navigation' :{
		        		templateUrl: "views/navigation.html",
		        		controller :'navigation-controller'
		        	},
	            	'container':{
	            		templateUrl: "views/dashboard.html",
	            		controller :'dashboard-controller'
	            	},
	            	'footer':{
	 
	            	}
	            }
	        }).state('patientdash', {
	            url:'/patientdash',
	            views :{
		        	'navigation' :{
		        		templateUrl: "views/navigation.html",
		        		controller :'navigation-controller'
		        	},
	            	'container':{
	            		templateUrl: "views/patientdash.html",
	            		controller :'patientdash-controller'
	            	},
	            	'footer':{
	 
	            	}
	            }
	        })
	        .state('register', {
	            url:'/register',
	            views :{
		        	'navigation' :{
		        		
		        	},
	            	'container':{
	            		templateUrl: "views/registration.html",
	            		controller :'register-controller'
	            	},
	            	'footer':{
	 
	            	}
	            }
	        })
	
	         
	}]);