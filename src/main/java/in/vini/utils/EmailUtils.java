package in.vini.utils;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailUtils {

	@Autowired
	private JavaMailSender email;

	public boolean sendEmail(String to, String subject, String body) {
		boolean isSent = false;
		try {
			MimeMessage mimeMessage = email.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body, true);
			email.send(mimeMessage);
			isSent = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isSent;
	}
}
