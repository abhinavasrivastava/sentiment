package com.sentiment.cache;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sentiment.dao.CacheMasterDaoImpl;
import com.sentiment.exception.AppException;
import com.sentiment.model.MovieDetail;

@Repository
public class MoviesCache {

	Logger logger = Logger.getLogger(MoviesCache.class);

	@Autowired
	CacheMasterDaoImpl cacheMasterDaoImpl;

	List<MovieDetail>movies;

	@PostConstruct
	public void loadAllMovies(){
		try {
			movies = cacheMasterDaoImpl.getAllMovies();
		} catch (AppException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public List<MovieDetail> getAllMovies(){
		return movies;
	}

}
