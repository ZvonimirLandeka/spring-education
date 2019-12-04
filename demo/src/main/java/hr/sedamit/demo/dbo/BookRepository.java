package hr.sedamit.demo.dbo;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.sedamit.demo.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
