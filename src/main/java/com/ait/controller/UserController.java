package com.ait.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ait.binding.LogInForm;
import com.ait.binding.SignUpForm;
import com.ait.binding.UnLockForm;
import com.ait.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/signup")
	public String signup(Model model) {

		model.addAttribute("user", new SignUpForm());
		return "signup";
	}

	@PostMapping("/signup")
	public String handleSignUp(@ModelAttribute("user") SignUpForm form, Model model) {

		boolean status = userService.signUp(form);

		if (status) {
			model.addAttribute("SucessMsg", "Check your Email");
		} else {
			model.addAttribute("ErrorMsg", "Problem occured");
		}
		return "signup";
	}

	@GetMapping("/unlock")
	public String unlock(@RequestParam String email, Model model) {

		UnLockForm unlockFormObj = new UnLockForm();
		unlockFormObj.setUserEmail(email);
		model.addAttribute("unlock", unlockFormObj);
		return "unlock";

	}

	@PostMapping("/unlock")
	public String unlock(@ModelAttribute("unlock") UnLockForm form, Model model) {
		System.out.println(form);

		if (form.getNewPwd().equals(form.getConfirmPwd())) {
			boolean status = userService.unLock(form);

			if (status) {
				model.addAttribute("successMsg", "Your accout unlocked successfully");
			} else {
				model.addAttribute("errorMsg", "Given temporary password is wrong check your Email");
			}
		} else {
			model.addAttribute("errorMsg", "New password and confim password is not same");
		}
		return "unlock";

	}

	@GetMapping("/login")
	public String loginPage(Model model) {

		model.addAttribute("login", new LogInForm());
		return "login";
	}

	@PostMapping("/login")
	public String login(@ModelAttribute("login") LogInForm form, Model model) {

		String status = userService.logIn(form);
		if (status.contains("success")) {

			// redirect req to dashboard method
			return "redirect:/dashboard";
		}
		model.addAttribute("errorMsg", status);

		return "login";
	}

	@GetMapping("/forgotPwd")
	public String forgotPwd() {
		return "forgotPwd";

	}

	@PostMapping("/forgotPwd")
	public String forgotPwd(@RequestParam("userEmail") String userEmail, Model model) {
		
		System.out.println(userEmail);
		
		boolean status = userService.forgotPwd(userEmail);
		if(status) {
			model.addAttribute("successMsg", "check your email");
		}else {
			model.addAttribute("errorMsg", "Invalid email");
		}
		return "forgotPwd";

	}

}
