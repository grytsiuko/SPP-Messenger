package messenger.backend.chat.personal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import messenger.backend.chat.PrivateChatEntity;
import messenger.backend.user.dto.UserShortDto;
import messenger.backend.user.exceptions.UserNotFoundException;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PersonalChatResponseDto {

    public static PersonalChatResponseDto fromEntity(PrivateChatEntity privateChatEntity, UUID currentUserId) {
        var companion = privateChatEntity
                .getUserChats()
                .stream()
                .filter(u -> !u.getUser().getId().equals(currentUserId))
                .findFirst()
                .orElseThrow(UserNotFoundException::new);

        return PersonalChatResponseDto.builder()
                .id(privateChatEntity.getId())
                .companion(UserShortDto.fromEntity(companion))
                .build();
    }

    private UUID id;
    private UserShortDto companion;
}
