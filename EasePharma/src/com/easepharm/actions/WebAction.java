package com.easepharm.actions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import com.easepharm.dao.DatabaseManager;
import com.easepharm.entity.Auth;
import com.easepharm.entity.BaseEntity;
import com.easepharm.entity.Doctor;
import com.easepharm.entity.Patient;
import com.easepharm.entity.Pharmacist;
import com.easepharm.entity.Prescription;
import com.easepharm.entity.SubscriptionRequest;
import com.easepharm.utils.Response;


public class WebAction {
	
	public static WebAction instance = new WebAction();
	
	private WebAction(){
		
	}
	
	public Response registerUser(String payLoad, HttpServletRequest request) throws JSONException {
		
		Response response = new Response();
		JSONObject outResp = new JSONObject();
		try {
			JSONObject userObject = new JSONObject(payLoad);
			String userType = userObject.optString("userType");
			String address = userObject.optString("address");
			String primaryId = userObject.optString("primaryId");
			String name = userObject.optString("firstName");
			String email = userObject.optString("email");
			String password = userObject.optString("password");
			
			Auth auth = new Auth();
			auth.setUserEmail(email);
			auth.setUserPassword(password);
			auth.setUserType(userType);
			BaseEntity entity = null;
			if("doc".equals(userType)) {
				entity = new Doctor(name,address,primaryId);
			}
			
			if("user".equals(userType)) {
				entity = new Patient(name,address,primaryId);	
			}
			
			if("pharm".equals(userType)) {
				entity = new Pharmacist(name,address,primaryId);
			}
			
			DatabaseManager.getInstance().registerUser(entity, auth);
			response.setStatus("success");
			outResp.put("status", "success");
			outResp.put("errorCode", "200");
			outResp.put("message", "Saved to database");
			
		}
		catch(Exception ex) {
			response.setStatus("failure");
			outResp.put("status", "error");
			outResp.put("errorCode", "-200");
			outResp.put("message", "Failed to create user");
			ex.printStackTrace();
			
		}	
		response.setResponseJson(outResp);
		return response;
	}
	
	public Response getUsersList() throws JSONException{
		
		Response response = new Response();
		List<BaseEntity> list = DatabaseManager.getInstance().getPatientList();
		List<JSONObject> returnList = new ArrayList<JSONObject>();
		JSONObject outResp = new JSONObject();
		for(BaseEntity object : list) {
			
			Patient patient = (Patient)object;
			JSONObject obj = new JSONObject();
			obj.put("id", patient.getId());
			obj.put("name", patient.getName());
			obj.put("phone", patient.getPhoneNo());
			obj.put("address", patient.getAddress());
			returnList.add(obj);
		}
		
		outResp.put("data", returnList);
		response.setResponseJson(outResp);
		response.setStatus("success");
		
		return response;
	}
	
	public Response listApporvalRequest(String payLoad, HttpServletRequest request) {
		
		Response response = new Response();
		try {
			Long rid = (Long)request.getSession().getAttribute("userId");
			JSONObject req = DatabaseManager.getInstance().getPendingApprovals(rid);
			response.setStatus("success");
			response.setResponseJson(req);
		}
		catch(Exception ex) {
			response.setStatus("error");
			ex.printStackTrace();
		}
		
		return response;
	}
	
	public Response getPermissions(String payLoad, HttpServletRequest request) throws JSONException {
		
		Response response = new Response();
		try {
			JSONObject userObject = new JSONObject(payLoad);
			Long rid = (Long)request.getSession().getAttribute("userId");
			String userId = userObject.optString("userId");
			String type = (String)request.getSession().getAttribute("userType");
			SubscriptionRequest object = new SubscriptionRequest(rid,userId,type);
			DatabaseManager.getInstance().saveToDatabase(object);
		}
		catch(Exception ex) {
			response.setStatus("error");
			ex.printStackTrace();
		}
		
		response.setStatus("success");
		return response;
	}
	
