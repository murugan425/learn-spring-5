/**
 * 
 */
package tamil.learn.springframework.learnwebservices.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author Murugan_Nagarajan
 *
 */
@ControllerAdvice
@RestController
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ApplicationException> handleApplicationExceptions(Exception ex, WebRequest request) {
		ApplicationException applicationException = new ApplicationException(LocalDateTime.now().toString(), ex.getMessage(), request.getDescription(true));
		return new ResponseEntity<ApplicationException>(applicationException, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public final ResponseEntity<ApplicationException> handleResourceNotFoundExceptions(UserNotFoundException ex, WebRequest request) {
		ApplicationException applicationException = new ApplicationException(LocalDateTime.now().toString(), ex.getMessage(), request.getDescription(true));
		return new ResponseEntity<ApplicationException>(applicationException, HttpStatus.NOT_FOUND);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		ApplicationException applicationException = new ApplicationException(LocalDateTime.now().toString(), "Request Validation Failed", ex.getBindingResult().getFieldErrors().toString());
		return new ResponseEntity<Object>(applicationException, HttpStatus.BAD_REQUEST);
	}
}

