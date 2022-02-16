package messenger.backend.chat.group.dto;

import lombok.Builder;
import lombok.Data;
import messenger.backend.userChat.UserChat;

@Data
@Builder
public class UserChatsDto {
    private UserChat contextUserChatEntity;
    private UserChat targetUserChatEntity;
}
