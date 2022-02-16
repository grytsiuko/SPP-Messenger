package messenger.backend.chat.personal.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class DeletePersonalChatRequestDto {
    @NotNull
    private UUID chatId;
}
