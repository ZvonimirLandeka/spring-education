package hr.sedamit.demo.service;

import java.util.List;
import java.util.Optional;

import hr.sedamit.demo.model.User;
public interface UserManager {
	List<User> getAllUsers();

	Optional<User> getUser(Long userId);

	User save(User user);

	void delete(Long userId);
}
