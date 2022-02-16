package messenger.backend.chat.group.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateGroupChatRequestDto {
    @NotBlank
    private String chatName;
}
