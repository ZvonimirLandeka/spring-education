package hr.sedamit.demo.web;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import hr.sedamit.demo.model.Role;
import hr.sedamit.demo.service.RoleManager;
import hr.sedamit.demo.web.dto.DTOFactory;
import hr.sedamit.demo.web.dto.RoleDTO;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("role")
@Slf4j
public class RoleController {

	private RoleManager roleManager;

	// ne treba zbog slf4j
//	private Logger log = LoggerFactory.getLogger(RoleController.class);

	private PasswordEncoder passwordEncoder;

	@Value("${role.list.allowed:true}")
	private boolean allowListRoles;

	public RoleController(RoleManager roleManager, PasswordEncoder passwordEncoder) {
		this.roleManager = roleManager;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping("/list")
	public List<RoleDTO> listAllRoles() {
		if (allowListRoles) {
			List<Role> roles = roleManager.getAllRoles();
			return roles.stream().map(DTOFactory::toRoleDTO).collect(Collectors.toList());
		} else {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Role listing not allowed!");
		}
	}
	

	@GetMapping("/{roleId}")
	public RoleDTO showRoleDetails(@PathVariable Long roleId) {
		Optional<Role> optionalRole = roleManager.getRole(roleId);
		if (optionalRole.isPresent()) {
			return DTOFactory.toRoleDTO(optionalRole.get());
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role with such id not found!");
		}
	}

	@PostMapping("/add")
//	@Transactional
	public RoleDTO addRole(@RequestBody RoleDTO roleData) {
		Role role = new Role();
		updateRoleData(role, roleData);
		Role savedRole = roleManager.save(role);
		return DTOFactory.toRoleDTO(savedRole);

		// Role roleRetured = roleManager.save(role);
		// ovdje ce puknuti i rollbackat ce se cijeli transakcija, ali samo ako je
		// postavljen transactional
//		int a = 1 / 0;
//		return roleRetured;
	}

	@PutMapping("/{roleId}/update")
	public RoleDTO updateRole(@PathVariable Long roleId, @RequestBody RoleDTO roleData) {

		Optional<Role> role = roleManager.getRole(roleId);

		if (!role.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No role with such id: " + roleId);
		} else {
			updateRoleData(role.get(), roleData);
			Role savedRole = roleManager.save(role.get());
			return DTOFactory.toRoleDTO(savedRole);
		}
	}

	@DeleteMapping("/delete/{roleId}")
	public Boolean deleteRole(@PathVariable Long roleId)
	{
		try {
			roleManager.delete(roleId);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@PostConstruct
	public void init() {
		log.info("RoleController ready for using. Initialized!");
	}

	private void updateRoleData(Role role, RoleDTO command) {
		role.setName(command.getName());
		role.setPermissions(command.getPermissions());
	}
}
