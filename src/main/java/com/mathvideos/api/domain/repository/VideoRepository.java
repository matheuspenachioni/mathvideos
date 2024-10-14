package com.mathvideos.api.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mathvideos.api.entity.Video;

@Repository
public interface VideoRepository extends JpaRepository<Video, String> {

	Optional<Video> findById(String id);
	
	@Query(value = "SELECT v FROM Video v WHERE LOWER(v.title) LIKE LOWER(CONCAT('%', :search, '%'))"
			+ " OR LOWER(v.description) LIKE LOWER(CONCAT('%', :search, '%'))", 
            countQuery = "SELECT COUNT(v.id) FROM Video v")
	Page<Video> listAll(Pageable pageable, @Param("search") String search);
	
}
