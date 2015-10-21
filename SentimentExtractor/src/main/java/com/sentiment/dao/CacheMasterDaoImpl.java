package com.sentiment.dao;

import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.sentiment.exception.AppException;
import com.sentiment.model.Movie;

@Repository
public class CacheMasterDaoImpl {
	
	Logger LOGGER = Logger.getLogger(CacheMasterDaoImpl.class);
	protected JdbcTemplate jdbcTemplate;


	@Autowired
	public void setDataSource(DataSource dataSource) {
		if(jdbcTemplate == null) {
			this.jdbcTemplate = new JdbcTemplate(dataSource);
		}
	}

	public List<Movie> getAllMovies() throws AppException{
		List<Movie> movies = null;
		String sql = "select * from movies_tweet_master where is_active=?";
		try{
			movies = jdbcTemplate.query(sql, new MovieRowMapper(), 1);
		}catch(DataAccessException e) {
			LOGGER.error(e.getMessage());
			throw new AppException(e.getMessage());
		}
		return movies;
	}
}
