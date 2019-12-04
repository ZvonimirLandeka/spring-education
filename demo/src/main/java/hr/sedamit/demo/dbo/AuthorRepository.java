package hr.sedamit.demo.dbo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import hr.sedamit.demo.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long>, JpaSpecificationExecutor {

	@Query("from Author a where a.nationality = ?1 and a.yearOfBirth > ?2")
	List<Author> findByNationality(String nationality, int yearOfBirth);

	@Query("from Author a where a.nationality = nationality11 and a.yearOfBirth > year")
	List<Author> findByNationality2(@Param("nationality11") String nationality, @Param("year") int yearOfBirth);
}
