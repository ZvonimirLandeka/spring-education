package hr.sedamit.demo.dbo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import hr.sedamit.demo.model.User;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor {

	// query metoda po nazivlju
	List<User> findByActiveTrue();

	// custom query
	@Query("from User u where u.active = true")
	List<User> findOnlyActiveUsers();


}
