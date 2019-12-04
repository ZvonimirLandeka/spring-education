package hr.sedamit.demo.service;

import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import hr.sedamit.demo.dbo.AuthorRepository;
import hr.sedamit.demo.dbo.AuthorSpecifications;
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
	public Page<Author> getAllAuthors(Pageable pageable) {
		return repository.findAll(AuthorSpecifications.byYearOfBirth(1900), pageable);
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
