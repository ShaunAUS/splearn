package tobyspring.splearn.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static tobyspring.splearn.domain.MemberFixture.createMemberRegisterRequest;
import static tobyspring.splearn.domain.MemberFixture.createPasswordEncoder;

class MemberTest {

    Member member;
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        this.passwordEncoder = createPasswordEncoder();
        member = Member.register(createMemberRegisterRequest("mm@naver.com"), passwordEncoder);
    }


    @Test
    void registerMember() {
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }


    @Test
    void activateMember() {
        member.activate();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
    }

    @Test
    void activateFail() {
        member.activate();

        assertThatThrownBy(member::activate)
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void deactivate() {
        member.activate();

        member.deactivate();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.DEACTIVATED);
    }

    @Test
    void deactivateFail() {
        assertThatThrownBy(member::deactivate)
                .isInstanceOf(IllegalStateException.class);

        member.activate();
        member.deactivate();

        assertThatThrownBy(member::deactivate)
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void veryfiyPassword() {
        var result = member.verifyPassword("password123", passwordEncoder);
        assertThat(result).isTrue();

        var resultV2 = member.verifyPassword("password1234", passwordEncoder);
        assertThat(resultV2).isFalse();
    }

    @Test
    void changeNickName() {

        assertThat(member.getNickName()).isEqualTo("testUser");

        member.changeNickName("newNickName");

        assertThat(member.getNickName()).isEqualTo("newNickName");

    }

    @Test
    void changePassword() {
        member.changePasword("newPassword", passwordEncoder);

        assertThat(member.verifyPassword("newPassword", passwordEncoder)).isTrue();
    }

    @Test
    void isActive() {
        assertThat(member.isActive()).isFalse();

        member.activate();
        assertThat(member.isActive()).isTrue();

        member.deactivate();

        assertThat(member.isActive()).isFalse();
    }

    @Test
    void invalidEmail() {
        assertThatThrownBy(() ->
                Member.register(createMemberRegisterRequest("invalidEmail"), passwordEncoder))
                .isInstanceOf(IllegalArgumentException.class);

        Member.register(createMemberRegisterRequest("test@naver.com"), passwordEncoder);

    }
}