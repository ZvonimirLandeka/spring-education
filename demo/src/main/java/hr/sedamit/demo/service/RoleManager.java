package hr.sedamit.demo.service;

import java.util.List;
import java.util.Optional;

import hr.sedamit.demo.model.Role;

public interface RoleManager {
	List<Role> getAllRoles();

	Optional<Role> getRole(Long roleId);

	Role save(Role role);

	void delete(Long roleId);
}
