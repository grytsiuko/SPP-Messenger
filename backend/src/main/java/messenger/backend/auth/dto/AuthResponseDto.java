package messenger.backend.auth.dto;

import lombok.Value;

import java.util.UUID;

@Value(staticConstructor = "of")
public class AuthResponseDto {
    String accessToken;
    UUID refreshToken;
}
