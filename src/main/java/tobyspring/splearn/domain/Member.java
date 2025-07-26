package tobyspring.splearn.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.Objects;

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

    public static Member create(MemberCreateRequest memberCreateRequest, PasswordEncoder passwordEncoder) {
        Member member = new Member();

        member.email = new Email(memberCreateRequest.email());
        member.nickName = requireNonNull(memberCreateRequest.nickName());
        member.passwordHash = requireNonNull(passwordEncoder.encode(memberCreateRequest.password()));
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
