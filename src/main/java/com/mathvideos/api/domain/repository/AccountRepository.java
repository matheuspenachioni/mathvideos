package com.mathvideos.api.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mathvideos.api.entity.User;

@Repository
public interface AccountRepository extends JpaRepository<User, String> {

	Optional<User> findByUsername(String username);
	
	Optional<User> findByMail(String mail);
	
}
