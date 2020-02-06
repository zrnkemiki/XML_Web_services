package ftn.xscience.service;


import static ftn.xscience.utils.dom.StringPathHandler.EMAIL_TEMPLATE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import ftn.xscience.model.publication.Publication;
import ftn.xscience.utils.dom.StringPathHandler;



@Service
public class EMailService {
	private JavaMailSender javaMailSender;
	
	@Autowired
	PublicationService publicationService;
	
	
	@Autowired
	public EMailService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	
	public void sendMail(String email, String subject, String text) throws MailException {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(email.toLowerCase());
		mail.setFrom("bgkgsiit@gmail.com");
		mail.setSubject(subject);
		mail.setText(text);
		javaMailSender.send(mail);
		
	}
	
	public String generateText(String sender, String receiver, String publicationTitle, String notificationType, String content) {
		return String.format(EMAIL_TEMPLATE, sender, receiver, publicationTitle, notificationType, content);
	}
	
	public Map<String, String> getReceiversByPublicationId(String publicationId) {
		// id = email
		// value = isparsirano ime
		Map<String, String> users = new HashMap<String, String>();
		ArrayList<String> titles = new ArrayList<String>();
		titles.add(publicationId);
		List<Publication.Author> authors = publicationService.getDocumentsByID(titles).get(0).getAuthor();
		for (Publication.Author author : authors) {
			users.put(author.getEmail(), StringPathHandler.generateSenderRecieverForEmail(author.getName().getFirstName(),
																				author.getName().getLastName(),
																				author.getEmail(),
																				author.getPhoneNumber()));
			
		}
		
		return users;
	}

}
