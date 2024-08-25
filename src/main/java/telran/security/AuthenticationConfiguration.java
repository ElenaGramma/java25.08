package telran.security;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import telran.accounting.entites.UserAccount;
import telran.accounting.repo.UserAccountRepository;

@Configuration
public class AuthenticationConfiguration implements UserDetailsService{

	@Autowired
	UserAccountRepository repo;
	@Value("${activationPeriod: 30}")
	int activationPeriod;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserAccount user=repo.findById(username).orElseThrow(()->new UsernameNotFoundException(username));
		String password=user.getHashCode();
		
		boolean passwordIsnotExpired=ChronoUnit.MINUTES.between(LocalDateTime.now(), user.getActivatioDate())>activationPeriod;
		
		String[] roles=user.getRoles().stream().map(r->"ROLE_"+r).toArray(String[]::new);
		
		return new User(username, password, AuthorityUtils.createAuthorityList(roles));
	}	
}

