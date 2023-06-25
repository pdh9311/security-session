package study.securitysession.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import study.securitysession.model.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);
}
