package messenger.backend.message.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import messenger.backend.message.MessageEntity;

import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MessageResponseDto {

    public static MessageResponseDto fromEntity(MessageEntity messageEntity) {
        return MessageResponseDto.builder()
                .id(messageEntity.getId())
                .text(messageEntity.getMessageBody())
                .senderName(messageEntity.getUserChat().getUser().getFullName())
                .senderId(messageEntity.getUserChat().getUser().getId())
                .createdAt(messageEntity.getCreatedAt())
                .build();
    }

    private UUID id;
    private String text;
    private String senderName;
    private UUID senderId;
    private Date createdAt;
}
