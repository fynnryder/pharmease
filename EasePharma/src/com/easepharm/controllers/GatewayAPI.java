package com.easepharm.controllers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.easepharm.utils.Response;
import com.easepharm.actions.WebAction;


@WebServlet("/services/action")
public class GatewayAPI  extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		doPost(request,response);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String method = request.getParameter("method");
		String payload = request.getParameter("payload");
		Response apiResponse = new Response();
		JSONObject returnJson = new JSONObject();
		PrintWriter out = response.getWriter();
		WebAction action = WebAction.getInstance();
		try{
		
			switch(method){
			
			
			case "REGISTER":
				apiResponse = action.registerUser(payload, request);
				break;	
			
			case "LOGIN":
				apiResponse = action.loginUser(payload,request);
				break;
			case "GET_PATIENT_LIST":
				apiResponse = action.getUsersList();
				break;
			case "VIEW_PRESCRIPTION":
				apiResponse = action.viewPrecription(payload, request);
				break;
			case  "GET_PERMISSION":
				apiResponse = action.getPermissions(payload, request);
				break;
			case  "LIST_APPROVALS":
				apiResponse = action.listApporvalRequest(payload, request);
				break;	
			case  "APPROVE_REQUEST":
				apiResponse = action.approveRequest(payload, request);
				break;		
		}
		
		request.setCharacterEncoding("utf8");
		response.setContentType("application/json");
		
		returnJson.put("response", apiResponse.getResponse() == null ? apiResponse.getResponseJson().toString():apiResponse.getResponse());
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
        out.write(returnJson.toString());
        out.close();
		
	}

}

