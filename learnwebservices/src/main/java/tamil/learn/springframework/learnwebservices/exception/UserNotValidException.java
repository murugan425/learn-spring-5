/**
 * 
 */
package tamil.learn.springframework.learnwebservices.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Murugan_Nagarajan
 *
 */
//@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class UserNotValidException extends BindException {

	private static final long serialVersionUID = 5351044208148103691L;

	public UserNotValidException(BindingResult bindingResult) {
		super(bindingResult);
	}

}
