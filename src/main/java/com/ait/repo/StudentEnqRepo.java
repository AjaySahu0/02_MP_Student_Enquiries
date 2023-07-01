package com.ait.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ait.entity.AshokItStudentEnquries;

public interface StudentEnqRepo extends JpaRepository<AshokItStudentEnquries, Integer> {

}