	public Response viewPrecription(String payLoad, HttpServletRequest request) throws JSONException {
		Response response = new Response();
		JSONObject userObject = new JSONObject(payLoad);
		String pid = userObject.optString("id");
		Long rid = (Long)request.getSession().getAttribute("userId");
		JSONObject outResp = new JSONObject();
		if(pid != null) {
			try {
				long id = Long.parseLong(pid);
				long requesterId = rid;
				SubscriptionRequest pEntity = DatabaseManager.getInstance().isRequestApproved(requesterId,id);
				
				if(pEntity == null) {
					response.setStatus("success");
					outResp.put("message", "Ask for approval");
					outResp.put("data", "");
				}else {
					if(pEntity.getStatus() == 0) {
						outResp.put("message", "Not Yet Approved");
						response.setStatus("success");
						outResp.put("data", "");
					}
					else {
						//Prescription entity = DatabaseManager.getInstance().getPrescription(id);
						//Prescription eobj = (Prescription)entity;
						outResp.put("message", "Show Prescription");
						response.setStatus("success");
					}
				}
				
			}
			catch(NumberFormatException ex) {
				ex.printStackTrace();
			}
		}
		
		response.setResponseJson(outResp);
		return response;
		
	}
	
	public Response approveRequest(String payLoad, HttpServletRequest request) {
		
		Response response = new Response();
		try {
			JSONObject userObject = new JSONObject(payLoad);
			Long requesterId = userObject.optLong("id");
			String type = userObject.optString("type");
			
			Long patientId = (Long)request.getSession().getAttribute("userId");
			JSONObject outResp = new JSONObject();
			if(requesterId != null) {
				try {
					
					int result = DatabaseManager.getInstance().approveRequest(requesterId,patientId,type);
					
					if(result == 1) {
						outResp.put("message", "Successfully approved");
						response.setStatus("success");
					}
				}
				catch(NumberFormatException ex) {
					response.setStatus("failure");
				}
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
			response.setStatus("failure");
		}
		
		return response;
	}
	
	public Response loginUser(String payLoad, HttpServletRequest request){
		Response response = new Response();
		try {
			JSONObject userObject = new JSONObject(payLoad);
			String password = userObject.optString("password");
			String email = userObject.optString("email");
			JSONObject outResp = new JSONObject();

			if (password == null || password.length() == 0) {
				response.setStatus("error");
				outResp.put("status", "error");
				outResp.put("errorCode", "-201");
				outResp.put("message", "Password cannot be empty");
				response.setResponseJson(outResp);
				return response;
			}
			if (email == null || email.length() == 0) {
				response.setStatus("error");
				outResp.put("status", "error");
				outResp.put("errorCode", "-201");
				outResp.put("message", "Email cannot be empty");
				response.setResponseJson(outResp);
				return response;
			}

			try {
					Auth instance = DatabaseManager.getInstance().getAuthObject(email, password);
					
					if(instance != null) {
						String passFromDb = instance.getUserPassword();
						String userType = instance.getUserType();
						long userId = instance.getUserId();
						
						if(passFromDb.equals(password)) {
							
							BaseEntity user = null;
							if("doc".equals(userType)) {
								user = DatabaseManager.getInstance().getDoctor(userId);
							}
							
							if("user".equals(userType)) {
								user = DatabaseManager.getInstance().getPatient(userId);
							}
							
							if("pharm".equals(userType)) {
								user = DatabaseManager.getInstance().getPharm(userId);
							}
							
							HttpSession session = request.getSession(true);
							session.setAttribute("userName",user.getName() );
							session.setAttribute("userId",user.getId() );
							session.setAttribute("userType", userType);
							
							response.setStatus("success");
							outResp.put("errorCode", "");
							outResp.put("message", "Login Successfull");
							outResp.put("userType", userType);
							response.setResponseJson(outResp);
						}
					}
			} catch (Exception ex) {
				ex.printStackTrace();
				response.setStatus("error");
				outResp.put("status", "error");
				outResp.put("errorCode", "-201");
				outResp.put("message", "Email cannot be empty");
				response.setResponseJson(outResp);
				return response;
			}
			response.setStatus("success");
		} catch (Exception ex) {
			ex.printStackTrace();
			response.setStatus("error");
			response.setResponse(ex.getMessage());
			return response;
		}

		return response;
	}	
	
	public static WebAction getInstance(){
		return instance;
	}

}
