package com.ait.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.binding.DashboardReponse;
import com.ait.binding.EnquiryForm;
import com.ait.binding.EnquirySearchCriteria;
import com.ait.entity.AshokITUsersDtls;
import com.ait.entity.AshokItCourses;
import com.ait.entity.AshokItEnquiryStatus;
import com.ait.entity.AshokItStudentEnquries;
import com.ait.repo.CourseRepo;
import com.ait.repo.EnqStatusRepo;
import com.ait.repo.StudentEnqRepo;
import com.ait.repo.UserDtlsRepo;

@Service
public class EnquiryServiceImp implements EnquiryService {

	@Autowired
	private UserDtlsRepo userRepo;

	@Autowired
	private CourseRepo courseRepo;

	@Autowired
	private EnqStatusRepo enqStatusRepo;

	@Autowired
	private StudentEnqRepo enqRepo;

	@Autowired
	private HttpSession session;

	@Override
	public DashboardReponse getDashboardData(Integer userId) {

		DashboardReponse response = new DashboardReponse();

		Optional<AshokITUsersDtls> findById = userRepo.findById(userId);

		if (findById.isPresent()) {
			AshokITUsersDtls userEntity = findById.get();

			List<AshokItStudentEnquries> enquiries = userEntity.getEnquiries();

			Integer totEnquiries = enquiries.size();

			Integer enrolled = enquiries.stream().filter(e -> e.getEnquiryStatus().equals("Enrolled"))
					.collect(Collectors.toList()).size();

			Integer lost = enquiries.stream().filter(e -> e.getEnquiryStatus().equals("Lost"))
					.collect(Collectors.toList()).size();

			response.setTotEnquiries(totEnquiries);
			response.setEntrolled(enrolled);
			response.setLost(lost);
		}

		return response;
	}

	@Override
	public boolean saveEnquiry(EnquiryForm form) {

		AshokItStudentEnquries enqEntity = new AshokItStudentEnquries();
		BeanUtils.copyProperties(form, enqEntity);

		Integer userId = (Integer) session.getAttribute("userId");
		/*
		 * AshokITUsersDtls userEntity = userRepo.findById(userId).get();
		 * enqEntity.setUser(userEntity); enqRepo.save(enqEntity); return true;
		 */

		Optional<AshokITUsersDtls> findById = userRepo.findById(userId);
		if (findById.isPresent()) {
			AshokITUsersDtls userEntity = findById.get();
			enqEntity.setUser(userEntity);
		}
		enqRepo.save(enqEntity);
		return true;
	}

	@Override
	public List<String> getcourseName() {

		List<AshokItCourses> findAll = courseRepo.findAll();

		List<String> names = new ArrayList<>();

		for (AshokItCourses entity : findAll) {
			names.add(entity.getCourseName());
		}

		return names;
	}

	@Override
	public List<String> getEnqStatus() {

		List<AshokItEnquiryStatus> findAll = enqStatusRepo.findAll();
		ArrayList<String> statusList = new ArrayList<>();

		for (AshokItEnquiryStatus entity : findAll) {
			statusList.add(entity.getCourseStatus());
		}
		return statusList;
	}

	@Override
	public List<EnquiryForm> viewEnquiries(EnquirySearchCriteria form, Integer id) {

		return null;
	}

	@Override
	public List<AshokItStudentEnquries> getEnquiries() {

		Integer userId = (Integer) session.getAttribute("userId");
		Optional<AshokITUsersDtls> findById = userRepo.findById(userId);
		if (findById.isPresent()) {
			AshokITUsersDtls usersDtlsEntity = findById.get();
			List<AshokItStudentEnquries> enquiries = usersDtlsEntity.getEnquiries();
			return enquiries;
		}
		return null;
	}

	@Override
	public List<AshokItStudentEnquries> getFilteredEnqs(EnquirySearchCriteria criteria, Integer userId) {

		Optional<AshokITUsersDtls> findById = userRepo.findById(userId);
		if (findById.isPresent()) {
			AshokITUsersDtls usersDtlsEntity = findById.get();
			List<AshokItStudentEnquries> enquiries = usersDtlsEntity.getEnquiries();

			// filter Logic
			if (null != criteria.getCourseName() & !"".equals(criteria.getCourseName())) {

				enquiries = enquiries.stream().filter(e -> e.getCourseName().equals(criteria.getCourseName()))
						.collect(Collectors.toList());
			}
			if (null != criteria.getStatus() & !"".equals(criteria.getStatus())) {
				enquiries = enquiries.stream().filter(e -> e.getEnquiryStatus().equals(criteria.getStatus()))
						.collect(Collectors.toList());
			}
			if (null != criteria.getClassMode() & !"".equals(criteria.getClassMode())) {
				enquiries = enquiries.stream().filter(e -> e.getClassMode().equals(criteria.getClassMode()))
						.collect(Collectors.toList());

			}
			return enquiries;
		}

		return null;
	}

	@Override
	public AshokItStudentEnquries editEnquiry(Integer enqId) {

		Optional<AshokItStudentEnquries> findById = enqRepo.findById(enqId);

		if (findById.isPresent()) {
			AshokItStudentEnquries studentEnquries = findById.get();
			return studentEnquries;
		}
		return null;
	}

}
