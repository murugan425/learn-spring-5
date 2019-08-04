package tamil.learn.springframework.learnwebservices.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import tamil.learn.springframework.learnwebservices.controller.vo.User;
import tamil.learn.springframework.learnwebservices.dao.UserDaoService;
import tamil.learn.springframework.learnwebservices.exception.UserNotFoundException;

@RestController
public class UserController {

	@Autowired
	private UserDaoService userService;
	
	@GetMapping(path = "users")
	public List<User> findAllUsers() {
		return userService.findAllUsers();
	};
	
	@RequestMapping(method = RequestMethod.POST, path = "users")
	public ResponseEntity<User> saveUser(@RequestBody User user) {
		User newuser = userService.save(user);		
		URI uriLoc = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(newuser.getId()).toUri();
		return ResponseEntity.created(uriLoc).build();		
	};
	
	@RequestMapping(method = RequestMethod.GET, path = "users/{id}")
	public User findUserById(@PathVariable int id) throws UserNotFoundException {
		User user = userService.findUserById(id);
		if(null == user) {
			throw new UserNotFoundException("User not found for User Id = " + id);
		}
		return user;
	}

}
