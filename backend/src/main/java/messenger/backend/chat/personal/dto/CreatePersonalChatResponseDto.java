package messenger.backend.chat.personal.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CreatePersonalChatResponseDto {
    private UUID chatId;
}
