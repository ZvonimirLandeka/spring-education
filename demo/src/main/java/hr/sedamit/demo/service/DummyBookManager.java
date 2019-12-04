package hr.sedamit.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import hr.sedamit.demo.model.Book;
import lombok.extern.slf4j.Slf4j;

@Service
@Profile("test")
@Slf4j
public class DummyBookManager implements BookManager {

	private Map<Long, Book> allBooks = new HashMap<>();

	private AtomicLong sequence = new AtomicLong(1);

	@Override
	public List<Book> getAllBooks() {
		log.debug("DummyBookManager: called method getAllBooks()");
		return new ArrayList<>(allBooks.values());
	}

	@Override
	public Optional<Book> getBook(Long bookId) {
		if (allBooks.containsKey(bookId)) {
			return Optional.of(allBooks.get(bookId));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Book save(Book book) {
		if (book.getId() == null || book.getId() == 0) {
			Long idLong = sequence.getAndIncrement();
			Book newBook = new Book(
//					idLong,
//					book.getBookname(), book.getPassword(), book.getFullName(), book.getAge(), book.isActive()
			);
			allBooks.put(newBook.getId(), newBook);
			return newBook;
		}
		else {
			if (!allBooks.containsKey(book.getId())) {
				throw new RuntimeException("Book doesn't exists, id: " + book.getId());
			}
			allBooks.put(book.getId(), book);
			return book;
		}

	}

	@Override
	public void delete(Long bookId) {
		allBooks.remove(bookId);
	}

	@PostConstruct
	public void init() {
		log.info("DummyBookManager initialized!");
	}

}
