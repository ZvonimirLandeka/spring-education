package hr.sedamit.demo.web.gui;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.server.ResponseStatusException;

import hr.sedamit.demo.model.Author;
import hr.sedamit.demo.service.AuthorManager;
import hr.sedamit.demo.web.commands.UpdateAuthorCommand;
import hr.sedamit.demo.web.dto.AuthorDTO;
import hr.sedamit.demo.web.dto.DTOFactory;

@Controller
@RequestMapping("/gui/author")
@SessionAttributes("authorData")
public class AuthorGUIController {

	private final AuthorManager authorManager;

	public AuthorGUIController(AuthorManager authorManager) {
		super();
		this.authorManager = authorManager;
	}

	@GetMapping("/list")
	public String listAuthors(Model model, Pageable pageable) {

		Page<Author> authors = authorManager.getAllAuthors(pageable);

		Page<AuthorDTO> authorDTOs = authors.map(DTOFactory::toAuthorDTO);
		model.addAttribute("authors", authorDTOs);

		return "author-list";
	}

	@GetMapping("/{authorId}/edit")
	public String editAuthor(Model model, @PathVariable Long authorId) {
		UpdateAuthorCommand authorData;
		if (authorId == 0) {
			authorData = new UpdateAuthorCommand();
		} else {
			Optional<Author> optionalAuthor = authorManager.getAuthor(authorId);
			if (!optionalAuthor.isPresent()) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No author with such id: " + authorId);
			}
			authorData = new UpdateAuthorCommand(optionalAuthor.get());
		}
		model.addAttribute("authorData", authorData);
		return "author-edit";
	}

	// zbog anotacije ce se dovuci iz cacha podaci oni koji su poslani u getmappingu
	@PostMapping("/{authorId}/edit")
	public String processEditAuthor(@ModelAttribute("authorData") @Valid UpdateAuthorCommand authorData,
			BindingResult result,
			Model model,
			@PathVariable Long authorId) {

		if (result.hasErrors()) {

			model.addAttribute("authorData", authorData);
			model.addAttribute("error", result.getAllErrors().get(0).getDefaultMessage());
			return "author-edit";
		}

//		result.addError(error);

		Author author;
		if (authorId == 0) {
			author = new Author();
		} else {
			Optional<Author> authorOptional = authorManager.getAuthor(authorId);
			if (!authorOptional.isPresent()) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No author with such id: " + authorId);
			}
			author = authorOptional.get();
		}
		updateAuthorData(author, authorData);
		authorManager.save(author);

		return "redirect:/gui/author/list";
	}

	private void updateAuthorData(Author author, UpdateAuthorCommand command) {
		author.setFirstName(command.getFirstName());
		author.setLastName(command.getLastName());
		author.setNationality(command.getNationality());
		author.setYearOfBirth(command.getYearOfBirth());
	}

}
