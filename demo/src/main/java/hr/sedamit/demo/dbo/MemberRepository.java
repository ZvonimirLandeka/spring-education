package hr.sedamit.demo.dbo;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.sedamit.demo.model.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
