package in.vini.services;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.vini.binding.LoginForm;
import in.vini.binding.SignUpForm;
import in.vini.binding.UnlockForm;
import in.vini.entity.UserDetailsEntity;
import in.vini.repository.UserDetailsRepository;
import in.vini.utils.EmailUtils;
import in.vini.utils.PwdUtils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDetailsRepository repo;

	@Autowired
	private HttpSession session;
	@Autowired
	private EmailUtils utils;

	public boolean registerUser(SignUpForm signUpForm) {

		UserDetailsEntity user = repo.findByEmail(signUpForm.getEmail());

		if (user != null) {
			return false;
		}

		UserDetailsEntity entity = new UserDetailsEntity();

		BeanUtils.copyProperties(signUpForm, entity);

		String tempPwd = PwdUtils.generatePwd();
		entity.setPassword(tempPwd);

		entity.setAccStatus("LOCKED");

		repo.save(entity);

		String to = signUpForm.getEmail();
		String subject = "unlock your account | Ashok IT";
		StringBuffer body = new StringBuffer("");
		body.append("<h1>use below temparory password to unlock your account</h1>");
		body.append("temparory password : " + tempPwd);
		body.append("<br/>");
		body.append("<a href =\"http://localhost:8088/unlock?email=" + to + "\">click here to unlock your account</a>");

		utils.sendEmail(to, subject, body.toString());
		return true;
	}

	public String loginUser(LoginForm loginForm) {

		UserDetailsEntity entity = repo.findByEmailAndPassword(loginForm.getUserName(), loginForm.getPassword());

		if (entity == null) {
			return "invalid credentials";
		}
		if (entity.getAccStatus().equals("locked")) {
			return "your account is locked";
		}
		
		session.setAttribute("userId", entity.getUserId());

		return "login succesfull";
	}

	public boolean unlockAccount(UnlockForm unlockForm) {

		UserDetailsEntity entity = repo.findByEmail(unlockForm.getEmail());

		if (entity.getPassword().equals(unlockForm.getTempPwd())) {

			entity.setAccStatus("UNLOCKED");

			entity.setPassword(unlockForm.getCpwd());

			repo.save(entity);

			return true;
		} else {

			return false;
		}

	}

	public boolean forgotPassword(String email) {
		
		UserDetailsEntity entity = repo.findByEmail(email);
		
		if(entity==null) {
			return false;
		}
		
		String subject = "recover password";
		String body = "your password is : " + entity.getPassword();

		utils.sendEmail(email, subject, body);
		
		return true;

	}

}
















