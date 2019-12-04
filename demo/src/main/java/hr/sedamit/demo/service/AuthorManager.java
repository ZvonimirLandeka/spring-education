package hr.sedamit.demo.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import hr.sedamit.demo.model.Author;
public interface AuthorManager {

	Page<Author> getAllAuthors(Pageable pageable);

	Optional<Author> getAuthor(Long authorId);

	Author save(Author author);

	void delete(Long authorId);
}
