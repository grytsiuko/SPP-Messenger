package messenger.backend.chat.group.dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
@NoArgsConstructor
public class ChangeGroupChatNameRequestDto {
    @NonNull
    private UUID chatId;
    @NotBlank
    private String newChatName;
}
