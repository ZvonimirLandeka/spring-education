package hr.sedamit.demo.service;

import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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
	@Cacheable(CacheNames.CACHE_AUTHOR_LIST)
	// po key-u se može npr za razlicite usere cachati
//	@Cacheable(value = CacheNames.CACHE_AUTHOR_LIST, key= "#pageable.pageNumber + #pageable.pageSize")
	public Page<Author> getAllAuthors(Pageable pageable) {
		return repository.findAll(AuthorSpecifications.byYearOfBirth(1900), pageable);
	}

	@Override
	@Cacheable(CacheNames.CACHE_AUTHOR_DETAILS)
	public Optional<Author> getAuthor(Long authorId) {
		log.debug("Fetching author");
		return repository.findById(authorId);
	}

	@Override
	@Caching(evict = { @CacheEvict(value = CacheNames.CACHE_AUTHOR_DETAILS, key = "#author.id"),
			@CacheEvict(value = CacheNames.CACHE_AUTHOR_LIST, allEntries = true) })

	// NACINI SIGURNOSTI NA SAMOJ METODI
//	@Secured("EDIT_AUTHORS")
//	@RolesAllowed("ROLE_ADMIN")
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
