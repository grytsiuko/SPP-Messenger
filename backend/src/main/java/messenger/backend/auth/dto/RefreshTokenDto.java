package messenger.backend.auth.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class RefreshTokenDto {
    @NotNull
    private UUID refreshToken;
}
