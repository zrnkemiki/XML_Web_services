package ftn.xscience.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;



@Service
public class EMailService {
	private JavaMailSender javaMailSender;
	
	@Autowired
	public EMailService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	
	public void sendMail(String email, String subject, String text) throws MailException {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(email);
		mail.setFrom("bgkgsiit@gmail.com");
		mail.setSubject(subject);
		mail.setText(text);
		javaMailSender.send(mail);
		
	}

}
