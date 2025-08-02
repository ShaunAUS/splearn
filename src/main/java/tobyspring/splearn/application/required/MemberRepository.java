package tobyspring.splearn.application.required;

import org.springframework.data.repository.Repository;
import tobyspring.splearn.domain.Member;

// jpa가 제공하는 repository 상속받아도 괜찮다.
public interface MemberRepository extends Repository<Member, Long> {

    Member save(Member member);

    Member findByEmail(String email);

    Member findById(Long id);

    boolean existsByEmail(String email);

    void delete(Member member);


}

