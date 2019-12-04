package hr.sedamit.demo.web.commands;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import hr.sedamit.demo.model.Author;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class UpdateAuthorCommand {

	@NotEmpty
	@Size(min = 2, max = 200, message = "Name must be length beetwen 2 and 200 chars")
	private String firstName;

	private String lastName;

	private String nationality;

	private int yearOfBirth;

	public UpdateAuthorCommand(Author author) {
		this.firstName = author.getFirstName();
		this.lastName = author.getLastName();
		this.nationality = author.getNationality();
		this.yearOfBirth = author.getYearOfBirth();
	}
}
