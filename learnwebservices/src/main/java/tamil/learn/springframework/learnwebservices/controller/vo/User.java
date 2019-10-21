/**
 * 
 */
package tamil.learn.springframework.learnwebservices.controller.vo;

import java.util.Date;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

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
public class User {
	private Integer id;
	
	@Size(min=3, message = "User name should have atleast 3 characters")
	private String name;
	
	@Past(message = "User birth date should be a past date")
	private Date birthDate;
}
