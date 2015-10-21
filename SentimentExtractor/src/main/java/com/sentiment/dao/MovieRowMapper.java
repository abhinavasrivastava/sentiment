package com.sentiment.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sentiment.model.Movie;

public class MovieRowMapper implements  RowMapper<Movie> {

	@Override
	public Movie mapRow(ResultSet rs, int rowNum) throws SQLException {
		Movie movie = new Movie();
		movie.setId(rs.getInt("id"));
		movie.setMovieName(rs.getString("movie_name"));
		movie.setKeywords(rs.getString("keywords"));
		movie.setImgUrl(rs.getString("img_url"));
		movie.setShortImgUrl(rs.getString("short_img_url"));
		movie.setStartDate(rs.getDate("start_date"));
		movie.setEndDate(rs.getDate("end_date"));
		movie.setActive(rs.getBoolean("is_active"));
		return movie;
	}

}