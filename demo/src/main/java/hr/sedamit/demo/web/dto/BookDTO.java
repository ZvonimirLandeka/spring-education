package hr.sedamit.demo.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BookDTO {

	private Long id;

	private String title;

	private AuthorDTO author;
}
