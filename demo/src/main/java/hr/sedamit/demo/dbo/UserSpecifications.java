package hr.sedamit.demo.dbo;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import hr.sedamit.demo.model.User;

public class UserSpecifications {

	public static Specification<User> activeUsers() {
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("active"), Boolean.TRUE);
	}

	public static Specification<User> olderThenUsers(Integer age) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("age"), age);
	}

	public static Specification<User> findUsers(UserSearchFilter filter) {
		
		Specification<User> spec = (Specification<User>) (root, query, criteriaBuilder) -> criteriaBuilder
				.and(new Predicate[0]);


		if (filter.getOlderThenAge() != null) {
			spec.and(activeUsers());
		}

		if (filter.getOlderThenAge() != null) {
			spec = spec.and(olderThenUsers(filter.getOlderThenAge()));
		}

		return spec;
//		return (root, query, criteriaBuilder) -> {
//			List<Predicate> predicates = new ArrayList<>();
//			
//			predicates.add(criteriaBuilder.equal(root.get("active"), filter.isActive()));
//
//			if (filter.getOlderThenAge() != null) {
//				predicates.add(criteriaBuilder.greaterThan(root.get("age"), filter.getOlderThenAge()));
//			}
//
//			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
//		};
	}

}
