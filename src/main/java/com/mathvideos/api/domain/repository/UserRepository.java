package com.mathvideos.api.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mathvideos.api.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	
}
