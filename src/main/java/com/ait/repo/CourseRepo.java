package com.ait.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ait.entity.AshokItCourses;

public interface CourseRepo extends JpaRepository<AshokItCourses, Integer> {

}
