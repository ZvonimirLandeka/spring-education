package hr.sedamit.demo.service;

import java.util.List;
import java.util.Optional;

import hr.sedamit.demo.model.Member;
public interface MemberManager {
	List<Member> getAllMembers();

	Optional<Member> getMember(Long memberId);

	Member save(Member member);

	void delete(Long memberId);
}
