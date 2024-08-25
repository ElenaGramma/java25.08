package telran.accounting.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import telran.accounting.dto.RolesResponseDto;
import telran.accounting.dto.UserAccountResponseDto;
import telran.accounting.dto.UserRegisterDto;
import telran.accounting.dto.UserUpdateDto;
import telran.accounting.entites.UserAccount;
import telran.accounting.exception.AccountActivationException;
import telran.accounting.exception.PasswordNotValidException;
import telran.accounting.exception.RoleExistsException;
import telran.accounting.exception.RoleNotExistsException;
import telran.accounting.exception.UserExistsException;
import telran.accounting.exception.UserNotFoundException;
import telran.accounting.repo.UserAccountRepository;

@Service
public class AccountingMongo implements IAccountingManagement,CommandLineRunner{

	@Autowired
	UserAccountRepository repo;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Value("${password_length:6}")
	private int passwortLength;
	
	@Value("${n_last_hash:3}")
	private int n_last_hash;
	
	
	@Override
	public UserAccountResponseDto registration(UserRegisterDto account) {
		if(repo.existsById(account.getLogin()))
		throw new UserExistsException(account.getLogin());
		if(!isPasswordValid(account.getPassword()))
			throw new ResponseStatusException(HttpStatus.CONFLICT,
					"password "+account.getPassword()+"is not valid");
		UserAccount acc = new UserAccount(account.getLogin(), 
				getHash(account.getPassword()), account.getFirstName(),account.getLastName());
		repo.save(acc);
		return  UserAccountResponseDto.build(acc);

	}

	private String getHash(String password) {
		
		return encoder.encode(password);
	}

	private boolean isPasswordValid(String password) {
		
		return password.length()<=passwortLength;
	}
	private UserAccount getUserAccount(String login) {
		return repo.findById(login).orElseThrow(() -> new UserNotFoundException(login));
	}
	
	@Override
	public UserAccountResponseDto removeUser(String login) {
		UserAccount acc=getUserAccount(login);	
		repo.deleteById(login);
		return  UserAccountResponseDto.build(acc);
	}

	@Override
	public UserAccountResponseDto getUser(String login) { 
		UserAccount acc=getUserAccount(login);
		return  UserAccountResponseDto.build(acc);
	}

	@Override
	public UserAccountResponseDto editUser(String login, UserUpdateDto acc) {
		UserAccount usA=repo.findById(login).orElse(null);	
			if(usA==null)
				throw new UserExistsException(login);
			usA.setFirstName(acc.getFirstName());
			usA.setLastName(acc.getLastName());
			repo.save(usA);
			return UserAccountResponseDto.build(usA);
	}

	@Override
	public boolean updatePassword(String login, String newPassword) {
		if(newPassword==null|| !isPasswordValid(newPassword))
			throw  new PasswordNotValidException(newPassword);
		
		UserAccount user=getUserAccount(login);
		
		if(encoder.matches(newPassword, user.getHashCode()))
			throw  new PasswordNotValidException(newPassword);
		
		LinkedList<String> lastHash=user.getLastHashCodes();
		if(isPasswordFromLast(newPassword,lastHash))
			throw  new PasswordNotValidException(newPassword);
		
		if(lastHash.size()==n_last_hash)
			lastHash.removeFirst();
		lastHash.add(user.getHashCode());	
		
	user.setHashCode(encoder.encode(newPassword));
	user.setActivatioDate(LocalDateTime.now());
	repo.save(user);
	return true;		
	}
	

	private boolean isPasswordFromLast(String newPassword, LinkedList<String> lastHash) {
		
		return lastHash.stream().anyMatch(p->encoder.matches(newPassword, p));
	}

	@Override
	public boolean revokeAccount(String login) {
		UserAccount acc=getUserAccount(login);
		if( acc.isRevoked()) 
			throw new UserExistsException(login);
		acc.setRevoked(true);
		repo.save(acc);
		return true;
	}

	@Override
	public boolean activateAccount(String login) {
		UserAccount acc=getUserAccount(login);
		if(acc.isRevoked()) 
			throw new AccountActivationException(login);
		acc.setRevoked(false);
		acc.setActivatioDate(LocalDateTime.now());
		repo.save(acc);
		return true;
	}

	@Override
	public String getPasswordHash(String login) {
		UserAccount acc=getUserAccount(login);
		if(acc.isRevoked()) 
			return null;
		return acc.getHashCode();
	}

	@Override
	public LocalDateTime getActivationDate(String login) {
		UserAccount acc=getUserAccount(login);
		if(acc.isRevoked()) 
			return null;
		return acc.getActivatioDate();
	}

	@Override
	public RolesResponseDto getRoles(String login) {
		UserAccount acc=getUserAccount(login);			
		if( acc.isRevoked()) {
			return null;
		}	
				return new RolesResponseDto(login, acc.getRoles());

	}

	@Override
	public RolesResponseDto addRoles(String login, String role) {
		role=role.toUpperCase();
		UserAccount acc=getUserAccount(login);	
		if( acc.isRevoked())
			throw new RoleExistsException(login);
		HashSet<String> roles = acc.getRoles();
		if (roles.contains(role))
			throw new RoleExistsException(role);
		roles.add(role);
		repo.save(acc);
		return new RolesResponseDto(login, acc.getRoles());
	}

	@Override
	public RolesResponseDto removeRoles(String login, String role) {
		UserAccount acc=getUserAccount(login);	
		if( acc.isRevoked())
			throw new RoleNotExistsException(login);
		HashSet<String> roles = acc.getRoles();
		if (roles.contains(role))
			throw new RoleNotExistsException(role);
		roles.remove(role);
		repo.save(acc);
		return new RolesResponseDto(login, acc.getRoles());
	}

	@Override
	public void run(String... args) throws Exception {
		if(!repo.existsById("admin")) {
			UserAccount admin=new UserAccount("admin", encoder.encode("administrator"), "", "");
		admin.setRoles(new HashSet<String>(List.of("ADMIN")));
		repo.save(admin);
		}
		
	}

}
