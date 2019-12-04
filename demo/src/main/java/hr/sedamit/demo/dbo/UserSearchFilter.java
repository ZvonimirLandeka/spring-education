package hr.sedamit.demo.dbo;

import lombok.Getter;

@Getter
public class UserSearchFilter {

	private Boolean active;

	private Integer olderThenAge;

	public UserSearchFilter(Boolean active, Integer olderThenAge) {
		super();
		this.active = active;
		this.olderThenAge = olderThenAge;
	}

}
