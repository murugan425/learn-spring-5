package tamil.learn.springframework.learnwebservices.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tamil.learn.springframework.learnwebservices.controller.vo.User;
import tamil.learn.springframework.learnwebservices.dao.UserDaoService;

@RestController
public class UserController {

	@Autowired
	private UserDaoService userService;
	
	@GetMapping(path = "users")
	public List<User> findAllUsers() {
		return userService.findAllUsers();
	};
	
	@RequestMapping(method = RequestMethod.POST, path = "users")
	public User saveUser(@RequestBody User user) {
		return userService.save(user);
	};
	
	@RequestMapping(method = RequestMethod.GET, path = "users/{id}")
	public User findUserById(@PathVariable int id) {
		return userService.findUserById(id); 
	}
}
