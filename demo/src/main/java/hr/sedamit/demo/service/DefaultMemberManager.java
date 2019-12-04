package hr.sedamit.demo.service;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import hr.sedamit.demo.dbo.MemberRepository;
import hr.sedamit.demo.model.Member;
import lombok.extern.slf4j.Slf4j;

@Service
@Profile("!test")
@Slf4j
public class DefaultMemberManager implements MemberManager {

	private MemberRepository repository;

	public DefaultMemberManager(MemberRepository memberRepository) {
		this.repository = memberRepository;
	}

	@Override
	public List<Member> getAllMembers() {
		return repository.findAll();
	}

	@Override
	public Optional<Member> getMember(Long memberId) {
		return repository.findById(memberId);
	}

	@Override
	public Member save(Member member) {
		return repository.save(member);
	}

	@Override
	public void delete(Long memberId) {
		repository.deleteById(memberId);
	}

	@PostConstruct
	public void init() {
		log.info("DefaultMemberManager initialized!");
	}

}
