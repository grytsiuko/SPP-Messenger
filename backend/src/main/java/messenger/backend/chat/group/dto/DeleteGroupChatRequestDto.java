package messenger.backend.chat.group.dto;


import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class DeleteGroupChatRequestDto {
    @NotNull
    private UUID chatId;
}
