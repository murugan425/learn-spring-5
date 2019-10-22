/**
 * 
 */
package tamil.learn.springframework.learnwebservices.controller;



import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
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

	@Autowired
	private MessageSource messageSource;
	
	@RequestMapping(method = RequestMethod.GET, path = "ping")
	public String pingTest() {
		return "pong";
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "hello")
	public String sayHello(@RequestHeader(name=HttpHeaders.ACCEPT_LANGUAGE, required = false) Locale locale) {
		return messageSource.getMessage("greeting.message", null, locale) + " Murugan";
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "hellobean")
	public HelloBean sayHelloBean() {
		return new HelloBean("Hello Murugan - I'm a bean/pojo");
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "helloparam/{param}")
	public HelloBean sayHelloParam(@PathVariable String param) {
		return new HelloBean("Hello "+ param);
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "helloparam/{param}/{param2}")
	public HelloBean sayHelloParam(@PathVariable String param, @PathVariable String param2) {
		return new HelloBean(String.format("Hello %s , %s", param, param2));
	}
}
