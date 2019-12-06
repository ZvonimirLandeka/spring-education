package hr.sedamit.demo.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserDTO {

	private Long id;

	private String username;

	private String fullName;

	private transient int age;

	private boolean active;
	
	private RoleDTO role;
}
