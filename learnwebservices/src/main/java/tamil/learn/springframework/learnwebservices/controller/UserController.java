package tamil.learn.springframework.learnwebservices.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	@PostMapping(path = "users", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> saveUser(@Valid @RequestBody User user) {
		User newuser = userService.save(user);		
		URI uriLoc = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newuser.getId()).toUri();
		return ResponseEntity.created(uriLoc).build();		
	};
	
	@GetMapping(path = "users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public EntityModel<User> findUserById(@PathVariable int id) throws UserNotFoundException {
		User user = userService.findUserById(id);
		if(null == user) {
			throw new UserNotFoundException("User not found for User Id = " + id);
		}
		WebMvcLinkBuilder linkToAllUsers = linkTo(methodOn(this.getClass()).findAllUsers());
		EntityModel<User> userEntity = new EntityModel<User>(user);
		userEntity.add(linkToAllUsers.withRel("all-users"));
		return userEntity;
	};
	
	@DeleteMapping(path = "users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteUserById(@PathVariable int id) {
		User user = userService.deleteUserById(id);
		if(null == user) {
			throw new UserNotFoundException("User not found for User Id = " + id);
		}
	}

}
