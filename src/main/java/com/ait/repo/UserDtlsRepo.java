package com.ait.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ait.entity.AshokITUsersDtls;

public interface UserDtlsRepo extends JpaRepository<AshokITUsersDtls, Integer> {
	
	public AshokITUsersDtls findByUserEmail(String userEmail);

	public AshokITUsersDtls findByUserEmailAndUserPassword(String userEmail, String userPassword);
}
