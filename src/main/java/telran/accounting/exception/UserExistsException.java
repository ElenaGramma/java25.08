package telran.accounting.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)

public class UserExistsException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public UserExistsException(String login) {
		super("user"+login+"exists");
	}
}
