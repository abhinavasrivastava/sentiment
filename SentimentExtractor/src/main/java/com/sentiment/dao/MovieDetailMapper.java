package com.sentiment.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sentiment.model.MovieDetail;

public class MovieDetailMapper implements  RowMapper<MovieDetail> {

	@Override
	public MovieDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
		MovieDetail movie = new MovieDetail();
		movie.setId(rs.getInt("mov_id"));
		movie.setKeywords(rs.getString("keywords"));
		movie.setStartDate(rs.getDate("start_date"));
		movie.setEndDate(rs.getDate("end_date"));
		movie.setActive(rs.getBoolean("is_active"));
		return movie;
	}

}