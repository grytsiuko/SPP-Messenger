package messenger.backend.user;

import lombok.RequiredArgsConstructor;
import messenger.backend.auth.jwt.JwtTokenService;
import messenger.backend.user.dto.UpdateProfileRequestDto;
import messenger.backend.user.dto.UserSearchInfoDto;
import messenger.backend.user.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserSearchInfoDto getUserSearchInfo(String username) {
        return userRepository.getByUsername(username)
                .map(UserSearchInfoDto::from)
                .orElseThrow(UserNotFoundException::new);
    }

    // TODO delete
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public void updateProfile(UpdateProfileRequestDto requestDto) {
        UserEntity userEntity = JwtTokenService.getContextUser();
        userEntity.setFullName(requestDto.getFullName());
        userEntity.setBio(requestDto.getBio());
        userRepository.saveAndFlush(userEntity);
    }
}
