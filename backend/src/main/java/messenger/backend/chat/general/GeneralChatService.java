package messenger.backend.chat.general;

import lombok.RequiredArgsConstructor;
import messenger.backend.auth.jwt.JwtTokenService;
import messenger.backend.chat.general.dto.GeneralChatResponseDto;
import messenger.backend.message.MessageService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GeneralChatService {

    private final GeneralChatRepository generalChatRepository;
    private final MessageService messageService;

    public List<GeneralChatResponseDto> getAll() {
        var currentUserId = JwtTokenService.getCurrentUserId();
        return generalChatRepository
                .findAllByMemberId(currentUserId)
                .stream()
                .map(chat -> GeneralChatResponseDto.fromEntity(
                        chat,
                        messageService.getLastMessageByChatId(chat.getId()),
                        currentUserId)
                )
                .collect(Collectors.toList());
    }
}
