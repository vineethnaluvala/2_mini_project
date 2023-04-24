package in.vini.services;

import org.springframework.stereotype.Service;

import in.vini.binding.LoginForm;
import in.vini.binding.SignUpForm;
import in.vini.binding.UnlockForm;

@Service
public interface UserService {

	public boolean registerUser(SignUpForm signUpForm);

	public String loginUser(LoginForm loginForm);

	public boolean unlockAccount(UnlockForm unlockForm);

	public boolean forgotPassword(String email);

}
