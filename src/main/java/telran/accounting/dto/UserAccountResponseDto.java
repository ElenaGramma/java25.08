package telran.accounting.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import telran.accounting.entites.UserAccount;
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserAccountResponseDto {

	private String login;
	private Set<String> roles;
	private String firstName;
	private String lastName;
	public static UserAccountResponseDto build(UserAccount user) {
		return new UserAccountResponseDto(user.getLogin(), user.getRoles(), user.getFirstName(), user.getLastName());
	}
}
