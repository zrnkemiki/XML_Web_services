package ftn.xscience.service;

import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.xscience.dto.UserCredentials;
import ftn.xscience.model.TUser;
import ftn.xscience.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	public TUser login(UserCredentials credentials) {
		TUser user = null;
		try {
			user = userRepository.getUserByEmail(credentials.getEmail());
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
		if (user == null || user.getPassword().contentEquals(credentials.getPassword())) {
			//throw new CustomException("Login failed!");
		}
		return user;
	}
}
