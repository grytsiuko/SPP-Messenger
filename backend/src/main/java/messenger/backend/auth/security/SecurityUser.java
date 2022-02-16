package messenger.backend.auth.security;

import lombok.Getter;
import messenger.backend.user.UserEntity;
import org.springframework.security.core.userdetails.User;


@Getter
public class SecurityUser extends User {

    private final UserEntity userEntity;

    public SecurityUser(UserEntity userEntity) {
        super(userEntity.getUsername(), userEntity.getPassword(), userEntity.getRole().getAuthorities());
        this.userEntity = userEntity;
    }
}
