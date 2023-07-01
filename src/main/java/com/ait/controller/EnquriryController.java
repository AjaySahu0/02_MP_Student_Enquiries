package com.ait.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ait.binding.DashboardReponse;
import com.ait.binding.EnquiryForm;
import com.ait.binding.EnquirySearchCriteria;
import com.ait.entity.AshokItStudentEnquries;
import com.ait.service.EnquiryService;

@Controller
public class EnquriryController {

	@Autowired
	private EnquiryService enqService;


	@Autowired
	private HttpSession session;

	@GetMapping("/logout")
	public String logout() {

		session.invalidate();
		return "index";
	}

	@GetMapping("/dashboard")
	public String dashboardPage(Model model) {

		Integer userId = (Integer) session.getAttribute("userId");

		DashboardReponse dashboardData = enqService.getDashboardData(userId);

		model.addAttribute("dashboard", dashboardData);

		return "dashboard";
	}

	private void initForm(Model model) {

		// get course for drop down
		List<String> enqCourses = enqService.getcourseName();

		// get enq status for drop down
		List<String> enqStatus = enqService.getEnqStatus();

		// create binding class obj
		EnquiryForm formObj = new EnquiryForm();

		// set data in model obj
		model.addAttribute("enqCourses", enqCourses);
		model.addAttribute("enqStatus", enqStatus);
		model.addAttribute("formObj", formObj);
	}

	@GetMapping("/add-enquiry")
	public String addEnquiryPage(Model model) {

		initForm(model);

		return "add-enquiry";
	}

	@PostMapping("/add-enquiry")
	public String addEnquiry(@ModelAttribute("formObj") EnquiryForm formObj, Model model) {

		System.out.println(formObj);
		boolean status = enqService.saveEnquiry(formObj);

		if (status) {
			model.addAttribute("successMsg", "Enquiry added");
		} else {
			model.addAttribute("errorMsg", "problem occured");
		}

		return "add-enquiry";
	}

	@GetMapping("/view-enquiries")
	public String views(Model model) {

		initForm(model);
		List<AshokItStudentEnquries> enquries = enqService.getEnquiries();
		model.addAttribute("enquries", enquries);
		return "view-enquiries";

	}

	@GetMapping("/filter-enquiries")
	public String getFilteredEngs(@RequestParam String cname, @RequestParam String status, @RequestParam String mode,
			Model model) {
		EnquirySearchCriteria criteria = new EnquirySearchCriteria();
		criteria.setCourseName(cname);
		criteria.setClassMode(mode);
		criteria.setStatus(status);

		// System.out.println(criteria);

		Integer userId = (Integer) session.getAttribute("userId");

		List<AshokItStudentEnquries> filteredEnqs = enqService.getFilteredEnqs(criteria, userId);

		model.addAttribute("enquries", filteredEnqs);

		return "filter-enquiries-page";
	}

	@GetMapping("/edit")
	public String edit(@RequestParam("enqId") Integer enqId, Model model) {
		
		initForm(model);

		AshokItStudentEnquries editEnquiry = enqService.editEnquiry(enqId);
		model.addAttribute("formObj", editEnquiry);

		return "add-enquiry";
	}
}
