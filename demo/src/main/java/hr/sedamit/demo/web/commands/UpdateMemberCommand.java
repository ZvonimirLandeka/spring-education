package hr.sedamit.demo.web.commands;

import hr.sedamit.demo.web.dto.AddressDTO;
import lombok.Getter;

@Getter
public class UpdateMemberCommand extends UpdateUserCommand {

	private String memberId;

	private AddressDTO addressDTO;

	public UpdateMemberCommand(String username, String password, String fullName, int age, boolean active,
			String memberId, AddressDTO addressDTO) {
		super(username, password, fullName, age, active);
		this.memberId = memberId;
		this.addressDTO = addressDTO;
	}
}
