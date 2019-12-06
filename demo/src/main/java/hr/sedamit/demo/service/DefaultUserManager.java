package hr.sedamit.demo.service;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import hr.sedamit.demo.dbo.UserRepository;
import hr.sedamit.demo.dbo.UserSearchFilter;
import hr.sedamit.demo.dbo.UserSpecifications;
import hr.sedamit.demo.model.User;
import lombok.extern.slf4j.Slf4j;

@Service
@Profile("!test")
@Slf4j
public class DefaultUserManager implements UserManager {

	private UserRepository repository;

	public DefaultUserManager(UserRepository userRepository) {
		this.repository = userRepository;
	}

	@Override
	public List<User> getAllUsers() {

		UserSearchFilter filter = new UserSearchFilter(true, 30);

		Specification<User> specification = UserSpecifications.findUsers(filter);
		return repository.findAll(specification);
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

	@PostConstruct
	public void init() {
		log.info("DefaultUserManager initialized!");
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = repository.findByUsername(username);
		if (user.isPresent()) {
			return user.get();
		} else {
			throw new UsernameNotFoundException("No user with such username: " + username);
		}
	}

}
