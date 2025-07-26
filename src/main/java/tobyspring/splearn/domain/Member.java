package tobyspring.splearn.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.Objects;

import static org.springframework.util.Assert.*;

@Getter
@ToString
public class Member {

    private String nickName;

    private String email;

    private String passwordHash;

    private MemberStatus status;

    private Member(String nickName, String email, String passwordHash) {
        this.nickName = Objects.requireNonNull(nickName);
        this.email = Objects.requireNonNull(email);
        this.passwordHash = Objects.requireNonNull(passwordHash);
        this.status = MemberStatus.PENDING;
    }

    public static Member create(String email, String nickName, String password, PasswordEncoder passwordEncoder) {
        return new Member(nickName, email, passwordEncoder.encode(password));
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
        this.nickName = newNickName;
    }

    public void changePasword(String newPassword,PasswordEncoder passwordEncoder) {
        this.passwordHash = passwordEncoder.encode(newPassword);
    }
}
