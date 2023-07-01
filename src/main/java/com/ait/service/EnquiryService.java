package com.ait.service;

import java.util.List;

import com.ait.binding.DashboardReponse;
import com.ait.binding.EnquiryForm;
import com.ait.binding.EnquirySearchCriteria;
import com.ait.entity.AshokItStudentEnquries;

public interface EnquiryService {

	public DashboardReponse getDashboardData(Integer userId);

	public List<String> getcourseName();

	public List<String> getEnqStatus();

	public boolean saveEnquiry(EnquiryForm form);

	public List<EnquiryForm> viewEnquiries(EnquirySearchCriteria form, Integer id);

	public List<AshokItStudentEnquries> getEnquiries();
	
	public List<AshokItStudentEnquries> getFilteredEnqs(EnquirySearchCriteria criteria , Integer userId);
	
	public AshokItStudentEnquries editEnquiry(Integer enqId);

}
