package com.sk.spring.umgmt.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sk.spring.umgmt.persistance.entity.RoleMaster;

@Repository
public interface RoleMasterRepository  extends JpaRepository<RoleMaster, Long>{

}
