package com.ait.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.binding.LogInForm;
import com.ait.binding.SignUpForm;
import com.ait.binding.UnLockForm;
import com.ait.entity.AshokITUsersDtls;
import com.ait.repo.UserDtlsRepo;
import com.ait.utility.EmailUtils;
import com.ait.utility.pwdUtils;

@Service
public class UserServiceImp implements UserService {

	@Autowired
	private UserDtlsRepo repo;

	@Autowired
	private HttpSession session;

	@Autowired
	private EmailUtils emailUtils;

	@Override
	public boolean signUp(SignUpForm form) {

		// copy signup form data to AshokitUserDtls
		AshokITUsersDtls entity = new AshokITUsersDtls();
		BeanUtils.copyProperties(form, entity);

		// generate pwd
		String randomPwd = pwdUtils.generateRandomPwd();
		entity.setUserPassword(randomPwd);

		// set account status as lock
		entity.setAccountStatus("LOCKED");

		// insert record
		repo.save(entity);

		// send email to unlock account

		String to = form.getUserEmail();
		String subject = "Unlock your account | Ashok IT";

		StringBuffer body = new StringBuffer();
		body.append("<h1> use below temporary password to unlock the Account</h1>");
		body.append("Temporary Password =" + randomPwd);
		body.append("<br>");
		body.append("<a href=\"http://localhost:8081/unlock?email=" + to + "\">click here to unlock your account</a>");

		emailUtils.sendEmail(to, body.toString(), subject);

		return true;
	}

	@Override
	public boolean unLock(UnLockForm form) {

		AshokITUsersDtls entity = repo.findByUserEmail(form.getUserEmail());
		if (entity.getUserPassword().equals(form.getTempPwd())) {
			entity.setUserPassword(form.getNewPwd());
			entity.setAccountStatus("UNLOCKED");
			repo.save(entity);
			return true;
		}

		return false;
	}

	@Override
	public String logIn(LogInForm form) {

		AshokITUsersDtls entity = repo.findByUserEmailAndUserPassword(form.userEmail, form.userPassword);

		if (entity == null) {
			return "Invalid credentials";
		}
		if (entity.getAccountStatus().equals("LOCKED")) {
			return "Your Account id Locked";

		}
		// create session and user data in session
		session.setAttribute("userId", entity.getUserId());
		return "success";
	}

	@Override
	public boolean forgotPwd(String email) {

		AshokITUsersDtls entity = repo.findByUserEmail(email);
		if (entity == null) {

			return false;

		}

		String subject = "Recover Password";
		String body = "your password ::" + entity.getUserPassword();
		emailUtils.sendEmail(email, body, subject);

		return true;
	}

}
