package ftn.xscience.service;

import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.xscience.dto.UserCredentials;
import ftn.xscience.exception.UnmarshallingUserException;
import ftn.xscience.exception.UserNotFoundException;
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
			throw new UnmarshallingUserException("[custom-err] Unmarshalling user [" + credentials.getEmail() + "] failed! \n[original-cause]\n" + e.getMessage());
		}
		
		if (user == null || !user.getPassword().equals(credentials.getPassword())) {
			throw new UserNotFoundException("Login failed - user not found.");
		}
		return user;
	}
}
