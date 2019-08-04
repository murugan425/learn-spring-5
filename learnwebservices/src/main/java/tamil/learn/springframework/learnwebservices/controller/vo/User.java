/**
 * 
 */
package tamil.learn.springframework.learnwebservices.controller.vo;

import java.util.Date;

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
	private String name;
	private Date birthDate;
}
