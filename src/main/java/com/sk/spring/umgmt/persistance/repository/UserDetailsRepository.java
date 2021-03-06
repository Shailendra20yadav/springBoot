package com.sk.spring.umgmt.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sk.spring.umgmt.persistance.entity.User;

@Repository
public interface UserDetailsRepository extends JpaRepository<User, Long> {
	
	public User findByUsername(String username);

}
