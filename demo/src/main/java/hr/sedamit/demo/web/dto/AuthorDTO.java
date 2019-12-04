package hr.sedamit.demo.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthorDTO {

	private Long id;

	private String firstName;

	private String lastName;

	private String nationality;

	private int yearOfBirth;

}
