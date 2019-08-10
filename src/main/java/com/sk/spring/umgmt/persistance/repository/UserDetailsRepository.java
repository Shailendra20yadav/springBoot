package com.sk.spring.umgmt.persistance.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.sk.spring.umgmt.persistance.model.User;
@Repository
public interface UserDetailsRepository extends PagingAndSortingRepository<User, Long> {
	
	public User findByUsername(String username);

}
