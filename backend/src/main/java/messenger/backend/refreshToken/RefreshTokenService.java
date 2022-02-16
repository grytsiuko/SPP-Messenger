package messenger.backend.refreshToken;

import lombok.RequiredArgsConstructor;
import messenger.backend.refreshToken.exceptions.RefreshTokenInvalidException;
import messenger.backend.user.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${refresh.validity}")
    private long refreshTokenValidityInMilliseconds;

    public RefreshTokenEntity createByUserEntity(UserEntity userEntity) {
        var refreshTokenEntity = RefreshTokenEntity.fromUserEntity(userEntity);
        return refreshTokenRepository.save(refreshTokenEntity);
    }

    public RefreshTokenEntity getById(UUID id) {
        return refreshTokenRepository
                .findById(id)
                .filter(this::validateRefreshToken)
                .orElseThrow(RefreshTokenInvalidException::new);
    }

    public void deleteById(UUID id) {
        refreshTokenRepository.deleteById(id);
    }

    public void deleteAllByUserEntityId(UUID id) {
        refreshTokenRepository.deleteAllByUserEntityId(id);
    }

    public boolean validateRefreshToken(RefreshTokenEntity refreshTokenEntity) {
        return refreshTokenEntity.getCreatedAt().getTime() + refreshTokenValidityInMilliseconds > new Date().getTime();
    }
}
