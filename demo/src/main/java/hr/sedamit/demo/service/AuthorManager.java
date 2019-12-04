package hr.sedamit.demo.service;

import java.util.List;
import java.util.Optional;

import hr.sedamit.demo.model.Author;
public interface AuthorManager {
	List<Author> getAllAuthors();

	Optional<Author> getAuthor(Long authorId);

	Author save(Author author);

	void delete(Long authorId);
}
