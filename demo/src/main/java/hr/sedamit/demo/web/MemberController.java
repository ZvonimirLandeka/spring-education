package hr.sedamit.demo.web;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import hr.sedamit.demo.model.Address;
import hr.sedamit.demo.model.Member;
import hr.sedamit.demo.service.MemberManager;
import hr.sedamit.demo.web.commands.UpdateMemberCommand;
import hr.sedamit.demo.web.dto.AddressDTO;
import hr.sedamit.demo.web.dto.DTOFactory;
import hr.sedamit.demo.web.dto.MemberDTO;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("member")
@Slf4j
public class MemberController {

	private MemberManager memberManager;

//	private Logger log = LoggerFactory.getLogger(MemberController.class);

	@Value("${member.list.allowed:true}")
	private boolean allowListMembers;

	public MemberController(MemberManager memberManager) {
		this.memberManager = memberManager;
	}

	@GetMapping("/list")
	public List<MemberDTO> listAllMembers() {
		if (allowListMembers) {
			List<Member> members = memberManager.getAllMembers();
			return members.stream().map(DTOFactory::toMemberDTO).collect(Collectors.toList());
		} else {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Member listing not allowed!");
		}
	}
	

	@GetMapping("/{memberId}")
	public MemberDTO showMemberDetails(@PathVariable Long memberId) {
		Optional<Member> optionalMember = memberManager.getMember(memberId);
		if (optionalMember.isPresent()) {
			return DTOFactory.toMemberDTO(optionalMember.get());
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Member with such id not found!");
		}
	}

	@PostMapping("/add")
//	@Transactional
	public MemberDTO addMember(@RequestBody UpdateMemberCommand memberData) {
		Member member = new Member();
		updateMemberData(member, memberData, true);
		Member savedMember = memberManager.save(member);
		return DTOFactory.toMemberDTO(savedMember);

		// Member memberRetured = memberManager.save(member);
		// ovdje ce puknuti i rollbackat ce se cijeli transakcija, ali samo ako je
		// postavljen transactional
//		int a = 1 / 0;
//		return memberRetured;
	}

	@PutMapping("/{memberId}/update")
	public MemberDTO updateMember(@PathVariable Long memberId, @RequestBody UpdateMemberCommand memberData) {

		Optional<Member> member = memberManager.getMember(memberId);

		if (!member.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No member with such id: " + memberId);
		} else {
			updateMemberData(member.get(), memberData, false);
			Member savedMember = memberManager.save(member.get());
			return DTOFactory.toMemberDTO(savedMember);
		}
	}

	@DeleteMapping("/delete/{memberId}")
	public Boolean deleteMember(@PathVariable Long memberId)
	{
		try {
			memberManager.delete(memberId);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@PostConstruct
	public void init() {
		log.info("MemberController ready for using. Initialized!");
	}

	private void updateMemberData(Member member, UpdateMemberCommand command, boolean isNew) {
		member.setUsername(command.getUsername());
		if (isNew) {
			member.setPassword(command.getPassword());
		}
		member.setFullName(command.getFullName());
		member.setAge(command.getAge());
		member.setActive(command.isActive());
		member.setMemberId(command.getMemberId());
		member.setAddress(toAddress(command.getAddressDTO()));
	}

	private Address toAddress(AddressDTO addressDTO) {
		return new Address(addressDTO.getCountry(), addressDTO.getCity(), addressDTO.getStreet(),
				addressDTO.getStreetNumber());
	}
}
