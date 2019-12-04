package hr.sedamit.demo.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("MEMBER")
@Getter @Setter
public class Member extends User {

	private String memberId;

	@Embedded
	private Address address;
}
