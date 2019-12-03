package hr.sedamit.demo.dbo;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.sedamit.demo.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
