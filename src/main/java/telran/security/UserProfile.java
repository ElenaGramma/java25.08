package telran.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class UserProfile extends User{
	

public UserProfile(String username, String password, Collection<? extends GrantedAuthority> authorities,boolean passwordIsnotExpired) {
		super(username, password, authorities);
		this.passwordIsnotExpired=passwordIsnotExpired;
	}

public boolean passwordIsnotExpired;

	

	
}
