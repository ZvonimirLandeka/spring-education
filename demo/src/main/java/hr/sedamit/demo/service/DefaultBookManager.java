package hr.sedamit.demo.service;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import hr.sedamit.demo.dbo.BookRepository;
import hr.sedamit.demo.model.Book;
import lombok.extern.slf4j.Slf4j;

@Service
@Profile("!test")
@Slf4j
public class DefaultBookManager implements BookManager {

	private BookRepository repository;

	public DefaultBookManager(BookRepository bookRepository) {
		this.repository = bookRepository;
	}

	@Override
	public List<Book> getAllBooks() {
		return repository.findAll();
	}

	@Override
	public Optional<Book> getBook(Long bookId) {
		return repository.findById(bookId);
	}

	@Override
	public Book save(Book book) {
		return repository.save(book);
	}

	@Override
	public void delete(Long bookId) {
		repository.deleteById(bookId);
	}

	@PostConstruct
	public void init() {
		log.info("DefaultBookManager initialized!");
	}

}
