package hr.sedamit.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
@Table(name = "books")
public class Book {

	@Id
	@GeneratedValue
	@Setter(AccessLevel.PRIVATE)
	private Long id;

	@Column(length = 2000)
	private String title;

	// na ovaj nacin bi se spremilo kad se sprema knjiga i njegov autor
//	@ManyToOne(cascade = CascadeType.ALL)
	// ovo je zapravo foreign key
	@ManyToOne
	private Author author;
}
