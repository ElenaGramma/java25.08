package telran.accounting.controllers;

import java.security.Principal;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import telran.accounting.dto.RolesResponseDto;
import telran.accounting.dto.UserAccountResponseDto;
import telran.accounting.dto.UserRegisterDto;
import telran.accounting.dto.UserUpdateDto;
import telran.accounting.service.IAccountingManagement;

@RequestMapping("/account")
@RestController
@EnableMethodSecurity(prePostEnabled = true)
public class AccountingControllers {
	
	@Autowired
	IAccountingManagement service;

	
	
	@PostMapping({"/register","/register/"})
	public UserAccountResponseDto registration(@RequestBody UserRegisterDto account) {
		
		return service.registration(account);
	}


	@DeleteMapping("/user/{login}")
	@PreAuthorize("hasRole('USER')")
	public UserAccountResponseDto removeUser(@PathVariable String login) {
		
		return service.removeUser(login);
	}

	@PostMapping("/{login}")
	public UserAccountResponseDto login(Principal principal) {
		
		return service.getUser(principal.getName());
	}

	@PostMapping("/user/{login}")
	public UserAccountResponseDto editUser(@PathVariable String login,@RequestBody UserUpdateDto acc) {
		
		return service.editUser(login, acc);
	}

	@PutMapping("/password")
	public boolean updatePassword(Principal principal,@RequestHeader("X-New-Password") String newPassword) {
		
		return service.updatePassword(principal.getName(), newPassword);
	}

	@PutMapping("/revoke/{login}")
	public boolean revokeAccount(@PathVariable String login) {
		
		return service.revokeAccount(login);
	}


	@PutMapping("/activate/{login}")
	public boolean activateAccount(@PathVariable String login) {
		
		return service.activateAccount(login);
	}

	@GetMapping("/password/{login}")
	public String getPasswordHash(@PathVariable String login) {
		
		return service.getPasswordHash(login);
	}

	@GetMapping("activation_date/{login}")
	public LocalDateTime getActivationDate(@PathVariable String login) {
		// TODO Auto-generated method stub
		return null;
	}

	@GetMapping("/roles/{login}")
	public RolesResponseDto getRoles(@PathVariable String login) {
		
		return service.getRoles(login);
	}

	@PutMapping("/user/{login}/role/{role}")
	public RolesResponseDto addRoles(@PathVariable String login,@PathVariable String role) {
		
		return service.addRoles(login, role);
	}

	@DeleteMapping("/user/{login}/role/{role}")
	public RolesResponseDto removeRoles(@PathVariable String login,@PathVariable String role) {
		
		return service.removeRoles(login, role);
	}
}
