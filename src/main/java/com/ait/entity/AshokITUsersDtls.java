package com.ait.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class AshokITUsersDtls {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;
	private String userName;
	private String userEmail;
	private Long userPhno;
	private String userPassword;
	private String accountStatus;
	
	//@OneToMany(cascade = CascadeType.ALL)
	//@JoinColumn(name = "user_id")
	
	@OneToMany(mappedBy = "user" ,cascade = CascadeType.ALL , fetch = FetchType.EAGER)
	private List<AshokItStudentEnquries> enquiries;
	

}
