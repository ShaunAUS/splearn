package tobyspring.splearn.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.util.Assert.isInstanceOf;

class MemberTest {

    @Test
    void createMember() {
        var member = new Member("testUser", "abcc@naver.com", "passwordHash123");

        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

    @Test
    void constructorNullCheck() {
        assertThatThrownBy(() -> new Member(null, "ab@naver.com", "123123"))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void activateMember() {
        var member = new Member("testUser", "abcc@naver.com", "passwordHash123");

        member.activate();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
    }

    @Test
    void activateFail() {
        var member = new Member("testUser", "abcc@naver.com", "passwordHash123");

        member.activate();

        assertThatThrownBy(member::activate)
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void deactivate() {
        var member = new Member("testUser", "abcc@naver.com", "passwordHash123");
        member.activate();

        member.deactivate();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.DEACTIVATED);
    }
    @Test
    void deactivateFail() {
        var member = new Member("testUser", "abcc@naver.com", "passwordHash123");

        assertThatThrownBy(member::deactivate)
                .isInstanceOf(IllegalStateException.class);

        member.activate();
        member.deactivate();

        assertThatThrownBy(member::deactivate)
                .isInstanceOf(IllegalStateException.class);
    }
}