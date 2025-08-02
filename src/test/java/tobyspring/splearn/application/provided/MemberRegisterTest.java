package tobyspring.splearn.application.provided;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJAdviceParameterNameDiscoverer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import tobyspring.splearn.application.required.MemberRepository;
import tobyspring.splearn.domain.Member;
import tobyspring.splearn.domain.MemberFixture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static tobyspring.splearn.domain.MemberFixture.createPasswordEncoder;

@DataJpaTest
class MemberRegisterTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager entityManager;


    @Test
    void createMember() {
        Member member = Member.register(MemberFixture.createMemberRegisterRequest("testEmail@naver.com"),createPasswordEncoder());

        assertThat(member.getId()).isNull();

        memberRepository.save(member);

        assertThat(member.getId()).isNotNull();

        entityManager.flush();

    }
}