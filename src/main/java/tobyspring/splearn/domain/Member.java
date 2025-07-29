package tobyspring.splearn.domain;

import lombok.Getter;
import lombok.ToString;

import static java.util.Objects.*;
import static org.springframework.util.Assert.*;

@Getter
@ToString
public class Member {

    private String nickName;

    private Email email;

    private String passwordHash;

    private MemberStatus status;

    private Member() {
    }

    public static Member register(MemberRegisterRequest memberRegisterRequest, PasswordEncoder passwordEncoder) {
        Member member = new Member();

        member.email = new Email(memberRegisterRequest.email());
        member.nickName = requireNonNull(memberRegisterRequest.nickName());
        member.passwordHash = requireNonNull(passwordEncoder.encode(memberRegisterRequest.password()));
        member.status = MemberStatus.PENDING;

        return member;
    }

    public void activate() {
        state(status == MemberStatus.PENDING, "PENDING 상태가 아닙니다");

        this.status = MemberStatus.ACTIVE;
    }

    public void deactivate() {
        state(status == MemberStatus.ACTIVE, "ACTIVE 상태가 아닙니다");

        this.status = MemberStatus.DEACTIVATED;
    }

    public boolean verifyPassword(String password, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(password, this.passwordHash);
    }

    public void changeNickName(String newNickName) {
        this.nickName = requireNonNull(newNickName);
    }

    public void changePasword(String newPassword, PasswordEncoder passwordEncoder) {
        this.passwordHash = passwordEncoder.encode(requireNonNull(newPassword));
    }

    public boolean isActive() {
        return this.status == MemberStatus.ACTIVE;
    }

}
