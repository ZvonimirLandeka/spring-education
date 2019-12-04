package hr.sedamit.demo.web;

import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
import hr.sedamit.demo.service.AuthorManager;
import hr.sedamit.demo.web.commands.UpdateAuthorCommand;
import hr.sedamit.demo.web.dto.AuthorDTO;
import hr.sedamit.demo.web.dto.DTOFactory;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("author")
@Slf4j
public class AuthorController {

	private AuthorManager authorManager;

//	private Logger log = LoggerFactory.getLogger(AuthorController.class);


	public AuthorController(AuthorManager authorManager) {
		this.authorManager = authorManager;
	}

	@GetMapping("/list")
	public Page<AuthorDTO> listAllAuthors(@PageableDefault(size = 10) Pageable pageable, Sort sort) {
		Page<Author> authors = authorManager.getAllAuthors(pageable);

		return authors.map(DTOFactory::toAuthorDTO);
	}
	

	@GetMapping("/{authorId}")
	public AuthorDTO showAuthorDetails(@PathVariable Long authorId) {
		Optional<Author> optionalAuthor = authorManager.getAuthor(authorId);
		if (optionalAuthor.isPresent()) {
			return DTOFactory.toAuthorDTO(optionalAuthor.get());
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Author with such id not found!");
		}
	}

	@PostMapping("/add")
//	@Transactional
	public AuthorDTO addAuthor(@RequestBody UpdateAuthorCommand authorData) {
		Author author = new Author();
		updateAuthorData(author, authorData);
		Author savedAuthor = authorManager.save(author);
		return DTOFactory.toAuthorDTO(savedAuthor);

		// Author authorRetured = authorManager.save(author);
		// ovdje ce puknuti i rollbackat ce se cijeli transakcija, ali samo ako je
		// postavljen transactional
//		int a = 1 / 0;
//		return authorRetured;
	}

	@PutMapping("/{authorId}/update")
	public AuthorDTO updateAuthor(@PathVariable Long authorId, @RequestBody UpdateAuthorCommand authorData) {

		Optional<Author> author = authorManager.getAuthor(authorId);

		if (!author.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No author with such id: " + authorId);
		} else {
			updateAuthorData(author.get(), authorData);
			Author savedAuthor = authorManager.save(author.get());
			return DTOFactory.toAuthorDTO(savedAuthor);
		}
	}

	@DeleteMapping("/delete/{authorId}")
	public Boolean deleteAuthor(@PathVariable Long authorId)
	{
		try {
			authorManager.delete(authorId);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@PostConstruct
	public void init() {
		log.info("AuthorController ready for using. Initialized!");
	}

	private void updateAuthorData(Author author, UpdateAuthorCommand command) {
		author.setFirstName(command.getFirstName());
		author.setLastName(command.getLastName());
		author.setNationality(command.getNationality());
		author.setYearOfBirth(command.getYearOfBirth());
	}
}
