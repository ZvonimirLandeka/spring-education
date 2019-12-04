package hr.sedamit.demo.dbo;

import org.springframework.data.jpa.domain.Specification;

import hr.sedamit.demo.model.Author;

public class AuthorSpecifications {

	public static Specification<Author> byYearOfBirth(int yearOfBirth) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("yearOfBirth"), yearOfBirth);
	}

}
