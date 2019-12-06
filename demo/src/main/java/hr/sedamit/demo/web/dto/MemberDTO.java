package hr.sedamit.demo.web.dto;

import lombok.Getter;

@Getter
public class MemberDTO extends UserDTO {

	private String memberId;

	private AddressDTO addressDTO;
	
	public MemberDTO(Long id, String username, String fullName, int age, boolean active, String memberId,
			AddressDTO addressDTO, RoleDTO roleDTO) {
		super(id, username, fullName, age, active, roleDTO);
		this.memberId = memberId;
		this.addressDTO = addressDTO;
	}
	
}
