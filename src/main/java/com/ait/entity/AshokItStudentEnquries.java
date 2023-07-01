package com.ait.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Entity
@Data
public class AshokItStudentEnquries {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer enqId;
	private String studentName;
	private long phno;
	private String classMode;
	private String courseName;
	private String enquiryStatus;

	@CreationTimestamp
	private LocalDate createdDate;

	@UpdateTimestamp
	private LocalDate updatedDate;

	//@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private AshokITUsersDtls user;

}
