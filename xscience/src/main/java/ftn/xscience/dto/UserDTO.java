package ftn.xscience.dto;

import ftn.xscience.model.TUser;
import ftn.xscience.model.TUser.Expertises;
import ftn.xscience.model.TUser.Publications;
import ftn.xscience.model.TUser.PublicationsForReview;

public class UserDTO {
	
	private String role;
	private String email;
	private String password;
	private String firstName;
	private String middleName;
	private String lastName;
	private String jwtToken;
	//private TUser.Expertises expertises;
	//private TUser.Publications publications;
	//private TUser.PublicationsForReview publicationsForReview;
	
	

	public UserDTO(TUser user) {
		this.role = user.getRole();
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.firstName = user.getFirstName();
		this.middleName = user.getMiddleName();
		this.lastName = user.getLastName();
		//this.expertises = user.getExpertises();
		//this.publications = user.getPublications();
		//this.publicationsForReview = user.getPublicationsForReview();
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getJwtToken() {
		return jwtToken;
	}
	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}
	/*
	public TUser.Expertises getExpertises() {
		return expertises;
	}
	public void setExpertises(TUser.Expertises expertises) {
		this.expertises = expertises;
	}
	public TUser.Publications getPublications() {
		return publications;
	}
	public void setPublications(TUser.Publications publications) {
		this.publications = publications;
	}
	public TUser.PublicationsForReview getPublicationsForReview() {
		return publicationsForReview;
	}
	public void setPublicationsForReview(TUser.PublicationsForReview publicationsForReview) {
		this.publicationsForReview = publicationsForReview;
	}*/

	
	public UserDTO(String role, String email, String password, String firstName, String middleName, String lastName,
			String jwtToken, Expertises expertises, Publications publications,
			PublicationsForReview publicationsForReview) {
		super();
		this.role = role;
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.jwtToken = jwtToken;
		//this.expertises = expertises;
		//this.publications = publications;
		//this.publicationsForReview = publicationsForReview;
	}

}
