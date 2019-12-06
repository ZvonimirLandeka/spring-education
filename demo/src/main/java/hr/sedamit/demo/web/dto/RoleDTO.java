package hr.sedamit.demo.web.dto;

import java.util.List;

import hr.sedamit.demo.model.Permission;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoleDTO {

	private Long id;
	private String name;
	private List<Permission> permissions;
}
