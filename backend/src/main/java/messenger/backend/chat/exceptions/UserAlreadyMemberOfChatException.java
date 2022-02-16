package messenger.backend.chat.exceptions;

import messenger.backend.utils.exceptions.WebException;
import org.springframework.http.HttpStatus;

public class UserAlreadyMemberOfChatException extends WebException {
    public UserAlreadyMemberOfChatException() {
        super("User is already member of this chat", HttpStatus.CONFLICT);
    }
}
