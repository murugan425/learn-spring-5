/**
 * 
 */
package tamil.learn.springframework.learnwebservices.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Murugan_Nagarajan
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class ApplicationException {
	private String timestamp;
	private String message;
	private String details;
}
