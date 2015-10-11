package com.sentiment.util;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sentiment.dao.ApiDaoImpl;
import com.sentiment.exception.AppException;
import com.sentiment.model.ApiUser;

@Repository
public class ApiKeycache {

	private Map<String, ApiUser> apikeyUserCache = new ConcurrentHashMap<String, ApiUser>();
	private Map<ApiUser, String> userApikeyCache = new ConcurrentHashMap<ApiUser, String>();
	
	@Autowired
	ApiDaoImpl apiDaoImpl;

	public ApiKeycache(){
	}
	
	@PostConstruct
	private void loadCache() throws AppException {
		List<ApiUser>apiUsers = apiDaoImpl.getAllApiUsers();
		if(apiUsers != null && apiUsers.size() > 0){
			for(ApiUser apiUser : apiUsers){
				apikeyUserCache.put(apiUser.getApiAuthKey(), apiUser);
				userApikeyCache.put(apiUser, apiUser.getApiAuthKey());
			}
		}
	}
	
	public ApiUser getApiUser(String apiAuthKey){
		return apikeyUserCache.get(apiAuthKey);
	}
	
	public String getApiKey(ApiUser user){
		return userApikeyCache.get(user);
	}
}
