package com.sentiment.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sentiment.model.MovieTwSearchDetail;

public class MovieTwSearchDetailMapper implements  RowMapper<MovieTwSearchDetail> {

	@Override
	public MovieTwSearchDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
		MovieTwSearchDetail movie = new MovieTwSearchDetail();
		movie.setId(rs.getInt("mov_id"));
		movie.setKeywords(rs.getString("keywords"));
		movie.setStartDate(rs.getDate("start_date"));
		movie.setEndDate(rs.getDate("end_date"));
		movie.setActive(rs.getBoolean("is_active"));
		return movie;
	}

}