package hr.sedamit.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;

import hr.sedamit.demo.model.User;

public interface UserManager extends UserDetailsService {
	List<User> getAllUsers();

	Optional<User> getUser(Long userId);

	User save(User user);

	void delete(Long userId);
}
