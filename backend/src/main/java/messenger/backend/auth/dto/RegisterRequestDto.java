package messenger.backend.auth.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class RegisterRequestDto {
    @NotEmpty
    private String username;
    @NotEmpty
    private String fullName;
    @NotEmpty
    private String password;
}
