package messenger.backend.chat.exceptions;

import messenger.backend.utils.exceptions.WebException;
import org.springframework.http.HttpStatus;

public class InvalidChatOperationException extends WebException {
    public InvalidChatOperationException() {
        super("Invalid chat operation", HttpStatus.NOT_FOUND);
    }
}
