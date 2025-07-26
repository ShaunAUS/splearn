package tobyspring.splearn.domain;

//도메인에서 사용하는 서비스
public interface PasswordEncoder {
    String encode(String password);
    boolean matches(String password, String passwordHash);
}
