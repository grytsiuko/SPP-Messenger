package messenger.backend.auth.security;

import lombok.RequiredArgsConstructor;
import messenger.backend.user.UserEntity;
import messenger.backend.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SecurityUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userEntityOptional = userRepository.getByUsername(username);
        return new SecurityUser(
                userEntityOptional
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username")));
    }
}
