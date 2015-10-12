package com.sentiment.util;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
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
	public void loadCache() throws AppException {
		List<ApiUser>apiUsers = apiDaoImpl.getAllApiUsers();
		Map<String, ApiUser> tmpApikeyUserCache = new ConcurrentHashMap<String, ApiUser>();
		Map<ApiUser, String> tmpUserApikeyCache = new ConcurrentHashMap<ApiUser, String>();
		if(apiUsers != null && apiUsers.size() > 0){
			for(ApiUser apiUser : apiUsers){
				tmpApikeyUserCache.put(apiUser.getApiAuthKey(), apiUser);
				tmpUserApikeyCache.put(apiUser, apiUser.getApiAuthKey());
			}
			apikeyUserCache = tmpApikeyUserCache;
			userApikeyCache = tmpUserApikeyCache;
		}
	}
	
	public ApiUser getApiUser(String apiAuthKey){
		ApiUser apiUser = null;
		if(StringUtils.isNotBlank(apiAuthKey)){
			apiUser = apikeyUserCache.get(apiAuthKey);
		}
		return apiUser;
	}
	
	public String getApiKey(ApiUser user){
		return userApikeyCache.get(user);
	}
}
