package hr.sedamit.demo.dbo;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.sedamit.demo.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
