package messenger.backend.user.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UpdateProfileRequestDto {
    @NotEmpty
    private String fullName;
    @NotNull
    private String bio;
}
