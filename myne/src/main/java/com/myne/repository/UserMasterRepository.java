package com.myne.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myne.entity.UserMaster;

@Repository
public interface UserMasterRepository extends JpaRepository<UserMaster, String>{

}
