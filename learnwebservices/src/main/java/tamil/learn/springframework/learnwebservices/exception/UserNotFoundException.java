/**
 * 
 */
package tamil.learn.springframework.learnwebservices.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Murugan_Nagarajan
 *
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1270608733587331637L;

	public UserNotFoundException(String message) {
		super(message);
	}

}
