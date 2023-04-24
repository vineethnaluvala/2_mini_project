package in.vini.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.vini.binding.LoginForm;
import in.vini.binding.SignUpForm;
import in.vini.binding.UnlockForm;
import in.vini.services.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService service;

	@GetMapping("/login")
	public String login(Model model) {

		model.addAttribute("loginForm", new LoginForm());

		return "login";
	}

	@PostMapping("/login")
	public String loginUser(@ModelAttribute("loginForm") LoginForm loginForm, Model model) {

		String loginUser = service.loginUser(loginForm);

		if (loginUser.contains("login succesfull")) {
			return "redirect:/dashboard";
		}
		model.addAttribute("errmsg", loginUser);
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
	public String unlockPage(@RequestParam String email, Model model) {

		UnlockForm form = new UnlockForm();
		form.setEmail(email);
		model.addAttribute("unlock", form);

		return "unlock";
	}

	@PostMapping("/unlock")
	public String unlockAccount(@ModelAttribute("unlock") UnlockForm unlockForm, Model model) {

		// System.out.println(unlockForm);

		if (unlockForm.getPwd().equals(unlockForm.getCpwd())) {

			boolean unlockAccount = service.unlockAccount(unlockForm);

			if (unlockAccount) {
				model.addAttribute("msg", "unlock success,please login");
			} else {
				model.addAttribute("errmsg", "given temp password is incorrect");

			}

		} else {
			model.addAttribute("errmsg", "both password and confirm password should be same...");

		}

		return "unlock";
	}

	@GetMapping("/forgot")
	public String forgotPwdPage() {

		return "forgotPwd";
	}

	@PostMapping("/forgot")
	public String forgotPage(@RequestParam("email") String email, Model model) {

		boolean status = service.forgotPassword(email);
		
		if(status) {
			model.addAttribute("msg","password sent to your email");
		}else {
			model.addAttribute("errMsg","invalid email id");
		}
		return "forgotPwd";

	}
}













