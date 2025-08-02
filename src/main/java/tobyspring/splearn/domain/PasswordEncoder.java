package tobyspring.splearn.domain;

//도메인에서 사용하는 서비스
// 논리상 application-required 패키지에 위치해야 하지만, 그렇게 되면 의존성 방향이 맞지않음
public interface PasswordEncoder {
    String encode(String password);
    boolean matches(String password, String passwordHash);
}
