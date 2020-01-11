package ftn.xscience.security;

import org.springframework.security.core.GrantedAuthority;

public enum JwtRole implements GrantedAuthority {
	AUTHOR, REVIEWER, EDITOR, PUBLIC_USER;

	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return name();
	}
	
}
