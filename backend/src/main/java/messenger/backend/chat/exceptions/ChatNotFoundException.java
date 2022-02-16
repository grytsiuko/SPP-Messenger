package messenger.backend.chat.exceptions;

import messenger.backend.utils.exceptions.WebException;
import org.springframework.http.HttpStatus;

public class ChatNotFoundException extends WebException {
    public ChatNotFoundException() {
        super("Chat with this id not found", HttpStatus.NOT_FOUND);
    }
}
