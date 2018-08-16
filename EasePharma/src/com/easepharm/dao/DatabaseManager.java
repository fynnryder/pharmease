package com.easepharm.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.easepharm.entity.Auth;
import com.easepharm.entity.BaseEntity;
import com.easepharm.entity.Doctor;
import com.easepharm.entity.Pharmacist;
import com.easepharm.entity.Prescription;
import com.easepharm.entity.SubscriptionRequest;

public class DatabaseManager {
	
	private static DatabaseManager instance = new DatabaseManager();
	private static EntityManager em;
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("pharmeasePU");
	
	private DatabaseManager() {
		
	}
	
	public static DatabaseManager getInstance() {
		return instance;
	}
	
	public void registerUser(BaseEntity userObj,Auth authObj) {
		
		em = emf.createEntityManager();
		
		em.getTransaction().begin();
        em.persist(userObj);
        
        long id  = userObj.getId();
        authObj.setUserId(id);
        em.persist(authObj);
        
        
        em.getTransaction().commit();
	}
	
	public void saveToDatabase(BaseEntity entity) {
		em = emf.createEntityManager();
		
		em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
	}
	public void saveToDatabase(SubscriptionRequest entity) {
		em = emf.createEntityManager();
		
		em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
	}
	public int approveRequest(long requestId,long patientId,String type) {
		
		em = emf.createEntityManager();
		type = type.equals("Doctor") ? "doc" : "pharm";
		String sql = "UPDATE SubscriptionRequest SR SET SR.status=:status where SR.requesterId=:requestId and SR.userId=:patientId and SR.userType=:type";
		
		em.getTransaction().begin();
		int result = em.createQuery(sql).setParameter("status", 1).setParameter("patientId", patientId).setParameter("requestId", requestId).setParameter("type", type).executeUpdate();
        em.getTransaction().commit();
        
		return result;
	}
	
	public JSONObject getPendingApprovals(Long userId) throws JSONException{
		em = emf.createEntityManager();
		String sql = "Select S From SubscriptionRequest S where S.status=:status and S.userId=:userId";
		List<SubscriptionRequest> list = em.createQuery(sql).setParameter("userId", userId).setParameter("status", 0).getResultList();
		
		List<Long> listOfDoc  = new ArrayList<Long>();
		List<Long> listOfPharm  = new ArrayList<Long>();
		for(SubscriptionRequest req : list) {
			if("doc".equals(req.getUserType())){
				listOfDoc.add(req.getRequesterId());
			}
			if("pharm".equals(req.getUserType())){
				listOfPharm.add(req.getRequesterId());
			}
		}
		
		String sqlD = "Select D From Doctor D where D.id in (:list)";
		String sqlP = "Select D From Pharmacist D where D.id in (:list)";
		
		List<BaseEntity> doc  = new ArrayList<BaseEntity>();
		if(!listOfDoc.isEmpty()) {
			em = emf.createEntityManager();
			doc = em.createQuery(sqlD).setParameter("list",listOfDoc).getResultList();
		}
		
		List<BaseEntity> pharm = new ArrayList<BaseEntity>();
		if(!listOfPharm.isEmpty()) {
			em = emf.createEntityManager();
			pharm = em.createQuery(sqlP).setParameter("list",listOfPharm).getResultList();
		}
		
		JSONArray out = new JSONArray();
		
		for(BaseEntity ent : doc) {
			JSONObject obj = new JSONObject();
			Doctor bean = (Doctor)ent;
			obj.put("id",bean.getId());
			obj.put("lic", bean.getLicenceNo());
			obj.put("name", bean.getName());
			obj.put("type", "Doctor");
			out.put(obj);
		}
		for(BaseEntity ent : pharm) {
			JSONObject obj = new JSONObject();
			Pharmacist bean = (Pharmacist)ent;
			obj.put("id",bean.getId());
			obj.put("lic", bean.getRegistrationNo());
			obj.put("name", bean.getName());
			obj.put("type", "Pharmacist");
			out.put(obj);
		}
		
		JSONObject object = new JSONObject();
		object.put("data", out);
		return object;
	}
	
	public List<BaseEntity> getPatientList() {
		
		em = emf.createEntityManager();
		List<BaseEntity> list = em.createQuery("Select e from Patient e").getResultList();
		
		return list;
	}
	
	public Prescription getPrescription(long userId) {
		em = emf.createEntityManager();
		List<Prescription> entity = em.createQuery("Select S from Prescription S where S.userId=:userId")
				.setParameter("userId", userId).getResultList();
		
		
		if(entity == null || entity.size() == 0)
			return null;
		
		return entity.get(0);
		
	}
	
	public SubscriptionRequest isRequestApproved(long requesterId, long userId) {
		em = emf.createEntityManager();
		String sql = "Select S from SubscriptionRequest S where S.requesterId=:requesterId and S.userId=:userId";
		List<SubscriptionRequest> entity  = em.createQuery(sql).setParameter("requesterId", requesterId).setParameter("userId", userId).getResultList();
		
		if(entity == null || entity.size() == 0)
			return null;
		
		return entity.get(0);
	}
	
	public BaseEntity getDoctor(long userId) {
		em = emf.createEntityManager();
		String sql = "Select S from Doctor S where S.id=:userId";
		List<BaseEntity> instance = em.createQuery(sql).setParameter("userId", userId).getResultList();
		
		if(instance == null || instance.size() == 0) {
			return null;
		}
		else {
			return instance.get(0);
		}
		
	}
	
	public BaseEntity getPatient(long userId) {
		em = emf.createEntityManager();
		String sql = "Select S from Patient S where S.id=:userId";
		List<BaseEntity> instance = em.createQuery(sql).setParameter("userId", userId).getResultList();
		
		if(instance == null || instance.size() == 0) {
			return null;
		}
		else {
			return instance.get(0);
		}
		
	}
	public BaseEntity getPharm(long userId) {
		em = emf.createEntityManager();
		String sql = "Select S from Pharmacist S where S.id=:userId";
		List<BaseEntity> instance = em.createQuery(sql).setParameter("userId", userId).getResultList();
		
		if(instance == null || instance.size() == 0) {
			return null;
		}
		else {
			return instance.get(0);
		}
	}
	public Auth getAuthObject(String email,String password) {
		em = emf.createEntityManager();
		String sql = "Select S from Auth S where S.userEmail=:email";
		Auth instance = null;
		List<Object> list = em.createQuery(sql).setParameter("email", email).getResultList();
		if(list == null || list.isEmpty()) {
			System.out.println("NO OBJECT FOUND=================================================");
		}
		else {
			instance = (Auth)list.get(0);
		}
		return instance;
	}

}
