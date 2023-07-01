package com.ait.runner;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.ait.entity.AshokItCourses;
import com.ait.entity.AshokItEnquiryStatus;
import com.ait.repo.CourseRepo;
import com.ait.repo.EnqStatusRepo;

@Component
public class DataLoader implements ApplicationRunner {

	@Autowired
	private CourseRepo courseRep;

	@Autowired
	private EnqStatusRepo statusRepo;

	@Override
	public void run(ApplicationArguments args) throws Exception {

		// adding the courses
		courseRep.deleteAll();

		AshokItCourses c1 = new AshokItCourses();
		c1.setCourseName("Java");

		AshokItCourses c2 = new AshokItCourses();
		c2.setCourseName("DevOps");

		AshokItCourses c3 = new AshokItCourses();
		c3.setCourseName("Python");

		AshokItCourses c4 = new AshokItCourses();
		c4.setCourseName("AWS");

		courseRep.saveAll(Arrays.asList(c1, c2, c3, c4));

		// adding the status

		statusRepo.deleteAll();

		AshokItEnquiryStatus s1 = new AshokItEnquiryStatus();
		s1.setCourseStatus("New");

		AshokItEnquiryStatus s2 = new AshokItEnquiryStatus();
		s2.setCourseStatus("Enrolled");

		AshokItEnquiryStatus s3 = new AshokItEnquiryStatus();
		s3.setCourseStatus("Lost");

		statusRepo.saveAll(Arrays.asList(s1, s2, s3));
	}

}
