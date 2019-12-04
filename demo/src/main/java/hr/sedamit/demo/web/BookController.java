package hr.sedamit.demo.web;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import hr.sedamit.demo.model.Author;
import hr.sedamit.demo.model.Book;
import hr.sedamit.demo.service.AuthorManager;
import hr.sedamit.demo.service.BookManager;
import hr.sedamit.demo.web.commands.UpdateBookCommand;
import hr.sedamit.demo.web.dto.BookDTO;
import hr.sedamit.demo.web.dto.DTOFactory;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("book")
@Slf4j
public class BookController {

	private BookManager bookManager;

	private AuthorManager authorManager;


	public BookController(BookManager bookManager, AuthorManager authorManager) {
		this.bookManager = bookManager;
		this.authorManager = authorManager;
	}

	@GetMapping("/list")
	public List<BookDTO> listAllBooks() {
		List<Book> books = bookManager.getAllBooks();
		return books.stream().map(DTOFactory::toBookDTO).collect(Collectors.toList());
	}
	

	@GetMapping("/{bookId}")
	public BookDTO showBookDetails(@PathVariable Long bookId) {
		Optional<Book> optionalBook = bookManager.getBook(bookId);
		if (optionalBook.isPresent()) {
			return DTOFactory.toBookDTO(optionalBook.get());
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book with such id not found!");
		}
	}

	@PostMapping("/add")
//	@Transactional
	public BookDTO addBook(@RequestBody UpdateBookCommand bookData) {
		try {
			Book book = new Book();
			updateBookData(book, bookData);
			Book savedBook = bookManager.save(book);
			return DTOFactory.toBookDTO(savedBook);
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
		// Book bookRetured = bookManager.save(book);
		// ovdje ce puknuti i rollbackat ce se cijeli transakcija, ali samo ako je
		// postavljen transactional
//		int a = 1 / 0;
//		return bookRetured;
	}

	@PutMapping("/{bookId}/update")
	public BookDTO updateBook(@PathVariable Long bookId, @RequestBody UpdateBookCommand bookData) {

		Optional<Book> book = bookManager.getBook(bookId);

		if (!book.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No book with such id: " + bookId);
		} else {
			try {
				updateBookData(book.get(), bookData);
				Book savedBook = bookManager.save(book.get());
				return DTOFactory.toBookDTO(savedBook);
			} catch (IllegalArgumentException e) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
			}
		}
	}

	@DeleteMapping("/delete/{bookId}")
	public Boolean deleteBook(@PathVariable Long bookId)
	{
		try {
			bookManager.delete(bookId);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@PostConstruct
	public void init() {
		log.info("BookController ready for using. Initialized!");
	}

	private void updateBookData(Book book, UpdateBookCommand command) {
		book.setTitle(command.getTitle());
		Optional<Author> authorOptional = authorManager.getAuthor(command.getAuthorId());
		if (authorOptional.isPresent()) {
			book.setAuthor(authorOptional.get());
		} else {
			throw new IllegalArgumentException("Not existing author id: " + command.getAuthorId());
		}
	}

}
