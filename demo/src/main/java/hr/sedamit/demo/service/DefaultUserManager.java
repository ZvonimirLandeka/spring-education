package hr.sedamit.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import hr.sedamit.demo.dbo.UserRepository;
import hr.sedamit.demo.model.User;

@Service
public class DefaultUserManager implements UserManager {

	private UserRepository repository;

	public DefaultUserManager(UserRepository userRepository) {
		this.repository = userRepository;
	}

	@Override
	public List<User> getAllUsers() {
		return repository.findAll();
	}

	@Override
	public Optional<User> getUser(Long userId) {
		return repository.findById(userId);
	}

	@Override
	public User save(User user) {
		return repository.save(user);
	}

	@Override
	public void delete(Long userId) {
		repository.deleteById(userId);
	}

}
