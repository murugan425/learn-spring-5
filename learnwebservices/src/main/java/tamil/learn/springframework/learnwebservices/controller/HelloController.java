/**
 * 
 */
package tamil.learn.springframework.learnwebservices.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tamil.learn.springframework.learnwebservices.controller.vo.HelloBean;

/**
 * @author Murugan_Nagarajan
 *
 */
@RestController
public class HelloController {

	@RequestMapping(method = RequestMethod.GET, path = "hello")
	public String sayHello() {
		return "Hello Murugan";
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "hellobean")
	public HelloBean sayHelloBean() {
		return new HelloBean("Hello Murugan - I'm a bean/pojo");
	}
}
