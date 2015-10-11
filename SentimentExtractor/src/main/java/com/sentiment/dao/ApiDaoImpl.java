package com.sentiment.dao;

import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.sentiment.exception.AppException;
import com.sentiment.model.ApiUser;

@Repository
public class ApiDaoImpl {

	Logger LOGGER = Logger.getLogger(ApiDaoImpl.class);
	protected JdbcTemplate jdbcTemplate;


	@Autowired
	public void setDataSource(DataSource dataSource) {
		if(jdbcTemplate == null) {
			this.jdbcTemplate = new JdbcTemplate(dataSource);
		}
	}

	public List<ApiUser> getAllApiUsers() throws AppException{
		List<ApiUser> apiUsers = null;
		String sql = "SELECT aat.user_id, aat.api_id, u.user_fname,u.user_lname,u.email_id "
				+ "FROM api_auth_token aat, dq_user u WHERE aat.user_id=u.user_id AND u.dq_status=?";
		try {
			apiUsers = this.jdbcTemplate.query(sql,new Object[]{1},new ApiUserRowMapper());
		}catch(EmptyResultDataAccessException ex){
			return null;
		}catch (DataAccessException e) {
			LOGGER.error(e.getMessage(), e);
			throw new AppException(e.getMessage());
		}
		return apiUsers;
	}

	public void saveApiUser(ApiUser apiUser) throws AppException {
		String sql = "insert into api_auth_token ("
				+ "user_id,"
				+ "api_id,"
				+ "api_auth_key,"
				+ "created_tx_stamp,"
				+ "last_updated_tx_stamp) "
				+ "values(?,?,?,?,?)";
		try{
			jdbcTemplate.update(sql, new Object[]{
					apiUser.getUser().getUserId(),
					apiUser.getApiId(),
					apiUser.getApiAuthKey(),
					new Date(),
					new Date()
			});
		}catch(DataAccessException e) {
			LOGGER.error(e.getMessage());
			throw new AppException(e.getMessage());
		}
	}
}
