package com.ait.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ait.entity.AshokItEnquiryStatus;

public interface EnqStatusRepo extends JpaRepository<AshokItEnquiryStatus, Integer>{

}
