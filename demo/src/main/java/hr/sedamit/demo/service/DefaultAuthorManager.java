package hr.sedamit.demo.service;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import hr.sedamit.demo.dbo.AuthorRepository;
import hr.sedamit.demo.model.Author;
import lombok.extern.slf4j.Slf4j;

@Service
@Profile("!test")
@Slf4j
public class DefaultAuthorManager implements AuthorManager {

	private AuthorRepository repository;

	public DefaultAuthorManager(AuthorRepository authorRepository) {
		this.repository = authorRepository;
	}

	@Override
	public List<Author> getAllAuthors() {
		return repository.findAll();
	}

	@Override
	public Optional<Author> getAuthor(Long authorId) {
		return repository.findById(authorId);
	}

	@Override
	public Author save(Author author) {
		return repository.save(author);
	}

	@Override
	public void delete(Long authorId) {
		repository.deleteById(authorId);
	}

	@PostConstruct
	public void init() {
		log.info("DefaultAuthorManager initialized!");
	}

}
