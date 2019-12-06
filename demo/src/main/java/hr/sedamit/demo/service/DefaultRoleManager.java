package hr.sedamit.demo.service;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import hr.sedamit.demo.dbo.RoleRepository;
import hr.sedamit.demo.model.Role;
import lombok.extern.slf4j.Slf4j;

@Service
@Profile("!test")
@Slf4j
public class DefaultRoleManager implements RoleManager {

	private RoleRepository repository;

	public DefaultRoleManager(RoleRepository roleRepository) {
		this.repository = roleRepository;
	}

	@Override
	public List<Role> getAllRoles() {
		return repository.findAll();
	}

	@Override
	public Optional<Role> getRole(Long roleId) {
		return repository.findById(roleId);
	}

	@Override
	public Role save(Role role) {
		return repository.save(role);
	}

	@Override
	public void delete(Long roleId) {
		repository.deleteById(roleId);
	}

	@PostConstruct
	public void init() {
		log.info("DefaultRoleManager initialized!");
	}

}
