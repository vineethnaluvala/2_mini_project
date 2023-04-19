package in.vini.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import in.vini.binding.SignUpForm;
import in.vini.services.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService service;

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	

	@PostMapping("/signup")
	public String hadleSignUp(@ModelAttribute("user") SignUpForm form, Model model) {
		boolean status = service.registerUser(form);
		if (status) {
			model.addAttribute("msg", "sign-up success check your mail");
		} else {
			model.addAttribute("errMsg", "mail already used,choose other one");
		}
		return "signUp";
	}

	@GetMapping("/signup")
	public String signUp(Model model) {
		model.addAttribute("user", new SignUpForm());
		return "signUp";
	}
	
	@GetMapping("/unlock")
	public String unlockPage() {
		return "unlock";
	}

	@GetMapping("/forgot")
	public String forgotPwdPage() {
		return "forgotPwd";
	}
}
