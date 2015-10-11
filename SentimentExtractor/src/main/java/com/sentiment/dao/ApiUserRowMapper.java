package com.sentiment.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sentiment.model.ApiUser;
import com.sentiment.model.DQUser;

	
public class ApiUserRowMapper implements  RowMapper<ApiUser> {

	@Override
	public ApiUser mapRow(ResultSet rs, int rowNum) throws SQLException {
		ApiUser apiUser = new ApiUser();
		apiUser.setUser(new DQUser());
		apiUser.getUser().setUserId(rs.getLong("user_id"));
		apiUser.getUser().setUserFname(rs.getString("user_fname"));
		apiUser.getUser().setUserLname(rs.getString("user_lname"));
		apiUser.getUser().setEmailId(rs.getString("email_id"));
		apiUser.setApiId(rs.getInt("api_id"));
		apiUser.setApiAuthKey(rs.getString("api_auth_key"));
		
		return apiUser;
	}

}
