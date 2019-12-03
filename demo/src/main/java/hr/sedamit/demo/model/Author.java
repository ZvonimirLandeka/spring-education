package hr.sedamit.demo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "authors")
public class Author {

	@Id
	@GeneratedValue
	@Setter(AccessLevel.PRIVATE)
	private Long id;

	@Column(length = 200)
	private String firstName;

	private String lastName;

	private String nationality;

	private int yearOfBirth;

	@OneToMany(mappedBy = "author")
	// ovdje je opasno jer se previse informacija dohvaca, a pitanje je hoce li se
	// koristiti
	// @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
	public List<Book> books = new ArrayList<>();

}
