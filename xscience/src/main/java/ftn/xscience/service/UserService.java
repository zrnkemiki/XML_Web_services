package ftn.xscience.service;

import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.xscience.dto.UserCredentials;
import ftn.xscience.exception.UnmarshallingException;
import ftn.xscience.exception.UserNotFoundException;
import ftn.xscience.model.user.TUser;
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
			throw new UnmarshallingException("[custom-err] Unmarshalling user [" + credentials.getEmail() + "] failed! \n[original-cause]\n" + e.getMessage());
		}
		
		if (user == null || !user.getPassword().equals(credentials.getPassword())) {
			throw new UserNotFoundException("Login failed - user not found.");
		}
		return user;
	}
	
	public TUser getUserByEmail(String email) {
		TUser user = null;
		
		try {
			user = userRepository.getUserByEmail(email);
		} catch (JAXBException e) {
			throw new UnmarshallingException("[custom-err] Unmarshalling user [" + email + "] failed!\n[original-err] " + e.getMessage());
		} 
		
		if (user == null) {
			throw new UserNotFoundException("User " + email + " not found!");
		}
		
		return user;
	}
}
