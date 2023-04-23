package in.vini.binding;

import lombok.Data;

@Data
public class UnlockForm {

	private String email;
	private String tempPwd;
	private String pwd;
	private String cpwd;

}
