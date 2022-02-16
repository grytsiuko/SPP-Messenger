package messenger.backend.chat.group.dto;

import lombok.Builder;
import lombok.Data;
import messenger.backend.userChat.UserChat;

import java.util.UUID;


@Data
@Builder
public class GroupChatUserInfoDto {
    private UUID id;
    private String username;
    private String fullName;
    private UserChat.PermissionLevel permissionLevel;
    private Byte[] profilePicture;

    public static GroupChatUserInfoDto from(UserChat userChat) {
        return GroupChatUserInfoDto.builder()
                .id(userChat.getUser().getId())
                .username(userChat.getUser().getUsername())
                .fullName(userChat.getUser().getFullName())
                .permissionLevel(userChat.getPermissionLevel())
                .profilePicture(userChat.getUser().getProfilePicture())
                .build();
    }
}
