package hr.sedamit.demo.dbo;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.sedamit.demo.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

}
