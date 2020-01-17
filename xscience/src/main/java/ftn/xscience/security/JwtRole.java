package ftn.xscience.security;

import org.springframework.security.core.GrantedAuthority;

public enum JwtRole implements GrantedAuthority {
	ROLE_AUTHOR, ROLE_REVIEWER, ROLE_EDITOR, ROLE_PUBLIC_USER;

	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return name();
	}
	
}
