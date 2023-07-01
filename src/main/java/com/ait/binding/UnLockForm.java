package com.ait.binding;

import lombok.Data;

@Data
public class UnLockForm {
	
	private String userEmail;
	
	private String tempPwd;
	
	private String newPwd;
	
	private String confirmPwd;
	

}
