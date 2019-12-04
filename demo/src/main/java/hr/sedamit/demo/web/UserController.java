package hr.sedamit.demo.web;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import hr.sedamit.demo.model.User;
import hr.sedamit.demo.service.UserManager;
import hr.sedamit.demo.web.commands.UpdateUserCommand;
import hr.sedamit.demo.web.dto.DTOFactory;
import hr.sedamit.demo.web.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("user")
@Slf4j
public class UserController {

	private UserManager userManager;

//	private Logger log = LoggerFactory.getLogger(UserController.class);

	@Value("${user.list.allowed:true}")
	private boolean allowListUsers;

	public UserController(UserManager userManager) {
		this.userManager = userManager;
	}

	@GetMapping("/list")
	public List<UserDTO> listAllUsers() {
		if (allowListUsers) {
			List<User> users = userManager.getAllUsers();
			return users.stream().map(DTOFactory::toUserDTO).collect(Collectors.toList());
		} else {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User listing not allowed!");
		}
	}
	

	@GetMapping("/{userId}")
	public UserDTO showUserDetails(@PathVariable Long userId) {
		Optional<User> optionalUser = userManager.getUser(userId);
		if (optionalUser.isPresent()) {
			return DTOFactory.toUserDTO(optionalUser.get());
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with such id not found!");
		}
	}

	@PostMapping("/add")
//	@Transactional
	public UserDTO addUser(@RequestBody UpdateUserCommand userData) {
		User user = new User();
		updateUserData(user, userData, true);
		User savedUser = userManager.save(user);
		return DTOFactory.toUserDTO(savedUser);

		// User userRetured = userManager.save(user);
		// ovdje ce puknuti i rollbackat ce se cijeli transakcija, ali samo ako je
		// postavljen transactional
//		int a = 1 / 0;
//		return userRetured;
	}

	@PutMapping("/{userId}/update")
	public UserDTO updateUser(@PathVariable Long userId, @RequestBody UpdateUserCommand userData) {

		Optional<User> user = userManager.getUser(userId);

		if (!user.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user with such id: " + userId);
		} else {
			updateUserData(user.get(), userData, false);
			User savedUser = userManager.save(user.get());
			return DTOFactory.toUserDTO(savedUser);
		}
	}

	@DeleteMapping("/delete/{userId}")
	public Boolean deleteUser(@PathVariable Long userId)
	{
		try {
			userManager.delete(userId);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@PostConstruct
	public void init() {
		log.info("UserController ready for using. Initialized!");
	}

	private void updateUserData(User user, UpdateUserCommand command, boolean isNew) {
		user.setUsername(command.getUsername());
		if (isNew) {
			user.setPassword(command.getPassword());
		}
		user.setFullName(command.getFullName());
		user.setAge(command.getAge());
		user.setActive(command.isActive());
	}
}
