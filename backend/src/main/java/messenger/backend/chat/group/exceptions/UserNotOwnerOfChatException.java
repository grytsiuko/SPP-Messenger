package messenger.backend.chat.group.exceptions;

import messenger.backend.utils.exceptions.WebException;
import org.springframework.http.HttpStatus;

public class UserNotOwnerOfChatException extends WebException {
    public UserNotOwnerOfChatException() {
        super("User is not owner of this chat", HttpStatus.FORBIDDEN);
    }
}
