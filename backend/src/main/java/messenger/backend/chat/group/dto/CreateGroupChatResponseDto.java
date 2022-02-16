package messenger.backend.chat.group.dto;


import lombok.Value;

import java.util.UUID;

@Value(staticConstructor = "of")
public class CreateGroupChatResponseDto {
    UUID chatId;
}
