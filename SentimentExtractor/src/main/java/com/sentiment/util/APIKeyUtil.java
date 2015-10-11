package com.sentiment.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sentiment.exception.AppException;
import com.sentiment.model.ApiUser;
import com.sentiment.model.DQUser;

@Component
public class APIKeyUtil {
	
	@Autowired
	ApiKeycache apiKeycache;
	
	public String getApiKey(DQUser user, String apiType, int apiId) throws AppException{
		ApiUser apiUser = new ApiUser(user, apiId, null);
		String key = apiKeycache.getApiKey(apiUser);
		return key;
	}
	
	public String getMD5String(String input){
		MessageDigest md5 = null;
		byte[] mdbytes = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
			mdbytes = md5.digest(input.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < mdbytes.length; i++) {
          sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
        }
		return sb.toString();
	}
}
