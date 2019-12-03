package hr.sedamit.demo.web;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import hr.sedamit.demo.model.User;
import hr.sedamit.demo.service.UserManager;


@RestController
@RequestMapping("user")
public class UserController {

	private UserManager userManager;

	public UserController(UserManager userManager) {
		this.userManager = userManager;
	}

	@GetMapping("/list")
	public List<User> listAllUsers(){
		return userManager.getAllUsers();
	}
	

	@GetMapping("/{userId}")
	public User showUserDetails(@PathVariable Long userId) {
		Optional<User> optionalUser = userManager.getUser(userId);
		if (optionalUser.isPresent()) {
			return optionalUser.get();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with such id not found!");
		}
	}

	@PutMapping("/save")
//	@Transactional
	public User save(@RequestBody User user) {
		User userRetured = userManager.save(user);
		// ovdje ce puknuti i rollbackat ce se cijeli transakcija, ali samo ako je
		// postavljen transactional
//		int a = 1 / 0;
		return userRetured;
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
}
