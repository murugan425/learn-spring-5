package tamil.learn.springframework.learnwebservices.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;

import tamil.learn.springframework.learnwebservices.controller.vo.User;

@Component
public class UserDaoService {
	
	private static List<User> users = new ArrayList<>();
	
	private static int usersCount;
	
	static {
		users.add(new User(1, "Murugan", new Date()));
		users.add(new User(2, "Senthamizh", new Date()));
		users.add(new User(3, "Tejashree", new Date()));
		usersCount = users.size();
	}
	
	public User save(User user) {
		if(user.getId() == null) {
			user.setId(++usersCount);
		}
		users.add(user);
		return user;
	}
	
	public List<User> findAllUsers() {
		return users;
	}
	
	public User findUserById(int id) {
		Predicate<User> findById = user -> user.getId()==id;		
		return users.stream().filter(findById).findAny().orElse(null);
	}

	public User deleteUserById(int id) {
		Predicate<User> findById = user -> user.getId()==id;		
		User user = users.stream().filter(findById).findAny().orElse(null);
		if(null != user) {
			users.removeIf(findById);
		}
		return user;
	}
}
