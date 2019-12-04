package hr.sedamit.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import hr.sedamit.demo.model.User;
import lombok.extern.slf4j.Slf4j;

@Service
@Profile("test")
@Slf4j
public class DummyUserManager implements UserManager {

	private Map<Long, User> allUsers = new HashMap<>();

	private AtomicLong sequence = new AtomicLong(1);

	@Override
	public List<User> getAllUsers() {
		log.debug("DummyUserManager: called method getAllUsers()");
		return new ArrayList<>(allUsers.values());
	}

	@Override
	public Optional<User> getUser(Long userId) {
		if (allUsers.containsKey(userId)) {
			return Optional.of(allUsers.get(userId));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public User save(User user) {
		if (user.getId() == null || user.getId() == 0) {
			Long idLong = sequence.getAndIncrement();
			User newUser = new User(
					idLong,
					user.getUsername(),
					user.getPassword(),
					user.getFullName(),
					user.getAge(),
					user.isActive()
			);
			allUsers.put(newUser.getId(), newUser);
			return newUser;
		}
		else {
			if (!allUsers.containsKey(user.getId())) {
				throw new RuntimeException("User doesn't exists, id: " + user.getId());
			}
			allUsers.put(user.getId(), user);
			return user;
		}

	}

	@Override
	public void delete(Long userId) {
		allUsers.remove(userId);
	}

	@PostConstruct
	public void init() {
		log.info("DummyUserManager initialized!");
	}

}
