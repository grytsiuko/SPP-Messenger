package messenger.backend.chat.group.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import messenger.backend.chat.GroupChatEntity;
import messenger.backend.user.UserEntity;

@Data
@AllArgsConstructor
public class GroupChatAndUserDto {
    private GroupChatEntity groupChatEntity;
    private UserEntity targetUserEntity;
}
