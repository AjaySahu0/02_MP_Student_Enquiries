package com.ait.service;

import com.ait.binding.LogInForm;
import com.ait.binding.SignUpForm;
import com.ait.binding.UnLockForm;

public interface UserService {
	
	public String logIn(LogInForm form);
	
	public boolean signUp(SignUpForm form);
	
	public boolean unLock(UnLockForm form); 
	
	public boolean forgotPwd(String email);
	
	

}
