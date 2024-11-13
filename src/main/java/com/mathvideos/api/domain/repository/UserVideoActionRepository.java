package com.mathvideos.api.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mathvideos.api.entity.UserVideoAction;

@Repository
public interface UserVideoActionRepository extends JpaRepository<UserVideoAction, Long> {

	@Query("SELECT uva FROM UserVideoAction uva WHERE uva.user.id = :userId AND uva.video.id = :videoId")
	Optional<UserVideoAction> getByUserIdAndVideoId(@Param("userId") String userId, @Param("videoId") String videoId);
}
